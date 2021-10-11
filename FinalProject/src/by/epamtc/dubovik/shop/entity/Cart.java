package by.epamtc.dubovik.shop.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Cart implements Serializable {

	private static final long serialVersionUID = -2431257232103676891L;
	private Map<Integer, Integer> cart;
	
	public Cart() {
		cart = new HashMap<Integer, Integer>();
	}

	public void put(int key, int value) {
		cart.put(key, value);
	}
	
	public Integer get(int key) {
		return cart.get(key);
	}
	
	public Integer remove(int key) {
		return cart.remove(key);
	}
	
	@Override
	public boolean equals(Object o) {
		return cart.equals(o);
	}
	
	@Override
	public int hashCode() {
		return cart.hashCode();
	}
	
	@Override
	public String toString() {
		return cart.toString();
	}
	
	public Set<Map.Entry<Integer, Integer>> entrySet(){
		return cart.entrySet();
	}
	
	public boolean containsKey(Integer key) {
		return cart.containsKey(key);
	}
}
