package com.sparta.trelloproject.domain.list.dto.response;

import lombok.Getter;

@Getter
public class TaskListResponse {

    private final Long taskListId;
    private final String taskListTitle;
    private final Long taskListIndex;

    public TaskListResponse(Long listId, String listTitle, Long taskListIndex) {
        this.taskListTitle = listTitle;
        this.taskListId = listId;
        this.taskListIndex = taskListIndex;
    }
}
