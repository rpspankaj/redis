package com.project.redis.service;

import com.project.redis.model.CModel;

public interface CService {
	
	public boolean add(CModel model);
	public CModel getByKey( String key);
	public boolean publish(CModel model);

}
