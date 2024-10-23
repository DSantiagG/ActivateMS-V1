package com.activate.ActivateMSV1.user_management_ms.service;

import com.activate.ActivateMSV1.user_management_ms.domain.Location;
import com.activate.ActivateMSV1.user_management_ms.domain.User;
import com.activate.ActivateMSV1.user_management_ms.infra.dto.*;
import com.activate.ActivateMSV1.user_management_ms.infra.mappers.UserAdapter;
import com.activate.ActivateMSV1.user_management_ms.infra.repository.UserRepository;
import com.activate.ActivateMSV1.user_management_ms.infra.repository.model.ModUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAdapter userAdapter;

    @Autowired
    private UserPublisherService userPublisherService;

    public void createUser(String name, int age, String email, Set<InterestDTO> interests, LocationDTO locationDTO) throws Exception {
        Location location = new Location(locationDTO.getLatitude(), locationDTO.getLongitude());
        HashSet<InterestDTO> interestsSet = new HashSet<>(interests);
        User user = new User(-1L, name, age, email, interestsSet,location);
        ModUser userMapped = userAdapter.mapDomainUserToModel(user);
        userRepository.save(userMapped);
        userPublisherService.publishUserToEvent(userMapped);
        userPublisherService.publishUserToRecommendation(userMapped);
    }

    public UserDTO getUserById(Long id) throws Exception {
        ModUser user = userRepository.findById(id).orElseThrow(() -> new Exception("Usuario no encontrado"));
        return userAdapter.mapModelUserToDTO(user);
    }

    public ArrayList<UserDTO> getUsers(){
        ArrayList<UserDTO> users = new ArrayList<>();
        for (ModUser user : userRepository.findAll()) {
            users.add(userAdapter.mapModelUserToDTO(user));
        }
        return users;
    }

    public void editProfile(Long id, String name, int age, String email) throws Exception {
        ModUser userModel = userRepository.findById(id).orElseThrow(() -> new Exception("Usuario no encontrado"));
        User userDomain = userAdapter.mapModelUserToDomain(userModel);
        userDomain.editProfile(name, age, email);
        ModUser userMapped = userAdapter.mapDomainUserToModel(userDomain);
        userRepository.save(userMapped);
        userPublisherService.publishUserToEvent(userMapped);
        userPublisherService.publishUserToRecommendation(userMapped);

    }

    public void addInterest(Long id, InterestDTO interest) throws Exception {
        ModUser userModel = userRepository.findById(id).orElseThrow(() -> new Exception("Usuario no encontrado"));
        User userDomain = userAdapter.mapModelUserToDomain(userModel);
        userDomain.addInterest(interest);
        ModUser userMapped = userAdapter.mapDomainUserToModel(userDomain);

        userRepository.save(userMapped);
        userPublisherService.publishUserToEvent(userMapped);
        userPublisherService.publishUserToRecommendation(userMapped);
    }

    public void deleteInterest(Long id, InterestDTO interest) throws Exception {
        ModUser userModel = userRepository.findById(id).orElseThrow(() -> new Exception("Usuario no encontrado"));
        User userDomain = userAdapter.mapModelUserToDomain(userModel);
        userDomain.deleteInterest(interest);
        ModUser userMapped = userAdapter.mapDomainUserToModel(userDomain);

        userRepository.save(userMapped);
        userPublisherService.publishUserToEvent(userMapped);
        userPublisherService.publishUserToRecommendation(userMapped);
    }

    public void udpateLocation(Long id, LocationDTO location) throws Exception {
        ModUser userModel = userRepository.findById(id).orElseThrow(() -> new Exception("Usuario no encontrado"));
        User userDomain = userAdapter.mapModelUserToDomain(userModel);
        userDomain.updateLocation(new Location(location.getLatitude(), location.getLongitude()));
        ModUser userMapped = userAdapter.mapDomainUserToModel(userDomain);

        userRepository.save(userMapped);
        userPublisherService.publishUserToEvent(userMapped);
        userPublisherService.publishUserToRecommendation(userMapped);
    }


}
