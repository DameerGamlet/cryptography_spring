package com.example.cryptography.domain;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Caesar {
    private String text;
    private String key;
}
