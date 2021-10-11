package by.epamtc.dubovik.shop.entity;

public class UserLogged extends Entity {
	
	private static final long serialVersionUID = -2296118640944821035L;
	
	private int id;
	private String login;
	private boolean isBlacklisted;
	private String role;
	
	public UserLogged() {}
	
	public UserLogged(String login, boolean isBlacklisted, String role) {
		this.login = login;
		this.isBlacklisted = isBlacklisted;
		this.role = role;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public boolean getIsBlacklisted() {
		return isBlacklisted;
	}
	
	public void setIsBlacklisted(boolean isBlacklisted) {
		this.isBlacklisted = isBlacklisted;
	}
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o)
			return true;
		if(o == null)
			return false;
		if(this.getClass() != o.getClass())
			return false;
		UserLogged other = (UserLogged)o;
		if(this.id == other.getId() &&
				equalsWithNull(this.login, other.getLogin()) &&
				this.isBlacklisted == other.getIsBlacklisted() &&
				equalsWithNull(this.role, other.getRole())) {
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		int result = 1;
		int prime = 31;
		result = result * id;
		result = result * prime + hashCodeWithNull(login);
		result = result * prime + (isBlacklisted ? 1 : 0);
		result = result * prime + hashCodeWithNull(role);
		return result;
	}

	@Override
	public String toString() {
		StringBuffer result = new StringBuffer(this.getClass().getName() + "@");
		result.append(id + ",");
		result.append(login + ",");
		result.append(isBlacklisted + ",");
		result.append(role);
		return result.toString();
	}

}
