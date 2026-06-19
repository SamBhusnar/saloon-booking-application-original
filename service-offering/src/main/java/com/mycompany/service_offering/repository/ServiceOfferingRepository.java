package com.mycompany.service_offering.repository;

import com.mycompany.service_offering.model.ServiceOffering;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ServiceOfferingRepository extends JpaRepository<ServiceOffering, Long> {

    List<ServiceOffering> findBySalonId(Long salonId);

    Optional<ServiceOffering> findByName(String name);


}
