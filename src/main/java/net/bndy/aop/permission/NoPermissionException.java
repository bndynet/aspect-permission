package net.bndy.aop.permission;

public class NoPermissionException extends Exception {

	private static final long serialVersionUID = 1L;
	private String permissionCode;
	
	public NoPermissionException(String permissionCode) {
		this.permissionCode = permissionCode;
	}

	public String getPermissionCode() {
		return this.permissionCode;
	}
	
	@Override
	public String getMessage() {
		return "No '" + this.getPermissionCode() +  "' Permission";
	}

}
