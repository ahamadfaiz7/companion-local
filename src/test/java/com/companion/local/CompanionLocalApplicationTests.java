package com.companion.local;

import com.companion.local.controller.CompanionLocalController;
import com.companion.local.model.CreateLinkedCardRequest;
import com.companion.local.model.CreateLinkedCardResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.w3c.dom.Element;

import java.io.Serializable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompanionLocalApplicationTests {

    @InjectMocks
    private CompanionLocalController companionLocalController;

    @Test
    public void testCreateLinkedCard() {
        companionLocalController.setCompanionTutukaEndpoint("https://companion.uat.tutuka.cloud/v2_0/XmlRpc.cfm");
        companionLocalController.setCompanionTutukaTerminalKey("FDB56993F7");
        MultiValueMap<String, String> headers = new HttpHeaders();
        CreateLinkedCardRequest createLinkedCardRequest = new CreateLinkedCardRequest("1372123433", "Faiz", "Ahamad", "Z3124325", "0842435881", "20401010T10:10:10", "6beebbae-98c2-4d74-97a8-a070645f4147", "20221216T10:10:10");
        ResponseEntity<Serializable> responseEntity = companionLocalController.createCard(headers, createLinkedCardRequest);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertNotNull(((CreateLinkedCardResponse) responseEntity.getBody()).getCardNumber());
        assertNotNull(((CreateLinkedCardResponse) responseEntity.getBody()).getCvv());
        assertNotNull(((CreateLinkedCardResponse) responseEntity.getBody()).getExpiryDate());
    }

    public void testCreateLinkedCardMock() {
        CreateLinkedCardResponse response = new CreateLinkedCardResponse();
        response.setCvv("345");
        response.setCardNumber("3456789766666");
        response.setExpiryDate("20-1-2030");
        CompanionLocalController listMock = mock(CompanionLocalController.class, "createCard");

        MultiValueMap<String, String> headers = new HttpHeaders();
        CreateLinkedCardRequest createLinkedCardRequest = new CreateLinkedCardRequest("1372123433", "Faiz", "Ahamad", "Z3124325", "0842435881", "20401010T10:10:10", "6beebbae-98c2-4d74-97a8-a070645f4147", "20221216T10:10:10");
        when(companionLocalController.createCard(headers, createLinkedCardRequest)).thenReturn(new ResponseEntity<java.io.Serializable>(response, HttpStatus.OK));

        ResponseEntity<Serializable> responseEntity = companionLocalController.createCard(headers, createLinkedCardRequest);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertNotNull(((CreateLinkedCardResponse) responseEntity.getBody()).getCardNumber());
        assertNotNull(((CreateLinkedCardResponse) responseEntity.getBody()).getCvv());
        assertNotNull(((CreateLinkedCardResponse) responseEntity.getBody()).getExpiryDate());
    }
}
