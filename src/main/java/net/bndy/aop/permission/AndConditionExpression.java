package net.bndy.aop.permission;

public class AndConditionExpression extends ConditionExpression {

	public AndConditionExpression(String value1, String value2) {
		super(ConditionOperation.AND, value1, value2);
	}
}
