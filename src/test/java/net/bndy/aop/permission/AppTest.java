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
	public void textToExpressionString() {
		String s = "(1 and 3) or (4 and 5) and 4 or 6";
		System.out.println(s + "=>" + new ConditionExpression(s).toString());
	}
	
	@Test
	public void parse() throws PermissionExpressionParseError {
		List<String> lst = new ArrayList<>();
		lst.add("1");
		lst.add("2");
		lst.add("3");
		lst.add("4");
		lst.add("5");
		Assert.assertEquals(new ConditionExpresionParser("1").result().result(lst), true);
		Assert.assertEquals(new ConditionExpresionParser("!1").result().result(lst), false);
		Assert.assertEquals(new ConditionExpresionParser("!9").result().result(lst), true);
		Assert.assertEquals(new ConditionExpresionParser("1 and 3").result().result(lst), true);
		Assert.assertEquals(new ConditionExpresionParser("1 and 7").result().result(lst), false);
		Assert.assertEquals(new ConditionExpresionParser("(1 and 2) or 8").result().result(lst), true);
		Assert.assertEquals(new ConditionExpresionParser("(1 and 8) or 2").result().result(lst), true);
		Assert.assertEquals(new ConditionExpresionParser("(1 and 8) or 9").result().result(lst), false);
		Assert.assertEquals(new ConditionExpresionParser("(1 and 8) or (2 and 4)").result().result(lst), true);
		Assert.assertEquals(new ConditionExpresionParser("(1 and 8) or (2 and 6)").result().result(lst), false);
		Assert.assertEquals(new ConditionExpresionParser("(1 and 8) or (2 and 6) or 3").result().result(lst), true);
		Assert.assertEquals(new ConditionExpresionParser("(1 and 8) or (2 and 6) or !3").result().result(lst), false);
	}
}
