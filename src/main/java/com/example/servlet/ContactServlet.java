package com.example.servlet;

import com.example.service.ContactService;
import com.example.service.TelephoneService;
import com.sample.Contact;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

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
