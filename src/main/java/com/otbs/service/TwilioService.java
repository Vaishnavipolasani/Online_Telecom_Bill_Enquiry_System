package com.otbs.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class TwilioService {

//    @Value("${twilio.account.sid}")
    private String accountSid = "ABC@gmail.com";

//    @Value("${twilio.auth.token}")
    private String authToken = "xyz";

//    @Value("${twilio.phone.number}")
    private String fromPhoneNumber="7529989668";

    @PostConstruct
    public void initTwilio() {
        Twilio.init(accountSid, authToken);
    }

    public void sendSms(String toPhoneNumber, String message) {
        Message.creator(
                new PhoneNumber(toPhoneNumber),
                new PhoneNumber(fromPhoneNumber),
                message
        ).create();
    }
}
