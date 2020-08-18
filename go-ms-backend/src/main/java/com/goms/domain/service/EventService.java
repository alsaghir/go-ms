package com.goms.domain.service;

import com.goms.application.service.data.UserDetailsData;

public interface EventService {

    <T> void publish(Integer key, T message);

}
