package com.paiondata.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SparkInfo {

    public static final String service = "Spark";

    private String appid;

    private String apiSecret;

    private String apiKey;

    private String inputPath;

    private String outputPath;
}
