package com.example.demo.service;

import com.example.demo.entities.UrlEntity;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.UrlRepo;
import com.example.demo.util.HashUtil;
import com.example.demo.util.UrlNormalizer;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UrlService {

    private final UrlRepo repo;

    public UrlService(UrlRepo repo) {
        this.repo = repo;
    }

    // STEP 1: Create & Save Short URL
    public String createAndSaveUrl(String baseUrl) {

        String normalizedUrl = UrlNormalizer.normalize(baseUrl);

        Optional<UrlEntity> existing = repo.findByBaseUrl(normalizedUrl);
        if (existing.isPresent()) {
            return existing.get().getNewUrl();
        }

        long uniqueNumber = System.currentTimeMillis();
        String newUrl = HashUtil.generateShortKey(uniqueNumber);

        UrlEntity entity = new UrlEntity();
        entity.setBaseUrl(normalizedUrl);
        entity.setNewUrl(newUrl);
        entity.setDayCount(0);
        entity.setMonthCount(0);
        entity.setRecentTimeStamp(LocalDateTime.now());

        repo.save(entity);
        return newUrl;
    }
    // STEP 2: Redirect logic
    public String getBaseUrl(String newUrl) {

        UrlEntity entity = repo.findByNewUrl(newUrl)
                .orElseThrow(() -> new NotFoundException("URL not found"));

        entity.setRecentTimeStamp(LocalDateTime.now());
        entity.setDayCount(entity.getDayCount() + 1);
        entity.setMonthCount(entity.getMonthCount() + 1);

        repo.save(entity);

        return entity.getBaseUrl();
    }
}
