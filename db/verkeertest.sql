-- MySQL dump 10.16  Distrib 10.1.11-MariaDB, for Win64 (AMD64)
--
-- Host: localhost    Database: verkeer
-- ------------------------------------------------------
-- Server version	10.1.11-MariaDB

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
-- Table structure for table `route`
--

DROP TABLE IF EXISTS `route`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `route` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(255) NOT NULL,
  `Distance` double NOT NULL COMMENT '(in meters)',
  `FromAddress` varchar(255) NOT NULL,
  `FromLatitude` double NOT NULL,
  `FromLongitude` double NOT NULL,
  `ToAddress` varchar(255) NOT NULL,
  `ToLatitude` double NOT NULL,
  `ToLongitude` double NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `route`
--

LOCK TABLES `route` WRITE;
/*!40000 ALTER TABLE `route` DISABLE KEYS */;
INSERT INTO `route` VALUES (11,'Rooigemlaan (R40) northbound',0,'Drongensesteenweg',51.0560905,3.6951634,'Palinghuizen',51.0663037,3.6996797),(12,'Rooigemlaan (R40) southbound ',0,'Palinghuizen',51.0663037,3.6996797,'Drongensesteenweg',51.0560905,3.6951634),(13,'Gasmeterlaan (R40) eastbound',0,'Palinghuizen',51.0663403,3.6996189,'Neuseplein',51.0675256,3.7279489),(14,'Nieuwevaart (R40) westbound ',0,'Neuseplein',51.0675256,3.7279489,'Palinghuizen',51.0663403,3.6996189),(15,'Dok-Noord (R40) southbound',0,'Neuseplein',51.0671252,3.7265261,'Dampoort',51.0566022,3.7384317),(16,'Dok-Noord (R40) northbound ',0,'Dampoort',51.0566022,3.7384317,'Neuseplein',51.0671252,3.7265261),(17,'Blaisantvest (N430) eastbound',0,'Einde Were',51.0524408,3.6997995,'Neuseplein',51.0674662,3.7277666),(18,'Blaisantvest (N430) westbound ',0,'Neuseplein',51.0674662,3.7277666,'Einde Were',51.0524408,3.6997995),(19,'Keizer Karelstraat northbound',0,'Sint-Lievenslaan',51.0383924,3.7368828,'Neuseplein',51.0674005,3.7271503),(20,'Keizer Karelstraat southbound ',0,'Neuseplein',51.0674005,3.7271503,'Sint-Lievenslaan',51.0383924,3.7368828),(21,'Kennedylaan (R4) southbound',0,'E34',51.1930377,3.8300059,'Port Arthurlaan',51.0737354,3.7335761),(22,'Kennedylaan (R4) northbound ',0,'Port Arthurlaan',51.0737354,3.7335761,'E34',51.1930377,3.8300059),(23,'Binnenring-Drongen (R4) northbound',0,'Sluisweg',51.0876298,3.7564962,'Industrieweg',51.0140239,3.7265974),(24,'Buitenring-Drongen (R4) southbound ',0,'Industrieweg',51.0140239,3.7265974,'Sluisweg',51.0876298,3.7564962),(25,'Paryslaan (R4) northbound',0,'Industrieweg',51.0856268,3.6693085,'E34',51.1976122,3.7856338),(26,'Paryslaan (R4) southbound ',0,'E34',51.1976122,3.7856338,'Industrieweg',51.0856268,3.6693085),(27,'Drongensesteenweg (N466) eastbound',0,'E40',51.0387794,3.6271859,'Rooigemlaan',51.0562087,3.6953683),(28,'Drongensesteenweg (N466) westbound ',0,'Rooigemlaan',51.0562087,3.6953683,'E40',51.0387794,3.6271859),(29,'Antwerpsesteenweg (N70) westbound',0,'R4',51.0838685,3.7949244,'Dampoort',51.0572052,3.7391772),(30,'Antwerpsesteenweg (N70) eastbound ',0,'Dampoort',51.0572052,3.7391772,'R4',51.0838685,3.7949244),(31,'B401 (northbound)',0,'E17',51.0229775,3.7354755,'Vlaanderenstraat',51.0485311,3.7313863),(32,'B401 (southbound) ',0,'Vlaanderenstraat',51.0485311,3.7313863,'E17',51.0229775,3.7354755),(33,'Brusselsesteenweg (N9) westbound',0,'R4',51.0103155,3.7912421,'Scheldekaai',51.0415136,3.7409548),(34,'Brusselsesteenweg (N9) eastbound ',0,'Scheldekaai',51.0415136,3.7409548,'R4',51.0103155,3.7912421),(35,'Oudenaardsesteenweg (N60) northbound',0,'E17',50.9802196,3.667998,'R40',51.0384331,3.726382),(36,'Oudenaardsesteenweg (N60) southbound ',0,'R40',51.0384331,3.726382,'E17',50.9802196,3.667998),(37,'Brugsevaart (N9) southbound',0,'R4',51.085339,3.6634923,'Gebroeders de Smetstraat',51.0642801,3.702627),(38,'Brugsevaart (N9) northbound ',0,'Gebroeders de Smetstraat',51.0642801,3.702627,'R4',51.085339,3.6634923);
/*!40000 ALTER TABLE `route` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `routedata`
--

DROP TABLE IF EXISTS `routedata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `routedata` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `RouteId` int(11) NOT NULL,
  `Timestamp` datetime NOT NULL,
  `Provider` int(11) NOT NULL,
  `TravelTime` int(11) NOT NULL COMMENT 'in seconds',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `routedata`
--

LOCK TABLES `routedata` WRITE;
/*!40000 ALTER TABLE `routedata` DISABLE KEYS */;
/*!40000 ALTER TABLE `routedata` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-02-14 12:23:18
