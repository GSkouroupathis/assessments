package net.peerindex.challenge.webcrawler;

import java.util.HashMap;

public class MyKeyValueStore implements KeyValueStore {

	private HashMap<String, String> store;
	
	public MyKeyValueStore() {
		this.store = new HashMap<String, String>();
	}
	
	@Override
	public boolean contains(String key) {
		if (key == null) throw new IllegalArgumentException();
		return this.store.containsKey(key);
	}

	@Override
	public String get(String key) {
		if (contains(key)) {
			return this.store.get(key);
		} else {
			return null;
		}
	}

	@Override
	public boolean put(String key, String value) {
		if (value == null) throw new IllegalArgumentException();
		
		if (contains(key)) {
			this.store.put(key, value);
			return false;
		} else {
			this.store.put(key, value);
			return true;
		}
	}

	@Override
	public boolean delete(String key) {
		if (contains(key)) {
			if (this.store.get(key) != null) {
				this.store.remove(key);
				return true;
			} else {
				return false;
			}
		}
		return false; //Not really needed
	}

}
