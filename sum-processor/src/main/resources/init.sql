CREATE TABLE IF NOT EXISTS sum_records (
    id SERIAL PRIMARY KEY,
    request TEXT NOT NULL,
    response TEXT,
    endpoint VARCHAR(255) NOT NULL,
    date TIMESTAMP NOT NULL,
    cod_response INTEGER NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
