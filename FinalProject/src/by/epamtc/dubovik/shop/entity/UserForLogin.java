package by.epamtc.dubovik.shop.entity;

import java.io.Serializable;
import java.util.Objects;

public class UserForLogin implements Serializable {
	
	private static final long serialVersionUID = -6875871018369306020L;
	private String login;
	private byte[] password;
	
	public UserForLogin() {}
	
	public UserForLogin(String login, byte[] password) {
		this.login = login;
		this.password = password;
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
	
	@Override
	public boolean equals(Object o) {
		if(this == o)
			return true;
		if(o == null)
			return false;
		if(this.getClass() != o.getClass())
			return false;
		UserForLogin other = (UserForLogin)o;
		if(Objects.equals(this.login, other.getLogin()) &&
				Objects.equals(this.password, other.getPassword())) {
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		int result = 1;
		int prime = 31;
		result = result * prime + Objects.hashCode(login);
		result = result * prime + Objects.hashCode(password);
		return result;
	}

	@Override
	public String toString() {
		StringBuffer result = new StringBuffer(this.getClass().getName() + "@");
		result.append(login + ",");
		result.append(password);
		return result.toString();
	}
}
