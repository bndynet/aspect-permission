package net.bndy.aop.permission;

import org.aspectj.lang.Signature;

public aspect PermissionAspect {

	pointcut checkPermission(Permission permission)
		: execution(@net.bndy.aop.permission.Permission * *.*(..)) && @annotation(permission); 
	  
    before(Permission permission) : checkPermission(permission) {
    	System.out.println(">> Begin checking permission...");
    }
    
//    after(Permission permission): checkPermission(permission) {
//    	System.out.println("End checking permission");
//    }
    
    Object around(Permission permission): checkPermission(permission) {
    	Signature signature = thisJoinPoint.getSignature();
	    try {
			long start = System.nanoTime();
			Object resultObject = proceed(permission);
			long end = System.nanoTime();
			System.out.println(">> Permission: @code=" + permission.code());
			System.out.println(String.format(">> %s took %d ns", signature, (end - start)));
			return resultObject;
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
    }
}
