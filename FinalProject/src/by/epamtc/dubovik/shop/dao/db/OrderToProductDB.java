package by.epamtc.dubovik.shop.dao.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import by.epamtc.dubovik.shop.connectionpool.ConnectionPool;
import by.epamtc.dubovik.shop.dao.DAOException;
import by.epamtc.dubovik.shop.dao.OrderToProductDAO;
import by.epamtc.dubovik.shop.dao.db.mapping.SaleMapping;
import by.epamtc.dubovik.shop.entity.OrderToProduct;

public class OrderToProductDB implements OrderToProductDAO {

	private static final String SQL_SELECT_ALL = 
			"SELECT * FROM `dubovik_Shop`.`sales` LIMIT ?,?";
	private static final String SQL_SELECT_BY_ORDERID = 
			"SELECT * FROM `dubovik_Shop`.`sales` WHERE `s_o_id`=? LIMIT ?,?";
	private static final String SQL_SELECT_BY_PRODUCTID = 
			"SELECT * FROM `dubovik_Shop`.`sales` WHERE `s_p_id`=? LIMIT ?,?";
	private static final String SQL_CREATE = 
			"INSERT INTO `dubovik_Shop`.`sales` (`s_p_id`, `s_quantity`) VALUES (?, ?)";
	private static final String SQL_DELETE_BY_ID = 
			"DELETE FROM `dubovik_Shop`.`sales` WHERE `s_o_id`=? AND  `s_p_id`=?";
	private static final String SQL_DELETE_BY_ENTITY = 
			"DELETE FROM `dubovik_Shop`.`sales` WHERE `s_o_id` = ? AND `s_p_id` = ? AND `s_quantity` = ?";
	private static final String SQL_UPDATE = 
			"UPDATE `dubovik_Shop`.`sales` SET `s_quantity` = ? WHERE `s_o_id` = ? AND `s_p_id` = ?";
	
	private OrderToProduct takeFromResultSet(ResultSet resultSet) throws SQLException {
		OrderToProduct orderToProduct = null;
		if (!resultSet.isAfterLast()) {
			orderToProduct = new OrderToProduct();
			orderToProduct.setOrderId(resultSet.getInt(SaleMapping.ORDER_ID));
			orderToProduct.setProductId(resultSet.getInt(SaleMapping.PRODUCT_ID));
			orderToProduct.setQuantity(resultSet.getInt(SaleMapping.QUANTITY));
		}
		return orderToProduct;
	}

	//"SELECT * FROM `dubovik_Shop`.`sales` LIMIT ?,?";
	@Override
	public List<OrderToProduct> findAll(int offset, int count) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	//"DELETE FROM `dubovik_Shop`.`sales` WHERE `s_o_id` = ? AND `s_p_id` = ? AND `s_quantity` = ?";
	@Override
	public boolean delete(OrderToProduct entity) throws DAOException {
		// TODO Auto-generated method stub
		return false;
	}

	//"INSERT INTO `dubovik_Shop`.`sales` (`s_p_id`, `s_quantity`) VALUES (?, ?)";
	@Override
	public boolean create(OrderToProduct entity) throws DAOException {
		// TODO Auto-generated method stub
		return false;
	}

	//"UPDATE `dubovik_Shop`.`sales` SET `s_quantity` = ? WHERE `s_o_id` = ? AND `s_p_id` = ?";
	@Override
	public boolean update(OrderToProduct entity) throws DAOException {
		// TODO Auto-generated method stub
		return false;
	}

	//"SELECT * FROM `dubovik_Shop`.`sales` WHERE `s_o_id`=? LIMIT ?,?";
	@Override
	public List<OrderToProduct> findByOrder(int orderId, int offset, int count) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	//"SELECT * FROM `dubovik_Shop`.`sales` WHERE `s_p_id`=? LIMIT ?,?";
	@Override
	public List<OrderToProduct> findByProduct(int productId, int offset, int count) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	//"DELETE FROM `dubovik_Shop`.`sales` WHERE `s_o_id`=? AND  `s_p_id`=?";
	@Override
	public boolean delete(int orderId, int productId) throws DAOException {
		// TODO Auto-generated method stub
		return false;
	}

}
