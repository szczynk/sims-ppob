package com.szczynk.simsppob.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.szczynk.simsppob.model.response.BannerResponse;
import com.szczynk.simsppob.model.response.WebResponse;
import com.szczynk.simsppob.service.BannerService;

@RestController
@RequestMapping("/banner")
public class BannerController {

    private final BannerService bannerService;

    public BannerController(BannerService bannerService) {
        this.bannerService = bannerService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping()
    public WebResponse<List<BannerResponse>> getAllBanner() {

        List<BannerResponse> response = bannerService.getAllBanners();

        return WebResponse.<List<BannerResponse>>builder()
                .status(0)
                .message("Sukses")
                .data(response)
                .build();
    }

}
