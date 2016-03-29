pragma foreign_keys=on;

CREATE TABLE cookbook
(
  id INTEGER DEFAULT NULL PRIMARY KEY AUTOINCREMENT,
  title VARCHAR(255)
);
CREATE UNIQUE INDEX cookbook_cookbook_id_uindex ON cookbook (id)

CREATE TABLE recipe
(
  id INTEGER DEFAULT NULL PRIMARY KEY AUTOINCREMENT,
  title VARCHAR(255)
);
CREATE UNIQUE INDEX recipes_recipe_id_uindex ON recipe (id)

CREATE TABLE ingredient
(
  id INTEGER DEFAULT NULL PRIMARY KEY AUTOINCREMENT,
  name VARCHAR(255)
);
CREATE UNIQUE INDEX ingredients_ingredient_id_uindex ON ingredient (id)

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
  amount INTEGER,
  FOREIGN KEY (recipe_id) REFERENCES recipe(id),
  FOREIGN KEY (ingredient_id) REFERENCES ingredient(id),
  UNIQUE(recipe_id, ingredient_id) ON CONFLICT ABORT
);
