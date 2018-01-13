package com.javamultiplex.rest;

import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.internal.util.Base64;

@Provider
public class SecurityFilter implements ContainerRequestFilter {

	private static final String AUTHORIZATION_HEADER_KEY = "Authorization";
	private static final String AUTHORIZATION_HEADER_PREFIX = "Basic ";
	private static final String SECURED_URI_PREFIX = "secured";

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {

		if (requestContext.getUriInfo().getPath().contains(SECURED_URI_PREFIX)) {

			List<String> list = requestContext.getHeaders().get(AUTHORIZATION_HEADER_KEY);
			if (list != null && list.size() > 0) {

				String authToken = list.get(0);
				authToken = authToken.replaceFirst(AUTHORIZATION_HEADER_PREFIX, "");
				String decodedtring = Base64.decodeAsString(authToken);
				StringTokenizer tokenizer = new StringTokenizer(decodedtring, ":");
				String userName = tokenizer.nextToken();
				String password = tokenizer.nextToken();
				if (userName.equals("user") && password.equals("password")) {
					return;
				}
			}

			Response unauthorizedStatus = Response.status(Response.Status.UNAUTHORIZED)
					.entity("User can't access the resource.").build();
			requestContext.abortWith(unauthorizedStatus);
		}
	}

}
