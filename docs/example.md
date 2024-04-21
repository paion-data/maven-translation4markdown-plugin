The configurations in this page can be set from several sources in the following order:

1. the [operating system's environment variables]; for instance, an environment variable can be set with
   `export DB_URL="jdbc:mysql://db/elide?serverTimezone=UTC"`
2. the [Java system properties]; for example, a Java system property can be set using
   `System.setProperty("DB_URL", "jdbc:mysql://db/elide?serverTimezone=UTC")`
3. **.properties** file placed under CLASSPATH. This file can be put under `src/main/resources` source directory with
   contents, for example, `DB_URL=jdbc:mysql://db/elide?serverTimezone=UTC`

