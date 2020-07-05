package com.sy.shope.controller.admin;

import com.sy.shope.entity.Spec;
import com.sy.shope.entity.SpecTemplate;
import com.sy.shope.service.facade.ISpecTemplateService;
import com.sy.shope.support.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 规格 后台
 * @author wangxiao
 * @since 1.1
 */
@RestController
@RequestMapping("/admin/spec")
public class AdminSpecController {


    @Autowired
    private ISpecTemplateService specTemplateService;

    @GetMapping("/template")
    public ResponseEntity<JsonResult<List<SpecTemplate>>> getSpecTemplates () {
        return ResponseEntity.ok(JsonResult.success(specTemplateService.list()));
    }





}
