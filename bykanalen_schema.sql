-- ============================================================================
-- BYKANALEN DATABASE SCHEMA
-- Fullständigt optimerat schema med best practices för normalisering,
-- datatyper, relationer och säkerhet
-- ============================================================================

-- Skapa databasen
CREATE DATABASE IF NOT EXISTS `bykanalen`;
USE `bykanalen`;

-- Ställ in teckenset för alla tabeller
SET CHARACTER SET utf8mb4;
SET COLLATE utf8mb4_unicode_ci;

-- ============================================================================
-- TABELLER
-- ============================================================================

-- ============================================================================
-- 1. USERS - Huvudsaklig användartabell
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
-- 3. EVENTS - Eventsystem
-- ============================================================================
CREATE TABLE `events` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(255) NOT NULL,
  `description` TEXT,
  `image` LONGBLOB,
  `publish_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `start_date` DATETIME NOT NULL,
  `end_date` DATETIME,
  `close_registration_date` DATETIME,
  PRIMARY KEY (`id`),
  INDEX `idx_events_start_date` (`start_date`),
  INDEX `idx_events_publish_date` (`publish_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================================
-- 4. EVENT_REGISTRATIONS - ManyToMany mellan users och events
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
-- 5. GENERAL_POSTS - Inlägg skapade av användare (OneToMany)
-- ============================================================================
CREATE TABLE `general_posts` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT NOT NULL,
  `title` VARCHAR(255) NOT NULL,
  `description` TEXT,
  `image` LONGBLOB,
  `publish_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `like_count` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_general_posts_user` 
    FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) 
    ON DELETE CASCADE,
  INDEX `idx_general_posts_user_id` (`user_id`),
  INDEX `idx_general_posts_publish_date` (`publish_date`),
  INDEX `idx_general_posts_like_count` (`like_count`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================================
-- 6. LISTINGS - Annonser/listningar skapade av användare (OneToMany)
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
-- 7. SERVICES - Tjänster/serviceerbjudanden
-- ============================================================================
CREATE TABLE `services` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(255) NOT NULL,
  `description` TEXT,
  `image` LONGBLOB,
  `publish_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `idx_services_publish_date` (`publish_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================================
-- 8. SERVICE_USERS - ManyToMany mellan services och users
-- ============================================================================
CREATE TABLE `service_users` (
  `service_id` BIGINT NOT NULL,
  `user_id` BIGINT NOT NULL,
  `registration_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
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
-- 9. GROUP_INFOS - Grupper/gemenskaper
-- ============================================================================
CREATE TABLE `group_infos` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `group_name` VARCHAR(100) NOT NULL,
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
-- 10. MEMBERLIST_GROUPS - ManyToMany mellan users och groups
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
-- VIEWS (Valfritt men användbara för Spring Boot queries)
-- ============================================================================

-- Visa gruppmedlemsantal utan att lagra det (denormalisering undviks)
CREATE VIEW `group_member_counts` AS
SELECT 
  gi.id,
  gi.group_name,
  COUNT(mg.user_id) as member_count
FROM `group_infos` gi
LEFT JOIN `memberlist_groups` mg ON gi.id = mg.group_info_id
GROUP BY gi.id, gi.group_name;

-- ============================================================================
-- INDIZES SAMMANFATTNING
-- ============================================================================
-- Foreign Keys är automatiskt indexerade
-- Ytterligare indexerade kolonner för vanliga queries:
-- - users: username, email (för login)
-- - user_details: type (för admin-checks)
-- - events: start_date, publish_date (för sorting)
-- - general_posts: user_id, publish_date, like_count (för feeds)
-- - listings: user_id, price, location (för filtreringar)
-- - services: publish_date
-- - group_infos: group_name, created_date
-- - memberlist_groups: group_info_id (för att hitta grupper för en user)

-- ============================================================================
-- FÖRKLARINGAR AV ÄNDRINGAR FRÅN ORIGINAL
-- ============================================================================
/*

1. PRIMARY KEYS & RELATIONER:
   - user_details: Ändrat från composite (id, user_id) till OneToOne med user_id som PK
   - general_posts: Fixat composite key, nu bara id som PK
   - listings: Fixat composite key, nu bara id som PK
   - Alla ManyToMany: Behållit composite keys för join-tables

2. DATATYPER:
   - password: VARCHAR(255) för gehashade lösenord
   - email: VARCHAR(255) enligt RFC 5321
   - age: TINYINT UNSIGNED (0-255)
   - title/name-fält: VARCHAR längre för flexibilitet
   - image-fält: LONGBLOB istället för BLOB/MEDIUMBLOB (mer utrymme)

3. NAMNGIVNING:
   - like_count: Ändrat från "like" (SQL-reserverat ord) till "like_count"
   - Konsistent singular för tabelnamn (users, user_details, events, etc.)
   - Konsistenta FK-namn: fk_[tabell]_[refererad_tabell]
   - Konsistenta index-namn: idx_[tabell]_[kolumn] eller uk_[tabell]_[kolumn]

4. SÄKERHET & INTEGRITET:
   - ON DELETE CASCADE: Om user tas bort, tas även related data bort
   - NOT NULL på kritiska fält
   - UNIQUE constraints på username och email
   - ENUM för type (standard/admin) - begränsar möjliga värden

5. DENORMALISERING BORTTAGEN:
   - member_count i group_infos: Använd vyn group_member_counts istället

6. KOLUMNER TILLAGDA:
   - registration_date i event_registrations och service_users
   - joined_date i memberlist_groups
   - created_date i group_infos
   - Dessa är användbara för sortering och analytics

7. CHARSET:
   - utf8mb4 istället för utf8mb3 (stöder alla Unicode-tecken korrekt)

*/
