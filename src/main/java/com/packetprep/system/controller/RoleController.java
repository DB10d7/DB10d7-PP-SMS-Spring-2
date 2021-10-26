package com.packetprep.system.controller;
import com.packetprep.system.Model.Day;
import com.packetprep.system.dto.*;
import com.packetprep.system.service.DayService;
import com.packetprep.system.service.RoleService;
import com.packetprep.system.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/role/")
@AllArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<Void> createRole(@RequestBody RoleDto roleDto) {
        roleService.save(roleDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<RoleDto>> getAllRoles() {
        return status(HttpStatus.OK).body(roleService.getAllRoles());
    }
}
