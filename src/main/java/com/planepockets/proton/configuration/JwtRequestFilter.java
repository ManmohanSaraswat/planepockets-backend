package com.planepockets.proton.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.planepockets.pojo.SimpleResponse;
import com.planepockets.proton.service.JwtService;
import com.planepockets.proton.service.UserAuthenticationService;
import com.planepockets.proton.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserAuthenticationService userAuthenticationService;

    private static final Logger log = LoggerFactory.getLogger(JwtRequestFilter.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final Map<String, Boolean> sessionCache = new ConcurrentHashMap<>();
    private final long CACHE_EXPIRATION_MS = 300000;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String header = request.getHeader("Authorization");
        String jwtToken = null;
        String userName = null;
        if (header != null && header.startsWith("Bearer ")) {
            jwtToken = header.substring(7);
            try {
                userName = jwtUtil.getUserNameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                log.info("got {} while checking for user", e.getLocalizedMessage());
                sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Unable to get token" + e.getLocalizedMessage());
                return;
            } catch (ExpiredJwtException e) {
                log.info("Provided token is expired! Please login again");
                sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Provided token is expired! Please login again");
                return;
            } catch (MalformedJwtException e) {
                log.info("Provided token is invalid! Please login again");
                sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Provided token is invalid! Please login again");
                return;
            }
        } else {
            log.info("Authentication token is not present in headers");
        }

        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = jwtService.loadUserByUsername(userName);
            if (jwtUtil.validateToken(jwtToken, userDetails, request, response)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        if (userName != null) {
            Boolean res = checkSessionCache(userName, header);
            if (!res) {
                log.info("User is not logged in!");
                sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "User is not logged in!");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private void sendErrorResponse(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        SimpleResponse exception = new SimpleResponse(message);
        try (PrintWriter writer = response.getWriter()) {
            objectMapper.writeValue(writer, exception);
        }
    }

    private void scheduleCacheEntryRemoval(String cacheKey) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                sessionCache.remove(cacheKey);
            }
        }, CACHE_EXPIRATION_MS);
    }

    private Boolean checkSessionCache(String userName, String header) {
        String cacheKey = userName + ":" + header;
        Boolean cachedResult = sessionCache.get(cacheKey);

        if (cachedResult == null) {
            // If not in cache, check the database
            Boolean res = userAuthenticationService.checkSessionForUser(userName, header);
            if (res) {
                sessionCache.put(cacheKey, true);
                scheduleCacheEntryRemoval(cacheKey);
            }
            return res;
        }

        // Double-check with the database if cached result is true
        if (cachedResult) {
            Boolean res = userAuthenticationService.checkSessionForUser(userName, header);
            if (!res) {
                sessionCache.remove(cacheKey);
                return false;
            }
        }

        return cachedResult;
    }

    public void invalidateSession(String userName, String header) {
        String cacheKey = userName + ":" + header;
        sessionCache.remove(cacheKey);
    }

}
