package com.example.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
/**
 * Web filter for csrf protection
 * @author Jinkai Zhang
 */
@WebFilter(urlPatterns = "/*")
public class CSFRProtectionFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

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

    @Override
    public void destroy() {

    }
}
