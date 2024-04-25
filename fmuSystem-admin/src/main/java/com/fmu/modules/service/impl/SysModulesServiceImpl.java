package com.fmu.modules.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fmu.modules.mapper.SysModulesMapper;
import com.fmu.modules.domain.SysModules;
import com.fmu.modules.service.ISysModulesService;

/**
 * 模型信息Service业务层处理
 * 
 * @author mm
 * @date 2024-04-23
 */
@Service
public class SysModulesServiceImpl implements ISysModulesService 
{
    @Autowired
    private SysModulesMapper sysModulesMapper;

    /**
     * 查询模型信息
     * 
     * @param moduleId 模型信息主键
     * @return 模型信息
     */
    @Override
    public SysModules selectSysModulesByModuleId(Long moduleId)
    {
        return sysModulesMapper.selectSysModulesByModuleId(moduleId);
    }

    /**
     * 查询模型信息列表
     * 
     * @param sysModules 模型信息
     * @return 模型信息
     */
    @Override
    public List<SysModules> selectSysModulesList(SysModules sysModules)
    {
        return sysModulesMapper.selectSysModulesList(sysModules);
    }

    /**
     * 新增模型信息
     * 
     * @param sysModules 模型信息
     * @return 结果
     */
    @Override
    public int insertSysModules(SysModules sysModules)
    {
        return sysModulesMapper.insertSysModules(sysModules);
    }

    /**
     * 修改模型信息
     * 
     * @param sysModules 模型信息
     * @return 结果
     */
    @Override
    public int updateSysModules(SysModules sysModules)
    {
        return sysModulesMapper.updateSysModules(sysModules);
    }

    /**
     * 批量删除模型信息
     * 
     * @param moduleIds 需要删除的模型信息主键
     * @return 结果
     */
    @Override
    public int deleteSysModulesByModuleIds(Long[] moduleIds)
    {
        return sysModulesMapper.deleteSysModulesByModuleIds(moduleIds);
    }

    /**
     * 删除模型信息信息
     * 
     * @param moduleId 模型信息主键
     * @return 结果
     */
    @Override
    public int deleteSysModulesByModuleId(Long moduleId)
    {
        return sysModulesMapper.deleteSysModulesByModuleId(moduleId);
    }
}
