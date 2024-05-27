# mmieckowski_Statement_Prepared_Statement

# JDBC Statement vs PreparedStatement


## Statement
* Used to execute string-based SQL queries, without any parameters
* Queries have inline values to the database
* Each time  the statement is executed the sql query is beeing compiled
* Vulnerable to SQL injection
* No database cache supprot
* Statement interface is suitable for DDL queries like CREATE, ALTER, and DROP
* No support for files and arrays
* Low performance

## PreparedStatement
* Used to execute parameterized SQL queries
* Compiles the SQL query only once and reuses it multiple times with different parameters
* Support for files and arrays
* Automatic prevention of SQL injection attacks by built in escaping of quotes and other special characters.
* Pre-compilation and database cache support. As soon as the database gets a query, it will check the cache before pre-compiling the query. Consequently, if it is not cached, the database engine will save it for the next usage.
* Fast communication between the database and the JVM through a binary protocol
* Batch execution during a single database connection: `addBatch` and `executeBatch` methods

## To run db for the app:
1) being in the root folder of the project, type in the terminal: `cd docker`
2) type `docker-compose up -d` to run container with postgres in detached mode
3) connect to db using DBeaver, providing the following info:
* port: 5432
* user: dev
* password: MyPass
* database: statements

### another docker commands
- `docker-compose stop`: Stops running containers without removing them. They can be started again with `docker-compose start`.

- `docker-compose down`: Stops the containers and removes them along with their associated networks.