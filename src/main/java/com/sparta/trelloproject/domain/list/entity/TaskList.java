package com.sparta.trelloproject.domain.list.entity;

import com.sparta.trelloproject.domain.board.entity.Board;
import com.sparta.trelloproject.domain.card.entity.Card;
import com.sparta.trelloproject.domain.list.dto.request.TaskListRequest;
import com.sparta.trelloproject.domain.list.dto.request.TaskListSaveRequest;
import com.sparta.trelloproject.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "lists")
public class TaskList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "list_index", nullable = false)
    private Long index;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public TaskList(TaskListSaveRequest request) {
        this.title = request.getTitle();
    }

    @OneToMany(mappedBy = "taskList", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Card> cards = new ArrayList<>();

    public TaskList(TaskListSaveRequest request, Board board, Long listIndex) {
        this.title = request.getTitle();
        this.board = board;
        this.index = listIndex;
    }

    public void updateList(TaskListRequest request) {
        if (request.getTitle() != null) {
            this.title = request.getTitle();
        }
    }

    public void changeListIndex(Board board, TaskList list, Long boardId, Long index, Long totalListIndex) {

        List<TaskList> lists = board.getLists();

        List<TaskList> sortedLists = lists.stream()
                .sorted(Comparator.comparing(TaskList::getIndex))
                .toList();

        if (index > list.getIndex()) {
            for (int i = 0; i < index; i++) {
                sortedLists.get(i).dcreaseIndex();
            }

        } else {
            for (int i = (int) (index + 1); i < lists.size(); i++) {
                sortedLists.get(i).increaseIndex();
            }
        }

        list.changeIndex(index);
    }


    public void dcreaseIndex() {
        this.index -= 1L;
    }

    public void increaseIndex() {
        this.index += 1L;
    }

    public void changeIndex(Long index) {
        this.index = index;
    }
}
