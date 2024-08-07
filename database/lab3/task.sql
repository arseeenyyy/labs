
CREATE OR REPLACE FUNCTION update_museum_rating()
RETURNS TRIGGER AS $$
DECLARE
    id INT;
    total_emotion_value INT;
    total_visits INT;
    new_rating FLOAT;
BEGIN
    IF TG_OP = 'DELETE' THEN
        id := OLD.museum_id;
    ELSE
        id := NEW.museum_id;
    END IF;
    
    SELECT SUM(emotion_value) INTO total_emotion_value
    FROM visits
    JOIN emotion ON visits.emotion_id = emotion.emotion_id
    WHERE id = museum_id;
    
    SELECT COUNT(*) INTO total_visits
    FROM visits
    WHERE id = museum_id;

    IF total_visits > 0 THEN
        new_rating := total_emotion_value::FLOAT / total_visits;
    ELSE
        new_rating := 0;
    END IF;

    UPDATE museum
    SET rate = new_rating
    WHERE id = museum_id;

    RETURN NULL; 
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE TRIGGER update_rating 
AFTER INSERT OR UPDATE ON visits 
FOR EACH ROW
EXECUTE FUNCTION update_museum_rating();

CREATE OR REPLACE TRIGGER update_rating_del
AFTER DELETE ON visits 
FOR EACH ROW 
EXECUTE FUNCTION update_museum_rating();


-------------------------------------------------------------------
INSERT INTO visits(museum_id, visitor_id, visiting_date, emotion_id) 
VALUES (1, 1, '12-12-2023', 2), 
(1, 2, '10-10-2023', 1),
(1, 3, '11-11-2023', 5);

SELECT * FROM museum;

DELETE FROM visits 
WHERE emotion_id = 1; 

