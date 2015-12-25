How to use flyway
================

This project supports flyway for database migrations. While multiple databases are supported, flyway supports only
the PostgreSQL database which is the target productions environment.

Database migration files should be put in the db.migration package within the resources and follow the standard
flyway notation. Flyway is invoked through is maven plugin.

Before invoking any flyway maven goal, **do not forget to clean and compile**.
 
Flyway maven goals
------------------

* `flyway:clean`
This will remove all database objects. Use with care.

* `flyway:migrate`
Applies all new migrations. This is the goal to execute when there are new migrations and the first time the application
is deployed as to initialize the database and the flyway table.

* `flyway:info`
Provides information on applied and new migrations.

