package com.project.redis.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.redis.model.CModel;
import com.project.redis.queue.RedisMessagePublisher;
import com.project.redis.repo.RedisRepository;
import com.project.redis.service.CService;

@Service
public class CServiceImpl implements  CService{
	Logger logger = LoggerFactory.getLogger(CServiceImpl.class);
	@Autowired
	private RedisRepository repo;
	
	@Autowired
	private RedisMessagePublisher publisher;
	
	@Override
	public boolean add(CModel model) {
		try {
			repo.add("employ", String.valueOf(model.getId()), model);
			return true;
			
		}catch (Exception e) {
			logger.error("Exception ",e);
		}
		return false;
		
	}

	@Override
	public CModel getByKey( String  key) {
		
		return (CModel) repo.findByKey("employ",key);
	}

	@Override
	public boolean publish(CModel model) {
		
		try {
			publisher.publish(model);
			return true;
			
		}catch (Exception e) {
			logger.error("Exception ",e);
		}
		return false;
	}

}
