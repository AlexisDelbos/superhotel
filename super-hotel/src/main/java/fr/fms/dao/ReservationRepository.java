package fr.fms.dao;

import fr.fms.entities.Hotel;
import fr.fms.entities.Reservation;

import java.util.List;

public interface ReservationRepository {
    List<Reservation> findByName(Hotel hotel);
}
