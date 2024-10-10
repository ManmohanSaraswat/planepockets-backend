package com.planepockets.proton.service;

import com.planepockets.proton.model.Role;
import com.planepockets.proton.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
	@Autowired
	private RoleRepository roleRepository;

	static final Logger log = LoggerFactory.getLogger(RoleService.class);

	public Role createNewRole(Role role) {
		log.info("New Role added successfully {}", role);
		return roleRepository.save(role);
	}
}
