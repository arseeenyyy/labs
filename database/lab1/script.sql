--dropping enum types 
DROP TYPE IF EXISTS sex CASCADE; 
DROP TYPE IF EXISTS nationality CASCADE; 
DROP TYPE IF EXISTS style CASCADE;
DROP TYPE IF EXISTS emotion_type CASCADE;
--dropping tables
DROP TABLE IF EXISTS visitor CASCADE;
DROP TABLE IF EXISTS emotion CASCADE; 
DROP TABLE IF EXISTS author CASCADE; 
DROP TABLE IF EXISTS showpiece_type CASCADE; 
DROP TABLE IF EXISTS location CASCADE; 
DROP TABLE IF EXISTS museum CASCADE; 
DROP TABLE IF EXISTS showpiece CASCADE; 
DROP TABLE IF EXISTS visits CASCADE;
--creating types
CREATE TYPE sex AS enum('male', 'female'); 
CREATE TYPE nationality AS enum('african', 'asian', 'european', 'central american', 'middle eastern', 'north african', 'south african', 'southeast asian');
CREATE TYPE style AS enum('abstractionism', 'avangardism', 'brutalism', 'classicism', 'cubism', 'expressionism', 'formalism', 'gothic', 'realism', 'rococo', 'urbanism', 'purism', 'neorealism', 'futurism', 'romanticism', 'symbolism');
CREATE TYPE emotion_type AS enum('angry', 'sad', 'happy', 'surprised', 'bored', 'disappointed', 'confused', 'dreamy', 'glad', 'interested', 'normal', 'scared', 'sorrowful', 'upset', 'amazed', 'critical');
--creating tables
CREATE TABLE visitor(
	visitor_id serial PRIMARY KEY, 
	name text NOT NULL, 
	surname text NOT NULL, 
	birthday date NOT NULL, 		
	sex sex NOT NULL, 
	nationality nationality NOT NULL, 
	CONSTRAINT date_not_future CHECK(birthday <= CURRENT_DATE)
); 
CREATE TABLE emotion(
	emotion_id serial PRIMARY KEY, 
	emotion_type emotion_type NOT NULL, 
	emotion_value int NOT NULL CHECK(emotion_value > 0 AND emotion_value < 101)
);
CREATE TABLE author(
	author_id serial PRIMARY KEY, 
	name text NOT NULL, 
	surname text NOT NULL, 
	birthday date NOT NULL,
	sex sex NOT NULL, 		 
	nationality nationality NOT NULL, 
	CONSTRAINT date_not_future CHECK(birthday <= CURRENT_DATE)
);
CREATE TABLE showpiece_type(
	type_id serial PRIMARY KEY, 
	style style NOT NULL, 
	creation_date date NOT NULL,		 
	material text NOT NULL, 
	CONSTRAINT date_not_future CHECK(creation_date <= CURRENT_DATE) 
);

----------------------------------------------
CREATE TABLE country(
	id serial PRIMARY KEY, 
	country text NOT NULL 
);
CREATE TABLE city(
	city_id serial PRIMARY KEY, 
	city text NOT NULL, 
	country_id int NOT NULL references country(id)
);
CREATE TABLE location(
	location_id serial PRIMARY KEY, 
	district text NOT NULL, 
	street text NOT NULL,
	city_id int NOT NULL REFERENCES city(city_id)
);
---------------------------------------------
CREATE TABLE museum(
	museum_id serial PRIMARY KEY, 
	name text NOT NULL, 
	foundation_date date NOT NULL,		 
	ticket_price int NOT NULL CHECK (ticket_price >= 0),
	working_hours text NOT NULL,
	location_id int NOT NULL REFERENCES location(location_id),
	rate FLOAT DEFAULT 0,
	CONSTRAINT date_not_future CHECK(foundation_date <= CURRENT_DATE)
);
CREATE TABLE showpiece(
        showpiece_id serial PRIMARY KEY, 
        name text NOT NULL, 
        author_id int NOT NULL REFERENCES author(author_id), 
        museum_id int NOT NULL REFERENCES museum(museum_id), 
        showpiece_type_id int NOT NULL REFERENCES showpiece_type(type_id)
);
CREATE TABLE visits (
    visit_id SERIAL PRIMARY KEY,
    museum_id INT NOT NULL REFERENCES museum(museum_id),
    visitor_id INT NOT NULL REFERENCES visitor(visitor_id),
    visiting_date DATE NOT NULL DEFAULT CURRENT_DATE,
    emotion_id int not null references emotion(emotion_id),
    CONSTRAINT date_not_future CHECK (visiting_date <= CURRENT_DATE)
    
);
--inserting data 
INSERT INTO visitor(name, surname, birthday, sex, nationality) 
VALUES
	('Arseniy', 'Rubtsov', '1995-12-04', 'male', 'european'), 
	('Victor', 'Nikolaenko','2003-06-25', 'male', 'european'), 
	('Daria', 'Rudenko', '2000-01-01', 'female', 'european'),	
	('Andrei', 'Volkov', '2007-10-18', 'male', 'asian'),
	('Antony', 'Trump', '1985-09-7', 'male', 'central american'),
	('Robert', 'Cole', '1999-03-28', 'male', 'middle eastern'),
	('Nancy', 'Fisher', '2005-06-04', 'female', 'african');	
INSERT INTO emotion(emotion_type, emotion_value) 
VALUES 
	('happy', 56), 
	('surprised', 100), 
	('bored', 20), 
	('confused', 80), 
	('glad', 90), 
	('interested', 45),
	('scared', 10),
	('critical', 28), 
	('upset', 15), 
	('normal', 67), 
	('dreamy', 61), 
	('happy', 89), 
	('normal', 78), 
	('amazed', 99);  
INSERT INTO author(name, surname, birthday, sex, nationality) 
VALUES 
	('Linda', 'Quinn', '1995-12-02', 'female', 'european'), 
	('Viola', 'Baker', '1991-06-05', 'female', 'asian'), 
	('Richard', 'Barret', '1984-05-06', 'male', 'central american'), 
	('Chirstopher', 'Reeves', '1999-02-01', 'male', 'southeast asian'), 
	('Susan', 'Oliver', '2001-01-12', 'female', 'north african'), 
	('Raymond', 'Johnson', '1979-08-09', 'male', 'middle eastern'); 
INSERT INTO showpiece_type(style, creation_date, material) 
VALUES 
	('gothic', '2022-05-05', 'canvast, acrylic'), 
	('rococo', '2002-01-01', 'stone'), 
	('realism', '2015-12-01', 'marble'), 
	('urbanism', '2021-01-01', 'glass'), 
	('neorealism', '2005-06-04', 'canvast, gouache'), 
	('classicism', '2014-02-12', 'clay'), 
	('avangardism', '2023-12-15', 'glass'), 
	('formalism', '2016-07-26', 'canvast, oil paints'), 
	('symbolism', '2017-06-12', 'glass'), 
	('formalism', '2015-04-29', 'steel'), 
	('gothic', '2012-01-14', 'wooden'), 
	('realism', '2011-05-16', 'plastic'), 
	('futurism', '2021-12-21', 'fiber'),
	('abstractionism', '2022-07-07', 'textile'), 
	('brutalism', '2023-05-17', 'glass'); 
INSERT INTO location(country, city, district, street) 
VALUES 
	('Russia', 'Saint-Peteresburg', 'Nevskiy', 'Babushkina'), 
	('Russia', 'Saint-Peteresburg', 'Primorskiy', 'Savushkina'), 
	('Russia', 'Saint-Peteresburg', 'Frunzenskiy', 'Salova'), 
	('Russia', 'Moscow', 'Arbat', 'Bolshaya Molchanovka'), 
	('Russia', 'Moscow', 'Tverskoi', 'Petrovka'),
	('Russia', 'Moscow', 'Taganskiy', 'Vekovaya'),
	('Russia', 'Saint-Peteresburg', 'Nevskiy', 'Gorohovaya'), 
	('Russia', 'Moscow', 'Arbat', 'Lenina');

insert into location(district, street, city_id) values ('Nevskiy', 'Babushkina', 3), ('Primorskiy', 'Savushkina', 3), ('Frunzenskiy', 'Salova', 3), ('Arbat', 'Bolshaya Molchanovka', 4), ('Tverskoi', 'Petrovka', 4), ('Taganskiy', 'Vekovaya', 4), ('Nevskiy', 'Gorohovaya', 3), ('Arbat', 'Lenina', 4);



INSERT INTO museum(name, foundation_date, ticket_price, working_hours, location_id)
VALUES 
	('Artefakt', '1895-05-01', 1000, '10:00-18:00', 1), 
	('Impulse', '1967-12-02', 500, '12:00-22:00', 2), 
	('Labyrinth', '2000-08-09', 300, '16:00-2:00', 3), 
	('Rezonanse', '2005-04-16', 1500, '11:00-23:00', 4), 
	('Evolution', '1955-12-05', 400, '2:00-8:00', 5), 
	('Eternity', '2012-02-01', 500, '13:00-19:00', 6), 
	('Bimba', '1999-01-01', 500, '12:00-15:00', 7), 
	('Sobaka', '2005-01-05', 600, '15:00-18:00', 8);

INSERT INTO showpiece(name, author_id, museum_id, showpiece_type_id) 
VALUES	
	('Moonwalk', 1, 1, 1), 
	('Space', 1, 1, 3), 
	('Brick', 3, 1, 2), 
	('Illusion', 3, 2, 5), 
	('TimesNewRoman', 3, 2, 6), 
	('Magnum', 2, 2, 12), 
	('BlueWater', 2, 3, 15), 
	('Fire', 4, 3, 14), 
	('Waterfall', 4, 4, 13), 
	('Darkness', 5, 4, 7), 
	('Angel', 5, 4, 4), 
	('Lake', 5, 5, 9), 
	('Harmony', 6, 5, 11), 
	('Macro', 6, 6, 10), 
	('Micro', 6, 6, 8);
INSERT INTO visits(museum_id, visitor_id, visiting_date)
VALUES 
	(1, 1, '2023-05-06'), 
	(1, 2, '2023-05-10'), 
	(1, 3, '2023-05-10'), 
	(1, 4, '2023-05-11'), 
	(1, 6, '2023-06-15'), 
	(2, 2, '2023-02-17'), 
	(2, 3, '2023-02-17'), 
	(2, 5, '2023-02-17'), 
	(3, 1, '2023-08-20'), 
	(3, 2, '2023-07-12'), 
	(4, 4, '2022-03-03'), 
	(4, 5, '2022-02-03'), 
	(4, 6, '2023-04-05'), 
	(4, 7, '2023-01-06'), 
	(5, 1, '2023-07-07'), 
	(5, 7, '2023-07-08'), 
	(6, 4, '2023-05-20'), 
	(6, 6, '2023-12-14'),
	(6, 7, '2023-11-12'); 
	
	
	
SELECT m.name, l.city
FROM museum m
INNER JOIN location l ON m.location_id = l.location_id
WHERE l.city = 'Saint-Peteresburg';



одинаковый район разная улица 
добавить два музея 


SELECT m.name, l.disctrict 
FROM museum m 
INNER JOIN location l ON m.location_id = l.location_id 
WHERE 



SELECT m.name AS museum_name, l.district, l.street 
FROM museum m 
INNER JOIN location l1 on m.location_id = l.location_id 
INNER JOIN location l2 
WHERE l1.district = l2.district AND l1.street != l2.street
WHERE l.district IN (SELECT district FROM location GROUP BY district HAVING COUNT(DISTINCT street) > 1)
ORDER BY l.district;



CREATE TABLE location(
	location_id serial PRIMARY KEY, 
	country text NOT NULL, 
	city text NOT NULL, 
	district text NOT NULL, 
	street text NOT NULL 
);



insert into visits(museum_id, visitor_id, visiting_date, emotion_id) values (1, 1, '2023-05-05', 1);
insert into visits(museum_id, visitor_id, visiting_date, emotion_id) values (1, 3, '2023-06-06', 5);


















SELECT name city FROM museum location INNER JOIN location ON museum.location_id=location.location_id WHERE location.city='Moscow';

