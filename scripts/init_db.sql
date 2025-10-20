-- init_postgres.sql
-- Create schema and sample data for the slaughterhouse assignment

CREATE TABLE IF NOT EXISTS animal (
  animalid SERIAL PRIMARY KEY,
  registrationnumber TEXT NOT NULL,
  weight REAL
);

CREATE TABLE IF NOT EXISTS tray (
  trayid SERIAL PRIMARY KEY,
  parttype TEXT NOT NULL,
  maxweight REAL
);

CREATE TABLE IF NOT EXISTS part (
  partid SERIAL PRIMARY KEY,
  type TEXT NOT NULL,
  weight REAL,
  animalid INTEGER REFERENCES animal(animalid),
  trayid INTEGER REFERENCES tray(trayid)
);

CREATE TABLE IF NOT EXISTS product (
  productid SERIAL PRIMARY KEY,
  name TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS producttray (
  productid INTEGER REFERENCES product(productid),
  trayid INTEGER REFERENCES tray(trayid),
  PRIMARY KEY(productid, trayid)
);

-- sample data
INSERT INTO animal (registrationnumber, weight) VALUES ('REG-001', 500.0) ON CONFLICT DO NOTHING;
INSERT INTO animal (registrationnumber, weight) VALUES ('REG-002', 520.0) ON CONFLICT DO NOTHING;

INSERT INTO tray (parttype, maxweight) VALUES ('leg', 100.0) ON CONFLICT DO NOTHING;
INSERT INTO tray (parttype, maxweight) VALUES ('loin', 120.0) ON CONFLICT DO NOTHING;

INSERT INTO product (name) VALUES ('Half Animal Pack') ON CONFLICT DO NOTHING;
INSERT INTO product (name) VALUES ('Leg Pack') ON CONFLICT DO NOTHING;

-- link trays to products (assuming tray ids 1 and 2)
INSERT INTO producttray (productid, trayid) VALUES (1,1) ON CONFLICT DO NOTHING;
INSERT INTO producttray (productid, trayid) VALUES (1,2) ON CONFLICT DO NOTHING;
INSERT INTO producttray (productid, trayid) VALUES (2,1) ON CONFLICT DO NOTHING;

-- parts (associate animals to trays)
INSERT INTO part (type, weight, animalid, trayid) VALUES ('leg', 50, 1, 1) ON CONFLICT DO NOTHING;
INSERT INTO part (type, weight, animalid, trayid) VALUES ('loin', 30, 1, 2) ON CONFLICT DO NOTHING;
INSERT INTO part (type, weight, animalid, trayid) VALUES ('leg', 52, 2, 1) ON CONFLICT DO NOTHING;
