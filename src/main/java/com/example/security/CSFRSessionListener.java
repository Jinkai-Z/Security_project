package com.example.security;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

import java.util.UUID;
/**
 * csrf token
 * @author Jinkai Zhang
 */
@WebListener
public class CSFRSessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSessionListener.super.sessionCreated(se);
        se.getSession().setAttribute("csrfToken", UUID.randomUUID().toString());
    }
}
