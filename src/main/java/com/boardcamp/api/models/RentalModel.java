package com.boardcamp.api.models;

import java.time.LocalDate;

import com.boardcamp.api.dtos.RentalDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rentals")
public class RentalModel {

    public RentalModel(RentalDTO dto, CustomerModel customer, GameModel game) {
        this.daysRented = dto.getDaysRented();
        this.customer = customer;
        this.game = game;

        this.rentDate = LocalDate.now().toString();
        this.originalPrice = game.getPricePerDay() * this.daysRented;
        this.returnDate = null;
        this.delayFee = 0;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String rentDate;

    @Column(nullable = false)
    private Integer daysRented;

    @Column
    private String returnDate;

    @Column(nullable = false)
    private Integer originalPrice;

    @Column(nullable = false)
    private Integer delayFee;

    @ManyToOne
    @JoinColumn(name = "customerId")
    private CustomerModel customer;

    @ManyToOne
    @JoinColumn(name = "gameId")
    private GameModel game;
}
