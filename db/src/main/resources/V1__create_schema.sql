CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    reset_token VARCHAR(100),
    reset_token_expiry TIMESTAMP
);

CREATE TABLE membership (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    plan VARCHAR(50) UNIQUE NOT NULL,
    price VARCHAR(20) NOT NULL
);

CREATE TABLE customer (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cuid UUID NOT NULL UNIQUE,
    user_id BIGINT,
    full_name VARCHAR(100),
    phone_number VARCHAR(20),
    country VARCHAR(100),
    company_name VARCHAR(100),
    plan_expiry_date DATE,
    registration_date DATE,
    account_status VARCHAR(20),
    membership_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (membership_id) REFERENCES membership(id)
);


CREATE TABLE proof_reader (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    puid UUID NOT NULL UNIQUE,
    user_id BIGINT,
    full_name VARCHAR(100),
    phone_number VARCHAR(20),
    supported_languages TEXT,
    years_of_experience INT,
    availability VARCHAR(100),
    resume_path VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES user(id)
);

CREATE TABLE language (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    language_name VARCHAR(255),
    language_key VARCHAR(255)
);

CREATE TABLE customerlang (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    language_id BIGINT NOT NULL,
    is_default BOOLEAN,
    FOREIGN KEY (customer_id) REFERENCES customer(id),
    FOREIGN KEY (language_id) REFERENCES language(id)
);

CREATE TABLE label_translation (
    label_translation_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    label_key VARCHAR(255) NOT NULL,
    language_code VARCHAR(20) NOT NULL,
    translations VARCHAR(255) NOT NULL,
    label_translated TEXT NOT NULL,
    status VARCHAR(50),
    approved_by BIGINT,
    created_date TIMESTAMP,
    updated_date TIMESTAMP,
    FOREIGN KEY (label_key) REFERENCES labels(label_key),
    FOREIGN KEY (approved_by) REFERENCES proof_reader(id)
);

CREATE TABLE labels (
    label_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    label_name VARCHAR(255) NOT NULL,
    label_key VARCHAR(255) NOT NULL,
    cuid UUID,
    userid BIGINT,
    created_date TIMESTAMP,
    updated_date TIMESTAMP,
    FOREIGN KEY (cuid) REFERENCES customer(cuid),
    FOREIGN KEY (userid) REFERENCES user(id)
);

CREATE TABLE contact_messages (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(255),
    email VARCHAR(255),
    phone_number VARCHAR(20),
    description TEXT,
    submitted_at TIMESTAMP
);

