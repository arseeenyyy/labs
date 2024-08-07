package arseeenyyy.com.github.server.db;

import arseeenyyy.com.github.common.interfaces.InDataBaseManager;
import arseeenyyy.com.github.common.models.Color;
import arseeenyyy.com.github.common.models.Coordinates;
import arseeenyyy.com.github.common.models.Dragon;
import arseeenyyy.com.github.common.models.DragonCave;
import arseeenyyy.com.github.common.util.Request;
import org.postgresql.util.PGobject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
public class DataBaseManager implements InDataBaseManager {
    private Connection connection;
    private static final Logger LOGGER = LoggerFactory.getLogger(DataBaseManager.class);
    private final String COLLECTION = "SELECT dragon.id, dragon.name, coordinates.x, coordinates.y, dragon.creationdate, dragon.age, dragon.description, dragon.wingspan, dragon.color, dragon_cave.number_of_treasures " +
            "FROM dragon " +
            "INNER JOIN coordinates ON dragon.coordinates_id = coordinates.coord_id " +
            "INNER JOIN dragon_cave ON dragon.cave_id = dragon_cave.cave_id where user_id = ?";
    private final String ALL_ELEMENTS = "SELECT dragon.id, dragon.name, coordinates.x, coordinates.y, dragon.creationdate, dragon.age, dragon.description, dragon.wingspan, dragon.color, dragon_cave.number_of_treasures " +
            "FROM dragon " +
            "INNER JOIN coordinates ON dragon.coordinates_id = coordinates.coord_id " +
            "INNER JOIN dragon_cave ON dragon.cave_id = dragon_cave.cave_id";
    private final String ADD_DRAGON = "INSERT INTO dragon (name, coordinates_id, age, description, wingspan, color, cave_id, user_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?) RETURNING id";
    private final String INSERT_NEW_CAVE = "insert into dragon_cave(number_of_treasures) values (?) returning cave_id";
    private final String INSERT_NEW_COORDINATES = "insert into coordinates(x, y) values (?, ?) returning coord_id";
    private final String GET_USER = "SELECT user_id, username, password_hash from users where username = ? and password_hash = ?";
    private final String GET_USR_ID = "select user_id from users where username = ? and password_hash = ?";
    private final String DELETE_DRAGONS_BY_USER_ID = "DELETE FROM dragon WHERE user_id = ?";
    private final String DELETE_COORDINATES_BY_USER_ID = "DELETE FROM coordinates WHERE coord_id IN (SELECT coordinates_id FROM dragon WHERE user_id = ?)";
    private final String DELETE_CAVES_BY_USER_ID = "DELETE FROM dragon_cave WHERE cave_id IN (SELECT cave_id FROM dragon WHERE user_id = ?)";
    private final String UPDATE_DRAGON_BY_ID = "";
    private final String ALIEN_COLLECTION = "SELECT dragon.id, dragon.name, coordinates.x, coordinates.y, dragon.creationdate, dragon.age, dragon.description, dragon.wingspan, dragon.color, dragon_cave.number_of_treasures " +
            "FROM dragon " +
            "INNER JOIN coordinates ON dragon.coordinates_id = coordinates.coord_id " +
            "INNER JOIN dragon_cave ON dragon.cave_id = dragon_cave.cave_id where user_id <> ?";
    private final String REMOVE_BY_ID = "delete from dragon where id = ? and user_id = ?";
    private final String REMOVE_BY_WINGSPAN = "delete from dragon where wingspan = ?";
    private final String INSERT_NEW_USER = "insert into users(username, password_hash) values (?, ?)";

    public Connection connectToDatabase() {
        Properties info = new Properties();
        try {
            info.load(new FileInputStream("db.cfg"));
            return DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/studs", info);
        } catch (IOException e) {
            System.err.println("Error loading database configuration: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error connecting to database: " + e.getMessage());
        }
        return null;
    }

    public ArrayList<Dragon> getAllElementsFromDataBase() {
        ArrayList<Dragon> collection = new ArrayList<>();
        connection = connectToDatabase();
        if (connection == null) {
            return collection;
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(ALL_ELEMENTS);
            ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Coordinates coordinates = new Coordinates(resultSet.getInt("x"), resultSet.getFloat("y"));
                Date creationDate = resultSet.getDate("creationdate");
                long age = resultSet.getLong("age");
                String description = resultSet.getString("description");
                double wingspan = resultSet.getDouble("wingspan");
                Color color = Color.valueOf(resultSet.getString("color"));
                long numberOfTreasures = resultSet.getLong("number_of_treasures");

                Dragon dragon = new Dragon(id, name, coordinates, toLocalDateTime(creationDate), age, description, wingspan, color, new DragonCave(numberOfTreasures));
                collection.add(dragon);
            }

        }catch (SQLException exception) {
            System.err.println("Error executing query: " + exception.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.error("Failed to close database connection. Error message: {}, SQLState: {}, ErrorCode: {}",
                        e.getMessage(), e.getSQLState(), e.getErrorCode(), e);            }
        }

        return collection;
    }
    public ArrayList<Dragon> getAlienCollection(int userId) {
        ArrayList<Dragon> alienCollection = new ArrayList<>();
        connection = connectToDatabase();
        try (PreparedStatement preparedStatement = connection.prepareStatement(ALIEN_COLLECTION)) {

            preparedStatement.setInt(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    Coordinates coordinates = new Coordinates(resultSet.getInt("x"), resultSet.getFloat("y"));
                    Date creationDate = resultSet.getDate("creationdate");
                    long age = resultSet.getLong("age");
                    String description = resultSet.getString("description");
                    double wingspan = resultSet.getDouble("wingspan");
                    Color color = Color.valueOf(resultSet.getString("color"));
                    long numberOfTreasures = resultSet.getLong("number_of_treasures");

                    Dragon dragon = new Dragon(id, name, coordinates, toLocalDateTime(creationDate), age, description, wingspan, color, new DragonCave(numberOfTreasures));
                    alienCollection.add(dragon);
                }
            }
        } catch (SQLException exception) {
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Error closing the connection: " + e.getMessage());
            }
        }
        return alienCollection;
    }

    public boolean removeDragonByWingspan(double wingspan) {
        connection = connectToDatabase();
        try (PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_BY_WINGSPAN)) {
            preparedStatement.setDouble(1, wingspan);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException exception) {
        }finally {
            try {
                connection.close();
            }catch (SQLException e) {
                LOGGER.error("Failed to close database connection. Error message: {}, SQLState: {}, ErrorCode: {}",
                        e.getMessage(), e.getSQLState(), e.getErrorCode(), e);
            }
        }
        return false;
    }

    public boolean removeDragonById(int id, int userid) {
        connection = connectToDatabase();
        try (PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_BY_ID)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, userid);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        }catch (SQLException exception) {

        } finally {
            try {
                connection.close();
            }catch (SQLException e) {
                LOGGER.error("Failed to close database connection. Error message: {}, SQLState: {}, ErrorCode: {}",
                        e.getMessage(), e.getSQLState(), e.getErrorCode(), e);
            }
        }
        return false;
    }
    public ArrayList<Dragon> getCollectionFromDataBase(int user_id) {
        ArrayList<Dragon> collection = new ArrayList<>();
        connection = connectToDatabase();
        if (connection == null) {
            return collection; // Return empty collection if connection failed
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(COLLECTION)) {
            preparedStatement.setInt(1, user_id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    Coordinates coordinates = new Coordinates(resultSet.getInt("x"), resultSet.getFloat("y"));
                    Date creationDate = resultSet.getDate("creationdate");
                    long age = resultSet.getLong("age");
                    String description = resultSet.getString("description");
                    double wingspan = resultSet.getDouble("wingspan");
                    Color color = Color.valueOf(resultSet.getString("color"));
                    long numberOfTreasures = resultSet.getLong("number_of_treasures");

                    Dragon dragon = new Dragon(id, name, coordinates, toLocalDateTime(creationDate), age, description, wingspan, color, new DragonCave(numberOfTreasures));
                    collection.add(dragon);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error executing query: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.error("Failed to close database connection. Error message: {}, SQLState: {}, ErrorCode: {}",
                        e.getMessage(), e.getSQLState(), e.getErrorCode(), e);
            }
        }

        return collection;
    }
    private LocalDateTime toLocalDateTime(Date date) {
        return new java.sql.Timestamp(date.getTime()).toLocalDateTime();
    }

    public boolean getUser(Request request) {
        connection = connectToDatabase();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_USER)) {
            String login = request.getLogin();
            String password = Encryptor.sha384(request.getPassword());
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                return rs.next();
            }

        }catch (SQLException exception) {

        }finally {
            try {
                connection.close();
            }catch (SQLException e) {
                LOGGER.error("Failed to close database connection. Error message: {}, SQLState: {}, ErrorCode: {}",
                        e.getMessage(), e.getSQLState(), e.getErrorCode(), e);            }
        }
        return false;
    }
    public Integer getUserId(Request request) {
        connection = connectToDatabase();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_USR_ID)) {
            String login = request.getLogin();
            String password = Encryptor.sha384(request.getPassword());
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next())
                    return rs.getInt("user_id");
            }
        }catch (SQLException exception) {
            exception.printStackTrace();
        }finally {
            try {
                connection.close();
            }catch (SQLException e) {
                LOGGER.error("Failed to close database connection. Error message: {}, SQLState: {}, ErrorCode: {}",
                        e.getMessage(), e.getSQLState(), e.getErrorCode(), e);            }
        }
        return null;
    }


    public boolean insertNewUser(Request request) {
        connection = connectToDatabase();
        String login = request.getLogin();
        String password = Encryptor.sha384(request.getPassword());
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEW_USER)) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        }catch (SQLException exception) {

        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.error("Failed to close database connection. Error message: {}, SQLState: {}, ErrorCode: {}",
                        e.getMessage(), e.getSQLState(), e.getErrorCode(), e);            }
        }
        return false;
    }

    public Integer insertNewDragonCave(DragonCave cave) {
        connection = connectToDatabase();
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEW_CAVE, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, cave.getNumberOfTreasures());
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }

        }catch (SQLException exception) {
        }finally {
            try {
                connection.close();
            }catch (SQLException e) {
                LOGGER.error("Failed to close database connection. Error message: {}, SQLState: {}, ErrorCode: {}",
                        e.getMessage(), e.getSQLState(), e.getErrorCode(), e);            }
        }
        return null;
    }

    public Integer insertNewCoordinates(Coordinates coordinates) {
        connection = connectToDatabase();
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEW_COORDINATES, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, coordinates.getX());
            preparedStatement.setFloat(2, coordinates.getY());
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                    if (rs.next())
                        return rs.getInt(1);
                }
            }

        }catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            try {
                connection.close();
            }catch (SQLException e) {
                LOGGER.error("Failed to close database connection. Error message: {}, SQLState: {}, ErrorCode: {}",
                        e.getMessage(), e.getSQLState(), e.getErrorCode(), e);            }
        }
        return null;
    }

    public Integer addDragonToDataBase(Dragon dragon, int caveId, int coordinatesId, int userId) {
        connection = connectToDatabase();
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_DRAGON, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, dragon.getName());
            preparedStatement.setInt(2, coordinatesId);
            preparedStatement.setLong(3, dragon.getAge());
            preparedStatement.setString(4, dragon.getDescription());
            preparedStatement.setDouble(5, dragon.getWingspan());
            PGobject colorObject = new PGobject();
            colorObject.setType("color");
            colorObject.setValue(dragon.getColor().toString());
            preparedStatement.setObject(6, colorObject);
            preparedStatement.setInt(7, caveId);
            preparedStatement.setInt(8, userId);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            try {
                connection.close();
            }catch (SQLException e)  {
                LOGGER.error("Failed to close database connection. Error message: {}, SQLState: {}, ErrorCode: {}",
                        e.getMessage(), e.getSQLState(), e.getErrorCode(), e);            }
        }
        return null;
    }
    public boolean deleteAllByUserId(int userId) {
        connection = connectToDatabase();
        boolean isSuccess = false;
        try {
            connection.setAutoCommit(false);
            try (PreparedStatement deleteCoordinatesStmt = connection.prepareStatement(DELETE_COORDINATES_BY_USER_ID);
                 PreparedStatement deleteCavesStmt = connection.prepareStatement(DELETE_CAVES_BY_USER_ID);
                 PreparedStatement deleteDragonsStmt = connection.prepareStatement(DELETE_DRAGONS_BY_USER_ID)) {

                deleteDragonsStmt.setInt(1, userId);
                int rowsAffected = deleteDragonsStmt.executeUpdate();

                deleteCoordinatesStmt.setInt(1, userId);
                deleteCoordinatesStmt.executeUpdate();

                deleteCavesStmt.setInt(1, userId);
                deleteCavesStmt.executeUpdate();


                if (rowsAffected > 0) {
                    isSuccess = true;
                }

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            } finally {
                try {
                    connection.close();
                }catch (SQLException e) {
                    LOGGER.error("Failed to close database connection. Error message: {}, SQLState: {}, ErrorCode: {}",
                            e.getMessage(), e.getSQLState(), e.getErrorCode(), e);                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }
}