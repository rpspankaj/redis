package com.project.redis.queue;

import com.project.redis.model.CModel;

public interface MessagePublisher {

    void publish(final CModel message);
}
