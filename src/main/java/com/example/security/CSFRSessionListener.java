package com.example.security;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import java.util.UUID;
/**
 * csrf token
 * @author Jinkai Zhang
 */
@WebListener
public class CSFRSessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        se.getSession().setAttribute("csrfToken", UUID.randomUUID().toString());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {

    }
}
