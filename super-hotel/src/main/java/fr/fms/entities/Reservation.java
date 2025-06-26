package fr.fms.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {
    private Long id;

    private LocalDate startDate;

    private LocalDate endDate;

    @ManyToOne
    private Hotel hotel;
}
