package net.bndy.aop.permission;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
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
	
	@Test
	public void testConditionExpression() {
		List<String>  permissions = new ArrayList<String>();
		for(Integer i = 0;  i< 10; i++) {
			permissions.add(i.toString());
		}
		
		ConditionExpression cExpression = new ConditionExpression(
				ConditionOperation.AND, 
				new AndConditionExpression("1", "3"),
				"11");
		Assert.assertEquals(cExpression.result(permissions), false);
		
		cExpression = new ConditionExpression(
				ConditionOperation.AND, 
				new AndConditionExpression("1", "3"),
				new NotConditionExpression("11")
				);
		Assert.assertEquals(cExpression.result(permissions), true);
		
		cExpression = new ConditionExpression(
				ConditionOperation.AND, 
				new AndConditionExpression("1", "3"),
				"2");
		Assert.assertEquals(cExpression.result(permissions), true);
		
		cExpression = new ConditionExpression(
				ConditionOperation.AND, 
				new AndConditionExpression("1", "3"),
				new AndConditionExpression("2", "4"));
		Assert.assertEquals(cExpression.result(permissions), true);
		
		cExpression = new ConditionExpression(
				ConditionOperation.AND, 
				new AndConditionExpression("1", "3"),
				new AndConditionExpression("2", "14"));
		Assert.assertEquals(cExpression.result(permissions), false);
		
		cExpression = new ConditionExpression(
				ConditionOperation.OR, 
				new AndConditionExpression("1", "3"),
				new AndConditionExpression("2", "14"));
		Assert.assertEquals(cExpression.result(permissions), true);
		
		cExpression = new ConditionExpression(
				ConditionOperation.OR, 
				new AndConditionExpression("1", "31"),
				new AndConditionExpression("2", "14"));
		Assert.assertEquals(cExpression.result(permissions), false);
		
		cExpression = new ConditionExpression(
				ConditionOperation.AND, 
				new NotConditionExpression("12"),
				new NotConditionExpression("14"));
		Assert.assertEquals(cExpression.result(permissions), true);
		
		cExpression = new ConditionExpression(
				ConditionOperation.AND, 
				new ConditionExpression(ConditionOperation.AND, new NotConditionExpression("11"), new AndConditionExpression("1", "2")),
				new ConditionExpression(ConditionOperation.OR, new NotConditionExpression("13"), new AndConditionExpression("3", "4"))
				);
		Assert.assertEquals(cExpression.result(permissions), true);
	}
	
	@Test
	public void testParser() {
		new ConditionExpresionParser("((1 and 2) or (3 or 4)) and (5 or 7)").result();
	}
}
