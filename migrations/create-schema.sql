create table citations (
    id int primary key
  , auteur text
  , contenu text
);

CREATE TABLE lieu (
    lieuID int primary key,
    nom text,
    lat REAL,
    lng REAL
);

CREATE TABLE dates (
    id int primary key,
    dates text

);

create table activites (
    id int primary key
  , nom text
  , description text
  , arrondissement text
  , dates text
  , lieuID int REFERENCES lieu ( lieuID )
  , datesID int REFERENCES dates ( id )
);
