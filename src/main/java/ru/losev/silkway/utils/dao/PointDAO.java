package ru.losev.silkway.utils.dao;

import com.google.maps.model.LatLng;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class PointDAO {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String user;

    @Value("${spring.datasource.password}")
    private String password;

    private AtomicLong id = new AtomicLong(0);

    public void insert(List<LatLng> points, String userId) {
        StringBuilder builder = new StringBuilder("INSERT INTO point (id, lat, lng, user_id) VALUES ");

        for (LatLng point : points) {
            builder.append("(").append(id.getAndIncrement()).append(",").append(point.lat).append(",").append(point.lng).append(",").append(userId).append("),");
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.append(";");

        String query = builder.toString();

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Long userId) {
        String query = "DELETE FROM point WHERE user_id = " + userId + ";";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
