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

import java.io.Serializable;

/**
 * BaseEntity是一个基础实体类，实现了Serializable接口，用于序列化操作，是项目中所有实体类的基类.
 *
 * @implSpec 实现了`Serializable`接口，要求子类也遵循序列化规则，以便于对象在网络传输和持久化存储。
 * @field serialVersionUID 提供序列化版本控制，避免反序列化时的不兼容问题，默认值为1L。
 */
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
}
