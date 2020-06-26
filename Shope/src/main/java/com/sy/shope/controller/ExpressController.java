package com.sy.shope.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.sy.shope.service.impl.ExpressService;
import com.sy.shope.support.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 快递查询
 * @author wangxiao
 * @since 1.1
 */
@RestController
@RequestMapping("/express")
public class ExpressController {

    @Autowired
    private ExpressService expressService;

    @GetMapping("/{number}")
    public ResponseEntity<JsonResult<JsonNode>> getExpressInfoByNumber(@PathVariable String number) {
        return ResponseEntity.ok(JsonResult.success(expressService.getExpressByNu(number)));
    }
}
