package com.szczynk.simsppob.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.szczynk.simsppob.model.response.ServiceResponse;
import com.szczynk.simsppob.repository.ServiceRepository;

@Service
public class ServiceService {

    private final ServiceRepository serviceRepository;

    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    public List<ServiceResponse> getAllServices() {
        Iterable<com.szczynk.simsppob.model.Service> services = this.serviceRepository.findAll();

        List<ServiceResponse> responses = new ArrayList<>();
        services.forEach(
                service -> responses.add(new ServiceResponse(
                        service.getServiceCode(),
                        service.getServiceName(),
                        service.getServiceImage(),
                        service.getServiceTariff())));

        return responses;
    }
}
