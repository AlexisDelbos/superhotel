package fr.fms.web;

import fr.fms.dao.HotelRepository;
import fr.fms.entities.City;
import fr.fms.entities.Hotel;
import fr.fms.entities.User;
import fr.fms.service.AccountServiceImpl;
import fr.fms.service.IHotelService;
import fr.fms.service.ImplHotelService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@Transactional
@RequestMapping("/api")
public class HotelController {

    @Autowired
    private ImplHotelService hotelService;

    @Autowired
    private AccountServiceImpl accountService;

    @GetMapping("/hotels")
    public List<Hotel> AllHotels() {
        return hotelService.getHotels();
    }

    @GetMapping("/hotels/{id}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable("id") Long id) {
        Optional<Hotel> hotel = hotelService.getHotel(id);
        if (hotel.isPresent()) {
            Hotel h = hotel.get();
            System.out.println(h.getName() + " " + h.getAddress());
            return new ResponseEntity<>(h, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/hotels/{id}")
    public ResponseEntity<?> deleteHotel(@PathVariable("id") Long id) {
        Optional<Hotel> hotelOpt = hotelService.getHotel(id);

        if (!hotelOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Hotel hotel = hotelOpt.get();
        String image = hotel.getImage();

        if (image != null && !image.isEmpty()) {
            try {
                String fileName = image.substring(image.lastIndexOf("/") + 1);
                Path imagePath = Paths.get("src/main/resources/static/images/" + fileName);

                if (Files.exists(imagePath)) {
                    Files.delete(imagePath);
                    System.out.println("Image supprimée: " + imagePath);
                }
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Erreur lors de la suppression de l'image.");
            }
        }

        hotelService.deleteHotel(id);
        return ResponseEntity.ok().body("Hotel supprimé avec succès.");
    }


    @PostMapping("/hotels")
    public ResponseEntity<Hotel> createHotel(
            @RequestParam("name") String name,
            @RequestParam("phone") String phone,
            @RequestParam("address") String address,
            @RequestParam("price") double price,
            @RequestParam("stars") int stars,
            @RequestParam("availableRooms") int availableRooms,
            @RequestParam("city") Long cityId,
            @RequestParam("managerId") Long managerId,
            @RequestParam(value = "file", required = false) MultipartFile file) {

        Optional<City> city = hotelService.getCity(cityId);
        if (!city.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        User manager = accountService.findUserByIdAndRole(managerId, "MANAGER");
        if (manager == null) {
            return ResponseEntity.badRequest().build();
        }

        Hotel hotel = new Hotel();
        hotel.setName(name);
        hotel.setPhone(phone);
        hotel.setAddress(address);
        hotel.setPrice(price);
        hotel.setStars(stars);
        hotel.setAvailableRooms(availableRooms);
        hotel.setCity(city.get());
        hotel.getManagers().add(manager);

        if (file != null && !file.isEmpty()) {
            try {
                String fileName = file.getOriginalFilename();
                String uploadDir = "src/main/resources/static/images/";
                Path path = Paths.get(uploadDir + fileName);
                Files.createDirectories(path.getParent());
                Files.write(path, file.getBytes());
                hotel.setImage("http://localhost:8080/images/" + fileName);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        Hotel updatedHotel = hotelService.saveHotel(hotel);
        return ResponseEntity.ok(updatedHotel);
    }


    @PutMapping("/hotels/{id}")
    public ResponseEntity<Hotel> updateHotel(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("phone") String phone,
            @RequestParam("address") String address,
            @RequestParam("price") double price,
            @RequestParam("stars") int stars,
            @RequestParam("availableRooms") int availableRooms,
            @RequestParam("city") Long cityId,
            @RequestParam("managerId") Long managerId, // identifiant du manager
            @RequestParam(value = "file", required = false) MultipartFile file) {

        Optional<Hotel> existingHotel = hotelService.getHotel(id);
        Optional<City> city = hotelService.getCity(cityId);
        if (!existingHotel.isPresent() || !city.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        // Utilisation de la méthode correcte pour vérifier si l'utilisateur est un manager
        User manager = accountService.findUserByIdAndRole(managerId, "MANAGER");
        if (manager == null) {
            return ResponseEntity.badRequest().build();
        }

        Hotel hotel = existingHotel.get();
        hotel.setName(name);
        hotel.setPhone(phone);
        hotel.setAddress(address);
        hotel.setPrice(price);
        hotel.setStars(stars);
        hotel.setAvailableRooms(availableRooms);
        hotel.setCity(city.get());

        // Effacer les managers précédents et ajouter le nouveau manager
        hotel.getManagers().clear();
        hotel.getManagers().add(manager); // Ajout du manager

        // Traitement de l'image (si elle existe)
        if (file != null && !file.isEmpty()) {
            try {
                String fileName = file.getOriginalFilename();
                String uploadDir = "src/main/resources/static/images/";
                Path path = Paths.get(uploadDir + fileName);
                Files.createDirectories(path.getParent());
                Files.write(path, file.getBytes());
                hotel.setImage(fileName);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        // Sauvegarde de l'hôtel
        Hotel updatedHotel = hotelService.saveHotel(hotel);
        return ResponseEntity.ok(updatedHotel);
    }


}
