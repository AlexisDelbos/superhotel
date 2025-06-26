package fr.fms;

import fr.fms.dao.UserRepository;
import fr.fms.entities.City;
import fr.fms.entities.Hotel;
import fr.fms.entities.Role;
import fr.fms.entities.User;
import fr.fms.service.AccountServiceImpl;
import fr.fms.service.ImplHotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;

@SpringBootApplication
public class SuperHotelApplication implements CommandLineRunner {

    @Autowired
    AccountServiceImpl accountService;

    @Autowired
    ImplHotelService hotelService;
    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {

        SpringApplication.run(SuperHotelApplication.class, args);
    }

    @Override
    public void run(String...args) throws Exception{
        dataUsers();
        dataCity();
    }

    private void dataUsers(){

        accountService.saveUser(new User(null,"admin@gmail.com","ADMIN",new ArrayList<>(), new ArrayList<>()));
        accountService.saveUser(new User(null,"user@gmail.com","USER",new ArrayList<>(), new ArrayList<>()));
        accountService.saveUser(new User(null,"manager@gmail.com","MANAGER",new ArrayList<>(), new ArrayList<>()));
        accountService.saveRole(new Role(null, "ADMIN"));
        accountService.saveRole(new Role(null, "MANAGER"));
        accountService.saveRole(new Role(null, "USER"));
        accountService.addRoleToUser("admin@gmail.com", "ADMIN");
        accountService.addRoleToUser("admin@gmail.com", "MANAGER");
        accountService.addRoleToUser("admin@gmail.com", "USER");
        accountService.addRoleToUser("manager@gmail.com", "USER");
        accountService.addRoleToUser("manager@gmail.com", "MANAGER");
        accountService.addRoleToUser("user@gmail.com", "USER");


    }

    private void dataCity() {
        City paris = hotelService.saveCity(new City(null, "Paris", "France", new ArrayList<>()));
        City marseille = hotelService.saveCity(new City(null, "Marseille", "France", new ArrayList<>()));
        City lyon = hotelService.saveCity(new City(null, "Lyon", "France", new ArrayList<>()));
        City londres = hotelService.saveCity(new City(null, "Londres", "Angleterre", new ArrayList<>()));
        City berlin = hotelService.saveCity(new City(null, "Berlin", "Allemagne", new ArrayList<>()));
        City washington = hotelService.saveCity(new City(null, "Washington", "Etats-Unis", new ArrayList<>()));

        hotelService.saveHotel(new Hotel(null, "Hotel Eiffel", "0625871397", 490.00,
                "2 boulevard de l'eiffel", "http://localhost:8080/images/hotelEiffel.jpg", 5, 20, new ArrayList<>(), paris));

        hotelService.saveHotel(new Hotel(null, "Hotel Velodrome", "0629393430", 50.00,
                "2 boulevard de la cannebière", "http://localhost:8080/images/hotelCannebiere.jpg", 2, 10, new ArrayList<>(), marseille));

        hotelService.saveHotel(new Hotel(null, "Hotel Georges ", "0231320593", 650.00,
                "2 boulevard de la Seine", "http://localhost:8080/images/hotelTourEiffel.jpg", 5, 40, new ArrayList<>(), paris));
        hotelService.saveHotel(new Hotel(null, "Hotel Lyon ", "0102030405", 89.00,
                "2 boulevard des Lumières", "http://localhost:8080/images/hotelLyon.jpg", 2, 40, new ArrayList<>(), lyon));
        hotelService.saveHotel(new Hotel(null, "Hotel London ", "0231320593", 990.00,
                "2 boulevard de Londres", "http://localhost:8080/images/hotelLondres.jpg", 5, 30, new ArrayList<>(), londres));
        hotelService.saveHotel(new Hotel(null, "Hotel Berlin ", "0231320593", 995.00,
                "2 boulevard de Munich", "http://localhost:8080/images/hotelBerlin.jpg", 5, 15, new ArrayList<>(), berlin));



        hotelService.addManagerToHotel("manager@gmail.com", "Hotel Eiffel");


    }


}
