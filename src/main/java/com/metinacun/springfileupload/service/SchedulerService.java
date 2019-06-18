package com.metinacun.springfileupload.service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Service;

import com.metinacun.springfileupload.utility.Constants;

@Service
public class SchedulerService implements SchedulingConfigurer{
	
	@Autowired
	FileManagmentService fileManagmentService;
	
	@Bean
	public TaskScheduler poolScheduler() {
		ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
		scheduler.setThreadNamePrefix("TestScheduler");
		scheduler.setPoolSize(1);
		scheduler.initialize();
		return scheduler;
	}
	
	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		
		taskRegistrar.setScheduler(poolScheduler());
		taskRegistrar.addTriggerTask(() -> fileManagmentService.loadEmployees(), t->{
			Calendar nextExecutionTime = new GregorianCalendar();
			Date lastActualExecutionTime = t.lastActualExecutionTime();
			nextExecutionTime.setTime(lastActualExecutionTime != null ? lastActualExecutionTime : new Date());
			nextExecutionTime.add(Calendar.MINUTE, Constants.SCHEDULER_TIME_AMOUNT);
			return nextExecutionTime.getTime();
		});
	}

}
