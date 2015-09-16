package de.thomasjacob.hypertunnel.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class MapOfLists<K, V> implements Serializable {
	private Map<K, List<V>> map = new HashMap<K, List<V>>();

	public void add(K key, int index, V value) {
		getOrCreate(key).add(index, value);
	}

	public void add(K key, V value) {
		getOrCreate(key).add(value);
	}

	public void addAll(K key, Collection<V> values) {
		getOrCreate(key).addAll(values);
	}

	public void addAll(K key, int index, Collection<V> values) {
		getOrCreate(key).addAll(index, values);
	}

	public Map<K, List<V>> asMap() {
		return Collections.unmodifiableMap(map);
	}

	public void clear() {
		map.clear();
	}

	public boolean containsKey(K key) {
		return map.containsKey(key);
	}

	public Set<Entry<K, List<V>>> entrySet() {
		return map.entrySet();
	}

	public List<V> get(K key) {
		return map.get(key);
	}

	public List<V> getOrCreate(K key) {
		List<V> list = map.get(key);
		if (list == null) {
			list = new ArrayList<V>();
			map.put(key, list);
		}

		return list;
	}

	public boolean isEmpty() {
		for (List<V> list : map.values()) {
			if (!list.isEmpty()) {
				return false;
			}
		}

		return true;
	}

	public Set<K> keySet() {
		return map.keySet();
	}

	public void put(K key, List<V> list) {
		map.put(key, list);
	}

	public V remove(K key, int index) {
		if (index < 0) {
			return null;
		}

		List<V> list = get(key);
		if (list == null) {
			return null;
		}

		if (index >= list.size()) {
			return null;
		}

		return list.remove(index);
	}

	public boolean remove(K key, V value) {
		List<V> list = get(key);
		if (list == null) {
			return false;
		}

		return list.remove(value);
	}

	public boolean removeAll(K key, Collection<V> values) {
		List<V> list = get(key);
		if (list == null) {
			return false;
		}

		return list.removeAll(values);
	}

	public List<V> removeKey(K key) {
		return map.remove(key);
	}

	public int size() {
		int size = 0;
		for (List<V> list : map.values()) {
			size += list.size();
		}

		return size;
	}

	@Override
	public String toString() {
		return map.toString();
	}

	public List<V> values() {
		List<V> values = new ArrayList<V>();
		for (List<V> list : map.values()) {
			values.addAll(list);
		}

		return values;
	}
}
