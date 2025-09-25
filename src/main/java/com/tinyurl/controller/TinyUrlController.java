package com.tinyurl.controller;

import com.tinyurl.dto.UrlRequest;
import com.tinyurl.dto.UrlResponse;
import com.tinyurl.model.Url;
import com.tinyurl.services.TinyUrlService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class TinyUrlController {

    private final TinyUrlService tinyUrlService;

    public TinyUrlController(TinyUrlService tinyUrlService) {
        this.tinyUrlService = tinyUrlService;
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirectToLongUrl(@PathVariable String shortCode) {
        Url url = tinyUrlService.findByShortCode(shortCode);
        if (url == null) {
            return ResponseEntity.notFound().build();
        }
        // Redirect to the long URL
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                .header("Location", url.getLongUrl())
                .build();
    }

    @PostMapping("/shorten")
    public ResponseEntity<UrlResponse> createShortUrl(@RequestBody UrlRequest urlRequest) {
        Url existingUrl = tinyUrlService.findByLongUrl(urlRequest.getLongUrl());
        if (existingUrl != null) {
            UrlResponse urlResponse = convertToResponse(existingUrl);
            return ResponseEntity.ok(urlResponse);
        }
        Url url = tinyUrlService.createShortUrl(urlRequest.getLongUrl());
        UrlResponse urlResponse = convertToResponse(url);
        return ResponseEntity.status(HttpStatus.CREATED).body(urlResponse);
    }

    private UrlResponse convertToResponse(Url url) {
        return new UrlResponse(
            url.getLongUrl(),
            url.getShortCode(),
            url.getCreatedAt(),
            url.getExpiresAt(),
            url.getClicks()
        );
    }
}
