package com.webgram.dgpsn.services.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khoutech.openexcel.beans.ExcelBean;
import com.khoutech.openexcel.beans.ExcelBeanBuilder;
import com.khoutech.openexcel.models.ExcelContentType;
import com.khoutech.openexcel.services.WorkbookService;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.webgram.dgpsn.annotations.Journal;
import com.webgram.dgpsn.entities.ReviewEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.ReviewMapper;
import com.webgram.dgpsn.models.DownloadFile;
import com.webgram.dgpsn.models.ReviewDTO;
import com.webgram.dgpsn.properties.DocumentProperties;
import com.webgram.dgpsn.repositories.ManagementUnitRepository;
import com.webgram.dgpsn.repositories.ReviewRepository;
import com.webgram.dgpsn.services.DataStorageService;
import com.webgram.dgpsn.services.ReviewService;
import com.webgram.dgpsn.services.modelExcel.ReviewExcelDTO;
import com.webgram.dgpsn.services.utils.DownloadFileUtils;
import com.webgram.dgpsn.tools.ActionType;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidParameterException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    static final String INVALID_EXTENSION_MESSAGE = "File: {0} does not match expected extension: {1}";

    static final String DOCUMENT_ROOT_DIRECTORY = "documents";

    static final String DOCUMENT = "review-";

    final DocumentProperties documentProperties;

    final DataStorageService dataStorageService;

    private final ObjectMapper objectMapper;

    private final WorkbookService workbookService;
    private final ManagementUnitRepository managementUnitRepository;

    String REVIEW_IDENTIFIER_NOT_FOUND_MESSAGE = "Invalide id review {0}";

    @Override
    @Journal(actionType = ActionType.ADD_REVIEW)
    public ReviewDTO createReview(MultipartFile file, String review) throws IOException {
        var reviewDTO = objectMapper.readValue(review, ReviewDTO.class);
        var createdReview = reviewRepository.save(reviewMapper.asEntity(reviewDTO));
        if(Objects.nonNull(file)){
            addFile(createdReview.getId(), file);
        }
        log.info("createdReview end ok - createdReviewId: {}", createdReview.getId());
        log.trace("createdReview end ok - createdReview: {}", createdReview);
        return reviewMapper.asDto(createdReview);
    }

    @Override
    @Journal(actionType = ActionType.UPDATE_REVIEW)
    public ReviewDTO updateReview(MultipartFile file, ReviewDTO reviewDTO) throws IOException {
        if(!reviewRepository.existsById(reviewDTO.getId())){
            throw new ResourceNotFoundException(MessageFormat.format(REVIEW_IDENTIFIER_NOT_FOUND_MESSAGE, reviewDTO.getId()));
        }
        var review = reviewMapper.asEntity(reviewDTO);
        review.setPath(reviewRepository.findById(reviewDTO.getId()).get().getPath());
        var updatedReview = reviewRepository.save(reviewMapper.asEntity(reviewDTO));
        if(Objects.nonNull(file)){
            addFile(updatedReview.getId(), file);
        }
        log.info("updatedReview ok id {}", updatedReview.getId());
        log.trace("updatedReview ok  {}", updatedReview);
        return reviewMapper.asDto(updatedReview);
    }

    @Override
    public ReviewDTO readReview(Long id) {
        var review = reviewRepository.findById(id)
                .map(reviewMapper::asDto)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(REVIEW_IDENTIFIER_NOT_FOUND_MESSAGE, id)));
        log.info("read review end ok - Id: {}", id);
        log.trace("read review end ok - review: {}", review);
        return review;
    }

    @Override
    @Journal(actionType = ActionType.DELETE_REVIEW)
    public void deleteReview(Long id) {
        if(!reviewRepository.existsById(id)){
            throw new ResourceNotFoundException(MessageFormat.format(REVIEW_IDENTIFIER_NOT_FOUND_MESSAGE, id));
        }
        reviewRepository.deleteById(id);
        log.info("delete review ok id {}", id);
    }

    @Override
    @Journal(actionType = ActionType.READ_REVIEW)
    public Page<ReviewDTO> readAllReview(Pageable pageable, Long projetId, String keyPoint, String date) throws ParseException {
        var reviews = reviewRepository
                .readAllByFilters(pageable, projetId, keyPoint, date)
                .map(reviewMapper::asDto);
        log.trace("list review ok {}", reviews);
        return reviews;
    }

    @Override
    public DownloadFile readFile(Long id) {

        ReviewDTO review = readReview(id);

        /* Getting downloadFile */
        DownloadFile downloadFile = DownloadFileUtils.generateDownloadFile(review.getPath());

        log.info("readFile end ok - assignmentId: {}", id);
        log.trace("readFile end ok - downloadFile: {}", downloadFile);

        return downloadFile;
    }

    @Override
    @Journal(actionType = ActionType.IMPORT_REVIEW)
    public void importReview(MultipartFile file, Long porjectId) {
        try (Workbook workbook = workbookService.findWorkBook(file.getInputStream(), ExcelContentType.fromContentType(file.getContentType()))) {
            Sheet sheet = workbook.getSheetAt(0);
            ExcelBean<ReviewExcelDTO> reviewExcelDTOExcelBean = new ExcelBeanBuilder<>(sheet, ReviewExcelDTO.class)
                    .skipLines(0)
                    .build();
            List<ReviewExcelDTO> reviewExcelDTOS = reviewExcelDTOExcelBean.parse();

            var projet = managementUnitRepository.findById(porjectId)
                    .orElseThrow(() -> new ResourceNotFoundException("Project avec id {} introuvable"));

            List<ReviewEntity> reviews = reviewExcelDTOS.stream()
                    .map(reviewMapper::asEntity)
                    .map(review -> review.setProjet(projet))
                    .collect(Collectors.toList());

            reviewRepository.saveAll(reviews);

            log.info("importReview end ok");
            log.trace("importReview end ok - projects: {}", reviews);
        } catch (IOException e) {
            log.info("Exceptions handle import file =============== {0}", e);
            throw new InvalidParameterException(MessageFormat.format("Exceptions handle import file ", "Banner", "idexists"));
        }
    }

    @Override
    @Journal(actionType = ActionType.EXPORT_REVIEW_TO_EXCEL)
    public void exportReview(PrintWriter writer) {
        /* Creating header */
        writer.append(Arrays.stream(ReviewExcelDTO.class.getDeclaredFields())
                .filter(f -> Objects.nonNull(f.getAnnotation(CsvBindByPosition.class)) && Objects.nonNull(f.getAnnotation(CsvBindByName.class)))
                .sorted(Comparator.comparing(f -> f.getAnnotation(CsvBindByPosition.class).position()))
                .map(f -> f.getAnnotation(CsvBindByName.class).column())
                .collect(Collectors.joining(";"))).append("\n");

        StatefulBeanToCsv<ReviewExcelDTO> beanToCsv = new StatefulBeanToCsvBuilder<ReviewExcelDTO>(writer)
                .withSeparator(';')
                .withQuotechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .build();

        var reviews = reviewRepository
                .findAll().stream().map(reviewMapper::asExcelDto);

        try {
            beanToCsv.write(reviews);
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            log.error("error");
//            throw new ValidateCassetteException("Export error");
        }
    }

    public ReviewDTO addFile(Long id, MultipartFile file) {

        /* Checking file extension */
        if (documentProperties.getAcceptFileExtensions().contains(FilenameUtils.getExtension(file.getOriginalFilename()))) {

            try(var fileInputStream = file.getInputStream()) {

                ReviewEntity review = reviewRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(REVIEW_IDENTIFIER_NOT_FOUND_MESSAGE, id)));


                /* Storing  file document */
                review.setPath(dataStorageService.storeFile(DOCUMENT_ROOT_DIRECTORY, DOCUMENT+review.getId(), FilenameUtils.getExtension(file.getOriginalFilename()), fileInputStream));

                ReviewDTO reviewUpdated = reviewMapper.asDto(reviewRepository.save(review));

                log.info("addFile end ok - reviewId: {}", review.getId());
                log.trace("addFile end ok - review: {}", reviewUpdated);

                return reviewUpdated;

            } catch (IOException e) {
                log.error(MessageFormat.format("An error occurred with file: {0}", file.getOriginalFilename()), e);
                throw new ResourceNotFoundException(MessageFormat.format(REVIEW_IDENTIFIER_NOT_FOUND_MESSAGE, id));
            }

        } else {
            throw new InvalidParameterException(MessageFormat.format(INVALID_EXTENSION_MESSAGE, file.getOriginalFilename(), documentProperties.getAcceptFileExtensions()));
        }
    }

}
