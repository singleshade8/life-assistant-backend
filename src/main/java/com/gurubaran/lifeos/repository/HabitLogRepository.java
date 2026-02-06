package com.gurubaran.lifeos.repository;

import com.gurubaran.lifeos.model.HabitLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;





public interface HabitLogRepository extends JpaRepository<HabitLog, Long> {
    Optional<HabitLog> findByHabitIdAndDate(Long habitId, LocalDate date);
    List<HabitLog> findByHabitIdOrderByDateDesc(Long habitId);
    List<HabitLog> findByDateBetweenAndCompletedTrue(LocalDate start, LocalDate end);
}
