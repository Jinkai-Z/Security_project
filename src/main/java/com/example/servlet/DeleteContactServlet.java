package com.example.servlet;

import com.example.service.ContactService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
