package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() throws SQLException, ClassNotFoundException {

    }

    public void createUsersTable()  {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users" +
                    "(id mediumint not null auto_increment," + // 1
                    " name VARCHAR(50), " + //2
                    "lastname VARCHAR(50), " + //3
                    "age tinyint, " +//4
                    "PRIMARY KEY (id))");
            System.out.println("Таблица создана");
        } catch (SQLException e) {
            e.printStackTrace();;
        }
    }

    public void dropUsersTable()  {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("Drop table if exists test1.users");
            System.out.println("Таблица удалена");
        } catch (SQLException e) {
            e.printStackTrace();;
        }
    }

    public void saveUser(String name, String lastName, byte age)  {
        String sql = "INSERT INTO users (name, lastname, age) VALUES(?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();;
        }
    }

    public void removeUserById(long id) {
        try (Statement statement = connection.createStatement()) {
            String sql = "DELETE FROM users where id";
            statement.executeUpdate(sql);
            System.out.println("User удален");
        } catch (SQLException e) {
            e.printStackTrace();;
        }
    }

    public List<User> getAllUsers() {
        List<User> allUser = new ArrayList<>();
        String sql = "SELECT id, name, lastName, age from users";

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                allUser.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();;
        }
        return allUser;
    }

    public void cleanUsersTable() {
        String sql = "DELETE FROM users";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица очищена");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Не удалось очистить");

        }
    }
}
