package com.example.servlet;

import com.example.service.PersonService;
import com.sample.Person;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
/**
 * web endpoint for login
 * @author Jinkai Zhang
 */
@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    private final PersonService personService = new PersonService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String email = req.getParameter("email");
        final String password = req.getParameter("password");
        final Person person = personService.tryLogin(email, password);
        if (person == null) {
            req.setAttribute("error", "Email and Password not match.");
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        } else {
            req.getSession().setAttribute("personId", person.getId());
            resp.sendRedirect("dashboard.jsp");
//            req.getRequestDispatcher("dashboard.jsp").forward(req, resp);
        }
    }
}
