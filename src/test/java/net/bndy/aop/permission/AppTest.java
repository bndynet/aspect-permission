package net.bndy.aop.permission;

import org.junit.Test;

public class AppTest {
	
	@Test 
	public void start() {
		
		ActionClass actionClass = new ActionClass();
		actionClass.sayHello();
		actionClass.sayMyHello("Hi, My Hello.");
		
		try {
			actionClass.adminHello();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
	}
}
