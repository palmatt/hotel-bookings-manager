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
public class RoomType {
    private String code;
    private String description;
    private List<String> amenities;
    private List<String> features;
}
