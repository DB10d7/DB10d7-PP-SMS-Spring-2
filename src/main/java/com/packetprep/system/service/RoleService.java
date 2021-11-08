package com.packetprep.system.service;
import com.packetprep.system.Model.Batch;
import com.packetprep.system.Model.Day;
import com.packetprep.system.Model.Role;
import com.packetprep.system.Model.User;
import com.packetprep.system.dto.DayRequest;
import com.packetprep.system.dto.DayResponse;
import com.packetprep.system.dto.RoleDto;
import com.packetprep.system.exception.BatchNotFoundException;
import com.packetprep.system.exception.DayNotFoundException;
import com.packetprep.system.exception.RoleNotFoundException;
import com.packetprep.system.mapper.DayMapper;
import com.packetprep.system.mapper.RoleMapper;
import com.packetprep.system.repository.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public String save(RoleDto roleDto) {
        try{
            Role role = roleRepository.findByRoleName(roleDto.getRoleName())
                    .orElseThrow(() -> new RoleNotFoundException(roleDto.getRoleName()));
            return "Role Already Created";
        }catch (Exception RoleNotFoundException){
            roleRepository.save(roleMapper.mapFromDtoToRole(roleDto));
            return "Role Successfully Created";
        }

    }

    @Transactional(readOnly = true)
    public RoleDto getRole(String roleName) {
        Role role = roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new RoleNotFoundException(roleName));
        return roleMapper.mapFromRoleToDto(role);
    }
    @Transactional(readOnly = true)
    public List<RoleDto> getAllRoles() {
        return roleRepository.findAll()
                .stream()
                .map(roleMapper::mapFromRoleToDto)
                .collect(toList());
    }
}
