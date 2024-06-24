/*
 * Copyright 2024 Paion Data
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.paiondata.common.entity;

import lombok.Builder;

import java.util.List;

@Builder
public class FileResult extends BaseEntity {

    private List<String> addedKeys;

    private List<String> updatedKeys;

    private List<String> deletedKeys;

    public FileResult() {
    }

    public FileResult(List<String> addedKeys, List<String> updatedKeys, List<String> deletedKeys) {
        this.addedKeys = addedKeys;
        this.updatedKeys = updatedKeys;
        this.deletedKeys = deletedKeys;
    }

    public List<String> getAddedKeys() {
        return addedKeys;
    }

    public void setAddedKeys(List<String> addedKeys) {
        this.addedKeys = addedKeys;
    }

    public List<String> getUpdatedKeys() {
        return updatedKeys;
    }

    public void setUpdatedKeys(List<String> updatedKeys) {
        this.updatedKeys = updatedKeys;
    }

    public List<String> getDeletedKeys() {
        return deletedKeys;
    }

    public void setDeletedKeys(List<String> deletedKeys) {
        this.deletedKeys = deletedKeys;
    }
}
