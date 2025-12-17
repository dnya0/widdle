CREATE TABLE IF NOT EXISTS word
(
    id              VARCHAR(100) NOT NULL UNIQUE PRIMARY KEY,
    word_text       VARCHAR(50)  NOT NULL UNIQUE,
    word_jamo       text[]       NOT NULL,
    length          INT          NOT NULL DEFAULT 6,
    is_used         BOOLEAN      NOT NULL DEFAULT FALSE,
    used_date_by    DATE         NULL,
    used_date_by_ts BIGINT       NULL, /* Unix Timestamp 저장 */
    is_korean       BOOLEAN      NOT NULL DEFAULT TRUE
);

/* Unix Timestamp를 저장하기 위한 update 문 */
UPDATE word
SET used_date_by_ts = EXTRACT(EPOCH FROM (used_date_by AT TIME ZONE 'Asia/Seoul'))::BIGINT
WHERE used_date_by_ts IS NULL;
