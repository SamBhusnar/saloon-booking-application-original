package com.mycompany.saloon_service.repository;

import com.mycompany.saloon_service.model.Salon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SalonRepository extends JpaRepository<Salon, Long> {
    // find by owner id
    List<Salon> findByOwnerId(Long ownerId);

    // search by city use the query to search keyword in city , name , address use lower fn

    @Query("SELECT s FROM Salon s WHERE " +
            "lower(s.city) LIKE lower(concat('%',:keyword,'%') ) OR " +
            "lower(s.name) LIKE lower(concat('%',:keyword,'%')) OR" +
            " lower(s.address) LIKE lower(concat('%',:keyword,'%'))")
    List<Salon> searchSaloons(@Param("keyword") String city);
}
