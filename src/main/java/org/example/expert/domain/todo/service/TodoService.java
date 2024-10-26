package org.example.expert.domain.todo.service;

import lombok.RequiredArgsConstructor;
import org.example.expert.client.WeatherClient;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.todo.dto.request.TodoSaveRequest;
import org.example.expert.domain.todo.dto.response.TodoProjectionsDto;
import org.example.expert.domain.todo.dto.response.TodoResponse;
import org.example.expert.domain.todo.dto.response.TodoSaveResponse;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.todo.repository.TodoRepository;
import org.example.expert.domain.user.dto.response.UserResponse;
import org.example.expert.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoService {

    private final TodoRepository todoRepository;
    private final WeatherClient weatherClient;

    @Transactional
    public TodoSaveResponse saveTodo(AuthUser authUser,
                                     TodoSaveRequest todoSaveRequest) {
        User user = User.fromAuthUser(authUser);

        String weather = weatherClient.getTodayWeather();

        Todo newTodo = new Todo(
                user.getNickname(),
                todoSaveRequest.getTitle(),
                todoSaveRequest.getContents(),
                weather,
                user
        );
        Todo savedTodo = todoRepository.save(newTodo);

        return new TodoSaveResponse(
                savedTodo.getId(),
                savedTodo.getTitle(),
                savedTodo.getContents(),
                weather,
                new UserResponse(
                        user.getId(),
                        user.getEmail(),
                        user.getNickname())
        );
    }

    public Page<TodoResponse> getTodos(int page,
                                       int size,
                                       String weather,
                                       LocalDateTime startDate,
                                       LocalDateTime endDate) {
        Pageable pageable = PageRequest.of(page - 1, size);

        Page<Todo> todos = todoRepository.
                findTodoByWeatherAndModifiedAt(
                        weather,
                        startDate,
                        endDate,
                        pageable);

        return todos.map(todo -> new TodoResponse(
                todo.getId(),
                todo.getTitle(),
                todo.getContents(),
                todo.getWeather(),
                new UserResponse(
                        todo.getUser().getId(),
                        todo.getUser().getEmail(),
                        todo.getUser().getNickname()
                ),
                todo.getCreatedAt(),
                todo.getModifiedAt()
        ));
    }

    public Page<TodoProjectionsDto> getTodosByQueryDSL(int page,
                                                 int size,
                                                 String title,
                                                 LocalDate startTime,
                                                 LocalDate endTime,
                                                 String nickname) {

        LocalDateTime startDate = startTime == null ? null : startTime.atStartOfDay();
        LocalDateTime endDate = endTime == null ? null : endTime.atTime(23, 59, 59);

        Pageable pageable = PageRequest.of(page - 1, size);

        Page<TodoProjectionsDto> todos = todoRepository
                .findByTitleAndCreatedAtAndNickname(
                        title,
                        startDate,
                        endDate,
                        nickname,
                        pageable
                );

        return todos;
    }

    public TodoResponse getTodo(long todoId) {
        Todo todo = todoRepository.findByIdByDsl(todoId);
        User user = todo.getUser();

        return new TodoResponse(
                todo.getId(),
                todo.getTitle(),
                todo.getContents(),
                todo.getWeather(),
                new UserResponse(
                        user.getId(),
                        user.getEmail(),
                        user.getNickname()
                ),
                todo.getCreatedAt(),
                todo.getModifiedAt()
        );
    }
}
