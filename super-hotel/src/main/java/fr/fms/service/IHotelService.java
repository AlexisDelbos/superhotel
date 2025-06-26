package fr.fms.service;


import fr.fms.entities.City;
import fr.fms.entities.Hotel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface IHotelService {
    List<Hotel> getHotels();

    void deleteHotel(Long id);

    Hotel saveHotel(Hotel hotel);

    Optional<Hotel> getHotel(Long id);

    List<City> getCities();

    void deleteCity(Long id);

    City saveCity(City city);

    Optional<City> getCity(Long id);

    Hotel getHotelByCity(Hotel hotel);
}
