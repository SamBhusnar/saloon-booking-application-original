package com.mycompany.saloon_service.controller;

import com.mycompany.saloon_service.dto.SaloonDto;
import com.mycompany.saloon_service.dto.UserDto;
import com.mycompany.saloon_service.mapper.SalonMapper;
import com.mycompany.saloon_service.model.Salon;
import com.mycompany.saloon_service.service.SaloonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salons")
@RequiredArgsConstructor
public class SalonController {
    private final SaloonService saloonService;

    // create salon and return salonDto
    @PostMapping
    public ResponseEntity<SaloonDto> createSaloon(@RequestBody SaloonDto salonDto) {
        // user dto instance
        UserDto userDto = new UserDto();

        userDto.setId(salonDto.getOwnerId());


        Salon saloon = saloonService.createSaloon(salonDto, userDto);
        SaloonDto salonDtoMapped = SalonMapper.toSalonDto(saloon);
        return ResponseEntity.ok(salonDtoMapped);
    }

    // update salon and return salonDto
    @PatchMapping("/{saloonId}")
    public ResponseEntity<SaloonDto> updateSaloon(
            @RequestBody SaloonDto salonDto,
            @PathVariable Long saloonId


    ) {
        // user dto instance
        UserDto userDto = new UserDto();

        userDto.setId(salonDto.getOwnerId());


        Salon salon = saloonService.updateSaloon(salonDto, userDto, saloonId);

        SaloonDto salonDtoMapped = SalonMapper.toSalonDto(salon);

        return ResponseEntity.ok(salonDtoMapped);
    }

    // get mapping
    @GetMapping
    public ResponseEntity<List<SaloonDto>> getAllSalons(
    ) {

        List<Salon> allSalons = saloonService.findAllSaloons();
        List<SaloonDto> allSalonsDto = SalonMapper.toSalonDtoList(allSalons);
        return ResponseEntity.ok(allSalonsDto);
    }

    // get salon by id
    @GetMapping("/{id}")
    public ResponseEntity<SaloonDto> getSalonById(
            @PathVariable Long id
    ) {

        Salon salonById = saloonService.findSaloonById(id);
        SaloonDto salonDto = SalonMapper.toSalonDto(salonById);
        return ResponseEntity.ok(salonDto);
    }

    // search salon
    @GetMapping("/search")
    public ResponseEntity<List<SaloonDto>> searchSalon(

            @RequestParam("city") String search


    ) {


        List<Salon> salons = saloonService.searchSaloonsByCity(search);

        List<SaloonDto> salonDtoList = SalonMapper.toSalonDtoList(salons);

        return ResponseEntity.ok(salonDtoList);
    }


    // get salon by owner id

    @GetMapping("/owner")
    public ResponseEntity<List<SaloonDto>> getSalonByOwnerId() {


        List<Salon> salons = saloonService.findSaloonsByOwnerId(42L);

        List<SaloonDto> salonDtoList = SalonMapper.toSalonDtoList(salons);

        return ResponseEntity.ok(salonDtoList);
    }


}
