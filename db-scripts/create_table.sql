-- Actor table create script
CREATE TABLE `imdb_actor` (
  `actor_id` INT(11) NOT NULL AUTO_INCREMENT,
  `actor_name` VARCHAR(255) NOT NULL,
  `actor_sex` VARCHAR(255) NOT NULL,
  `actor_dob` DATE NOT NULL,
  `actor_bio` VARCHAR(255) NULL,
  `is_deleted` TINYINT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`actor_id`))engine=InnoDB;

CREATE TABLE `imdb_file` (
  `file_id` INT(11) NOT NULL AUTO_INCREMENT,
  `file_name` VARCHAR(255) NULL,
  `created_at` DATETIME NOT NULL DEFAULT current_timestamp,
  `is_deleted` TINYINT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`file_id`))engine=InnoDB;

CREATE TABLE `imdb_producer` (
  `producer_id` INT(11) NOT NULL AUTO_INCREMENT,
  `producer_name` VARCHAR(45) NOT NULL,
  `producer_sex` VARCHAR(45) NOT NULL,
  `producer_dob` DATE NOT NULL,
  `producer_bio` VARCHAR(45) NULL,
  `is_deleted` TINYINT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`producer_id`))engine=InnoDB;

CREATE TABLE `imdb_movie` (
  `movie_id` INT(11) NOT NULL AUTO_INCREMENT,
  `movie_name` VARCHAR(255) NOT NULL,
  `movie_year_of_release` VARCHAR(45) NULL,
  `movie_plot` VARCHAR(45) NULL,
  `is_deleted` TINYINT(1) NOT NULL DEFAULT 0,
  `frn_movie_poster` INT(11) NULL,
  `frn_producer_id` INT(11) NULL,
  PRIMARY KEY (`movie_id`),
  INDEX `fk_imdb_movie_poster_idx` (`frn_movie_poster` ASC),
  INDEX `fk_imdb_movie_producer_idx` (`frn_producer_id` ASC),
  CONSTRAINT `fk_imdb_movie_poster`
    FOREIGN KEY (`frn_movie_poster`)
    REFERENCES `imdb_file` (`file_id`)
    ON DELETE SET NULL
    ON UPDATE SET NULL,
  CONSTRAINT `fk_imdb_movie_producer`
    FOREIGN KEY (`frn_producer_id`)
    REFERENCES `imdb_producer` (`producer_id`)
    ON DELETE SET NULL
    ON UPDATE SET NULL)engine=InnoDB;

-- Association table for actor to movie --
   CREATE TABLE `imdb_actor_to_movie` (
  `am_id` INT(11) NOT NULL,
  `frn_actor_id` INT(11) NULL,
  `frn_movie_id` INT(11) NULL,
  PRIMARY KEY (`am_id`),
  INDEX `fk_imdb_actor_to_movie_actor_id_idx` (`frn_actor_id` ASC),
  INDEX `fk_imdb_actor_to_movie_movie_id_idx` (`frn_movie_id` ASC),
  CONSTRAINT `fk_imdb_actor_to_movie_actor_id`
    FOREIGN KEY (`frn_actor_id`)
    REFERENCES `dx_imdb`.`imdb_actor` (`actor_id`)
    ON DELETE SET NULL
    ON UPDATE SET NULL,
  CONSTRAINT `fk_imdb_actor_to_movie_movie_id`
    FOREIGN KEY (`frn_movie_id`)
    REFERENCES `dx_imdb`.`imdb_movie` (`movie_id`)
    ON DELETE SET NULL
    ON UPDATE SET NULL)engine=InnoDB;

