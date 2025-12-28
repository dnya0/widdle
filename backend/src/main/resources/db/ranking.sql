CREATE TABLE IF NOT EXISTS ranking
(
    id             VARCHAR(100) NOT NULL UNIQUE PRIMARY KEY,
    nickname       VARCHAR(50)  NOT NULL UNIQUE,
    device_id      VARCHAR(100) NOT NULL UNIQUE,
    total_streak   INT          NOT NULL DEFAULT 0,
    success_rate   INT          NOT NULL DEFAULT 0 CHECK (success_rate >= 0 AND success_rate <= 100),
    current_streak INT          NOT NULL DEFAULT 0,
    best_streak    INT          NOT NULL DEFAULT 0,
    today_playtime       BIGINT       NOT NULL DEFAULT 0,
    total_playtime       BIGINT       NOT NULL DEFAULT 0,
    is_korean      BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at     BIGINT       NOT NULL,
    modified_at    BIGINT       NOT NULL
);

CREATE INDEX idx_ranking_today_playtime ON ranking(today_playtime ASC);

CREATE INDEX idx_ranking_success_rate ON ranking(success_rate DESC);

CREATE INDEX idx_ranking_korean_today ON ranking(is_korean, today_playtime ASC);
