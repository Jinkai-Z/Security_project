package com.example.servlet;

import com.example.service.PersonService;
import com.sample.Person;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Objects;

@WebServlet(urlPatterns = "/signup")
public class SignUpServlet extends HttpServlet {
    private PersonService personService = new PersonService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String email = req.getParameter("email");
        final String name = req.getParameter("name");
        final String password = req.getParameter("password");
        final String password1 = req.getParameter("password1");
        if (!Objects.equals(password1, password)) {
            error(req, "Two password not match");
        } else {
            if (email == null || email.isBlank()) {
                error(req, "Email should not empty");
            } else {
                if (name == null || name.isBlank()) {
                    error(req, "Name should not empty");
                } else {
                    if (password == null || password.isBlank()) {
                        error(req, "Password should not null");
                    } else {
                        final Person person = personService.registerPerson(name, email, password);
                        if (person == null) {
                            error(req, "Sign up fail. Please check db connection. ");
                        } else {
                            error(req, "Sign up successful.");
                        }
                    }
                }
            }

        }
        req.getRequestDispatcher("register.jsp").forward(req, resp);
    }

    private void error(HttpServletRequest req, String msg) {
        req.setAttribute("error", msg);
    }
}
