CREATE TABLE IF NOT EXISTS ort(
    id uuid PRIMARY KEY USING INDEX TABLESPACE gartenspace,
    plz text NOT NULL CHECK (plz ~ '\d{5}'),
    name text NOT NULL
    ) TABLESPACE gartenspace;

CREATE TABLE IF NOT EXISTS garten (
    id uuid PRIMARY KEY USING INDEX TABLESPACE gartenspace,
    version integer NOT NULL DEFAULT 0,
    name text NOT NULL,
    flaeche integer NOT NULL CHECK (flaeche >= 10 AND flaeche <= 500),
    ort_id uuid NOT NULL UNIQUE USING INDEX TABLESPACE gartenspace REFERENCES ort,
    erzeugt       timestamp NOT NULL,
    aktualisiert  timestamp NOT NULL
) TABLESPACE gartenspace;

CREATE TABLE IF NOT EXISTS baum (
    id uuid NOT NULL UNIQUE USING INDEX TABLESPACE gartenspace,
    name text NOT NULL,
    aeste integer NOT NULL CHECK ( aeste >= 3 AND aeste <= 10),
    garten_id uuid REFERENCES garten,
    idx integer NOT NULL DEFAULT 0
) TABLESPACE gartenspace;
