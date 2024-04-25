package com.fmu.modules.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.JSONObject;
import com.fmu.common.config.RuoYiConfig;
import com.fmu.common.constant.Constants;
import com.fmu.common.utils.StringUtils;
import com.fmu.modules.domain.SysSingleModule;
import no.ntnu.ihb.fmi4j.importer.fmi1.CoSimulationSlave;
import no.ntnu.ihb.fmi4j.importer.fmi1.Fmu;
import no.ntnu.ihb.fmi4j.modeldescription.CoSimulationModelDescription;
import no.ntnu.ihb.fmi4j.modeldescription.variables.TypedScalarVariable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.fmu.common.annotation.Log;
import com.fmu.common.core.controller.BaseController;
import com.fmu.common.core.domain.AjaxResult;
import com.fmu.common.enums.BusinessType;
import com.fmu.modules.domain.SysModules;
import com.fmu.modules.service.ISysModulesService;
import com.fmu.common.utils.poi.ExcelUtil;
import com.fmu.common.core.page.TableDataInfo;

/**
 * 模型信息Controller
 * 
 * @author mm
 * @date 2024-04-23
 */
@RestController
@RequestMapping("/modules/modulesInfo")
public class SysModulesController extends BaseController
{
    @Autowired
    private ISysModulesService sysModulesService;
    /**
     * 上传模型信息返回
     */
    //@PreAuthorize("@ss.hasPermi('modules:modulesInfo:read')")
    @PostMapping("/read" )
    public SysSingleModule readInfo(@RequestBody Map<String,String> requestBody) throws IOException {
        //String decodedPath = URLDecoder.decode(path, StandardCharsets.UTF_8);
        String filePath = requestBody.get("filePath");
        // 本地资源路径
        String localPath = RuoYiConfig.getProfile();
        // 数据库资源地址
        String realPath = localPath + StringUtils.substringAfter(filePath, Constants.RESOURCE_PREFIX);
        System.out.println(realPath);
        SysSingleModule sysSingleModule = new SysSingleModule();
        Fmu fmu = Fmu.from(new File(realPath)); //URLs are also supported

        CoSimulationSlave slave = fmu.asCoSimulationFmu().newInstance();

        //JSONObject jsonObject = new JSONObject();//创建jsonObject对象
        List<JSONObject> variableList = new ArrayList<>();
        CoSimulationModelDescription modelDescriptionDes = slave.getModelDescription();

        //读取模型文件信息
        sysSingleModule.setModuleName(modelDescriptionDes.getModelName());//模型名
        sysSingleModule.setFmuVersion(modelDescriptionDes.getFmiVersion());//fmu版本
        sysSingleModule.setAuthor(modelDescriptionDes.getAuthor());//作者
        sysSingleModule.setDescription(modelDescriptionDes.getDescription());//描述

        for (TypedScalarVariable variable : modelDescriptionDes.getModelVariables()) {

            JSONObject variableObject = new JSONObject();
            variableObject.put("序号", variable.getValueReference());
            variableObject.put("变量名", variable.getName());
            variableObject.put("初始值", variable.getStart());
            variableObject.put("因果关系", variable.getCausality());
            variableObject.put("是否变量", variable.getVariability());
            variableObject.put("描述", variable.getDescription());

            variableList.add(variableObject);
        }
        fmu.close();
        String jsonString = JSON.toJSONString(variableList,JSONWriter.Feature.WriteMapNullValue);
        sysSingleModule.setParameterList(jsonString);
        System.out.println(sysSingleModule.getParameterList());
        return sysSingleModule;
    }


    /**
     * 查询模型信息列表
     */
    @PreAuthorize("@ss.hasPermi('modules:modulesInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysModules sysModules)
    {
        startPage();
        List<SysModules> list = sysModulesService.selectSysModulesList(sysModules);
        return getDataTable(list);
    }

    /**
     * 导出模型信息列表
     */
    @PreAuthorize("@ss.hasPermi('modules:modulesInfo:export')")
    @Log(title = "模型信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysModules sysModules)
    {
        List<SysModules> list = sysModulesService.selectSysModulesList(sysModules);
        ExcelUtil<SysModules> util = new ExcelUtil<SysModules>(SysModules.class);
        util.exportExcel(response, list, "模型信息数据");
    }

    /**
     * 获取模型信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('modules:modulesInfo:query')")
    @GetMapping(value = "/{moduleId}")
    public AjaxResult getInfo(@PathVariable("moduleId") Long moduleId)
    {
        return success(sysModulesService.selectSysModulesByModuleId(moduleId));
    }

    /**
     * 新增模型信息
     */
    @PreAuthorize("@ss.hasPermi('modules:modulesInfo:add')")
    @Log(title = "模型信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysModules sysModules)
    {
        if(!StringUtils.isBlank(sysModules.getDescription())){
            sysModules.setDescription(sysModules.getDescription().replaceAll("<[/]?p>", ""));
        }
        if(!StringUtils.isBlank(sysModules.getParameterList())){
            sysModules.setParameterList(sysModules.getParameterList().replaceAll("<[/]?p>", ""));
        }
        System.out.println(sysModules.toString());
        return toAjax(sysModulesService.insertSysModules(sysModules));
    }

    /**
     * 修改模型信息
     */
    @PreAuthorize("@ss.hasPermi('modules:modulesInfo:edit')")
    @Log(title = "模型信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysModules sysModules)
    {
        if(!StringUtils.isBlank(sysModules.getDescription())){
            sysModules.setDescription(sysModules.getDescription().replaceAll("<[/]?p>", ""));
        }
        if(!StringUtils.isBlank(sysModules.getParameterList())){
            sysModules.setParameterList(sysModules.getParameterList().replaceAll("<[/]?p>", ""));
        }
        // System.out.println(sysModules.toString());
        // System.out.println(sysModules.getDescription());
        return toAjax(sysModulesService.updateSysModules(sysModules));
    }

    /**
     * 删除模型信息
     */
    @PreAuthorize("@ss.hasPermi('modules:modulesInfo:remove')")
    @Log(title = "模型信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{moduleIds}")
    public AjaxResult remove(@PathVariable Long[] moduleIds)
    {
        return toAjax(sysModulesService.deleteSysModulesByModuleIds(moduleIds));
    }
}
