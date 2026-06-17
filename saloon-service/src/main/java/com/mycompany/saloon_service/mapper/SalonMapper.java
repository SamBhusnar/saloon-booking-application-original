package com.mycompany.saloon_service.mapper;

import com.mycompany.saloon_service.dto.SaloonDto;
import com.mycompany.saloon_service.model.Salon;

import java.util.List;

public class SalonMapper {

    public static SaloonDto toSalonDto(Salon saloon) {
        SaloonDto saloonDto=new SaloonDto();
        saloonDto.setId(saloon.getId());
        saloonDto.setName(saloon.getName());
        saloonDto.setCity(saloon.getCity());
        saloonDto.setAddress(saloon.getAddress());
        saloonDto.setEmail(saloon.getEmail());
        saloonDto.setCloseTime(saloon.getCloseTime());
        saloonDto.setOpenTime(saloon.getOpenTime());
        saloonDto.setImages(saloon.getImages());
        saloonDto.setOwnerId(saloon.getOwnerId());
        saloonDto.setPhoneNumber(saloon.getPhoneNumber());
        return saloonDto;
    }

    public static List<SaloonDto> toSalonDtoList(List<Salon> saloons) {
        return saloons.stream().map(SalonMapper::toSalonDto).toList();
    }


}
