package com.wildcodeschool.wildandwizard.repository;

import com.wildcodeschool.wildandwizard.entity.School;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SchoolRepository {

    private final static String DB_URL = "jdbc:mysql://localhost:3306/spring_jdbc_quest?serverTimezone=GMT";
    private final static String DB_USER = "h4rryp0tt3r";
    private final static String DB_PASSWORD = "Horcrux4life!";

    @GetMapping("/api/school")
    public List<School> findAll() {
        try (
                Connection connection = DriverManager.getConnection(
                        DB_URL, DB_USER, DB_PASSWORD
                );
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT * FROM school"
                );
                ResultSet resulSet = statement.executeQuery();
        ) {
            List<School> school = new ArrayList<School>();
            while (resulSet.next()) {
                Long id = resulSet.getLong("id");
                String name = resulSet.getString("name");
                Long capacity = resulSet.getLong("capacity");
                String country = resulSet.getString("country");
                school.add(new School(id, name, capacity, country));
            }
            return school;
        } catch (SQLException e) {
            // send HttpStatus 500 to the client if something goes wrong
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "", e
            );
        }
    }



    public School findById(Long id) {
        try {
            Connection connection = DriverManager.getConnection(
                    DB_URL, DB_USER, DB_PASSWORD
            );
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM school WHERE id = ?;"
            );
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                Long capacity = resultSet.getLong("capacity");
                String country = resultSet.getString("country");

                return new School(id, name, capacity, country);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<School> findByCountry(String country) {
        try {
            Connection connection = DriverManager.getConnection(
                    DB_URL, DB_USER, DB_PASSWORD
            );
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM school WHERE country = ?;"
            );
            statement.setString(1, country);
            ResultSet resultSet = statement.executeQuery();
            List<School> school = new ArrayList<School>();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                Long capacity = resultSet.getLong("capacity");
                 country = resultSet.getString("country");
                school.add(new School(id, name, capacity, country));
            }
            return school;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
