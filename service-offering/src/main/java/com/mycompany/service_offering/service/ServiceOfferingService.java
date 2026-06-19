package com.mycompany.service_offering.service;

import com.mycompany.service_offering.dto.CategoryDto;
import com.mycompany.service_offering.dto.SalonDto;
import com.mycompany.service_offering.dto.ServiceOfferingDto;
import com.mycompany.service_offering.model.ServiceOffering;

import java.util.Set;

public interface ServiceOfferingService {

    public ServiceOffering createService(
            SalonDto salonDto,
            ServiceOfferingDto serviceOfferingDto,
            CategoryDto categoryDto
    );

    ServiceOffering updateService(Long serviceId, ServiceOffering updatedData);

    Set<ServiceOffering> getAllServiceBySalonId(Long salonId, Long categoryId);

    Set<ServiceOffering> getServiceByIds(Set<Long> ids);

    ServiceOffering getServiceById(Long serviceId);

}
