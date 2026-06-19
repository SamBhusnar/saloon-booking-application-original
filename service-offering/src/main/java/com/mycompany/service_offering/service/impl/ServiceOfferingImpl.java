package com.mycompany.service_offering.service.impl;

import com.mycompany.service_offering.dto.CategoryDto;
import com.mycompany.service_offering.dto.SalonDto;
import com.mycompany.service_offering.dto.ServiceOfferingDto;
import com.mycompany.service_offering.exception.ServiceOfferingAlreadyExistsException;
import com.mycompany.service_offering.exception.ServiceOfferingNotExistsException;
import com.mycompany.service_offering.model.ServiceOffering;
import com.mycompany.service_offering.repository.ServiceOfferingRepository;
import com.mycompany.service_offering.service.ServiceOfferingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ServiceOfferingImpl implements ServiceOfferingService {

    private final ServiceOfferingRepository serviceOfferingRepository;

    @Override
    public ServiceOffering createService(SalonDto salonDto, ServiceOfferingDto serviceOfferingDto, CategoryDto categoryDto) {
        // check service offering service exists with given  name if yes then throw exception else continue
        Optional<ServiceOffering> byName = serviceOfferingRepository.findByName(serviceOfferingDto.getName());
        byName.ifPresent(serviceOffering -> {
            throw new ServiceOfferingAlreadyExistsException("Service offering already exists with " + serviceOfferingDto.getName() + " name");
        });

        ServiceOffering serviceOffering = new ServiceOffering();
        serviceOffering.setName(serviceOfferingDto.getName());
        serviceOffering.setDescription(serviceOfferingDto.getDescription());
        serviceOffering.setImage(serviceOfferingDto.getImage());
        serviceOffering.setPrice(serviceOfferingDto.getPrice());
        serviceOffering.setDuration(serviceOfferingDto.getDuration());
        serviceOffering.setSalonId(salonDto.getId());
        serviceOffering.setCategoryId(categoryDto.getId());
        return serviceOfferingRepository.save(serviceOffering);
    }

    @Override
    public ServiceOffering updateService(Long serviceId, ServiceOffering updatedData) {

        serviceOfferingRepository.findById(serviceId).orElseThrow(() -> new ServiceOfferingNotExistsException("Service offering not found with id " + serviceId));
        Optional<ServiceOffering> byName = serviceOfferingRepository.findByName(updatedData.getName());
        if (byName.isPresent() && !byName.get().getId().equals(serviceId)) {
            throw new ServiceOfferingAlreadyExistsException("Service offering already exists with " + updatedData.getName() + " name choose another name");

        }


        ServiceOffering newServiceOffering = new ServiceOffering();
        newServiceOffering.setName(updatedData.getName());
        newServiceOffering.setPrice(updatedData.getPrice());
        newServiceOffering.setDuration(updatedData.getDuration());
        newServiceOffering.setDescription(updatedData.getDescription());
        newServiceOffering.setImage(updatedData.getImage());
        newServiceOffering.setCategoryId(updatedData.getCategoryId());
        newServiceOffering.setSalonId(updatedData.getSalonId());
        newServiceOffering.setId(serviceId);

        return serviceOfferingRepository.save(newServiceOffering);
    }

    @Override
    public Set<ServiceOffering> getAllServiceBySalonId(Long salonId, Long categoryId) {


        List<ServiceOffering> bySalonId = serviceOfferingRepository.findBySalonId(salonId);

        Set<ServiceOffering> serviceOfferings = new HashSet<>();
        for (ServiceOffering item : bySalonId) {
            boolean equals = item.getCategoryId().equals(categoryId);
            if (equals) {
                serviceOfferings.add(item);
            }
        }

        return serviceOfferings;
    }

    @Override
    public Set<ServiceOffering> getServiceByIds(Set<Long> ids) {


        List<ServiceOffering> services = serviceOfferingRepository.findAllById(ids);
        return new HashSet<>(services);


    }

    @Override
    public ServiceOffering getServiceById(Long serviceId) {
        return serviceOfferingRepository.findById(serviceId).orElseThrow(() -> new ServiceOfferingNotExistsException("Service offering not found with id " + serviceId));
    }


}
