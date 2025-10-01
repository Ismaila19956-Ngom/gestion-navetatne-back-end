package com.webgram.dgpsn.aspects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webgram.dgpsn.annotations.Journal;
import com.webgram.dgpsn.annotations.JournalAttribute;
import com.webgram.dgpsn.entities.JournalEntity;
import com.webgram.dgpsn.exceptions.UnhandledTraceableFieldException;
import com.webgram.dgpsn.models.UserDTO;
import com.webgram.dgpsn.repositories.JournalRepository;
import com.webgram.dgpsn.repositories.UserRepository;
import com.webgram.dgpsn.security.SecurityUtils;
import com.webgram.dgpsn.utils.HttpRequestResponseUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import ua_parser.Client;
import ua_parser.Parser;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Aspect
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Slf4j
public class JournalAspect {
    final JournalRepository journalRepository;
    final UserRepository userRepository;
    final ObjectMapper objectMapper;

    @Around("@annotation(journal)")
    public Object traceJournal(ProceedingJoinPoint p, Journal journal) throws Throwable {
        Object object = p.proceed();
        if (Objects.isNull(object) || (object instanceof Optional && ((Optional<?>) object).isEmpty())) {
            saveJournal(journal.actionType(), "{}", null);
            log.info("traceJournal null or optional empty - actionType: {}", journal.actionType());
        } else {
            final List<Object> objects;

            /* Checking if object is a list */
            if (object instanceof ArrayList) {
                objects = (List<Object>) object;
            } else if (object instanceof Optional && ((Optional<?>) object).isPresent()) {
                objects = Collections.singletonList(((Optional<?>) object).get());
            } else {
                objects = Collections.singletonList(object);
            }
            for (Object o : objects) {
                traceJournalForObject(journal.actionType(), o, false);
            }
        }
        return object;
    }

    private String asString(Object object) {


        if (Objects.isNull(object)) {
            return null;
        }

        if (object.getClass().equals(String.class) || ClassUtils.isPrimitiveOrWrapper(object.getClass()) || object.getClass().isEnum()) {
            return object.toString();
        }

        if (object.getClass().equals(Date.class)) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            return dateFormat.format(object);
        }

        if (object.getClass().equals(LocalDate.class)) {
            return ((LocalDate) object).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }

        if (object.getClass().equals(LocalDateTime.class)) {
            return ((LocalDateTime) object).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        }

        throw new UnhandledTraceableFieldException(MessageFormat.format("The type {0} is not handled !", object.getClass().getName()));
    }

    private void saveJournal(String actionType, String data, UserDTO user) {
        // Récupérer la chaîne User-Agent brute
        String rawUserAgent = HttpRequestResponseUtils.getUserAgent();

        // Créer une instance du parser. On peut la déclarer comme un champ statique
        // pour de meilleures performances.
        Parser uaParser = new Parser();

        // Parsez la chaîne User-Agent
        Client client = uaParser.parse(rawUserAgent);

        // Extraire le nom du navigateur et la famille d'appareils
        String browserName = client.userAgent.family;
        String deviceFamily = client.device.family;

        // S'assurer que le nom de l'appareil est plus lisible si inconnu
        if (deviceFamily.equalsIgnoreCase("Other") || deviceFamily.equalsIgnoreCase("Spider")) {
            deviceFamily = "Desktop";
        }

        var journal = JournalEntity
                .builder()
                .ipAddress(HttpRequestResponseUtils.getClientIpAddress())
                .actionType(actionType.split(":")[0])
                .path(actionType.split(":")[1])
                .queryString(HttpRequestResponseUtils.getPageQueryString())
                .page(HttpRequestResponseUtils.getRequestUri())
                .url(HttpRequestResponseUtils.getRequestUrl())
                .userAgent(HttpRequestResponseUtils.getUserAgent())
                .navigateur(browserName)
                .deviceType(deviceFamily)
                .method(HttpRequestResponseUtils.getRequestMethod())
                .refererPage(HttpRequestResponseUtils.getRefererPage())
                .data(data)
                .user(SecurityUtils.getCurrentUserLogin().orElse("anonymous"))
                .build();

        if (Objects.isNull(user)) {
            SecurityUtils.getCurrentUserLogin()
                    .flatMap(userRepository::findByLogin)
                    .ifPresent(userEntity -> {
                        journal.setLogin(userEntity.getLogin());
                        if (Objects.nonNull(userEntity.getAgent())) {
                            journal.setUser(String.join(" ", userEntity.getAgent().getPrenom(), userEntity.getAgent().getNom()));
                        }
                    });
        } else {
            journal.setUser(user.getLogin());
            if (Objects.nonNull(user.getAgent())) {
                journal.setUser(String.join(" ", user.getAgent().getPrenom(), user.getAgent().getNom()));
            }
        }

        journalRepository.save(journal);
    }

    private void traceJournalForObject(String actionType, Object object, boolean isLogout) throws IllegalAccessException, JsonProcessingException {

        log.debug("traceEvent start - actionType: {} - object: {}", actionType, object);

        /* Initializing data map */
        TreeMap<String, String> data = new TreeMap<>();

        /* Iterate over object attributes */
        for (Field field : FieldUtils.getFieldsListWithAnnotation(object.getClass(), JournalAttribute.class)) {

            /* Getting fieldObject */
            Object fieldObject = FieldUtils.readField(object, field.getName(), true);

            /* If object class is not a complex type */
            if (field.getType().equals(String.class) ||
                    ClassUtils.isPrimitiveOrWrapper(field.getType()) ||
                    field.getType().isEnum() ||
                    field.getType().equals(Date.class) ||
                    field.getType().equals(LocalDateTime.class) ||
                    field.getType().equals(LocalDate.class)
            ) {
                data.put(field.getName(), asString(fieldObject));
            } else {
                if (Objects.nonNull(fieldObject)) {
                    for (Field subField : FieldUtils.getFieldsListWithAnnotation(field.getClass(), JournalAttribute.class)) {

                        /* Setting subField Name */
                        String subFieldName = field.getName() + "|" + subField.getName();

                        /* Adding subField to data map */
                        data.put(subFieldName, asString(FieldUtils.readField(fieldObject, subFieldName, true)));

                    }
                }
            }
        }

        saveJournal(actionType, objectMapper.writeValueAsString(data), isLogout ? (UserDTO) object : null);

        log.info("traceEvent end ok - actionType: {} - data: {} - ip: {}", actionType, data, HttpRequestResponseUtils.getUserAgent());
    }
}
