
import java.io.IOException;
import java.util.Date;
import java.util.List;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;

public class StoreServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
	System.out.println( " crowdsourced intelligence" );
		this.getServletContext().log( "[xxxxx] something came " + req.toString() );
		doPost(req,resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {


		Key key = getKey();
                DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		if( req.getQueryString().equals("put")) {
			String property = req. getParameter("name");
			String value = req. getParameter("value");
			this.getServletContext().log( "[xxxxx] " + property + ","+ value + " asked to be saved:" );
                	Entity entity = new Entity("entityname", key);
                	entity.setProperty(property,value);
                	datastore.put(entity);
		}
		else if( req.getQueryString().equals("get")) {
		
			Query query = new Query("entityname", key ).addSort("gang",Query.SortDirection.DESCENDING);
			List<Entity> entities = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(5));
			for( Entity e : entities ) {
				this.getServletContext().log( "[xxxxx] " + e.getProperty("gang"));
			}
		}
		else {
	System.out.println( "  sniper on the roof ");
		}
			this.getServletContext().log( "[xxxxx] " + req.getQueryString() );
			resp.setContentType("text/html");
      			PrintWriter out = resp.getWriter();
      			out.println("<h1> agents in place "+ req.getQueryString()+"</h1>");
	}

	public Key getKey(){
		Key key = KeyFactory.createKey("keykind", "keyname");
		return key;
	}

}
