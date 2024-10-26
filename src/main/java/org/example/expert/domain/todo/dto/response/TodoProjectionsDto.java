package org.example.expert.domain.todo.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TodoProjectionsDto {
    private final String title;
    private final Long managerCount;
    private final Long commentCount;
}