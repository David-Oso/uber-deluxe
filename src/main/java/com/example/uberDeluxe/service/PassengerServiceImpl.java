package com.example.uberDeluxe.service;

import com.example.uberDeluxe.cloud.CloudService;
import com.example.uberDeluxe.data.dto.request.BookRideRequest;
import com.example.uberDeluxe.data.dto.request.RegisterPassengerRequest;
import com.example.uberDeluxe.data.dto.response.ApiResponse;
import com.example.uberDeluxe.data.dto.response.RegisterResponse;
import com.example.uberDeluxe.data.models.AppUser;
import com.example.uberDeluxe.data.models.Passenger;
import com.example.uberDeluxe.data.repositories.PassengerRepository;
import com.example.uberDeluxe.exception.BusinessLogicException;
import com.example.uberDeluxe.mapper.ParaMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.example.uberDeluxe.utils.AppUtilities.NUMBER_OF_ITEMS_PER_PAGE;

@Service
@AllArgsConstructor
@Slf4j
public class PassengerServiceImpl implements  PassengerService{
    private final PassengerRepository passengerRepository;
    private final CloudService cloudService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public RegisterResponse register(RegisterPassengerRequest registerRequest) {
        AppUser appUser = ParaMapper.map(registerRequest);
        appUser.setCreatedAt(LocalDateTime.now().toString());
        Passenger passenger = new Passenger();
        passenger.setUserDetails(appUser);
        Passenger savedPassenger = passengerRepository.save(passenger);
        RegisterResponse registerResponse = getRegisterResponse(savedPassenger);
        return registerResponse;
    }

    private RegisterResponse getRegisterResponse(Passenger savedPassenger) {
        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.setId(savedPassenger.getId());
         registerResponse.setSuccess(true);
        registerResponse.setMessage("User Registration Successful");
        return registerResponse;
    }

    @Override
    public Passenger getPassengerById(Long passengerId) {
        return passengerRepository.findById(passengerId).orElseThrow(()->
                new BusinessLogicException(
                        String.format("Passenger with id %d not found", passengerId)));
    }

    @Override
    public void savedPassenger(Passenger passenger) {
        passengerRepository.save(passenger);
    }

    @Override
    public Optional<Passenger> getPassengerBy(Long userid) {
        return passengerRepository.findById(userid);
    }

    @Override
    public Passenger updatePassenger(Long passengerId, JsonPatch updatePayload) {
        ObjectMapper mapper = new ObjectMapper();
        Passenger foundPassenger = getPassengerById(passengerId);

        JsonNode node = mapper.convertValue(foundPassenger, JsonNode.class);
        try{
            JsonNode updatedNode = updatePayload.apply(node);
            var updatePassenger = mapper.convertValue(updatedNode, Passenger.class);
            updatePassenger = passengerRepository.save(updatePassenger);
            return updatePassenger;
        }catch (JsonPatchException e){
            log.error(e.getMessage());
            throw new RuntimeException();
        }
    }

    @Override
    public Page<Passenger> getAllPassenger(int pageNumber) {
        if(pageNumber < 1)pageNumber = 0;
        else pageNumber -= 1;
        Pageable pageable = PageRequest.of(pageNumber, NUMBER_OF_ITEMS_PER_PAGE);
        return passengerRepository.findAll(pageable);
    }

    @Override
    public void deletePassenger(Long id) {
        passengerRepository.deleteById(id);
    }

    @Override
    public ApiResponse bookRide(BookRideRequest bookRideRequest) {
        return null;
    }
}
