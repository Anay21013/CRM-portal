package com.erp.core.role;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.erp.core.exceptions.BusinessException;
import com.erp.core.permission.Permission;
import com.erp.core.permission.PermissionEntity;
import com.erp.core.permission.PermissionRepository;
import com.erp.core.role.permission.RolePermission;
import com.erp.core.role.permission.RolePermissionEntity;
import com.erp.core.role.permission.RolePermissionRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@Transactional
public class RoleServiceImpl implements IRoleService {

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PermissionRepository permissionRepository;
	
	@Autowired
	private RolePermissionRepository rolePermissionRepository;


	@Override
	public List<Role> getRoleList() {
		log.debug("Fetch All Roles");
		List<RoleEntity> roleEntityList = roleRepository.findAllByDeletedFalse();
		List<Role> roleList = new ArrayList<Role>();

		roleEntityList.forEach(roleEntity -> {
			Role role = new Role(roleEntity);
			if(roleEntity.getParentRole()!=null) { role.setParentRole(getRole(roleEntity.getParentRole()));}
			roleList.add(role);
		});
		return roleList;

	}
	
	@Override
	public Role getRole(Long id) {
		log.debug("Fetch Role with {} ",id);
		RoleEntity roleEntity = roleRepository.findById(id).get();
		Role role =  new Role(roleEntity);
		if(roleEntity.getParentRole()!=null) { role.setParentRole(getRole(roleEntity.getParentRole()));}
		return role;
	}
	
	
	@Override
	public Role getRoleDetail(Long id) {
		log.debug("Fetch Role details with {} ",id);
		RoleEntity roleEntity = roleRepository.findById(id).get();
		Role role =  new Role(roleEntity);
		if(roleEntity.getParentRole()!=null) { role.setParentRole(getRoleDetail(roleEntity.getParentRole()));}
		role.setPermissions(getAllPermissionForRole(id));
		return role;
	}
	
	@Override
	public Role getRoleByName(String name) {
		log.debug("Fetch Role with name: "+name);
		RoleEntity roleEntity = roleRepository.findByNameAndDeletedFalse(name).get();
		Role role =  new Role(roleEntity);
		if(roleEntity.getParentRole()!=null) { role.setParentRole(getRole(roleEntity.getParentRole()));}
		return role;
	}

	@Override
	public Role addRole(Role role) {
		log.debug("Add new Role..");
		Optional<RoleEntity> existingroleEntity = roleRepository.findByNameAndDeletedFalse(role.getName());
		if(existingroleEntity.isPresent()) {
			throw new BusinessException("Role already exists.");
		}
		RoleEntity roleEntity = role.populateRoleEntity();
		roleEntity = roleRepository.save(roleEntity);
		return new Role(roleEntity);
	}

	@Override
	public Role deleteRole(Long id) {
		log.debug("Delete Role with {} ",id);
		RoleEntity workExperienceEntity = roleRepository.findById(id).get();
		workExperienceEntity.setDeleted(true);
		workExperienceEntity = roleRepository.save(workExperienceEntity);
		return new Role(workExperienceEntity);
	}

	@Override
	public Role updateRole(Role role) {
		log.debug("Updating Role with id: "+role.getId());
		RoleEntity roleEntityFromDB = roleRepository.findById(role.getId()).get();
		RoleEntity roleEntity = role.populateRoleEntity();
		
		if(!roleEntityFromDB.equals(roleEntity)) {
			roleEntityFromDB.setDescription(roleEntity.getDescription());
			roleEntityFromDB.setName(roleEntity.getName());
			roleEntityFromDB.setParentRole(roleEntity.getParentRole());
			roleEntityFromDB.setEffectiveDate(roleEntity.getEffectiveDate());
			
			roleEntityFromDB = roleRepository.save(roleEntityFromDB);
		}
		
		return new Role(roleEntityFromDB);
	}
	
	public List<Permission> getAllPermission(){
		log.debug("Fetch All Permissions");
		List<PermissionEntity> allPermission = permissionRepository.getByDeletedFalse(); 
		List<Permission> permissions = new ArrayList<>();
		allPermission.forEach(permissionEntity -> {
			permissions.add(new Permission(permissionEntity));
		});
		return permissions;
	} 
	
	public List<Permission> getPermissionForRole(Long id){
		log.debug("Fetch All Permissions for a role");
		List<RolePermissionEntity> rolePermissionEntityList = rolePermissionRepository.findByRoleIdAndDeletedFalse(List.of(id));
		List<Permission> permissions = new ArrayList<>(); 
		for(RolePermissionEntity rolePermissionEntity : rolePermissionEntityList) {
			Optional<PermissionEntity> permissionEntity = permissionRepository.findById(rolePermissionEntity.getPermissionId());
			Permission permission = new Permission(permissionEntity.get());
			permissions.add(permission);
		}
		return permissions;
	}
	
	//Get Permissions For Parents as well
	@Override
	public List<Permission> getAllPermissionForRole(Long roleId){
		log.debug("Fetch All Permissions for a role and parent role");
		Set<Long> finalRoleIdSet = new HashSet<>();
		Set<Long> roleIdSet = new HashSet<>();

		roleIdSet.add(roleId);
		finalRoleIdSet.addAll(roleIdSet);

		while (!roleIdSet.isEmpty()) {
			Role role = getRole(new ArrayList<>(roleIdSet).get(0));
			roleIdSet.clear();
			if(role.getParentRole()!=null) {roleIdSet.add(role.getParentRole().getId());
			finalRoleIdSet.add(role.getParentRole().getId());}
		}
		log.debug("Fetch All Role Permissions with role Id Set");
		List<RolePermissionEntity> rolePermissionEntityList = rolePermissionRepository.findByRoleIdAndDeletedFalse(finalRoleIdSet.stream().toList());
		List<Permission> permissions = new ArrayList<>(); 
		for(RolePermissionEntity rolePermissionEntity : rolePermissionEntityList) {
			Optional<PermissionEntity> permissionEntity = permissionRepository.findById(rolePermissionEntity.getPermissionId());
			Permission permission = new Permission(permissionEntity.get());
			permissions.add(permission);
		}
		return permissions;
	}
	
	@Override
	public List<Permission> getAllPermissionForRoleName(String roleName){
		Role role = getRoleByName(roleName);
		return getAllPermissionForRole(role.getId());
	}
	
	@Override
	public List<RolePermission> addRolePermissions(Long roleId,List<Long> permissionIdList){
		log.debug("Add permission for role id: "+roleId);
		List<RolePermission> permissionAddedForRole = new ArrayList<>();
		for(Long permissionId : permissionIdList ) {
			RolePermissionEntity rolePermissionEntity = new RolePermissionEntity();
			rolePermissionEntity.setRoleId(roleId);
			rolePermissionEntity.setPermissionId(permissionId);
			rolePermissionEntity = rolePermissionRepository.save(rolePermissionEntity);
			permissionAddedForRole.add(new RolePermission(rolePermissionEntity));
		}
		return permissionAddedForRole;
	}
	
	@Override
	public RolePermission deleteRolePermission(Long roleId,Long permissionId) {
		log.debug("Delete permission with permission {} ",permissionId+" and Role {} ",roleId);
		RolePermissionEntity rolePermissionEntity = rolePermissionRepository.findByRoleIdAndPermissionIdAndDeletedFalse(roleId, permissionId);
		rolePermissionEntity.setDeleted(true);
		rolePermissionEntity = rolePermissionRepository.save(rolePermissionEntity);
		return new RolePermission(rolePermissionEntity);
	}

	@Override
	public List<String> getAllPermissionNamesForRole(Long roleId) {
		log.debug("Fetch All Permissions for a role and parent role");
		Set<Long> finalRoleIdSet = new HashSet<>();
		Set<Long> roleIdSet = new HashSet<>();

		roleIdSet.add(roleId);
		finalRoleIdSet.addAll(roleIdSet);

		while (!roleIdSet.isEmpty()) {
			Role role = getRole(new ArrayList<>(roleIdSet).get(0));
			roleIdSet.clear();
			if(role.getParentRole()!=null) {roleIdSet.add(role.getParentRole().getId());
			finalRoleIdSet.add(role.getParentRole().getId());}
		}
		log.debug("Fetch All Role Permissions with role Id Set");
		List<RolePermissionEntity> rolePermissionEntityList = rolePermissionRepository.findByRoleIdAndDeletedFalse(finalRoleIdSet.stream().toList());
		List<String> permissionNamesList = new ArrayList<>(); 
		for(RolePermissionEntity rolePermissionEntity : rolePermissionEntityList) {
			Optional<PermissionEntity> permissionEntity = permissionRepository.findById(rolePermissionEntity.getPermissionId());
			Permission permission = new Permission(permissionEntity.get());
			permissionNamesList.add(permission.getName());
		}
		return permissionNamesList;
	}
	
}
