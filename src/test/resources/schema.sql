-- Create table if not exist for integration test.
DROP TABLE IF EXISTS  TBL0003_CUSTOMERS_CONTACTS;
DROP TABLE IF EXISTS  TBL0002_DOCUMENTS;
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

CREATE TABLE TBL0002_DOCUMENTS (
                                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                               document_type VARCHAR(50) NOT NULL,
                                               number VARCHAR(50) NOT NULL,
                                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                                               updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE TBL0003_CUSTOMERS_CONTACTS (
                                            customer_id BIGINT NOT NULL,
                                            document_id BIGINT NOT NULL,
                                            PRIMARY KEY (customer_id, document_id),
                                            FOREIGN KEY (customer_id) REFERENCES TBL0001_CUSTOMERS(id),
                                            FOREIGN KEY (document_id) REFERENCES TBL0002_DOCUMENTS(id)
);
