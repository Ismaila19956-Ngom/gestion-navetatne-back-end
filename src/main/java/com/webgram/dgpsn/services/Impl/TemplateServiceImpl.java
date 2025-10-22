package com.webgram.dgpsn.services.Impl;

import com.webgram.dgpsn.entities.QTemplateEntity;
import com.webgram.dgpsn.entities.TemplateEntity;
import com.webgram.dgpsn.entities.enums.CategorieAlerte;
import com.webgram.dgpsn.entities.enums.Priority;
import com.webgram.dgpsn.entities.enums.TypeAlerte;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.TemplateMapper;
import com.webgram.dgpsn.models.TemplateDto;
import com.webgram.dgpsn.repositories.TemplateRepository;
import com.webgram.dgpsn.services.TemplateService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class TemplateServiceImpl implements TemplateService {
    private final TemplateRepository templateRepository;
    private final TemplateMapper templateMapper;

    String TEMPLATE_IDENTIFIER_NOT_FOUND_MESSAGE = "Invalide id template: {0}";

    @Override
    public TemplateDto create(TemplateDto templateDto) {
        var createdTemplate = templateRepository.save(templateMapper.asEntity(templateDto));
        log.info("createdTemplate ok id {}", createdTemplate.getId());
        log.trace("createdTemplate ok  {}", createdTemplate);
        return templateMapper.asDto(createdTemplate);
    }

    @Override
    public TemplateDto update(TemplateDto templateDto) {
        log.info("updatedTemplate ok id {}", templateDto.getId());
        if(!templateRepository.existsById(templateDto.getId())){
            throw new ResourceNotFoundException(MessageFormat.format(TEMPLATE_IDENTIFIER_NOT_FOUND_MESSAGE, templateDto.getId()));
        }
        var template = templateMapper.asEntity(templateDto);
        var updatedTemplate = templateRepository.save(template);
        log.info("updatedTemplate ok id {}", updatedTemplate.getId());
        log.trace("updatedTemplate ok  {}", updatedTemplate);
        return templateMapper.asDto(updatedTemplate);
    }

    @Override
    public TemplateDto read(Long id) {
        var template = templateRepository.findById(id)
                .map(templateMapper::asDto)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(TEMPLATE_IDENTIFIER_NOT_FOUND_MESSAGE, id)));
        log.info("read template end ok - Id: {}", id);
        log.trace("read template end ok - template: {}", template);
        return template;
    }


    @Override
    public void delete(Long id) {
        if(!templateRepository.existsById(id)){
            throw new ResourceNotFoundException(MessageFormat.format(TEMPLATE_IDENTIFIER_NOT_FOUND_MESSAGE, id));
        }
        templateRepository.deleteById(id);
        log.info("delete template ok id {}", id);
    }

    @Override
    public Page<TemplateDto> readAll(Pageable pageable, String libelle,CategorieAlerte categorieAlerte, TypeAlerte typeAlerte, Priority priority) {
        var template = templateRepository.readAllByFilters(pageable, libelle, categorieAlerte, typeAlerte,priority)
                .map(templateMapper::asDto);
        log.trace("list template get ok {}", template);
        return template;
    }

    @Override
    public Set<TypeAlerte> readAlertTypes(CategorieAlerte categorieAlerte) {
        var addedTypes = ((List<TemplateEntity>) templateRepository.findAll(QTemplateEntity.templateEntity.categorieAlerte.eq(categorieAlerte)))
                .stream()
                .map(TemplateEntity::getTypeAlerte)
                .collect(Collectors.toList());
        return TypeAlerte.getAllTypesAlerte(categorieAlerte).stream()
                .filter(alertType -> (addedTypes.stream().noneMatch(alertType::equals)))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<CategorieAlerte> readAllCategorieAlerte() {
        return CategorieAlerte.getAllCategorieAlerte();
    }
}
