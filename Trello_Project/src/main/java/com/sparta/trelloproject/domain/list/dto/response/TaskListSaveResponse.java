package com.sparta.trelloproject.domain.list.dto.response;

import com.sparta.trelloproject.domain.list.entity.TaskList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TaskListSaveResponse {

    private Long id;
    private final String message = "리스트가 성공적으로 생성됐습니다.";

    public TaskListSaveResponse(TaskList taskList) {
        this.id = taskList.getId();
    }

}
