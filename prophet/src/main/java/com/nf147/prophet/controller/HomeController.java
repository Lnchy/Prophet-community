package com.nf147.prophet.controller;

import com.nf147.prophet.service.Impl.RalServiceImpl;
import com.nf147.prophet.util.Result;
import com.nf147.prophet.util.validate.NeedLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/index")
public class HomeController {
    @Autowired
    RalServiceImpl ralServicel;

    @GetMapping("/{pageCode}")
    @NeedLogin
    public Result homeReplyList(@PathVariable("pageCode") int pageCode) {
        return ralServicel.getRalList(pageCode, 10);
    }
}
