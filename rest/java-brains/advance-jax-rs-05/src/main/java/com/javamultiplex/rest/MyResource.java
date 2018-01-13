package com.javamultiplex.rest;

import java.util.Calendar;
import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("test")
public class MyResource {

	@GET
	@Produces("text/shortdate")
	public Date test(){
		return Calendar.getInstance().getTime();
	}
	
}
