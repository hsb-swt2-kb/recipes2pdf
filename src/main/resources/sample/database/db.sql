pragma foreign_keys=on;
-- Kategorie
CREATE TABLE "category"
(
  id INTEGER DEFAULT NULL PRIMARY KEY AUTOINCREMENT,
  `name` VARCHAR,
  UNIQUE(`name`) ON CONFLICT ABORT
);

-- Gerichtart
CREATE TABLE "course"
(
  id INTEGER DEFAULT NULL PRIMARY KEY AUTOINCREMENT,
  `name` VARCHAR,
  UNIQUE(`name`) ON CONFLICT ABORT
);

-- Region
CREATE TABLE "region"
(
  id INTEGER DEFAULT NULL PRIMARY KEY AUTOINCREMENT,
  `name` VARCHAR,
  UNIQUE(`name`) ON CONFLICT ABORT
);

-- Tageszeit
CREATE TABLE "daytime"
(
  id INTEGER DEFAULT NULL PRIMARY KEY AUTOINCREMENT,
  `name` VARCHAR,
  UNIQUE(`name`) ON CONFLICT ABORT
);

-- Saison
CREATE TABLE "season"
(
  id INTEGER DEFAULT NULL PRIMARY KEY AUTOINCREMENT,
  `name` VARCHAR,
  UNIQUE(`name`) ON CONFLICT ABORT
);

-- Ernährungsart
CREATE TABLE "nurture"
(
  id INTEGER DEFAULT NULL PRIMARY KEY AUTOINCREMENT,
  `name` VARCHAR,
  UNIQUE(`name`) ON CONFLICT ABORT
);

-- Quelle
CREATE TABLE "source"
(
  id INTEGER DEFAULT NULL PRIMARY KEY AUTOINCREMENT,
  `name` VARCHAR,
  UNIQUE(`name`) ON CONFLICT ABORT
);

-- Bewertung
CREATE TABLE "rating"
(
  id INTEGER DEFAULT NULL PRIMARY KEY AUTOINCREMENT,
  `name` VARCHAR,
  UNIQUE(`name`) ON CONFLICT ABORT
);

-- Sortierebene
CREATE TABLE "sortlevel"
(
  id INTEGER DEFAULT NULL PRIMARY KEY AUTOINCREMENT,
  `name` VARCHAR,
  UNIQUE(`name`) ON CONFLICT ABORT
);

-- Sortiereben<-->Kochbuch
CREATE TABLE "cookbook_sortlevel"
(
  id INTEGER DEFAULT NULL PRIMARY KEY AUTOINCREMENT,
  cookbook_id INTEGER,
  sortlevel_id INTEGER,
  FOREIGN KEY (cookbook_id) REFERENCES cookbook(id),
  FOREIGN KEY (sortlevel_id) REFERENCES sortlevel(id)
  UNIQUE(cookbook_id, sortlevel_id) ON CONFLICT ABORT
);


CREATE TABLE cookbook
(
  id INTEGER DEFAULT NULL PRIMARY KEY AUTOINCREMENT,
  title VARCHAR(255)
);
CREATE UNIQUE INDEX cookbook_cookbook_id_uindex ON cookbook (id);

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
  FOREIGN KEY (category_id) REFERENCES category(id),
  FOREIGN KEY (course_id) REFERENCES course(id),
  FOREIGN KEY (region_id) REFERENCES region(id),
  FOREIGN KEY (daytime_id) REFERENCES daytime(id),
  FOREIGN KEY (season_id) REFERENCES season(id),
  FOREIGN KEY (nurture_id) REFERENCES nurture(id)
);
CREATE UNIQUE INDEX recipes_recipe_id_uindex ON recipe (id);

CREATE TABLE ingredient
(
  id INTEGER DEFAULT NULL PRIMARY KEY AUTOINCREMENT,
  name VARCHAR(255)
);
CREATE UNIQUE INDEX ingredients_ingredient_id_uindex ON ingredient (id);

--DROP TABLE IF EXISTS cookbook_recipe;
CREATE TABLE cookbook_recipe
(
  id INTEGER DEFAULT NULL PRIMARY KEY AUTOINCREMENT,
  cookbook_id INTEGER,
  recipe_id INTEGER,
  FOREIGN KEY (cookbook_id) REFERENCES cookbook(id),
  FOREIGN KEY (recipe_id) REFERENCES recipe(id),
  UNIQUE(cookbook_id, recipe_id) ON CONFLICT ABORT
);

CREATE TABLE recipe_ingredient
(
  id INTEGER DEFAULT NULL PRIMARY KEY AUTOINCREMENT,
  recipe_id INTEGER,
  ingredient_id INTEGER,
  unit_id INTEGER,
  amount INTEGER,
  FOREIGN KEY (recipe_id) REFERENCES recipe(id),
  FOREIGN KEY (ingredient_id) REFERENCES ingredient(id),
  FOREIGN KEY (unit_id) REFERENCES unit(id),
  UNIQUE(recipe_id, ingredient_id) ON CONFLICT ABORT
);

CREATE TABLE unit
(
  id INTEGER DEFAULT NULL PRIMARY KEY AUTOINCREMENT,
  `name` VARCHAR,
  UNIQUE(`name`) ON CONFLICT ABORT
);
