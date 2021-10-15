package by.epamtc.dubovik.shop.entity;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
	
	private static final long serialVersionUID = -3709659241419930282L;
	private long id;
	private String phone;
	private String login;
	private byte[] password;
	private String name;
	private String eMail;
	private boolean isBlacklisted;
	private long roleId;
	
	public User() {}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
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
	
	public long getRoleId() {
		return roleId;
	}
	
	public void setRoleId(long roleId) {
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
				Objects.equals(this.phone, other.getPhone()) &&
				Objects.equals(this.login, other.getLogin()) &&
				Objects.equals(this.password, other.getPassword()) &&
				Objects.equals(this.name, other.getName()) &&
				Objects.equals(this.eMail, other.getEMail()) &&
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
		result = result * prime + (int)id;
		result = result * prime + Objects.hashCode(phone);
		result = result * prime + Objects.hashCode(login);
		result = result * prime + Objects.hashCode(password);
		result = result * prime + Objects.hashCode(name);
		result = result * prime + Objects.hashCode(eMail);
		result = result * prime + (isBlacklisted ? 1 : 0);
		result = result * prime + (int)roleId;
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
