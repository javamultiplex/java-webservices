package com.javamultiplex.rest.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class InvocationDemo {

	public static void main(String[] args) {
		
		
		Invocation invocation = prepareRequestForMessagesByYear(2015);
		Response response = invocation.invoke();
		System.out.println(response.getStatus());
		
	}

	private static Invocation prepareRequestForMessagesByYear(int year) {

		Client client=ClientBuilder.newClient();
		return client.target("http://localhost:8050/advance-jax-rs-01/webapi")
				                     .path("messages")
				                     .queryParam("year", year)
				                     .request(MediaType.APPLICATION_JSON)
				                     .buildGet();
	}
	
}
