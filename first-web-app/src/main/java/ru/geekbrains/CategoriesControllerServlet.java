package ru.geekbrains;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.geekbrains.persist.Category;
import ru.geekbrains.persist.CategoryRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

@WebServlet(name = "CategoriesControllerServlet", urlPatterns = {"/categories/*"})
public class CategoriesControllerServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(CategoriesControllerServlet.class);

    private CategoryRepository categoryRepository;

    @Override
    public void init() throws ServletException {
        categoryRepository = (CategoryRepository) getServletContext().getAttribute("categoryRepository");
        if (categoryRepository == null) {
            throw new ServletException("Category repository not initialized");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Index categories page");

        if (req.getServletPath().equals("/categories")) {
            if (req.getPathInfo() == null) {
                try {
                    req.setAttribute("categories", categoryRepository.findAll());
                    req.setAttribute("activePage", "categories");
                    getServletContext().getRequestDispatcher("/WEB-INF/categories.jsp").forward(req, resp);
                } catch (SQLException ex) {
                    throw new IllegalStateException(ex);
                }
            } else if (req.getPathInfo().equals("/new")) {
                req.setAttribute("category", new Category());
                getServletContext().getRequestDispatcher("/WEB-INF/category.jsp").forward(req, resp);
            } else if (req.getPathInfo().equals("/edit")) {
                String id = req.getParameter("id");
                try {
                    Optional<Category> opt = categoryRepository.findById(Long.parseLong(id));
                    if (opt.isPresent()) {
                        req.setAttribute("category", opt.get());
                    } else {
                        resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                        return;
                    }
                } catch (SQLException ex) {
                    throw new IllegalStateException(ex);
                }
                getServletContext().getRequestDispatcher("/WEB-INF/category.jsp").forward(req, resp);
            }
            else if(req.getPathInfo().equals("/delete")){
                String id = req.getParameter("id");
                try {
                    Optional<Category> opt = categoryRepository.findById(Long.parseLong(id));
                    if (opt.isPresent()) {
                        categoryRepository.delete(Long.parseLong(id));
                    } else {
                        resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                        return;
                    }
                } catch (SQLException ex) {
                    throw new IllegalStateException(ex);
                }
                resp.sendRedirect(req.getContextPath()+req.getServletPath());
            }
            else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getPathInfo().equals("/upsert")) {
            try {
                String strId = req.getParameter("id");
                if (strId.isEmpty()) {
                    categoryRepository.insert(new Category(-1L,
                            req.getParameter("name"),
                            req.getParameter("description")));
                } else {

                    categoryRepository.update(new Category(Long.parseLong(strId),
                            req.getParameter("name"),
                            req.getParameter("description")
                    ));
                }
                resp.sendRedirect(req.getContextPath()+req.getServletPath());
            } catch (SQLException ex) {
                throw new IllegalStateException(ex);
            }
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}

