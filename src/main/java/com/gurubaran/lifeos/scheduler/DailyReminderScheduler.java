package com.gurubaran.lifeos.scheduler;

import com.gurubaran.lifeos.repository.HabitLogRepository;
import com.gurubaran.lifeos.repository.HabitRepository;
import com.gurubaran.lifeos.model.Habit;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DailyReminderScheduler {

    private final HabitRepository habitRepo;
    private final HabitLogRepository logRepo;

    public DailyReminderScheduler(HabitRepository habitRepo, HabitLogRepository logRepo) {
        this.habitRepo = habitRepo;
        this.logRepo = logRepo;
    }

    // Runs every day at 9:00 AM (server time)
    @Scheduled(cron = "0 0 9 * * ?")
    public void remindIncompleteHabits() {
        LocalDate today = LocalDate.now();
        List<Habit> habits = habitRepo.findAll();

        habits.forEach(habit -> {
            boolean doneToday = logRepo.findByHabitIdAndDate(habit.getId(), today)
                    .map(log -> log.isCompleted())
                    .orElse(false);

            if (!doneToday) {
                System.out.println("🔔 Reminder: You have not completed habit today → " + habit.getName());
            }
        });
    }
}
