The configurations in this page can be set from several sources in the following order:

1. the [operating system's environment variables]; for instance, an environment variable can be set with
   `export DB_URL="jdbc:mysql://db/elide?serverTimezone=UTC"`
2. the [Java system properties]; for example, a Java system property can be set using
   `System.setProperty("DB_URL", "jdbc:mysql://db/elide?serverTimezone=UTC")`
3. a **.properties** file placed under CLASSPATH. This file can be put under `src/main/resources` source directory with
   contents, for example, `DB_URL=jdbc:mysql://db/elide?serverTimezone=UTC`

Core Properties
---------------

:::note

The following configurations can be placed in the properties file called **application.properties**

:::

- **MODEL_PACKAGE_NAME**: The fully qualified package name that contains a set of Elide JPA models

(Elide) JPA DataStore
---------------------

:::note

The following configurations can be placed in the properties file called **jpadatastore.properties**

:::

- **DB_USER**: Persistence DB username (needs have both Read and Write permissions).
- **DB_PASSWORD**: The persistence DB user password.
- **DB_URL**: The persistence DB URL, such as "jdbc:mysql://localhost/elide?serverTimezone=UTC".
- **DB_DRIVER**: The SQL DB driver class name, such as "com.mysql.jdbc.Driver".
- **DB_DIALECT**: The SQL DB dialect name, such as "org.hibernate.dialect.MySQLDialect".
- **HIBERNATE_HBM2DDL_AUTO**: What to do with existing JPA database when webservice starts; can be one of the 4 values:

