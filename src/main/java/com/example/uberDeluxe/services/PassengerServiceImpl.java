package com.example.uberDeluxe.services;

import com.example.uberDeluxe.data.dto.request.RegisterPassengerRequest;
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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
@Slf4j
public class PassengerServiceImpl implements PassengerService{
    private final PassengerRepository passengerRepository;
    private static final int NUMBER_OF_ITEMS_PER_PAGE = 3;

    @Override
    public RegisterResponse register(RegisterPassengerRequest registerRequest) {
        AppUser appUser = ParaMapper.map(registerRequest);
        appUser.setCreatedAt(LocalDate.now().toString());
        Passenger passenger = new Passenger();
        passenger.setUserDetails(appUser);
        Passenger savedPassenger = passengerRepository.save(passenger);
        RegisterResponse registerResponse = getRegisterResponse(savedPassenger);
        return registerResponse;
    }

    @Override
    public Passenger getPassengerById(Long passengerId) {
        return passengerRepository.findById(passengerId).orElseThrow(()->
                new BusinessLogicException(
                        String.format("Passenger with id %d not found", passengerId)));
    }

    @Override
    public Passenger updatePassenger(Long passengerId, JsonPatch updatePayload) {
        ObjectMapper mapper = new ObjectMapper();
        Passenger foundPassenger = getPassengerById(passengerId);
//        Passenger object to node
        JsonNode node  = mapper.convertValue(foundPassenger, JsonNode.class);
        try{
//            apply patch
            JsonNode updatedNode = updatePayload.apply(node);
//            node to Passenger object
            var updatePassenger = mapper.convertValue(updatedNode, Passenger.class);
            updatePassenger = passengerRepository.save(updatePassenger);
            return updatePassenger;
        }catch (JsonPatchException exception){
            log.error(exception.getMessage());
            throw  new RuntimeException();
        }
    }

    @Override
    public Page<Passenger> getAllPassenger(int pageNumber) {
        if(pageNumber < 1)pageNumber = 0;
        else pageNumber -= 1;
        Pageable pageable = PageRequest.of(pageNumber, NUMBER_OF_ITEMS_PER_PAGE);
        return  passengerRepository.findAll(pageable);
    }

    @Override
    public void deletePassenger(Long id) {
        passengerRepository.deleteById(id);
    }
    private static RegisterResponse getRegisterResponse(Passenger savedPassenger){
        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.setId(savedPassenger.getId());
        registerResponse.setCode(HttpStatus.CREATED.value());
        registerResponse.setSuccess(true);
        registerResponse.setMessage("User Registration Successful");
        return  registerResponse;
    }
}
