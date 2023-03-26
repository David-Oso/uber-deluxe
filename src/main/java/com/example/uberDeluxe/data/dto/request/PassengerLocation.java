package com.example.uberDeluxe.data.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PassengerLocation {
    private String houseNumber;
    private String street;
    private String city;
}
