package com.tinyurl.services;

import com.tinyurl.config.UrlConfig;
import com.tinyurl.model.Url;
import com.tinyurl.repository.TinyUrlRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;

@Service
public class TinyUrlService {

    private final TinyUrlRepository tinyUrlRepository;
    private final UrlConfig urlConfig;

    public TinyUrlService(TinyUrlRepository tinyUrlRepository, UrlConfig urlConfig) {
        this.tinyUrlRepository = tinyUrlRepository;
        this.urlConfig = urlConfig;
    }

    public Url createShortUrl(String longUrl) {
        String shortCode;
        do {
            shortCode = generateShortCode();
        } while (tinyUrlRepository.findByShortCode(shortCode).isPresent());

        Url url = new Url();
        url.setLongUrl(longUrl);
        url.setShortCode(shortCode);
        url.setClicks(0L);
        return tinyUrlRepository.save(url);
    }

    @Transactional
    public Url findByShortCode(String shortCode) {
        Optional<Url> urlOptional = tinyUrlRepository.findByShortCode(shortCode);
        return urlOptional.orElse(null);
    }

    @Transactional
    public Url findByLongUrl(String longUrl) {
        Optional<Url> urlOptional = tinyUrlRepository.findByLongUrl(longUrl);
        return urlOptional.orElse(null);
    }

    private String generateShortCode() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder shortCode = new StringBuilder();

        int length = urlConfig.getShortUrlLength();
        for (int i = 0; i < length; i++) {
            shortCode.append(chars.charAt(random.nextInt(chars.length())));
        }

        return shortCode.toString();
    }
}
