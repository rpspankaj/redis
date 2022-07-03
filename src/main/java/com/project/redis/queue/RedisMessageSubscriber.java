package com.project.redis.queue;


import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import com.project.redis.model.CModel;
import com.project.redis.util.RedisUtility;

@Service
public class RedisMessageSubscriber implements MessageListener {


    public void onMessage(final Message message, final byte[] pattern) {
    	CModel cmodel = null;
    	try {
    		cmodel =(CModel)RedisUtility.deserialize(message.getBody());
    		 System.out.println("Message received: " + cmodel);
    	}catch(Exception e) {
    		 System.out.println(e);
    	}
       
    }
    
   
}