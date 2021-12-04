package ru.job4j.grabber;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

public class PsqlStore implements Store, AutoCloseable {

    private Connection cnn;

    public PsqlStore(Properties cfg) {
        String sql = String.format(
                "create table if not exists post(%s, %s, %s, %s, %s);",
                "id serial primary key",
                "name text unique",
                "text name",
                "link text",
                "created timestamp"
        );
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
            cnn = DriverManager.getConnection(cfg.getProperty("url"), cfg.getProperty("username"), cfg.getProperty("password"));
            executeQuery(sql);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void executeQuery(String sql) throws SQLException {
        try (Statement statement = cnn.createStatement()) {
            statement.execute(sql);
        }
    }

    @Override
    public void save(Post post) {

    }

    @Override
    public List<Post> getAll() {
        return null;
    }

    @Override
    public Post findById(int id) {
        return null;
    }

    @Override
    public void close() throws Exception {
        if (cnn != null) {
            cnn.close();
        }
    }
}
