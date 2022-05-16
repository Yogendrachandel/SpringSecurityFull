package com.learn.userrole;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.learn.userpermission.ApplicationUserPermission;

public enum ApplicationUserRole {
	// we have define two role STUDENT,(no permission) and
	//ADMIN(COURSE_READ,COURSE_WRITE,STUDENT_READ,STUDENT_WRITE) 4 permission
   // now we have added another role ADMIN_TRANIEE (COURSE_READ ,STUDENT_READ) permission.
	 STUDENT(ApplicationUserRole.rolePermissionListforStudent())
	,ADMIN(ApplicationUserRole.rolePermissionListforAdmin()),
	ADMIN_TRANIEE(ApplicationUserRole.rolePermissionListforAdmin_trainee());
	
	
	private final Set<ApplicationUserPermission>permission;
	
	
	private ApplicationUserRole(Set<ApplicationUserPermission> permission) {
		System.out.println(" ApplicationUserRole constructor is called: "+ permission);
		this.permission = permission;
	}


	//This if for studenet .we have not give any permissions to student .
	public static  Set<ApplicationUserPermission> rolePermissionListforStudent(){
		System.out.println(" Permssion is going to set for student");
		return new HashSet<>();
	}
	
	
	//This if for Admin .we have give 4 permissions to admin .
	public static  Set<ApplicationUserPermission> rolePermissionListforAdmin(){
		Set<ApplicationUserPermission> appUserPermission=new HashSet<>();
		
		appUserPermission.add(ApplicationUserPermission.COURSE_READ);
		appUserPermission.add(ApplicationUserPermission.COURSE_WRITE);
		appUserPermission.add(ApplicationUserPermission.STUDENT_READ);
		appUserPermission.add(ApplicationUserPermission.STUDENT_WRITE);
		
		System.out.println(" Permssion is going to set for ADMIN: "+appUserPermission);
		return appUserPermission;
		
		
	}
	
	
	
	  //This if for Admin_TRANIEE .we have give 2 permissions to admin .
		public static  Set<ApplicationUserPermission> rolePermissionListforAdmin_trainee(){
			Set<ApplicationUserPermission> appUserPermission=new HashSet<>();
			
			appUserPermission.add(ApplicationUserPermission.COURSE_READ);
			appUserPermission.add(ApplicationUserPermission.STUDENT_READ);
			
			System.out.println(" Permssion is going to set for ADMIN_TRAINEE: "+appUserPermission);
			return appUserPermission;
		}


		public Set<ApplicationUserPermission> getPermission() {
			return permission;
		}
	
	
	//This Permission Based authntication ,SimpleGrantedAuthority is implementation class for GrantedAuthority interface 
	public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
		
        Set<SimpleGrantedAuthority> permissions = getPermission().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermissions()))
                .collect(Collectors.toSet());
        
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        
        System.out.println("SimpleGrantedAuthority permissions:: "+permissions);
        return permissions;
    }
	

}
