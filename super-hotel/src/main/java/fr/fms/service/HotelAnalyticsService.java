package fr.fms.service;

import fr.fms.dao.HotelRepository;
import fr.fms.dao.ReservationRepository;
import fr.fms.dao.ReviewRepository;
import fr.fms.entities.Hotel;
import fr.fms.entities.Reservation;
import fr.fms.entities.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelAnalyticsService {

    private final HotelRepository hotelRepository;
    private final ReviewRepository reviewRepository;
    private final ReservationRepository reservationRepository;

    List<Review> reviews = reviewRepository.findByName(Hotel);
    List<Reservation> reservations = reservationRepository.findByName(Hotel);

    // Calcule taux d'occupation
    long totalRooms = Hotel.getNumberOfRooms();
}
