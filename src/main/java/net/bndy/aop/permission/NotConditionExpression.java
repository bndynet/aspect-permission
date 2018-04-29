package net.bndy.aop.permission;

public class NotConditionExpression extends ConditionExpression {
	public NotConditionExpression(String value) {
		super(ConditionOperation.NOT, value, null);
	}
}
