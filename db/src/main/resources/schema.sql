-- SQL schema with created_date and updated_date on every table

-- 1. Users Table
CREATE TABLE user_table (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    role VARCHAR(100),
    password VARCHAR(255),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2. Customer Table
CREATE TABLE customer (
    cid SERIAL PRIMARY KEY,
    cname VARCHAR(255) NOT NULL,
    status VARCHAR(50),
    user_id INT REFERENCES users(id),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 3. Languages Table
CREATE TABLE languages (
    lid SERIAL PRIMARY KEY,
    name VARCHAR(10) NOT NULL,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 4. Customer Language Table
CREATE TABLE customer_language (
    cl_id SERIAL PRIMARY KEY,
    cid INT NOT NULL REFERENCES customer(cid),
    lid INT NOT NULL REFERENCES languages(lid),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 5. Proof Readers Table
CREATE TABLE proof_readers (
    id SERIAL PRIMARY KEY,
    proof_reader_name VARCHAR(255) NOT NULL,
    status VARCHAR(50),
    role VARCHAR(100),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 6. Labels Table
CREATE TABLE labels (
    label_id SERIAL PRIMARY KEY,
    label_name VARCHAR(255) NOT NULL,
    label_translation_id INT REFERENCES label_translation(label_translation_id),
    cid INT NOT NULL REFERENCES customer(cid),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 7. Label Translation Table
CREATE TABLE label_translation (
    label_translation_id SERIAL PRIMARY KEY,
    language VARCHAR(10) NOT NULL,
    label_translated TEXT NOT NULL,
    status VARCHAR(50),
    approved_by INT REFERENCES proof_readers(id),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
