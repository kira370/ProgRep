
DROP TABLE IF EXISTS `cd`;
CREATE TABLE `cd` (
  `idDoc` int(50) NOT NULL,
  `nom` varchar(25) NOT NULL,
  `auteur` varchar(25) NOT NULL,
  `compositeur` varchar(25) NOT NULL,
  `annee` year(4) DEFAULT NULL,
  PRIMARY KEY (`idDoc`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/

DROP TABLE IF EXISTS `document`;
CREATE TABLE `document` (
  `id` int(255) NOT NULL,
  `idDoc` int(255) NOT NULL,
  `idUser` int(255) DEFAULT NULL,
  `typeDoc` enum('cd','livre','dvd') NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idUser` (`idUser`),
  CONSTRAINT `document_ibfk_1` FOREIGN KEY (`idUser`) REFERENCES `utilisateur` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/
DROP TABLE IF EXISTS `dvd`;
CREATE TABLE `dvd` (
  `idDoc` int(255) NOT NULL,
  `nom` varchar(255) NOT NULL,
  `realisateur` varchar(255) NOT NULL,
  `producteur` varchar(255) NOT NULL,
  `sortie` year(4) NOT NULL,
  PRIMARY KEY (`idDoc`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/

DROP TABLE IF EXISTS `livre`;
CREATE TABLE `livre` (
  `idDoc` int(255) NOT NULL,
  `nom` varchar(25) NOT NULL,
  `auteur` varchar(25) NOT NULL,
  `annee` year(4) NOT NULL,
  PRIMARY KEY (`idDoc`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/

DROP TABLE IF EXISTS `utilisateur`;
CREATE TABLE `utilisateur` (
  `id` int(255) NOT NULL,
  `login` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `nomPrenom` varchar(20) NOT NULL,
  `isSecretaire` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE SEQUENCE cd_seq START WITH 1;
CREATE SEQUENCE document_seq START WITH 1;
CREATE SEQUENCE dvd_seq START WITH 1;
CREATE SEQUENCE livre_seq START WITH 1;
CREATE SEQUENCE utilisateur_seq START WITH 1;
/

CREATE OR REPLACE TRIGGER cd_bir 
BEFORE INSERT ON cd 
FOR EACH ROW

BEGIN
  SELECT cd_seq.NEXTVAL
  INTO   :new.id
  FROM   dual;
END;
/

CREATE OR REPLACE TRIGGER document_bir 
BEFORE INSERT ON document 
FOR EACH ROW

BEGIN
  SELECT document_seq.NEXTVAL
  INTO   :new.id
  FROM   dual;
END;
/

CREATE OR REPLACE TRIGGER dvd_bir 
BEFORE INSERT ON dvd 
FOR EACH ROW

BEGIN
  SELECT dvd_seq.NEXTVAL
  INTO   :new.id
  FROM   dual;
END;
/

CREATE OR REPLACE TRIGGER livre_bir 
BEFORE INSERT ON livre 
FOR EACH ROW

BEGIN
  SELECT livre_seq.NEXTVAL
  INTO   :new.id
  FROM   dual;
END;
/

CREATE OR REPLACE TRIGGER utilisateur_bir 
BEFORE INSERT ON utilisateur 
FOR EACH ROW

BEGIN
  SELECT utilisateur_seq.NEXTVAL
  INTO   :new.id
  FROM   dual;
END;
/


INSERT INTO `document` VALUES ('1', '1', '1', 'livre');
INSERT INTO `document` VALUES ('2', '2', null, 'livre');
INSERT INTO `document` VALUES ('3', '1', null, 'dvd');
INSERT INTO `dvd` VALUES ('1', 'Interstellar', 'Christopher Nolan', 'Christopher Nolan, Lynda Obst, Emma Thomas', '2014');
INSERT INTO `livre` VALUES ('1', 'La vie c\'est la vie', 'Delannoy Jimmy', '2017');
INSERT INTO `livre` VALUES ('2', 'DCF77', 'Delannoy Jimmy', '2002');
INSERT INTO `utilisateur` VALUES ('1', 'delannoy', 'delannoy', 'Jimmy Delannoy', '0');
