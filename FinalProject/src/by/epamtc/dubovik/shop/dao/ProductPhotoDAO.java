package by.epamtc.dubovik.shop.dao;

import java.util.List;

import by.epamtc.dubovik.shop.dao.generic.GenericIntIdDAO;
import by.epamtc.dubovik.shop.entity.ProductPhoto;

public interface ProductPhotoDAO extends GenericIntIdDAO<ProductPhoto> {
	
	public List<ProductPhoto> findByProductId(int productId, int offset, int count) throws DAOException;

}
