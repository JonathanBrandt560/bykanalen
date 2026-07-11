-- ============================================================================
-- BYKANALEN DATABASE - COMPLETE SETUP
-- Schema + Testdata - Allt i en fil!
-- ============================================================================

-- Skapa databasen
CREATE DATABASE IF NOT EXISTS `bykanalen` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `bykanalen`;

-- ============================================================================
-- TABELLER
-- ============================================================================

-- ============================================================================
-- 1. USERS - Huvudsaklig användartabell (global)
-- ============================================================================
CREATE TABLE `users` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(50) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `age` TINYINT UNSIGNED,
  `first_name` VARCHAR(100) NOT NULL,
  `last_name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_users_username` (`username`),
  UNIQUE KEY `uk_users_email` (`email`),
  INDEX `idx_users_first_name` (`first_name`),
  INDEX `idx_users_last_name` (`last_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================================
-- 2. USER_DETAILS - OneToOne relation med users
-- ============================================================================
CREATE TABLE `user_details` (
  `user_id` BIGINT NOT NULL,
  `type` ENUM('standard', 'admin') NOT NULL DEFAULT 'standard',
  `is_suspended` BOOLEAN NOT NULL DEFAULT FALSE,
  `registration_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `fk_user_details_user` 
    FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) 
    ON DELETE CASCADE,
  INDEX `idx_user_details_type` (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================================
-- 3. GROUP_INFOS - Byar/Samhällen (och andra grupper)
-- ============================================================================
CREATE TABLE `group_infos` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `group_name` VARCHAR(100) NOT NULL,
  `description` TEXT,
  `text1` TEXT,
  `text2` TEXT,
  `text3` TEXT,
  `image1` LONGBLOB,
  `image2` LONGBLOB,
  `image3` LONGBLOB,
  `created_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_group_infos_group_name` (`group_name`),
  INDEX `idx_group_infos_created_date` (`created_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================================
-- 4. MEMBERLIST_GROUPS - ManyToMany mellan users och groups
-- Användare kan vara medlem i flera groups/byar
-- ============================================================================
CREATE TABLE `memberlist_groups` (
  `user_id` BIGINT NOT NULL,
  `group_info_id` BIGINT NOT NULL,
  `joined_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`, `group_info_id`),
  CONSTRAINT `fk_memberlist_groups_user` 
    FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) 
    ON DELETE CASCADE,
  CONSTRAINT `fk_memberlist_groups_group_info` 
    FOREIGN KEY (`group_info_id`) REFERENCES `group_infos` (`id`) 
    ON DELETE CASCADE,
  INDEX `idx_memberlist_groups_group_info_id` (`group_info_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================================
-- 5. GROUP_ADMINS - Administratörer och moderatörer för varje group
-- ============================================================================
CREATE TABLE `group_admins` (
  `user_id` BIGINT NOT NULL,
  `group_info_id` BIGINT NOT NULL,
  `role` ENUM('moderator', 'admin') NOT NULL DEFAULT 'moderator',
  `assigned_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`, `group_info_id`),
  CONSTRAINT `fk_group_admins_user` 
    FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) 
    ON DELETE CASCADE,
  CONSTRAINT `fk_group_admins_group` 
    FOREIGN KEY (`group_info_id`) REFERENCES `group_infos` (`id`) 
    ON DELETE CASCADE,
  INDEX `idx_group_admins_group_id` (`group_info_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================================
-- 6. LISTINGS - Annonser (GLOBAL - ALLA KAN SE OAVSETT GRUPP)
-- ============================================================================
CREATE TABLE `listings` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT NOT NULL,
  `title` VARCHAR(255) NOT NULL,
  `description` TEXT,
  `image` LONGBLOB,
  `publish_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `price` INT NOT NULL,
  `location` VARCHAR(100),
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_listings_user` 
    FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) 
    ON DELETE CASCADE,
  INDEX `idx_listings_user_id` (`user_id`),
  INDEX `idx_listings_publish_date` (`publish_date`),
  INDEX `idx_listings_price` (`price`),
  INDEX `idx_listings_location` (`location`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================================
-- 7. EVENTS - Evenemang (GROUP-SPECIFIC - BARA MEDLEMMAR I GRUPPEN KAN SE)
-- ============================================================================
CREATE TABLE `events` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `group_id` BIGINT NOT NULL,
  `title` VARCHAR(255) NOT NULL,
  `description` TEXT,
  `image` LONGBLOB,
  `publish_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `start_date` DATETIME NOT NULL,
  `end_date` DATETIME,
  `close_registration_date` DATETIME,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_events_group` 
    FOREIGN KEY (`group_id`) REFERENCES `group_infos` (`id`) 
    ON DELETE CASCADE,
  INDEX `idx_events_group_id` (`group_id`),
  INDEX `idx_events_start_date` (`start_date`),
  INDEX `idx_events_publish_date` (`publish_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================================
-- 8. EVENT_REGISTRATIONS - ManyToMany mellan users och events
-- ============================================================================
CREATE TABLE `event_registrations` (
  `user_id` BIGINT NOT NULL,
  `event_id` BIGINT NOT NULL,
  `registration_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`, `event_id`),
  CONSTRAINT `fk_event_registrations_user` 
    FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) 
    ON DELETE CASCADE,
  CONSTRAINT `fk_event_registrations_event` 
    FOREIGN KEY (`event_id`) REFERENCES `events` (`id`) 
    ON DELETE CASCADE,
  INDEX `idx_event_registrations_event_id` (`event_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================================
-- 9. GENERAL_POSTS - Forumsinlägg (GROUP-SPECIFIC - BARA MEDLEMMAR I GRUPPEN KAN SE)
-- ============================================================================
CREATE TABLE `general_posts` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `group_id` BIGINT NOT NULL,
  `user_id` BIGINT NOT NULL,
  `title` VARCHAR(255) NOT NULL,
  `description` TEXT,
  `image` LONGBLOB,
  `publish_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `like_count` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_general_posts_group` 
    FOREIGN KEY (`group_id`) REFERENCES `group_infos` (`id`) 
    ON DELETE CASCADE,
  CONSTRAINT `fk_general_posts_user` 
    FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) 
    ON DELETE CASCADE,
  INDEX `idx_general_posts_group_id` (`group_id`),
  INDEX `idx_general_posts_user_id` (`user_id`),
  INDEX `idx_general_posts_publish_date` (`publish_date`),
  INDEX `idx_general_posts_like_count` (`like_count`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================================
-- 10. SERVICES - Tjänster/serviceerbjudanden (GROUP-SPECIFIC - BARA MEDLEMMAR I GRUPPEN KAN SE)
-- ============================================================================
CREATE TABLE `services` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `group_id` BIGINT NOT NULL,
  `title` VARCHAR(255) NOT NULL,
  `description` TEXT,
  `image` LONGBLOB,
  `publish_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_services_group` 
    FOREIGN KEY (`group_id`) REFERENCES `group_infos` (`id`) 
    ON DELETE CASCADE,
  INDEX `idx_services_group_id` (`group_id`),
  INDEX `idx_services_publish_date` (`publish_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================================
-- 11. SERVICE_USERS - ManyToMany mellan services och users
-- ============================================================================
CREATE TABLE `service_users` (
  `service_id` BIGINT NOT NULL,
  `user_id` BIGINT NOT NULL,
  PRIMARY KEY (`service_id`, `user_id`),
  CONSTRAINT `fk_service_users_service` 
    FOREIGN KEY (`service_id`) REFERENCES `services` (`id`) 
    ON DELETE CASCADE,
  CONSTRAINT `fk_service_users_user` 
    FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) 
    ON DELETE CASCADE,
  INDEX `idx_service_users_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================================
-- VIEWS - Användbara för Spring Boot queries
-- ============================================================================

-- Visa gruppmedlemsantal
CREATE VIEW `group_member_counts` AS
SELECT 
  gi.id,
  gi.group_name,
  COUNT(mg.user_id) as member_count
FROM `group_infos` gi
LEFT JOIN `memberlist_groups` mg ON gi.id = mg.group_info_id
GROUP BY gi.id, gi.group_name;

-- Visa vilka som är admins/moderatörer i varje group
CREATE VIEW `group_admin_info` AS
SELECT 
  ga.group_info_id,
  gi.group_name,
  ga.user_id,
  u.username,
  u.first_name,
  u.last_name,
  ga.role
FROM `group_admins` ga
JOIN `group_infos` gi ON ga.group_info_id = gi.id
JOIN `users` u ON ga.user_id = u.id;

-- ============================================================================
-- TESTDATA - USERS
-- ============================================================================

INSERT INTO users (username, password, email, age, first_name, last_name) VALUES
('anna_nilsson', 'password123', 'anna.nilsson@email.com', 42, 'Anna', 'Nilsson'),
('erik_lundström', 'password123', 'erik.lundstrom@email.com', 35, 'Erik', 'Lundström'),
('maria_andersson', 'password123', 'maria.andersson@email.com', 28, 'Maria', 'Andersson'),
('johan_berg', 'password123', 'johan.berg@email.com', 51, 'Johan', 'Berg'),
('lisa_svensson', 'password123', 'lisa.svensson@email.com', 33, 'Lisa', 'Svensson'),
('magnus_johansson', 'password123', 'magnus.johansson@email.com', 45, 'Magnus', 'Johansson'),
('sofia_pettersson', 'password123', 'sofia.pettersson@email.com', 29, 'Sofia', 'Pettersson'),
('per_ekström', 'password123', 'per.ekstrom@email.com', 56, 'Per', 'Ekström'),
('karin_lindqvist', 'password123', 'karin.lindqvist@email.com', 48, 'Karin', 'Lindqvist'),
('daniel_holm', 'password123', 'daniel.holm@email.com', 31, 'Daniel', 'Holm'),
('eva_larsson', 'password123', 'eva.larsson@email.com', 37, 'Eva', 'Larsson'),
('thomas_öberg', 'password123', 'thomas.oberg@email.com', 44, 'Thomas', 'Öberg'),
('ingrid_blomquist', 'password123', 'ingrid.blomquist@email.com', 62, 'Ingrid', 'Blomquist'),
('robert_ström', 'password123', 'robert.strom@email.com', 40, 'Robert', 'Ström'),
('helena_björk', 'password123', 'helena.bjork@email.com', 34, 'Helena', 'Björk'),
('nils_ericsson', 'password123', 'nils.ericsson@email.com', 52, 'Nils', 'Ericsson'),
('julia_lundén', 'password123', 'julia.lunden@email.com', 26, 'Julia', 'Lundén'),
('sven_sundin', 'password123', 'sven.sundin@email.com', 58, 'Sven', 'Sundin'),
('anna_admin', 'password123', 'anna.admin@email.com', 41, 'Anna', 'Admin');

-- ============================================================================
-- TESTDATA - USER_DETAILS
-- ============================================================================

INSERT INTO user_details (user_id, type, is_suspended, registration_date) VALUES
(1, 'standard', FALSE, '2024-01-15 10:30:00'),
(2, 'standard', FALSE, '2024-02-03 14:20:00'),
(3, 'standard', FALSE, '2024-02-15 09:45:00'),
(4, 'standard', FALSE, '2024-03-01 11:00:00'),
(5, 'standard', FALSE, '2024-03-10 15:30:00'),
(6, 'standard', FALSE, '2024-03-22 08:15:00'),
(7, 'standard', FALSE, '2024-04-05 12:00:00'),
(8, 'standard', FALSE, '2024-04-18 16:45:00'),
(9, 'standard', FALSE, '2024-05-02 10:20:00'),
(10, 'standard', FALSE, '2024-05-12 13:50:00'),
(11, 'standard', FALSE, '2024-05-25 09:30:00'),
(12, 'standard', FALSE, '2024-06-01 14:15:00'),
(13, 'standard', FALSE, '2024-06-10 11:40:00'),
(14, 'standard', FALSE, '2024-06-20 15:25:00'),
(15, 'standard', FALSE, '2024-07-01 10:00:00'),
(16, 'standard', FALSE, '2024-07-15 12:30:00'),
(17, 'standard', FALSE, '2024-07-28 14:50:00'),
(18, 'standard', FALSE, '2024-08-05 09:20:00'),
(19, 'admin', FALSE, '2024-01-01 08:00:00');

-- ============================================================================
-- TESTDATA - GROUP_INFOS & MEMBERLIST_GROUPS
-- ============================================================================

INSERT INTO group_infos (group_name, description, text1, text2, text3, created_date) VALUES
('Tygelsjö',
 'En vacker by i Skåne med cirka 500 invånare. Här finns både trevliga grannar och många gemensamma aktiviteter under året.',
 'Välkommen till Tygelsjö! Vi är en liten men aktiv gemenskap där alla hjälps åt.',
 'Vi anordnar många evenemang under året - från midsommar till julmarknad.',
 'Missa inte våra regelbundna träffar och aktiviteter för både barn och vuxna!',
 '2024-01-01 08:00:00'),

('Östra Grevinge',
 'En charmig liten by med en stark känsla av gemenskap. Vi samlas ofta för att fira olika högtider och anordna gemensamma aktiviteter.',
 'Östra Grevinge är ett fantastiskt ställe att bo på med trevliga människor.',
 'Vi har många traditioner och aktiviteter som håller gemenskapen levande året runt.',
 'Kom och bli en del av vår växande gemenskap!',
 '2024-01-05 09:30:00');

-- Tygelsjö medlemmar
INSERT INTO memberlist_groups (user_id, group_info_id, joined_date) VALUES
(1, 1, '2024-01-15 10:30:00'),
(2, 1, '2024-02-03 14:20:00'),
(3, 1, '2024-02-15 09:45:00'),
(4, 1, '2024-03-01 11:00:00'),
(5, 1, '2024-03-10 15:30:00'),
(9, 1, '2024-05-02 10:20:00'),
(10, 1, '2024-05-12 13:50:00'),
(13, 1, '2024-06-10 11:40:00'),
(16, 1, '2024-07-15 12:30:00'),
(18, 1, '2024-08-05 09:20:00');

-- Östra Grevinge medlemmar
INSERT INTO memberlist_groups (user_id, group_info_id, joined_date) VALUES
(6, 2, '2024-03-22 08:15:00'),
(7, 2, '2024-04-05 12:00:00'),
(8, 2, '2024-04-18 16:45:00'),
(11, 2, '2024-05-25 09:30:00'),
(12, 2, '2024-06-01 14:15:00'),
(14, 2, '2024-06-20 15:25:00'),
(15, 2, '2024-07-01 10:00:00'),
(17, 2, '2024-07-28 14:50:00'),
(19, 2, '2024-01-01 08:00:00');

-- Admin också i Tygelsjö
INSERT INTO memberlist_groups (user_id, group_info_id, joined_date) VALUES
(19, 1, '2024-01-01 08:00:00');

-- ============================================================================
-- TESTDATA - SERVICES (GROUP-SPECIFIC)
-- ============================================================================

-- TYGELSJÖ SERVICES
INSERT INTO services (group_id, title, description, publish_date) VALUES
(1, 'Gräsklippning', 'Professionell gräsklippning av privatträdgårdar och större ytor', '2024-01-10 10:00:00'),
(1, 'Hundpassning', 'Daglig hundpassning och promenader för arbetande hundsägare', '2024-01-15 11:30:00'),
(1, 'Cykelreparation', 'Reparation och service av cyklar - allt från punktering till större reparationer', '2024-02-01 09:00:00'),
(1, 'Babysitting', 'Barnomsorg och babysitting för små barn i hemmet', '2024-02-05 14:20:00'),
(1, 'Möbelflyttning', 'Hjälp med möbeltransport och flytt av möbler', '2024-02-10 08:30:00'),
(1, 'Snöskottning', 'Snöskottning och snöröjning på privata vägar och parkeringsplatser', '2024-02-15 16:00:00'),
(1, 'Matlagning/Catering', 'Hemlagad matlagning och catering för mindre sammankomster', '2024-03-01 12:00:00'),
(1, 'Hemstädning', 'Professionell städning av hem och lägenheter', '2024-03-05 10:15:00');

-- ÖSTRA GREVINGE SERVICES
INSERT INTO services (group_id, title, description, publish_date) VALUES
(2, 'Privatlektion', 'Privatlektioner i matematik, engelska, svenska och andra ämnen', '2024-03-10 13:45:00'),
(2, 'Handyman/Liten reparation', 'Små reparationer, montering och underhåll i hemmet', '2024-03-15 09:30:00'),
(2, 'Biltvätt', 'Handtvätt och detaljerande av bilar', '2024-04-01 11:00:00'),
(2, 'Trädgårdsarbete', 'Trädgårdsplanering, plantering och trädgårdsarbete', '2024-04-05 08:45:00'),
(2, 'Hundfrisering', 'Professionell hundfrisering och grooming', '2024-04-10 14:30:00'),
(2, 'Målning/Tapetsering', 'Målning och tapetsering av väggarna i hem', '2024-04-15 10:00:00'),
(2, 'Hundträning', 'Grundläggande hundträning och beteendekonsultation', '2024-05-01 09:00:00');

-- ============================================================================
-- TESTDATA - SERVICE_USERS
-- ============================================================================

INSERT INTO service_users (service_id, user_id) VALUES
(1, 1), (8, 1),
(2, 2), (15, 2),
(7, 3), (8, 3),
(3, 4),
(7, 5),
(8, 9), (1, 9),
(11, 10),
(7, 13),
(1, 16),
(11, 18),
(12, 6), (10, 6),
(13, 7), (15, 7),
(14, 8),
(9, 11),
(3, 12), (10, 12),
(10, 14), (12, 14),
(13, 15),
(9, 17);

-- ============================================================================
-- TESTDATA - EVENTS (GROUP-SPECIFIC)
-- ============================================================================

-- TYGELSJÖ EVENTS
INSERT INTO events (group_id, title, description, publish_date, start_date, end_date, close_registration_date) VALUES
(1, 'Midsommarfirande i Folkparken',
 'Traditionell midsommarfirande med dansbana, mat och dryck. Vi startar klockan 14:00 med grejer för barn och uppvärmningen på dansbanan. Alla är välkomna!',
 '2024-04-15 10:00:00', '2024-06-21 14:00:00', '2024-06-21 23:00:00', '2024-06-19 23:59:00'),

(1, 'Dansbandskväll med Hasse Staffanz',
 'Välkommen till en fantastisk kväll med dansbandet Hasse Staffanz! Dansbanan öppen från 19:00, musik börjar 20:00. Mat och dryck finns tillgängligt.',
 '2024-05-01 09:00:00', '2024-07-13 19:00:00', '2024-07-13 23:30:00', '2024-07-10 23:59:00'),

(1, 'Lucia-firande på Torget',
 'År fest med luciatåg, sång och glögg. Vi firar Lucia tillsammans med klassiska lucialåtar och avslutar med fika och pepparkakor. Börjar 17:30 på torget.',
 '2024-10-01 08:00:00', '2024-12-13 17:30:00', '2024-12-13 19:30:00', '2024-12-10 23:59:00'),

(1, 'Påskmarknad i Centrum',
 'Traditionell påskmarknad med hantverkare, blomsterförsäljare och påskdekorationer. Mat och kaffe finns på plats. Marknaden är öppen från 10:00-16:00.',
 '2024-02-15 11:00:00', '2024-03-30 10:00:00', '2024-03-30 16:00:00', '2024-03-28 23:59:00'),

(1, 'Friidrott-träningen öppet för alla',
 'Kom och träna friidrott tillsammans! Vi tränar på idrottsplatsen varje vecka. Passar för alla åldrar och nivåer. Träningen startar 18:30 på fredagar.',
 '2024-04-05 14:00:00', '2024-06-07 18:30:00', '2024-06-07 20:00:00', NULL),

(1, 'Sommarcafé vid sjön',
 'En mysig sommarkväll vid vattnet! Vi arrangerar ett öppet kafé med hemlagat fika, musik och god miljö. Perfekt för att umgås med grannar och nya vänner. Starts 17:00.',
 '2024-05-20 10:00:00', '2024-07-26 17:00:00', '2024-07-26 22:00:00', NULL),

(1, 'Familjedagen på Gården',
 'En rolig dag för hela familjen med många aktiviteter! Pony-ridning för barn, ansiktsmålning, spel och tävlingar. Mat och dryck finns tillgängligt. Vi startar 11:00 och avslutar 16:00.',
 '2024-03-10 09:00:00', '2024-05-19 11:00:00', '2024-05-19 16:00:00', '2024-05-15 23:59:00'),

(1, 'Filmkväll: Klassiska svenska filmer',
 'Vi visar klassiska svenska filmer på det stora vita duken. Denna gång visar vi en favorit från 1970-talet! Början 19:30, kaffe och bullar serveras.',
 '2024-06-01 15:00:00', '2024-08-16 19:30:00', '2024-08-16 21:45:00', '2024-08-14 23:59:00');

-- ÖSTRA GREVINGE EVENTS
INSERT INTO events (group_id, title, description, publish_date, start_date, end_date, close_registration_date) VALUES
(2, 'Cykeltur runt sjön',
 'En vacker cykeltur för alla cykelnivåer! Vi åker runt sjön på cirka 20 km. Turen tar omkring 2-3 timmar. Vi stannar för en fika-paus på halva vägen. Börjar 09:00 från parkeringen vid cykelvägen.',
 '2024-05-10 08:00:00', '2024-09-15 09:00:00', '2024-09-15 12:30:00', NULL),

(2, 'Ungdomsdiskoteket - Fredagskväll',
 'Ungdomsdiskoteket är tillbaka! DJ spelar dagens hetaste låtar och klassiker. Åldersgräns: 13-18 år. Ingång: 80 kr. Frukost och dryck finns att köpa. Dörren öppen 19:00-23:00.',
 '2024-04-20 16:00:00', '2024-09-06 19:00:00', '2024-09-06 23:00:00', NULL),

(2, 'Julmarknad med allsång',
 'Vår stora julmarknad med många försäljare, hantverkare och julkort! Vi har också levande musik och allsång av julklassiker. Glögg och pepparkakor är gratis för alla besökare. Marknaden är öppen 10:00-17:00.',
 '2024-09-01 10:00:00', '2024-11-30 10:00:00', '2024-11-30 17:00:00', '2024-11-28 23:59:00'),

(2, 'Promenerad för pensionärer',
 'En lugn och mysig promenad för vuxna och pensionärer. Vi går omkring 5 km genom skogar och naturen. Turen tar cirka 1,5 timmar. Vi slutar med kaffe och en pratstund på ett fint café. Börjar 10:00 från biblioteket.',
 '2024-05-01 08:30:00', '2024-10-10 10:00:00', '2024-10-10 11:30:00', NULL),

(2, 'Klassisk körmusik i Kyrkan',
 'En vacker konsert med klassisk körmusik i vår vackra kyrka. Vi uppför både klassiska och moderna körwerk. Entré: 100 kr. Konserten börjar 19:30. Kaffe och bullar serveras efter konserten.',
 '2024-07-01 14:00:00', '2024-09-28 19:30:00', '2024-09-28 21:30:00', '2024-09-25 23:59:00'),

(2, 'Trädgårdsmässa och växtshopping',
 'Välkommen till vår årliga trädgårdsmässa! Vi har ett stort utbud av växter, träd och trädgårdsartiklar. Experter på plats som kan ge dig tips för din trädgård. Även försäljning av hemgjorda marmelader och fruktkonserver. Öppet 09:00-16:00.',
 '2024-03-15 10:00:00', '2024-05-12 09:00:00', '2024-05-12 16:00:00', '2024-05-10 23:59:00'),

(2, 'Grillkväll med grannar',
 'En avslappnad grillkväll där grannar träffas och umgås! Vi grillär korv och köttbullar, och alla är välkomna att ta med något. Vi sitter ute och njuter av sommaren tillsammans. Börjar 18:00. Helt gratis!',
 '2024-06-10 15:00:00', '2024-08-09 18:00:00', '2024-08-09 22:00:00', NULL),

(2, 'Barn-teater: Sagor från landet långt bort',
 'En spännande barnteater med sagor från exotiska länder! En rolig föreställning för barn 4-10 år. Längd: cirka 45 minuter. Börjar 14:00 på kulturhuset.',
 '2024-04-01 11:00:00', '2024-06-15 14:00:00', '2024-06-15 14:45:00', '2024-06-12 23:59:00');

-- ============================================================================
-- TESTDATA - EVENT_REGISTRATIONS
-- ============================================================================

-- Tygelsjö events
INSERT INTO event_registrations (user_id, event_id, registration_date) VALUES
(1, 1, '2024-04-20 10:15:00'), (2, 1, '2024-04-21 14:30:00'), (3, 1, '2024-04-22 09:45:00'),
(4, 1, '2024-04-23 16:20:00'), (5, 1, '2024-05-01 11:00:00'), (9, 1, '2024-06-01 09:00:00'),
(10, 1, '2024-06-05 12:30:00'), (13, 1, '2024-05-05 13:45:00'), (16, 1, '2024-06-10 10:30:00'),
(18, 1, '2024-06-15 15:15:00'),
(2, 2, '2024-05-05 14:00:00'), (4, 2, '2024-05-08 10:20:00'), (9, 2, '2024-06-01 09:15:00'),
(10, 2, '2024-06-05 13:50:00'), (13, 2, '2024-06-10 15:25:00'), (16, 2, '2024-06-18 14:10:00'),
(18, 2, '2024-06-20 11:55:00'),
(1, 3, '2024-10-15 09:30:00'), (3, 3, '2024-10-20 14:15:00'), (5, 3, '2024-11-01 10:45:00'),
(9, 3, '2024-11-10 11:00:00'), (13, 3, '2024-11-20 09:55:00'), (16, 3, '2024-12-01 10:20:00'),
(1, 4, '2024-02-20 10:00:00'), (3, 4, '2024-02-25 14:30:00'), (5, 4, '2024-03-01 09:15:00'),
(9, 4, '2024-03-10 11:20:00'),
(2, 5, '2024-04-10 15:30:00'), (5, 5, '2024-04-15 10:45:00'), (10, 5, '2024-04-20 14:20:00'),
(1, 6, '2024-05-25 10:30:00'), (3, 6, '2024-06-01 14:15:00'), (5, 6, '2024-06-05 09:40:00'),
(9, 6, '2024-06-15 11:50:00'), (13, 6, '2024-06-25 10:05:00'),
(1, 7, '2024-03-20 11:00:00'), (3, 7, '2024-03-25 14:30:00'), (5, 7, '2024-04-01 09:45:00'),
(9, 7, '2024-04-20 10:55:00'), (13, 7, '2024-05-05 15:40:00'),
(2, 8, '2024-06-10 09:30:00'), (4, 8, '2024-06-15 14:45:00'), (10, 8, '2024-07-01 11:35:00'),
(16, 8, '2024-07-15 15:40:00'), (18, 8, '2024-07-20 10:25:00');

-- Östra Grevinge events
INSERT INTO event_registrations (user_id, event_id, registration_date) VALUES
(6, 9, '2024-05-15 10:45:00'), (8, 9, '2024-05-20 14:30:00'), (11, 9, '2024-06-01 15:20:00'),
(12, 9, '2024-06-05 11:40:00'), (14, 9, '2024-06-10 13:25:00'), (17, 9, '2024-06-15 10:05:00'),
(7, 10, '2024-05-10 15:45:00'), (15, 10, '2024-05-20 10:20:00'), (17, 10, '2024-06-01 16:55:00'),
(6, 11, '2024-09-10 10:30:00'), (7, 11, '2024-09-15 14:45:00'), (8, 11, '2024-09-20 09:20:00'),
(11, 11, '2024-10-05 11:40:00'), (12, 11, '2024-10-10 13:55:00'), (14, 11, '2024-10-20 16:30:00'),
(15, 11, '2024-11-01 14:50:00'), (17, 11, '2024-11-05 09:35:00'),
(8, 12, '2024-05-15 14:20:00'), (12, 12, '2024-06-01 16:10:00'), (14, 12, '2024-06-05 11:30:00'),
(8, 13, '2024-07-15 14:40:00'), (12, 13, '2024-07-20 09:55:00'),
(6, 14, '2024-03-25 11:20:00'), (11, 14, '2024-04-10 10:35:00'), (14, 14, '2024-05-01 12:40:00'),
(6, 15, '2024-06-20 10:30:00'), (8, 15, '2024-06-25 15:45:00'), (11, 15, '2024-07-10 11:55:00'),
(12, 15, '2024-07-15 13:30:00'), (14, 15, '2024-07-20 10:05:00'),
(7, 16, '2024-04-25 16:40:00'), (15, 16, '2024-05-01 11:25:00'), (17, 16, '2024-05-10 09:50:00');

-- ============================================================================
-- TESTDATA - GENERAL_POSTS (GROUP-SPECIFIC)
-- ============================================================================

-- Tygelsjö posts
INSERT INTO general_posts (group_id, user_id, title, description, publish_date, like_count) VALUES
(1, 3, 'Söker rekommendation för gräsklippning',
 'Hej! Min gräsmatta behöver klippas och jag har aldrig gjort det själv. Kan någon rekommendera en bra och pålitlig person som kan göra det? Bor på Storgatan. Tack på förhand!',
 '2024-06-01 10:30:00', 12),

(1, 5, 'Hundpassning - behöver hjälp denna vecka',
 'Hej alla! Jag behöver någon som kan passa min hund under dagen nästa vecka. Hon är väldigt snäll och älskar att gå på promenader. Betalar gärna. Finns det någon intresserad?',
 '2024-06-02 14:15:00', 8),

(1, 9, 'Tips för att skydda fruktträden',
 'Jag har problem med fåglar som äter mina körsbär. Någon som har bra tips på hur man skyddar träden? Mörkväv eller något annat som fungerar?',
 '2024-06-03 09:45:00', 15),

(1, 10, 'Cykeln behöver reparation - vem kan jag kontakta?',
 'Min cykelkedja är sliten och jag behöver nya däck. Vet någon en bra cykelreparatör i området? Helst någon som inte är för dyr och gör bra arbete.',
 '2024-06-04 16:20:00', 6),

(1, 1, 'Vilken vacker promenadväg vid sjön!',
 'Jag var och gick promenaden runt sjön igår och vad det var vackert! Speciellt vid den lilla stranden var det helt underbar. Kan rekommendera alla att ta en tur där.',
 '2024-06-05 11:00:00', 24),

(1, 2, 'Stort tack till Anna för gräsklippningen!',
 'Bara ville säga ett stort TACK till Anna Nilsson för att hon klippte min gräsmatta igår. Professionell, snabb och pålitlig! Rekommenderar starkt.',
 '2024-06-06 10:15:00', 18),

(1, 4, 'Nya grejor i lekeplatsen - mycket uppskattat!',
 'Vilken glad överraskning att se att nya grejor sattes upp i lekeplatsen! Barnen spelar där från morgon till kväll nu. Stort tack till de som gjort detta möjligt.',
 '2024-06-07 15:30:00', 22),

(1, 9, 'Trafikfara på Skolvägen - gör något!',
 'Jag är väldigt oroad över att bilar kör mycket fort på Skolvägen. Mina barn går till skolan där varje dag och jag är rädd något farligt ska hända.',
 '2024-06-08 08:50:00', 31),

(1, 13, 'Villd dumpning av trädgårdsavfall vid vägen',
 'Jag hittade en stor hög med trädgårdsavfall dumpat vid vägen nära Skogsvägen. Det är väldigt oprofessionellt och miljöskadligt.',
 '2024-06-10 13:20:00', 9),

(1, 1, 'Biblioteket öppnar på nytt - här är de nya öppettiderna',
 'Gott nytt! Biblioteket har renoverat och öppnar på nytt nästa vecka. De nya öppettiderna är: Måndag-Fredag: 10:00-18:00, Lördag: 10:00-14:00, Söndag: Stängt.',
 '2024-06-11 09:30:00', 11),

(1, 13, 'Snöskottning denna vintern - någon intresserad?',
 'Med tanke på att vintern kommer, undrar jag om det finns någon som är intresserad av att snöskotta vägen framför mitt hus denna vinter? Betalar gärna per tillfälle.',
 '2024-06-12 14:10:00', 5),

(1, 16, 'Tappade nycklar vid torget - behövs din hjälp!',
 'Jag tappade mina bilnycklar någonstans vid torget igår omkring 15:00. Jag är desperat att hitta dem då jag inte har reserv. Om någon hittar dem, vädjar jag att ni kontaktar mig.',
 '2024-06-13 10:05:00', 7),

(1, 13, 'Behöver vi inte göra något för vår miljö?',
 'Jag är väldigt oroad över hur vi behandlar vår miljö lokalt. Allt gräsöverskärning, sophögor vid vägen, avfall på gatorna. Kan vi inte starta något miljö-initiativ?',
 '2024-06-25 16:30:00', 20),

(1, 16, 'Kompostering av trädgårdsavfall - tips?',
 'Jag vill börja kompostera mitt trädgårdsavfall istället för att slita bort det. Någon som redan komposterar och kan ge tips? Hur bygger man en bra komposter?',
 '2024-06-26 10:40:00', 12);

-- Östra Grevinge posts
INSERT INTO general_posts (group_id, user_id, title, description, publish_date, like_count) VALUES
(2, 7, 'Vad gör vi när det regnar hela sommaren?',
 'Är det bara jag som märker att det regnar väldigt mycket denna sommar? Varje gång jag planerar något utomhus börjar det regna! Någon annan som är frustrerad?',
 '2024-06-27 13:15:00', 23),

(2, 8, 'Gräsmattan växer snabbare än jag kan klippa!',
 'Är det bara mig som tycker att gräsmattan växer överallt denna säsong? Jag klipper varje vecka och nästa vecka ser det ut som jungeln igen. Någon annan som kämpar?',
 '2024-06-28 12:25:00', 17),

(2, 14, 'Vilken var den värsta grillfesten du varit på?',
 'Nu när sommaren är här och alla grillar, undrar jag - vilken var den värsta grillfesten du varit på? Dela dina grillfest-horror-berättelser här!',
 '2024-06-29 18:50:00', 14),

(2, 6, 'Yoga-träning startar nästa vecka - intresserad?',
 'Jag startar en yogagrupp för nybörjare nästa vecka på onsdagskvällar. Det blir fokus på avslappning och stretching. Vi tränar i hemmet, och det kostar bara 50 kr per träning.',
 '2024-06-19 14:20:00', 11),

(2, 11, 'Tips för att hålla sig aktiv under sommaren',
 'Sommaren är här och det är dags att vara aktiv! Jag gillar att gå promenader, cykla och diska i trädgården. Vad gör ni för att hålla er aktiva?',
 '2024-06-20 10:30:00', 9),

(2, 14, 'Barnvagn till försäljning - mycket bra skick',
 'Jag säljer en nästan ny barnvagn från märket Stokke. Vi växte ur den väldigt snabbt. Den är i mycket bra skick och kommer med många tillbehör. Priset är 1200 kr.',
 '2024-06-21 11:15:00', 4),

(2, 17, 'Möbler från flytt - allt måste bort!',
 'Jag har för många möbler från när jag flyttade. Jag erbjuder en högryggad fåtölj, två stolar och en soffbord. Allt är i bra skick men behöver plats. Billiga priser!',
 '2024-06-22 15:45:00', 6),

(2, 8, 'Förlorad grå tygväska - värdefullt innehål',
 'Jag tappade en grå tygväska någonstans mellan Torget och Biblioteket för två dagar sedan. Den innehöll personnyckel och mycket privata saker. Om någon hittar den, vädjar jag att ni återlämnar den.',
 '2024-06-23 09:20:00', 3),

(2, 12, 'Hittad katt på Björkvägen - ägare sökes!',
 'Jag hittade en vacker orange katt på Björkvägen igår. Den verkar vara domestic och mycket tam. Jag antar att ägaren letar efter den. Om det är din katt, kontakta mig direkt!',
 '2024-06-24 14:55:00', 7),

(2, 15, 'Ny familj flyttade in på Storgatan - välkommen!',
 'Bara ville säga välkommen till vår nya grannar på Storgatan! Vi såg att de flyttade in förra veckan. De verkar vara väldigt trevliga människor med två små barn.',
 '2024-06-16 12:50:00', 13),

(2, 17, 'Ugglornas ljud på nätterna - inte alla gillar det',
 'Jag har märkt att det finns mycket uggloröst på nätterna i trädgården. Det är faktiskt ganska vackert, men jag förstår att det kan vara störande för vissa. Någon som vet arter?',
 '2024-06-17 22:15:00', 8),

(2, 8, 'Cykelvägen är full av grus - farligt!',
 'Cykelvägen mellan Skolan och Centrum är full av löst grus och stenar. Det är väldigt farligt att cykla där nu, speciellt för barn. Kan inte vägen städas?',
 '2024-06-18 09:35:00', 16),

(2, 12, 'Kom ihåg Midsommarfirandet - det blir underbart!',
 'Om du inte redan vet det blir det midsommarfirande i Folkparken den 21 juni! Det är alltid en stor höjdpunkt på året med musik, dans och god mat.',
 '2024-06-14 11:25:00', 19),

(2, 14, 'Familjedagen på Gården kommer - boka redan nu!',
 'Familjedagen på Gården den 19 maj blir fantastisk! Ponyridning för barnen, ansiktsmålning och mycket mer. Börja planera nu!',
 '2024-06-15 15:40:00', 10),

(2, 6, 'Störande ljud på nätterna - någon som märker detta?',
 'Jag bor på Björkvägen och har märkt väldigt störande motorljud på nätterna omkring 23:00-01:00. Det verkar komma från någon som gör något med en bil eller motorcykel.',
 '2024-06-09 21:45:00', 14);

-- ============================================================================
-- TESTDATA - LISTINGS (GLOBAL - ALLA KAN SE)
-- ============================================================================

INSERT INTO listings (user_id, title, description, publish_date, price, location) VALUES
(1, 'Grå soffa i bra skick - snabb försäljning', 'Säljer en grå 3-sits soffa från Ikea. Den är omkring 5 år gammal men i mycket bra skick. Inga fläckar eller skador. Måste bort på grund av flytt.', '2024-06-01 10:30:00', 1500, 'Centrum'),
(2, 'Högrygga fåtölj - klassisk design', 'En vacker högrygga fåtölj i mörkblå tyg. Mycket bekväm och i utmärkt skick. Perfekt för att läsa en bok eller bara koppla av.', '2024-06-02 14:15:00', 800, 'Storgatan'),
(3, 'Matbord med 4 stolar - trä', 'Trämöbler! Ett vackert matbord i ljust trä med 4 stolar. Bordet är omkring 120x80 cm. Möblerna är några år gamla men fortfarande i mycket bra skick.', '2024-06-03 09:45:00', 2200, 'Centrum'),
(5, 'Bokhylla - Svart trä', 'En stor bokhylla i svart trä med flera hyllnivåer. Perfekt för att lagra böcker och dekoration. Mått omkring 200x100x40 cm. Finns lite repor men inget allvarligt.', '2024-06-04 16:20:00', 350, 'Björkvägen'),
(4, 'Dammsugarrobot - nästan ny', 'Säljer en dammsugarrobot av märket Roborock. Den är bara 2 år gammal och nästan inte använd. Allt tillbehör finns.', '2024-06-05 11:00:00', 1800, 'Östervägen'),
(6, 'Bärbar högtalare - Bluetooth', 'En kraftfull Bluetooth-högtalare som är perfekt för ute och inne. Vattenresistent och med bra batteritid. Färg: Svart.', '2024-06-06 10:15:00', 350, 'Skogsvägen'),
(7, 'Laptop-väska och tillbehör', 'En svart laptop-väska som passar de flesta 15-tums bärbara datorer. Den har flera fickor för organisering. Följer med USB-kabel och skyddsfodral.', '2024-06-07 15:30:00', 150, 'Storgatan'),
(9, 'Smartklocka - Fitbit', 'En Fitbit smartklocka i rosa. Den mäter puls, steg och sömnkvalitet. Bara några månader gammal. Perfekt för att träcka din aktivitet.', '2024-06-08 08:50:00', 600, 'Centrum'),
(8, 'Barnvagn med tillbehör', 'En Stokke-barnvagn i brun färg med flera tillbehör inklusive regnkåpa och väska. Vi växte ur den väldigt snabbt så den är nästan oanvänd.', '2024-06-09 21:45:00', 2500, 'Skolvägen'),
(10, 'Barnleksaker - stor samling', 'Stor samling av barnleksaker från olika åldrar (3-10 år). Allt från pussel, konstruktionssatser, bilar och mer. Många från Lego och andra kända märken.', '2024-06-10 13:20:00', 400, 'Kyrkovägen'),
(3, 'Cykelkurz för barn', 'En röd cykelkurz som passar små barn (3-6 år). Den är säker och enkelt att montera på cykeln. I mycket bra skick.', '2024-06-11 09:30:00', 200, 'Björkvägen'),
(2, 'Mountainbike - Scott märke', 'En Scott-mountainbike i svart och orange. Det är en begagnad men väl skött cykel. 21 växlar och god vägkvalitet.', '2024-06-12 14:10:00', 1200, 'Skogsvägen'),
(4, 'Cykelhjälm - Abus märke', 'En grå cykelhjälm från Abus med SPD-låsning för bergcykel. Den passar ungefär 52-58 cm huvudomfång. Använd men i helt bra skick.', '2024-06-13 10:05:00', 250, 'Västervägen'),
(11, 'Inliners - rollerskor för barn', 'Ett par inliners för barn i storlek 34-37. De är i bra skick och lätt att justera för att växa med barnet. Perfekt för sommaren!', '2024-06-14 11:25:00', 180, 'Storgatan'),
(12, 'Trädgårdsmöbler - bord och stolar', 'En set av trädgårdsmöbler med ett bord och 4 stolar i aluminiumram. De är väldigt lätta och lätta att flytta. Mycket bra för sommarfester.', '2024-06-15 15:40:00', 900, 'Centrum'),
(13, 'Trädgårdsredskap - komplett set', 'Hela samlingen av trädgårdsredskap inklusive spade, högaffär, kratta, sekatör och mycket mer. Allt är i bra skick och väl skött.', '2024-06-16 12:50:00', 350, 'Kyrkovägen'),
(14, 'Trampolin - 3 meter', 'En stor trampolin som är 3 meter i diameter. Den är väl använd men fortfarande helt säker och funktionell. Vi växte ur den och behöver plats.', '2024-06-17 22:15:00', 600, 'Västervägen'),
(15, 'Grill - gasgrill', 'En svart gasgrill med 3 eldstäder. Mycket bra för att grilla under sommaren. Den är väl skött men vi behöver plats.', '2024-06-18 09:35:00', 1100, 'Östervägen'),
(16, 'Boksamling - klassiker och modern litteratur', 'En stor samling av böcker på svenska och engelska. Allt från klassiker till moderna romaner. Många är signerade utgåvor.', '2024-06-19 14:20:00', 500, 'Centrum'),
(17, 'DVD-samling - actionfilmer', 'En samling av omkring 30 DVD:er med actionfilmer och thrillers. Allt från klassiker till nyare filmer. Mycket bra skick.', '2024-06-20 10:30:00', 200, 'Björkvägen'),
(18, 'Mikrovågsugn - IKEA', 'En vit mikrovågsugn från IKEA i mycket bra skick. Den fungerar perfekt och är energieffektiv. Bara några år gammal.', '2024-06-21 11:15:00', 250, 'Skogsvägen'),
(1, 'Kaffebryggare - kaffemaskin', 'En mörkgrå kaffemaskin från Philips med timerfunktion. Den brygger framtida kaffe och är mycket lätt att använda. I mycket bra skick.', '2024-06-22 15:45:00', 280, 'Storgatan'),
(6, 'Matblandare - KitchenAid', 'En röd KitchenAid matblandare med flera tillbehör inklusive krok, vispa och slev. Den är väl använd men fungerar perfekt.', '2024-06-23 09:20:00', 800, 'Kyrkovägen'),
(10, 'Verktygsväska - komplett set', 'En stor verktygsväska fylld med alla grundläggande verktyg: hammare, skruvdragare, skiftnycklar, såg och mycket mer.', '2024-06-24 14:55:00', 400, 'Skolvägen'),
(12, 'El-borr - Makita', 'En kraftfull borr från Makita med många tillbehör. Den är väl använd men fungerar perfekt. Perfekt för hemrenovering och mindre byggprojekt.', '2024-06-25 16:30:00', 600, 'Centrum'),
(14, 'Gitarr - Yamaha akustisk', 'En Yamaha akustisk gitarr i mycket bra skick. Den är lätt att spela och har en vacker ljud. Perfekt för nybörjare eller erfarna.', '2024-06-26 10:40:00', 950, 'Västervägen'),
(5, 'Geckåarium - kompletta setup', 'Ett helt akvarium-setup för geckoödlor med värmelampa, växter och dekoreringar. Allt fungerar perfekt.', '2024-06-27 13:15:00', 500, 'Östervägen'),
(7, 'Persiennor - flera uppsättningar', 'Flera uppsättningar av persiennor i olika storlekar och färger (vit och grå). De är lätta att montera och i mycket bra skick.', '2024-06-28 12:25:00', 150, 'Björkvägen'),
(9, 'Spegel - stor väggspegel', 'En stor väggspegel med vacker ram i guld. Mått omkring 150x100 cm. Perfekt för vardagsrum eller sovrummet. Väldigt vacker och i perfekt skick.', '2024-06-29 18:50:00', 400, 'Skogsvägen');

-- ============================================================================
-- SETUP COMPLETE!
-- ============================================================================
-- Du kan nu köra:
-- SELECT * FROM users;
-- SELECT * FROM group_infos;
-- SELECT * FROM memberlist_groups;
-- SELECT * FROM services WHERE group_id = 1;
-- SELECT * FROM events WHERE group_id = 1;
-- SELECT * FROM general_posts WHERE group_id = 1;
-- SELECT * FROM listings;
-- Osv!