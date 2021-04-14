package com.example.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import javax.servlet.http.Cookie;
/**
 * web endpoint for logout
 * @author Jinkai Zhang
 */
@WebServlet(urlPatterns = "/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("personId", null);
        req.getSession().invalidate();
        
        for (Cookie ck : req.getCookies()) {
            ck.setMaxAge(0);
            ck.setValue("");
            resp.addCookie(ck);
        }
        resp.sendRedirect("index.jsp");
    }
}
