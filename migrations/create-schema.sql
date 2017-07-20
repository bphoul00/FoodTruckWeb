create table citations (
    id int primary key
  , auteur text
  , contenu text
);

create table activites (
    id int primary key
  , nom text
  , description text
  , arrondissement text
  , dates text
);

CREATE TABLE lieu (
      id int primary key
	, activitesID int REFERENCES activites ( id )
    , nom text
	, point geometry
);



CREATE TABLE dates (
	  id int primary key
    , activitesID int REFERENCES activites ( id )
    , dates text
	, schedule DATE

);
