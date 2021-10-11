package by.epamtc.dubovik.shop.entity;

import java.io.Serializable;

public class Entity implements Serializable {
	
	private static final long serialVersionUID = -1147138083222384911L;

	protected boolean equalsWithNull(Object o1, Object o2) {
		return o1 != null ? o1.equals(o2) : o2 == null;
	}
	
	protected int hashCodeWithNull(Object o) {
		return o != null ? o.hashCode() : 0;
	}

}
