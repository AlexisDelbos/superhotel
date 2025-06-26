package fr.fms.service;

import fr.fms.dao.CityRepository;
import fr.fms.dao.HotelRepository;
import fr.fms.dao.UserRepository;
import fr.fms.dto.RecommandationDto;
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

    private final AiService aiService;

    private static  String prompt= "";

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private UserRepository userRepository;

    public ImplHotelService(AiService aiService) {
        this.aiService = aiService;
    }


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
// ---------------------------------------- IA


    public List<RecommandationDto> getHotelsSortedByRating() {
        List<Hotel> hotels = hotelRepository.findAll();

        for (Hotel hotel : hotels) {
            prompt = "repond en francais et dis moi  ,pour chaque hotel, fais moi une recommandation en fonction de son nom et de sa note et par rapport au taux d'occupation entre le totalroom et availableroom  :\nHotel: " + hotel.getCity().getName() + ", Rating: " + hotel.getStars() + ", Name: " + hotel.getName() + "\n";
            try {
                String response = aiService.chat(prompt);
                hotel.setRecommendation(response.trim());
                System.out.println("Generated recommendation for hotel: " + hotel.getName());
            } catch (Exception e) {
                System.err.println("Error generating recommendation for hotel: " + hotel.getName());
                hotel.setRecommendation("No recommendation available at the moment.");
            }
        }
        List<RecommandationDto>  recommandationDto =  hotels.stream().map(hotel -> {
            int occupancyRate = (int) Math.ceil(
                    (double) hotel.getAvailableRooms() / hotel.getTotalRooms() * 100
            );

            return new RecommandationDto(hotel.getName(), hotel.getCity().getName(), occupancyRate, hotel.getRecommendation());
        }).sorted(Comparator.comparingInt(RecommandationDto::occupancyRate).reversed()).toList();
        hotelRepository.saveAll(hotels);
        return recommandationDto;
    }

}
