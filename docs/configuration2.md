---
sidebar_position: 4
title: Configuration

---

[//]: # "Copyright Paion Data"

[//]: # "Licensed under the Apache License, Version 2.0 &#40;the "License"&#41;;"
[//]: # "you may not use this file except in compliance with the License."
[//]: # "You may obtain a copy of the License at"

[//]: # "    http://www.apache.org/licenses/LICENSE-2.0"

[//]: # "Unless required by applicable law or agreed to in writing, software"
[//]: # "distributed under the License is distributed on an "AS IS" BASIS,"
[//]: # "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied."
[//]: # "See the License for the specific language governing permissions and"
[//]: # "limitations under the License."

The configurations in this page can be set from several sources in the following order:

1. the [operating system's environment variables]; for instance, an environment variable can be set with
   `export DB_URL="jdbc:mysql://db/elide?serverTimezone=UTC"`
2. the [Java system properties]; for example, a Java system property can be set using
   `System.setProperty("DB_URL", "jdbc:mysql://db/elide?serverTimezone=UTC")`
3. a **.properties** file placed under CLASSPATH. This file can be put under `src/main/resources` source directory with
   contents, for example, `DB_URL=jdbc:mysql://db/elide?serverTimezone=UTC`