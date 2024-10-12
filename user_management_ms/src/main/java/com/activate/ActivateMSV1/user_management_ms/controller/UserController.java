package com.activate.ActivateMSV1.user_management_ms.controller;

import com.activate.ActivateMSV1.user_management_ms.infra.dto.UserDTO;
import com.activate.ActivateMSV1.user_management_ms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.activate.ActivateMSV1.user_management_ms.infra.dto.*;

import java.util.List;

@RestController
@RequestMapping("/api/activate/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserDTO userDTO) {
        try {
            userService.createUser(userDTO.getName(), userDTO.getAge(), userDTO.getEmail(), userDTO.getInterests(), userDTO.getLocation());
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuario creado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        try {
            UserDTO user = userService.getUserById(id);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        try {
            List<UserDTO> users = userService.getUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/{id}/profile")
    public ResponseEntity<String> editProfile(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        try {
            userService.editProfile(id, userDTO.getName(), userDTO.getAge(), userDTO.getEmail());
            return ResponseEntity.ok("Perfil actualizado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/interests")
    public ResponseEntity<String> addInterest(@PathVariable Long id, @RequestBody InterestRequestDTO interestDTO) {
        try {
            userService.addInterest(id, interestDTO.getInterest());
            return ResponseEntity.ok("Interés agregado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}/interests")
    public ResponseEntity<String> deleteInterest(@PathVariable Long id, @RequestBody InterestRequestDTO interestDTO) {
        try {
            userService.deleteInterest(id, interestDTO.getInterest());
            return ResponseEntity.ok("Interés eliminado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/location")
    public ResponseEntity<String> updateLocation(@PathVariable Long id, @RequestBody LocationDTO locationDTO) {
        try {
            userService.udpateLocation(id, locationDTO);
            return ResponseEntity.ok("Ubicación actualizada");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}

