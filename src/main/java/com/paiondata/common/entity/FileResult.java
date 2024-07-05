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

/**
 * FileResult类扩展了BaseEntity，用于封装文件处理结果的详细信息，特别是与文件中的键（如文件名、ID等）相关的增删改情况.
 *
 * @field addedKeys  已添加的键列表，表示在处理过程中新增的内容项。
 * @field updatedKeys 已更新的键列表，表示修改过的内容项。
 * @field deletedKeys 已删除的键列表，表示从文件或集合中移除的内容项。
 *
 * @constructor FileResult 默认构造函数。
 * @constructor FileResult 初始化构造函数，接收addedKeys、updatedKeys和deletedKeys列表作为参数。
 *
 * @method getAddedKeys 获取新增键的列表。
 * @method setAddedKeys 设置新增键的列表。
 * @method getUpdatedKeys 获取更新键的列表。
 * @method setUpdatedKeys 设置更新键的列表。
 * @method getDeletedKeys 获取删除键的列表。
 * @method setDeletedKeys 设置删除键的列表。
 */
@Builder
public class FileResult extends BaseEntity {

    private List<String> addedKeys;

    private List<String> updatedKeys;

    private List<String> deletedKeys;

    /**
     * 默认构造函数.
     */
    public FileResult() {
    }

    /**
     * 构造函数.
     * @param addedKeys 添加的键
     * @param updatedKeys 更新的键
     * @param deletedKeys 删除的键
     */
    public FileResult(final List<String> addedKeys, final List<String> updatedKeys, final List<String> deletedKeys) {
        this.addedKeys = addedKeys;
        this.updatedKeys = updatedKeys;
        this.deletedKeys = deletedKeys;
    }

    /**
     * 获取新增键的列表.
     * @return 新增键的列表
     */
    public List<String> getAddedKeys() {
        return addedKeys;
    }

    /**
     * 设置新增键的列表.
     * @param addedKeys 新增键的列表
     */
    public void setAddedKeys(final List<String> addedKeys) {
        this.addedKeys = addedKeys;
    }

    /**
     * 获取更新键的列表.
     * @return 更新键的列表
     */
    public List<String> getUpdatedKeys() {
        return updatedKeys;
    }

    /**
     * 设置更新键的列表.
     * @param updatedKeys 更新键的列表
     */
    public void setUpdatedKeys(final List<String> updatedKeys) {
        this.updatedKeys = updatedKeys;
    }

    /**
     * 获取删除键的列表.
     * @return 删除键的列表
     */
    public List<String> getDeletedKeys() {
        return deletedKeys;
    }

    /**
     * 设置删除键的列表.
     * @param deletedKeys 删除键的列表
     */
    public void setDeletedKeys(final List<String> deletedKeys) {
        this.deletedKeys = deletedKeys;
    }
}
