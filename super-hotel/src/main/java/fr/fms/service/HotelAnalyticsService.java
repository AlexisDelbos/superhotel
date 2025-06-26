package fr.fms.service;

import fr.fms.dao.HotelRepository;
import fr.fms.dao.ReviewRepository;
import fr.fms.entities.Hotel;
import fr.fms.entities.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelAnalyticsService {

    private final HotelRepository hotelRepository;
    private final ReviewRepository reviewRepository;

    // Retourne l'analyse de l'hotel
    public String analyzeHotelPerformance(String hotelName) {
        Hotel hotel = hotelRepository.findByName(hotelName);
        if (hotel == null) {
            return "Hôtel non trouvé.";
        }
        double occupation = (double) (hotel.getTotalRooms() - hotel.getAvailableRooms()) / hotel.getTotalRooms() * 100;
        long reviewCount = reviewRepository.countByHotel(hotel);
        double averageRating = reviewRepository.averageRatingByHotel(hotel);
        List<Review> reviews = reviewRepository.findByHotel(hotel);
        StringBuilder avisClients = new StringBuilder();
        for (Review review : reviews) {
            avisClients.append(review.getComment()).append("; ");
        }
        String prompt = String.format(
                "L'hôtel %s a un taux d'occupation de %.2f%%. Il a reçu %d avis avec une note moyenne de %.2f. avis clients : %s",
                hotel.getName(),
                occupation,
                reviewCount,
                averageRating,
                avisClients.toString());
        return prompt;
    }
}
