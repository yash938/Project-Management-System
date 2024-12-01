package com.Project_Management.Controller;

import com.Project_Management.Dto.PaymentLinkResponse;
import com.Project_Management.Entity.PlanType;
import com.Project_Management.Entity.User;
import com.Project_Management.Service.UserService;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.HTTP;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {
//
//    @Value("${}")
//    private String apiKey;
//
//    @Value("${}")
//    private String apiSecret;
//
//    @Autowired
//    private UserService userService;
//
//
//    public ResponseEntity<PaymentLinkResponse> createPaymentLinkResponse(
//            @PathVariable PlanType planType
//            ){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User user = (User) authentication.getPrincipal();
//
//        int amount = 799 * 100;
//        if(planType.equals(PlanType.ANNUALLY)){
//            amount = amount * 12;
//            amount=(int)(amount * 0.7);
//        }
//        try{
//            RazorpayClient razorpayClient = new RazorpayClient(apiKey,apiSecret);
//            JSONObject paymentLink = new JSONObject();
//            paymentLink.put("amount",amount);
//            paymentLink.put("currency","INR");
//
//            JSONObject customer = new JSONObject();
//            customer.put("name",user.getFullName());
//            customer.put("email",user.getEmail());
//            paymentLink.put("customer",customer);
//
//            JSONObject notify = new JSONObject();
//            notify.put("email",true);
//            paymentLink.put("notify",notify);
//
//            paymentLink.put("callback_url","http://localhost:5173/upgrade_plan/success?plantype"+planType);
//            PaymentLink paymentLink1 = razorpayClient.paymentLink.create(paymentLink);
//            String id = paymentLink1.get("id");
//            String shortUrl = paymentLink1.get("short_url");
//
//            PaymentLinkResponse paymentLinkResponse = new PaymentLinkResponse(id,shortUrl);
//            return new ResponseEntity<>(paymentLinkResponse, HttpStatus.OK);
//
//        } catch (RazorpayException e) {
//            throw new RuntimeException(e);
//        }
//    }



}
