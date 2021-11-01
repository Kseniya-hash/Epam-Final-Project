package by.epamtc.dubovik.shop.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Cart implements Serializable {

	private static final long serialVersionUID = -2431257232103676891L;
	private Map<Long, Integer> cart;
	
	public Cart() {
		cart = new HashMap<Long, Integer>();
	}

	public void put(long key, int value) {
		cart.put(key, value);
	}
	
	public Integer get(long key) {
		return cart.get(key);
	}
	
	public Integer remove(long key) {
		return cart.remove(key);
	}
	
	@Override
	public boolean equals(Object o) {
		if(cart == o)
			return true;
		if(o == null)
			return false;
		if(o.getClass() != this.getClass())
			return false;
		Cart other = (Cart) o;
		return cart.equals(other.cart);
	}
	
	@Override
	public int hashCode() {
		return cart.hashCode();
	}
	
	@Override
	public String toString() {
		return cart.toString();
	}
	
	public Set<Map.Entry<Long, Integer>> entrySet(){
		return cart.entrySet();
	}
	
	public boolean containsKey(Long key) {
		return cart.containsKey(key);
	}
}
