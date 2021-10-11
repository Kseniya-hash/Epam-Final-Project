package by.epamtc.dubovik.shop.service.impl.sortproduct;

import java.util.HashMap;

import by.epamtc.dubovik.shop.service.exception.ServiceException;

public class SortMapping {
	
	private HashMap<SortType, ProductSort> productMap;
	
	private SortMapping() {
		productMap = new HashMap<SortType, ProductSort>();
		productMap.put(SortType.RATING, new SortByRating());
		productMap.put(SortType.COMMENT_COUNT, new SortByCommentCount());
		productMap.put(SortType.PRICE_INC, new SortByPriceInc());
		productMap.put(SortType.PRICE_DESC, new SortByPriceDesc());
	}
	
	public ProductSort takeByKey(SortType type) throws ServiceException {
		if(!productMap.containsKey(type)) {
			throw new ServiceException("Invalid sorting option for products");
		}
		return productMap.get(type);
	}
	
	private static class SigletonHolder {
		private final static SortMapping INSTANCE = new SortMapping();
	}
	
	public static SortMapping getInstance() {
		return SigletonHolder.INSTANCE;
	}

}
