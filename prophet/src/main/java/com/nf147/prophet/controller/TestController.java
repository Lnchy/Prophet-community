package com.nf147.prophet.controller;

import com.nf147.prophet.dao.IssueMapper;
import com.nf147.prophet.service.Impl.IssueServiceImpl;
import com.nf147.prophet.service.Impl.RoleRelatedServiceImpl;
import com.nf147.prophet.util.PageHelp;
import com.nf147.prophet.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    RoleRelatedServiceImpl roleRelatedService;

    @GetMapping("/role")
    public Result testRoleUser() {
        return Result.status(true).body(roleRelatedService.userIsRole(1007, "user"));
    }

    @GetMapping("/path")
    public Result testPath() throws FileNotFoundException {
        return Result.status(true).body(this.getClass().getClassLoader().getResource("static").getPath());
    }

    @GetMapping("/page")
    public Result testPageHelp() {
        return Result.status(true).body("测试pageHelp").page(PageHelp.total(20,10,2));
    }

}
