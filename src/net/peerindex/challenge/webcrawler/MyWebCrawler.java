package net.peerindex.challenge.webcrawler;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

public class MyWebCrawler implements WebCrawler{

	private KeyValueStore store;
	private Iterator<URL> iterator;
	
	public MyWebCrawler() {
		//nada
	}
	
	@Override
	public void setKeyValueStore(KeyValueStore store) {
		this.store = store;
	}

	@Override
	public void setURLStream(Iterator<URL> iterator) {
		this.iterator = iterator;		
	}

	@Override
	public void initialise() {
		System.out.println("Initializing the crawler");
	}

	@Override
	public void execute() {
		while (iterator.hasNext()){
			 HttpURLConnection connection = null;
			 try {
			      //Create connection
			      URL url = iterator.next();
			      connection = (HttpURLConnection)url.openConnection();
			      connection.setRequestMethod("GET");
				
			      connection.setUseCaches (false);
			      connection.setDoInput(true);
			      connection.setDoOutput(true);
			      System.out.println("Crawling " + url.toString());
			      //Send request
			      DataOutputStream wr = new DataOutputStream (
			                  connection.getOutputStream ());
			      wr.flush ();
			      wr.close ();

			      //Get Response	
			      InputStream is = connection.getInputStream();
			      BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			      String line;
			      StringBuffer response = new StringBuffer(); 
			      while((line = rd.readLine()) != null) {
			        response.append(line);
			        response.append('\r');
			      }
			      rd.close();
			      store.put(url.toString(), response.toString()); 
			      
			    } catch (Exception e) {

			      e.printStackTrace();
			    } finally {
			      if(connection != null) {
			        connection.disconnect(); 
			      }
			    }
		}
	}

	@Override
	public void shutdown() {
		System.out.println("Shutting down the crawler");
	}

}
