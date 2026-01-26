package com.example.demo.controller;

import com.example.demo.exception.BadRequestException;
import com.example.demo.service.UrlService;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping
public class UrlController {
    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }
    @PostMapping("/shorten")
    public String shortenUrl(@RequestBody @NonNull Map<String, String> body) {
        String originalUrl = body.get("url");
        if (originalUrl == null || originalUrl.isBlank()) {
            throw new BadRequestException("URL must not be empty");
        }
        return urlService.createAndSaveUrl(originalUrl);
    }

    @GetMapping("/{shortKey}")
    public void redirect(
            @PathVariable String shortKey,
            HttpServletResponse response) {

        String originalUrl = urlService.getBaseUrl(shortKey);
        response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
        response.setHeader("Location", originalUrl);
    }



}
