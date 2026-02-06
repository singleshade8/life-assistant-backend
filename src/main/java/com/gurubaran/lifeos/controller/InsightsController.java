package com.gurubaran.lifeos.controller;

import com.gurubaran.lifeos.repository.HabitLogRepository;
import com.gurubaran.lifeos.repository.HabitRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/insights")
public class InsightsController {

    private final HabitLogRepository logRepo;
    private final HabitRepository habitRepo;

    public InsightsController(HabitLogRepository logRepo, HabitRepository habitRepo) {
        this.logRepo = logRepo;
        this.habitRepo = habitRepo;
    }

    @GetMapping("/habits/weekly/breakdown")
    public Map<String, Object> weeklyHabitBreakdown() {
        LocalDate end = LocalDate.now();
        LocalDate start = end.minusDays(6);

        var logs = logRepo.findByDateBetweenAndCompletedTrue(start, end);

        Map<String, Integer> byHabit = new HashMap<>();
        logs.forEach(log -> {
            String name = log.getHabit().getName();
            byHabit.put(name, byHabit.getOrDefault(name, 0) + 1);
        });

        Map<String, Object> result = new HashMap<>();
        result.put("startDate", start);
        result.put("endDate", end);
        result.put("byHabit", byHabit);

        return result;
    }

    @GetMapping("/habits/weekly/summary")
    public Map<String, Object> weeklyHabitSummary() {
        LocalDate end = LocalDate.now();
        LocalDate start = end.minusDays(6);

        var logs = logRepo.findByDateBetweenAndCompletedTrue(start, end);
        var habits = habitRepo.findAll();

        Map<Long, Integer> countsByHabitId = new HashMap<>();
        logs.forEach(log -> {
            Long habitId = log.getHabit().getId();
            countsByHabitId.put(habitId, countsByHabitId.getOrDefault(habitId, 0) + 1);
        });

        List<Map<String, Object>> summary = new ArrayList<>();
        String bestHabit = null;
        int bestScore = -1;

        for (var habit : habits) {
            int completedDays = countsByHabitId.getOrDefault(habit.getId(), 0);
            int consistency = (int) Math.round((completedDays / 7.0) * 100);

            Map<String, Object> item = new HashMap<>();
            item.put("habit", habit.getName());
            item.put("completedDays", completedDays);
            item.put("consistency", consistency);

            summary.add(item);

            if (consistency > bestScore) {
                bestScore = consistency;
                bestHabit = habit.getName();
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("startDate", start);
        result.put("endDate", end);
        result.put("summary", summary);
        result.put("bestHabit", bestHabit);

        return result;
    }
}
