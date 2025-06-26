package fr.fms.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "T_Hotel")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Hotel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String phone;
    private double price;
    private String address;
    private String image;
    private int stars;
    private int availableRooms;
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "hotels")
    private Collection<User> managers = new ArrayList<>();


    @ManyToOne
    private City city;
}
