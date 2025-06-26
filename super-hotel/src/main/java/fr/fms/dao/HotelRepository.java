package fr.fms.dao;

import fr.fms.entities.City;
import fr.fms.entities.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

    Hotel findByCity(City city);

    Hotel findByName(String hotelName);
}
