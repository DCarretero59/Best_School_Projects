-- MySQL dump 10.13  Distrib 5.6.23, for Win64 (x86_64)
--
-- Host: localhost    Database: akinator
-- ------------------------------------------------------
-- Server version	5.6.24-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `answers`
--

DROP TABLE IF EXISTS `answers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `answers` (
  `idCharacter` int(11) NOT NULL,
  `idQuestion` int(11) NOT NULL,
  `Answer` double NOT NULL,
  KEY `Character_idx` (`idCharacter`),
  KEY `Question_idx` (`idQuestion`),
  CONSTRAINT `Character` FOREIGN KEY (`idCharacter`) REFERENCES `characters` (`idCharacter`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `Question` FOREIGN KEY (`idQuestion`) REFERENCES `questions` (`idquestions`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `answers`
--

LOCK TABLES `answers` WRITE;
/*!40000 ALTER TABLE `answers` DISABLE KEYS */;
INSERT INTO `answers` VALUES (1,17,1),(1,18,0),(1,19,0),(1,20,1),(1,21,0),(1,22,0),(1,23,0),(1,24,1),(1,25,0),(1,26,0),(1,27,1),(1,28,0),(1,29,0),(1,30,0),(1,31,0),(1,32,1),(1,33,0),(1,34,0),(1,35,0),(1,36,0),(1,37,0),(1,38,1),(1,39,0),(1,40,1),(1,41,0),(1,42,1),(1,43,0),(1,44,0),(1,45,0),(1,46,1),(1,47,0),(1,48,0),(1,49,0),(1,50,1),(1,51,0),(1,52,0),(1,53,0),(1,54,0),(1,55,0),(1,56,0),(1,57,0),(1,58,1),(1,59,0),(5,19,0),(5,20,1),(5,21,0),(5,45,1),(5,46,0),(5,42,1),(5,43,0),(5,44,0),(5,22,0),(5,23,0),(5,24,0),(5,25,0),(5,47,1),(5,48,0),(5,49,0),(5,50,0),(5,51,0),(5,38,1),(5,39,0),(5,40,0),(5,41,0),(5,17,1),(5,18,0),(5,26,0),(5,27,1),(5,28,0),(5,29,0),(5,30,0),(5,31,0),(5,34,1),(5,35,0),(5,36,0),(5,37,0),(5,32,0),(5,33,0),(5,52,1),(5,53,0),(5,54,0),(5,55,0),(5,56,0),(5,57,0),(5,58,0),(5,59,0),(6,19,0.5),(6,20,1),(6,21,0),(6,45,0),(6,46,0),(6,42,0),(6,43,1),(6,44,0),(6,22,1),(6,23,0),(6,24,0),(6,25,0),(6,47,0),(6,48,0),(6,49,0),(6,50,0),(6,51,1),(6,38,0.5),(6,39,0),(6,40,0),(6,41,0),(6,17,0),(6,18,0),(6,26,0),(6,27,0),(6,28,0),(6,29,1),(6,30,0),(6,31,0),(6,34,1),(6,35,0),(6,36,0),(6,37,0),(6,32,0),(6,33,0.5),(6,52,0),(6,53,0),(6,54,0),(6,55,1),(6,56,0),(6,57,0),(6,58,0),(6,59,0),(7,19,0),(7,20,1),(7,21,0),(7,45,0),(7,46,0),(7,42,1),(7,43,0),(7,44,0),(7,22,0),(7,23,0),(7,24,1),(7,25,0),(7,47,0),(7,48,0),(7,49,1),(7,50,0),(7,51,0),(7,38,1),(7,39,0),(7,40,0),(7,41,0),(7,17,1),(7,18,0),(7,26,0),(7,27,0),(7,28,0),(7,29,0),(7,30,1),(7,31,0),(7,34,0),(7,35,0),(7,36,1),(7,37,0),(7,32,1),(7,33,0),(7,52,0),(7,53,0),(7,54,0),(7,55,1),(7,56,0),(7,57,0),(7,58,0),(7,59,0),(8,19,0),(8,20,0),(8,21,0),(8,45,0),(8,46,0),(8,42,0),(8,43,0),(8,44,0),(8,22,0),(8,23,0),(8,24,0),(8,25,0),(8,47,0),(8,48,0),(8,49,0),(8,50,0),(8,51,0),(8,38,0),(8,39,0),(8,40,0),(8,41,0),(8,17,0),(8,18,0),(8,26,0),(8,27,0),(8,28,0),(8,29,0),(8,30,0),(8,31,0),(8,34,0),(8,35,0),(8,36,0),(8,37,0),(8,32,0),(8,33,0),(8,52,0),(8,53,0),(8,54,0),(8,55,0),(8,56,0),(8,57,0),(8,58,0),(8,59,0);
/*!40000 ALTER TABLE `answers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categories` (
  `idcategories` int(11) NOT NULL AUTO_INCREMENT,
  `Categories` varchar(45) NOT NULL,
  `Active` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`idcategories`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'Gender',1),(2,'Age',1),(3,'Ethnicity',1),(4,'Hair Color',1),(5,'Place of Birth',1),(6,'Origin',1),(7,'Fighting Style',1),(8,'Company',1),(9,'Civil Status',1),(10,'Family',1),(11,'Power 1(Movement)',1),(12,'Power 2(Power up)',1),(13,'Physical Appearance',1);
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `characters`
--

DROP TABLE IF EXISTS `characters`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `characters` (
  `idCharacter` int(11) NOT NULL AUTO_INCREMENT,
  `characterName` varchar(45) NOT NULL,
  `Active` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`idCharacter`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `characters`
--

LOCK TABLES `characters` WRITE;
/*!40000 ALTER TABLE `characters` DISABLE KEYS */;
INSERT INTO `characters` VALUES (1,'Batman',1),(5,'Superman',1),(6,'Spider-Man',1),(7,'Flash',1),(8,'Green Lantern',1);
/*!40000 ALTER TABLE `characters` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questions`
--

DROP TABLE IF EXISTS `questions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `questions` (
  `idquestions` int(11) NOT NULL AUTO_INCREMENT,
  `idCategory` int(11) NOT NULL,
  `Question` varchar(150) NOT NULL,
  `Active` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`idquestions`),
  KEY `Category_idx` (`idCategory`),
  CONSTRAINT `Category` FOREIGN KEY (`idCategory`) REFERENCES `categories` (`idcategories`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questions`
--

LOCK TABLES `questions` WRITE;
/*!40000 ALTER TABLE `questions` DISABLE KEYS */;
INSERT INTO `questions` VALUES (17,1,'Is your character male?',1),(18,1,'Is your character female?',1),(19,2,'Is your character a teenager?',1),(20,2,'Is your character a full grown man?',1),(21,2,'Is your character a child',1),(22,3,'Does your character have a African American origin?',1),(23,3,'Does your character have a Latin origin?',1),(24,3,'Does your character have a white descendancy?',1),(25,3,'Does your character belong to planet Earth?',1),(26,4,'Is your character bald?',1),(27,4,'Does your character have black hair?',1),(28,4,'Does your character have white hair?',1),(29,4,'Does your character have brown hair?',1),(30,4,'Is your character blond?',1),(31,4,'Does your character have red hair?',1),(32,5,'Was your character born on Earth?',1),(33,5,'Did your character belong to the Milky Way galaxy?',1),(34,6,'Was your character born with their powers?',1),(35,6,'Did your characters acquire his/her powers from an experiment?',1),(36,6,'Did your character acquire his powers by accident?',1),(37,6,'Was your character chosen to acquire those powers?',1),(38,7,'Does your character fight bare handed?',1),(39,7,'Does your character use energy projection as his main fighting style?',1),(40,7,'Does your character use any sort of weapons (rope, sword, shield, etc.)?',1),(41,7,'Does your character have mental powers?',1),(42,8,'Does your character belong to the DC Universe?',1),(43,8,'Does your character belong to the Marvel Universe?',1),(44,8,'Does your character belong to the Dark Horse Comics?',1),(45,9,'Has your character ever been married?',1),(46,9,'Has your character ever had children?',1),(47,10,'Can your character fly along with having superspeed?',1),(48,10,'Can your character fly?',1),(49,10,'Can your character run faster than the speed of sound?',1),(50,10,'Does your character perform parkour or use some kind of equipment to move across town?',1),(51,10,'Can your character perform unimaginable jumps?',1),(52,11,'Does your character has more than one powerr?',1),(53,11,'Does your character have super strenght?',1),(54,11,'Can your character use some kind of element (fire, water, air, etc...)',1),(55,11,'Can your character move objects through the use of their powers?',1),(56,11,'Can your character move through objects?',1),(57,11,'Can your  character use magic?',1),(58,11,'Does your character depend on gadgets to fight?',1),(59,12,'Is your character handicapped?',1);
/*!40000 ALTER TABLE `questions` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-05-12 19:36:20
