package com.szczynk.simsppob.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.szczynk.simsppob.model.response.ServiceResponse;
import com.szczynk.simsppob.model.response.WebResponse;
import com.szczynk.simsppob.service.ServiceService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/services")
@SecurityRequirement(name = "Bearer Authentication")
public class ServiceController {

    private final ServiceService serviceService;

    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @GetMapping()
    public WebResponse<List<ServiceResponse>> getAllService() {

        List<ServiceResponse> response = serviceService.getAllServices();

        return WebResponse.<List<ServiceResponse>>builder()
                .status(0)
                .message("Sukses")
                .data(response)
                .build();
    }

}
