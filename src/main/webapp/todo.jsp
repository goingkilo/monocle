<%-- //[START all]--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="com.google.appengine.api.users.UserService"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>
<%-- //[START imports]--%>
<%@ page import="com.google.appengine.api.datastore.DatastoreService"%>
<%@ page
	import="com.google.appengine.api.datastore.DatastoreServiceFactory"%>
<%@ page import="com.google.appengine.api.datastore.Entity"%>
<%@ page import="com.google.appengine.api.datastore.FetchOptions"%>
<%@ page import="com.google.appengine.api.datastore.Key"%>
<%@ page import="com.google.appengine.api.datastore.KeyFactory"%>
<%@ page import="com.google.appengine.api.datastore.Query"%>
<%-- //[END imports]--%>
<%@ page import="java.util.List"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>
<head>
<link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />
</head>

<body>


	<form action="/todoServlet" method="post">
		<div>
			<textarea name="item" rows="3" cols="60"></textarea>
		</div>
		<div>
			<input type="submit" value="add todo" />
		</div>
	</form>
	<%-- //[START datastore]--%>
	<%
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Key todolistKey = KeyFactory.createKey("todolist", "TODOLIST");
		// Run an ancestor query to ensure we see the most up-to-date
		// view of the Greetings belonging to the selected Guestbook.
		Query query = new Query("todolistitem", todolistKey).addSort("date", Query.SortDirection.DESCENDING);
		List<Entity> todoListItems = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(15));
		if (todoListItems.isEmpty()) {
	%>
	<p>To Do List has no items.</p>
	<%
		} else {
	%>
	<p> (updated)Items in To Do List.</p>
	<%
		for (Entity item : todoListItems) {
				pageContext.setAttribute("todoitem",
						item.getProperty("content"));
	%>
	<blockquote>${fn:escapeXml(todoitem)}</blockquote>
	<%
		}
		}
	%>


	<%-- //[END datastore]--%>


</body>
</html>
<%-- //[END all]--%>
