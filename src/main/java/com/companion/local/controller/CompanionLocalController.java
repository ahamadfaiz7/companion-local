package com.companion.local.controller;


import com.companion.local.model.CreateLinkedCardRequest;
import com.companion.local.model.CreateLinkedCardResponse;
import com.companion.local.request.MethodCall;
import com.companion.local.request.Param;
import com.companion.local.request.Params;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
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
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api")
public class CompanionLocalController {

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
        value1.setString("0030069501");
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
            NodeList nodes = doc.getElementsByTagName("value");

            for (int i = 0; i < nodes.getLength(); i++) {
                Element element = (Element) nodes.item(i);
                NodeList name = element.getElementsByTagName("string");
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

    /**
     * generates the xml rpc string request
     *
     * @param request
     * @return
     * @throws Exception
     */
    private String generateRequestXmlString(@RequestBody MethodCall request) throws Exception {
        List<com.companion.local.request.Value> valueList = request.getParams().getParam().stream().map(s -> s.getValue()).collect(Collectors.toList());
        String requestData = request.getMethodName();
        int count = 0;
        for (com.companion.local.request.Value val : valueList) {
            requestData = requestData.concat(val.getString() != null ? val.getString() : val.getDateTimeIso8601());
            count = ++count;
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
    private static String encode(String key, String data) throws Exception {
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
}
