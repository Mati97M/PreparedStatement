CREATE TABLE IF NOT EXISTS students
(
    id BIGINT PRIMARY KEY,
    first_name VARCHAR(20),
    last_name VARCHAR(40),
    mean NUMERIC (3, 2)
);

INSERT INTO students VALUES
    (1, 'John', 'Smith', 3.5),
    (2, 'Mary', 'Johnson', 4),
    (3, 'James', 'Williams', 5),
    (4, 'Patricia', 'Jones', 3.5),
    (5, 'Robert', 'Brown', 3.7),
    (6, 'Jennifer', 'Davis', 2),
    (7, 'Michael', 'Miller', 4.5);

