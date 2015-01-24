
import java.io.IOException;
import java.util.Date;
import java.util.List;

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

public class TodoServlet extends HttpServlet {

	private static final String TODOLIST = "TODOLIST";
	private static final String KIND_TODOLIST = "todolist";
	private static final String KIND_TODOLIST_ITEM = "todolistitem";

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		this.getServletContext().log( "[xxxxx] begin");
		String item = req.getParameter("item");
		this.getServletContext().log( "[xxxxx] " + item );

		Key todolistKey = KeyFactory.createKey(KIND_TODOLIST, TODOLIST);
		Entity todoListItem = new Entity(KIND_TODOLIST_ITEM, todolistKey);
		todoListItem .setProperty("content", item );
		todoListItem .setProperty("date", new Date());
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		datastore.put(todoListItem );

		this.getServletContext().log( "[xxxxx] persisted");
		//debug
		Query query = new Query(KIND_TODOLIST_ITEM, todolistKey).addSort("date",Query.SortDirection.DESCENDING);
		List<Entity> todoListItems = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(5));
		this.getServletContext().log( "[xxxxx] # = " + todoListItems.size());
		for( Entity e : todoListItems ) {
			this.getServletContext().log( "[xxxxx] " + e.getProperty("content"));
		}
		//end debug
		resp.sendRedirect("/todo.jsp");
	}
}
//[END all]