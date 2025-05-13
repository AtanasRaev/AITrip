package com.aitrip.utils;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!test") // Don't run during tests
public class Init implements CommandLineRunner {

    private final PhoneNumberUtil phoneNumberUtil;

    public Init(PhoneNumberUtil phoneNumberUtil) {
        this.phoneNumberUtil = phoneNumberUtil;
    }

    @Override
    public void run(String... args) throws Exception {
        this.phoneNumberUtil.validatePhoneNumber("+4915225340782");
    }
}
