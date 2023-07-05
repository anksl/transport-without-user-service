package com.transport.feign;

import com.transport.api.dto.UserDto;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "user-service", url = "http://localhost:8080/api/users")
public interface UsersServiceClient {
    @GetMapping("/current")
    @Headers("Content-Type: application/json")
    UserDto getCurrentUser();
}
