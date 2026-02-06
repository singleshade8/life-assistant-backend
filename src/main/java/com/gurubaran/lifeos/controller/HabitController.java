package com.gurubaran.lifeos.controller;

import com.gurubaran.lifeos.model.Habit;
import com.gurubaran.lifeos.model.HabitLog;
import com.gurubaran.lifeos.repository.HabitLogRepository;
import com.gurubaran.lifeos.repository.HabitRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/habits")
public class HabitController {

    private final HabitRepository habitRepo;
    private final HabitLogRepository logRepo;

    public HabitController(HabitRepository habitRepo, HabitLogRepository logRepo) {
        this.habitRepo = habitRepo;
        this.logRepo = logRepo;
    }

    // Create habit
    @PostMapping
    public Habit createHabit(@RequestBody Habit habit) {
        return habitRepo.save(habit);
    }

    // List habits
    @GetMapping
    public List<Habit> getHabits() {
        return habitRepo.findAll();
    }

    // Mark today's habit as done
    @PostMapping("/{habitId}/today/complete")
    public HabitLog completeToday(@PathVariable Long habitId) {
        Habit habit = habitRepo.findById(habitId).orElseThrow();
        LocalDate today = LocalDate.now();

        HabitLog log = logRepo.findByHabitIdAndDate(habitId, today)
                .orElseGet(() -> {
                    HabitLog l = new HabitLog();
                    l.setHabit(habit);
                    l.setDate(today);
                    return l;
                });

        log.setCompleted(true);
        return logRepo.save(log);
    }

    // Get current streak
    @GetMapping("/{habitId}/streak")
    public int getStreak(@PathVariable Long habitId) {
        List<HabitLog> logs = logRepo.findByHabitIdOrderByDateDesc(habitId);

        int streak = 0;
        LocalDate current = LocalDate.now();

        for (HabitLog log : logs) {
            if (!log.getDate().equals(current) || !log.isCompleted()) {
                break;
            }
            streak++;
            current = current.minusDays(1);
        }
        return streak;
    }
}
