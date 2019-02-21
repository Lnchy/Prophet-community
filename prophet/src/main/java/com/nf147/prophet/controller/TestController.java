package com.nf147.prophet.controller;

import com.nf147.prophet.service.Impl.RoleRelatedServiceImpl;
import com.nf147.prophet.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    RoleRelatedServiceImpl roleRelatedService;

    @GetMapping("/role")
    public Result testRoleUser() {
        return Result.status(true).body(roleRelatedService.userIsRole(1007, "user"));
    }
}
