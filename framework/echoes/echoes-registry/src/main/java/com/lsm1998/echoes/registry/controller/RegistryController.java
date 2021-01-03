package com.lsm1998.echoes.registry.controller;

import com.lsm1998.echoes.common.domain.AjaxData;
import com.lsm1998.echoes.registry.service.RegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("echoes")
@RestController
@CrossOrigin
public class RegistryController
{
    @Autowired
    private RegistryService registryService;

    /**
     * 全部节点信息
     *
     * @return
     */
    @GetMapping
    public AjaxData index()
    {
        return AjaxData.success(registryService.get());
    }

    /**
     * 注册一个节点
     *
     * @param request
     * @param serviceName
     * @param port
     * @return
     */
    @PostMapping
    public AjaxData registry(HttpServletRequest request, @RequestParam String serviceName, @RequestParam Integer port)
    {
        registryService.registry(serviceName, request.getRemoteAddr(), port);
        return AjaxData.success();
    }

    @GetMapping("/{name}")
    public AjaxData election(@PathVariable(name = "name") String serviceName)
    {
        return AjaxData.success(registryService.electionNode(serviceName));
    }
}
