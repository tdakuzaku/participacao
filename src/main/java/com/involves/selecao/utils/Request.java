package com.involves.selecao.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Request {
	
	public String get(String uri) throws IOException {
		URL url = new URL(uri);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		return this.readStream(con).toString();
	}
	
	private StringBuffer readStream(HttpURLConnection con) throws UnsupportedEncodingException, IOException {
		InputStreamReader reader = new InputStreamReader(con.getInputStream(), "UTF-8");
		BufferedReader in = new BufferedReader(reader);
		
		StringBuffer content = new StringBuffer();
		String inputLine;
		
		while ((inputLine = in.readLine()) != null) {
		    content.append(inputLine);
		}
		in.close();
		
		return content;
	}
}
