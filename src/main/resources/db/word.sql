CREATE TABLE IF NOT EXISTS word
(
    id        VARCHAR(100) NOT NULL UNIQUE PRIMARY KEY,
    word_text VARCHAR(50)  NOT NULL UNIQUE,
    word_jamo text[]       NOT NULL,
    length    INT          NOT NULL DEFAULT 6,
    is_used   BOOLEAN      NOT NULL DEFAULT FALSE,
    is_korean BOOLEAN      NOT NULL DEFAULT TRUE
);