package com.webgram.dgpsn.controllers;

import com.webgram.dgpsn.entities.enums.Feature;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.webgram.dgpsn.entities.enums.Module;
import com.webgram.dgpsn.security.SecurityPermissions;

import java.util.Set;

@RestController
@RequestMapping("/permissions")
@Tag(name = "permissions-controller", description = "permissions controller")
@RequiredArgsConstructor
public class SecurityPermissionController {

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Set<SecurityPermissions> readAllPermissions(@RequestParam(value = "module", required = false) Module module) {
        return SecurityPermissions.readSecurityPermissionsByModule(module);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/readByModule")
    public Set<SecurityPermissions> readAllPermissionsByModule(@RequestParam(value = "module", required = false) String module, @RequestParam(value = "feature", required = false) String feature) {
        return SecurityPermissions.readSecurityPermissionsByModule(module,feature);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/readModules")
    public Set<Module> readAllModules() {
        return Module.readModules();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/readFeatureByModule")
    public Set<Feature> readFeatureByModule(@RequestParam(value = "module", required = false) String module) {
        return Feature.readFeatureByModule(module);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/filterByModuleAndFeature")
    public Set<SecurityPermissions> filterByModuleAndFeature(
            @RequestParam(value = "moduleName", required = false) String module,
            @RequestParam(value = "featureName", required = false) String feature
    ) {
        return SecurityPermissions.filterByModuleAndFeature(module, feature);
    }

}
