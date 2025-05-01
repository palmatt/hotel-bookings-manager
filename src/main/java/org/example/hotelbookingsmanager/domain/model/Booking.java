package org.example.hotelbookingsmanager.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Booking {
    private String hotelId;
    private LocalDate arrival;
    private LocalDate departure;
    private String roomType;
    private String roomRate;
}
