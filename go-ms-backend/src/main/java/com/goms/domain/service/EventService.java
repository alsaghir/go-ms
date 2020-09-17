package com.goms.domain.service;

public interface EventService {

    <T> void publish(Integer key, T message);

}
