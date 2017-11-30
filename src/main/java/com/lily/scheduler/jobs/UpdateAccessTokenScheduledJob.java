package com.lily.scheduler.jobs;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.lily.accessToken.AccessTokenException;
import com.lily.accessToken.UpdateAccessToken;

public class UpdateAccessTokenScheduledJob extends QuartzJobBean {

	private static Logger logger = Logger.getLogger(UpdateAccessTokenScheduledJob.class);

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		System.out.println("AccessToken更新时间：" + new Date());
		try {
			UpdateAccessToken.updateAccessToken();
		} catch (AccessTokenException e) {
			logger.error("更新AccessToken发生错误");
			e.printStackTrace();
		}
	}

}
