-- SQL schema with created_date and updated_date on every table

-- 1. Users Table
CREATE TABLE user_table (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    role VARCHAR(100),
    password VARCHAR(255),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP );

-- 2. Customer Table
CREATE TABLE customer (
    cid SERIAL PRIMARY KEY,
    cname VARCHAR(255) NOT NULL,
    status VARCHAR(50),
    user_id INT REFERENCES user_table(id),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP );

-- 3. Languages Table
CREATE TABLE languages (
    lid SERIAL PRIMARY KEY,
    name VARCHAR(10) NOT NULL,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP );

-- 4. Customer Language Table
CREATE TABLE customer_language (
    cl_id SERIAL PRIMARY KEY,
    cid INT NOT NULL REFERENCES customer(cid),
    lid INT NOT NULL REFERENCES languages(lid),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP );

-- 5. Proof Readers Table
CREATE TABLE proof_readers (
    id SERIAL PRIMARY KEY,
    proof_reader_name VARCHAR(255) NOT NULL,
    status VARCHAR(50),
    role VARCHAR(100),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP );

INSERT INTO proof_readers (proof_reader_name, status, role)
VALUES
('Amit Sharma', 'ACTIVE', 'Content Reviewer'),
('Priya Iyer', 'ACTIVE', 'Language Expert'),
('Ravi Patel', 'INACTIVE', 'Junior Proofreader'),
('Sneha Reddy', 'ACTIVE', 'ADMIN'),
('Karan Mehta', 'ACTIVE', 'Multilingual Specialist');

-- 6. Labels Table
CREATE TABLE labels (
    label_id SERIAL PRIMARY KEY,
    label_name VARCHAR(255) NOT NULL,
    label_translation_id INT REFERENCES label_translation(label_translation_id),
    cid INT NOT NULL REFERENCES customer(cid),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP );



ALTER TABLE labels ADD COLUMN label_key VARCHAR(255) NOT NULL DEFAULT '';


-- 7. Label Translation Table
CREATE TABLE label_translation (
    label_translation_id SERIAL PRIMARY KEY,
    language VARCHAR(10) NOT NULL,
    label_translated TEXT NOT NULL,
    status VARCHAR(50),
    approved_by INT REFERENCES proof_readers(id),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP );

ALTER TABLE label_translation ADD COLUMN label VARCHAR(255);

ALTER TABLE label_translation RENAME COLUMN language TO language_code;

ALTER TABLE label_translation ADD COLUMN translations VARCHAR(1000) DEFAULT '{}' NOT NULL;

ALTER TABLE label_translation DROP COLUMN translations;



INSERT INTO customer (cid, cname, status, user_id)
VALUES (1, 'Test Customer', 'ACTIVE', 1);

INSERT INTO user_table (name, role, password)
VALUES ('Test User', 'ADMIN', 'password123');



CREATE TABLE language (
     id SERIAL PRIMARY KEY,
    lname VARCHAR(50),
    language_key VARCHAR(10)
);
INSERT INTO language (lname, language_key) VALUES
('English', 'en'),
('Spanish', 'es'),
('French', 'fr'),
('German', 'de'),
('Japanese', 'ja');


CREATE TABLE customerlang (
    id SERIAL PRIMARY KEY,
    customer_id INT,
    lid INT,
    is_default BOOLEAN,
    FOREIGN KEY (lid) REFERENCES languages(id));



select * from language;

select * from customerlang;

select * from customer;

select * from user_table;

INSERT INTO user_table (id, name, role, password) VALUES
(2, 'John Doe', 'EDITOR', 'johnpass'),
(3, 'Jane Smith', 'VIEWER', 'janesecure'),
(4, 'Maria Garcia', 'ADMIN', 'mariapass');

INSERT INTO customer (cid, cname, status, user_id) VALUES
(1, 'Acme Corp', 'ACTIVE', 2),
(3, 'Globex Inc.', 'INACTIVE', 3),
(4, 'OpenSky LLC', 'ACTIVE', 4),
(5, 'Demo Customer', 'ACTIVE', 1);

INSERT INTO customerlang (customer_id, id, is_default) VALUES
(2, 1, TRUE),
(2, 2, FALSE),
(2, 3, FALSE);

INSERT INTO customerlang (customer_id, language_id, is_default) VALUES
(1, 1, TRUE),
(1, 2, FALSE),
(1, 3, FALSE);

ALTER TABLE customer ADD COLUMN cuid UUID NOT NULL DEFAULT gen_random_uuid();
CREATE UNIQUE INDEX idx_customer_cuid ON customer(cuid);

