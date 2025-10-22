package com.webgram.dgpsn.services.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khoutech.openexcel.services.WorkbookService;
import com.webgram.dgpsn.annotations.Journal;
import com.webgram.dgpsn.entities.enums.TypeApplicant;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.QueryMapper;
import com.webgram.dgpsn.models.QueryDTO;
import com.webgram.dgpsn.properties.DocumentProperties;
import com.webgram.dgpsn.repositories.ManagementUnitRepository;
import com.webgram.dgpsn.repositories.QueryRepository;
import com.webgram.dgpsn.services.DataStorageService;
import com.webgram.dgpsn.services.QueryService;
import com.webgram.dgpsn.tools.ActionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class QueryServiceImpl implements QueryService {
    private final QueryRepository queryRepository;
    private final QueryMapper queryMapper;

    private String QUERY_IDENTIFIER_NOT_FOUND_MESSAGE = "Invalide id query: {}";

    static final String INVALID_EXTENSION_MESSAGE = "File: {0} does not match expected extension: {1}";

    static final String DOCUMENT_ROOT_DIRECTORY = "documents";

    static final String DOCUMENT = "query-";

    final DocumentProperties documentProperties;

    final DataStorageService dataStorageService;

    private final ObjectMapper objectMapper;

    private final WorkbookService workbookService;

    private final ManagementUnitRepository managementUnitRepository;

    @Override
    @Journal(actionType = ActionType.ADD_QUERY)
//    public QueryDTO create(MultipartFile file, String query) throws IOException {
//         var queryDTO = objectMapper.readValue(query, QueryDTO.class);
//         var savedQuery = queryRepository.save(queryMapper.asEntity(queryDTO));
//        if(Objects.nonNull(file)){
//            addFile(savedQuery.getId(), file);
//        }
//        log.info("Query successfully added {}", savedQuery);
//
//        return queryMapper.asDto(savedQuery);
//    }
    public QueryDTO create(QueryDTO queryDTO) {
        var savedQuery = queryRepository.save(queryMapper.asEntity(queryDTO));
        log.info("Query successfully added {}", savedQuery);
        return queryMapper.asDto(savedQuery);
    }

    @Override
    @Journal(actionType = ActionType.UPDATE_QUERY)
//    public QueryDTO update(MultipartFile file, QueryDTO queryDTO) throws IOException {
//        try{
//            if(queryRepository.existsById(queryDTO.getId())) {
//                var query = queryMapper.asEntity(queryDTO);
//                query.setPath(queryRepository.findById(queryDTO.getId()).get().getPath());
//                if(Objects.nonNull(file)){
//                    addFile(query.getId(), file);
//                }
//                var updatedQuery = queryMapper.asDto(queryRepository.save(query));
//
//                log.info("Query successfully updated {} ", query.getId());
//
//                return updatedQuery;
//            } else {
//                throw new ResourceNotFoundException("Query", queryDTO.getId());
//            }
//        } catch (IllegalArgumentException ex) {
//            throw new ResourceNotFoundException("Query", queryDTO.getId());
//        }
//    }
    public QueryDTO update(QueryDTO queryDTO) {
        try{
            if(queryRepository.existsById(queryDTO.getId())) {
                var query = queryMapper.asEntity(queryDTO);
                var updatedQuery= queryMapper.asDto(queryRepository.save(query));
                log.info("Query successfully updated {} ", updatedQuery.getId());
                return updatedQuery;
            } else {
                throw new ResourceNotFoundException("Query", queryDTO.getId());
            }
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("Query", queryDTO.getId());
        }
    }
    @Override
    public QueryDTO read(Long queryId) {
        var query = queryRepository
                .findById(queryId)
                .orElseThrow(()-> new ResourceNotFoundException("Query", queryId));

        log.info("reading query id {}", queryId);

        return queryMapper.asDto(query);
    }

    @Override
    @Journal(actionType = ActionType.DELETE_QUERY)
    public void delete(Long queryId) {
        try {
            queryRepository.deleteById(queryId);
            log.info("The query id {} is deleted", queryId);
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("Query", queryId);
        }
    }

    @Override
    @Journal(actionType = ActionType.READ_QUERY)
    public Page<QueryDTO> readAll(
            Pageable pageable,
            Date date,
            TypeApplicant typeDemandeur,
            TypeApplicant typeDestinataire,
            Long categorieRequeteId,
            Long typeRequeteId,
            Long demandeurActorId,
            Long demandeurStructureId,
            Long destinataireActorId,
            Long destinataireStructureId,
            Long projetId
    ) {
        return queryRepository
                .readAllByFilters(pageable,
                        date,
                        typeDemandeur,
                        typeDestinataire,
                        categorieRequeteId,
                        typeRequeteId,
                        demandeurActorId,
                        demandeurStructureId,
                        destinataireActorId,
                        destinataireStructureId,
                        projetId
                )
                .map(queryMapper::asDto);
    }

//    @Override
//    @Journal(actionType = ActionType.IMPORT_QUERY)
//    public void importQuery(MultipartFile file, Long projectId) {
//        try (Workbook workbook = workbookService.findWorkBook(file.getInputStream(), ExcelContentType.fromContentType(file.getContentType()))) {
//            Sheet sheet = workbook.getSheetAt(0);
//            ExcelBean<QueryExcelDTO> queryExcelDTOExcelBean = new ExcelBeanBuilder<>(sheet, QueryExcelDTO.class)
//                    .skipLines(0)
//                    .build();
//            List<QueryExcelDTO> queryExcelDTOS = queryExcelDTOExcelBean.parse();
//
//            var projet = managementUnitRepository.findById(projectId)
//                    .orElseThrow(() -> new ResourceNotFoundException("Project avec id {} introuvable"));
//
//            List<QueryEntity> queries = queryExcelDTOS.stream()
//                    .map(queryMapper::asEntity)
//                    .map(query -> query.setProjet(projet))
//                    .collect(Collectors.toList());
//
//            queryRepository.saveAll(queries);
//
//            log.info("importQuery end ok");
//            log.trace("importQuery end ok - projects: {}", queries);
//        } catch (IOException e) {
//            log.info("Exceptions handle import file =============== {0}", e);
//            throw new InvalidParameterException(MessageFormat.format("Exceptions handle import file ", "Banner", "idexists"));
//        }
//    }

//    @Override
//    @Journal(actionType = ActionType.EXPORT_QUERY_TO_EXCEL)
//    public void exportQuery(PrintWriter writer) {
//        /* Creating header */
//        writer.append(Arrays.stream(QueryExcelDTO.class.getDeclaredFields())
//                .filter(f -> Objects.nonNull(f.getAnnotation(CsvBindByPosition.class)) && Objects.nonNull(f.getAnnotation(CsvBindByName.class)))
//                .sorted(Comparator.comparing(f -> f.getAnnotation(CsvBindByPosition.class).position()))
//                .map(f -> f.getAnnotation(CsvBindByName.class).column())
//                .collect(Collectors.joining(";"))).append("\n");
//
//        StatefulBeanToCsv<QueryExcelDTO> beanToCsv = new StatefulBeanToCsvBuilder<QueryExcelDTO>(writer)
//                .withSeparator(';')
//                .withQuotechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
//                .build();
//
//        var queries = queryRepository
//                .findAll().stream().map(queryMapper::asExcelDto);
//
//        try {
//            beanToCsv.write(queries);
//        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
//            log.error("error");
////            throw new ValidateCassetteException("Export error");
//        }
//    }

//    @Override
//    public DownloadFile readFile(Long id) {
//
//        QueryDTO query = read(id);
//
//        /* Getting downloadFile */
//        DownloadFile downloadFile = DownloadFileUtils.generateDownloadFile(query.getPath());
//
//        log.info("readFile end ok - queryId: {}", id);
//        log.trace("readFile end ok - downloadFile: {}", downloadFile);
//
//        return downloadFile;
//    }
//
//    public QueryDTO addFile(Long id, MultipartFile file) {
//
//        /* Checking file extension */
//        if (documentProperties.getAcceptFileExtensions().contains(FilenameUtils.getExtension(file.getOriginalFilename()))) {
//
//            try(var fileInputStream = file.getInputStream()) {
//
//                QueryEntity query = queryRepository.findById(id)
//                        .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(QUERY_IDENTIFIER_NOT_FOUND_MESSAGE, id)));
//
//
//                /* Storing  file document */
//                query.setPath(dataStorageService.storeFile(DOCUMENT_ROOT_DIRECTORY, DOCUMENT+query.getId(), FilenameUtils.getExtension(file.getOriginalFilename()), fileInputStream));
//
//                QueryDTO queryUpdated = queryMapper.asDto(queryRepository.save(query));
//
//                log.info("addFile end ok - queryId: {}", queryUpdated.getId());
//                log.trace("addFile end ok - query: {}", queryUpdated);
//
//                return queryUpdated;
//
//            } catch (IOException e) {
//                log.error(MessageFormat.format("An error occurred with file: {0}", file.getOriginalFilename()), e);
//                throw new ResourceNotFoundException(MessageFormat.format(QUERY_IDENTIFIER_NOT_FOUND_MESSAGE, id));
//            }
//
//        } else {
//            throw new InvalidParameterException(MessageFormat.format(INVALID_EXTENSION_MESSAGE, file.getOriginalFilename(), documentProperties.getAcceptFileExtensions()));
//        }
//    }
}
