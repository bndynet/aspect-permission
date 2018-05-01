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
	
	public ConditionExpression(ConditionOperation operation, String value, ConditionExpression exp1) {
		this.operation = operation;
		this.value = value;
		this.extraValue = exp1;
	}
	
	public ConditionExpression(ConditionOperation operation, ConditionExpression exp1, String extraValue) {
		this.operation = operation;
		this.value = exp1;
		this.extraValue = extraValue;
	}
	
	public boolean result(List<String> all) {
		boolean leftValue = false;
		boolean rightValue = false;

		if (this.value != null) {
			if (this.value instanceof ConditionExpression) {
				leftValue = ((ConditionExpression)this.value).result(all);
			} else {
				leftValue = this.value.toString().isEmpty() ? true : all.contains(this.value);
			}
		} else {
			leftValue = true;
		}
		
		if (this.extraValue != null) {
			if (this.extraValue instanceof ConditionExpression) {
				rightValue = ((ConditionExpression)this.extraValue).result(all);
			} else {
				rightValue = this.extraValue.toString().isEmpty() ? true : all.contains(this.extraValue);
			}
		} else {
			rightValue = true;
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
	
	@Override
	public String toString() {
		switch (this.operation) {
		case NOT:
			return "!" + this.value.toString();

		default:
			return "(" + this.value.toString() + " " + this.operation + " " + this.extraValue.toString() + ")";
		}
	}
}
