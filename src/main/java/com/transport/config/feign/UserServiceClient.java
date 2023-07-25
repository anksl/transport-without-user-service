package com.transport.config.feign;

import com.transport.api.dto.UserDto;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "user-service")
public interface UserServiceClient {

    @GetMapping(value = "/api/users/current", produces = MediaType.APPLICATION_JSON_VALUE)
    UserDto getCurrentUser();
}
