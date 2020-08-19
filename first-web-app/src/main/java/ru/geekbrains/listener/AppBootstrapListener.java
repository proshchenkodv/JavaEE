package ru.geekbrains.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.geekbrains.persist.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebListener
public class AppBootstrapListener implements ServletContextListener {

    private static final Logger logger = LoggerFactory.getLogger(AppBootstrapListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("Initializing application");

        ServletContext sc = sce.getServletContext();
        String jdbcConnectionString = sc.getInitParameter("jdbcConnectionString");
        String username = sc.getInitParameter("username");
        String password = sc.getInitParameter("password");

        try {
            Connection conn = DriverManager.getConnection(jdbcConnectionString, username, password);
            sc.setAttribute("connection", conn);

            ProductRepository productRepository = new ProductRepository(conn);
            sc.setAttribute("productRepository", productRepository);

            if (productRepository.findAll().isEmpty()) {
                logger.info("No products in DB. Initializing.");

                productRepository.insert(new Product(-1L, "Apple Macbook pro 2015", "Apple profession laptop", new BigDecimal(3000)));
                productRepository.insert(new Product(-1L, "Apple Macbook air 2015", "Apple netbook", new BigDecimal(2000)));
                productRepository.insert(new Product(-1L, "Apple iPhone I", "Apple phone", new BigDecimal(100)));
                productRepository.insert(new Product(-1L, "Apple iPhone II", "Apple phone", new BigDecimal(200)));
                productRepository.insert(new Product(-1L, "Apple iPhone III", "Apple phone", new BigDecimal(300)));
                productRepository.insert(new Product(-1L, "Apple iPhone IV", "Apple phone", new BigDecimal(400)));
                productRepository.insert(new Product(-1L, "Apple iPhone V", "Apple phone", new BigDecimal(500)));
                productRepository.insert(new Product(-1L, "Apple iPhone VI", "Apple phone", new BigDecimal(600)));
                productRepository.insert(new Product(-1L, "Apple iPhone VII", "Apple phone", new BigDecimal(700)));

            }

            CategoryRepository categoryRepository=new CategoryRepository(conn);
            sc.setAttribute("categoryRepository",categoryRepository);

            if (categoryRepository.findAll().isEmpty()) {
                logger.info("No categories in DB. Initializing.");

                categoryRepository.insert(new Category(-1L, "Apple profession laptop", "Apple profession laptop description"));
                categoryRepository.insert(new Category(-1L, "Apple Macbook air 2015", "Apple netbook description"));
                categoryRepository.insert(new Category(-1L, "Apple tablet", "Apple tablet description"));
                categoryRepository.insert(new Category(-1L, "Apple phone", "Apple phone description"));

            }

            CompanyRepository companyRepository=new CompanyRepository(conn);
            sc.setAttribute("companyRepository",companyRepository);

            if (companyRepository.findAll().isEmpty()) {
                logger.info("No companies in DB. Initializing.");

                companyRepository.insert(new Company(-1L, "Out Company Name", "Out Company adress"));

            }

        } catch (SQLException ex) {
            logger.error("", ex);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext sc = sce.getServletContext();
        Connection conn = (Connection) sc.getAttribute("connection");
        try {
            if (conn != null && conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException ex) {
            logger.error("", ex);
        }
    }
}
