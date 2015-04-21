package com.monocle.util;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;


public class StoreWrapper {


	public void put( String keykind, String keyname, String entity, String property, String value) {
		Key key = KeyFactory.createKey(keykind, keyname);
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Entity e = new Entity(entity, key);
		e.setProperty(property,value);
		datastore.put(e);
	}


	public List<Entity> get(String keykind, String keyname, String entity, String sortProperty, int rows) {
		Key key = KeyFactory.createKey(keykind, keyname);
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query query = new Query(entity, key ).addSort(sortProperty,Query.SortDirection.DESCENDING);
		List<Entity> entities = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(rows));
		return entities;
	}
}
