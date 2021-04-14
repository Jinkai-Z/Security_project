package com.example.servlet;

import com.example.service.ContactService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
/**
 * web endpoint for delete contact
 * @author Jinkai Zhang
 */
@WebServlet(urlPatterns = "/contact_del")
public class DeleteContactServlet extends HttpServlet {
    ContactService contactService = new ContactService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            final String contactId = req.getParameter("contactId");
            contactService.deleteContact(Long.parseLong(contactId));
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(404);
        }
    }
}
