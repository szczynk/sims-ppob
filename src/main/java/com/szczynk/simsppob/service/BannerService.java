package com.szczynk.simsppob.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.szczynk.simsppob.model.Banner;
import com.szczynk.simsppob.model.response.BannerResponse;
import com.szczynk.simsppob.repository.BannerRepository;

@Service
public class BannerService {

    private final BannerRepository bannerRepository;

    public BannerService(BannerRepository bannerRepository) {
        this.bannerRepository = bannerRepository;
    }

    public List<BannerResponse> getAllBanners() {
        Iterable<Banner> banners = this.bannerRepository.findAll();

        List<BannerResponse> responses = new ArrayList<>();
        banners.forEach(
                banner -> responses.add(new BannerResponse(
                        banner.getBannerName(),
                        banner.getBannerImage(),
                        banner.getDescription())));

        return responses;
    }
}
