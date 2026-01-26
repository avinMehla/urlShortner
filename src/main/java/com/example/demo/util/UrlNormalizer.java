package com.example.demo.util;

import java.net.URI;

public class UrlNormalizer {

    public static String normalize(String url) {
        try {
            url = url.trim();

            // Add protocol if missing
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "https://" + url;
            }

            URI uri = new URI(url);

            String scheme = uri.getScheme();
            String host = uri.getHost();
            String path = uri.getPath();

            if (host == null) {
                throw new IllegalArgumentException("Invalid URL");
            }

            // Remove trailing slash
            if (path != null && path.endsWith("/")) {
                path = path.substring(0, path.length() - 1);
            }

            // LinkedIn: remove query params
            if (host.contains("linkedin.com")) {
                return scheme + "://" + host + (path != null ? path : "");
            }

            // Default: keep query params for other sites
            return uri.normalize().toString();

        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid URL format");
        }
    }
}
