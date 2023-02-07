package com.companion.local.controller;


import com.companion.local.model.*;
import com.companion.local.model.LinkedCards.Data;
import com.companion.local.request.MethodCall;
import com.companion.local.request.Param;
import com.companion.local.request.Params;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.CharacterData;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api")
public class CompanionLocalController {

    public static final String TERMINAL_ID = "0030069501";
    public static final String VALUE = "value";
    public static final String STRING = "string";
    public static final String FAILED = "FAILED";
    public static final String SUCCESS = "SUCCESS";
    public static final String FAILED_RESPONSE = "Failed Response";
    @Value("${companion.tutuka.uat.endpoint}")
    private String companionTutukaEndpoint;
    @Value("${companion.tutuka.uat.terminal.key}")
    private String companionTutukaTerminalKey;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    RestTemplate restTemplate = restTemplate(new RestTemplateBuilder());

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/local.createLinkedCard")
    public ResponseEntity<CreateLinkedCardResponse> createCard(@RequestHeader MultiValueMap<String, String> headers, @RequestBody CreateLinkedCardRequest request) {
        CreateLinkedCardResponse response = new CreateLinkedCardResponse();
        MethodCall methodCall = new MethodCall();
        Params params = new Params();
        List<Param> paramList = new ArrayList<>();

        Param param1 = new Param();
        com.companion.local.request.Value value1 = new com.companion.local.request.Value();
        value1.setString(TERMINAL_ID);
        param1.setValue(value1);
        paramList.add(param1);

        Param param2 = new Param();
        com.companion.local.request.Value value2 = new com.companion.local.request.Value();
        value2.setString(request.getReference());
        param2.setValue(value2);
        paramList.add(param2);

        Param param3 = new Param();
        com.companion.local.request.Value value3 = new com.companion.local.request.Value();
        value3.setString(request.getFirstName());
        param3.setValue(value3);
        paramList.add(param3);

        Param param4 = new Param();
        com.companion.local.request.Value value4 = new com.companion.local.request.Value();
        value4.setString(request.getLastName());
        param4.setValue(value4);
        paramList.add(param4);

        Param param5 = new Param();
        com.companion.local.request.Value value5 = new com.companion.local.request.Value();
        value5.setString(request.getIdOrPassport());
        param5.setValue(value5);
        paramList.add(param5);

        Param param6 = new Param();
        com.companion.local.request.Value value6 = new com.companion.local.request.Value();
        value6.setString(request.getCellphoneNumber());
        param6.setValue(value6);
        paramList.add(param6);

        Param param7 = new Param();
        com.companion.local.request.Value value7 = new com.companion.local.request.Value();
        value7.setDateTimeIso8601(request.getExpiryDate());
        param7.setValue(value7);
        paramList.add(param7);

        Param param8 = new Param();
        com.companion.local.request.Value value8 = new com.companion.local.request.Value();
        value8.setString(request.getTransactionId());
        param8.setValue(value8);
        paramList.add(param8);

        Param param9 = new Param();
        com.companion.local.request.Value value9 = new com.companion.local.request.Value();
        value9.setDateTimeIso8601(request.getTransactionDate());
        param9.setValue(value9);
        paramList.add(param9);

        Param param10 = new Param();
        com.companion.local.request.Value value10 = new com.companion.local.request.Value();
        value10.setString("");
        param10.setValue(value10);
        paramList.add(param10);

        params.setParam(paramList);
        methodCall.setMethodName("CreateLinkedCard");
        methodCall.setParams(params);

        String responseFromCompanionApi = null;

        try {
            responseFromCompanionApi = restTemplate.postForObject(companionTutukaEndpoint, generateRequestXmlString(methodCall), String.class);
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(responseFromCompanionApi));

            Document doc = db.parse(is);
            NodeList nodes = doc.getElementsByTagName(VALUE);

            for (int i = 0; i < nodes.getLength(); i++) {
                Element element = (Element) nodes.item(i);
                NodeList name = element.getElementsByTagName(STRING);
                response.setCvv(getCharacterDataFromElement((Element) name.item(0)));
                response.setCardNumber(getCharacterDataFromElement((Element) name.item(1)));
                response.setExpiryDate(getCharacterDataFromElement((Element) name.item(3)));
                response.setTrackingNumber(getCharacterDataFromElement((Element) name.item(4)));
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(response, HttpStatus.OK);

    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/local.createLinkedCardList")
    public ResponseEntity<List<CreateLinkedCardResponse>> createCard(@RequestHeader MultiValueMap<String, String> headers, @RequestBody List<CreateLinkedCardRequest> requestList) {

        List<CreateLinkedCardResponse> responseList = new ArrayList<CreateLinkedCardResponse>();
        for (CreateLinkedCardRequest request : requestList) {
            CreateLinkedCardResponse response = new CreateLinkedCardResponse();
            MethodCall methodCall = new MethodCall();
            Params params = new Params();
            List<Param> paramList = new ArrayList<>();

            Param param1 = new Param();
            com.companion.local.request.Value value1 = new com.companion.local.request.Value();
            value1.setString(TERMINAL_ID);
            param1.setValue(value1);
            paramList.add(param1);

            Param param2 = new Param();
            com.companion.local.request.Value value2 = new com.companion.local.request.Value();
            value2.setString(request.getReference());
            param2.setValue(value2);
            paramList.add(param2);

            Param param3 = new Param();
            com.companion.local.request.Value value3 = new com.companion.local.request.Value();
            value3.setString(request.getFirstName());
            param3.setValue(value3);
            paramList.add(param3);

            Param param4 = new Param();
            com.companion.local.request.Value value4 = new com.companion.local.request.Value();
            value4.setString(request.getLastName());
            param4.setValue(value4);
            paramList.add(param4);

            Param param5 = new Param();
            com.companion.local.request.Value value5 = new com.companion.local.request.Value();
            value5.setString(request.getIdOrPassport());
            param5.setValue(value5);
            paramList.add(param5);

            Param param6 = new Param();
            com.companion.local.request.Value value6 = new com.companion.local.request.Value();
            value6.setString(request.getCellphoneNumber());
            param6.setValue(value6);
            paramList.add(param6);

            Param param7 = new Param();
            com.companion.local.request.Value value7 = new com.companion.local.request.Value();
            value7.setDateTimeIso8601(request.getExpiryDate());
            param7.setValue(value7);
            paramList.add(param7);

            Param param8 = new Param();
            com.companion.local.request.Value value8 = new com.companion.local.request.Value();
            value8.setString(request.getTransactionId());
            param8.setValue(value8);
            paramList.add(param8);

            Param param9 = new Param();
            com.companion.local.request.Value value9 = new com.companion.local.request.Value();
            value9.setDateTimeIso8601(request.getTransactionDate());
            param9.setValue(value9);
            paramList.add(param9);

            Param param10 = new Param();
            com.companion.local.request.Value value10 = new com.companion.local.request.Value();
            value10.setString("");
            param10.setValue(value10);
            paramList.add(param10);

            params.setParam(paramList);
            methodCall.setMethodName("CreateLinkedCard");
            methodCall.setParams(params);

            String responseFromCompanionApi = null;

            try {
                responseFromCompanionApi = restTemplate.postForObject(companionTutukaEndpoint, generateRequestXmlString(methodCall), String.class);
                DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                InputSource is = new InputSource();
                is.setCharacterStream(new StringReader(responseFromCompanionApi));

                Document doc = db.parse(is);
                NodeList nodes = doc.getElementsByTagName(VALUE);

                for (int i = 0; i < nodes.getLength(); i++) {
                    Element element = (Element) nodes.item(i);
                    NodeList name = element.getElementsByTagName(STRING);
                    response.setCvv(getCharacterDataFromElement((Element) name.item(0)));
                    response.setCardNumber(getCharacterDataFromElement((Element) name.item(1)));
                    response.setExpiryDate(getCharacterDataFromElement((Element) name.item(3)));
                    response.setTrackingNumber(getCharacterDataFromElement((Element) name.item(4)));
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.setCvv(FAILED_RESPONSE);
                response.setCardNumber(FAILED_RESPONSE);
                response.setExpiryDate(FAILED_RESPONSE);
                response.setTrackingNumber(FAILED_RESPONSE);
            }
            responseList.add(response);
        }
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/local.activateCard")
    public ResponseEntity<List<ActivateCardResponse>> activateCard(@RequestHeader MultiValueMap<String, String> headers, @RequestBody List<ActivateCardRequest> requestList) {

        List<ActivateCardResponse> responseList = new ArrayList<ActivateCardResponse>();
        for (ActivateCardRequest request : requestList) {
            ActivateCardResponse response = new ActivateCardResponse();
            response.setCardIdentifier(request.getCardIdentifier());
            MethodCall methodCall = new MethodCall();
            Params params = new Params();
            List<Param> paramList = new ArrayList<>();

            Param param1 = new Param();
            com.companion.local.request.Value value1 = new com.companion.local.request.Value();
            value1.setString(TERMINAL_ID);
            param1.setValue(value1);
            paramList.add(param1);

            Param param2 = new Param();
            com.companion.local.request.Value value2 = new com.companion.local.request.Value();
            value2.setString(request.getCardIdentifier());
            param2.setValue(value2);
            paramList.add(param2);

            Param param3 = new Param();
            com.companion.local.request.Value value3 = new com.companion.local.request.Value();
            value3.setString(request.getTransactionId());
            param3.setValue(value3);
            paramList.add(param3);

            Param param4 = new Param();
            com.companion.local.request.Value value4 = new com.companion.local.request.Value();
            value4.setDateTimeIso8601(request.getTransactionDate());
            param4.setValue(value4);
            paramList.add(param4);

            Param param5 = new Param();
            com.companion.local.request.Value value5 = new com.companion.local.request.Value();
            value5.setString("");
            param5.setValue(value5);
            paramList.add(param5);


            params.setParam(paramList);
            methodCall.setMethodName("ActivateCard");
            methodCall.setParams(params);

            String responseFromCompanionApi = null;

            try {
                responseFromCompanionApi = restTemplate.postForObject(companionTutukaEndpoint, generateRequestXmlString(methodCall), String.class);
                DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                InputSource is = new InputSource();
                is.setCharacterStream(new StringReader(responseFromCompanionApi));

                Document doc = db.parse(is);
                NodeList nodes = doc.getElementsByTagName(VALUE);

                for (int i = 0; i < nodes.getLength(); i++) {
                    Element element = (Element) nodes.item(i);
                    NodeList name = element.getElementsByTagName(STRING);
                    response.setResponseStatus(getCharacterDataFromElement((Element) name.item(0)));
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.setResponseStatus(FAILED);
            }
            responseList.add(response);
        }
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/local.changePin")
    public ResponseEntity<List<CardResponse>> changePin(@RequestHeader MultiValueMap<String, String> headers, @RequestBody List<CardRequest> requestList) {

        List<CardResponse> responseList = new ArrayList<CardResponse>();
        for (CardRequest request : requestList) {
            CardResponse response = new CardResponse();
            response.setCardIdentifier(request.getCardIdentifier());
            MethodCall methodCall = new MethodCall();
            Params params = new Params();
            List<Param> paramList = new ArrayList<>();

            Param param1 = new Param();
            com.companion.local.request.Value value1 = new com.companion.local.request.Value();
            value1.setString(TERMINAL_ID);
            param1.setValue(value1);
            paramList.add(param1);

            Param param2 = new Param();
            com.companion.local.request.Value value2 = new com.companion.local.request.Value();
            value2.setString(request.getReference());
            param2.setValue(value2);
            paramList.add(param2);

            Param param3 = new Param();
            com.companion.local.request.Value value3 = new com.companion.local.request.Value();
            value3.setString(request.getCardIdentifier());
            param3.setValue(value3);
            paramList.add(param3);

            Param param4 = new Param();
            com.companion.local.request.Value value4 = new com.companion.local.request.Value();
            value4.setString(request.getNewPIN());
            param4.setValue(value4);
            paramList.add(param4);

            Param param5 = new Param();
            com.companion.local.request.Value value5 = new com.companion.local.request.Value();
            value5.setString(request.getTransactionId());
            param5.setValue(value5);
            paramList.add(param5);

            Param param6 = new Param();
            com.companion.local.request.Value value6 = new com.companion.local.request.Value();
            value6.setDateTimeIso8601(request.getTransactionDate());
            param6.setValue(value6);
            paramList.add(param6);

            Param param7 = new Param();
            com.companion.local.request.Value value7 = new com.companion.local.request.Value();
            value7.setString("");
            param7.setValue(value7);
            paramList.add(param7);


            params.setParam(paramList);
            methodCall.setMethodName("ChangePin");
            methodCall.setParams(params);

            String responseFromCompanionApi = null;

            try {
                responseFromCompanionApi = restTemplate.postForObject(companionTutukaEndpoint, generateRequestXmlString(methodCall), String.class);
                DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                InputSource is = new InputSource();
                is.setCharacterStream(new StringReader(responseFromCompanionApi));

                Document doc = db.parse(is);
                NodeList nodes = doc.getElementsByTagName(VALUE);

                for (int i = 0; i < nodes.getLength(); i++) {
                    Element element = (Element) nodes.item(i);
                    NodeList name = element.getElementsByTagName(STRING);
                    response.setResponseStatus(getCharacterDataFromElement((Element) name.item(0)));
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.setResponseStatus(FAILED);
            }
            responseList.add(response);
        }
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/local.getActiveLinkedCards")
    public ResponseEntity<List<CreateLinkedCardResponse>> getActiveLinkedCards(@RequestHeader MultiValueMap<String, String> headers, @RequestBody CreateLinkedCardRequest request) {
        List<CreateLinkedCardResponse> responseList = new ArrayList<CreateLinkedCardResponse>();
        MethodCall methodCall = new MethodCall();
        Params params = new Params();
        List<Param> paramList = new ArrayList<>();

        Param param1 = new Param();
        com.companion.local.request.Value value1 = new com.companion.local.request.Value();
        value1.setString(TERMINAL_ID);
        param1.setValue(value1);
        paramList.add(param1);

        Param param2 = new Param();
        com.companion.local.request.Value value2 = new com.companion.local.request.Value();
        value2.setString(request.getReference());
        param2.setValue(value2);
        paramList.add(param2);

        Param param3 = new Param();
        com.companion.local.request.Value value3 = new com.companion.local.request.Value();
        value3.setString(request.getTransactionId());
        param3.setValue(value3);
        paramList.add(param3);

        Param param4 = new Param();
        com.companion.local.request.Value value4 = new com.companion.local.request.Value();
        value4.setDateTimeIso8601(request.getTransactionDate());
        param4.setValue(value4);
        paramList.add(param4);

        Param param5 = new Param();
        com.companion.local.request.Value value5 = new com.companion.local.request.Value();
        value5.setString("");
        param5.setValue(value5);
        paramList.add(param5);


        params.setParam(paramList);
        methodCall.setMethodName("GetActiveLinkedCards");
        methodCall.setParams(params);

        String responseFromCompanionApi = null;

        try {
            responseFromCompanionApi = restTemplate.postForObject(companionTutukaEndpoint, generateRequestXmlString(methodCall), String.class);
            String dataString = responseFromCompanionApi != null ? responseFromCompanionApi.substring(responseFromCompanionApi.indexOf("<array>") + 7, responseFromCompanionApi.indexOf("</array>")) : null;

            JAXBContext marshal = JAXBContext.newInstance(Data.class);
            Unmarshaller unmarshaller = marshal.createUnmarshaller();

            StringReader reader = new StringReader(dataString);
            Data data = (Data) unmarshaller.unmarshal(reader);

            for (com.companion.local.model.LinkedCards.Value value : data.getValue()) {
                CreateLinkedCardResponse response = new CreateLinkedCardResponse();
                response.setCvv(value.getStruct().getMember().get(0).getValue().getString());
                response.setCardNumber(value.getStruct().getMember().get(1).getValue().getString());
                response.setExpiryDate(value.getStruct().getMember().get(2).getValue().getString());
                response.setTrackingNumber(value.getStruct().getMember().get(4).getValue().getString());
                responseList.add(response);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(responseList, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/local.retireCard")
    public ResponseEntity<List<CardResponse>> retireCard(@RequestHeader MultiValueMap<String, String> headers, @RequestBody List<RetireCardRequest> requestList) {

        List<CardResponse> responseList = new ArrayList<CardResponse>();
        for (RetireCardRequest request : requestList) {
            CardResponse response = new CardResponse();
            response.setCardIdentifier(request.getCardIdentifier());
            MethodCall methodCall = new MethodCall();
            Params params = new Params();
            List<Param> paramList = new ArrayList<>();

            Param param1 = new Param();
            com.companion.local.request.Value value1 = new com.companion.local.request.Value();
            value1.setString(TERMINAL_ID);
            param1.setValue(value1);
            paramList.add(param1);

            Param param2 = new Param();
            com.companion.local.request.Value value2 = new com.companion.local.request.Value();
            value2.setString(request.getReference());
            param2.setValue(value2);
            paramList.add(param2);

            Param param3 = new Param();
            com.companion.local.request.Value value3 = new com.companion.local.request.Value();
            value3.setString(request.getCardIdentifier());
            param3.setValue(value3);
            paramList.add(param3);

            Param param4 = new Param();
            com.companion.local.request.Value value4 = new com.companion.local.request.Value();
            value4.setString(request.getTransactionId());
            param4.setValue(value4);
            paramList.add(param4);

            Param param5 = new Param();
            com.companion.local.request.Value value5 = new com.companion.local.request.Value();
            value5.setDateTimeIso8601(request.getTransactionDate());
            param5.setValue(value5);
            paramList.add(param5);

            Param param6 = new Param();
            com.companion.local.request.Value value6 = new com.companion.local.request.Value();
            value6.setString("");
            param6.setValue(value6);
            paramList.add(param6);


            params.setParam(paramList);
            methodCall.setMethodName("RetireCard");
            methodCall.setParams(params);

            String responseFromCompanionApi = null;

            try {
                responseFromCompanionApi = restTemplate.postForObject(companionTutukaEndpoint, generateRequestXmlString(methodCall), String.class);
                DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                InputSource is = new InputSource();
                is.setCharacterStream(new StringReader(responseFromCompanionApi));

                Document doc = db.parse(is);
                NodeList nodes = doc.getElementsByTagName(VALUE);

                for (int i = 0; i < nodes.getLength(); i++) {
                    Element element = (Element) nodes.item(i);
                    NodeList name = element.getElementsByTagName(STRING);
                    response.setResponseStatus(getCharacterDataFromElement((Element) name.item(0)));
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.setResponseStatus(FAILED);
            }
            responseList.add(response);
        }
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/local.updateBearer")
    public ResponseEntity<List<CardResponse>> updateBearer(@RequestHeader MultiValueMap<String, String> headers, @RequestBody List<UpdateBearerRequest> requestList) {

        List<CardResponse> responseList = new ArrayList<CardResponse>();
        for (UpdateBearerRequest request : requestList) {
            CardResponse response = new CardResponse();
            response.setCardIdentifier(request.getCardIdentifier());
            MethodCall methodCall = new MethodCall();
            Params params = new Params();
            List<Param> paramList = new ArrayList<>();

            Param param1 = new Param();
            com.companion.local.request.Value value1 = new com.companion.local.request.Value();
            value1.setString(TERMINAL_ID);
            param1.setValue(value1);
            paramList.add(param1);

            Param param2 = new Param();
            com.companion.local.request.Value value2 = new com.companion.local.request.Value();
            value2.setString(request.getReference());
            param2.setValue(value2);
            paramList.add(param2);

            Param param3 = new Param();
            com.companion.local.request.Value value3 = new com.companion.local.request.Value();
            value3.setString(request.getCardIdentifier());
            param3.setValue(value3);
            paramList.add(param3);

            Param param4 = new Param();
            com.companion.local.request.Value value4 = new com.companion.local.request.Value();
            value4.setString(request.getFirstName());
            param4.setValue(value4);
            paramList.add(param4);

            Param param5 = new Param();
            com.companion.local.request.Value value5 = new com.companion.local.request.Value();
            value5.setString(request.getLastName());
            param5.setValue(value5);
            paramList.add(param5);

            Param param6 = new Param();
            com.companion.local.request.Value value6 = new com.companion.local.request.Value();
            value6.setString(request.getIdOrPassport());
            param6.setValue(value6);
            paramList.add(param6);

            Param param7 = new Param();
            com.companion.local.request.Value value7 = new com.companion.local.request.Value();
            value7.setString(request.getCellphoneNumber());
            param7.setValue(value7);
            paramList.add(param7);

            Param param8 = new Param();
            com.companion.local.request.Value value8 = new com.companion.local.request.Value();
            value8.setString(request.getTransactionId());
            param8.setValue(value8);
            paramList.add(param8);

            Param param9 = new Param();
            com.companion.local.request.Value value9 = new com.companion.local.request.Value();
            value9.setDateTimeIso8601(request.getTransactionDate());
            param9.setValue(value9);
            paramList.add(param9);

            Param param10 = new Param();
            com.companion.local.request.Value value10 = new com.companion.local.request.Value();
            value10.setString("");
            param10.setValue(value10);
            paramList.add(param10);

            params.setParam(paramList);
            methodCall.setMethodName("UpdateBearer");
            methodCall.setParams(params);

            String responseFromCompanionApi = null;

            try {
                responseFromCompanionApi = restTemplate.postForObject(companionTutukaEndpoint, generateRequestXmlString(methodCall), String.class);
                DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                InputSource is = new InputSource();
                is.setCharacterStream(new StringReader(responseFromCompanionApi));

                Document doc = db.parse(is);
                NodeList nodes = doc.getElementsByTagName(VALUE);

                for (int i = 0; i < nodes.getLength(); i++) {
                    Element element = (Element) nodes.item(i);
                    NodeList name = element.getElementsByTagName(STRING);
                    response.setResponseStatus(getCharacterDataFromElement((Element) name.item(0)));
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.setResponseStatus(FAILED);
            }
            responseList.add(response);
        }
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/local.stopCard")
    public ResponseEntity<List<CardResponse>> stopCard(@RequestHeader MultiValueMap<String, String> headers, @RequestBody List<StopCardRequest> requestList) {

        List<CardResponse> responseList = new ArrayList<CardResponse>();
        for (StopCardRequest request : requestList) {
            CardResponse response = new CardResponse();
            response.setCardIdentifier(request.getCardIdentifier());
            MethodCall methodCall = new MethodCall();
            Params params = new Params();
            List<Param> paramList = new ArrayList<>();

            Param param1 = new Param();
            com.companion.local.request.Value value1 = new com.companion.local.request.Value();
            value1.setString(TERMINAL_ID);
            param1.setValue(value1);
            paramList.add(param1);

            Param param2 = new Param();
            com.companion.local.request.Value value2 = new com.companion.local.request.Value();
            value2.setString(request.getReference());
            param2.setValue(value2);
            paramList.add(param2);

            Param param3 = new Param();
            com.companion.local.request.Value value3 = new com.companion.local.request.Value();
            value3.setString(request.getCardIdentifier());
            param3.setValue(value3);
            paramList.add(param3);

            Param param4 = new Param();
            com.companion.local.request.Value value4 = new com.companion.local.request.Value();
            value4.setString(request.getReasonID());
            param4.setValue(value4);
            paramList.add(param4);

            Param param5 = new Param();
            com.companion.local.request.Value value5 = new com.companion.local.request.Value();
            value5.setString(request.getNote());
            param5.setValue(value5);
            paramList.add(param5);


            Param param6 = new Param();
            com.companion.local.request.Value value6 = new com.companion.local.request.Value();
            value6.setString(request.getTransactionId());
            param6.setValue(value6);
            paramList.add(param6);

            Param param7 = new Param();
            com.companion.local.request.Value value7 = new com.companion.local.request.Value();
            value7.setDateTimeIso8601(request.getTransactionDate());
            param7.setValue(value7);
            paramList.add(param7);

            Param param8 = new Param();
            com.companion.local.request.Value value8 = new com.companion.local.request.Value();
            value8.setString("");
            param8.setValue(value8);
            paramList.add(param8);

            params.setParam(paramList);
            methodCall.setMethodName("StopCard");
            methodCall.setParams(params);

            String responseFromCompanionApi = null;

            try {
                responseFromCompanionApi = restTemplate.postForObject(companionTutukaEndpoint, generateRequestXmlString(methodCall), String.class);
                DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                InputSource is = new InputSource();
                is.setCharacterStream(new StringReader(responseFromCompanionApi));

                Document doc = db.parse(is);
                NodeList nodes = doc.getElementsByTagName(VALUE);

                for (int i = 0; i < nodes.getLength(); i++) {
                    Element element = (Element) nodes.item(i);
                    NodeList name = element.getElementsByTagName(STRING);
                    response.setResponseStatus(getCharacterDataFromElement((Element) name.item(0)));
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.setResponseStatus(FAILED);
            }
            responseList.add(response);
        }
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }


    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/local.printLinkedCard")
    public ResponseEntity<CardResponse> printLinkedCard(@RequestHeader MultiValueMap<String, String> headers, @RequestBody PrintLinkedCardRequest request) {

        ToggleVoucherFeatureRequest toggleVoucherFeatureRequest = new ToggleVoucherFeatureRequest(request.getTransactionId(), request.getCardIdentifier(), "PHYSICAL_CARD_DISABLED", "1", request.getTransactionId(), request.getTransactionDate());
        MultiValueMap<String, String> toggleVoucherHeaders = new HttpHeaders();

        ResponseEntity<CardResponse> cardResponseResponseEntity = toggleVoucherFeature(toggleVoucherHeaders, toggleVoucherFeatureRequest);
        if (((CardResponse) cardResponseResponseEntity.getBody()).getResponseStatus().equals("Approved")) {
            CardResponse response = new CardResponse();
            response.setCardIdentifier(request.getCardIdentifier());
            MethodCall methodCall = new MethodCall();
            Params params = new Params();
            List<Param> paramList = new ArrayList<>();

            Param param1 = new Param();
            com.companion.local.request.Value value1 = new com.companion.local.request.Value();
            value1.setString(TERMINAL_ID);
            param1.setValue(value1);
            paramList.add(param1);

            Param param2 = new Param();
            com.companion.local.request.Value value2 = new com.companion.local.request.Value();
            value2.setString(request.getTitle());
            param2.setValue(value2);
            paramList.add(param2);

            Param param3 = new Param();
            com.companion.local.request.Value value3 = new com.companion.local.request.Value();
            value3.setString(request.getInitials());
            param3.setValue(value3);
            paramList.add(param3);

            Param param4 = new Param();
            com.companion.local.request.Value value4 = new com.companion.local.request.Value();
            value4.setString(request.getLastName());
            param4.setValue(value4);
            paramList.add(param4);

            Param param5 = new Param();
            com.companion.local.request.Value value5 = new com.companion.local.request.Value();
            value5.setString(request.getAddress1());
            param5.setValue(value5);
            paramList.add(param5);

            Param param6 = new Param();
            com.companion.local.request.Value value6 = new com.companion.local.request.Value();
            value6.setString(request.getAddress2());
            param6.setValue(value6);
            paramList.add(param6);

            Param param7 = new Param();
            com.companion.local.request.Value value7 = new com.companion.local.request.Value();
            value7.setString(request.getAddress3());
            param7.setValue(value7);
            paramList.add(param7);


            Param param8 = new Param();
            com.companion.local.request.Value value8 = new com.companion.local.request.Value();
            value8.setString(request.getAddress4());
            param8.setValue(value8);
            paramList.add(param8);

            Param param9 = new Param();
            com.companion.local.request.Value value9 = new com.companion.local.request.Value();
            value9.setString(request.getAddress5());
            param9.setValue(value9);
            paramList.add(param9);

            Param param10 = new Param();
            com.companion.local.request.Value value10 = new com.companion.local.request.Value();
            value10.setString(request.getAdditionalData());
            param10.setValue(value10);
            paramList.add(param10);

            Param param11 = new Param();
            com.companion.local.request.Value value11 = new com.companion.local.request.Value();
            value11.setString(request.getContactNumber());
            param11.setValue(value11);
            paramList.add(param11);

            Param param12 = new Param();
            com.companion.local.request.Value value12 = new com.companion.local.request.Value();
            value12.setString(request.getCardIdentifier());
            param12.setValue(value12);
            paramList.add(param12);

            Param param13 = new Param();
            com.companion.local.request.Value value13 = new com.companion.local.request.Value();
            value13.setString(request.getTransactionId());
            param13.setValue(value13);
            paramList.add(param13);

            Param param14 = new Param();
            com.companion.local.request.Value value14 = new com.companion.local.request.Value();
            value14.setDateTimeIso8601(request.getTransactionDate());
            param14.setValue(value14);
            paramList.add(param14);

            Param param15 = new Param();
            com.companion.local.request.Value value15 = new com.companion.local.request.Value();
            value15.setString("");
            param15.setValue(value15);
            paramList.add(param15);

            params.setParam(paramList);
            methodCall.setMethodName("PrintLinkedCard");
            methodCall.setParams(params);

            String responseFromCompanionApi = null;

            try {
                responseFromCompanionApi = restTemplate.postForObject(companionTutukaEndpoint, generateRequestXmlString(methodCall), String.class);
                DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                InputSource is = new InputSource();
                is.setCharacterStream(new StringReader(responseFromCompanionApi));

                Document doc = db.parse(is);
                NodeList nodes = doc.getElementsByTagName(VALUE);

                for (int i = 0; i < nodes.getLength(); i++) {
                    Element element = (Element) nodes.item(i);
                    NodeList name = element.getElementsByTagName(STRING);
                    response.setResponseStatus(getCharacterDataFromElement((Element) name.item(0)));
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.setResponseStatus(FAILED);
            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            CardResponse response = new CardResponse();
            response.setResponseStatus(((CardResponse) cardResponseResponseEntity.getBody()).getResponseStatus());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/local.unstopCard")
    public ResponseEntity<List<CardResponse>> unstopCard(@RequestHeader MultiValueMap<String, String> headers, @RequestBody List<UnstopCardRequest> requestList) {

        List<CardResponse> responseList = new ArrayList<CardResponse>();
        for (UnstopCardRequest request : requestList) {
            CardResponse response = new CardResponse();
            response.setCardIdentifier(request.getCardIdentifier());
            MethodCall methodCall = new MethodCall();
            Params params = new Params();
            List<Param> paramList = new ArrayList<>();

            Param param1 = new Param();
            com.companion.local.request.Value value1 = new com.companion.local.request.Value();
            value1.setString(TERMINAL_ID);
            param1.setValue(value1);
            paramList.add(param1);

            Param param2 = new Param();
            com.companion.local.request.Value value2 = new com.companion.local.request.Value();
            value2.setString(request.getReference());
            param2.setValue(value2);
            paramList.add(param2);

            Param param3 = new Param();
            com.companion.local.request.Value value3 = new com.companion.local.request.Value();
            value3.setString(request.getCardIdentifier());
            param3.setValue(value3);
            paramList.add(param3);

            Param param4 = new Param();
            com.companion.local.request.Value value4 = new com.companion.local.request.Value();
            value4.setString(request.getNote());
            param4.setValue(value4);
            paramList.add(param4);

            Param param5 = new Param();
            com.companion.local.request.Value value5 = new com.companion.local.request.Value();
            value5.setString(request.getTransactionId());
            param5.setValue(value5);
            paramList.add(param5);

            Param param6 = new Param();
            com.companion.local.request.Value value6 = new com.companion.local.request.Value();
            value6.setDateTimeIso8601(request.getTransactionDate());
            param6.setValue(value6);
            paramList.add(param6);

            Param param7 = new Param();
            com.companion.local.request.Value value7 = new com.companion.local.request.Value();
            value7.setString("");
            param7.setValue(value7);
            paramList.add(param7);


            params.setParam(paramList);
            methodCall.setMethodName("UnstopCard");
            methodCall.setParams(params);

            String responseFromCompanionApi = null;

            try {
                responseFromCompanionApi = restTemplate.postForObject(companionTutukaEndpoint, generateRequestXmlString(methodCall), String.class);
                DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                InputSource is = new InputSource();
                is.setCharacterStream(new StringReader(responseFromCompanionApi));

                Document doc = db.parse(is);
                NodeList nodes = doc.getElementsByTagName(VALUE);

                for (int i = 0; i < nodes.getLength(); i++) {
                    Element element = (Element) nodes.item(i);
                    NodeList name = element.getElementsByTagName(STRING);
                    response.setResponseStatus(getCharacterDataFromElement((Element) name.item(0)));
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.setResponseStatus(FAILED);
            }
            responseList.add(response);
        }
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/local.toggleVoucherFeature")
    public ResponseEntity<CardResponse> toggleVoucherFeature(@RequestHeader MultiValueMap<String, String> headers, @RequestBody ToggleVoucherFeatureRequest request) {

            CardResponse response = new CardResponse();
            response.setCardIdentifier(request.getCardIdentifier());
            MethodCall methodCall = new MethodCall();
            Params params = new Params();
            List<Param> paramList = new ArrayList<>();

            Param param1 = new Param();
            com.companion.local.request.Value value1 = new com.companion.local.request.Value();
            value1.setString(TERMINAL_ID);
            param1.setValue(value1);
            paramList.add(param1);

            Param param2 = new Param();
            com.companion.local.request.Value value2 = new com.companion.local.request.Value();
            value2.setString(request.getReference());
            param2.setValue(value2);
            paramList.add(param2);

            Param param3 = new Param();
            com.companion.local.request.Value value3 = new com.companion.local.request.Value();
            value3.setString(request.getCardIdentifier());
            param3.setValue(value3);
            paramList.add(param3);

            Param param4 = new Param();
            com.companion.local.request.Value value4 = new com.companion.local.request.Value();
            value4.setString(request.getFeatureName());
            param4.setValue(value4);
            paramList.add(param4);

            Param param5 = new Param();
            com.companion.local.request.Value value5 = new com.companion.local.request.Value();
            value5.set_boolean(request.getFeatureStatus());
            param5.setValue(value5);
            paramList.add(param5);


            Param param6 = new Param();
            com.companion.local.request.Value value6 = new com.companion.local.request.Value();
            value6.setString(request.getTransactionId());
            param6.setValue(value6);
            paramList.add(param6);

            Param param7 = new Param();
            com.companion.local.request.Value value7 = new com.companion.local.request.Value();
            value7.setDateTimeIso8601(request.getTransactionDate());
            param7.setValue(value7);
            paramList.add(param7);

            Param param8 = new Param();
            com.companion.local.request.Value value8 = new com.companion.local.request.Value();
            value8.setString("");
            param8.setValue(value8);
            paramList.add(param8);

            params.setParam(paramList);
            methodCall.setMethodName("ToggleVoucherFeature");
            methodCall.setParams(params);

            String responseFromCompanionApi = null;

            try {
                responseFromCompanionApi = restTemplate.postForObject(companionTutukaEndpoint, generateRequestXmlString(methodCall), String.class);
                DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                InputSource is = new InputSource();
                is.setCharacterStream(new StringReader(responseFromCompanionApi));

                Document doc = db.parse(is);
                NodeList nodes = doc.getElementsByTagName(VALUE);

                for (int i = 0; i < nodes.getLength(); i++) {
                    Element element = (Element) nodes.item(i);
                    NodeList name = element.getElementsByTagName(STRING);
                    response.setResponseStatus(getCharacterDataFromElement((Element) name.item(0)));
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.setResponseStatus(FAILED);
            }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * generates the xml rpc string request
     *
     * @param request
     * @return
     * @throws Exception
     */
    private String generateRequestXmlString(@RequestBody MethodCall request) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, JAXBException {
        List<com.companion.local.request.Value> valueList = request.getParams().getParam().stream().map(s -> s.getValue()).toList();
        String requestData = request.getMethodName();
        int count = 0;
        for (com.companion.local.request.Value val : valueList) {
            requestData = requestData.concat(val.getString() != null ? val.getString() : val.getDateTimeIso8601() != null ? val.getDateTimeIso8601() : val.get_boolean());
            ++count;
            if (count == valueList.size() - 1)
                break;
        }
        String encodedChecksum = encode(companionTutukaTerminalKey, requestData);
        request.getParams().getParam().get(valueList.size() - 1).getValue().setString(encodedChecksum);
        JAXBContext marshal = JAXBContext.newInstance(MethodCall.class);
        Marshaller marshallerObj = marshal.createMarshaller();
        marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        StringWriter sw = new StringWriter();
        marshallerObj.marshal(request, sw);
        String req = sw.toString();
        req = req.replace("dateTimeIso8601", "dateTime.iso8601");
        req = req.replace("_boolean", "boolean");
        return req;
    }

    /**
     * generate the HMAC hex encoded checksum
     *
     * @param key
     * @param data
     * @return
     * @throws Exception
     */
    private static String encode(String key, String data) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
        sha256_HMAC.init(secret_key);

        return Hex.encodeHexString(sha256_HMAC.doFinal(data.getBytes("UTF-8")));
    }

    /**
     * Parses the xml children nodes to fetch the values
     *
     * @param element
     * @return
     */
    public static String getCharacterDataFromElement(Element element) {
        Node child = element.getFirstChild();
        if (child instanceof CharacterData) {
            CharacterData cd = (CharacterData) child;
            return cd.getData();
        }
        return "";
    }

    public String getCompanionTutukaEndpoint() {
        return companionTutukaEndpoint;
    }

    public void setCompanionTutukaEndpoint(String companionTutukaEndpoint) {
        this.companionTutukaEndpoint = companionTutukaEndpoint;
    }

    public String getCompanionTutukaTerminalKey() {
        return companionTutukaTerminalKey;
    }

    public void setCompanionTutukaTerminalKey(String companionTutukaTerminalKey) {
        this.companionTutukaTerminalKey = companionTutukaTerminalKey;
    }
}
