package ru.geekbrains;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.geekbrains.persist.Category;
import ru.geekbrains.persist.CategoryRepository;
import ru.geekbrains.persist.Company;
import ru.geekbrains.persist.CompanyRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

@WebServlet(name = "AboutControllerServlet", urlPatterns = {"/about/*"})
public class AboutControllerServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(AboutControllerServlet.class);

    private CompanyRepository companyRepository;

    @Override
    public void init() throws ServletException {
        companyRepository = (CompanyRepository) getServletContext().getAttribute("companyRepository");
        if (companyRepository == null) {
            throw new ServletException("Company repository not initialized");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Index company page");

        if (req.getServletPath().equals("/about")) {
            if (req.getPathInfo() == null) {
                try {
                    req.setAttribute("company", companyRepository.findAll());
                    req.setAttribute("activePage", "about");
                    getServletContext().getRequestDispatcher("/WEB-INF/about.jsp").forward(req, resp);
                } catch (SQLException ex) {
                    throw new IllegalStateException(ex);
                }
            } else if (req.getPathInfo().equals("/edit")) {
                String id = req.getParameter("id");
                try {
                    Optional<Company> opt = companyRepository.findById(Long.parseLong(id));
                    if (opt.isPresent()) {
                        req.setAttribute("company", opt.get());
                    } else {
                        resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                        return;
                    }
                } catch (SQLException ex) {
                    throw new IllegalStateException(ex);
                }
                getServletContext().getRequestDispatcher("/WEB-INF/about.jsp").forward(req, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getPathInfo().equals("/upsert")) {
            try {
                String strId = req.getParameter("id");
                companyRepository.update(new Company(Long.parseLong(strId),
                        req.getParameter("name"),
                        req.getParameter("adress")
                ));
                resp.sendRedirect(req.getContextPath() + req.getServletPath());
            } catch (SQLException ex) {
                throw new IllegalStateException(ex);
            }
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
