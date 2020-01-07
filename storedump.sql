-- MySQL dump 10.13  Distrib 8.0.18, for osx10.15 (x86_64)
--
-- Host: localhost    Database: store
-- ------------------------------------------------------
-- Server version	8.0.18

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Customer`
--

DROP TABLE IF EXISTS `Customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Customer` (
  `CustomerID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `CustomerName` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `CustomerAddress` varchar(200) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`CustomerID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Customer`
--

LOCK TABLES `Customer` WRITE;
/*!40000 ALTER TABLE `Customer` DISABLE KEYS */;
INSERT INTO `Customer` VALUES (2,'Amar','Pejaten'),(5,'Alvi','Jambi'),(7,'Saint','Home');
/*!40000 ALTER TABLE `Customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Employee`
--

DROP TABLE IF EXISTS `Employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Employee` (
  `EmployeeID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `EmployeeName` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `EmployeeAddress` varchar(200) COLLATE utf8mb4_general_ci NOT NULL,
  `Position` varchar(20) COLLATE utf8mb4_general_ci NOT NULL,
  `Salary` int(10) unsigned NOT NULL,
  PRIMARY KEY (`EmployeeID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Employee`
--

LOCK TABLES `Employee` WRITE;
/*!40000 ALTER TABLE `Employee` DISABLE KEYS */;
INSERT INTO `Employee` VALUES (1,'Marc','kemayoran','kasir',3550000),(3,'Septian','Mansion','Akuntan',7000000);
/*!40000 ALTER TABLE `Employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Item`
--

DROP TABLE IF EXISTS `Item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Item` (
  `ItemID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `ItemName` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `ItemQty` int(10) unsigned NOT NULL,
  `Description` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `Price` int(10) unsigned NOT NULL,
  PRIMARY KEY (`ItemID`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Item`
--

LOCK TABLES `Item` WRITE;
/*!40000 ALTER TABLE `Item` DISABLE KEYS */;
INSERT INTO `Item` VALUES (6,'Bearing 2409',117,'NTN',35000),(10,'miasdf',1254,'asdasd',42141),(11,'Bearing',1285,'asdasd',123123),(13,'asfadgg',123,'',123),(14,'asdf',123,'',42),(15,'saff',213,'',2313),(16,'Fan',20,'kps',15000);
/*!40000 ALTER TABLE `Item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Purchase`
--

DROP TABLE IF EXISTS `Purchase`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Purchase` (
  `PurchaseID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `SupplierID` int(10) unsigned NOT NULL,
  `DatenTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`PurchaseID`),
  KEY `purchase_ibfk_1` (`SupplierID`),
  CONSTRAINT `purchase_ibfk_1` FOREIGN KEY (`SupplierID`) REFERENCES `supplier` (`SupplierID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Purchase`
--

LOCK TABLES `Purchase` WRITE;
/*!40000 ALTER TABLE `Purchase` DISABLE KEYS */;
INSERT INTO `Purchase` VALUES (1,8,'2020-01-07 10:13:26'),(2,3,'2020-01-07 10:15:56'),(3,9,'2020-01-07 10:16:27'),(4,4,'2020-01-07 10:37:54'),(6,4,'2020-01-07 15:20:49');
/*!40000 ALTER TABLE `Purchase` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PurchaseDetail`
--

DROP TABLE IF EXISTS `PurchaseDetail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `PurchaseDetail` (
  `PurchaseDetailID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `PurchaseID` int(10) unsigned NOT NULL,
  `EmployeeID` int(10) unsigned NOT NULL,
  `ItemID` int(10) unsigned NOT NULL,
  `ItemQty` int(10) unsigned NOT NULL,
  `Price` int(10) unsigned NOT NULL,
  PRIMARY KEY (`PurchaseDetailID`),
  KEY `purchasedetail_ibfk_1` (`PurchaseID`),
  KEY `purchasedetail_ibfk_2` (`EmployeeID`),
  KEY `purchasedetail_ibfk_3` (`ItemID`),
  CONSTRAINT `purchasedetail_ibfk_1` FOREIGN KEY (`PurchaseID`) REFERENCES `purchase` (`PurchaseID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `purchasedetail_ibfk_2` FOREIGN KEY (`EmployeeID`) REFERENCES `employee` (`EmployeeID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `purchasedetail_ibfk_3` FOREIGN KEY (`ItemID`) REFERENCES `item` (`ItemID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PurchaseDetail`
--

LOCK TABLES `PurchaseDetail` WRITE;
/*!40000 ALTER TABLE `PurchaseDetail` DISABLE KEYS */;
INSERT INTO `PurchaseDetail` VALUES (1,4,3,11,23,110000),(2,6,1,10,20,40000),(4,6,1,11,20,100000),(5,6,1,6,100,29000);
/*!40000 ALTER TABLE `PurchaseDetail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sale`
--

DROP TABLE IF EXISTS `sale`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sale` (
  `SaleID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `CustomerID` int(10) unsigned NOT NULL,
  `DatenTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`SaleID`),
  KEY `sale_ibfk_1` (`CustomerID`),
  CONSTRAINT `sale_ibfk_1` FOREIGN KEY (`CustomerID`) REFERENCES `customer` (`CustomerID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sale`
--

LOCK TABLES `sale` WRITE;
/*!40000 ALTER TABLE `sale` DISABLE KEYS */;
INSERT INTO `sale` VALUES (1,2,'2019-12-31 17:00:00'),(8,2,'2020-01-06 22:00:09'),(12,2,'2020-01-07 10:05:09'),(13,5,'2020-01-07 14:56:13'),(14,2,'2020-01-07 14:58:21');
/*!40000 ALTER TABLE `sale` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `saledetail`
--

DROP TABLE IF EXISTS `saledetail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `saledetail` (
  `SaleDetailID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `SaleID` int(10) unsigned NOT NULL,
  `EmployeeID` int(10) unsigned NOT NULL,
  `ItemID` int(10) unsigned NOT NULL,
  `ItemQty` int(10) unsigned NOT NULL,
  `Price` int(10) unsigned NOT NULL,
  PRIMARY KEY (`SaleDetailID`),
  KEY `saledetail_ibfk_1` (`SaleID`),
  KEY `saledetail_ibfk_2` (`EmployeeID`),
  KEY `saledetail_ibfk_3` (`ItemID`),
  CONSTRAINT `saledetail_ibfk_1` FOREIGN KEY (`SaleID`) REFERENCES `sale` (`SaleID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `saledetail_ibfk_2` FOREIGN KEY (`EmployeeID`) REFERENCES `employee` (`EmployeeID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `saledetail_ibfk_3` FOREIGN KEY (`ItemID`) REFERENCES `item` (`ItemID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `saledetail`
--

LOCK TABLES `saledetail` WRITE;
/*!40000 ALTER TABLE `saledetail` DISABLE KEYS */;
INSERT INTO `saledetail` VALUES (4,1,1,6,17,45000),(6,1,1,13,2,500),(11,8,1,10,6,4500000),(12,8,1,10,6,4500000);
/*!40000 ALTER TABLE `saledetail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Supplier`
--

DROP TABLE IF EXISTS `Supplier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Supplier` (
  `SupplierID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `SupplierName` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `SupplierAddress` varchar(200) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`SupplierID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Supplier`
--

LOCK TABLES `Supplier` WRITE;
/*!40000 ALTER TABLE `Supplier` DISABLE KEYS */;
INSERT INTO `Supplier` VALUES (3,'Kevin','Sudirman'),(4,'Septian','Kemayoran'),(5,'Purnama','Kalimantan'),(8,'Ade','JakTim'),(9,'kin','asdf');
/*!40000 ALTER TABLE `Supplier` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-01-08  0:34:41
