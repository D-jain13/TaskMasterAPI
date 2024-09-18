package com.dhairya.taskMaster.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.dhairya.taskMaster.entity.Employee;
import com.dhairya.taskMaster.repository.EmpRepo;

public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	private EmpRepo empRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<Employee> emp = empRepo.findByEmail(username);
		
		return emp.map(CustomUserDetails :: new).orElseThrow(() -> new UsernameNotFoundException("Employee not found with " +username));
	}

}


