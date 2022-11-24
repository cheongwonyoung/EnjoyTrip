CREATE DATABASE  IF NOT EXISTS `enjoytrip` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `enjoytrip`;
-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: enjoytrip
-- ------------------------------------------------------
-- Server version	8.0.30

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `areacode`
--

DROP TABLE IF EXISTS `areacode`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `areacode` (
  `area_code` int NOT NULL,
  `area_name` varchar(20) NOT NULL,
  PRIMARY KEY (`area_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `board`
--

DROP TABLE IF EXISTS `board`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `board` (
  `article_no` int NOT NULL AUTO_INCREMENT,
  `user_id` varchar(16) DEFAULT NULL,
  `subject` varchar(100) DEFAULT NULL,
  `content` varchar(2000) DEFAULT NULL,
  `hit` int DEFAULT '0',
  `register_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`article_no`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `content`
--

DROP TABLE IF EXISTS `content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `content` (
  `content_id` int NOT NULL,
  `content_type_id` int NOT NULL,
  `addr1` varchar(100) DEFAULT NULL,
  `addr2` varchar(100) DEFAULT NULL,
  `tel` text,
  `firstimage` varchar(200) DEFAULT NULL,
  `secondimage` varchar(200) DEFAULT NULL,
  `readcount` int DEFAULT '0',
  `area_code` int DEFAULT NULL,
  `sigungu_code` int DEFAULT NULL,
  `lattitude` decimal(20,17) NOT NULL,
  `longitude` decimal(20,17) NOT NULL,
  `title` text NOT NULL,
  PRIMARY KEY (`content_id`),
  KEY `content_to_area_idx` (`area_code`),
  KEY `content_to_sigungu_fk_idx` (`sigungu_code`),
  KEY `content_to_type_fk_idx` (`content_type_id`),
  CONSTRAINT `content_to_area_fk` FOREIGN KEY (`area_code`) REFERENCES `areacode` (`area_code`) ON DELETE CASCADE,
  CONSTRAINT `content_to_sigungu_fk` FOREIGN KEY (`sigungu_code`) REFERENCES `sigungucode` (`sigungu_code`) ON DELETE CASCADE,
  CONSTRAINT `content_to_type_fk` FOREIGN KEY (`content_type_id`) REFERENCES `content_type` (`content_type_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `content_detail`
--

DROP TABLE IF EXISTS `content_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `content_detail` (
  `content_id` int NOT NULL,
  `overview` text,
  PRIMARY KEY (`content_id`),
  CONSTRAINT `detail_to_content_fk` FOREIGN KEY (`content_id`) REFERENCES `content` (`content_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `content_type`
--

DROP TABLE IF EXISTS `content_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `content_type` (
  `content_type_id` int NOT NULL,
  `content_type_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`content_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `do`
--

DROP TABLE IF EXISTS `do`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `do` (
  `area_code` int DEFAULT NULL,
  `name` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `members`
--

DROP TABLE IF EXISTS `members`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `members` (
  `user_email` varchar(40) NOT NULL,
  `user_password` varchar(45) NOT NULL,
  `user_name` varchar(50) DEFAULT NULL,
  `join_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `token` varchar(1000) DEFAULT NULL,
  `profile_img` varchar(200) DEFAULT NULL,
  `save_folder` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`user_email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `memo`
--

DROP TABLE IF EXISTS `memo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `memo` (
  `memo_no` int NOT NULL AUTO_INCREMENT,
  `user_id` varchar(16) DEFAULT NULL,
  `comment` varchar(500) DEFAULT NULL,
  `memo_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `article_no` int DEFAULT NULL,
  PRIMARY KEY (`memo_no`),
  KEY `memo_to_board_article_no_fk` (`article_no`),
  KEY `memo_to_member_fk_idx` (`user_id`),
  CONSTRAINT `memo_to_board_article_no_fk` FOREIGN KEY (`article_no`) REFERENCES `ssafyweb`.`board` (`article_no`),
  CONSTRAINT `memo_to_member_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `ssafyweb`.`members` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `plan_board`
--

DROP TABLE IF EXISTS `plan_board`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `plan_board` (
  `plan_id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(45) DEFAULT NULL,
  `hit` int DEFAULT '0',
  `register_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `thumbnail` text,
  `user_email` varchar(40) NOT NULL,
  PRIMARY KEY (`plan_id`),
  KEY `planboard_to_members_fk_idx` (`user_email`),
  CONSTRAINT `planboard_to_members_fk` FOREIGN KEY (`user_email`) REFERENCES `members` (`user_email`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `plan_content`
--

DROP TABLE IF EXISTS `plan_content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `plan_content` (
  `plan_id` int NOT NULL,
  `content_id` int NOT NULL,
  `content_desc` text,
  KEY `plancontent_content_fk_idx` (`content_id`),
  KEY `content_to_board_fk_idx` (`plan_id`),
  CONSTRAINT `content_to_board_fk` FOREIGN KEY (`plan_id`) REFERENCES `plan_board` (`plan_id`) ON DELETE CASCADE,
  CONSTRAINT `plancontent_to_content_fk` FOREIGN KEY (`content_id`) REFERENCES `content` (`content_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sigungu`
--

DROP TABLE IF EXISTS `sigungu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sigungu` (
  `areaCode` int DEFAULT NULL,
  `sigunguCode` int DEFAULT NULL,
  `name` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sigungucode`
--

DROP TABLE IF EXISTS `sigungucode`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sigungucode` (
  `area_code` int NOT NULL,
  `sigungu_code` int NOT NULL,
  `sigungu_name` varchar(20) NOT NULL,
  PRIMARY KEY (`sigungu_code`,`area_code`),
  KEY `sigungu_to_area_fk` (`area_code`),
  CONSTRAINT `sigungu_to_area_fk` FOREIGN KEY (`area_code`) REFERENCES `areacode` (`area_code`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `travelspot`
--

DROP TABLE IF EXISTS `travelspot`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `travelspot` (
  `idx` int DEFAULT NULL,
  `addr1` text,
  `addr2` text,
  `areacode` int DEFAULT NULL,
  `booktour` text,
  `cat1` text,
  `cat2` text,
  `cat3` text,
  `contentid` int DEFAULT NULL,
  `contenttypeid` int DEFAULT NULL,
  `createdtime` bigint DEFAULT NULL,
  `firstimage` text,
  `firstimage2` text,
  `mapx` double DEFAULT NULL,
  `mapy` double DEFAULT NULL,
  `mlevel` int DEFAULT NULL,
  `modifiedtime` bigint DEFAULT NULL,
  `readcount` int DEFAULT NULL,
  `sigungucode` int DEFAULT NULL,
  `tel` text,
  `title` text,
  `zipcode` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-11-25  4:41:36
