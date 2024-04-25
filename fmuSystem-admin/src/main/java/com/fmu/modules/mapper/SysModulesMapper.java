package com.fmu.modules.mapper;

import java.util.List;
import com.fmu.modules.domain.SysModules;

/**
 * 模型信息Mapper接口
 * 
 * @author mm
 * @date 2024-04-23
 */
public interface SysModulesMapper 
{
    /**
     * 查询模型信息
     * 
     * @param moduleId 模型信息主键
     * @return 模型信息
     */
    public SysModules selectSysModulesByModuleId(Long moduleId);

    /**
     * 查询模型信息列表
     * 
     * @param sysModules 模型信息
     * @return 模型信息集合
     */
    public List<SysModules> selectSysModulesList(SysModules sysModules);

    /**
     * 新增模型信息
     * 
     * @param sysModules 模型信息
     * @return 结果
     */
    public int insertSysModules(SysModules sysModules);

    /**
     * 修改模型信息
     * 
     * @param sysModules 模型信息
     * @return 结果
     */
    public int updateSysModules(SysModules sysModules);

    /**
     * 删除模型信息
     * 
     * @param moduleId 模型信息主键
     * @return 结果
     */
    public int deleteSysModulesByModuleId(Long moduleId);

    /**
     * 批量删除模型信息
     * 
     * @param moduleIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysModulesByModuleIds(Long[] moduleIds);
}
