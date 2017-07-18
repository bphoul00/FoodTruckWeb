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
  , lieuNom text
  , lat int
  , lng int
);
