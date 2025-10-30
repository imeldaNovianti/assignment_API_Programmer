package com.nutech.simsppob.model;

public class ServiceItem {
    private String service_code;
    private String service_name;
    private String service_icon;
    private int service_tariff;

    public ServiceItem(String service_code, String service_name, String service_icon, int service_tariff) {
        this.service_code = service_code;
        this.service_name = service_name;
        this.service_icon = service_icon;
        this.service_tariff = service_tariff;
    }

    public String getService_code() {
        return service_code;
    }

    public String getService_name() {
        return service_name;
    }

    public String getService_icon() {
        return service_icon;
    }

    public int getService_tariff() {
        return service_tariff;
    }
}
