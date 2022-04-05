package com.appsdeveloperblog.app.ws.security;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.appsdeveloperblog.app.ws.shared.dto.UserDto;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;

public class AuthorizationFilter extends BasicAuthenticationFilter {

	public AuthorizationFilter(AuthenticationManager authManager) {
		super(authManager);

	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		String header = req.getHeader(SecurityConstants.HEADER_STRING);

		if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
			chain.doFilter(req, res);
			return;
		}

		UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(req, res);

	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(SecurityConstants.HEADER_STRING);

		if (token != null) {

			token = token.replace(SecurityConstants.getTokenSecret(), "");

//  <<<    JWT Jackson error "Illegal character...  >>>>
//			String user = Jwts.parser()
//					.setSigningKey(SecurityConstants.getTokenSecret())
//					.parseClaimsJws(token)
//					.getBody().getSubject();
			
			String user = token;

			if (user != null) {
				return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
			}
			return null;
		}

		return null;
	}


}
