package by.epamtc.dubovik.shop.entity;

public class User extends Entity {
	
	private static final long serialVersionUID = -3709659241419930282L;
	private int id;
	private String phone;
	private String login;
	private byte[] password;
	private String name;
	private String eMail;
	private boolean isBlacklisted;
	private int roleId;
	
	public User() {}
	
	public User(int id, 
			String phone, 
			String login,
			byte[] password, 
			String name, 
			String eMail, 
			boolean isBlacklisted, 
			int roleId) {
		this.id = id;
		this.phone = phone;
		this.login = login;
		this.password = password;
		this.name = name;
		this.eMail = eMail;
		this.isBlacklisted = isBlacklisted;
		this.roleId = roleId;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public byte[] getPassword() {
		return password;
	}
	
	public void setPassword(byte[] password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEMail() {
		return eMail;
	}
	
	public void setEMail(String eMail) {
		this.eMail = eMail;
	}
	
	public boolean getIsBlacklisted() {
		return isBlacklisted;
	}
	
	public void setIsBlacklisted(boolean isBlacklisted) {
		this.isBlacklisted = isBlacklisted;
	}
	
	public int getRoleId() {
		return roleId;
	}
	
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o)
			return true;
		if(o == null)
			return false;
		if(this.getClass() != o.getClass())
			return false;
		User other = (User)o;
		if(this.id == other.getId() &&
				equalsWithNull(this.phone, other.getPhone()) &&
				equalsWithNull(this.login, other.getLogin()) &&
				equalsWithNull(this.password, other.getPassword()) &&
				equalsWithNull(this.name, other.getName()) &&
				equalsWithNull(this.eMail, other.getEMail()) &&
				this.isBlacklisted == other.getIsBlacklisted() &&
				this.roleId == other.getRoleId()) {
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		int result = 1;
		int prime = 31;
		result = result * prime + id;
		result = result * prime + hashCodeWithNull(phone);
		result = result * prime + hashCodeWithNull(login);
		result = result * prime + hashCodeWithNull(password);
		result = result * prime + hashCodeWithNull(name);
		result = result * prime + hashCodeWithNull(eMail);
		result = result * prime + (isBlacklisted ? 1 : 0);
		result = result * prime + roleId;
		return result;
	}

	@Override
	public String toString() {
		StringBuffer result = new StringBuffer(this.getClass().getName() + "@");
		result.append(id + ",");
		result.append(phone + ",");
		result.append(login + ",");
		result.append(password + ",");
		result.append(name + ",");
		result.append(eMail + ",");
		result.append(isBlacklisted + ",");
		result.append(roleId);
		return result.toString();
	}
}
