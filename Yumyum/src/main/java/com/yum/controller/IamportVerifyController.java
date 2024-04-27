package com.yum.controller;



import java.io.IOException;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/verifyiamport")
// iamport 결제 검증
public class IamportVerifyController {

    private final IamportClient iamportClient;

    // REST API 와 REST API secret 입력
    public IamportVerifyController(){
        this.iamportClient = new IamportClient("7464134155225840", "nxuuws9VZYJEhe0mU3qZozR473fqlO8L09YUHLXDKNtso1W1n1DzzxbQQR0iNaDMu6asnChOKceyVgiL");
    }

    // 아임포트 액세스 토큰(access token) 발급 받기
    @PostMapping("/{impUid}")
    public IamportResponse<Payment> paymentByImpUid(@PathVariable String impUid) throws IamportResponseException, IOException{
        log.info("paymentByImpUid 진입");
        return iamportClient.paymentByImpUid(impUid);
    }
}
