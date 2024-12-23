package com.easybank.cards.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@Setter
@Getter
@ConfigurationProperties(prefix = "cards")
public class CardsContactInfoDto {

    private String message;
    private Map<String, String> contactsDetails;
    private List<String> onCallSupport;
}