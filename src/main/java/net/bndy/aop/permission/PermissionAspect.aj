package net.bndy.aop.permission;

import java.util.List;

import org.aspectj.lang.Signature;

public aspect PermissionAspect {

	pointcut checkPermission(Permission permission)
		: execution(@net.bndy.aop.permission.Permission * *.*(..)) && @annotation(permission); 
	  
    before(Permission permission) : checkPermission(permission) {
    	System.out.println("===============================");
    	System.out.println(">> Begin checking permission...");
    }
    
    Object around(Permission permission): checkPermission(permission) {
    	Signature signature = thisJoinPoint.getSignature();
    	String code = permission.code();
		System.out.println("** Permission: @code=" + code);

	    try {
			PermissionContext permissionContext = permission.contextCls().newInstance();
			List<String> permissionCodes = permissionContext.getPermissions();
			System.out.println("** Own permissions: " + permissionCodes);
			if (!permissionCodes.contains(code)) {
				throw new NoPermissionException(code);
			}

			long start = System.nanoTime();
			Object resultObject = proceed(permission);
			long end = System.nanoTime();
			System.out.println(String.format(">> %s took %d ns", signature, (end - start)));
			return resultObject;
		} catch (IllegalAccessException | InstantiationException | NoPermissionException e) {
			throw new RuntimeException(e);
		}
    }
}
