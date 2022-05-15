package com.learn.userrole;

import java.util.HashSet;
import java.util.Set;

import com.learn.userpermission.ApplicationUserPermission;

public enum ApplicationUserRole {
	// we have define two role STUDENT,(no permission) and ADMIN(COURSE_READ,COURSE_WRITE,STUDENT_READ,STUDENT_WRITE) 4 permission

	 STUDENT(ApplicationUserRole.rolePermissionListforStudent())
	,ADMIN(ApplicationUserRole.rolePermissionListforStudent());
	
	
	private final Set<ApplicationUserPermission>permission;
	
	
	private ApplicationUserRole(Set<ApplicationUserPermission> permission) {
		this.permission = permission;
	}


	//This if for studenet .we have not give any permissions to student .
	public static  Set<ApplicationUserPermission> rolePermissionListforStudent(){
		return new HashSet<>();
	}
	
	
	//This if for Admin .we have give 4 permissions to admin .
	public static  Set<ApplicationUserPermission> rolePermissionListforAdmin(){
		Set<ApplicationUserPermission> appUserPermission=new HashSet<>();
		
		appUserPermission.add(ApplicationUserPermission.COURSE_READ);
		appUserPermission.add(ApplicationUserPermission.COURSE_WRITE);
		appUserPermission.add(ApplicationUserPermission.STUDENT_READ);
		appUserPermission.add(ApplicationUserPermission.STUDENT_WRITE);
		
		return appUserPermission;
	}


	public Set<ApplicationUserPermission> getPermission() {
		return permission;
	}
	
	
	

}
