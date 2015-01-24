
import java.io.IOException;
import java.io.PrintWriter;
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

public class TodoServletHTML extends HttpServlet {
	
	String page = "<html><head><link type=\"text/css\" rel=\"stylesheet\" href=\"/stylesheets/main.css\" /></head><body>"+
"<script src=\"./jquery.js\"></script>	<form action=\"/todoServletHtml\" method=\"post\">		<div>			<textarea name=\"item\" rows=\"3\" cols=\"60\"></textarea>"+
	"</div>		<div>			<input type=\"submit\" value=\"add todo\" />		</div>	</form>"+
	"<div class='todolist'>	--list here --	</div></body></html>";

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
		//
		//resp.sendRedirect("/todo.jsp");
		PrintWriter out = resp.getWriter();
		out.write( getHTML(datastore,todolistKey));
		out.flush();
		out.close();
	}

	private char[] getHTML(DatastoreService datastore, Key todolistKey) {
		Query query = new Query(KIND_TODOLIST_ITEM, todolistKey).addSort("date",Query.SortDirection.DESCENDING);
		
		List<Entity> todoListItems = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(20));
		this.getServletContext().log( "[xxxxx] # = " + todoListItems.size());
		
		StringBuffer s = new StringBuffer("<html>");
		for( Entity e : todoListItems ) {
			String date = ((java.util.Date) e.getProperty("date")).toString();
			String content = (String) e.getProperty("content");
			s.append( date );
			s.append( " .. ");
			s.append( content);
			s.append( "<br>");
			this.getServletContext().log( "[xxxxx] " + s.toString() );
		}
		return page.replace( "--list here --", s.toString()).toCharArray();
	}
}
