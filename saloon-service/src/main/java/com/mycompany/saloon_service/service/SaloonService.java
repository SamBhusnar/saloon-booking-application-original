package com.mycompany.saloon_service.service;


import com.mycompany.saloon_service.dto.SaloonDto;
import com.mycompany.saloon_service.dto.UserDto;
import com.mycompany.saloon_service.model.Salon;

import java.util.List;

public interface SaloonService {
    // create saloon,update saloon,delete saloon,find saloon by id,find all saloons

    public Salon createSaloon(SaloonDto saloon, UserDto user);
    public void deleteSaloon(Long id);
    public Salon updateSaloon(SaloonDto saloon, UserDto user, Long id);
    public Salon findSaloonById(Long id);
    public List<Salon> findAllSaloons();
    // get saloon by owner id
    public List<Salon> findSaloonsByOwnerId(Long ownerId);
    // search saloon by city name
    public List<Salon> searchSaloonsByCity(String city);
}
