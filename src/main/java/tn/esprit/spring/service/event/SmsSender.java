package tn.esprit.spring.service.event;

import tn.esprit.spring.entities.SmsRequest;

public interface SmsSender {
    void sendSms(SmsRequest smsRequest);
}
