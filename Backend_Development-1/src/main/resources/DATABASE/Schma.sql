CREATE TABLE proof_readers (
    id SERIAL PRIMARY KEY,
    proof_reader_name VARCHAR(255) NOT NULL,
    status VARCHAR(50),
    role VARCHAR(100),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE user_table (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    role VARCHAR(100),
    password VARCHAR(255),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE customer (
    cid SERIAL PRIMARY KEY,
    cname VARCHAR(255) NOT NULL,
    status VARCHAR(50),
    user_id INT REFERENCES user_table(id),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE label_translation (
    label_translation_id SERIAL PRIMARY KEY,
    language VARCHAR(50) NOT NULL,
    label_translated TEXT NOT NULL,
    status VARCHAR(50),
    approved_by INT REFERENCES proof_readers(id),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE labels (
    labelId SERIAL PRIMARY KEY,
    labelname VARCHAR(255) NOT NULL,
    label_translation_id INT REFERENCES label_translation(label_translation_id),
    cid INT REFERENCES customer(cid),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
