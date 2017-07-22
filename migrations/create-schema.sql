CREATE TABLE activites (
  id INT PRIMARY KEY
  , nom TEXT
  , description TEXT
  , arrondissement TEXT
);

CREATE TABLE lieu (
  id INT PRIMARY KEY
  , activitesID INT REFERENCES activites ( id )
  , nom TEXT
  , point GEOMETRY
);


CREATE TABLE dates (
  id INT PRIMARY KEY
  , activitesID INT REFERENCES activites ( id )
  , dates TEXT
  , schedule DATE
);

CREATE TABLE bixi (
  id INT PRIMARY KEY
  , nom TEXT
  , identifiantTerminale INT
  , etat INT
  , bloquee BOOLEAN
  , suspendue BOOLEAN
  , horService BOOLEAN
  , lu REAL
  , lc REAL
  , bk BOOLEAN
  , bl boolean
  , latitude REAL
  , longitude REAL
  , nombreBornesDisponible INT
  , nombreBornesIndisponible INT
  , nombreVelosDisponible INT
  , nombreVelosIndisponible INT
  , point GEOMETRY
);
