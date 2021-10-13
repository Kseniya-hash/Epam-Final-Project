package by.epamtc.dubovik.shop.service.util;

import java.util.List;
import java.util.Set;

public interface SecurityConfig {

	public Set<String> getAllRoles();

	public List<String> getCommandsForRole(String role);

}
