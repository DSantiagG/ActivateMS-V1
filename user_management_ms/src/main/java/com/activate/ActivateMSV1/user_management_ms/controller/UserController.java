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

    /**
     * Creates a new user.
     *
     * Accepts a `UserDTO` in the request body and uses `UserService` to create and save the user.
     * Returns a `201 Created` status on success, or `400 Bad Request` with an error message on failure.
     *
     * @param userDTO The user's data (name, age, email, interests, location).
     * @return `201 Created` on success, or `400 Bad Request` on error.
     */
    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserDTO userDTO) {
        try {
            userService.createUser(userDTO.getName(), userDTO.getAge(), userDTO.getEmail(), userDTO.getInterests(), userDTO.getLocation());
            System.out.println("Usuario creado exitosamente");
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuario creado exitosamente");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Retrieves a user by their ID.
     *
     * Fetches a `UserDTO` by ID from the `UserService`.
     * Returns `200 OK` with the user data, or `404 Not Found` if the user doesn't exist.
     *
     * @param id The user's ID.
     * @return `200 OK` with the user data, or `404 Not Found` if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        try {
            UserDTO user = userService.getUserById(id);
            System.out.println("Usuario"+id +" encontrado");
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            System.out.println("Usuario"+id +" NO encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Retrieves all users.
     *
     * Fetches a list of all `UserDTO` from the `UserService`.
     * Returns `200 OK` with the list of users, or `400 Bad Request` in case of an error.
     *
     * @return `200 OK` with the list of users, or `400 Bad Request` on error.
     */
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        try {
            List<UserDTO> users = userService.getUsers();
            System.out.println("Usuarios encontrados");
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }


    /**
     * Updates the profile of an existing user.
     *
     * Modifies the user profile identified by the provided `id` using the information from `UserDTO`.
     * Returns `200 OK` if the update is successful, or `400 Bad Request` in case of an error.
     *
     * @param id The ID of the user whose profile is being updated.
     * @param userDTO The user data to update (name, age, email).
     * @return `200 OK` if successful, or `400 Bad Request` on error.
     */
    @PutMapping("/{id}/profile")
    public ResponseEntity<String> editProfile(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        try {
            userService.editProfile(id, userDTO.getName(), userDTO.getAge(), userDTO.getEmail());
            System.out.println("Perfil actualiado exitosamente");
            return ResponseEntity.ok("Perfil actualizado exitosamente");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Adds a new interest to the user.
     *
     * Adds an interest to the user identified by the provided `id` using the `InterestRequestDTO` data.
     * Returns `200 OK` if the interest is added successfully, or `400 Bad Request` in case of an error.
     *
     * @param id The ID of the user to whom the interest will be added.
     * @param interestDTO The interest to be added.
     * @return `200 OK` if successful, or `400 Bad Request` on error.
     */
    @PutMapping("/{id}/interests/add")
    public ResponseEntity<String> addInterest(@PathVariable Long id, @RequestBody InterestRequestDTO interestDTO) {
        try {
            userService.addInterest(id, interestDTO.getInterest());
            System.out.printf("Interés agregado exitosamente");
            return ResponseEntity.ok("Interés agregado exitosamente");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Deletes an interest from the user.
     *
     * Removes an interest from the user identified by the provided `id` using the `InterestRequestDTO` data.
     * Returns `200 OK` if the interest is deleted successfully, or `400 Bad Request` in case of an error.
     *
     * @param id The ID of the user from whom the interest will be removed.
     * @param interestDTO The interest to be removed.
     * @return `200 OK` if successful, or `400 Bad Request` on error.
     */
    @PutMapping("/{id}/interests/remove")
    public ResponseEntity<String> deleteInterest(@PathVariable Long id, @RequestBody InterestRequestDTO interestDTO) {
        try {
            userService.deleteInterest(id, interestDTO.getInterest());
            System.out.printf("Interés eliminado exitosamente");
            return ResponseEntity.ok("Interés eliminado exitosamente");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    /**
     * Updates the location of the user.
     *
     * Updates the location of the user identified by the provided `id` using the `LocationDTO` data.
     * Returns `200 OK` if the location is updated successfully, or `400 Bad Request` in case of an error.
     *
     * @param id The ID of the user whose location will be updated.
     * @param locationDTO The new location data.
     * @return `200 OK` if successful, or `400 Bad Request` on error.
     */
    @PutMapping("/{id}/location")
    public ResponseEntity<String> updateLocation(@PathVariable Long id, @RequestBody LocationDTO locationDTO) {
        try {
            userService.udpateLocation(id, locationDTO);
            System.out.printf("Ubicación actualizada");
            return ResponseEntity.ok("Ubicación actualizada");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}

