/*
Navicat MySQL Data Transfer

Source Server         : myBDD
Source Server Version : 50505
Source Host           : localhost:3306
Source Database       : biblio

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2018-03-11 10:53:19
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for cd
-- ----------------------------
DROP TABLE IF EXISTS `cd`;
CREATE TABLE `cd` (
  `idDoc` int(50) NOT NULL AUTO_INCREMENT,
  `nom` varchar(25) NOT NULL,
  `auteur` varchar(25) NOT NULL,
  `compositeur` varchar(25) NOT NULL,
  `annee` year(4) DEFAULT NULL,
  PRIMARY KEY (`idDoc`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of cd
-- ----------------------------

-- ----------------------------
-- Table structure for document
-- ----------------------------
DROP TABLE IF EXISTS `document`;
CREATE TABLE `document` (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `idDoc` int(255) NOT NULL,
  `idUser` int(255) DEFAULT NULL,
  `typeDoc` enum('cd','livre','dvd') NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idUser` (`idUser`),
  CONSTRAINT `document_ibfk_1` FOREIGN KEY (`idUser`) REFERENCES `utilisateur` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of document
-- ----------------------------
INSERT INTO `document` VALUES ('1', '1', '1', 'livre');
INSERT INTO `document` VALUES ('2', '2', null, 'livre');
INSERT INTO `document` VALUES ('3', '1', null, 'dvd');

-- ----------------------------
-- Table structure for dvd
-- ----------------------------
DROP TABLE IF EXISTS `dvd`;
CREATE TABLE `dvd` (
  `idDoc` int(255) NOT NULL AUTO_INCREMENT,
  `nom` varchar(255) NOT NULL,
  `realisateur` varchar(255) NOT NULL,
  `producteur` varchar(255) NOT NULL,
  `sortie` year(4) NOT NULL,
  PRIMARY KEY (`idDoc`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of dvd
-- ----------------------------
INSERT INTO `dvd` VALUES ('1', 'Interstellar', 'Christopher Nolan', 'Christopher Nolan, Lynda Obst, Emma Thomas', '2014');

-- ----------------------------
-- Table structure for livre
-- ----------------------------
DROP TABLE IF EXISTS `livre`;
CREATE TABLE `livre` (
  `idDoc` int(255) NOT NULL AUTO_INCREMENT,
  `nom` varchar(25) NOT NULL,
  `auteur` varchar(25) NOT NULL,
  `annee` year(4) NOT NULL,
  PRIMARY KEY (`idDoc`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of livre
-- ----------------------------
INSERT INTO `livre` VALUES ('1', 'La vie c\'est la vie', 'Delannoy Jimmy', '2017');
INSERT INTO `livre` VALUES ('2', 'DCF77', 'Delannoy Jimmy', '2002');

-- ----------------------------
-- Table structure for utilisateur
-- ----------------------------
DROP TABLE IF EXISTS `utilisateur`;
CREATE TABLE `utilisateur` (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `login` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `nomPrenom` varchar(20) NOT NULL,
  `isSecretaire` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of utilisateur
-- ----------------------------
INSERT INTO `utilisateur` VALUES ('1', 'delannoy', 'delannoy', 'Jimmy Delannoy', '0');
