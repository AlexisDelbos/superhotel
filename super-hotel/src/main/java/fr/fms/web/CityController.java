package fr.fms.web;

import fr.fms.dao.CityRepository;
import fr.fms.entities.City;
import fr.fms.entities.Hotel;
import fr.fms.service.ImplHotelService;
import jakarta.transaction.Transactional;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@Transactional
@RequestMapping("/api")
public class CityController {

    @Autowired
    private ImplHotelService hotelService;

    @GetMapping("/cities")
    public List<City> AllCities() {
        return hotelService.getCities();
    }


    @PostMapping ("/cities")
    public ResponseEntity createCity(@RequestBody City city) {
        City newCity = new City(null, city.getName(), city.getCountry(), null);
        City cityUpdated = hotelService.saveCity(newCity);
        return ResponseEntity.ok(cityUpdated);
    }


}
