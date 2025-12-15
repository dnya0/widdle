CREATE TABLE IF NOT EXISTS dashboard
(
    id             VARCHAR(100)             NOT NULL UNIQUE PRIMARY KEY,
    nickname       VARCHAR(50)              NOT NULL UNIQUE,
    total_streak   INT                      NOT NULL DEFAULT 0,
    success_rate   INT                      NOT NULL DEFAULT 0,
    current_streak INT                      NOT NULL DEFAULT 0,
    best_streak    INT                      NOT NULL DEFAULT 0,
    is_korean      BOOLEAN                  NOT NULL DEFAULT TRUE,
    created_at     TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at    TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);
