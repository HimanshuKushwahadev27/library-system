CREATE TABLE catalog_ownership (

    id UUID PRIMARY KEY,

    book_id UUID NOT NULL,

    user_keycloak_id UUID NOT NULL,

    chapter_id UUID NULL,

    type VARCHAR(20) NOT NULL,

    CONSTRAINT chk_catalog_ownership_type
    CHECK (
        (type = 'BOOK' AND chapter_id IS NULL)
        OR
        (type = 'CONTENT' AND chapter_id IS NOT NULL)
    )
);

-- Index for fast ownership check
CREATE INDEX idx_catalog_ownership_user_book
    ON catalog_ownership(user_keycloak_id, book_id);

-- Index for chapter lookup
CREATE INDEX idx_catalog_ownership_user_chapter
    ON catalog_ownership(user_keycloak_id, chapter_id);

-- Prevent duplicate full book ownership
CREATE UNIQUE INDEX uk_catalog_full_book
    ON catalog_ownership(user_keycloak_id, book_id)
    WHERE chapter_id IS NULL;

-- Prevent duplicate chapter ownership
CREATE UNIQUE INDEX uk_catalog_chapter
    ON catalog_ownership(user_keycloak_id, chapter_id)
    WHERE chapter_id IS NOT NULL;