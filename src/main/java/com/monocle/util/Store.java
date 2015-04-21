package com.monocle.util;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;

/**
 * @author mooser
 * Single property entities, all belonging to entity "Entity"
 * we hide this from the user, presenting a get(name), put(name,value) interface
 * over HTTP
 */
@Path("/store")
public class Store {

	@GET
	@Path("/save/{property}/{value}")
	public Response put(
			@PathParam("property") String name,
			@PathParam("value") String value 
			) {
		put("alpha","beta","daentity",name,value);
		return Response.status(200).entity("OK").build();
	}

	@GET
	@Path("/get/{property}")
	public Response get(
			@PathParam("property") String property ) 
	{
		List<Entity> items = get("alpha", "beta", "daentity", property, 5) ;
		StringBuilder s  = new StringBuilder();
		for(Entity e : items) {
			s.append( e.getProperty(property) );
		}
		System.out.println( "RESPONSE:"+ s.toString() );
		return Response.status(200).entity(s.toString()).build();
	}
	
	@GET
	@Path("/saveq/{keykind}/{keyname}/{entity}/{property}/{value}")
	public Response putAll(
			@PathParam("keykind") String keykind,
			@PathParam("keyname") String keyname,
			@PathParam("entity") String entity,
			@PathParam("property") String name,
			@PathParam("value") String value 
			) {
		put( keykind, keyname, entity,name,value);
		return Response.status(200).entity("OK").build();
	}

	@GET
	@Path("/getq/{keykind}/{keyname}/{entity}/{property}")
	public Response getAll(
			@PathParam("keykind") String keykind,
			@PathParam("keyname") String keyname,
			@PathParam("entity") String entity,
			@PathParam("property") String property ) 
	{
		List<Entity> items = get(keykind, keyname, entity, property, 5) ;
		StringBuilder s  = new StringBuilder();
		for(Entity e : items) {
			s.append( e.getProperty(property) );
		}
		System.out.println( "RESPONSE:"+ s.toString() );
		return Response.status(200).entity(s.toString()).build();
	}

	private void put( String keykind, String keyname, String entity, String property, String value) {
		Key key = KeyFactory.createKey(keykind, keyname);
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Entity e = new Entity(entity, key);
		e.setProperty(property,value);
		datastore.put(e);
	}

	private List<Entity> get(String keykind, String keyname, String entity, String sortProperty, int rows) {
		Key key = KeyFactory.createKey(keykind, keyname);
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query query = new Query(entity, key ).addSort(sortProperty,Query.SortDirection.DESCENDING);
		List<Entity> entities = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(rows));
		return entities;
	}
}
