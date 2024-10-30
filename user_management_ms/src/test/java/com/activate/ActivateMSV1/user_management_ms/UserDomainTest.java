package com.activate.ActivateMSV1.user_management_ms;

import com.activate.ActivateMSV1.user_management_ms.infra.dto.InterestDTO;
import com.activate.ActivateMSV1.user_management_ms.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class UserDomainTest {
    private User user;
    private HashSet<InterestDTO> interests;
    private Location location;

    @BeforeEach
    void setUp() throws Exception {
        interests = new HashSet<>();
        interests.add(InterestDTO.CINEMA);
        interests.add(InterestDTO.MUSIC);
        interests.add(InterestDTO.SPORTS);
        location = new Location(40L, -3L);
        user = new User(1L, "Pepe", 20, "pepe@gmail.com", interests, location);
    }

    @Test
    void testConstructor() {
        assertNotNull(user);
        assertEquals(1L, user.getId());
        assertEquals("Pepe", user.getName());
        assertEquals(20, user.getAge());
        assertEquals("pepe@gmail.com", user.getEmail());
        assertEquals(interests, user.getInterests());
        assertEquals(location, user.getLocation());
    }

    @Test
    void testConstructorInsufficientInterests() {
        HashSet<InterestDTO> pocosIntereses = new HashSet<>();
        pocosIntereses.add(InterestDTO.CINEMA);
        pocosIntereses.add(InterestDTO.MUSIC);
        Exception exception = assertThrows(Exception.class, () -> {
            new User(1L, "Pepe", 20, "pepe@gmail.com", pocosIntereses, location);
        });
        assertEquals("Número de intereses insuficiente: El usuario debe tener al menos 3 intereses", exception.getMessage());
    }

    @Test
    void testEditProfile() throws Exception {
        assertTrue(user.editProfile("Juan", 25, "juan@gmail.com"));
        assertEquals("Juan", user.getName());
        assertEquals(25, user.getAge());
        assertEquals("juan@gmail.com", user.getEmail());
    }

    @Test
    void testEditProfileThrowsExceptionForNegativeAge() {
        Exception exception = assertThrows(Exception.class, () -> {
            user.editProfile("Juan", -1, "juan@gmail.com");
        });
        assertEquals("Edad no valida ,la edad no puede ser negativa", exception.getMessage());
    }

    @Test
    void testEditProfileThrowsExceptionForUnderage() {
        Exception exception = assertThrows(Exception.class, () -> {
            user.editProfile("Juan", 17, "juan@gmail.com");
        });
        assertEquals("Edad no válida (Debes ser mayor de edad)", exception.getMessage());
    }

    @Test
    void testEditProfileThrowsExceptionForNullEmail() {
        Exception exception = assertThrows(Exception.class, () -> {
            user.editProfile("Juan", 25, null);
        });
        assertEquals("El email ingresado no es valido", exception.getMessage());
    }

    @Test
    void testEditProfileThrowsExceptionForEmptyEmail() {
        Exception exception = assertThrows(Exception.class, () -> {
            user.editProfile("Juan", 25, "");
        });
        assertEquals("El email ingresado no es valido", exception.getMessage());
    }

    @Test
    void testEditProfileThrowsExceptionForEmailWithoutAtSymbol() {
        Exception exception = assertThrows(Exception.class, () -> {
            user.editProfile("Juan", 25, "juangmail.com");
        });
        assertEquals("El email ingresado no es valido", exception.getMessage());
    }

    @Test
    void testEditProfileThrowsExceptionForEmailWithoutDomain() {
        Exception exception = assertThrows(Exception.class, () -> {
            user.editProfile("Juan", 25, "juan@");
        });
        assertEquals("El email ingresado no es valido", exception.getMessage());
    }

    @Test
    void testEditProfileThrowsExceptionForEmailWithInvalidCharacters() {
        Exception exception = assertThrows(Exception.class, () -> {
            user.editProfile("Juan", 25, "juan@!gmail.com");
        });
        assertEquals("El email ingresado no es valido", exception.getMessage());
    }

    @Test
    void testAddInterest() throws Exception {
        assertTrue(user.addInterest(InterestDTO.TECHNOLOGY));
        assertTrue(user.getInterests().contains(InterestDTO.TECHNOLOGY));
    }

    @Test
    void testAddInterestThrowExceptionInterestAlreadyExist() throws Exception {
        Exception exception = assertThrows(Exception.class, () -> {
            user.addInterest(InterestDTO.CINEMA);
        });
        assertEquals("El interés ya ha sido agregado previamente", exception.getMessage());
    }

    @Test
    void testDeleteInterest() throws Exception {
        assertTrue(user.addInterest(InterestDTO.TECHNOLOGY));
        assertTrue(user.deleteInterest(InterestDTO.TECHNOLOGY));
        assertFalse(user.getInterests().contains(InterestDTO.TECHNOLOGY));
    }

    @Test
    void testDeleteInterestThrowExceptionInsufficientInterests() throws Exception {
        Exception exception = assertThrows(Exception.class, () -> {
            user.deleteInterest(InterestDTO.CINEMA);
        });
        assertEquals("No se puede eliminar el interés. Debes tener al menos tres intereses registrados", exception.getMessage());
    }

    @Test
    void testUpdateLocation() throws Exception {
        Location nuevaUbicacion = new Location(41L, -4L);
        assertTrue(user.updateLocation(nuevaUbicacion));
        assertEquals(nuevaUbicacion, user.getLocation());
    }

    @Test
    void testUpdateLocationThrowsExceptionForSameLocation() {
        Exception exception = assertThrows(Exception.class, () -> {
            user.updateLocation(location);
        });
        assertEquals("La ubicación actual no ha cambiado. No es necesario actualizarla.", exception.getMessage());
    }

    @Test
    void testUpdateLocationWithDifferentLocation() throws Exception {
        Location nuevaUbicacion = new Location(41L, -4L);
        assertTrue(user.updateLocation(nuevaUbicacion));
        assertEquals(nuevaUbicacion, user.getLocation());
    }
}
