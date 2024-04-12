package com.scaler.paymentservice.controllers;

import com.razorpay.RazorpayException;
import com.razorpay.Webhook;
import com.scaler.paymentservice.dtos.CreatePaymentLinkRequestDto;
import com.scaler.paymentservice.services.PaymentService;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private PaymentService paymentService;

    public PaymentController(PaymentService paymentService){
        this.paymentService = paymentService;
    }

    @PostMapping("/")
    public String createPaymentLink(@RequestBody CreatePaymentLinkRequestDto createPaymentLinkRequestDto) throws RazorpayException {
        String link = paymentService.createPaymentLink(createPaymentLinkRequestDto.getOrderId());
        return link;
    }

    @PostMapping("/webhook")
    public void handleWebhookEvent(@RequestBody Map<String, String> webhookEvent){
        System.out.println(webhookEvent);

    }
}
