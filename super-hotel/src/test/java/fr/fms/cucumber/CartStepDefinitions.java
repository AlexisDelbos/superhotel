package fr.fms.cucumber;

import fr.fms.entities.City;
import fr.fms.entities.Hotel;
import fr.fms.service.ImplHotelService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.hamcrest.Matchers.hasItems;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class CartStepDefinitions {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ImplHotelService hotelService;


    private ResultActions response;

    @Given("l'hotel est sauvé")
    public void l_hotel_est_ajoute() {
        City paris = hotelService.saveCity(new City(null, "Paris", "France", new ArrayList<>()));

        Hotel hotel = new Hotel(null, "Hotel Eiffel", "0625871397", 490.00,
                "2 boulevard de l'eiffel", "http://localhost:8080/images/hotelEiffel.jpg", 5, 20, new ArrayList<>(), paris);

        hotelService.saveHotel(hotel);
    }

    @Given("un autre hotel est aussi sauvé")
    public void un_autre_hotel_est_aussi_sauve() {
        City lyon = hotelService.saveCity(new City(null, "Lyon", "France", new ArrayList<>()));

        Hotel hotel = new Hotel(null, "Hotel Bellecour", "0632547890", 320.00,
                "5 place Bellecour", "http://localhost:8080/images/hotelBellecour.jpg", 4, 10, new ArrayList<>(), lyon);

        hotelService.saveHotel(hotel);
    }


    @When("l'utilisateur consulte la liste des hôtels")
    public void l_utilisateur_consulte_la_liste_des_hotels() throws Exception {
        response = mockMvc.perform(get("/api/hotels"));
    }


    @Then("la page est renvoyée avec un statut 200")
    public void la_page_est_renvoyee_avec_un_statut() throws Exception {
        response.andExpect(status().isOk());
    }

    // $[*] : pour ceux qui connaissent pas : Pour chaque élément à la racine d’un tableau JSON ça renvoi la valeur
    // par exemple là c'est les noms
    @Then("la réponse contient les noms des hôtels")
    public void la_reponse_contient_les_noms_des_hotels() throws Exception {
        response.andExpect(jsonPath("$[*].name", hasItems("Hotel Eiffel", "Hotel Bellecour")));
    }

    @Then("la réponse contient l'adresse de l'hôtel")
    public void la_reponse_contient_l_adresse_de_l_hotel() throws Exception {
        response.andExpect(jsonPath("$[*].address", hasItems("2 boulevard de l'eiffel", "5 place Bellecour")));
    }

    @Then("la réponse contient les numéros de téléphone des hôtels")
    public void la_reponse_contient_les_numeros_de_telephone_des_hotels() throws Exception {
        response.andExpect(jsonPath("$[*].phone", hasItems("0625871397", "0632547890")));
    }




}
