package fr.fms.service;

import fr.fms.dao.CityRepository;
import fr.fms.dao.HotelRepository;
import fr.fms.dao.UserRepository;
import fr.fms.entities.City;
import fr.fms.entities.Hotel;
import fr.fms.entities.User;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ImplHotelService implements IHotelService{


    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public List<Hotel> getHotels() {
        return hotelRepository.findAll();
    }

    @Override
    public void deleteHotel(Long id){
        hotelRepository.deleteById(id);
    }

    @Override
    public Hotel saveHotel(Hotel hotel){
        return hotelRepository.save(hotel);
    }

    @Override
    public Optional<Hotel> getHotel(Long id){
        return hotelRepository.findById(id);
    }

    // ---------------------------------------------

    @Override
    public List<City> getCities(){
        return cityRepository.findAll();
    }

    @Override
    public void deleteCity(Long id){
        cityRepository.deleteById(id);
    }

    @Override
    public City saveCity(City city){
        return cityRepository.save(city);
    }

    @Override
    public Optional<City> getCity(Long id){
        return cityRepository.findById(id);
    }

    // ----------------------------------------

    @Override
    public Hotel getHotelByCity(Hotel hotel){
        Optional<Hotel> hotelOptional = hotelRepository.findById(hotel.getId());
        if(hotelOptional.isPresent()){
            return hotelOptional.get();
        }

        return hotel;
    }


    public void addManagerToHotel(String managerEmail, String hotelName) {
        Hotel hotel = hotelRepository.findByName(hotelName);
        if (hotel == null) {
            throw new RuntimeException("Hotel not found");
        }

        User manager = userRepository.findByUsername(managerEmail);
        if (manager == null || !manager.getRoles().stream().anyMatch(role -> role.getRole().equals("MANAGER"))) {
            throw new RuntimeException("Manager not found or does not have the 'MANAGER' role");
        }

        Hibernate.initialize(hotel.getManagers());

        hotel.getManagers().add(manager);
        hotelRepository.save(hotel);
    }



}
