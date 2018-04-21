package net.bndy.aop.permission;

import net.bndy.aop.permission.Permission;

public class ActionClass {
	
	@Permission(code = "RW")
	public void sayHello() {
		System.out.println("This method has been executed which has Permission annotation.");
	}
}
