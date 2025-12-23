CREATE TABLE IF NOT EXISTS ranking
(
    id             VARCHAR(100) NOT NULL UNIQUE PRIMARY KEY,
    nickname       VARCHAR(50)  NOT NULL UNIQUE,
    total_streak   INT          NOT NULL DEFAULT 0,
    success_rate   INT          NOT NULL DEFAULT 0 CHECK (success_rate >= 0 AND success_rate <= 100),
    current_streak INT          NOT NULL DEFAULT 0,
    best_streak    INT          NOT NULL DEFAULT 0,
    playtime       BIGINT       NOT NULL DEFAULT 0,
    is_korean      BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at     BIGINT       NOT NULL,
    modified_at    BIGINT       NOT NULL
);
