package com.planepockets.proton.util;

import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
	public static final String SECRET_KEY = "PlanePocketsBackedApplicationForPlanePocketsProjectCreatedInSpringBootWithMySql12asjkaalwn1267snkq12asknas92";
	public static final int TOKEN_VALIDITY = 3600 * 24;
	private static final Logger log = LoggerFactory.getLogger(JwtUtil.class);

	public String getUserNameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimResolver.apply(claims);
	}

	public Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();
	}

	public boolean validateToken(String token, UserDetails userDetails, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();
			String userName = claims.getSubject();
			if (userName.equals(userDetails.getUsername()))
				return true;
			return false;
		} catch (SignatureException ex) {
			log.info("Invalid Jwt Signature");
		} catch (MalformedJwtException ex) {
			request.setAttribute("malformed", ex.getMessage());
			log.info("Invalid jwt token");
		} catch (ExpiredJwtException ex) {
			log.info("Expired jwt token");
			request.setAttribute("expired", ex.getMessage());
		} catch (IllegalArgumentException ex) {
			log.info("Jwt Claims string is empty");
		}
		return false;
	}

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY * 1000))
				.signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();
	}

}