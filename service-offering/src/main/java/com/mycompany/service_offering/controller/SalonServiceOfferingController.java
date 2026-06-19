package com.mycompany.service_offering.controller;


import com.mycompany.service_offering.dto.CategoryDto;
import com.mycompany.service_offering.dto.SalonDto;
import com.mycompany.service_offering.dto.ServiceOfferingDto;
import com.mycompany.service_offering.model.ServiceOffering;
import com.mycompany.service_offering.service.ServiceOfferingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/service-offering/salon-owner")
public class SalonServiceOfferingController {

    private final ServiceOfferingService service;


    @PostMapping
    public ResponseEntity<ServiceOffering> createService(
            @RequestBody @Valid ServiceOfferingDto serviceOfferingDto
    ) {

        SalonDto salonDto = new SalonDto();
        salonDto.setId(1L);

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(serviceOfferingDto.getCategoryId());

        ServiceOffering service1 = service.createService(salonDto, serviceOfferingDto, categoryDto);

        return ResponseEntity.ok(service1);


    }


    @PutMapping("/{serviceId}")
    public ResponseEntity<ServiceOffering> updateService(

            @PathVariable Long serviceId,
            @RequestBody ServiceOffering serviceOffering
    ) {


        ServiceOffering service1 = service.updateService(serviceId, serviceOffering);

        return ResponseEntity.ok(service1);


    }


}
