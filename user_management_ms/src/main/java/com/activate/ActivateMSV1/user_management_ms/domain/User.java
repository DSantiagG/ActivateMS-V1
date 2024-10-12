package com.activate.ActivateMSV1.user_management_ms.domain;

import com.activate.ActivateMSV1.user_management_ms.domain.exceptions.*;

import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {
    private Long id;
    private String name;
    private int age;
    private String email;
    private HashSet<Interest> interests;
    private Location location;

    public User(Long id, String name, int age, String email, HashSet<Interest> interests, Location location) throws Exception {
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
        String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        if (email == null) {
            return false;
        }
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean addInterest(Interest interest) throws Exception {
        if(interests.contains(interest)) throw new InterestAlreadyAddedException();
        return interests.add(interest);
    }

    public boolean deleteInterest(Interest interest) throws Exception {
        if(!interests.contains(interest)) throw new InterestNotFoundException();
        if(interests.size()-1<3) throw new CannotRemoveInterestException();
        return interests.remove(interest);
    }

    public boolean updateLocation(Location location) throws Exception {
        if(location.getLatitude()==this.location.getLatitude() && location.getLength()==this.location.getLength())
            throw new UnnecessaryLocationUpdateException();
        this.location = location;
        return true;
    }
}
