CREATE DATABASE  IF NOT EXISTS `giftr` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `giftr`;

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
-- Table structure for table `Gender`
--

DROP TABLE IF EXISTS `Gender`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Gender` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Gender`
--

LOCK TABLES `Gender` WRITE;
/*!40000 ALTER TABLE `Gender` DISABLE KEYS */;
INSERT INTO `Gender` VALUES (3,'Female'),(2,'Male'),(1,'Unknown');
/*!40000 ALTER TABLE `Gender` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `GiftType`
--

DROP TABLE IF EXISTS `GiftType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `GiftType` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GiftType`
--

LOCK TABLES `GiftType` WRITE;
/*!40000 ALTER TABLE `GiftType` DISABLE KEYS */;
INSERT INTO `GiftType` VALUES (4,'Both'),(3,'Gag'),(2,'Practical'),(1,'Unknown');
/*!40000 ALTER TABLE `GiftType` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Match`
--

DROP TABLE IF EXISTS `Match`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Match` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `status` int(11) unsigned NOT NULL,
  `created` datetime NOT NULL,
  `lastModified` datetime NOT NULL,
  `priceMin` int(11) DEFAULT '0',
  `priceMax` int(11) DEFAULT '0',
  `user1` int(11) unsigned NOT NULL,
  `user2` int(11) unsigned NOT NULL,
  `user1Transaction` int(11) DEFAULT NULL,
  `user2Transaction` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_match_status_idx` (`status`),
  KEY `fk_match_user1_idx` (`user1`),
  KEY `fk_match_user2_idx` (`user2`),
  CONSTRAINT `fk_match_status` FOREIGN KEY (`status`) REFERENCES `MatchStatus` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_match_user1` FOREIGN KEY (`user1`) REFERENCES `User` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_match_user2` FOREIGN KEY (`user2`) REFERENCES `User` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Match`
--

LOCK TABLES `Match` WRITE;
/*!40000 ALTER TABLE `Match` DISABLE KEYS */;
/*!40000 ALTER TABLE `Match` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MatchStatus`
--

DROP TABLE IF EXISTS `MatchStatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MatchStatus` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MatchStatus`
--

LOCK TABLES `MatchStatus` WRITE;
/*!40000 ALTER TABLE `MatchStatus` DISABLE KEYS */;
INSERT INTO `MatchStatus` VALUES (2,'Completed'),(1,'Pending');
/*!40000 ALTER TABLE `MatchStatus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `User` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(45) DEFAULT NULL,
  `status` int(11) unsigned NOT NULL,
  `joinDate` datetime NOT NULL,
  `lastActive` datetime NOT NULL,
  `gender` int(11) unsigned NOT NULL,
  `location` tinytext,
  `giftType` int(11) unsigned NOT NULL,
  `interests` tinytext,
  `priceMin` int(11) unsigned DEFAULT '0',
  `priceMax` int(11) unsigned DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  KEY `fk_user_status_idx` (`status`),
  KEY `fk_user_gender_idx` (`gender`),
  KEY `fk_user_gifttype_idx` (`giftType`),
  CONSTRAINT `fk_user_gender` FOREIGN KEY (`gender`) REFERENCES `Gender` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_gifttype` FOREIGN KEY (`giftType`) REFERENCES `GiftType` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_status` FOREIGN KEY (`status`) REFERENCES `UserStatus` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UserStatus`
--

DROP TABLE IF EXISTS `UserStatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UserStatus` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserStatus`
--

LOCK TABLES `UserStatus` WRITE;
/*!40000 ALTER TABLE `UserStatus` DISABLE KEYS */;
INSERT INTO `UserStatus` VALUES (1,'Active'),(3,'Deleted'),(4,'Flagged'),(2,'Inactive');
/*!40000 ALTER TABLE `UserStatus` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
