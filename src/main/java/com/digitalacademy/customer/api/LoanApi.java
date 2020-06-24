package com.digitalacademy.customer.api;

import com.digitalacademy.customer.model.response.GetLoanInfoResponse;
import com.digitalacademy.customer.utill.Utill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
public class LoanApi {
    public RestTemplate restTemplate;

    public LoanApi(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public GetLoanInfoResponse getLoanInfo(Long id) throws Exception{
        String data = "{}";
        try {
            RequestEntity requestEntity = RequestEntity
                    .get(URI.create( "http://localhost:8082/api/loan/info/"+ id)).build();

            System.out.println("Request: " + requestEntity.getMethod()+"Url: "+
                    requestEntity.getUrl());

            ResponseEntity<String> response = restTemplate.exchange(requestEntity,String.class);
        }catch (final HttpClientErrorException httpClientErrorException){
            System.out.println("httpClientErrorException "+ httpClientErrorException);
            throw new Exception("httpClientErrorException");
        }catch (HttpServerErrorException httpServerErrorException){
            System.out.println("httpServerErrorException");
            throw new Exception("httpServerErrorException");
        }catch (Exception e){
            throw e;
        }
        return Utill.readValue(data,GetLoanInfoResponse.class);
    }
}
