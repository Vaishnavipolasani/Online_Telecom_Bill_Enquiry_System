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

//    @Scheduled(cron = "0 0 0 * * ?") // Run every day at midnight
    @Scheduled(cron = "* * * * * ?") // Run every second
    public void checkPlanExpiry() {			  // @Scheduled(cron = "0 0/1 * * * ?") // Run every minute	
        LocalDate today = LocalDate.now();
        System.out.println("date of today is "+today);
        List<Plan> plans = planRepository.findAll();

        for (Plan plan : plans) {
            for (Connection connection : plan.getConnections()) {
                LocalDate expiryDate = connection.getActivationdate().plusDays(plan.getNumberOfDay());
                System.out.println("Date of expiry "+expiryDate);
                System.out.println("if condition status : "+ expiryDate.minusDays(20).isEqual(today));
                if (expiryDate.minusDays(18).isEqual(today)) { 	// 5 days before expiry
                	System.out.println("i am inside this block...");
                    sendExpiryNotification(connection);
                }
            }
        }
    }

    private void sendExpiryNotification(Connection connection) {
        String messageText = "Dear " + connection.getCustomerObj().getName() +
                ",\n\nYour plan i.e " +"'"+ connection.getPlan().getPlanName() +"'"+
                " is about to expire in 5 days.\nRecharge immiediately to get the uninterrupted subscription."
                + "\nIgnore this message if you had already recharged.\n\nRegards,\nOTBS Team";

        // Send email
        emailService.sendEmail(connection.getCustomerObj().getEmail(), "Plan Expiry Notification", messageText);

        // Send SMS
//        String toPhoneNumber = connection.getCustomerObj().getPhoneNumber();
//        twilioService.sendSms(toPhoneNumber, messageText);
    }
}
