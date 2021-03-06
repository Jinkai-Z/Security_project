package com.example.servlet;

import com.example.service.ContactService;
import com.example.service.TelephoneService;
import com.sample.Contact;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * web endpoint for add contact
 * @author Jinkai Zhang
 */
@WebServlet(urlPatterns = {"/contact_add"})
public class ContactServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            final long personId = (long) req.getSession().getAttribute("personId");
            final String contactName = req.getParameter("contactName");
            final String telephone = req.getParameter("telephone");

            final ContactService contactService = new ContactService();
            final TelephoneService telephoneService = new TelephoneService();
            final Contact contact = contactService.addContact(personId, contactName);
            telephoneService.addTelephone(contact.getId(), telephone);
            req.setAttribute("error", "add success.");
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "add contact fail.");
        }
        req.getRequestDispatcher("dashboard.jsp").forward(req, resp);
    }
}
