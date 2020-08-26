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
public class CompanyRepository {

    private static final Logger logger = LoggerFactory.getLogger(CategoryRepository.class);

    @Inject
    private ServletContext context;

    private Connection conn;

    public CompanyRepository() {

    }

    @PostConstruct
    public void init() throws SQLException {
        conn = (Connection) context.getAttribute("connection");
        createTableIfNotExists(conn);
        if (this.findAll().isEmpty()) {
            logger.info("No companies in DB. Initializing.");

            insert(new Company(-1L, "Out Company Name", "Out Company address"));

        }
    }

    public void insert(Company company) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "insert into companies(name, adress) values (?, ?);")) {
            stmt.setString(1, company.getName());
            stmt.setString(2, company.getAdress());
            stmt.execute();
        }
    }

    public void update(Company company) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "update companies set name = ?, adress = ? where id = ?;")) {
            stmt.setString(1, company.getName());
            stmt.setString(2, company.getAdress());
            stmt.setLong(3, company.getId());
            stmt.execute();
        }
    }

    public void delete(long id) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "delete from copmanies where id = ?;")) {
            stmt.setLong(1, id);
            stmt.execute();
        }
    }

    public Optional<Company> findById(long id) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "select id, name, adress from companies where id = ?")) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(new Company(rs.getLong(1), rs.getString(2), rs.getString(3)));
            }
        }
        return Optional.empty();
    }

    public List<Company> findAll() throws SQLException {
        List<Company> res = new ArrayList<>();
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("select id, name, adress from companies");

            while (rs.next()) {
                res.add(new Company(rs.getLong(1), rs.getString(2), rs.getString(3)));
            }
        }
        return res;
    }

    private void createTableIfNotExists(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("create table if not exists companies (\n" +
                    "    id int auto_increment primary key,\n" +
                    "    name varchar(25),\n" +
                    "    adress varchar(255)" +
                    ");");
        }
    }
}
