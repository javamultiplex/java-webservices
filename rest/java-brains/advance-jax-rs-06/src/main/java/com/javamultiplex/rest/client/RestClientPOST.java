package com.javamultiplex.rest.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import com.javamultiplex.messanger.model.Message;

public class RestClientPOST {

	
	public static void main(String[] args) {
		
		Client client=ClientBuilder.newClient();
		WebTarget baseTarget = client.target("http://localhost:8050/advance-jax-rs-01/webapi");
		WebTarget messageTarget = baseTarget.path("messages");
		Message message=new Message(6, "Hello this is Rohit", "Rohit Agarwal");
		Response response = messageTarget.request().post(Entity.json(message));
		Message createdMessage = response.readEntity(Message.class);
		System.out.println(createdMessage.getMessage());
		
	}
	
}
