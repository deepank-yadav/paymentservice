package com.scaler.paymentservice.services;

import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.scaler.paymentservice.configs.RazorpayConfig;
import org.json.JSONObject;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class RazorpayPaymentService implements PaymentService{

    private RazorpayClient razorpayClient;

    public RazorpayPaymentService(RazorpayClient razorpayClient) {
        this.razorpayClient = razorpayClient;
    }
    @Override
    public String createPaymentLink(String orderId) throws RazorpayException {

        JSONObject paymentLinkRequest = new JSONObject();
        paymentLinkRequest.put("amount",1000);// 10.01 => 1001 // 99.99 => 9999 // 999 => 99900
        paymentLinkRequest.put("currency","INR");
        paymentLinkRequest.put("accept_partial",false);
        paymentLinkRequest.put("first_min_partial_amount",100);
        paymentLinkRequest.put("expire_by",System.currentTimeMillis()+15*60*1000);
        paymentLinkRequest.put("reference_id",orderId);
        paymentLinkRequest.put("description","Payment for policy no #23456");


        // Order order = orderService.getOrderDetails(orderId)
        // String cutomerName = order.getUser().getName()
        // String contact = order.getUser().getMobile()

        JSONObject customer = new JSONObject();
        customer.put("name","+919996203771");
        customer.put("contact","Deepank Yadav");
        customer.put("email","deepankyadav@example.com");
        paymentLinkRequest.put("customer",customer);

        JSONObject notify = new JSONObject();
        notify.put("sms",true);
        notify.put("email",true);
        paymentLinkRequest.put("reminder_enable",true);
        JSONObject notes = new JSONObject();
        notes.put("Order Items","1. iPhone 15 Pro Max");
        paymentLinkRequest.put("notes",notes);
        paymentLinkRequest.put("callback_url","https://github.com/deepank-yadav/paymentservice");
        paymentLinkRequest.put("callback_method","get");

        PaymentLink payment = razorpayClient.paymentLink.create(paymentLinkRequest);
        return payment.get("short_url");
    }

    @Override
    public String getPaymentStatus(String paymentId) {
        return "";
    }
}
