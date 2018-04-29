package net.bndy.aop.permission;

import java.util.List;

public class ConditionExpression {
	private Object value;
	private Object extraValue;
	private ConditionOperation operation;
	
	protected ConditionExpression(ConditionOperation operation, String value, String extraValue) {
		this.operation = operation;
		this.value = value;
		this.extraValue = extraValue;
	}
	
	public ConditionExpression(ConditionOperation operation, ConditionExpression exp1, ConditionExpression exp2) {
		this.operation = operation;
		this.value = exp1;
		this.extraValue = exp2;
	}
	
	public ConditionExpression(ConditionOperation operation, ConditionExpression exp1, String extraValue) {
		this.operation = operation;
		this.value = exp1;
		this.extraValue = extraValue;
	}
	
	public boolean result(List<String> all) {
		boolean leftValue = false;
		boolean rightValue = false;

		if (this.value != null && this.value instanceof ConditionExpression) {
			leftValue = ((ConditionExpression)this.value).result(all);
		} else {
			leftValue = all.contains(this.value);
		}
		
		if (this.extraValue != null && this.extraValue instanceof ConditionExpression) {
			rightValue = ((ConditionExpression)this.extraValue).result(all);
		} else {
			rightValue = all.contains(this.extraValue);
		}
		
		switch (this.operation) {
			case NOT:
				return !leftValue;
				
			case AND:
				return leftValue && rightValue;
				
			case OR:
				return leftValue || rightValue;

			default:
				return false;
		}
	}
}
