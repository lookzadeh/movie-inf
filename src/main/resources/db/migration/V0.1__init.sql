
create table if not exists award
(
    id                 binary(16)    not null primary key,
    created_by         varchar(255)  null,
    created_date       datetime(6)   null,
    deleted            bit           null,
    last_modified_date datetime(6)   null,
    modified_by        varchar(255)  null,
    version            int           null,
    movie_year         int           null,
    nominee            varchar(300)  not null,
    category           varchar(300)  not null,
    is_won             boolean       null,
    additional_info    varchar(3000) null
);

create table if not exists setting
(
    id                 binary(16)    not null primary key,
    created_by         varchar(255)  null,
    created_date       datetime(6)   null,
    deleted            bit           null,
    last_modified_date datetime(6)   null,
    modified_by        varchar(255)  null,
    version            int           null,
    name               varchar(100)  not null,
    value              varchar(1000) null
);

create table if not exists movie
(
    id                 binary(16)    not null primary key,
    created_by         varchar(255)  null,
    created_date       datetime(6)   null,
    deleted            bit           null,
    last_modified_date datetime(6)   null,
    modified_by        varchar(255)  null,
    version            int           null,
    title              varchar(300)  not null,
    year               varchar(20)   null,
    rated              varchar(1000) null,
    released           varchar(300)  null,
    runtime            varchar(300)  null,
    genre              varchar(300)  null,
    director           varchar(300)  null,
    writer             varchar(300)  null,
    actors             varchar(1000) null,
    plot               varchar(1000) null,
    language           varchar(300)  null,
    country            varchar(300)  null,
    awards             varchar(300)  null,
    poster             varchar(300)  null,
    ratings            varchar(1000) null,
    metascore          varchar(300)  null,
    imdb_rating        varchar(300)  null,
    imdb_votes         varchar(300)  null,
    imdb_id            varchar(100)  not null,
    type               varchar(300)  null,
    dvd                varchar(300)  null,
    box_office         varchar(300)  null,
    production         varchar(300)  null,
    website            varchar(300)  null,
    has_best_picture   boolean       null
);

CREATE INDEX idx_award_nominee    ON award (nominee);
CREATE INDEX idx_movie_title      ON movie (title);
CREATE INDEX idx_movie_imdb_id    ON movie (imdb_id);
ALTER TABLE award
    ADD UNIQUE uqc_award_nominee (nominee);
ALTER TABLE movie
    ADD UNIQUE uqc_movie_imdb_id (imdb_id);