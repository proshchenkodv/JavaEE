package ru.geekbrains.persist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@ApplicationScoped
@Named
public class CategoryRepository {

    private static final Logger logger = LoggerFactory.getLogger(CategoryRepository.class);

    @Inject
    private ServletContext context;

    private Connection conn;

    public CategoryRepository() {
    }

    @PostConstruct
    public void init() throws SQLException {
        conn = (Connection) context.getAttribute("connection");
        createTableIfNotExists(conn);
        if (this.findAll().isEmpty()) {
            logger.info("No categories in DB. Initializing.");

            insert(new Category(-1L, "Apple profession laptop", "Apple profession laptop description"));
           insert(new Category(-1L, "Apple Macbook air 2015", "Apple netbook description"));
            insert(new Category(-1L, "Apple tablet", "Apple tablet description"));
            insert(new Category(-1L, "Apple phone", "Apple phone description"));

        }
    }

    public void insert(Category category) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "insert into categories(name, description) values (?, ?);")) {
            stmt.setString(1, category.getName());
            stmt.setString(2, category.getDescription());
            stmt.execute();
        }
    }

    public void update(Category category) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "update categories set name = ?, description = ? where id = ?;")) {
            stmt.setString(1, category.getName());
            stmt.setString(2, category.getDescription());
            stmt.setLong(3, category.getId());
            stmt.execute();
        }
    }

    public void delete(long id) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "delete from categories where id = ?;")) {
            stmt.setLong(1, id);
            stmt.execute();
        }
    }

    public Optional<Category> findById(long id) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "select id, name, description from categories where id = ?")) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(new Category(rs.getLong(1), rs.getString(2), rs.getString(3)));
            }
        }
        return Optional.empty();
    }

    public List<Category> findAll() throws SQLException {
        List<Category> res = new ArrayList<>();
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("select id, name, description from categories");

            while (rs.next()) {
                res.add(new Category(rs.getLong(1), rs.getString(2), rs.getString(3)));
            }
        }
        return res;
    }

    private void createTableIfNotExists(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("create table if not exists categories (\n" +
                    "    id int auto_increment primary key,\n" +
                    "    name varchar(25),\n" +
                    "    description varchar(255)" +
                    ");");
        }
    }
}
