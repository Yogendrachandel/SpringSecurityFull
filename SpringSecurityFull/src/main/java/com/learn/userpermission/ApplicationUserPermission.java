package com.learn.userpermission;

public enum ApplicationUserPermission {
//THERE ARE 4 BELOW PERMISSIONS
	STUDENT_READ("student:read"),
	STUDENT_WRITE("student:write"),
	COURSE_READ("course:read"),
	COURSE_WRITE("course:write");
	
	
	private final String permissions; 

	ApplicationUserPermission(String string) {
		this.permissions=string;
	}

	public String getPermissions() {
		return permissions;
	}
	
	
	
}
