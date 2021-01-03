package com.lsm1998.echoes.registry.controller;

import com.lsm1998.echoes.common.domain.AjaxData;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("echoes")
@RestController
@CrossOrigin
public class ConnectController
{
    @GetMapping("ping")
    public AjaxData ping()
    {
        return AjaxData.success();
    }
}
