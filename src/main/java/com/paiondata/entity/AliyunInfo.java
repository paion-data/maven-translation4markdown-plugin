package com.paiondata.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AliyunInfo {

    public static final String service = "Aliyun";

    private String apiKey;

    private String targetLanguage;

    private Boolean mode;
}
