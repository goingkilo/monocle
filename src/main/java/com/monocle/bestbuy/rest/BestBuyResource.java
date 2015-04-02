package com.monocle.bestbuy.rest;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
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

@Path("/product")
public class BestBuyResource {

	@POST
	
	@Path("/query")
	@Produces("application/json")
	@Consumes("application/x-www-form-urlencoded") 
	public Response getMsg(
			@DefaultValue("longDescription=black*") @FormParam("expr") String pExpr,
			@DefaultValue("name,salePrice,image,longDescription") @FormParam("show") String pShow,
			@DefaultValue("3") @FormParam("page") String pPage,
			@DefaultValue("15") @FormParam("pagesize") String pPageSize
			) {
		System.out.println ("++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println (pExpr);
		System.out.println (pShow);
		System.out.println (pPage);
		System.out.println (pPageSize);

		String url = "http://api.remix.bestbuy.com/v1/products(EXPR)?show=SHOW&pageSize=PAGESIZE&page=PAGE&apiKey=xsq2hy53xq74mhrrkt7ex6s5&format=json";
		url = url.replace( "EXPR", pExpr);
		url =url.replace("SHOW",pShow);
		url =url.replace("PAGESIZE",pPageSize);
		url =url.replace("PAGE",pPage);
		System.out.println (">>"+url);
		String json = getJSON(url);
		System.out.println (">>\n" + json);
		System.out.println ("++++++++++++++++++++++++++++++++++++++++++++");		

		return Response.ok(json, MediaType.APPLICATION_JSON).build();

	}

	private String getJSON(String url )  {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

			public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
				int status = response.getStatusLine().getStatusCode();
				if (status >= 200 && status < 300) {
					HttpEntity entity = response.getEntity();
					return entity != null ? EntityUtils.toString(entity) : null;
				} else {
					throw new ClientProtocolException("Unexpected response status: " + status);
				}
			}

		};
		try {
			HttpGet httpget = new HttpGet(url);
			System.out.println("Executing request " + httpget.getRequestLine());
			String responseBody = httpclient.execute(httpget, responseHandler);
			return responseBody;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "fail";
	}


}
