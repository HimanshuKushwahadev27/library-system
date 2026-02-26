-- order
CREATE TABLE orders (
    id UUID PRIMARY KEY,

    book_id UUID NOT NULL,
    user_keycloak_id UUID NOT NULL,

    amount NUMERIC(15,2) NOT NULL,

    status VARCHAR(50) NOT NULL,

    payment_id UUID NOT NULL UNIQUE,

    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,
    
	CHECK (amount > 0)
);

-- order-indexing
CREATE INDEX idx_orders_user
    ON orders(user_keycloak_id);

CREATE INDEX idx_orders_status
    ON orders(status);

CREATE INDEX idx_orders_created_at
    ON orders(created_at);
    
--order history
CREATE TABLE order_status_history (
    id UUID PRIMARY KEY,

    order_id UUID NOT NULL,

    old_status VARCHAR(50) NOT NULL,
    new_status VARCHAR(50) NOT NULL,

    changed_at TIMESTAMPTZ NOT NULL,
    changed_by UUID NOT NULL,
    
  
    CONSTRAINT fk_order_status_history_order
        FOREIGN KEY (order_id)
        REFERENCES orders(id)
        ON DELETE CASCADE
);

--index order-history
CREATE INDEX idx_order_status_history_order_id
    ON order_status_history(order_id);

CREATE INDEX idx_order_status_history_changed_at
    ON order_status_history(changed_at);
    
    
-- idempotency-records
CREATE TABLE idempotency_records (
    id UUID PRIMARY KEY,

    idempotency_key VARCHAR(100) NOT NULL,

    user_keycloak_id UUID NOT NULL,

    request_hash TEXT,
    response_body TEXT,
    http_status INTEGER,

    status VARCHAR(50) NOT NULL,

    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ,
    expires_at TIMESTAMPTZ,

    CONSTRAINT uk_idem_user_key
        UNIQUE (idempotency_key, user_keycloak_id)
);


--indexing 
CREATE INDEX idx_idem_user_key
    ON idempotency_records(idempotency_key, user_keycloak_id);

CREATE INDEX idx_idem_expires_at
    ON idempotency_records(expires_at);

CREATE INDEX idx_idem_status
    ON idempotency_records(status);
