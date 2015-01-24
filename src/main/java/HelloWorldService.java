package com.monocle.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
 
@Path("/notebooks")
public class HelloWorldService {
 
	@GET
	@Path("/new/{name}")
	public Response f1(@PathParam("name") String msg) {
		String output = "create new notebook with name :"+msg";
		return Response.status(200).entity(output).build();
	}

	@GET
	@Path("/update")
	public Response f2() {
		String output = "update the contents of the notebook with what's on the screen";
		return Response.status(200).entity(output).build();
	}

	@GET
	@Path("/delete")
	public Response f3() {
		String output = "delete a specific notebook ";
		return Response.status(200).entity(output).build();
	}

}
