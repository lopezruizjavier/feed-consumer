package com.sparta.feedconsumer.provider.controller;

import com.sparta.feedconsumer.provider.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class ProviderController {
    @Autowired
    private ProviderService providerService;

    @PostMapping("/load/{provider}")
    public int load(@PathVariable("provider") String providerName, @RequestBody byte[] content) {
        return providerService.loadData(providerName, content);
    }

    @GetMapping("/data/{provider}/total")
    public int total(@PathVariable("provider") String providerName) {
        return providerService.getTotalData(providerName);
    }
}
