package com.petproject.monitoring.scheduler;

import com.petproject.monitoring.scheduler.service.IScheduleService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UpdateTweetsScheduler {

    private IScheduleService scheduleService;

    // scheduler run every 30 min
    @Scheduled(cron = "${scheduler.cron.updater.twitter}")
    public void updateTweetsByAPIScheduler() {
        scheduleService.checkForNewTweets();
    }
}
