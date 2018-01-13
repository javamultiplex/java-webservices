package com.javamultiplex.rest.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import com.javamultiplex.messanger.model.Message;

public class RestClientGET {

	
	public static void main(String[] args) {
		
		Client client=ClientBuilder.newClient();
		/*String response = client
							.target("http://localhost:8050/advance-jax-rs-01/webapi/messages/2")
							.request()
							.get(String.class);*/
		WebTarget baseTarget = client.target("http://localhost:8050/advance-jax-rs-01/webapi");
		WebTarget messageTarget = baseTarget.path("messages");
		WebTarget singleMessageTarget = messageTarget.path("{messageId}");
		
		Message message = singleMessageTarget.resolveTemplate("messageId", "2").request().get(Message.class);
		
		//Message message = response.readEntity(Message.class);
		//System.out.println(message.getMessage());
		System.out.println(message.getMessage());
	}
	
}
