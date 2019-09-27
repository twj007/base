package com.quartz.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.Trigger;

import java.util.Date;

/**
 * 
* @Description: 计划任务信息
* @author snailxr
* @date 2014年6月6日 下午10:49:43
 */
@Getter
@Setter
@ToString
public class ScheduleJob {

	private String name;
	private String group;
	private String description;
	private Class<? extends Job> jobClass;
	private JobDataMap jobDataMap;
	private boolean durability;
	private boolean shouldRecover;
	private transient JobKey key;
	private String cronExpression;
	private Date nextExecuteTime;
	private String state;
}