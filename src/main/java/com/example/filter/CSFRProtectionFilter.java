package com.example.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class CSFRProtectionFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        if ("POST".equalsIgnoreCase(httpServletRequest.getMethod())
                || ((HttpServletRequest) request).getRequestURI().endsWith("/logout")) {
            final String csrfToken = request.getParameter("csrfToken");
            if (!httpServletRequest.getSession().getAttribute("csrfToken").equals(csrfToken)) {
                response.getWriter().write("Illegal Access. csrfToken error.");
            } else {
                chain.doFilter(request, response);
            }
        } else {
            chain.doFilter(request, response);
        }
    }
}
