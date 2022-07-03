package com.project.redis.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.redis.model.CModel;
import com.project.redis.service.CService;

@RestController
@RequestMapping
public class CustomApi {

	@Autowired
	private CService service;
	
	@PostMapping
	public boolean add(@RequestBody CModel cModel) {
		return service.add(cModel);
	}
	
	@GetMapping(path = "{key}")
	public CModel getUseById(@PathVariable("key") String key) {
		return service.getByKey( key);
	}

	@GetMapping
	public String test() {
		return "Server is UP !!!";
	}
	
	@PostMapping("pubsub")
	public boolean pubsub(@RequestBody CModel cModel) {
		return service.publish(cModel);
	}
}