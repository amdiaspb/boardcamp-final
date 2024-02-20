package com.boardcamp.api.services;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.boardcamp.api.dtos.RentalDTO;
import com.boardcamp.api.exceptions.CustomerNotFoundException;
import com.boardcamp.api.exceptions.GameNotFoundException;
import com.boardcamp.api.exceptions.RentalLimitUnprocessableEntityException;
import com.boardcamp.api.exceptions.RentalNotFoundException;
import com.boardcamp.api.exceptions.RentalReturnedUnprocessableEntityException;
import com.boardcamp.api.models.CustomerModel;
import com.boardcamp.api.models.GameModel;
import com.boardcamp.api.models.RentalModel;
import com.boardcamp.api.repositories.CustomerRepository;
import com.boardcamp.api.repositories.GameRepository;
import com.boardcamp.api.repositories.RentalRepository;

@Service
public class RentalService {

    final RentalRepository rentalRepository;
    final CustomerRepository customerRepository;
    final GameRepository gameRepository;

    RentalService(RentalRepository rentalRepository, CustomerRepository customerRepository, GameRepository gameRepository) {
        this.rentalRepository = rentalRepository;
        this.customerRepository = customerRepository;
        this.gameRepository = gameRepository;
    }

    public RentalModel save(RentalDTO dto) {
        CustomerModel customer = customerRepository.findById(dto.getCustomerId()).orElseThrow(() -> new CustomerNotFoundException("Customer not found!"));
        GameModel game = gameRepository.findById(dto.getGameId()).orElseThrow(() -> new GameNotFoundException("Game not found!"));
        Integer rentalCount = rentalRepository.rentalCountByGameId(game.getId()) != null ? rentalRepository.rentalCountByGameId(game.getId()) : 0;

        if (rentalCount >= game.getStockTotal()) {
            throw new RentalLimitUnprocessableEntityException("Game is not in stock!");
        }

        RentalModel rental = new RentalModel(dto, customer, game);
        return rentalRepository.save(rental);
    }

    public Optional<RentalModel> findById(Long id) {
        return rentalRepository.findById(id);
    }

    public List<RentalModel> findAll() {
        return rentalRepository.findAll();
    }

    public RentalModel update(Long id) {
        RentalModel rental = rentalRepository.findById(id).orElseThrow(() -> new RentalNotFoundException("Rental not found!"));
        if (rental.getReturnDate() != null) {
            throw new RentalReturnedUnprocessableEntityException("Rental already returned!");
        }

        LocalDate returnDate = LocalDate.now();
        rental.setReturnDate(returnDate.toString());

        LocalDate rentDate = LocalDate.parse(rental.getRentDate());
        Long daysBetween = ChronoUnit.DAYS.between(rentDate, returnDate);
        
        if (daysBetween > rental.getDaysRented()) {
            Integer daysDiff = Math.toIntExact(daysBetween) - rental.getDaysRented();
            rental.setDelayFee(rental.getGame().getPricePerDay() * daysDiff);
        }

        return rentalRepository.save(rental);
    }
}
