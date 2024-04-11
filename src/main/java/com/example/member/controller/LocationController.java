package com.example.member.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.member.dto.LocationDTO;
import java.util.Map;

@RestController
public class LocationController {
    private static final Logger logger = LoggerFactory.getLogger(LocationController.class);

    @PostMapping("/location")
    public ResponseEntity<?> postLocation(@RequestBody LocationDTO locationDTO) {
        // 요청에서 받은 경도와 위도를 로그에 출력
        logger.info("Received location: Longitude = {}, Latitude = {}", locationDTO.getLongitude(), locationDTO.getLatitude());

        return null;
    }
}
