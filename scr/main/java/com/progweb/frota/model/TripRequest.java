package com.progweb.frota.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class TripRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User driver;

    @ManyToOne
    private Vehicle vehicle;

    
    public enum TripStatus {
        PENDENTE,
        ACEITA,
        REJEITADA,
        CONCLUIDA
    }


    private TripStatus status;
    private LocalDateTime requestTime;
    private LocalDateTime tripStartTime;
    private LocalDateTime tripEndTime;
}
