package com.nutech.simsppob.service;

import com.nutech.simsppob.model.Banner;
import com.nutech.simsppob.repository.BannerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BannerService {

    private final BannerRepository bannerRepository;

    public BannerService(BannerRepository bannerRepository) {
        this.bannerRepository = bannerRepository;
    }

    public List<Banner> getBanners() {
        return bannerRepository.getAllBanners();
    }
}
