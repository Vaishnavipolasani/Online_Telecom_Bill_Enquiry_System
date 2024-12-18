package com.otbs.service;


import java.time.LocalDate;

import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.*;

import com.otbs.model.*;
import com.otbs.repository.*;

import jakarta.transaction.Transactional;


@Service
public class BillServiceImpl implements BillService {

    @Autowired
    private ConnectionRepository connectionRepository;

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private UsageInfoRepository usageInfoRepository;

    @Autowired
    private PlanRepository planRepository;

    @Override
    @Transactional
    public void generatePrepaidBill(int connectionId) {
        Connection connection = connectionRepository.findById(connectionId)
                .orElseThrow(() -> new RuntimeException("Connection not found"));

        Plan plan = connection.getPlan();
        if (LocalDate.now().isAfter(planend(connection.getActivationdate(),plan.getNumberOfDay()))){ //compare validity date with the current date
            // Block service if validity expired
            connection.setStatus("Blocked");
            connectionRepository.save(connection);  //updating that the plan blocked

            // Generate summary bill
            UsageInfo usage = usageInfoRepository.findByConnection(connection)
                    .orElseThrow(() -> new RuntimeException("Usage info not found"));
            
            Bill existingBill = billRepository.findByConnectionAndUsage(connection, usage); //checking bill alredy there
            if (existingBill != null) {
            	System.out.println("problem solved");// bill already there
            	mailsending();
            }
            else {

	            double additionalCharges = calculateAdditionalCharges(usage, plan);
	            double totalCharges = plan.getFixedRate() + additionalCharges;
	
	            Bill bill = new Bill();
	            bill.setConnection(connection);
	            bill.setUsage(usage);
	            bill.setDate(LocalDate.now());
	            bill.setTotalAmount(totalCharges);
	            bill.setStatus("Paid");
	            billRepository.save(bill);
            }
        }
    }

    @Override
    @Transactional
    public void generatePostpaidBill(int connectionId) {
        Connection connection = connectionRepository.findById(connectionId)
                .orElseThrow(() -> new RuntimeException("Connection not found"));

        Plan plan = connection.getPlan();
        UsageInfo usage = usageInfoRepository.findByConnection(connection)
                .orElseThrow(() -> new RuntimeException("Usage info not found"));
        
        Bill existingBill = billRepository.findByConnectionAndUsage(connection, usage); //checking bill alredy there
        if (existingBill != null) System.out.println("problem solved");           // bill alredy there
        else {                                                                     
        // Calculate additional charges if usage exceeds limits
        double additionalCharges = calculateAdditionalCharges(usage, plan);        // generate bill
        double totalCharges = plan.getFixedRate() + additionalCharges;
        double tax = totalCharges * 0.18; // Assuming 18% tax
        double finalAmount = totalCharges + tax;

        // Create a new bill
        Bill bill = new Bill();
        bill.setConnection(connection);
        bill.setUsage(usage);
        bill.setDate(LocalDate.now());
        bill.setTotalAmount(finalAmount);
        bill.setTax(tax);
        bill.setStatus("Unpaid");
        bill.setDueDate(LocalDate.now().plusDays(15)); // Set due date
        billRepository.save(bill);

        // Suspend service if the previous bill is unpaid
        if (bill.getStatus().equalsIgnoreCase("Unpaid") &&  //ignoring the case-sensitive means "unpaid" and "UNPAID" both are same 
                LocalDate.now().isAfter(bill.getDueDate())) {
            connection.setStatus("Blocked");
            connectionRepository.save(connection);
        }
        
        //new to update the connection ...
        connection.setActivationdate(LocalDate.now());  //updating the last billing time 
        connectionRepository.save(connection); 
        }
    }

    @Override
    @Scheduled(cron = "0 * * * * ?") // Run daily at midnight
    public void generateBillsForAllConnections() {
        List<Connection> connections = connectionRepository.findAll(); //getting all the connection , i think we need to fetch the " active " connection
        for (Connection connection : connections) {
            if ("Prepaid".equalsIgnoreCase(connection.getConnectionType())) {
                generatePrepaidBill(connection.getConnectionId());
            } else if ("Postpaid".equalsIgnoreCase(connection.getConnectionType())) {
                generatePostpaidBill(connection.getConnectionId());  //edited by me to check date 
            }
        }
        System.out.println("sivaraj- hear");
    }
    

    @Override
    public List<Bill> getBillsByCustomerId(int customerId) {
        return billRepository.findByConnection_CustomerObj_CustomerId(customerId);
    }

    private double calculateAdditionalCharges(UsageInfo usage, Plan plan) {
        double dataCharges = Math.max(0, usage.getDataUsed() - Double.parseDouble(convertStringToInt(plan.getDataLimit())))
                * plan.getExtraChargePerMB();
;        double callCharges = Math.max(0, usage.getCalls() - Double.parseDouble(convertStringToInt(plan.getCallLimit())))
                * plan.getExtraChargePerCall();
        double smsCharges = Math.max(0, usage.getSms() - Double.parseDouble(convertStringToInt(plan.getSmsLimit())))
                * plan.getExtraChargePerSMS();
        return dataCharges + callCharges + smsCharges;
    }
    
    
    //convert the string to integer for plan convertion 
    
    public static String convertStringToInt(String input) {
        // Remove any non-numeric characters from the string
        return input.replaceAll("[^0-9]", "");
    }
    
    public static LocalDate planend(LocalDate date,int day) {
    	return date.plusDays(day);
    }
    
    
    
    public static void  mailsending() {
    	
    	 final String senderEmail = "sivarajc357@gmail.com";
         final String senderPassword = "qgcvyvzesaxvlfcz"; // Use App Password if 2FA is enabled

         // Receiver's email
         String recipientEmail = "sivarajc2005@gmail.com";

         // SMTP server properties
         Properties properties = new Properties();
         properties.put("mail.smtp.host", "smtp.gmail.com"); // SMTP host
         properties.put("mail.smtp.port", "587"); // TLS port
         properties.put("mail.smtp.auth", "true"); // Enable authentication
         properties.put("mail.smtp.starttls.enable", "true"); // Enable TLS

         // Create a session with authentication
         Session session = Session.getInstance(properties, new Authenticator() {
             protected PasswordAuthentication getPasswordAuthentication() {
                 return new PasswordAuthentication(senderEmail, senderPassword);
             }
         });

         try {
             // Create a MIME email message
             MimeMessage message = new MimeMessage(session);
             message.setFrom(new InternetAddress(senderEmail)); // Sender's email
             message.setRecipients(
                 Message.RecipientType.TO,
                 InternetAddress.parse(recipientEmail)
             );
             message.setSubject("Beautiful HTML Email from Java"); // Email subject

             
             

             // HTML content for the email body
             String htmlContent = "<!DOCTYPE html>"
            	        + "<html>"
            	        + "<head>"
            	        + "    <title>Bill Notification</title>"
            	        + "    <style>"
            	        + "        body {"
            	        + "            font-family: Arial, sans-serif;"
            	        + "            margin: 0;"
            	        + "            padding: 0;"
            	        + "            background-color: #f4f4f4;"
            	        + "        }"
            	        + "        .email-container {"
            	        + "            max-width: 600px;"
            	        + "            margin: 20px auto;"
            	        + "            background-color: #ffffff;"
            	        + "            border: 1px solid #e0e0e0;"
            	        + "            border-radius: 8px;"
            	        + "            overflow: hidden;"
            	        + "            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);"
            	        + "        }"
            	        + "        .header {"
            	        + "            background-color: #4CAF50;"
            	        + "            color: #ffffff;"
            	        + "            text-align: center;"
            	        + "            padding: 20px;"
            	        + "            font-size: 24px;"
            	        + "        }"
            	        + "        .content {"
            	        + "            padding: 20px;"
            	        + "            color: #333333;"
            	        + "        }"
            	        + "        .content p {"
            	        + "            margin: 10px 0;"
            	        + "            font-size: 16px;"
            	        + "            line-height: 1.5;"
            	        + "        }"
            	        + "        .bill-details {"
            	        + "            margin: 20px 0;"
            	        + "            border-collapse: collapse;"
            	        + "            width: 100%;"
            	        + "        }"
            	        + "        .bill-details th, .bill-details td {"
            	        + "            border: 1px solid #dddddd;"
            	        + "            text-align: left;"
            	        + "            padding: 8px;"
            	        + "        }"
            	        + "        .bill-details th {"
            	        + "            background-color: #f2f2f2;"
            	        + "        }"
            	        + "        .footer {"
            	        + "            background-color: #f2f2f2;"
            	        + "            color: #777777;"
            	        + "            text-align: center;"
            	        + "            padding: 15px;"
            	        + "            font-size: 12px;"
            	        + "        }"
            	        + "        .button {"
            	        + "            display: inline-block;"
            	        + "            margin: 20px auto;"
            	        + "            padding: 10px 20px;"
            	        + "            font-size: 16px;"
            	        + "            color: #ffffff;"
            	        + "            background-color: #4CAF50;"
            	        + "            text-decoration: none;"
            	        + "            border-radius: 5px;"
            	        + "            text-align: center;"
            	        + "        }"
            	        + "    </style>"
            	        + "</head>"
            	        + "<body>"
            	        + "    <div class=\"email-container\">"
            	        + "        <!-- Header -->"
            	        + "        <div class=\"header\">"
            	        + "            <strong>Your Bill Notification</strong>"
            	        + "        </div>"
            	        + "        <!-- Content -->"
            	        + "        <div class=\"content\">"
            	        + "            <p>Dear Customer,</p>"
            	        + "            <p>Please find below the details of your bill:</p>"
            	        + "            <!-- Bill Details Table -->"
            	        + "            <table class=\"bill-details\">"
            	        + "                <tr>"
            	        + "                    <th>Bill ID</th>"
            	        + "                    <td>{{billId}}</td>"
            	        + "                </tr>"
            	        + "                <tr>"
            	        + "                    <th>Due Date</th>"
            	        + "                    <td>{{dueDate}}</td>"
            	        + "                </tr>"
            	        + "                <tr>"
            	        + "                    <th>Total Amount</th>"
            	        + "                    <td>{{totalAmount}}</td>"
            	        + "                </tr>"
            	        + "            </table>"
            	        + "            <!-- Payment Button -->"
            	        + "            <p style=\"text-align: center;\">"
            	        + "                <a href=\"{{paymentLink}}\" class=\"button\">Pay Now</a>"
            	        + "            </p>"
            	        + "            <p>If you have already paid this bill, please disregard this email.</p>"
            	        + "            <p>Thank you for choosing our services.</p>"
            	        + "        </div>"
            	        + "        <!-- Footer -->"
            	        + "        <div class=\"footer\">"
            	        + "            <p>&copy; 2024 Your Company Name. All Rights Reserved.</p>"
            	        + "            <p>Contact us: <a href=\"mailto:support@yourcompany.com\">support@yourcompany.com</a></p>"
            	        + "        </div>"
            	        + "    </div>"
            	        + "</body>"
            	        + "</html>".replace("{{billId}}", "12345")
                        .replace("{{dueDate}}", "2024-12-31")
                        .replace("{{totalAmount}}", "$100")
                        .replace("{{paymentLink}}", "https://payment.example.com");

             // Set the content of the email to HTML
             message.setContent(htmlContent, "text/html; charset=utf-8");

             // Send the email
             Transport.send(message);

             System.out.println("HTML email sent successfully!");

         } catch (MessagingException e) {
             e.printStackTrace();
         }
    }



}

