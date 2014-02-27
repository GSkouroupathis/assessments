package net.peerindex.challenge.webcrawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Factory.
 */
public class Factory {

    public static KeyValueStore createKeyValueStore() {
        return new MyKeyValueStore();
    }

    public static WebCrawler createWebCrawler() {
        return new MyWebCrawler();
    }

    public static Iterator<URL> createURLIterator() {
    	ArrayList urlArrayList = new ArrayList();
    	for (int i=0; i<167; i++) {
    		String file_name = createFileName(i);
    		String file_path = "resources/" + file_name;
    		File file = new File(file_path);
    		BufferedReader br = null;
			try {
				br = new BufferedReader(new FileReader(file));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
    		String line;
    		try {
				while ((line = br.readLine()) != null) {
					JSONObject jsonObj = new JSONObject(line);
					String url_string = jsonObj.getString("url");
					URL url = new URL(url_string);
					urlArrayList.add(url);
					System.out.println(url_string);
				}
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
    	}
    	return urlArrayList.iterator();
    }
    
    private static String createFileName(int i) {
    	String prefix;
    	if (i >= 100) {
    		prefix = "00";
    	} else if (i >= 10) {
    		prefix = "000";
    	} else {
    		prefix = "0000";
    	}
    	return "part-m-" + prefix + Integer.toString(i);
    }

}
