package com.fmu.modules.service;

import java.util.List;

import com.fmu.modules.domain.SysModules;

/**
 * 模型信息Service接口
 *
 * @author mm
 * @date 2024-04-23
 */
public interface ISysModulesService {
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
     * 批量删除模型信息
     *
     * @param moduleIds 需要删除的模型信息主键集合
     * @return 结果
     */
    public int deleteSysModulesByModuleIds(Long[] moduleIds);

    /**
     * 删除模型信息信息
     *
     * @param moduleId 模型信息主键
     * @return 结果
     */
    public int deleteSysModulesByModuleId(Long moduleId);
}
