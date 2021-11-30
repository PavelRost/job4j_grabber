package ru.job4j.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

public class AlertRabbit {
    public static void main(String[] args) {
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            JobDetail job = newJob(Rabbit.class).build();
            SimpleScheduleBuilder times = simpleSchedule()
                    .withIntervalInSeconds(getInterval())
                    .repeatForever();
            Trigger trigger = newTrigger()
                    .startNow()
                    .withSchedule(times)
                    .build();
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException se) {
            se.printStackTrace();
        }
    }

    public static class Rabbit implements Job {
        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            System.out.println("Rabbit runs here ...");
        }
    }

    private static int getInterval() {
        int rsl = -1;
        Path path = Paths.get("src/main/resources/rabbit.properties");
        if (path.toFile().length() == 0) {
            throw new IllegalArgumentException("Файл пустой");
        }
        try (BufferedReader br = new BufferedReader(new FileReader(String.valueOf(path)))) {
            String value = br.readLine();
            if (value.contains("=")
                    && !value.endsWith("=")
                    && !value.contains("==")) {
                String[] temp = value.split("=");
                rsl = Integer.parseInt(temp[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rsl;
    }
}
