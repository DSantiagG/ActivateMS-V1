package com.activate.ActivateMSV1.user_management_ms.infra.mappers;

import com.activate.ActivateMSV1.user_management_ms.domain.User;
import com.activate.ActivateMSV1.user_management_ms.domain.Location;
import com.activate.ActivateMSV1.user_management_ms.infra.dto.*;
import com.activate.ActivateMSV1.user_management_ms.infra.repository.*;
import com.activate.ActivateMSV1.user_management_ms.infra.repository.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserAdapter {
    @Autowired
    private UserRepository userRepository;

    public ModUser mapDomainUserToModel(User user) {
        ModUser userMapped = userRepository.findById(user.getId())
                .orElse(new ModUser());

        userMapped.setName(user.getName());
        userMapped.setAge(user.getAge());
        userMapped.setEmail(user.getEmail());
        Set<InterestDTO> interestsMapped = new HashSet<>();
        for (InterestDTO interes : user.getInterests()) {
            interestsMapped.add(InterestDTO.valueOf(interes.toString()));
        }
        userMapped.setInterests(interestsMapped);
        userMapped.setLocation(new ModLocation(user.getLocation().getLatitude(), user.getLocation().getLongitude()));
        return userMapped;
    }

    public User mapModelUserToDomain (ModUser user) throws Exception {
        HashSet<InterestDTO> interestsMapped = new HashSet<>();
        for (InterestDTO interesMapped : user.getInterests()) {
            interestsMapped.add(InterestDTO.valueOf(interesMapped.toString()));
        }
        return new User(user.getId(), user.getName(), user.getAge(), user.getEmail(),interestsMapped, new Location(user.getLocation().getLatitude(), user.getLocation().getLength()));
    }

    public UserDTO mapModelUserToDTO(ModUser user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setAge(user.getAge());
        userDTO.setEmail(user.getEmail());
        Set<InterestDTO> interestsDTO = new HashSet<>();
        for (InterestDTO interes : user.getInterests()) {
            interestsDTO.add(InterestDTO.valueOf(interes.toString()));
        }
        userDTO.setInterests(interestsDTO);
        userDTO.setLocation(new LocationDTO(user.getLocation().getLatitude(), user.getLocation().getLength()));
        return userDTO;
    }



}
