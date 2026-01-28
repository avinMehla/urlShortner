package com.example.demo.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data   // lombok handles getters and setters
@Document(collection = "urls")
public class UrlEntity {

    @Id
    private String id;

    private String   baseUrl;
    private String newUrl;
    private Integer monthCount;
    private Integer dayCount;
    private LocalDateTime recentTimeStamp;
}
