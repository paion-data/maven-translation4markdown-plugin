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
public class GitChangesFiles {
    private List<String> addedFiles;
    private List<String> deletedFiles;
    private List<String> modifiedFiles;
}
