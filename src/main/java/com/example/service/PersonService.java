package com.example.service;

import com.example.dao.ConnectionFactory;
import com.sample.Person;

import java.sql.*;
/**
 * Person Service
 * @author Jinkai Zhang
 */
public class PersonService {
    private BcryptService bcryptService = new BcryptService();

    public Person registerPerson(String name, String email, String password) {
        final Person person = new Person();
        try {
            ConnectionFactory.execute(connection -> {
                final String encodePassword;

                try (PreparedStatement preparedStatement = connection.prepareStatement("insert into person( name, email, password) value (?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
                    preparedStatement.setString(1, name);
                    preparedStatement.setString(2, email);
                    encodePassword = bcryptService.encodePassword(password);
                    preparedStatement.setString(3, encodePassword);
                    preparedStatement.executeUpdate();
                    try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                        if (resultSet.next()) {
                            person.setId(resultSet.getLong(1));
                        } else {
                            throw new RuntimeException("Please use Mysql 8.0 jdbc driver");
                        }
                    }

                }
                person.setEmail(email);
                person.setName(name);
                person.setPassword(encodePassword);

                return true;
            });
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
        return person;
    }

    public Person tryLogin(String email, String password) {
        final Person person = findPersonByEmail(email);
        if (person != null) {
            if (bcryptService.checkPasswordMatch(password, person.getPassword())) {
                return person;
            }
        }
        return null;
    }

    public Person findPersonByEmail(String email) {
        try {
            return ConnectionFactory.execute(connection -> {
                try (PreparedStatement preparedStatement = connection.prepareStatement("select id, name, email, password from person where email=?")) {
                    preparedStatement.setString(1, email);
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        if (resultSet.next()) {
                            final Person person = new Person();
                            person.setId(resultSet.getLong(1));
                            person.setName(resultSet.getString(2));
                            person.setEmail(resultSet.getString(3));
                            person.setPassword(resultSet.getString(4));
                            return person;
                        }
                    }
                }
                return null;
            });
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public Person findPersonById(Long id) {
        try {
            return ConnectionFactory.execute(connection -> {
                try (PreparedStatement preparedStatement = connection.prepareStatement("select id, name, email, password from person where id=?")) {
                    preparedStatement.setLong(1, id);
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        if (resultSet.next()) {
                            final Person person = new Person();
                            person.setId(resultSet.getLong(1));
                            person.setName(resultSet.getString(2));
                            person.setEmail(resultSet.getString(3));
                            person.setPassword(resultSet.getString(4));
                            return person;
                        }
                    }
                }
                return null;
            });
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public boolean updatePerson(long id, String email, String name, String password) {
        String sql = "update person\n" +
                "set email = " + email + " and name = " + name + " and password = " + password + "\n" +
                "where id = " + id;
        try (Connection connection = ConnectionFactory.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(sql);
            }
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }
}
