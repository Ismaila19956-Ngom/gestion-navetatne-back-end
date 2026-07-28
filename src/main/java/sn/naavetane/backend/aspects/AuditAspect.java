package sn.naavetane.backend.aspects;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import sn.naavetane.backend.annotations.AuditableAction;
import sn.naavetane.backend.services.AuditService;

import java.lang.reflect.Method;

@Aspect
@Component
@RequiredArgsConstructor
public class AuditAspect {

    private final AuditService auditService;

    @AfterReturning("@annotation(sn.naavetane.backend.annotations.AuditableAction)")
    public void logAuditActivity(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        AuditableAction auditableAction = method.getAnnotation(AuditableAction.class);

        String acteur = "SYSTEM";
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            acteur = SecurityContextHolder.getContext().getAuthentication().getName();
        }

        String adresseIp = "";
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            adresseIp = request.getRemoteAddr();
        }

        String details = "Méthode exécutée: " + method.getName();

        auditService.logAction(acteur, auditableAction.action(), auditableAction.ressource(), details, adresseIp);
    }
}
