package com.example.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
/**
 * Web filter for login check
 * @author Jinkai Zhang
 */
@WebFilter(urlPatterns = "/*")
public class LoginCheckerFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest req = (HttpServletRequest) request;
        final String uri = req.getRequestURI();
        if (!uri.endsWith("register.jsp") && !uri.endsWith("login") && !uri.endsWith("index.jsp") && !uri.endsWith("signup")) {
            final Object personId = req.getSession().getAttribute("personId");
            if (personId == null) {
                ((HttpServletResponse) response).sendRedirect("index.jsp");
                return;
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
