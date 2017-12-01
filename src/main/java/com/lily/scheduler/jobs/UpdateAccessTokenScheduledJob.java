package com.lily.scheduler.jobs;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.lily.accessToken.UpdateAccessToken;

public class UpdateAccessTokenScheduledJob implements Job {

	private static Logger logger = Logger.getLogger(UpdateAccessTokenScheduledJob.class);

	private UpdateAccessToken updateAccessToken = UpdateAccessToken.getUpdateAccessToken();

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			updateAccessToken.execute();
			logger.info("AccessToken更新时间：" + new Date());
		} catch (Exception e) {
			logger.error("AccessToken获取错误");
			e.printStackTrace();
		}

	}

}
