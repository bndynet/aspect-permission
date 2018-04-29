package net.bndy.aop.permission;

public class OrConditionExpression extends ConditionExpression {
	public OrConditionExpression(String value1, String value2) {
		super(ConditionOperation.OR, value1, value2);
	}
}
