package com.example.filter;

import com.example.security.SecureToken;
import com.example.security.TokenSignatureException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Optional;
import javax.servlet.http.Cookie;
/**
 * Web filter for login check
 * @author Jinkai Zhang
 * @author Justin Heinrichs
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
            
            Optional<String> auth = getAuth(req);
            if (auth.isPresent()) {
                Optional<SecureToken> token = getAuthenticatedToken(auth.get());
                
                if (!token.isPresent() || !isAuthorized(token.get(), intendedAudience(uri))) {
                     ((HttpServletResponse) response).sendRedirect("index.jsp");
                }
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

    public Optional<String> getAuth(HttpServletRequest request) {
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
    
    private Optional<SecureToken> getAuthenticatedToken(String token) throws ServletException {
        try {
            SecureToken auth = SecureToken.SecureTokenBuilder.unpack(token);
            if (auth.validateSignature()) {
                return Optional.of(auth);
            }
            return Optional.empty();
        } catch (GeneralSecurityException | TokenSignatureException e) {
            throw new ServletException(e.getMessage());
        }
    }
    
    private boolean isAuthorized(SecureToken token, String audience) {
        if (token.getAudience().contains(audience)) {
            return true;
        }
        return false;
    }

    @Override
    public void destroy() {

    }
    
    
}
