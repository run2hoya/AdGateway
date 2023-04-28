package com.castis.adgateway.controller;


import com.castis.adgateway.controller.common.AbstractController;
import com.castis.adlib.define.Constants;
import com.castis.adlib.define.ResultCode;
import com.castis.adlib.dto.ResultDetail;
import com.castis.adlib.dto.TransactionID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;


@RestController
@Slf4j
@RequiredArgsConstructor
public class SampleController extends AbstractController {


    @RequestMapping(value = "/sample/{param}", method = RequestMethod.GET, produces = "application/json; charset=utf8")
    public ResponseEntity<?> getSample(HttpServletRequest req, @PathVariable("param") String param) {

        long startTime = System.currentTimeMillis();
        ResponseEntity<?> result = null;
        TransactionID trId = null;

        try {
            trId = startLog(req, Constants.request.GET);

            log.info(trId + "hello {}", param);

            result = new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
        } catch (Exception e) {
            log.error(trId + "ERROR", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResultDetail(ResultCode.INTERNAL_SERVER_ERROR, ResultCode.INTERNAL_SERVER_ERROR_NAME,
                    e.getMessage()));
        } finally {
            endLog(startTime, Constants.request.GET, trId, null);
        }

        return result;
    }





}
