package com.mycompany.saloon_service.service.impl;

import com.mycompany.saloon_service.dto.SaloonDto;
import com.mycompany.saloon_service.dto.UserDto;
import com.mycompany.saloon_service.exception.SalonAndOwnerNotMatchException;
import com.mycompany.saloon_service.exception.SalonNotFoundException;
import com.mycompany.saloon_service.model.Salon;
import com.mycompany.saloon_service.model.Salon;
import com.mycompany.saloon_service.repository.SalonRepository;
import com.mycompany.saloon_service.service.SaloonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SaloonServiceImpl implements SaloonService {

    private final SalonRepository salonRepository;




    @Override
    public Salon createSaloon(SaloonDto saloonDto, UserDto userDto) {
        Salon saloon = new Salon();
        // set the fields of saloon
        saloon.setName(saloonDto.getName());
        saloon.setEmail(saloonDto.getEmail());
        saloon.setAddress(saloonDto.getAddress());
        saloon.setImages(saloonDto.getImages());
        saloon.setCity(saloonDto.getCity());
        saloon.setPhoneNumber(saloonDto.getPhoneNumber());
        saloon.setOpenTime(saloonDto.getOpenTime());
        saloon.setCloseTime(saloonDto.getCloseTime());
        saloon.setOwnerId(userDto.getId());
        return salonRepository.save(saloon);


    }

    @Override
    public Salon updateSaloon(SaloonDto updatedSaloonDto, UserDto user, Long salonId) {
        if(!updatedSaloonDto.getOwnerId().equals(user.getId())){
            throw new SalonAndOwnerNotMatchException("Salon and owner not match");
        }
        Salon  existingSalon=salonRepository.findById(salonId).orElseThrow(() -> new SalonNotFoundException("Salon not found"));
        existingSalon.setCity(updatedSaloonDto.getCity());
        existingSalon.setName(updatedSaloonDto.getName());
        existingSalon.setAddress(updatedSaloonDto.getAddress());
        existingSalon.setEmail(updatedSaloonDto.getEmail());
        existingSalon.setPhoneNumber(updatedSaloonDto.getPhoneNumber());
        existingSalon.setOpenTime(updatedSaloonDto.getOpenTime());
        existingSalon.setCloseTime(updatedSaloonDto.getCloseTime());
        existingSalon.setImages(updatedSaloonDto.getImages());
        existingSalon.setOwnerId(user.getId());
        return salonRepository.save(existingSalon);

    }



    @Override
    public void deleteSaloon(Long id) {
        // check salon exists or not if not throw exception
        findSaloonById(id);
        salonRepository.deleteById(id);

    }

    @Override
    public Salon findSaloonById(Long id) {
        return salonRepository.findById(id).orElseThrow(() -> new SalonNotFoundException("Salon not found"));
    }

    @Override
    public List<Salon> findAllSaloons() {
        return  salonRepository.findAll();
    }

    @Override
    public List<Salon> findSaloonsByOwnerId(Long ownerId) {
        return  salonRepository.findByOwnerId(ownerId);
    }

    @Override
    public List<Salon> searchSaloonsByCity(String city) {
        return  salonRepository.searchSaloons(city);
    }
}
