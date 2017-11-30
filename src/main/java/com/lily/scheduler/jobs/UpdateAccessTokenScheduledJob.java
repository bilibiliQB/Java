package com.lily.scheduler.jobs;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.lily.accessToken.AccessTokenException;
import com.lily.accessToken.UpdateAccessToken;

public class UpdateAccessTokenScheduledJob extends QuartzJobBean {

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		try {
			UpdateAccessToken.updateAccessToken();
			System.out.println("AccessToken更新时间：" + new Date());
		} catch (AccessTokenException e) {
			e.printStackTrace();
		}
	}

}
