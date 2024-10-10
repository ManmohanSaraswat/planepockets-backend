package com.planepockets.proton.configuration;

import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException, MalformedJwtException {
		final String expired = (String) request.getAttribute("expired");
		final String malformed = (String) request.getAttribute("malformed");
		if (expired != null) {
			response.sendError(401, expired);
		} else if (malformed != null) {
			response.sendError(498, malformed);
		}
	}
}
