package com.companion.local;

import com.companion.local.controller.CompanionLocalController;
import com.companion.local.model.*;
import com.companion.local.model.LinkedCards.TransferLinkCardResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompanionLocalApplicationTests {

    @InjectMocks
    private CompanionLocalController companionLocalController;
    private static CreateLinkedCardResponse createLinkedCardResponse;
    private static CreateLinkedCardRequest createLinkedCardRequest;
    private static CreateLinkedCardRequest getActiveCardRequests;
    private static CompanionLocalController companionLocalControllerMock;
    private static List<CreateLinkedCardResponse> getActiveCardResponses = new ArrayList<>();
    private static List<ActivateCardRequest> activateCardRequestList = new ArrayList<>();
    private static List<ActivateCardResponse> activateCardResponses = new ArrayList<>();
    private static CardResponse cardResponse;
    private static List<CardResponse> cardResponses = new ArrayList<>();
    private static  UpdateBearerRequest updateBearerRequest;
    private static CardActionRequest cardActionRequest;
    private static TransferLinkCardRequest transferLinkCardRequest;
    private static StopCardRequest stopCardRequest;
    private static UnstopCardActionRequest unstopCardActionRequest;
    private static RetireCardRequest retireCardRequest;

    @BeforeAll
    public static void loadPrerequisites() {
        createLinkedCardRequest = new CreateLinkedCardRequest("137212348687", "Faiz", "Ahamad", "Z3124325", "0842435881", "20401010T10:10:10", "6beebbae-98c2-4d74-97a8-a070645f4147", "20221216T10:10:10");

    @BeforeAll
    public static void loadPrerequisites() {
        createLinkedCardRequest = new CreateLinkedCardRequest("1372123433", "Faiz", "Ahamad", "Z3124325", "0842435881", "20401010T10:10:10", "6beebbae-98c2-4d74-97a8-a070645f4147", "20221216T10:10:10");
        createLinkedCardResponse = new CreateLinkedCardResponse();
        createLinkedCardResponse.setCvv("345");
        createLinkedCardResponse.setCardNumber("3456789766666");
        createLinkedCardResponse.setExpiryDate("20-1-2030");

        getActiveCardRequests = new CreateLinkedCardRequest("9901017307083", "", "", "", "", "", "6beebbae-98c2-4d74-97a8-a070645f4147", "20230131T10:10:10");
        getActiveCardResponses.add(createLinkedCardResponse);

        activateCardRequestList.add(new ActivateCardRequest("5371645393315835", "6beebbae-98c2-4d74-97a8-a070645f4147", "20230202T10:10:10"));
        ActivateCardResponse activateCardResponse = new ActivateCardResponse();
        activateCardResponse.setResponseStatus("Approved");
        activateCardResponses.add(activateCardResponse);

        cardResponse = new CardResponse();
        cardResponse.setResponseStatus("Approved");
        cardResponses.add(cardResponse);

        cardActionRequest = new CardActionRequest("9901013706080","5371645854773381","6beebbae-98c2-4d74-97a8-a070645f4147","20221216T10:10:10");
        updateBearerRequest = new UpdateBearerRequest("137212343390","Faiz","Ahamad","Z7867869087","0842435882","20401010T10:10:10","6beebbae-98c2-4d74-97a8-a070645f4147","20221216T10:10:10","5371645867106298");

        transferLinkCardRequest = new TransferLinkCardRequest("137284352847523","5371645871856201","5371645857108437","6beebbae-98c2-4d74-97a8-a070645f4147","20230207T10:10:10");

        stopCardRequest = new StopCardRequest("137287878787","5371645802197113","","6beebbae-98c2-4d74-97a8-a070645f4147","20221216T10:10:10","1","Card is being consolidated");

        unstopCardActionRequest = new UnstopCardActionRequest("137287878787","5371645802197113","6beebbae-98c2-4d74-97a8-a070645f4147","20221216T10:10:10","UnstopCard");

        retireCardRequest = new RetireCardRequest("137212343390","5371645867106298","6beebbae-98c2-4d74-97a8-a070645f4147","20221216T10:10:10");
    }

    @BeforeEach
    void setUp() {
        companionLocalController.setCompanionTutukaEndpoint("https://companion.uat.tutuka.cloud/v2_0/XmlRpc.cfm");
        companionLocalController.setCompanionTutukaTerminalKey("FDB56993F7");
        companionLocalControllerMock = mock(CompanionLocalController.class);
    }

    /**
     * Integration Test
     * This will create/fetch the card from VE. if it's already  created will be fetched.
     */
    @Test
     void testCreateLinkedCard() {
        MultiValueMap<String, String> headers = new HttpHeaders();
        ResponseEntity responseEntity = companionLocalController.createCard(headers, createLinkedCardRequest);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertNotNull(((CreateLinkedCardResponse) responseEntity.getBody()).getCardNumber());
        assertNotNull(((CreateLinkedCardResponse) responseEntity.getBody()).getCvv());
        assertNotNull(((CreateLinkedCardResponse) responseEntity.getBody()).getExpiryDate());
    }

    /**
     * J-unit Mock Test
     * This will NOT call VE & will return a dummy response
     */
    @Test
     void testCreateLinkedCardMock() {
     void testCreateLinkedCardMock() {
        MultiValueMap<String, String> headers = new HttpHeaders();
        when(companionLocalControllerMock.createCard(headers, createLinkedCardRequest)).thenReturn(new ResponseEntity(createLinkedCardResponse, HttpStatus.OK));
        ResponseEntity responseEntity = companionLocalControllerMock.createCard(headers, createLinkedCardRequest);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertNotNull(((CreateLinkedCardResponse) responseEntity.getBody()).getCardNumber());
        assertNotNull(((CreateLinkedCardResponse) responseEntity.getBody()).getCvv());
        assertNotNull(((CreateLinkedCardResponse) responseEntity.getBody()).getExpiryDate());
    }

    /**
     * Integration Test
     * This will fetch the real time cards from VE
     */
    @Test
     void testGetActiveLinkedCards() {
        MultiValueMap<String, String> headers = new HttpHeaders();
        ResponseEntity responseEntity = companionLocalController.getActiveLinkedCards(headers, getActiveCardRequests);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertNotNull(((List<CreateLinkedCardResponse>) responseEntity.getBody()).get(0).getCardNumber());
        assertNotNull(((List<CreateLinkedCardResponse>) responseEntity.getBody()).get(0).getCvv());
        assertNotNull(((List<CreateLinkedCardResponse>) responseEntity.getBody()).get(0).getExpiryDate());
    }

    /**
     * J-unit Mock Test
     * This will NOT call VE & will return a dummy response
     */
    @Test
     void testGetActiveLinkedCardsMock() {
        MultiValueMap<String, String> headers = new HttpHeaders();
        when(companionLocalControllerMock.getActiveLinkedCards(headers, getActiveCardRequests)).thenReturn(new ResponseEntity(getActiveCardResponses, HttpStatus.OK));
        ResponseEntity responseEntity = companionLocalControllerMock.getActiveLinkedCards(headers, getActiveCardRequests);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertNotNull(((List<CreateLinkedCardResponse>) responseEntity.getBody()).get(0).getCardNumber());
        assertNotNull(((List<CreateLinkedCardResponse>) responseEntity.getBody()).get(0).getCvv());
        assertNotNull(((List<CreateLinkedCardResponse>) responseEntity.getBody()).get(0).getExpiryDate());
    }

    /**
     * Integration Test
     * This will activate the card in VE
     */
    @Test
     void testActivateCard() {
        MultiValueMap<String, String> headers = new HttpHeaders();
        ResponseEntity responseEntity = companionLocalController.activateCard(headers, activateCardRequestList);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertNotNull(((List<ActivateCardResponse>) responseEntity.getBody()).get(0).getResponseStatus());
    }

    /**
     * J-unit Mock Test
     * This will NOT activate the card in VE,rather will return a dummy response
     */
    @Test
     void testActivateCardMock() {
        MultiValueMap<String, String> headers = new HttpHeaders();
        when(companionLocalControllerMock.activateCard(headers, activateCardRequestList)).thenReturn(new ResponseEntity(activateCardResponses, HttpStatus.OK));
        ResponseEntity responseEntity = companionLocalControllerMock.activateCard(headers, activateCardRequestList);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertNotNull(((List<ActivateCardResponse>) responseEntity.getBody()).get(0).getResponseStatus());
    }

    /**
     * J-unit Mock Test
     * This will NOT print the card in VE,rather will return a dummy response
     */
    @Test
     void testPrintLinkedCardMock() {
        MultiValueMap<String, String> headers = new HttpHeaders();
        PrintLinkedCardRequest printLinkedCardRequest = new PrintLinkedCardRequest();
        when(companionLocalControllerMock.printLinkedCard(headers, printLinkedCardRequest)).thenReturn(new ResponseEntity(cardResponse, HttpStatus.OK));
        ResponseEntity responseEntity = companionLocalControllerMock.printLinkedCard(headers, printLinkedCardRequest);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertNotNull(((CardResponse) responseEntity.getBody()).getResponseStatus());

    }

    /**
     * J-unit Mock Test
     * This will NOT toggle the card feature in VE,rather will return a dummy response
     */
    @Test
     void testToggleVoucherFeatureMock() {
        MultiValueMap<String, String> headers = new HttpHeaders();
        ToggleVoucherFeatureRequest toggleVoucherFeatureRequest = new ToggleVoucherFeatureRequest();
        when(companionLocalControllerMock.toggleVoucherFeature(headers, toggleVoucherFeatureRequest)).thenReturn(new ResponseEntity(cardResponse, HttpStatus.OK));
        ResponseEntity responseEntity = companionLocalControllerMock.toggleVoucherFeature(headers, toggleVoucherFeatureRequest);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertNotNull(((CardResponse) responseEntity.getBody()).getResponseStatus());

    }

    /**
     * J-unit Mock Test
     * This will NOT update the card bearer in VE,rather will return a dummy response
     */
    @Test

     void testUpdateBearerMock() {
        MultiValueMap<String, String> headers = new HttpHeaders();
        List<UpdateBearerRequest> updateBearerRequest = new ArrayList<>();
        when(companionLocalControllerMock.updateBearer(headers, updateBearerRequest)).thenReturn(new ResponseEntity(cardResponses, HttpStatus.OK));
        ResponseEntity responseEntity = companionLocalControllerMock.updateBearer(headers, updateBearerRequest);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertNotNull(((List<CardResponse>) responseEntity.getBody()).get(0).getResponseStatus());

    }

    /**
     * Integration Test
     * This will update the cardholder details in VE
     */
    @Test
     void testUpdateBearer() {
        MultiValueMap<String, String> headers = new HttpHeaders();
        ResponseEntity responseEntity = companionLocalController.updateBearer(headers, updateBearerRequest);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertNotNull(((CardResponse) responseEntity.getBody()).getResponseStatus());
        assertThat(((CardResponse) responseEntity.getBody()).getResponseStatus()).isEqualTo("Approved");
    }

    /**
     * Integration Test
     * This will fetch the card status from VE
     */
    @Test
     void testGetCardStatus() {
        MultiValueMap<String, String> headers = new HttpHeaders();
        ResponseEntity responseEntity = companionLocalController.cardStatus(headers, cardActionRequest);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertNotNull(((CardResponse) responseEntity.getBody()).getResponseStatus());
        assertThat(((CardResponse) responseEntity.getBody()).getResponseStatus()).isEqualTo("valid");
    }

    /**
     * Integration Test
     * This will fetch the real time cards from VE
     */
    @Test
     void testGetLinkedCards() {
        MultiValueMap<String, String> headers = new HttpHeaders();
        ResponseEntity responseEntity = companionLocalController.getLinkedCards(headers, getActiveCardRequests);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertNotNull(((List<CreateLinkedCardResponse>) responseEntity.getBody()).get(0).getCardNumber());
        assertNotNull(((List<CreateLinkedCardResponse>) responseEntity.getBody()).get(0).getCvv());
        assertNotNull(((List<CreateLinkedCardResponse>) responseEntity.getBody()).get(0).getExpiryDate());
    }

    /**
     * Integration Test
     * This will transfer the link of card  in VE
     */
    @Test
     void testTransferLink() {
        MultiValueMap<String, String> headers = new HttpHeaders();
        ResponseEntity responseEntity = companionLocalController.transferLink(headers, transferLinkCardRequest);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertNotNull(((TransferLinkCardResponse) responseEntity.getBody()).getResponseStatus());
        assertThat(((TransferLinkCardResponse) responseEntity.getBody()).getResponseStatus()).isEqualTo("Card already linked to a different reference");

    }

    /**
     * Integration Test
     * This will stop the card  in VE
     */
    @Test
    @Order(1)
    void testStopCardUnstopCard() {
        MultiValueMap<String, String> headers = new HttpHeaders();
        ResponseEntity responseEntity = companionLocalController.stopCard(headers, stopCardRequest);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertNotNull(((CardResponse) responseEntity.getBody()).getResponseStatus());
        assertThat(((CardResponse) responseEntity.getBody()).getResponseStatus()).isEqualTo("Approved");

        ResponseEntity unstopResponseEntity = companionLocalController.unstopCard(headers, unstopCardActionRequest);
        assertThat(unstopResponseEntity.getStatusCodeValue()).isEqualTo(200);
        assertNotNull(((CardResponse) unstopResponseEntity.getBody()).getResponseStatus());
        assertThat(((CardResponse) unstopResponseEntity.getBody()).getResponseStatus()).isEqualTo("Approved");
    }

    /**
     * Integration Test
     * This will un-stop the card  in VE
     */
    @Test
     void testUpdateCVV() {
        MultiValueMap<String, String> headers = new HttpHeaders();
        ResponseEntity responseEntity = companionLocalController.updateCVV(headers, retireCardRequest);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertNotNull(((CardResponse) responseEntity.getBody()).getResponseStatus());
        assertThat(((CardResponse) responseEntity.getBody()).getResponseStatus()).isEqualTo("Approved");
    }
}
