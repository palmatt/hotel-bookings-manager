package org.example.hotelbookingsmanager.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Hotel {
    private String id;
    private String name;
    private List<RoomType> roomTypes;
    private List<Room> rooms;
}
