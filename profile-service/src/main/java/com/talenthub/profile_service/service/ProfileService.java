package com.talenthub.profile_service.service;

import org.springframework.stereotype.Service;

import com.talenthub.profile_service.entity.Profile;
import com.talenthub.profile_service.repository.ProfileRepository;
import com.talenthub.profile_service.exception.EmailAlreadyExistsException;

import jakarta.transaction.Transactional;

@Service 
public class ProfileService {
    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository){
        this.profileRepository = profileRepository;
    }

    public Profile create(String email, Profile request){
        if(profileRepository.existsByEmail(email)){
            throw new EmailAlreadyExistsException("This Profile already created");
        }

        Profile newProfile = new Profile(
            email, 
            request.getFirstName(),
            request.getLastName(),
            request.getPhone(),
            request.getAddress(),
            request.getAbout(),
            request.getLinkedin(),
            request.getGithub(),
            request.getWebsite(),
            request.getProfileImageUrl()
        );

        return profileRepository.save(newProfile);
        
    }
    public Profile get(String email){
           return profileRepository.findByEmail(email)
           .orElseThrow(() -> new EmailNotFoundException("This Profile is not exist"));
    }
    @Transactional 
    public String delete(String email) {
        Profile existProfile = profileRepository.findByEmail(email)
           .orElseThrow(() -> new EmailNotFoundException("User Profile is not exist"));
        
        profileRepository.delete(existProfile);

        return email + ": Profile deleted successful";
    }
    public Profile update(String email, Profile request) {
        Profile existProfile = profileRepository.findByEmail(email)
           .orElseThrow(() -> new EmailNotFoundException("User Profile is not exist"));

        existProfile.setFirstName(request.getFirstName());
        existProfile.setLastName(request.getLastName());
        existProfile.setPhone(request.getPhone());
        existProfile.setAddress(request.getAddress());
        existProfile.setAbout(request.getAbout());
        existProfile.setLinkedin(request.getLinkedin());
        existProfile.setGithub(request.getGithub());
        existProfile.setWebsite(request.getWebsite());
        existProfile.setProfileImageUrl(request.getProfileImageUrl());

        return profileRepository.save(existProfile);
    }

}
