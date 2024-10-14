package com.activate.ActivateMSV1.user_management_ms.domain;

import com.activate.ActivateMSV1.user_management_ms.domain.exceptions.*;
import com.activate.ActivateMSV1.user_management_ms.infra.dto.InterestDTO;
import lombok.Getter;

import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
public class User {
    private Long id;
    private String name;
    private int age;
    private String email;
    private HashSet<InterestDTO> interests;
    private Location location;
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    public User(Long id, String name, int age, String email, HashSet<InterestDTO> interests, Location location) throws Exception {
        this.id = id;
        this.name = name;
        if(age <0) throw new NegativeAgeException();
        if(age <18) throw new InvalidAgeException();
        this.age = age;
        this.email = email;
        if(interests.size()<3) throw new InsufficientInterestsException();
        this.interests = interests;
        this.location = location;
    }

    public boolean editProfile(String name, int age, String email) throws Exception {
        if(age<0) throw new NegativeAgeException();
        if(age<18) throw new  InvalidAgeException();
        if(!isValidEmail(email)) throw new InvalidEmailException();
        this.name =name;
        this.age =age;
        this.email=email;
        return true;
    }

    private boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean addInterest(InterestDTO interest) throws Exception {
        if(interests.contains(interest)) throw new InterestAlreadyAddedException();
        return interests.add(interest);
    }

    public boolean deleteInterest(InterestDTO interest) throws Exception {
        if(!interests.contains(interest)) throw new InterestNotFoundException();
        if(interests.size()-1<3) throw new CannotRemoveInterestException();
        return interests.remove(interest);
    }

    public boolean updateLocation(Location location) throws Exception {
        if(location.getLatitude()==this.location.getLatitude() && location.getLongitude()==this.location.getLongitude())
            throw new UnnecessaryLocationUpdateException();
        this.location = location;
        return true;
    }
}
