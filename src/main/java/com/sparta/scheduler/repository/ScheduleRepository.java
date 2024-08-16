package com.sparta.scheduler.repository;

import com.sparta.scheduler.dto.ScheduleRequestDto;
import com.sparta.scheduler.dto.ScheduleResponseDto;
import com.sparta.scheduler.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Schedule save(Schedule schedule) {
        // DB 저장
        KeyHolder keyHolder = new GeneratedKeyHolder(); // 기본 키를 반환받기 위한 객체

        String sql = "INSERT INTO schedule (schedule, username, password, created_at, updated_at) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(con -> {
                    PreparedStatement preparedStatement = con.prepareStatement(sql,
                            Statement.RETURN_GENERATED_KEYS);

                    preparedStatement.setString(1, schedule.getSchedule());
                    preparedStatement.setString(2, schedule.getUsername());
                    preparedStatement.setString(3, schedule.getPassword());
                    preparedStatement.setString(4, schedule.getCreated_at());
                    preparedStatement.setString(5, schedule.getUpdated_at());

                    return preparedStatement;
                },
                keyHolder);

        // DB Insert 후 받아온 기본키 확인
        Long id = keyHolder.getKey().longValue();
        schedule.setId(id);

        return schedule;
    }

    public List<ScheduleResponseDto> findAll() {

        // DB 조회
        String sql = "SELECT * FROM schedule";

        return jdbcTemplate.query(sql, new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                // SQL 의 결과로 받아온 Memo 데이터들을 MemoResponseDto 타입으로 변환해줄 메서드
                Long id = rs.getLong("id");
                String username = rs.getString("username");
                String schedule = rs.getString("schedule");
                String created_at = rs.getString("created_at");
                String updated_at = rs.getString("updated_at");
                return new ScheduleResponseDto(id, username, schedule, created_at, updated_at);
            }
        });
    }

    public void update(Long id, ScheduleRequestDto requestDto) {
        String sql = "UPDATE schedule " +
                "SET username = ?, schedule = ?, updated_at = ? " +
                "WHERE id = ?";
        jdbcTemplate.update(
                sql,
                requestDto.getSchedule(),
                requestDto.getUsername(),
                requestDto.getUpdated_at(),
                id
        );
    }

    public void delete(Long id) {
        String sql = "DELETE FROM schedule WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public Schedule findById(Long id) {
        // DB 조회
        String sql = "SELECT * FROM schedule WHERE id = ?";

        return jdbcTemplate.query(sql, resultSet -> {
            if (resultSet.next()) {
                Schedule schedule = new Schedule();
                schedule.setSchedule(resultSet.getString("schedule"));
                schedule.setUsername(resultSet.getString("username"));
                schedule.setCreated_at(resultSet.getString("created_at"));
                schedule.setUpdated_at(resultSet.getString("updated_at"));
                return schedule;
            } else {
                return null;
            }
        }, id);
    }
}
