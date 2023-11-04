package com.szczynk.simsppob.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.szczynk.simsppob.exception.BadRequest;
import com.szczynk.simsppob.model.request.ProfileUpdateRequest;
import com.szczynk.simsppob.model.response.ProfileResponse;
import com.szczynk.simsppob.model.response.WebResponse;
import com.szczynk.simsppob.service.ProfileService;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@RestController
@RequestMapping("/profile")
@SecurityRequirement(name = "Bearer Authentication")
public class ProfileController {

    private final ProfileService profileService;

    @Value("${minio.bucketName}")
    private String minioBucketName;

    @Value("${minio.publicUrl}")
    private String minioPublicUrl;

    @Value("${minio.profileImageFolderName}")
    private String minioProfileImageFolderName;

    private final MinioClient minioClient;

    public ProfileController(ProfileService profileService, MinioClient minioClient) {
        this.profileService = profileService;
        this.minioClient = minioClient;
    }

    // Here, the @AuthenticationPrincipal annotation injects the currently
    // authenticated user’s UserDetails into the method. This annotation helps
    // resolve Authentication.getPrincipal() to a method argument.
    //
    // https://www.baeldung.com/get-user-in-spring-security
    @ResponseStatus(HttpStatus.OK)
    @GetMapping()
    public WebResponse<ProfileResponse> getProfile(@AuthenticationPrincipal UserDetails userDetails) {

        // using email
        ProfileResponse response = profileService.getProfile(userDetails.getUsername());

        return WebResponse.<ProfileResponse>builder()
                .status(0)
                .message("Sukses")
                .data(response)
                .build();
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/update")
    public WebResponse<ProfileResponse> updateProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody ProfileUpdateRequest request) {
        ProfileResponse response = profileService.updateProfile(userDetails.getUsername(), request);

        return WebResponse.<ProfileResponse>builder()
                .status(0)
                .message("Sukses")
                .data(response)
                .build();
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(path = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public WebResponse<ProfileResponse> updateProfileImage(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam("file") MultipartFile file) {

        if (file == null) {
            throw new BadRequest("Format Image tidak sesuai");
        }

        try (InputStream inputStream = file.getInputStream()) {

            String contentType = file.getContentType();
            if (contentType != null &&
                    !contentType.equals("image/jpeg") &&
                    !contentType.equals("image/png")) {
                throw new BadRequest("Format Image tidak sesuai");
            }

            String pathName = String.format(
                    "/%s/%s",
                    this.minioProfileImageFolderName,
                    file.getOriginalFilename());

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(this.minioBucketName)
                            .object(pathName)
                            .stream(inputStream, inputStream.available(), -1)
                            .build());

            String uri = this.minioPublicUrl + "/" + this.minioBucketName + pathName;

            ProfileResponse response = profileService.updateProfileImage(userDetails.getUsername(), uri);

            return WebResponse.<ProfileResponse>builder()
                    .status(0)
                    .message("Sukses")
                    .data(response)
                    .build();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }
}
