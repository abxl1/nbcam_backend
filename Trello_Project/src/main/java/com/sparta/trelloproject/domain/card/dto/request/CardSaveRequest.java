package com.sparta.trelloproject.domain.card.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CardSaveRequest {

    @NotNull
    private String title;

    private String description;

    private LocalDateTime deadline;

    private Long assignUser;

}