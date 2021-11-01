package by.epamtc.dubovik.shop.dao.jdbcimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.epamtc.dubovik.shop.dao.DAOException;
import by.epamtc.dubovik.shop.dao.OrderStatusDAO;
import by.epamtc.dubovik.shop.dao.connectionpool.ConnectionPool;
import by.epamtc.dubovik.shop.dao.jdbcimpl.mapping.OrderStatusMapping;
import by.epamtc.dubovik.shop.entity.OrderStatus;

public class OrderStatusJDBC implements OrderStatusDAO {
	private static final String SQL_SELECT_ALL = 
			"SELECT * FROM order_statuses ORDER BY os_id ASC LIMIT ?,?";
	private static final String SQL_SELECT_BY_ID = 
			"SELECT * FROM order_statuses WHERE os_id=?";
	private static final String SQL_CREATE = 
			"INSERT INTO order_statuses (unq_os_name) VALUES (?)";
	private static final String SQL_UPDATE = 
			"UPDATE order_statuses SET unq_os_name = ? WHERE os_id = ?";
	
	private static final ConnectionPool POOL = ConnectionPool.getInstance();

	@Override
	public OrderStatus findById(long id) throws DAOException {
		
		OrderStatus status = null;
		
		try (Connection cn = POOL.takeConnection(); 
				PreparedStatement st = cn.prepareStatement(SQL_SELECT_BY_ID)){
			st.setLong(1, id);
			try(ResultSet rs = st.executeQuery()){
				if(rs.next()) {
					status = takeFromResultSet(rs);
				}
			}
		} catch(SQLException e) {
			throw new DAOException(e);
		}
		return status;
	}

	@Override
	public List<OrderStatus> findAll(int offset, int count) throws DAOException {
		
		List<OrderStatus> statuses= new ArrayList<>();
		
		try (Connection cn = POOL.takeConnection(); 
				PreparedStatement st = cn.prepareStatement(SQL_SELECT_ALL)){
			st.setInt(1, offset);
			st.setInt(2, count);
			try(ResultSet rs = st.executeQuery()){
				while(rs.next()) {
					OrderStatus current = takeFromResultSet(rs);
					statuses.add(current);
				}
			}
		} catch(SQLException e) {
			throw new DAOException(e); 
		}
		return statuses;
	}

	@Override
	public boolean create(OrderStatus entity) throws DAOException {
		
		boolean flag = false;
		try (Connection cn = POOL.takeConnection(); 
				PreparedStatement st = cn.prepareStatement(SQL_CREATE)){
			st.setString(1, entity.getName());
			int result = st.executeUpdate();
			flag = result > 0;
		} catch(SQLException e) {
			throw new DAOException(e);
		}
		return flag;
	}

	@Override
	public boolean update(OrderStatus entity) throws DAOException {
		
		boolean flag = false;
		try (Connection cn = POOL.takeConnection(); 
				PreparedStatement st = cn.prepareStatement(SQL_UPDATE)){
			st.setString(1, entity.getName());
			st.setLong(2, entity.getId());
			int result = st.executeUpdate();
			flag = result > 0;
		} catch(SQLException e) {
			throw new DAOException(e);
		}
		return flag;
	}
	
	private OrderStatus takeFromResultSet(ResultSet resultSet)
			throws SQLException {
		OrderStatus status = null;
		if (!resultSet.isAfterLast()) {
			status = new OrderStatus();
			status.setId(resultSet.getLong(OrderStatusMapping.ID));
			status.setName(resultSet.getString(OrderStatusMapping.NAME));
		}
		return status;
	}

}
