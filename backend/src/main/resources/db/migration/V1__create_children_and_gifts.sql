CREATE TABLE children (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    age INTEGER NOT NULL CHECK (age >= 0)
);

CREATE TABLE gifts (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    image_url TEXT,
    product_url TEXT,
    reserved_by VARCHAR(255),
    reserved_at TIMESTAMP,
    child_id BIGINT NOT NULL REFERENCES children(id) ON DELETE CASCADE,
    CONSTRAINT reservation_fields_consistent CHECK ((reserved_by IS NULL) = (reserved_at IS NULL))
);

CREATE INDEX idx_children_first_name ON children(first_name);
CREATE INDEX idx_gifts_child_id ON gifts(child_id);
CREATE INDEX idx_gifts_reserved_by ON gifts(reserved_by) WHERE reserved_by IS NOT NULL;
