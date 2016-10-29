BEGIN TRANSACTION;
CREATE TABLE unit
(
  id INTEGER DEFAULT NULL PRIMARY KEY AUTOINCREMENT,
  `name` VARCHAR,
  UNIQUE(`name`) ON CONFLICT ABORT
);
CREATE TABLE "source"
(
  id INTEGER DEFAULT NULL PRIMARY KEY AUTOINCREMENT,
  `name` VARCHAR,
  UNIQUE(`name`) ON CONFLICT ABORT
);
INSERT INTO `source` (id,name) VALUES (1,'Eigene');
INSERT INTO `source` (id,name) VALUES (2,'Chefkoch');
INSERT INTO `source` (id,name) VALUES (3,'Weightwatchers');
CREATE TABLE "sortlevel"
(
  id INTEGER DEFAULT NULL PRIMARY KEY AUTOINCREMENT,
  `name` VARCHAR,
  UNIQUE(`name`) ON CONFLICT ABORT
);
INSERT INTO `sortlevel` (id,name) VALUES (1,'Kategorie');
INSERT INTO `sortlevel` (id,name) VALUES (2,'Gerichtart');
INSERT INTO `sortlevel` (id,name) VALUES (3,'Ernährungsart');
INSERT INTO `sortlevel` (id,name) VALUES (4,'Quelle');
INSERT INTO `sortlevel` (id,name) VALUES (5,'Tageszeit');
INSERT INTO `sortlevel` (id,name) VALUES (6,'Anlass');
INSERT INTO `sortlevel` (id,name) VALUES (7,'Region');
INSERT INTO `sortlevel` (id,name) VALUES (8,'Jahreszeit');
CREATE TABLE "season"
(
  id INTEGER DEFAULT NULL PRIMARY KEY AUTOINCREMENT,
  `name` VARCHAR,
  UNIQUE(`name`) ON CONFLICT ABORT
);
INSERT INTO `season` (id,name) VALUES (1,'Frühling');
INSERT INTO `season` (id,name) VALUES (2,'Sommer');
INSERT INTO `season` (id,name) VALUES (3,'Herbst');
INSERT INTO `season` (id,name) VALUES (4,'Weihnachten');
INSERT INTO `season` (id,name) VALUES (5,NULL);
CREATE TABLE "region"
(
  id INTEGER DEFAULT NULL PRIMARY KEY AUTOINCREMENT,
  `name` VARCHAR,
  UNIQUE(`name`) ON CONFLICT ABORT
);
INSERT INTO `region` (id,name) VALUES (1,'griechisch');
INSERT INTO `region` (id,name) VALUES (2,'deutsch');
INSERT INTO `region` (id,name) VALUES (3,'italienisch');
INSERT INTO `region` (id,name) VALUES (4,'spanisch');
INSERT INTO `region` (id,name) VALUES (5,'syrisch');
CREATE TABLE recipe_ingredient
(
  id INTEGER DEFAULT NULL PRIMARY KEY AUTOINCREMENT,
  recipe_id INTEGER,
  ingredient_id INTEGER,
  unit_id INTEGER,
  amount REAL,
  FOREIGN KEY (recipe_id) REFERENCES recipe(id),
  FOREIGN KEY (ingredient_id) REFERENCES ingredient(id),
  FOREIGN KEY (unit_id) REFERENCES unit(id),
  UNIQUE(recipe_id, ingredient_id) ON CONFLICT ABORT
);
CREATE TABLE recipe
(
  id INTEGER DEFAULT NULL PRIMARY KEY AUTOINCREMENT,
  title VARCHAR(255),
  text VARCHAR(255),
  portions INTEGER,
  duration INTEGER,
  image BLOB,
  calories INTEGER,
  category_id INTEGER,
  course_id INTEGER,
  region_id INTEGER,
  daytime_id INTEGER,
  season_id INTEGER,
  nurture_id INTEGER,
  source_id INTEGER,
  FOREIGN KEY (category_id) REFERENCES category(id),
  FOREIGN KEY (course_id) REFERENCES course(id),
  FOREIGN KEY (region_id) REFERENCES region(id),
  FOREIGN KEY (daytime_id) REFERENCES daytime(id),
  FOREIGN KEY (season_id) REFERENCES season(id),
  FOREIGN KEY (nurture_id) REFERENCES nurture(id),
  FOREIGN KEY (source_id) REFERENCES source(id)
);
CREATE TABLE "rating"
(
  id INTEGER DEFAULT NULL PRIMARY KEY AUTOINCREMENT,
  `name` VARCHAR,
  UNIQUE(`name`) ON CONFLICT ABORT
);
CREATE TABLE "nurture"
(
  id INTEGER DEFAULT NULL PRIMARY KEY AUTOINCREMENT,
  `name` VARCHAR,
  UNIQUE(`name`) ON CONFLICT ABORT
);
INSERT INTO `nurture` (id,name) VALUES (1,'Vegan');
INSERT INTO `nurture` (id,name) VALUES (2,'Vegetarisch');
INSERT INTO `nurture` (id,name) VALUES (3,'Fleisch');
CREATE TABLE ingredient
(
  id INTEGER DEFAULT NULL PRIMARY KEY AUTOINCREMENT,
  name VARCHAR(255)
);
CREATE TABLE "daytime"
(
  id INTEGER DEFAULT NULL PRIMARY KEY AUTOINCREMENT,
  `name` VARCHAR,
  UNIQUE(`name`) ON CONFLICT ABORT
);
INSERT INTO `daytime` (id,name) VALUES (1,'Frühstück');
INSERT INTO `daytime` (id,name) VALUES (2,'Mittag');
INSERT INTO `daytime` (id,name) VALUES (3,'Abend');
CREATE TABLE "course"
(
  id INTEGER DEFAULT NULL PRIMARY KEY AUTOINCREMENT,
  `name` VARCHAR,
  UNIQUE(`name`) ON CONFLICT ABORT
);
INSERT INTO `course` (id,name) VALUES (1,'Nudeln');
INSERT INTO `course` (id,name) VALUES (2,'Reis');
INSERT INTO `course` (id,name) VALUES (3,'Kartoffeln');
INSERT INTO `course` (id,name) VALUES (4,'Fleisch');
CREATE TABLE "cookbook_sortlevel"
(
  id INTEGER DEFAULT NULL PRIMARY KEY AUTOINCREMENT,
  cookbook_id INTEGER,
  sortlevel_id INTEGER,
  FOREIGN KEY (cookbook_id) REFERENCES cookbook(id),
  FOREIGN KEY (sortlevel_id) REFERENCES sortlevel(id)
  UNIQUE(cookbook_id, sortlevel_id) ON CONFLICT ABORT
);
CREATE TABLE cookbook_recipe
(
  id INTEGER DEFAULT NULL PRIMARY KEY AUTOINCREMENT,
  cookbook_id INTEGER,
  recipe_id INTEGER,
  FOREIGN KEY (cookbook_id) REFERENCES cookbook(id),
  FOREIGN KEY (recipe_id) REFERENCES recipe(id),
  UNIQUE(cookbook_id, recipe_id) ON CONFLICT ABORT
);
CREATE TABLE cookbook
(
  id INTEGER DEFAULT NULL PRIMARY KEY AUTOINCREMENT,
  title VARCHAR(255)
);
INSERT INTO `cookbook` (id,title) VALUES (1,'Standardkochbuch');
CREATE TABLE `cause` (
	`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
	`name`	TEXT NOT NULL UNIQUE
);
INSERT INTO `cause` (id,name) VALUES (1,'Grillen');
INSERT INTO `cause` (id,name) VALUES (2,'Geburtstag');
INSERT INTO `cause` (id,name) VALUES (3,'Hochzeit');
CREATE TABLE "category"
(
  id INTEGER DEFAULT NULL PRIMARY KEY AUTOINCREMENT,
  `name` VARCHAR,
  UNIQUE(`name`) ON CONFLICT ABORT
);
INSERT INTO `category` (id,name) VALUES (1,'Grundrezept');
INSERT INTO `category` (id,name) VALUES (2,'Vorspeise');
INSERT INTO `category` (id,name) VALUES (3,'Hauptgericht');
INSERT INTO `category` (id,name) VALUES (4,'Nachspeise');
INSERT INTO `category` (id,name) VALUES (5,'Brot');
INSERT INTO `category` (id,name) VALUES (6,'Kuchen');
CREATE UNIQUE INDEX recipes_recipe_id_uindex ON recipe (id);
CREATE UNIQUE INDEX ingredients_ingredient_id_uindex ON ingredient (id);
CREATE UNIQUE INDEX cookbook_cookbook_id_uindex ON cookbook (id);
COMMIT;
