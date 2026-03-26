CREATE TABLE users (
    id UUID PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    display_name VARCHAR(120) NOT NULL,
    first_name VARCHAR(120) NOT NULL,
    last_name VARCHAR(120) NOT NULL,
    address VARCHAR(255),
    status VARCHAR(20) NOT NULL,
    recieved_linking_purpose BOOLEAN NOT NULL DEFAULT FALSE,
    linking_purpose_origin UUID REFERENCES users(id),
    game_count INTEGER NOT NULL DEFAULT 0,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ
);

CREATE TABLE user_settings (
    user_id UUID PRIMARY KEY REFERENCES users(id) ON DELETE CASCADE,
    push_enabled BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMPTZ NOT NULL
);

CREATE TABLE couple_links (
    id UUID PRIMARY KEY,
    user_1_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    user_2_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    CONSTRAINT chk_different_users CHECK (user_1_id <> user_2_id)
);
CREATE INDEX idx_couple_links_user_1 ON couple_links(user_1_id);
CREATE INDEX idx_couple_links_user_2 ON couple_links(user_2_id);
CREATE UNIQUE INDEX uq_couple_link_active_user1 ON couple_links(user_1_id) WHERE status = 'ACTIVE';
CREATE UNIQUE INDEX uq_couple_link_active_user2 ON couple_links(user_2_id) WHERE status = 'ACTIVE';

CREATE TABLE devices (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    platform VARCHAR(20) NOT NULL,
    fcm_token VARCHAR(400) NOT NULL UNIQUE,
    app_version VARCHAR(40) NOT NULL,
    last_seen_at TIMESTAMPTZ NOT NULL,
    created_at TIMESTAMPTZ NOT NULL
);
CREATE INDEX idx_devices_user_id ON devices(user_id);

CREATE TABLE refresh_tokens (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    token_hash VARCHAR(255) NOT NULL UNIQUE,
    expires_at TIMESTAMPTZ NOT NULL,
    revoked_at TIMESTAMPTZ,
    created_at TIMESTAMPTZ NOT NULL,
    device_id UUID NOT NULL REFERENCES devices(id) ON DELETE CASCADE
);
CREATE INDEX idx_refresh_tokens_user ON refresh_tokens(user_id);
CREATE INDEX idx_refresh_tokens_expires ON refresh_tokens(expires_at);

CREATE TABLE notifications (
    id BIGSERIAL PRIMARY KEY,
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    type VARCHAR(50) NOT NULL,
    title VARCHAR(180) NOT NULL,
    body VARCHAR(500) NOT NULL,
    read_at TIMESTAMPTZ,
    created_at TIMESTAMPTZ NOT NULL
);
CREATE INDEX idx_notifications_user_created ON notifications(user_id, created_at DESC);

CREATE TABLE ideas (
    id BIGSERIAL PRIMARY KEY,
    owner UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    value VARCHAR(255) NOT NULL,
    removed BOOLEAN NOT NULL DEFAULT FALSE,
    got BOOLEAN NOT NULL DEFAULT FALSE,
    finished BOOLEAN NOT NULL DEFAULT FALSE,
    finished_by UUID REFERENCES users(id),
    thrown BOOLEAN NOT NULL DEFAULT FALSE,
    thrown_by UUID REFERENCES users(id),
    created_at TIMESTAMPTZ NOT NULL,
    remove_date TIMESTAMPTZ,
    got_date TIMESTAMPTZ,
    finished_date TIMESTAMPTZ,
    thrown_date TIMESTAMPTZ
);

CREATE TABLE got_ideas (
    "user" UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    idea BIGINT NOT NULL REFERENCES ideas(id) ON DELETE CASCADE,
    created_at TIMESTAMPTZ NOT NULL,
    PRIMARY KEY ("user", idea)
);

CREATE TABLE reviews (
    id BIGSERIAL PRIMARY KEY,
    owner UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    idea BIGINT NOT NULL REFERENCES ideas(id) ON DELETE CASCADE,
    grade DECIMAL NOT NULL,
    fichiers BYTEA,
    content VARCHAR(1000) NOT NULL,
    comment VARCHAR(1000),
    recommend BOOLEAN,
    created_at TIMESTAMPTZ,
    updated_at TIMESTAMPTZ
);

CREATE TABLE history (
    id BIGSERIAL PRIMARY KEY,
    idee_de UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    termine_par UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    idee BIGINT NOT NULL REFERENCES ideas(id) ON DELETE CASCADE,
    revue_de_proprietaire BIGINT REFERENCES reviews(id),
    revue_de_tireur BIGINT REFERENCES reviews(id),
    created_at TIMESTAMPTZ NOT NULL,
    reel_date TIMESTAMPTZ NOT NULL
);
