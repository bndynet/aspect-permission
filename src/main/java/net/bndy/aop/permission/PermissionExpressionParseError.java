package net.bndy.aop.permission;

public class PermissionExpressionParseError extends Exception {
	private static final long serialVersionUID = 1L;

	public PermissionExpressionParseError(String expression) {
		super("Invalid permission expression: " + expression);
	}
}
