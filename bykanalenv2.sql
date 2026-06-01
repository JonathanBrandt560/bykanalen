CREATE DATABASE  IF NOT EXISTS `bykanalen` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `bykanalen`;
-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: localhost    Database: bykanalen
-- ------------------------------------------------------
-- Server version	8.0.44

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
-- Table structure for table `event_registrations`
--

DROP TABLE IF EXISTS `event_registrations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `event_registrations` (
  `user_id` bigint NOT NULL,
  `event_id` bigint NOT NULL,
  `registration_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`,`event_id`),
  KEY `idx_event_registrations_event_id` (`event_id`),
  CONSTRAINT `fk_event_registrations_event` FOREIGN KEY (`event_id`) REFERENCES `events` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_event_registrations_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event_registrations`
--

LOCK TABLES `event_registrations` WRITE;
/*!40000 ALTER TABLE `event_registrations` DISABLE KEYS */;
INSERT INTO `event_registrations` VALUES (1,1,'2024-04-20 10:15:00'),(1,3,'2024-10-15 09:30:00'),(1,4,'2024-02-20 10:00:00'),(1,6,'2024-05-25 10:30:00'),(1,7,'2024-03-20 11:00:00'),(1,11,'2024-09-10 10:30:00'),(1,14,'2024-03-25 11:20:00'),(1,15,'2024-06-20 10:30:00'),(2,1,'2024-04-21 14:30:00'),(2,2,'2024-05-05 14:00:00'),(2,5,'2024-04-10 15:30:00'),(2,8,'2024-06-10 09:30:00'),(2,9,'2024-05-15 10:45:00'),(2,11,'2024-09-15 14:45:00'),(3,1,'2024-04-22 09:45:00'),(3,3,'2024-10-20 14:15:00'),(3,4,'2024-02-25 14:30:00'),(3,6,'2024-06-01 14:15:00'),(3,7,'2024-03-25 14:30:00'),(3,10,'2024-04-25 16:30:00'),(3,11,'2024-09-20 09:20:00'),(3,15,'2024-06-25 15:45:00'),(3,16,'2024-04-10 14:30:00'),(4,1,'2024-04-23 16:20:00'),(4,2,'2024-05-08 10:20:00'),(4,8,'2024-06-15 14:45:00'),(4,9,'2024-05-20 14:30:00'),(4,11,'2024-10-01 15:10:00'),(4,12,'2024-05-10 09:00:00'),(4,13,'2024-07-10 10:15:00'),(5,1,'2024-05-01 11:00:00'),(5,3,'2024-11-01 10:45:00'),(5,4,'2024-03-01 09:15:00'),(5,5,'2024-04-15 10:45:00'),(5,6,'2024-06-05 09:40:00'),(5,7,'2024-04-01 09:45:00'),(5,10,'2024-05-01 14:15:00'),(5,11,'2024-10-05 11:40:00'),(5,14,'2024-04-01 14:50:00'),(5,15,'2024-07-01 09:20:00'),(5,16,'2024-04-20 10:15:00'),(6,1,'2024-05-05 13:45:00'),(6,2,'2024-05-12 16:45:00'),(6,8,'2024-06-20 10:20:00'),(6,9,'2024-05-25 09:55:00'),(6,11,'2024-10-10 13:55:00'),(7,1,'2024-05-10 10:30:00'),(7,3,'2024-11-05 16:20:00'),(7,4,'2024-03-05 16:45:00'),(7,6,'2024-06-10 15:25:00'),(7,7,'2024-04-10 16:20:00'),(7,10,'2024-05-10 15:45:00'),(7,11,'2024-10-15 10:25:00'),(7,15,'2024-07-05 14:10:00'),(7,16,'2024-04-25 16:40:00'),(8,1,'2024-05-15 15:15:00'),(8,2,'2024-05-20 11:30:00'),(8,8,'2024-06-25 16:05:00'),(8,11,'2024-10-20 16:30:00'),(8,12,'2024-05-15 14:20:00'),(8,13,'2024-07-15 14:40:00'),(9,1,'2024-06-01 09:00:00'),(9,2,'2024-06-01 09:15:00'),(9,3,'2024-11-10 11:00:00'),(9,4,'2024-03-10 11:20:00'),(9,6,'2024-06-15 11:50:00'),(9,7,'2024-04-20 10:55:00'),(9,11,'2024-10-25 12:15:00'),(9,14,'2024-04-10 10:35:00'),(9,15,'2024-07-10 11:55:00'),(10,1,'2024-06-05 12:30:00'),(10,5,'2024-04-20 14:20:00'),(10,8,'2024-07-01 11:35:00'),(10,9,'2024-06-01 15:20:00'),(10,11,'2024-11-01 14:50:00'),(11,2,'2024-06-05 13:50:00'),(11,3,'2024-11-15 13:40:00'),(11,4,'2024-03-15 13:50:00'),(11,6,'2024-06-20 13:35:00'),(11,7,'2024-05-01 13:15:00'),(11,11,'2024-11-05 09:35:00'),(11,15,'2024-07-15 13:30:00'),(12,2,'2024-06-10 15:25:00'),(12,5,'2024-04-25 09:55:00'),(12,8,'2024-07-05 13:50:00'),(12,9,'2024-06-05 11:40:00'),(13,3,'2024-11-20 09:55:00'),(13,6,'2024-06-25 10:05:00'),(13,7,'2024-05-05 15:40:00'),(13,12,'2024-05-20 10:45:00'),(13,13,'2024-07-20 09:55:00'),(13,14,'2024-04-20 16:05:00'),(14,2,'2024-06-15 10:40:00'),(14,8,'2024-07-10 09:15:00'),(14,15,'2024-07-20 10:05:00'),(15,3,'2024-11-25 15:30:00'),(15,6,'2024-07-01 16:45:00'),(15,10,'2024-05-20 10:20:00'),(15,16,'2024-05-01 11:25:00'),(16,2,'2024-06-18 14:10:00'),(16,8,'2024-07-15 15:40:00'),(16,9,'2024-06-10 13:25:00'),(16,12,'2024-06-01 16:10:00'),(16,14,'2024-05-01 12:40:00'),(17,3,'2024-12-01 10:20:00'),(17,5,'2024-05-02 16:10:00'),(17,10,'2024-06-01 16:55:00'),(17,16,'2024-05-10 09:50:00'),(18,2,'2024-06-20 11:55:00'),(18,8,'2024-07-20 10:25:00'),(18,9,'2024-06-15 10:05:00'),(18,12,'2024-06-05 11:30:00'),(18,13,'2024-08-01 15:25:00');
/*!40000 ALTER TABLE `event_registrations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `events`
--

DROP TABLE IF EXISTS `events`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `events` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` text COLLATE utf8mb4_unicode_ci,
  `image` longblob,
  `publish_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `start_date` datetime NOT NULL,
  `end_date` datetime DEFAULT NULL,
  `close_registration_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_events_start_date` (`start_date`),
  KEY `idx_events_publish_date` (`publish_date`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `events`
--

LOCK TABLES `events` WRITE;
/*!40000 ALTER TABLE `events` DISABLE KEYS */;
INSERT INTO `events` VALUES (1,'Midsommarfirande i Folkparken','Traditionell midsommarfirande med kring dansbana, mat och dryck. Vi startar klockan 14:00 med grejer för barn och uppvärmningen på dansbanan. Midvinterblotets klassiska prog både från logen och från dansbandet Fridas Blomst. Alla är välkomna!',NULL,'2024-04-15 10:00:00','2024-06-21 14:00:00','2024-06-21 23:00:00','2024-06-19 23:59:00'),(2,'Dansbandskväll med Hasse Staffanz','Välkommen till en fantastisk kväll med dansbandet Hasse Staffanz och deras klassiska låtar! Dansbanan öppen från 19:00, musik börjar 20:00. Mat och dryck finns tillgängligt. Detta blir en minnesvärrd kväll för all dansande ungdom och gamla dansare!',NULL,'2024-05-01 09:00:00','2024-07-13 19:00:00','2024-07-13 23:30:00','2024-07-10 23:59:00'),(3,'Lucia-firande på Torget','År fest med luciatåg, sång och glögg. Vi firar Lucia tillsammans med klassiska lucialåtar. Lucia kommer att ledas av en av våra ungdomar och vi avslutar med fika och pepparkakor. Börjar 17:30 på torget.',NULL,'2024-10-01 08:00:00','2024-12-13 17:30:00','2024-12-13 19:30:00','2024-12-10 23:59:00'),(4,'Påskmarknad i Centrum','Traditionell påskmarknad med hantverkare, blomsterförsäljare och påskdekorationer. Mat och kaffe finns på plats. Marknaden är öppen från 10:00-16:00. Perfekt tillfälle att handla påskgodis och blomster!',NULL,'2024-02-15 11:00:00','2024-03-30 10:00:00','2024-03-30 16:00:00','2024-03-28 23:59:00'),(5,'Friidrott-träningen öppet för alla','Kom och träna friidrott tillsammans! Vi tränar på idrottsplatsen varje vecka. Passar för alla åldrar och nivåer. Vi fokuserar på roligt träningspass och gemenskapen. Träningen startar 18:30 på fredagar.',NULL,'2024-04-05 14:00:00','2024-06-07 18:30:00','2024-06-07 20:00:00',NULL),(6,'Sommarcafé vid sjön','En mysig sommarkväll vid vattnet! Vi arrangerar ett öppet kafé med hemlagat fika, musik och god miljö. Perfekt för att umgås med grannar och nya vänner. Starts 17:00, avslutas när det blir mörkt. Helt gratis inträde!',NULL,'2024-05-20 10:00:00','2024-07-26 17:00:00','2024-07-26 22:00:00',NULL),(7,'Familjedagen på Gården','En rolig dag för hela familjen med många aktiviteter! Pony-ridning för barn, ansiktsmålning, spel och tävlingar. Mat och dryck finns tillgängligt. Vi startar 11:00 och avslutar 16:00. Anmälan rekommenderas för planering av mat.',NULL,'2024-03-10 09:00:00','2024-05-19 11:00:00','2024-05-19 16:00:00','2024-05-15 23:59:00'),(8,'Filmkväll: Klassiska svenska filmer','Vi visar klassiska svenska filmer på det stora vita duken. Denna gång visar vi en favorit från 1970-talet! Början 19:30, kaffe och bullar serveras. Sittplatser finns för ca 80 personer. Kom i tid för att få bra plats!',NULL,'2024-06-01 15:00:00','2024-08-16 19:30:00','2024-08-16 21:45:00','2024-08-14 23:59:00'),(9,'Cykeltur runt sjön','En vacker cykeltur för alla cykelnivåer! Vi åker runt sjön på cirka 20 km. Turen tar omkring 2-3 timmar. Vi stannar för en fika-paus på halva vägen. Börjar 09:00 från parkeringen vid cykelvägen.',NULL,'2024-05-10 08:00:00','2024-09-15 09:00:00','2024-09-15 12:30:00',NULL),(10,'Ungdomsdiskoteket - Fredagskväll','Ungdomsdiskoteket är tillbaka! DJ spelar dagens hetaste låtar och klassiker. Åldersgräns: 13-18 år. Ingång: 80 kr. Frukost och dryck finns att köpa. Dörren öppen 19:00-23:00.',NULL,'2024-04-20 16:00:00','2024-09-06 19:00:00','2024-09-06 23:00:00',NULL),(11,'Julmarknad med allsång','Vår stora julmarknad med många försäljare, hantverkare och julkort! Vi har också levande musik och allsång av julklassiker. Glögg och pepparkakor är gratis för alla besökare. Marknaden är öppen 10:00-17:00.',NULL,'2024-09-01 10:00:00','2024-11-30 10:00:00','2024-11-30 17:00:00','2024-11-28 23:59:00'),(12,'Promenerad för pensionärer','En lugn och mysig promenad för vuxna och pensionärer. Vi går omkring 5 km genom skogar och naturen. Turen tar cirka 1,5 timmar. Vi slutar med kaffe och en pratstund på ett fint café. Börjar 10:00 från biblioteket.',NULL,'2024-05-01 08:30:00','2024-10-10 10:00:00','2024-10-10 11:30:00',NULL),(13,'Klassisk körmusik i Kyrkan','En vacker konsert med klassisk körmusik i vår vackra kyrka. Vi uppför både klassiska och moderna körwerk. Entré: 100 kr. Konserten börjar 19:30. Kaffe och bullar serveras efter konserten.',NULL,'2024-07-01 14:00:00','2024-09-28 19:30:00','2024-09-28 21:30:00','2024-09-25 23:59:00'),(14,'Trädgårdsmässa och växtshopping','Välkommen till vår årliga trädgårdsmässa! Vi har ett stort utbud av växter, träd och trädgårdsartiklar. Experter på plats som kan ge dig tips för din trädgård. Även försäljning av hemgjorda marmelader och fruktkonserver. Öppet 09:00-16:00.',NULL,'2024-03-15 10:00:00','2024-05-12 09:00:00','2024-05-12 16:00:00','2024-05-10 23:59:00'),(15,'Grillkväll med grannar','En avslappnad grillkväll där grannar träffas och umgås! Vi grillär korv och köttbullar, och alla är välkomna att ta med något. Vi sitter ute och njuter av sommaren tillsammans. Börjar 18:00. Helt gratis!',NULL,'2024-06-10 15:00:00','2024-08-09 18:00:00','2024-08-09 22:00:00',NULL),(16,'Barn-teater: Sagor från landet långt bort','En spännande barnteater med sagor från exotiska länder! Länge sedan barnen fick se live-teater? Detta är en rolig föreställning för barn 4-10 år. Längd: cirka 45 minuter. Börjar 14:00 på kulturhuset. Anmälan öppnad!',NULL,'2024-04-01 11:00:00','2024-06-15 14:00:00','2024-06-15 14:45:00','2024-06-12 23:59:00');
/*!40000 ALTER TABLE `events` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `general_posts`
--

DROP TABLE IF EXISTS `general_posts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `general_posts` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `title` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` text COLLATE utf8mb4_unicode_ci,
  `image` longblob,
  `publish_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `like_count` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_general_posts_user_id` (`user_id`),
  KEY `idx_general_posts_publish_date` (`publish_date`),
  KEY `idx_general_posts_like_count` (`like_count`),
  CONSTRAINT `fk_general_posts_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `general_posts`
--

LOCK TABLES `general_posts` WRITE;
/*!40000 ALTER TABLE `general_posts` DISABLE KEYS */;
INSERT INTO `general_posts` VALUES (1,3,'Söker rekommendation för gräsklippning','Hej! Min gräsmatta behöver klippas och jag har aldrig gjort det själv. Kan någon rekommendera en bra och pålitlig person som kan göra det? Bor på Storgatan. Tack på förhand!',NULL,'2024-06-01 10:30:00',12),(2,5,'Hundpassning - behöver hjälp denna vecka','Hej alla! Jag behöver någon som kan passa min hund under dagen nästa vecka. Hon är väldigt snäll och älskar att gå på promenader. Betalar gärna. Finns det någon intresserad?',NULL,'2024-06-02 14:15:00',8),(3,7,'Tips för att skydda fruktträden','Jag har problem med fåglar som äter mina körsbär. Någon som har bra tips på hur man skyddar träden? Mörkväv eller något annat som fungerar?',NULL,'2024-06-03 09:45:00',15),(4,10,'Cykeln behöver reparation - vem kan jag kontakta?','Min cykelkedja är sliten och jag behöver nya däck. Vet någon en bra cykelreparatör i området? Helst någon som inte är för dyr och gör bra arbete.',NULL,'2024-06-04 16:20:00',6),(5,1,'Vilken vacker promenadväg vid sjön!','Jag var och gick promenaden runt sjön igår och vad det var vackert! Speciellt vid den lilla stranden var det helt underbar. Kan rekommendera alla att ta en tur där. Tog några fina foton som jag måste dela senare!',NULL,'2024-06-05 11:00:00',24),(6,2,'Stort tack till Anna för gräsklippningen!','Bara ville säga ett stort TACK till Anna Nilsson för att hon klippte min gräsmatta igår. Professionell, snabb och pålitlig! Rekommenderar starkt. Denne person är värd sitt pris!',NULL,'2024-06-06 10:15:00',18),(7,4,'Nya grejor i lekeplatsen - mycket uppskattat!','Vilken glad överraskning att se att nya grejor sattes upp i lekeplatsen! Barnen spelar där från morgon till kväll nu. Stort tack till de som gjort detta möjligt. Gemenskapen växer!',NULL,'2024-06-07 15:30:00',22),(8,6,'Trafikfara på Skolvägen - gör något!','Jag är väldigt oroad över att bilar kör mycket fort på Skolvägen. Mina barn går till skolan där varje dag och jag är rädd något farligt ska hända. Kan vi inte få någon hastighetsreglering eller varningsskyltar? Denna vägen behöver åtgärdas!',NULL,'2024-06-08 08:50:00',31),(9,8,'Störande ljud på nätterna - någon som märker detta?','Jag bor på Björkvägen och har märkt väldigt störande motorljud på nätterna omkring 23:00-01:00. Det verkar komma från någon som gör något med en bil eller motorcykel. Någon annan som märker detta? Det bör anmälas till polisen.',NULL,'2024-06-09 21:45:00',14),(10,11,'Villd dumpning av trädgårdsavfall vid vägen','Jag hittade en stor hög med trädgårdsavfall dumpat vid vägen nära Skogsvägen. Det är väldigt oprofessionellt och miljöskadligt. Vem gör detta och kan vi inte lösa det? Vi bör ha respekt för vår vackra miljö.',NULL,'2024-06-10 13:20:00',9),(11,9,'Biblioteket öppnar på nytt - här är de nya öppettiderna','Gott nytt! Biblioteket har renoverat och öppnar på nytt nästa vecka. De nya öppettiderna är: Måndag-Fredag: 10:00-18:00, Lördag: 10:00-14:00, Söndag: Stängt. Kom och besök det nya biblioteket!',NULL,'2024-06-11 09:30:00',11),(12,13,'Snöskottning denna vintern - någon intresserad?','Med tanke på att vintern kommer, undrar jag om det finns någon som är intresserad av att snöskotta vägen framför mitt hus denna vinter? Betalar gärna per tillfälle. Låt mig veta!',NULL,'2024-06-12 14:10:00',5),(13,16,'Tappade nycklar vid torget - behövs din hjälp!','Jag tappade mina bilnycklar någonstans vid torget igår omkring 15:00. Jag är desperat att hitta dem då jag inte har reserv. Om någon hittar dem, vädjar jag att ni kontaktar mig. Stor belöning!',NULL,'2024-06-13 10:05:00',7),(14,12,'Kom ihåg Midsommarfirandet - det blir underbart!','Om du inte redan vet det blir det midsommarfirande i Folkparken den 21 juni! Det är alltid en stor höjdpunkt på året med musik, dans och god mat. Jag rekommenderar att man registrerar sig i förväg. Ses där!',NULL,'2024-06-14 11:25:00',19),(15,14,'Familjedagen på Gården kommer - boka redan nu!','Familjedagen på Gården den 19 maj blir fantastisk! Ponyridning för barnen, ansiktsmålning och mycket mer. Det är fullt av saker för familjen att göra tillsammans. Börja planera nu!',NULL,'2024-06-15 15:40:00',10),(16,17,'Ny familj flyttade in på Storgatan - välkommen!','Bara ville säga välkommen till vår nya grannar på Storgatan! Vi såg att de flyttade in förra veckan. De verkar vara väldigt trevliga människor med två små barn. Känns bra att ha liv i gatan igen!',NULL,'2024-06-16 12:50:00',13),(17,15,'Ugglornas ljud på nätterna - inte alla gillar det','Jag har märkt att det finns mycket uggloröst på nätterna i trädgården. Det är faktiskt ganska vackert, men jag förstår att det kan vara störande för vissa. Någon som vet vad det är för arter av ugglor?',NULL,'2024-06-17 22:15:00',8),(18,18,'Cykelvägen är full av grus - farligt!','Cykelvägen mellan Skolan och Centrum är full av löst grus och stenar. Det är väldigt farligt att cykla där nu, speciellt för barn. Kan inte vägen städas eller dammtväckas? Detta behöver fixas snarast!',NULL,'2024-06-18 09:35:00',16),(19,2,'Yoga-träning startar nästa vecka - intresserad?','Jag startar en yogagrupp för nybörjare nästa vecka på onsdagskvällar. Det blir fokus på avslappning och stretching. Vi tränar i hemmet, och det kostar bara 50 kr per träning. Någon intresserad? Max 10 personer.',NULL,'2024-06-19 14:20:00',11),(20,3,'Tips för att hålla sig aktiv under sommaren','Sommaren är här och det är dags att vara aktiv! Jag gillar att gå promenader, cykla och diska i trädgården. Vad gör ni för att hålla er aktiva? Dela era tips och idéer här i tråden!',NULL,'2024-06-20 10:30:00',9),(21,1,'Barnvagn till försäljning - mycket bra skick','Jag säljer en nästan ny barnvagn från märket Stokke. Vi växte ur den väldigt snabbt. Den är i mycket bra skick och kommer med många tillbehör. Priset är 1200 kr. Intresserad? Kontakta mig!',NULL,'2024-06-21 11:15:00',4),(22,5,'Möbler från flytt - allt måste bort!','Jag har för många möbler från när jag flyttade. Jag erbjuder en högryggad fåtölj, två stolar och en soffbord. Allt är i bra skick men behöver plats. Billiga priser! Kom förbi denna vecka.',NULL,'2024-06-22 15:45:00',6),(23,4,'Förlorad grå tygväska - värdefullt innehål','Jag tappade en grå tygväska någonstans mellan Torget och Biblioteket för två dagar sedan. Den innehöll personnyckel och mycket privata saker. Om någon hittar den, vädjar jag att ni återlämnar den. Kan hämta där som helst.',NULL,'2024-06-23 09:20:00',3),(24,10,'Hittad katt på Björkvägen - ägare sökes!','Jag hittade en vacker orange katt på Björkvägen igår. Den verkar vara domestic och mycket tam. Jag antar att ägaren letar efter den. Om det är din katt, kontakta mig direkt!',NULL,'2024-06-24 14:55:00',7),(25,11,'Behöver vi inte göra något för vår miljö?','Jag är väldigt oroad över hur vi behandlar vår miljö lokalt. Allt gräsöverskärning, sophögar vid vägen, avfall på gatorna. Kan vi inte starta något miljö-initiativ i samhället? Jag är intresserad av att vara med!',NULL,'2024-06-25 16:30:00',20),(26,12,'Kompostering av trädgårdsavfall - tips?','Jag vill börja kompostera mitt trädgårdsavfall istället för att slita bort det. Någon som redan komposterar och kan ge tips? Hur bygger man en bra komposter? Vilka tips finns?',NULL,'2024-06-26 10:40:00',12),(27,6,'Vad gör vi när det regnar hela sommaren?','Är det bara jag som märker att det regnar väldigt mycket denna sommar? Varje gång jag planerar något utomhus börjar det regna! Någon annan som är frustrerad? Eller är detta bara vanlig sommarväder för Sverige?',NULL,'2024-06-27 13:15:00',23),(28,8,'Gräsmattan växer snabbare än jag kan klippa!','Är det bara mig som tycker att gräsmattan växer överallt denna säsong? Jag klipper varje vecka och nästa vecka ser det ut som jungeln igen. Någon annan som kämpar med detta?',NULL,'2024-06-28 12:25:00',17),(29,14,'Vilken var den värsta grillfesten du varit på?','Nu när sommaren är här och alla grillar, undrar jag - vilken var den värsta grillfesten du varit på? Dela dina grillfest-horror-berättelser här! Jag börjar: Grillmästaren tände på elden med BENSIN...',NULL,'2024-06-29 18:50:00',14);
/*!40000 ALTER TABLE `general_posts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_infos`
--

DROP TABLE IF EXISTS `group_infos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `group_infos` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `group_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `text1` text COLLATE utf8mb4_unicode_ci,
  `text2` text COLLATE utf8mb4_unicode_ci,
  `text3` text COLLATE utf8mb4_unicode_ci,
  `image1` longblob,
  `image2` longblob,
  `image3` longblob,
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_group_infos_group_name` (`group_name`),
  KEY `idx_group_infos_created_date` (`created_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_infos`
--

LOCK TABLES `group_infos` WRITE;
/*!40000 ALTER TABLE `group_infos` DISABLE KEYS */;
/*!40000 ALTER TABLE `group_infos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `group_member_counts`
--

DROP TABLE IF EXISTS `group_member_counts`;
/*!50001 DROP VIEW IF EXISTS `group_member_counts`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `group_member_counts` AS SELECT 
 1 AS `id`,
 1 AS `group_name`,
 1 AS `member_count`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `listings`
--

DROP TABLE IF EXISTS `listings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `listings` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `title` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` text COLLATE utf8mb4_unicode_ci,
  `image` longblob,
  `publish_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `price` int NOT NULL,
  `location` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_listings_user_id` (`user_id`),
  KEY `idx_listings_publish_date` (`publish_date`),
  KEY `idx_listings_price` (`price`),
  KEY `idx_listings_location` (`location`),
  CONSTRAINT `fk_listings_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `listings`
--

LOCK TABLES `listings` WRITE;
/*!40000 ALTER TABLE `listings` DISABLE KEYS */;
INSERT INTO `listings` VALUES (1,1,'Grå soffa i bra skick - snabb försäljning','Säljer en grå 3-sits soffa från Ikea. Den är omkring 5 år gammal men i mycket bra skick. Inga fläckar eller skador. Måste bort på grund av flytt. Kan leverera till närliggande områden.',NULL,'2024-06-01 10:30:00',1500,'Centrum'),(2,2,'Högrygga fåtölj - klassisk design','En vacker högrygga fåtölj i mörkblå tyg. Mycket bekväm och i utmärkt skick. Perfekt för att läsa en bok eller bara koppla av. Säljs på grund av möbelförflyttning.',NULL,'2024-06-02 14:15:00',800,'Storgatan'),(3,3,'Matbord med 4 stolar - trä','Trämöbler! Ett vackert matbord i ljust trä med 4 stolar. Bordet är omkring 120x80 cm. Möblerna är några år gamla men fortfarande i mycket bra skick. Realistiska erbjudanden välkomnas.',NULL,'2024-06-03 09:45:00',2200,'Centrum'),(4,5,'Bokhylla - Svart trä','En stor bokhylla i svart trä med flera hyllnivåer. Perfekt för att lagra böcker och dekoration. Mått omkring 200x100x40 cm. Finns lite repor men inget som förstör det övergripande intrycket.',NULL,'2024-06-04 16:20:00',350,'Björkvägen'),(5,4,'Dammsugarrobot - nästan ny','Säljer en dammsugarrobot av märket Roborock. Den är bara 2 år gammal och nästan inte använd. Den här roboten sparar mycket tid och gör jobbet perfekt. Allt tillbehör finns.',NULL,'2024-06-05 11:00:00',1800,'Östervägen'),(6,6,'Bärbar högtalare - Bluetooth','En kraftfull Bluetooth-högtalare som är perfekt för ute och inne. Vattenresistent och med bra batteritid. Färg: Svart. Den är nästan aldrig använd.',NULL,'2024-06-06 10:15:00',350,'Skogsvägen'),(7,7,'Laptop-väska och tillbehör','En svart laptop-väska som passar de flesta 15-tums bärbara datorer. Den har flera fickor för organisering. Följer med USB-kabel och skyddsfodral för datorn.',NULL,'2024-06-07 15:30:00',150,'Storgatan'),(8,9,'Smartklocka - Fitbit','En Fitbit smartklocka i rosa. Den mäter puls, steg och sömnkvalitet. Synkroniseras med telefonen via app. Bara några månader gammal. Perfekt för att träcka din aktivitet.',NULL,'2024-06-08 08:50:00',600,'Centrum'),(9,8,'Barnvagn med tillbehör','En Stokke-barnvagn i brun färg med flera tillbehör inklusive regnkåpa och väska. Vi växte ur den väldigt snabbt så den är nästan oanvänd. Mycket bra investering för en ny familj.',NULL,'2024-06-09 21:45:00',2500,'Skolvägen'),(10,10,'Barnleksaker - stor samling','Stor samling av barnleksaker från olika åldrar (3-10 år). Allt från pussel, konstruktionssatser, bilar och mer. Många från Lego och andra kända märken. Säljs som paket.',NULL,'2024-06-10 13:20:00',400,'Kyrkovägen'),(11,3,'Cykelkurv för barn','En röd cykelkurv som passar små barn (3-6 år). Den är säker och enkelt att montera på cykeln. I mycket bra skick. Perfekt för småbarnsföräldrar.',NULL,'2024-06-11 09:30:00',200,'Björkvägen'),(12,2,'Mountainbike - Scott märke','En Scott-mountainbike i svart och orange. Det är en begagnad men väl skött cykel. 21 växlar och god vägkvalitet. Passar både nybörjare och erfarna cyklister. Nytt skick!',NULL,'2024-06-12 14:10:00',1200,'Skogsvägen'),(13,4,'Cykelhjälm - Abus märke','En grå cykelhjälm från Abus med SPD-låsning för bergcykel. Den passar ungefär 52-58 cm huvudomfång. Använd men i helt bra skick. Nästan aldrig använd.',NULL,'2024-06-13 10:05:00',250,'Västervägen'),(14,11,'Inliners - rollerskor för barn','Ett par inliners för barn i storlek 34-37. De är i bra skick och lätt att justera för att växa med barnet. Perfekt för sommaren! Även skyddsutrustning medföljer.',NULL,'2024-06-14 11:25:00',180,'Storgatan'),(15,12,'Trädgårdsmöbler - bord och stolar','En set av trädgårdsmöbler med ett bord och 4 stolar i aluminiumram. De är väldigt lätta och lätta att flytta. Mycket bra för sommarfester och utomhusmat.',NULL,'2024-06-15 15:40:00',900,'Centrum'),(16,13,'Trädgårdsredskap - komplett set','Hela samlingen av trädgårdsredskap inklusive spade, högaffär, kratta, sekatör och mycket mer. Allt är i bra skick och väl skött. Perfekt för någon som just börjar trädgårdsarbeta.',NULL,'2024-06-16 12:50:00',350,'Kyrkovägen'),(17,14,'Trampolin - 3 meter','En stor trampolin som är 3 meter i diameter. Den är väl använd men fortfarande helt säker och funktionell. Vi växte ur den och behöver plats. Måste hämtas.',NULL,'2024-06-17 22:15:00',600,'Västervägen'),(18,15,'Grill - gasgrill','En svart gasgrill med 3 eldstäder. Mycket bra för att grilla under sommaren. Den är väl skött men vi behöver plats. Grillerna är i mycket bra skick.',NULL,'2024-06-18 09:35:00',1100,'Östervägen'),(19,16,'Boksamling - klassiker och modern litteratur','En stor samling av böcker på svenska och engelska. Allt från klassiker till moderna romaner. Många är signerade utgåvor. Perfekt för bokälskare. Säljs som paket eller enskilt.',NULL,'2024-06-19 14:20:00',500,'Centrum'),(20,17,'DVD-samling - actionfilmer','En samling av omkring 30 DVD:er med actionfilmer och thrillers. Allt från klassiker till nyare filmer. Mycket bra skick. Säljs billigt för att göra plats.',NULL,'2024-06-20 10:30:00',200,'Björkvägen'),(21,18,'Mikrovågsugn - IKEA','En vit mikrovågsugn från IKEA i mycket bra skick. Den fungerar perfekt och är energieffektiv. Bara några år gammal. Måste bort på grund av köksuppdatering.',NULL,'2024-06-21 11:15:00',250,'Skogsvägen'),(22,1,'Kaffebryggare - kaffemaskin','En mörkgrå kaffemaskin från Philips med timerfunktion. Den brygger framtida kaffe och är mycket lätt att använda. I mycket bra skick.',NULL,'2024-06-22 15:45:00',280,'Storgatan'),(23,6,'Matblandare - KitchenAid','En röd KitchenAid matblandare med flera tillbehör inklusive krok, vispa och slev. Den är väl använd men fungerar perfekt. Fantastisk för bak och matlagning.',NULL,'2024-06-23 09:20:00',800,'Kyrkovägen'),(24,10,'Verktygsväska - komplett set','En stor verktygsväska fylld med alla grundläggande verktyg: hammare, skruvdragare, skiftnycklar, såg och mycket mer. Allt är i bra skick och väl organiserat.',NULL,'2024-06-24 14:55:00',400,'Skolvägen'),(25,12,'El-borr - Makita','En kraftfull borr från Makita med många tillbehör. Den är väl använd men fungerar perfekt. Perfekt för båda hemrenovering och mindre byggprojekt.',NULL,'2024-06-25 16:30:00',600,'Centrum'),(26,14,'Gitarr - Yamaha akustisk','En Yamaha akustisk gitarr i mycket bra skick. Den är lätt att spela och har en vacker ljud. Perfekt för nybörjare eller erfarna. Ingen väska medföljer dock.',NULL,'2024-06-26 10:40:00',950,'Västervägen'),(27,5,'Geckåarium - kompletta setup','Ett helt akvarium-setup för geckoödlor med värmelampa, växter och dekoreringar. Allt fungerar perfekt. Vi behövde göra plats för något annat.',NULL,'2024-06-27 13:15:00',500,'Östervägen'),(28,7,'Persiennor - flera uppsättningar','Flera uppsättningar av persiennor i olika storlekar och färger (vit och grå). De är lätta att montera och i mycket bra skick. Billigt för alla tillsammans!',NULL,'2024-06-28 12:25:00',150,'Björkvägen'),(29,9,'Spegel - stor väggspegel','En stor väggspegel med vacker ram i guld. Mått omkring 150x100 cm. Perfekt för vardagsrum eller sovrummet. Väldigt vacker och i perfekt skick.',NULL,'2024-06-29 18:50:00',400,'Skogsvägen');
/*!40000 ALTER TABLE `listings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `memberlist_groups`
--

DROP TABLE IF EXISTS `memberlist_groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `memberlist_groups` (
  `user_id` bigint NOT NULL,
  `group_info_id` bigint NOT NULL,
  `joined_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`,`group_info_id`),
  KEY `idx_memberlist_groups_group_info_id` (`group_info_id`),
  CONSTRAINT `fk_memberlist_groups_group_info` FOREIGN KEY (`group_info_id`) REFERENCES `group_infos` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_memberlist_groups_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `memberlist_groups`
--

LOCK TABLES `memberlist_groups` WRITE;
/*!40000 ALTER TABLE `memberlist_groups` DISABLE KEYS */;
/*!40000 ALTER TABLE `memberlist_groups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `service_users`
--

DROP TABLE IF EXISTS `service_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service_users` (
  `service_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `registration_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`service_id`,`user_id`),
  KEY `idx_service_users_user_id` (`user_id`),
  CONSTRAINT `fk_service_users_service` FOREIGN KEY (`service_id`) REFERENCES `services` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_service_users_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service_users`
--

LOCK TABLES `service_users` WRITE;
/*!40000 ALTER TABLE `service_users` DISABLE KEYS */;
INSERT INTO `service_users` VALUES (1,1,'2024-03-20 10:00:00'),(1,9,'2024-03-30 10:05:00'),(1,16,'2024-02-28 10:00:00'),(2,2,'2024-02-10 14:00:00'),(3,4,'2024-02-20 09:00:00'),(3,12,'2024-03-01 08:30:00'),(4,7,'2024-03-15 15:00:00'),(4,15,'2024-05-01 14:00:00'),(5,6,'2024-05-10 10:30:00'),(5,14,'2024-03-20 09:00:00'),(6,6,'2024-02-25 08:00:00'),(6,14,'2024-03-20 09:10:00'),(7,3,'2024-03-25 11:30:00'),(7,13,'2024-04-15 11:00:00'),(8,3,'2024-03-25 11:35:00'),(8,9,'2024-03-30 10:00:00'),(9,5,'2024-04-01 13:00:00'),(9,11,'2024-04-05 14:30:00'),(9,17,'2024-05-10 13:30:00'),(10,4,'2024-02-20 09:10:00'),(10,12,'2024-03-01 08:40:00'),(11,10,'2024-04-10 12:00:00'),(11,18,'2024-03-25 08:00:00'),(12,1,'2024-03-20 10:05:00'),(12,16,'2024-02-28 10:05:00'),(13,7,'2024-03-15 15:10:00'),(14,8,'2024-04-20 09:30:00'),(15,2,'2024-02-10 14:10:00');
/*!40000 ALTER TABLE `service_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `services`
--

DROP TABLE IF EXISTS `services`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `services` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` text COLLATE utf8mb4_unicode_ci,
  `image` longblob,
  `publish_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_services_publish_date` (`publish_date`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `services`
--

LOCK TABLES `services` WRITE;
/*!40000 ALTER TABLE `services` DISABLE KEYS */;
INSERT INTO `services` VALUES (1,'Gräsklippning','Professionell gräsklippning av privatträdgårdar och större ytor',NULL,'2024-01-10 10:00:00'),(2,'Hundpassning','Daglig hundpassning och promenader för arbetande hundsägare',NULL,'2024-01-15 11:30:00'),(3,'Cykelreparation','Reparation och service av cyklar - allt från punktering till större reparationer',NULL,'2024-02-01 09:00:00'),(4,'Babysitting','Barnomsorg och babysitting för små barn i hemmet',NULL,'2024-02-05 14:20:00'),(5,'Möbelflyttning','Hjälp med möbeltransport och flytt av möbler',NULL,'2024-02-10 08:30:00'),(6,'Snöskottning','Snöskottning och snöröjning på privata vägar och parkeringsplatser',NULL,'2024-02-15 16:00:00'),(7,'Matlagning/Catering','Hemlagad matlagning och catering för mindre sammankomster',NULL,'2024-03-01 12:00:00'),(8,'Hemstädning','Professionell städning av hem och lägenheter',NULL,'2024-03-05 10:15:00'),(9,'Privatlektion','Privatlektioner i matematik, engelska, svenska och andra ämnen',NULL,'2024-03-10 13:45:00'),(10,'Handyman/Liten reparation','Små reparationer, montering och underhåll i hemmet',NULL,'2024-03-15 09:30:00'),(11,'Biltvätt','Handtvätt och detaljerande av bilar',NULL,'2024-04-01 11:00:00'),(12,'Trädgårdsarbete','Trädgårdsplanering, plantering och trädgårdsarbete',NULL,'2024-04-05 08:45:00'),(13,'Hundfrisering','Professionell hundfrisering och grooming',NULL,'2024-04-10 14:30:00'),(14,'Målning/Tapetsering','Målning och tapetsering av väggarna i hem',NULL,'2024-04-15 10:00:00'),(15,'Hundträning','Grundläggande hundträning och beteendekonsultation',NULL,'2024-05-01 09:00:00');
/*!40000 ALTER TABLE `services` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_details`
--

DROP TABLE IF EXISTS `user_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_details` (
  `user_id` bigint NOT NULL,
  `type` enum('standard','admin') COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'standard',
  `is_suspended` tinyint(1) NOT NULL DEFAULT '0',
  `registration_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`),
  KEY `idx_user_details_type` (`type`),
  CONSTRAINT `fk_user_details_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_details`
--

LOCK TABLES `user_details` WRITE;
/*!40000 ALTER TABLE `user_details` DISABLE KEYS */;
INSERT INTO `user_details` VALUES (1,'standard',0,'2024-01-15 10:30:00'),(2,'standard',0,'2024-02-03 14:20:00'),(3,'standard',0,'2024-02-15 09:45:00'),(4,'standard',0,'2024-03-01 11:00:00'),(5,'standard',0,'2024-03-10 15:30:00'),(6,'standard',0,'2024-03-22 08:15:00'),(7,'standard',0,'2024-04-05 12:00:00'),(8,'standard',0,'2024-04-18 16:45:00'),(9,'standard',0,'2024-05-02 10:20:00'),(10,'standard',0,'2024-05-12 13:50:00'),(11,'standard',0,'2024-05-25 09:30:00'),(12,'standard',0,'2024-06-01 14:15:00'),(13,'standard',0,'2024-06-10 11:40:00'),(14,'standard',0,'2024-06-20 15:25:00'),(15,'standard',0,'2024-07-01 10:00:00'),(16,'standard',0,'2024-07-15 12:30:00'),(17,'standard',0,'2024-07-28 14:50:00'),(18,'standard',0,'2024-08-05 09:20:00'),(19,'admin',0,'2024-01-01 08:00:00');
/*!40000 ALTER TABLE `user_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `age` tinyint unsigned DEFAULT NULL,
  `first_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `last_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_users_username` (`username`),
  UNIQUE KEY `uk_users_email` (`email`),
  KEY `idx_users_first_name` (`first_name`),
  KEY `idx_users_last_name` (`last_name`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'anna_nilsson','password123','anna.nilsson@email.com',42,'Anna','Nilsson'),(2,'erik_lundström','password123','erik.lundstrom@email.com',35,'Erik','Lundström'),(3,'maria_andersson','password123','maria.andersson@email.com',28,'Maria','Andersson'),(4,'johan_berg','password123','johan.berg@email.com',51,'Johan','Berg'),(5,'lisa_svensson','password123','lisa.svensson@email.com',33,'Lisa','Svensson'),(6,'magnus_johansson','password123','magnus.johansson@email.com',45,'Magnus','Johansson'),(7,'sofia_pettersson','password123','sofia.pettersson@email.com',29,'Sofia','Pettersson'),(8,'per_ekström','password123','per.ekstrom@email.com',56,'Per','Ekström'),(9,'karin_lindqvist','password123','karin.lindqvist@email.com',48,'Karin','Lindqvist'),(10,'daniel_holm','password123','daniel.holm@email.com',31,'Daniel','Holm'),(11,'eva_larsson','password123','eva.larsson@email.com',37,'Eva','Larsson'),(12,'thomas_öberg','password123','thomas.oberg@email.com',44,'Thomas','Öberg'),(13,'ingrid_blomquist','password123','ingrid.blomquist@email.com',62,'Ingrid','Blomquist'),(14,'robert_ström','password123','robert.strom@email.com',40,'Robert','Ström'),(15,'helena_björk','password123','helena.bjork@email.com',34,'Helena','Björk'),(16,'nils_ericsson','password123','nils.ericsson@email.com',52,'Nils','Ericsson'),(17,'julia_lundén','password123','julia.lunden@email.com',26,'Julia','Lundén'),(18,'sven_sundin','password123','sven.sundin@email.com',58,'Sven','Sundin'),(19,'anna_admin','password123','anna.admin@email.com',41,'Anna','Admin');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Final view structure for view `group_member_counts`
--

/*!50001 DROP VIEW IF EXISTS `group_member_counts`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb3_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `group_member_counts` AS select `gi`.`id` AS `id`,`gi`.`group_name` AS `group_name`,count(`mg`.`user_id`) AS `member_count` from (`group_infos` `gi` left join `memberlist_groups` `mg` on((`gi`.`id` = `mg`.`group_info_id`))) group by `gi`.`id`,`gi`.`group_name` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-06-01 12:45:28
