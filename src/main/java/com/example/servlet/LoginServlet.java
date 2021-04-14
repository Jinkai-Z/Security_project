package com.example.servlet;

import com.example.security.SecureToken;
import com.example.service.PersonService;
import com.sample.Person;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import javax.servlet.http.Cookie;
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
            try {
                var token = new SecureToken.SecureTokenBuilder("crispy", Long.toString(person.getId()), "users");
                String auth = token.build().toString();

                resp.addCookie(new Cookie("auth", auth));
                req.getSession().setAttribute("personId", person.getId());
                resp.sendRedirect("dashboard.jsp");
            } catch (Exception e) {
                req.setAttribute("error", "Authentication error.");
                req.getRequestDispatcher("index.jsp").forward(req, resp);
            }
//            req.getRequestDispatcher("dashboard.jsp").forward(req, resp);
        }
    }
}
