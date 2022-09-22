CREATE SEQUENCE sequence_generator START WITH 100 INCREMENT BY 1;
CREATE TABLE currency_display (
    id BIGINT NOT NULL,
    code VARCHAR(3) NOT NULL,
    display VARCHAR(255) NOT NULL,
    language VARCHAR(30) NOT NULL,
    CONSTRAINT PK_CURRENCY_DISPLAY PRIMARY KEY (id));
CREATE UNIQUE INDEX currency_display_idx ON currency_display(code, language);
