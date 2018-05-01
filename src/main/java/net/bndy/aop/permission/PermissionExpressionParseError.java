package net.bndy.aop.permission;

public class PermissionExpressionParseError extends Exception {
	
	public PermissionExpressionParseError(String expression) {
		super("Invalid permission expression: " + expression);
	}
}
