package com.otbs.scheduler;

import com.otbs.model.Plan;
import com.otbs.model.Connection;
import com.otbs.repository.PlanRepository;
import com.otbs.service.EmailService;
import com.otbs.service.TwilioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class PlanExpiryNotifier {

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TwilioService twilioService;

    @Scheduled(cron = "0 0 0 * * ?") // Run every day at midnight
    public void checkPlanExpiry() {			  // @Scheduled(cron = "0 0/1 * * * ?") // Run every minute	
        LocalDate today = LocalDate.now();
        List<Plan> plans = planRepository.findAll();

        for (Plan plan : plans) {
            for (Connection connection : plan.getConnections()) {
                LocalDate expiryDate = connection.getActivationdate().plusDays(plan.getNumberOfDay());
                if (expiryDate.minusDays(5).isEqual(today)) { // 5 days before expiry
                    sendExpiryNotification(connection);
                }
            }
        }
    }

    private void sendExpiryNotification(Connection connection) {
        String messageText = "Dear " + connection.getCustomerObj().getName() +
                ",\n\nYour plan " + connection.getPlan().getPlanName() +
                " is about to expire in 5 days.\n\nRegards,\nOTBS Team";

        // Send email
        emailService.sendEmail(connection.getCustomerObj().getEmail(), "Plan Expiry Notification", messageText);

        // Send SMS
        String toPhoneNumber = connection.getCustomerObj().getPhoneNumber();
        twilioService.sendSms(toPhoneNumber, messageText);
    }
}
