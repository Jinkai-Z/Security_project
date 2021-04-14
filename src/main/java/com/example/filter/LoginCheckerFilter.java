package com.example.filter;

import com.example.security.SecureToken;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;
import javax.servlet.http.Cookie;
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
            
            boolean authorized = getToken(req)
                    .flatMap(this::authenticateToken)
                    .flatMap(token -> authorizeToken(token, intendedAudience(uri)))
                    .isPresent();
            
            if (!authorized) {
                ((HttpServletResponse) response).sendRedirect("index.jsp");
            }
        }
        chain.doFilter(request, response);
    }
    
    private String intendedAudience(String uri) {
        if (uri.equals("/admin")) {
            return "admin";
        } else {
            return "users";
        }
    }

    public Optional<String> getToken(HttpServletRequest request) {
        Cookie cookies[] = request.getCookies();
        if (cookies != null) {
            for(Cookie ck : cookies) {
                if (ck.getName().equals("auth")) {
                    return Optional.of(ck.getValue());
                }
            }
        }
        return Optional.empty();
    }
    
    private Optional<SecureToken> authenticateToken(String token) {
        try {
            SecureToken auth = SecureToken.SecureTokenBuilder.unpack(token);
            if (auth.isValid()) {
                return Optional.of(auth);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        return Optional.empty();
    }
    
    private Optional<SecureToken> authorizeToken(SecureToken token, String audience) {
        if (token.getAudience().contains(audience)) {
            return Optional.of(token);
        }
        return Optional.empty();
    }

    @Override
    public void destroy() {

    }
    
    
}
