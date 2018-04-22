package net.bndy.aop.permission;

import net.bndy.aop.permission.Permission;

public class ActionClass {
	
	@Permission(code = "SayHello", contextCls = AppPermissionContext.class)
	public void sayHello() {
		System.out.println("Say hello.");
	}
	
	@Permission(code = "SetHello", contextCls = AppPermissionContext.class)
	public void sayMyHello(String msg) {
		System.out.println("Say my hello.");
	}

	@Permission(code = "AdminHello", contextCls = AppPermissionContext.class)
	public void adminHello() {
		System.out.println("Admin Hello");
	}
}
