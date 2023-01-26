\c postgres
drop database enchere;
create database enchere;
\c enchere

create sequence sqtokenadmin;
create sequence sqtokenuser;

create table Utilisateur(
idUtilisateur serial primary key,
nom varchar(20),
prenom varchar(20),
email varchar(20),
mdp varchar(20),
DateInscription date default CURRENT_DATE,
compte float default 0
);


create table Admin(
idAdmin serial primary key,    
email varchar(20),
mdp varchar(20)
);

create table RechargementCompte(
idRechargementCompte serial primary key,    
idUtilisateur int references Utilisateur(idUtilisateur),
montant float,
DateHeureRechargement TIMESTAMP default CURRENT_TIMESTAMP ,
estValider int default 0
);

create table CategorieProduit(
idCategorieProduit serial primary key,
typeCategorie varchar(20)
);

create table Produit(
idProduit serial primary key,
nomProduit varchar(50),
description text,
prix float,
numero_serie varchar(30),
DateSortie date,
Etat int,
Provenance varchar(10),
photo text default 'vide.png',
idCategorieProduit int REFERENCES CategorieProduit(idCategorieProduit)
);



create table Enchere(
idEnchere serial primary key,
idUtilisateur int references Utilisateur(idUtilisateur),
description text,
prixMinimumVente float,
durreEnchere int,
DateHeureEnchere TIMESTAMP default CURRENT_TIMESTAMP,
status int default 0
);

---histo encherisseur --


-- SELECT e.description, e.prixMinimumVente, e.durreEnchere, e.DateHeureEnchere, ho.montant_offre, ho.DateHeureMise, p.nomProduit, p.description, cp.typeCategorie FROM Utilisateur u JOIN HistoriqueOffre ho using(idUtilisateur) JOIN Enchere e using(idEnchere) JOIN Produit_Enchere pe  using(idEnchere) JOIN Produit p using(idProduit) JOIN CategorieProduit cp using(idCategorieProduit) WHERE u.idUtilisateur = 1 ORDER BY ho.DateHeureMise DESC;

---histovendeur---

-- SELECT e.description, e.prixMinimumVente, e.durreEnchere, e.DateHeureEnchere, p.nomProduit, p.description, cp.typeCategorie, re.prix_gagnant,u2.nom, u2.prenom, u2.email FROM Utilisateur u JOIN Enchere e using(idUtilisateur) JOIN Produit_Enchere pe using(idEnchere) JOIN Produit p using(idProduit) JOIN CategorieProduit cp using(idCategorieProduit) LEFT JOIN ResultatEnchere re using(idEnchere) LEFT JOIN Utilisateur u2 ON u2.idUtilisateur = re.idUtilisateur WHERE u.idUtilisateur = 1 ORDER BY e.DateHeureEnchere DESC;




create table Produit_Enchere(
idEnchere int references Enchere(idEnchere),
idProduit int references Produit(idProduit)
);



create table HistoriqueOffre(
idHistoriqueOffre serial primary key,
idEnchere int references Enchere(idEnchere),
idUtilisateur int references Utilisateur(idUtilisateur),
montant_offre float,
DateHeureOffre TIMESTAMP default CURRENT_TIMESTAMP
);


create table ResultatEnchere(
idResultatEnchere serial primary key,
idEnchere int references Enchere(idEnchere),
idUtilisateur int references Utilisateur(idUtilisateur),
prix_gagnant float,
DateHeureGagnat TIMESTAMP default CURRENT_TIMESTAMP
);

create table PourcentagePrelevee(
pourcentage float
);


create table PrelevementEnchere(
idPrelevement serial primary key,
idEnchere int references Enchere(idEnchere),
montant float,
DatePrelevement DATE default CURRENT_DATE
);



create table tokenAdmin(
idtokenadmin varchar(10) primary key not null default 'tokena'||nextval('sqtokenadmin'),
idadmin int references Admin(idAdmin),
token varchar(100),
datecreation date,
dateexpiration date,
role varchar(10)
);


create table tokenUser(
idtokenuser varchar(10) primary key not null default 'tokena'||nextval('sqtokenuser'),
idUtilisateur int references Utilisateur(idUtilisateur),    
token varchar(100),
datecreation date,
dateexpiration date,
role varchar(10)
);


--statistiques-----

--view 1 : nombre de membres par jour , mois , annee



select count(idUtilisateur) as nombre , extract(year from DateInscription) as annee , extract(month from DateInscription) as mois , to_char(DateInscription,'Mon') from Utilisateur group by extract(year from DateInscription) , extract(month from DateInscription),to_char(DateInscription,'Mon');



--view 2 : nombre total enchere par jour , mois , annee


select count(idEnchere) as nombre , extract(year from DateHeureEnchere) as annee , extract(month from DateHeureEnchere) as mois , to_char(DateHeureEnchere,'Mon') from enchere group by extract(year from DateHeureEnchere) , extract(month from DateHeureEnchere),to_char(DateHeureEnchere,'Mon');


--view 3 : nombre de catégorie  produits vendus par catégories

create or replace view categorieProduitVendu as  
WITH all_categories AS (SELECT idCategorieProduit FROM CategorieProduit)
SELECT cp2.idCategorieProduit, cp2.typeCategorie , COUNT(re.idEnchere) as total_produit_vendu
FROM all_categories cp
LEFT JOIN Produit p 
using(idCategorieProduit)
LEFT JOIN Produit_Enchere pe 
using(idProduit)
LEFT JOIN Enchere e 
using(idEnchere)
LEFT JOIN ResultatEnchere re 
ON re.idEnchere = e.idEnchere AND re.idEnchere = pe.idEnchere
LEFT JOIN CategorieProduit cp2
ON cp2.idCategorieProduit = cp.idCategorieProduit
GROUP BY cp2.idCategorieProduit,cp2.typeCategorie order by COUNT(re.idEnchere) desc;



--view 6 : nombre de vente  des produits par client


create or replace view StatClient as
WITH all_utilisateurs AS ( SELECT idUtilisateur FROM utilisateur)
SELECT cp2.nom ,cp2.prenom , cp2.idUtilisateur , COUNT(e.idUtilisateur) as nombre_produit_vendu
FROM all_utilisateurs cp
LEFT JOIN Enchere e 
using(idUtilisateur)
LEFT JOIN utilisateur cp2
ON cp2.idUtilisateur = cp.idUtilisateur
GROUP BY cp2.idUtilisateur order by COUNT(e.idUtilisateur) desc;

-----view-----



create or replace view ProduitCategorie as
select p.idProduit , p.nomProduit , p.description , p.prix , p.numero_serie , p.DateSortie , p.Etat , p.Provenance , p.photo , c.idCategorieProduit , c.typeCategorie  from Produit p inner join CategorieProduit c using(idCategorieProduit);


create or replace view v_total_membre as
select count(idUtilisateur) as nombre , extract(year from DateInscription) as annee , extract(month from DateInscription) as mois, extract(day from DateInscription) as jour from Utilisateur group by
extract(year from DateInscription) , extract(month from DateInscription) , extract(day from DateInscription);


INSERT INTO Utilisateur (nom, prenom, email, mdp) VALUES ('John', 'Doe', 'john.doe@example.com', 'password123');
INSERT INTO Utilisateur (nom, prenom, email, mdp) VALUES ('Jane', 'Smith', 'jane@example.com', 'password456');



--- chiffre d'affaire par annee , mois ----
create or replace view chiffreAffaireMoisAnnee as
WITH months(month, year) AS (SELECT generate_series(1, 12), extract(year from current_date))SELECT months.month, months.year, coalesce(SUM(pe.montant),0) as montant FROM months LEFT JOIN PrelevementEnchere pe ON extract(month from pe.DatePrelevement) = months.month AND extract(year from pe.DatePrelevement) = months.year GROUP BY months.month, months.year;


--Chiffre d'affaire de l'application----
select sum(montant) from PrelevementEnchere;


INSERT INTO Enchere (idUtilisateur, description, prixMinimumVente,durreEnchere)
VALUES (1, 'Enchere pour un iPhone', 700,30);
INSERT INTO Enchere (idUtilisateur, description, prixMinimumVente,durreEnchere)
VALUES (2, 'Enchere pour une chemise',300,40);

INSERT INTO categorieproduit (idcategorieproduit, typecategorie) VALUES
(1, 'Alimentation'),
(2, 'Vêtements'),
(3, 'Electronique'),
(4, 'Jouets'),
(5, 'Livres');


INSERT INTO Produit (nomProduit, description, prix, numero_serie, datesortie, etat, provenance, idcategorieproduit) VALUES
('Pomme Granny Smith', 'Une pomme croquante et juteuse', 0.99, '123456789', '2022-01-01', 1, 'France', 1),
('T-Shirt en coton', 'Un t-shirt confortable en coton', 15.00, '987654321', '2021-05-01', 0, 'Chine', 2),
('Smartphone Samsung Galaxy S21', 'Un smartphone haut de gamme avec des caractéristiques exceptionnelles', 799.99, '111222333', '2021-01-01', 0, 'Corée', 3),
('Lego Star Wars', 'Un jouet de construction pour les fans de Star Wars', 49.99, '444555666', '2020-12-01', 1, 'Danemark', 4),
('Harry Potter et la Philosophie', 'Un livre populaire pour les fans de Harry Potter', 12.99, '777888999', '2019-07-01', 1, 'Bretagne', 5);

insert into produit_enchere values (1,1);
insert into produit_enchere values (1,2);
insert into produit_enchere values (1,3);
insert into produit_enchere values (2,1);
insert into produit_enchere values (2,3);
insert into produit_enchere values (2,4);



INSERT INTO HistoriqueOffre (idEnchere, idUtilisateur, montant_offre) VALUES (1, 1, 750);
INSERT INTO HistoriqueOffre (idEnchere, idUtilisateur, montant_offre) VALUES (1, 2, 800);
INSERT INTO HistoriqueOffre (idEnchere, idUtilisateur, montant_offre) VALUES (2, 2,600);
INSERT INTO HistoriqueOffre (idEnchere, idUtilisateur, montant_offre) VALUES (2, 1,900);


INSERT INTO Admin (email, mdp) VALUES ('admin@example.com', 'adminpassword');


--enchérisseur--

create or replace view HistoriqueEnchere as
select u.idutilisateur , u.nom , u.prenom , e.description ,
 e.prixminimumvente , e.durreenchere , e.dateheureenchere ,
  ho.idenchere , ho.montant_offre , ho.dateheureoffre 
  from Utilisateur u join HistoriqueOffre ho using(idUtilisateur) join ResultatEnchere re using(idEnchere) join Enchere e using(idenchere);

---vendeur--





INSERT INTO ResultatEnchere (idEnchere, idUtilisateur, prix_gagnant) VALUES (1, 1, 800);
INSERT INTO ResultatEnchere (idEnchere, idUtilisateur, prix_gagnant) VALUES (2, 2, 40);


insert into RechargementCompte(idUtilisateur,montant) values (1,300) , (1,400) ;


INSERT INTO Produit_Enchere (idEnchere, idProduit) VALUES (1, 1);
INSERT INTO Produit_Enchere (idEnchere, idProduit) VALUES (2, 4);
INSERT INTO Produit_Enchere (idEnchere, idProduit) VALUES (2, 5);

