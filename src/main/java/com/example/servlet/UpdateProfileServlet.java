package com.example.servlet;

import com.example.service.BcryptService;
import com.example.service.PersonService;
import com.sample.Person;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
/**
 * web endpoint for update person
 * @author Jinkai Zhang
 */
@WebServlet(urlPatterns = "/update_person")
public class UpdateProfileServlet extends HttpServlet {
    PersonService personService = new PersonService();
    BcryptService bcryptService = new BcryptService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            final long personId = (long) req.getSession().getAttribute("personId");
            final String name = req.getParameter("name");
            final String email = req.getParameter("email");
            final String password = req.getParameter("password");
            final Person person = personService.findPersonById(personId);
            if (person != null) {
                if (name != null) {
                    person.setName(name);
                }
                if (email != null) {
                    person.setEmail(email);
                }
                if (password != null) {
                    person.setPassword(bcryptService.encodePassword(password));
                }
                personService.updatePerson(person.getId(),
                        person.getEmail(), person.getName(), password);
                req.setAttribute("error", "update success");
            } else {
                req.setAttribute("error", "update failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "update failed");
        }
        req.getRequestDispatcher("profile.jsp").forward(req, resp);
    }
}
