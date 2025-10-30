package com.nutech.simsppob.model;

public class PpobService {
    private Long id;
    private String serviceCode;
    private String serviceName;
    private Long serviceTariff;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getServiceCode() { return serviceCode; }
    public void setServiceCode(String serviceCode) { this.serviceCode = serviceCode; }

    public String getServiceName() { return serviceName; }
    public void setServiceName(String serviceName) { this.serviceName = serviceName; }

    public Long getServiceTariff() { return serviceTariff; }
    public void setServiceTariff(Long serviceTariff) { this.serviceTariff = serviceTariff; }
}
