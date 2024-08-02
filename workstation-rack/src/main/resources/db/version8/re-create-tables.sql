DROP TABLE IF EXISTS t_team CASCADE;
DROP TABLE IF EXISTS t_team_member CASCADE;
DROP TABLE IF EXISTS t_rack CASCADE;
DROP TABLE IF EXISTS t_rack_asset CASCADE;
DROP TABLE IF EXISTS t_booking CASCADE;

CREATE TABLE t_team
(
    id               UUID PRIMARY KEY DEFAULT gen_random_uuid() NOT NULL,
    name             VARCHAR(255)                               NOT NULL,
    product          VARCHAR(255)                               NOT NULL,
    created_at       TIMESTAMP        DEFAULT now()             NOT NULL,
    modified_at      TIMESTAMP        DEFAULT now()             NOT NULL,
    default_location VARCHAR(255)                               NOT NULL
);



CREATE TABLE t_team_member
(
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid() NOT NULL,
    team_id     UUID                                       NOT NULL,
    ctw_id      VARCHAR(255)                               NOT NULL,
    name        VARCHAR(255)                               NOT NULL,
    created_at  TIMESTAMP        DEFAULT now()             NOT NULL,
    modified_at TIMESTAMP        DEFAULT now()             NOT NULL,
    FOREIGN KEY (team_id) REFERENCES t_team (id)
);


CREATE TABLE t_rack
(
    id               UUID PRIMARY KEY DEFAULT gen_random_uuid() NOT NULL,
    serial_number    VARCHAR(255)                               NOT NULL,
    team_id          UUID,
    created_at       TIMESTAMP        DEFAULT now()             NOT NULL,
    default_location VARCHAR(255)                               NOT NULL,
    status           VARCHAR(50)                                NOT NULL,
    modified_at      TIMESTAMP        DEFAULT now()             NOT NULL,
    FOREIGN KEY (team_id) REFERENCES t_team (id)
);


CREATE TABLE t_rack_asset
(
    id        UUID PRIMARY KEY DEFAULT gen_random_uuid() NOT NULL,
    asset_tag VARCHAR(255)                               NOT NULL,
    rack_id   UUID                                       NOT NULL,
    FOREIGN KEY (rack_id) REFERENCES t_rack (id)
);


CREATE TABLE t_booking
(
    id           UUID PRIMARY KEY DEFAULT gen_random_uuid() NOT NULL,
    rack_id      UUID                                       NOT NULL,
    requester_id UUID                                       NOT NULL,
    book_from    TIMESTAMP                                  NOT NULL,
    book_to      TIMESTAMP                                  NOT NULL,
    created_at   TIMESTAMP        DEFAULT now()             NOT NULL,
    modified_at  TIMESTAMP        DEFAULT now()             NOT NULL,
    FOREIGN KEY (rack_id) REFERENCES t_rack (id),
    FOREIGN KEY (requester_id) REFERENCES t_team_member (id)
);


