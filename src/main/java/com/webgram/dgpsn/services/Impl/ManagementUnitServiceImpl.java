package com.webgram.dgpsn.services.Impl;

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
import com.webgram.dgpsn.entities.*;
import com.webgram.dgpsn.entities.enums.StructureProjectType;
import com.webgram.dgpsn.entities.enums.TypeProjet;
import com.webgram.dgpsn.exceptions.PublishProjectOutOfBoundsException;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.ManagementUnitMapper;
import com.webgram.dgpsn.mappers.PartnerProjectMapper;
import com.webgram.dgpsn.mappers.StructureMapper;
import com.webgram.dgpsn.models.DownloadFile;
import com.webgram.dgpsn.models.ManagementUnitDTO;
import com.webgram.dgpsn.models.StructureDTO;
import com.webgram.dgpsn.models.TreeNodeDTO;
import com.webgram.dgpsn.models.responses.StatisticProjectDTO;
import com.webgram.dgpsn.properties.DocumentProperties;
import com.webgram.dgpsn.repositories.*;
import com.webgram.dgpsn.security.SecurityUtils;
import com.webgram.dgpsn.services.DataStorageService;
import com.webgram.dgpsn.services.ManagementUnitService;
import com.webgram.dgpsn.services.modelExcel.ManagementUnitExcelDTO;
import com.webgram.dgpsn.services.utils.DownloadFileUtils;
import com.webgram.dgpsn.tools.ActionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.XMLConstants;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidParameterException;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ManagementUnitServiceImpl implements ManagementUnitService {
    private final StructureProjectRepository structureProjectRepository;
    private final ManagementUnitRepository managementUnitRepository;
    private final ManagementUnitMapper managementUnitMapper;
    private final FundingRepository fundingRepository;
    private final PartnerProjectMapper partnerProjectMapper;
    private final ActorProjetRepository actorProjetRepository;
    private final UserRepository userRepository;
    private final BudgetActivityRepository budgetActivityRepository;
    private final ExpenseActivityRepository expenseActivityRepository;
    private final ValueIndicatorRepository valueIndicatorRepository;
    private final TacheRepository tacheRepository;
    private final FundingSourceRepository fundingSourceRepository;
    private final StructureMapper structureMapper;
    final WorkbookService workbookService;

    String PROJECT_DIRECTORY = "//project";

    final DocumentProperties projetProperties;
    final Environment env;

    final DataStorageService dataStorageService;

    static final String DOCUMENT_ROOT_DIRECTORY = "projets";
    static final String DOCUMENT = "projet-img-";
    static final String INVALID_EXTENSION_MESSAGE = "File: {0} does not match expected extension: {1}";

    @Override
    @Journal(actionType = ActionType.CREATE_PROJECT)
    public ManagementUnitDTO create(ManagementUnitDTO managementUnitDTO) {
        var projet = managementUnitMapper.asEntity(managementUnitDTO);
        projet.setPublish(false);
        projet.setActif(Boolean.TRUE);
        var savedProjet = managementUnitRepository.save(projet);

        log.info("Projet successfully added {}", savedProjet);

        return managementUnitMapper.asDto(savedProjet);
    }

    @Override
    @Journal(actionType = ActionType.UPDATE_PROJECT)
    public ManagementUnitDTO update(ManagementUnitDTO managementUnitDTO) {
        try {
            if (managementUnitRepository.existsById(managementUnitDTO.getId())) {
                var projet = managementUnitMapper.asEntity(managementUnitDTO);
                projet.setPublish(false);
                projet.setActif(Boolean.TRUE);

                var updatedProjet = managementUnitMapper.asDto(managementUnitRepository.save(projet));

                log.info("Projet successfully updated {} ", projet.getId());

                return updatedProjet;
            } else {
                throw new ResourceNotFoundException("Projet", managementUnitDTO.getId());
            }
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("Projet", managementUnitDTO.getId());
        }
    }

    @Override
    @Journal(actionType = ActionType.READ_PROJECT)
    public ManagementUnitDTO read(Long projetId) {
        var projet = managementUnitRepository
                .findById(projetId)
                .orElseThrow(() -> new ResourceNotFoundException("Projet", projetId));

        log.info("reading projet id {}", projetId);

        return managementUnitMapper.asDto(projet);
    }

    @Override
    @Journal(actionType = ActionType.DELETE_PROJECT)
    public void delete(Long projetId) {
        try {
            managementUnitRepository.deleteById(projetId);
            log.info("The projet id {} is deleted", projetId);
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("Projet", projetId);
        }
    }

    @Override
    @Journal(actionType = ActionType.READ_PROJECT)
    public Page<ManagementUnitDTO> readAll(
            Pageable pageable,
            String code,
            String name,
            TypeProjet type,
            String dateDebut,
            String dateFin,
            Double budget,
            Long poids,
            Long responsibleId,
            String tag,
            Long parentId,
            Long axePSEId,
            Boolean publish

    ) {
        List<Long> projectIds = new ArrayList<>();
        var user = SecurityUtils.getCurrentUserLogin()
                .flatMap(userRepository::findByLogin)
                .orElseThrow();

        if (("CDP".equals(user.getProfile().getCode()) || "CDD".equals(user.getProfile().getCode()) || "COP".equals(user.getProfile().getCode())) && Objects.nonNull(type) && TypeProjet.PROJECT.equals(type)) {
            projectIds = actorProjetRepository.getProjectIdByActor(user.getAgent().getId());
        }

        var pageManagementUnit = managementUnitRepository
                .readAllByFiltering(
                        pageable, code, name, type, dateDebut, dateFin,
                        budget, poids, responsibleId, tag, parentId, axePSEId, publish, projectIds)
                .map(managementUnitMapper::asDto);
        pageManagementUnit.getContent().forEach(management -> {
            var ministeres = structureProjectRepository.findByProjetIdAndStructureProjectType(management.getId(), StructureProjectType.GUARDIANSHIP);
            management.setMinisterTutelles(partnerProjectMapper.parse(ministeres));
        });

        return pageManagementUnit;
    }

    @Override
    @Journal(actionType = ActionType.IMPORT_PROJECT)
    public void importProject(MultipartFile file) {
        try (Workbook workbook = workbookService.findWorkBook(file.getInputStream(), ExcelContentType.fromContentType(file.getContentType()))) {
            Sheet sheet = workbook.getSheetAt(0);
            ExcelBean<ManagementUnitExcelDTO> projectDTOExcelBean = new ExcelBeanBuilder<>(sheet, ManagementUnitExcelDTO.class)
                    .skipLines(0)
                    .build();
            List<ManagementUnitExcelDTO> projectDTOS = projectDTOExcelBean.parse();
            List<ManagementUnitEntity> projects = projectDTOS.stream()
                    .map(managementUnitMapper::asEntity)
                    .collect(Collectors.toList());
            managementUnitRepository.saveAll(projects);
            log.info("importProject end ok");
            log.trace("importProject end ok - projects: {}", projects);
        } catch (IOException e) {
            log.info("Exceptions handle import file =============== {0}", e);
            throw new InvalidParameterException(MessageFormat.format("Exceptions handle import file ", "Banner", "idexists"));
        }
    }

    @Override
    @Journal(actionType = ActionType.EXPORT_PROJECT_TO_EXCEL)
    public void export(PrintWriter writer) {

        /* Creating header */
        writer.append(Arrays.stream(ManagementUnitExcelDTO.class.getDeclaredFields())
                .filter(f -> Objects.nonNull(f.getAnnotation(CsvBindByPosition.class)) && Objects.nonNull(f.getAnnotation(CsvBindByName.class)))
                .sorted(Comparator.comparing(f -> f.getAnnotation(CsvBindByPosition.class).position()))
                .map(f -> f.getAnnotation(CsvBindByName.class).column())
                .collect(Collectors.joining(";"))).append("\n");

        StatefulBeanToCsv<ManagementUnitExcelDTO> beanToCsv = new StatefulBeanToCsvBuilder<ManagementUnitExcelDTO>(writer)
                .withSeparator(';')
                .withQuotechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .build();

        List<ManagementUnitExcelDTO> managementUnitExcelDTOS = managementUnitRepository.findByType(TypeProjet.PROGRAMME).stream()
                .map(managementUnitMapper::asExcelDto)
                .collect(Collectors.toList());

        try {
            beanToCsv.write(managementUnitExcelDTOS);
            log.info("export ok");
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            log.error("error");
            // throw new ValidateCassetteException("Export error");
        }

    }

    @Override
    @Journal(actionType = ActionType.EXPORT_PROJECT_TO_EXCEL)
    public void exportProjets(PrintWriter writer) {

        /* Creating header */
        writer.append(Arrays.stream(ManagementUnitExcelDTO.class.getDeclaredFields())
                .filter(f -> Objects.nonNull(f.getAnnotation(CsvBindByPosition.class)) && Objects.nonNull(f.getAnnotation(CsvBindByName.class)))
                .sorted(Comparator.comparing(f -> f.getAnnotation(CsvBindByPosition.class).position()))
                .map(f -> f.getAnnotation(CsvBindByName.class).column())
                .collect(Collectors.joining(";"))).append("\n");

        StatefulBeanToCsv<ManagementUnitExcelDTO> beanToCsv = new StatefulBeanToCsvBuilder<ManagementUnitExcelDTO>(writer)
                .withSeparator(';')
                .withQuotechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .build();

        List<ManagementUnitExcelDTO> managementUnitExcelDTOS = managementUnitRepository.findByType(TypeProjet.PROJECT).stream()
                .map(managementUnitMapper::asExcelDto)
                .collect(Collectors.toList());

        try {
            beanToCsv.write(managementUnitExcelDTOS);
            log.info("export ok");
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            log.error("error");
            // throw new ValidateCassetteException("Export error");
        }

    }

    @Override
    @Journal(actionType = ActionType.EXPORT_PROJECT_TO_PDF)
    public DownloadFile generateFilePdf() {

        Properties p = new Properties();

        p.setProperty("resource.loader", "file");
        p.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        p.setProperty("file.resource.loader.path", projetProperties.getFileStorageRootPath() + PROJECT_DIRECTORY);
        p.setProperty("file.resource.loader.cache", "false");
        p.setProperty("file.resource.loader.modificationCheckInterval", "0");

//        String templateName = env.getProperty("storage-projet.template-name");
        String templateName = "templates/liste_programmes_template.vm";

        final VelocityEngine engine = new VelocityEngine();
        engine.init(p);
        Template template = engine.getTemplate(templateName, "UTF-8");

        log.info("\t > template selected : {}", templateName);

        VelocityContext context = new VelocityContext();

        var projectDTOS = managementUnitRepository.findAll().stream()
                .map(managementUnitMapper::asExcelDto).collect(Collectors.toList());

        int numberOfPrograms = projectDTOS.size();
        context.put("nbrPrograms", numberOfPrograms);
        context.put("title", "Liste des programmes");
        context.put("programmes", projectDTOS);

        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
        String fileName = "PDF_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + ".pdf";
        String filePath = projetProperties.getFileStorageRootPath() + "/" + fileName;

        log.info("filename ----------------- {}", projetProperties.getFileStorageRootPath() + "/" + fileName);
        try (OutputStream out = new FileOutputStream(projetProperties.getFileStorageRootPath() + "/" + fileName)) {

            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, out);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            Transformer transformer = transformerFactory.newTransformer();

            Result res = new SAXResult(fop.getDefaultHandler());

            byte[] bytes = writer.toString().getBytes(StandardCharsets.UTF_8);
            Source src = new StreamSource(new ByteArrayInputStream(bytes));

            transformer.transform(src, res);


        } catch (Exception e) {
            log.info("error outputFileStrem ", e);
        }
        return DownloadFileUtils.generateDownloadFile(filePath);

    }

    @Override
    public DownloadFile redFile(Long id) {

        var project = managementUnitRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Projet introuvable %d", id)));

        /* Getting downloadFile */
        DownloadFile downloadFile = DownloadFileUtils.generateDownloadFile(project.getPath());

        log.info("readFile end ok - projetId: {}", id);
        log.trace("readFile end ok - downloadFile: {}", downloadFile);

        return downloadFile;
    }

    @Override
    public void uploadImage(Long id, MultipartFile file) {
        if (Objects.nonNull(file)) {
            addFile(id, file);
        }
    }

    @Override
    public void publishOrUnpublish(Long projetId) {
        var projet = managementUnitRepository.findById(projetId);

        if (projet.isPresent()) {
            var check = projet.get().isPublish() ? false : true;

            if (!check) {
                projet.get().setPublish(false);
            } else if (check && managementUnitRepository.countAllByPublish(true) < 3) {
                projet.get().setPublish(true);
            } else {
                throw new PublishProjectOutOfBoundsException(String.format("Le nombre de projets (3) à publier est déjà atteint. Veuillez annuler une publication ancienne pour publier le nouveau."));
            }
            managementUnitRepository.save(projet.get());
        } else {
            throw new ResourceNotFoundException(String.format("Un projet avec l'id %d n'existe pas", projetId));
        }
    }

    @Override
    public List<ManagementUnitDTO> readPublishedProjects() {
        return managementUnitRepository.findAllByPublish(true)
                .stream().map(managementUnitMapper::asDto).collect(Collectors.toList());
    }

    @Override
    public StatisticProjectDTO readStatisticProject() {
        var statistic = StatisticProjectDTO.builder()
                .totalProject(managementUnitRepository.countTotalProjects().get())
                .averageAge(null)
                .totalFunding(fundingRepository.readTotalFunding().get())
                .averageFundingPerProject(0.0)
                .tauxDecaissement(null)
                .build();

        return statistic;
//        fundingRepository
//                .averageFundingByProject()
//                .stream()
//                .mapToDouble(project -> Objects.isNull(project.getAverage()) ? 0.0 : project.getAverage())
//                .sum()
    }


    private void addFile(Long id, MultipartFile file) {
        /* Checking file extension */
        if (projetProperties.getAcceptFileExtensions().contains(FilenameUtils.getExtension(file.getOriginalFilename()))) {

            try (var fileInputStream = file.getInputStream()) {

                var projet = managementUnitRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format("Projet avec id {} introuvable", id)));

                /* Storing  file projet */
                projet.setPath(dataStorageService.storeFile(DOCUMENT_ROOT_DIRECTORY, DOCUMENT + projet.getId(), FilenameUtils.getExtension(file.getOriginalFilename()), fileInputStream));

                var projetUpdated = managementUnitMapper.asDto(managementUnitRepository.save(projet));

                log.info("addFile end ok - projetId: {}", projetUpdated.getId());
                log.trace("addFile end ok - projet: {}", projetUpdated);

            } catch (IOException e) {
                log.error(MessageFormat.format("An error occurred with file: {0}", file.getOriginalFilename()), e);
                throw new ResourceNotFoundException(MessageFormat.format("Projet avec id {} introuvable", id));
            }

        } else {
            throw new InvalidParameterException(MessageFormat.format(INVALID_EXTENSION_MESSAGE, file.getOriginalFilename(), projetProperties.getAcceptFileExtensions()));
        }
    }

//    public TreeNodeDTO readTreeManagmentUnit(Long projetId) {
//
//        ManagementUnitEntity project = managementUnitRepository.findById(projetId)
//                .filter(p -> p.getType() == TypeProjet.PROJECT)
//                .orElseThrow(() -> new NoSuchElementException("Projet non trouvé ou type incorrect."));
//
//        TreeNodeDTO rootNode = new TreeNodeDTO(project.getId(), project.getCode(), project.getNomenclature(), project.getName(), project.getType());
//
//        // Charger toutes les entités
//        List<ManagementUnitEntity> allProjects = managementUnitRepository.findAll();
//        Map<Long, TreeNodeDTO> nodeMap = new HashMap<>();
//        allProjects.forEach(p -> nodeMap.put(p.getId(), new TreeNodeDTO(p.getId(), p.getCode(), p.getNomenclature(), p.getName(), p.getType())));
//
//        // Construire l'arbre récursivement
//        buildSubTreeRecursively(rootNode, allProjects, nodeMap);
//
//        // Trier l'arbre
//        sortRecursively(rootNode);
//        return rootNode;
//    }

    @Override
    @CacheEvict(value = "managementUnitTree", allEntries = true)
    public TreeNodeDTO addNodeToTreeManagmentUnit(Long parentId, TreeNodeDTO nodeDTO) {
        var parent = managementUnitRepository.findById(parentId)
                .orElseThrow(() -> new ResourceNotFoundException("Parent non trouvé"));

        var managementUnit = ManagementUnitEntity.builder()
                .code(nodeDTO.getCode())
                .nomenclature(nodeDTO.getNomenclature())
                .name(nodeDTO.getName())
                .type(nodeDTO.getType())
                .actif(true)
                .parent(parent)
                .budgetDgpsn(parent.getBudgetDgpsn())
                .build();

        var saved = managementUnitRepository.save(managementUnit);

        // Retourne le nœud avec l'ID généré en utilisant le Builder
        return TreeNodeDTO.builder()
                .id(saved.getId())
                .code(saved.getCode())
                .nomenclature(saved.getNomenclature())
                .name(saved.getName())
                .type(saved.getType())
                .build();
    }

    @Override
    public List<TreeNodeDTO> readAllProjectsWithTree() {
        // Charger toutes les unités de gestion
        List<ManagementUnitEntity> allUnits = managementUnitRepository.findAllByActifIsTrue();
        // Charger tous les budgets
        List<BudgetActivityEntity> allBudgets = budgetActivityRepository.findAll();
        // Charger toutes les dépenses
        List<ExpenseActivityEntity> allExpenses = expenseActivityRepository.findAll();

        // Créer un Map pour accéder rapidement aux noeuds
        Map<Long, TreeNodeDTO> nodeMap = new HashMap<>();
        List<TreeNodeDTO> rootNodes = new ArrayList<>();

        // Créer tous les nœuds et les stocker dans le Map
        allUnits.forEach(unit -> {
            // CORRECTION ICI : Utiliser le Builder
            TreeNodeDTO node = TreeNodeDTO.builder()
                    .id(unit.getId())
                    .code(unit.getCode())
                    .nomenclature(unit.getNomenclature())
                    .name(unit.getName())
                    .type(unit.getType())
                    .build();

            nodeMap.put(unit.getId(), node);

            // Identifier les racines (Projets)
            if (Objects.nonNull(unit.getType()) && unit.getType().equals(TypeProjet.PROJECT)) {
                rootNodes.add(node);
            }
        });

        // Construire l'arbre
        allUnits.forEach(unit -> {
            if (Objects.nonNull(unit.getType()) && !unit.getType().equals(TypeProjet.PROGRAMME) && !unit.getType().equals(TypeProjet.PROJECT)) {
                TreeNodeDTO parentNode = nodeMap.get(unit.getParent().getId());
                if (parentNode != null) {
                    parentNode.addChild(nodeMap.get(unit.getId()));
                }
            }
        });

        // **Étape 1:** Associer les budgets aux activités
        allBudgets.forEach(budget -> {
            if (budget.getProjet() != null) {
                TreeNodeDTO activityNode = nodeMap.get(budget.getProjet().getId());
                if (activityNode != null) {
                    // On additionne les budgets existants
                    Double currentBudget = activityNode.getBudget() != null ? activityNode.getBudget() : 0.0;
                    activityNode.setBudget(currentBudget + budget.getAmount());
                }
            }
        });

        // **Étape 2:** Associer les dépenses aux activités
        allExpenses.forEach(expense -> {
            if (expense.getProjet() != null) {
                TreeNodeDTO activityNode = nodeMap.get(expense.getProjet().getId());
                if (activityNode != null) {
                    // On additionne les dépenses existantes
                    Double currentDepense = activityNode.getDepense() != null ? activityNode.getDepense() : 0.0;
                    Double depense = expense.getUnitAmount().isEmpty() ? currentDepense : currentDepense + Double.parseDouble(expense.getUnitAmount());
                    activityNode.setDepense(depense);
                }
            }
        });

        // **Étape 3:** Remonter les budgets et les dépenses dans l'arbre
        rootNodes.forEach(this::aggregateBudgets);

        // Trier l'arborescence pour un rendu propre
        rootNodes.forEach(this::sortRecursively);

        return rootNodes;
    }

    private void buildSubTreeRecursively(TreeNodeDTO parentNode, List<ManagementUnitEntity> allProjects, Map<Long, TreeNodeDTO> nodeMap) {
        Long parentId = parentNode.getId();

        // Trouver les enfants directs
        allProjects.stream()
                .filter(p -> p.getParent() != null && p.getParent().getId().equals(parentId))
                .forEach(child -> {
                    TreeNodeDTO childNode = nodeMap.get(child.getId());
                    parentNode.getChildren().add(childNode);
                    // Récursion pour ajouter les sous-niveaux
                    buildSubTreeRecursively(childNode, allProjects, nodeMap);
                });
    }

//    private void sortRecursively(TreeNodeDTO node) {
//        node.getChildren().removeIf(child -> child.getType() == null); // Supprimer les enfants sans type
//        node.getChildren().sort(Comparator.comparing(child -> child.getType().ordinal()));
//        node.getChildren().forEach(this::sortRecursively);
//    }

    private Double aggregateBudgets(TreeNodeDTO node) {
        if (node.getChildren().isEmpty()) {
            // Si le nœud est une feuille (activité), calculer le taux d'exécution
            double budget = node.getBudget() != null ? node.getBudget() : 0.0;
            double depense = node.getDepense() != null ? node.getDepense() : 0.0;
            double tauxExecution = (budget > 0) ? (depense / budget) * 100 : 0.0;
            node.setTauxExecution(tauxExecution);
            return budget;
        }

        // Sinon, on additionne les budgets, dépenses et taux d'exécution de ses enfants
        double totalBudget = 0.0;
        double totalDepense = 0.0;

        for (TreeNodeDTO child : node.getChildren()) {
            totalBudget += aggregateBudgets(child);
            totalDepense += child.getDepense() != null ? child.getDepense() : 0.0;
        }

        // Mise à jour du budget, de la dépense cumulée et du taux d'exécution dans le parent
        node.setBudget(totalBudget);
        node.setDepense(totalDepense);
        double tauxExecution = (totalBudget > 0) ? (totalDepense / totalBudget) * 100 : 0.0;
        node.setTauxExecution(tauxExecution);

        return totalBudget;
    }

    ////////Laty
    @Override
    public TreeNodeDTO readTreeManagmentUnit(Long projetId) {
        return null;
//        ManagementUnitEntity project = managementUnitRepository.findById(projetId)
//                .filter(p -> p.getType() == TypeProjet.PROJECT)
//                .orElseThrow(() -> new NoSuchElementException("Projet non trouvé ou type incorrect."));
//
//        TreeNodeDTO rootNode = new TreeNodeDTO(
//                project.getId(),
//                project.getCode(),
//                project.getNomenclature(),
//                project.getName(),
//                project.getType()
//        );
//
//        // Charger toutes les entités
//        List<ManagementUnitEntity> allProjects = managementUnitRepository.findAll();
//        List<ValueIndicatorEntity> allIndicators = valueIndicatorRepository.findAll();
//        List<TacheEntity> allTaches = tacheRepository.findAll();
//
//        Map<Long, TreeNodeDTO> nodeMap = new HashMap<>();
//
//        // Créer les nœuds pour les ManagementUnit
//        allProjects.forEach(p ->
//                nodeMap.put(p.getId(), new TreeNodeDTO(
//                        p.getId(),
//                        p.getCode(),
//                        p.getNomenclature(),
//                        p.getName(),
//                        p.getType()
//                ))
//        );
//
//        // Construire l'arbre avec indicateurs et tâches
//        buildCompleteTree(rootNode, allProjects, allIndicators, allTaches, nodeMap);
//
//        // Trier l'arbre
//        sortRecursively(rootNode);
//
//        return rootNode;
    }

    private void buildCompleteTree(
            TreeNodeDTO parentNode,
            List<ManagementUnitEntity> allProjects,
            List<ValueIndicatorEntity> allIndicators,
            List<TacheEntity> allTaches,
            Map<Long, TreeNodeDTO> nodeMap) {

        Long parentId = parentNode.getId();

        // 1. Ajouter les enfants ManagementUnit (OBJECTIF, ACTION, ACTIVITY)
        allProjects.stream()
                .filter(p -> p.getParent() != null && p.getParent().getId().equals(parentId))
                .forEach(child -> {
                    TreeNodeDTO childNode = nodeMap.get(child.getId());
                    parentNode.getChildren().add(childNode);

                    // Récursion pour les sous-niveaux
                    buildCompleteTree(childNode, allProjects, allIndicators, allTaches, nodeMap);
                });

        // 2. Si c'est une ACTIVITY, ajouter les indicateurs
        if (parentNode.getType() == TypeProjet.ACTIVITY) {
            allIndicators.stream()
                    .filter(indicator -> indicator.getActivity() != null &&
                            indicator.getActivity().getId().equals(parentId))
                    .forEach(indicator -> {
                        TreeNodeDTO indicatorNode = createIndicatorNode(indicator);
                        parentNode.getChildren().add(indicatorNode);

                        // 3. Pour chaque indicateur, ajouter les tâches associées
                        addTachesToIndicator(indicatorNode, allTaches, parentId);
                    });
        }
    }

    private TreeNodeDTO createIndicatorNode(ValueIndicatorEntity indicator) {
        TreeNodeDTO node = new TreeNodeDTO();
        node.setId(indicator.getId());
        node.setCode(indicator.getIndicatorProjet() != null &&
                indicator.getIndicatorProjet().getIndicator() != null ?
                indicator.getIndicatorProjet().getIndicator().getCode() : "IND-" + indicator.getId());
        node.setName(indicator.getIndicatorProjet() != null &&
                indicator.getIndicatorProjet().getIndicator() != null ?
                indicator.getIndicatorProjet().getIndicator().getLibelle() : "Indicateur");
        node.setNomenclature(node.getCode());
        node.setType(TypeProjet.INDICATOR);
        node.setChildren(new ArrayList<>());

        return node;
    }

    private void addTachesToIndicator(TreeNodeDTO indicatorNode, List<TacheEntity> allTaches, Long activityId) {
        allTaches.stream()
                .filter(tache -> tache.getActivite() != null &&
                        tache.getActivite().getId().equals(activityId))
                .forEach(tache -> {
                    TreeNodeDTO tacheNode = createTacheNode(tache);
                    indicatorNode.getChildren().add(tacheNode);
                });
    }

    private void sortRecursively(TreeNodeDTO node) {
        if (node.getChildren() == null || node.getChildren().isEmpty()) {
            return;
        }

        node.getChildren().removeIf(child -> child.getType() == null);
        node.getChildren().sort(Comparator.comparing(child -> child.getType().ordinal()));
        node.getChildren().forEach(this::sortRecursively);
    }

    ///////////////////////NEW VESION TREE RECUP BY BUDGET
    @Override
    public TreeNodeDTO readTreeManagementUnitByBudgetId(Long budgetId) {
        if (budgetId == null) {
            throw new IllegalArgumentException("budgetId cannot be null");
        }

        // 1. Récupérer tous les ManagementUnit liés à ce budget
        List<ManagementUnitEntity> units = managementUnitRepository.findByBudgetDgpsnId(budgetId);

        if (units.isEmpty()) {
            return TreeNodeDTO.builder()
                    .id(0L)
                    .name("Aucun élément financé par ce budget")
                    .type(TypeProjet.PROGRAMME)
                    .children(new ArrayList<>())
                    .build();
        }

        // 2. Map id → entity pour accès rapide
        Map<Long, ManagementUnitEntity> unitMap = units.stream()
                .collect(Collectors.toMap(ManagementUnitEntity::getId, Function.identity()));

        // 3. Map id → TreeNodeDTO
        Map<Long, TreeNodeDTO> nodeMap = new HashMap<>();

        // 4. Créer les nœuds (OBJECTIF, ACTION, ACTIVITY, INDICATOR)
        for (ManagementUnitEntity entity : units) {
            TypeProjet type = entity.getType();
            if (!List.of(TypeProjet.OBJECTIF, TypeProjet.ACTION, TypeProjet.ACTIVITY, TypeProjet.INDICATOR).contains(type)) {
                continue;
            }

            TreeNodeDTO node = managementUnitMapper.asTreeDto(entity);
            node.setChildren(new ArrayList<>());
            nodeMap.put(entity.getId(), node);
        }

        // 5. Ajouter les TÂCHES comme enfants des INDICATEURS
        for (ManagementUnitEntity indicatorUnit : units) {
            if (indicatorUnit.getType() != TypeProjet.INDICATOR) continue;

            Long indicatorId = indicatorUnit.getId();
            List<TacheEntity> taches = tacheRepository.findByIndicatorId(indicatorId);

            TreeNodeDTO indicatorNode = nodeMap.get(indicatorId);
            if (indicatorNode == null) continue;

            List<TreeNodeDTO> tacheNodes = taches.stream()
                    .map(tache -> TreeNodeDTO.builder()
                            .id(tache.getId())
                            .code("T" + tache.getTrimestre() + (tache.getMois() != null ? "-" + tache.getMois() : ""))
                            .nomenclature("Tâche")
                            .name(tache.getCommentaire() != null && !tache.getCommentaire().isBlank()
                                    ? tache.getCommentaire()
                                    : "Tâche sans libellé")
                            .type(TypeProjet.TACHE)
                            .trimestre(tache.getTrimestre())
                            .mois(tache.getMois())
                            .semaines(tache.getSemaines() != null ? String.join(",", tache.getSemaines()) : null)
                            .commentaire(tache.getCommentaire())
                            .statut(tache.getStatut() != null ? tache.getStatut().name() : "PLANIFIE")
                            .startDate(tache.getDateDebut())
                            .endDate(tache.getDateFin())
                            .children(new ArrayList<>())
                            .build())
                    .toList();

            indicatorNode.getChildren().addAll(tacheNodes);
        }

        // 6. Construire l’arborescence
        TreeNodeDTO root = TreeNodeDTO.builder()
                .id(-1L)
                .name("Budget PTBA")
                .type(TypeProjet.PROGRAMME)
                .children(new ArrayList<>())
                .build();

        for (ManagementUnitEntity entity : units) {
            TreeNodeDTO node = nodeMap.get(entity.getId());
            if (node == null) continue;

            ManagementUnitEntity parent = entity.getParent();

            if (parent == null || !unitMap.containsKey(parent.getId())) {
                // Pas de parent ou parent hors budget → accrocher au root
                root.getChildren().add(node);
            } else {
                // Parent dans le même budget → lien normal
                TreeNodeDTO parentNode = nodeMap.get(parent.getId());
                if (parentNode != null) {
                    parentNode.getChildren().add(node);
                }
            }
        }

        // 7. Trier récursivement par code
        sortTreeByCode(root);

        return root;
    }

    // Méthode utilitaire pour trier les enfants
    private void sortTreeByCode(TreeNodeDTO node) {
        if (node.getChildren() != null && !node.getChildren().isEmpty()) {
            node.getChildren().sort(Comparator.comparing(
                    n -> n.getCode() != null ? n.getCode() : n.getName(),
                    String.CASE_INSENSITIVE_ORDER
            ));
            node.getChildren().forEach(this::sortTreeByCode);
        }
    }

    // Méthode utilitaire pour trier récursivement
    private void sortChildren(TreeNodeDTO node) {
        if (node.getChildren() != null) {
            node.getChildren().sort(Comparator.comparing(n ->
                    n.getCode() != null ? n.getCode() : n.getName(), String.CASE_INSENSITIVE_ORDER));
            node.getChildren().forEach(this::sortChildren);
        }
    }


    /**
     * Ajoute récursivement tous les parents d'une unité de gestion jusqu'au projet racine
     */
    private void addParentHierarchy(Long managementUnitId, Set<Long> allIds) {
        ManagementUnitEntity unit = managementUnitRepository.findById(managementUnitId).orElse(null);
        if (unit != null && unit.getParent() != null) {
            Long parentId = unit.getParent().getId();
            if (!allIds.contains(parentId)) {
                allIds.add(parentId);
                addParentHierarchy(parentId, allIds);
            }
        }
    }

    /**
     * Trouve le projet racine (PROGRAMME ou PROJECT sans parent)
     */
    private ManagementUnitEntity findRootProject(List<ManagementUnitEntity> units) {
        // Chercher d'abord un PROGRAMME ou PROJECT sans parent
        for (ManagementUnitEntity unit : units) {
            if ((unit.getType() == TypeProjet.PROGRAMME || unit.getType() == TypeProjet.PROJECT)
                    && unit.getParent() == null) {
                return unit;
            }
        }

        // Si pas trouvé, chercher n'importe quel élément sans parent
        for (ManagementUnitEntity unit : units) {
            if (unit.getParent() == null) {
                return unit;
            }
        }

        // En dernier recours, prendre le premier
        return units.isEmpty() ? null : units.get(0);
    }

    /**
     * Construit l'arbre en filtrant uniquement les éléments financés ou leurs parents
     */
    private TreeNodeDTO buildFilteredTree(
            ManagementUnitEntity root,
            Set<Long> includedManagementUnitIds,
            Set<Long> financedActivityIds,
            Set<Long> financedTacheIds,
            Set<Long> financedIndicatorIds,
            Long budgetId) {

        // Étape 1: Mapper les listes d'entités en listes de DTOs
        List<StructureDTO> responsibleStructures = new ArrayList<>();
        if (root.getStructuresResponsables() != null) {
            responsibleStructures = root.getStructuresResponsables().stream()
                    .map(structureMapper::asDto)
                    .collect(Collectors.toList());
        }

        List<StructureDTO> involvedActors = new ArrayList<>();
        if (root.getActorsInvolved() != null) {
            involvedActors = root.getActorsInvolved().stream()
                    .map(structureMapper::asDto)
                    .collect(Collectors.toList());
        }

        // Étape 2: Créer le TreeNodeDTO et assigner les propriétés
        TreeNodeDTO rootNode = new TreeNodeDTO();
        rootNode.setId(root.getId());
        rootNode.setCode(root.getCode());
        rootNode.setNomenclature(root.getNomenclature());
        rootNode.setName(root.getName());
        rootNode.setType(root.getType());
        rootNode.setStructuresResponsables(responsibleStructures);
        rootNode.setActorsInvolved(involvedActors);
        rootNode.setChildren(new ArrayList<>()); // Initialiser la liste des enfants

        // Calculer le budget agrégé pour ce nœud
        calculateAggregatedBudget(rootNode, root.getId(), financedActivityIds, financedTacheIds, financedIndicatorIds, budgetId);

        // Construire récursivement les enfants ManagementUnit
        List<ManagementUnitEntity> children = managementUnitRepository.findByParentId(root.getId());

        for (ManagementUnitEntity child : children) {
            // Inclure si l'enfant est requis dans la hiérarchie
            if (includedManagementUnitIds.contains(child.getId())) {
                TreeNodeDTO childNode = buildFilteredTree(
                        child,
                        includedManagementUnitIds,
                        financedActivityIds,
                        financedTacheIds,
                        financedIndicatorIds,
                        budgetId
                );
                rootNode.addChild(childNode);
            }
        }

        // Si c'est une ACTIVITY, ajouter ses tâches financées directement
        if (root.getType() == TypeProjet.ACTIVITY && financedActivityIds.contains(root.getId())) {
            addFinancedTachesForActivity(rootNode, root.getId(), financedTacheIds);
        }

        // Si c'est un INDICATOR (ManagementUnit), ajouter ses tâches financées
        if (root.getType() == TypeProjet.INDICATOR && financedIndicatorIds.contains(root.getId())) {
            addFinancedTachesForIndicator(rootNode, root.getId(), financedTacheIds);
        }

        return rootNode;
    }

    /**
     * Ajoute les tâches financées d'une activité
     */
    private void addFinancedTachesForActivity(
            TreeNodeDTO activityNode,
            Long activityId,
            Set<Long> financedTacheIds) {

        List<TacheEntity> allTaches = tacheRepository.findByActiviteId(activityId);

        for (TacheEntity tache : allTaches) {
            if (financedTacheIds.contains(tache.getId())) {
                TreeNodeDTO tacheNode = createTacheNode(tache);
                activityNode.addChild(tacheNode);
            }
        }
    }

    /**
     * Ajoute les tâches financées d'un indicateur (ManagementUnit type=INDICATOR)
     */
    private void addFinancedTachesForIndicator(
            TreeNodeDTO indicatorNode,
            Long indicatorId,
            Set<Long> financedTacheIds) {

        List<TacheEntity> allTaches = tacheRepository.findByIndicatorId(indicatorId);

        for (TacheEntity tache : allTaches) {
            if (financedTacheIds.contains(tache.getId())) {
                TreeNodeDTO tacheNode = createTacheNode(tache);
                indicatorNode.addChild(tacheNode);
            }
        }
    }

    /**
     * Crée un nœud pour une tâche
     */
    private TreeNodeDTO createTacheNode(TacheEntity tache) {
        TreeNodeDTO node = new TreeNodeDTO();
        node.setId(tache.getId());
        node.setCode("TACHE-" + tache.getId());
        node.setNomenclature("TACHE-" + tache.getId());
        node.setName("Tâche T" + tache.getTrimestre() + "-M" + tache.getMois());
        node.setType(TypeProjet.TACHE);
        node.setTrimestre(tache.getTrimestre());
        node.setMois(tache.getMois());
        node.setSemaines(tache.getSemaines());
        node.setCommentaire(tache.getCommentaire());
        node.setStatut(tache.getStatut() != null ? tache.getStatut().name() : null);
        node.setStartDate(tache.getDateDebut());
        node.setEndDate(tache.getDateFin());

        // Calculer budget pour cette tâche
        List<FundingSourceEntity> sources = fundingSourceRepository.findByTacheId(tache.getId());
        double totalBudget = sources.stream()
                .mapToDouble(s -> s.getMontant() != null ? s.getMontant() : 0.0)
                .sum();

        node.setBudget(totalBudget);
        node.setDepense(0.0);
        node.setTauxExecution(0.0);

        return node;
    }

    /**
     * Calcule le budget agrégé pour un nœud (somme des budgets de tous ses descendants financés)
     */
    private void calculateAggregatedBudget(
            TreeNodeDTO node,
            Long managementUnitId,
            Set<Long> financedActivityIds,
            Set<Long> financedTacheIds,
            Set<Long> financedIndicatorIds,
            Long budgetId) {

        double totalBudget = 0.0;

        ManagementUnitEntity unit = managementUnitRepository.findById(managementUnitId).orElse(null);
        if (unit == null) {
            node.setBudget(0.0);
            node.setDepense(0.0);
            node.setTauxExecution(0.0);
            return;
        }

        // Si c'est une activité financée, récupérer les sources de financement
        if (unit.getType() == TypeProjet.ACTIVITY && financedActivityIds.contains(managementUnitId)) {
            List<FundingSourceEntity> activitySources = fundingSourceRepository
                    .findByManagementUnitIdPerso(managementUnitId);
            totalBudget += activitySources.stream()
                    .mapToDouble(s -> s.getMontant() != null ? s.getMontant() : 0.0)
                    .sum();

            // Ajouter les budgets des tâches de cette activité
            List<TacheEntity> taches = tacheRepository.findByActiviteId(managementUnitId);
            for (TacheEntity tache : taches) {
                if (financedTacheIds.contains(tache.getId())) {
                    List<FundingSourceEntity> tacheSources = fundingSourceRepository.findByTacheId(tache.getId());
                    totalBudget += tacheSources.stream()
                            .mapToDouble(s -> s.getMontant() != null ? s.getMontant() : 0.0)
                            .sum();
                }
            }
        }

        // Si c'est un indicateur financé (ManagementUnit type=INDICATOR)
        if (unit.getType() == TypeProjet.INDICATOR && financedIndicatorIds.contains(managementUnitId)) {
            List<FundingSourceEntity> indicatorSources = fundingSourceRepository
                    .findByManagementUnitIdPerso(managementUnitId);
            totalBudget += indicatorSources.stream()
                    .mapToDouble(s -> s.getMontant() != null ? s.getMontant() : 0.0)
                    .sum();

            // Ajouter les budgets des tâches de cet indicateur
            List<TacheEntity> taches = tacheRepository.findByIndicatorId(managementUnitId);
            for (TacheEntity tache : taches) {
                if (financedTacheIds.contains(tache.getId())) {
                    List<FundingSourceEntity> tacheSources = fundingSourceRepository.findByTacheId(tache.getId());
                    totalBudget += tacheSources.stream()
                            .mapToDouble(s -> s.getMontant() != null ? s.getMontant() : 0.0)
                            .sum();
                }
            }
        }

        // Pour les niveaux supérieurs (ACTION, OBJECTIF, PROJECT), agréger les budgets des enfants
        if (unit.getType() != TypeProjet.ACTIVITY && unit.getType() != TypeProjet.INDICATOR) {
            List<ManagementUnitEntity> children = managementUnitRepository.findByParentId(managementUnitId);
            for (ManagementUnitEntity child : children) {
                totalBudget += calculateDescendantsBudget(
                        child.getId(),
                        financedActivityIds,
                        financedTacheIds,
                        financedIndicatorIds,
                        budgetId
                );
            }
        }

        node.setBudget(totalBudget);
        node.setDepense(0.0); // À implémenter selon votre logique métier
        node.setTauxExecution(totalBudget > 0 ? (node.getDepense() / totalBudget) * 100 : 0.0);
    }

    /**
     * Calcule récursivement le budget total des descendants
     */
    private double calculateDescendantsBudget(
            Long managementUnitId,
            Set<Long> financedActivityIds,
            Set<Long> financedTacheIds,
            Set<Long> financedIndicatorIds,
            Long budgetId) {

        double total = 0.0;

        ManagementUnitEntity unit = managementUnitRepository.findById(managementUnitId).orElse(null);
        if (unit == null) {
            return 0.0;
        }

        // Si c'est une activité financée
        if (unit.getType() == TypeProjet.ACTIVITY && financedActivityIds.contains(managementUnitId)) {
            List<FundingSourceEntity> activitySources = fundingSourceRepository
                    .findByManagementUnitIdPerso(managementUnitId);
            total += activitySources.stream()
                    .mapToDouble(s -> s.getMontant() != null ? s.getMontant() : 0.0)
                    .sum();

            // Tâches de cette activité
            List<TacheEntity> taches = tacheRepository.findByActiviteId(managementUnitId);
            for (TacheEntity tache : taches) {
                if (financedTacheIds.contains(tache.getId())) {
                    List<FundingSourceEntity> tacheSources = fundingSourceRepository.findByTacheId(tache.getId());
                    total += tacheSources.stream()
                            .mapToDouble(s -> s.getMontant() != null ? s.getMontant() : 0.0)
                            .sum();
                }
            }
        }

        // Si c'est un indicateur financé
        if (unit.getType() == TypeProjet.INDICATOR && financedIndicatorIds.contains(managementUnitId)) {
            List<FundingSourceEntity> indicatorSources = fundingSourceRepository
                    .findByManagementUnitIdPerso(managementUnitId);
            total += indicatorSources.stream()
                    .mapToDouble(s -> s.getMontant() != null ? s.getMontant() : 0.0)
                    .sum();

            // Tâches de cet indicateur
            List<TacheEntity> taches = tacheRepository.findByIndicatorId(managementUnitId);
            for (TacheEntity tache : taches) {
                if (financedTacheIds.contains(tache.getId())) {
                    List<FundingSourceEntity> tacheSources = fundingSourceRepository.findByTacheId(tache.getId());
                    total += tacheSources.stream()
                            .mapToDouble(s -> s.getMontant() != null ? s.getMontant() : 0.0)
                            .sum();
                }
            }
        }

        // Parcourir récursivement les enfants
        List<ManagementUnitEntity> children = managementUnitRepository.findByParentId(managementUnitId);
        for (ManagementUnitEntity child : children) {
            total += calculateDescendantsBudget(
                    child.getId(),
                    financedActivityIds,
                    financedTacheIds,
                    financedIndicatorIds,
                    budgetId
            );
        }

        return total;
    }
}
