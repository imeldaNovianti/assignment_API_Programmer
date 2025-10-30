package com.nutech.simsppob.service;

import com.nutech.simsppob.model.ServiceItem;
import com.nutech.simsppob.repository.ServicesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicesService {

    private final ServicesRepository repository;

    public ServicesService(ServicesRepository repository) {
        this.repository = repository;
    }

    public List<ServiceItem> getServices() {
        return repository.getAllServices();
    }
}
