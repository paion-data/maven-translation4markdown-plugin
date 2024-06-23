package com.paiondata.common.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileResult {

    private List<String> addedKeys;

    private List<String> updatedKeys;

    private List<String> deletedKeys;
}
