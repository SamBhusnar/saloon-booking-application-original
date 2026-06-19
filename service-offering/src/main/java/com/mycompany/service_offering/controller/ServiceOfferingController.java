package com.mycompany.service_offering.controller;

import com.mycompany.service_offering.model.ServiceOffering;
import com.mycompany.service_offering.service.ServiceOfferingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/service-offering")
public class ServiceOfferingController {

    private final ServiceOfferingService service;

    @GetMapping("/salon/{salonId}")
    public ResponseEntity<Set<ServiceOffering>> getAllServiceBySalonId(
            @PathVariable Long salonId,
            @RequestParam(required = false) Long categoryId

    ) {
        return ResponseEntity.ok(service.getAllServiceBySalonId(salonId, categoryId));
    }


    @GetMapping("/{id}")
    public ResponseEntity<ServiceOffering> getServiceById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(service.getServiceById(id));

    }


    @GetMapping("/list/{ids}")
    public ResponseEntity<Set<ServiceOffering>> getServiceById(
            @PathVariable Set<Long> ids
    ) {

        return ResponseEntity.ok(service.getServiceByIds(ids));

    }


}
