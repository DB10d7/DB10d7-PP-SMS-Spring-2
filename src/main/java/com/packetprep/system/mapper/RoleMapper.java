package com.packetprep.system.mapper;
import com.packetprep.system.Model.Role;
import com.packetprep.system.dto.RoleDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public class RoleMapper {

    public Role mapFromDtoToRole(RoleDto roleDto) {
        Role role = new Role();
        role.setRoleName(roleDto.getRoleName());
        return role;
    }
    public RoleDto mapFromRoleToDto(Role role){
        RoleDto roleDto = new RoleDto();
        roleDto.setRoleId(role.getRoleId());
        roleDto.setRoleName(role.getRoleName());
        return roleDto;
    }
}
