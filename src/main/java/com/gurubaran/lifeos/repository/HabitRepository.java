package com.gurubaran.lifeos.repository;

import com.gurubaran.lifeos.model.Habit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitRepository extends JpaRepository<Habit, Long> {
}
