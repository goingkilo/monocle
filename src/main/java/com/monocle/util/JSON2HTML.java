package com.monocle.util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSON2HTML {

	static String readFile(String path, Charset encoding) 
			throws IOException 
	{
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

	void debug (List<String> ret) {
		for(String line : ret) {
			System.out.println(line);
		}
	}

	public static String html(JSONObject product){
		StringBuilder s = new StringBuilder();
		for( int i = 0 ; i < product.length() ; i++) {
			s.append("<div id=\"product\"");

			s.append("<div id=\"name\"");
			s.append("</div>");
			s.append("<div id=\"name\"");
			s.append("</div>");
			s.append("<div id=\"name\"");
			s.append("</div>");
			s.append("<div id=\"name\"");
			s.append("</div>");

			s.append("</div>");
		}
		return s.toString();
	}

	public static List<JSONObject> getProducts(String jsonString) {
		List<JSONObject> ret = new ArrayList<JSONObject>();
		JSONObject doc = new JSONObject (jsonString);
		JSONArray products = doc.getJSONArray("products");
		for( int i = 0 ; i < products.length() ; i++) {
			JSONObject product = products.getJSONObject(i);
			System.out.println(  product.get("name") );
			ret.add(product);
		}
		return ret;
	}

	public static void main (String[] args) throws IOException {
		new JSON2HTML().getProducts( readFile("C:/Users/kilo/workspace/mdownloader/downloader/src/main/java/rhino/json.txt",Charset.defaultCharset()));
	}


}
