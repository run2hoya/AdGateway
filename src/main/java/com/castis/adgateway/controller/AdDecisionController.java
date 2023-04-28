package com.castis.adgateway.controller;


import com.castis.adgateway.controller.common.AbstractController;
import com.castis.adgateway.dto.EntityModel;
import com.castis.adgateway.dto.response.v1_5.PlacementResponse;
import com.castis.adgateway.service.AdDecisionService;
import com.castis.adlib.define.Constants;
import com.castis.adlib.define.ResultCode;
import com.castis.adlib.dto.ResultDetail;
import com.castis.adlib.dto.TransactionID;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;


@RestController
@Slf4j
@RequiredArgsConstructor
public class AdDecisionController extends AbstractController {

    private final AdDecisionService adDecisionService;

    @RequestMapping(value = "/PlacementRequest", method = RequestMethod.GET)
    public ResponseEntity<?> placementRequest(HttpServletRequest req, HttpServletResponse response, @RequestParam("VOD_Request_ID") String requestId, @RequestParam("VOD_Session_ID") String messageId) {

        long startTime = System.currentTimeMillis();

        TransactionID trId = null;

        try {
            trId = startLog(req, Constants.request.GET);

            log.info("{} placementRequest requestId = {}, messageId= {}", trId, requestId, messageId);
            PlacementResponse placementResponse = adDecisionService.processDecision(trId, requestId, messageId);

            log.info("{} 광고 요청 결과 placementResponse = {}", trId, placementResponse);


            return new ResponseEntity<>(placementResponse, getHeaders(placementResponse), HttpStatus.OK);

        } catch (Exception e) {
            log.error(trId + "ERROR", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResultCode.INTERNAL_SERVER_ERROR_NAME);
        } finally {
            endLog(startTime, Constants.request.GET, trId, null);
        }

    }





}
