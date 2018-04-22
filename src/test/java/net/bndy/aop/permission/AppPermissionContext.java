package net.bndy.aop.permission;

import java.util.ArrayList;
import java.util.List;

public class AppPermissionContext implements PermissionContext {

	@Override
	public List<String> getPermissions() {
		List<String> list = new ArrayList<String>();
		list.add("SayHello");
		list.add("SetHello");
		return list;
	} 
}
