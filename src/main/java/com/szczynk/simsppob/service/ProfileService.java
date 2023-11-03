package com.szczynk.simsppob.service;

import org.springframework.stereotype.Service;

import com.szczynk.simsppob.model.User;
import com.szczynk.simsppob.model.request.ProfileUpdateRequest;
import com.szczynk.simsppob.model.response.ProfileResponse;
import com.szczynk.simsppob.repository.UserRepository;

@Service
public class ProfileService {

    private UserRepository userRepository;

    public ProfileService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ProfileResponse getProfile(String email) {

        User user = userRepository.findByEmail(email).orElse(null);

        return ProfileResponse
                .builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .profileImage(user.getProfileImage())
                .build();
    }

    public ProfileResponse updateProfile(String email, ProfileUpdateRequest request) {
        User user = userRepository.findByEmail(email).orElse(null);

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        userRepository.save(user);

        return ProfileResponse
                .builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .profileImage(user.getProfileImage())
                .build();
    }

    public ProfileResponse updateProfileImage(String email, String uri) {
        User user = userRepository.findByEmail(email).orElse(null);

        user.setProfileImage(uri);
        userRepository.save(user);

        return ProfileResponse
                .builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .profileImage(user.getProfileImage())
                .build();
    }

}
