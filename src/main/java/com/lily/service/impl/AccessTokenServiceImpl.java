package com.lily.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lily.mapper.AccessTokenMapper;
import com.lily.pojo.AccessToken;
import com.lily.service.AccessTokenService;

@Service
public class AccessTokenServiceImpl implements AccessTokenService {
	
	@Autowired
	private AccessTokenMapper accessTokenMapper;

	@Override
	public String getAccessToken() {
		AccessToken at = accessTokenMapper.getAccessToken();
		if(at != null){
			return at.getAccess_token();
		}
		return null;
	}

	@Override
	public int updateAccessToken(String access_token) {
		AccessToken at = new AccessToken(access_token);
		return accessTokenMapper.updateAccessToken(at);
	}

}
