-- Create table if not exist for integration test.
DROP TABLE IF EXISTS  TBL0001_CUSTOMERS;
CREATE TABLE TBL0001_CUSTOMERS (
                                                 id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                                 first_name VARCHAR(255) NOT NULL,
                                                 last_name VARCHAR(255) NOT NULL,
                                                 cpf VARCHAR(11) UNIQUE NOT NULL,
                                                 address VARCHAR(255),
                                                 phone VARCHAR(15),
                                                 birth_date DATE,
                                                 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                                                 updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);
