package com.monocle.util;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;

@Path("/store")
public class Store {


        @POST
	@Path("/put/{kind}/{key}/{name}/{value}")
        public Response put(
                        @FormParam("kind") String kind,
                        @FormParam("key") String key,
                        @FormParam("name") String name,
                        @FormParam("value") String value 
                        ) {
                System.out.println ("++++++++++++++++++++++++++++++++++++++++++++");
                System.out.println (kind +","+key+","+name+","+value);

		Entity todoListItem = new Entity( kind, key);
		todoListItem .setProperty( name, value);
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		datastore.put(todoListItem );
		return Response.status(200).entity("OK").build();
	}

	@GET
	@Path("/get/{kind}/{key}/{sortColumn}/{rows}")
	public Response get( @PathParam("kind") String kind, @PathParam("key") String key ,@PathParam("sortColumn") String sortColumn, @PathParam("rows") int rows) {
		Key keykey = KeyFactory.createKey(kind,key);
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query query = new Query( kind, keykey).addSort(sortColumn,Query.SortDirection.DESCENDING);
		List<Entity> items = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(rows));
		StringBuilder s  = new StringBuilder();
		for(Entity e : items) {
			s.append( e.getProperty(sortColumn) );
		}
		System.out.println( "RESPONSE:"+ s.toString() );
		return Response.status(200).entity(s.toString()).build();
	}
}
