package com.fmu.modules.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.fmu.common.annotation.Excel;
import com.fmu.common.core.domain.BaseEntity;

/**
 * 模型信息对象 sys_modules
 *
 * @author mm
 * @date 2024-04-23
 */
public class SysModules extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 模型id
     */
    private Long moduleId;

    /**
     * 分类ID
     */
    @Excel(name = "分类ID")
    private Long deptId;

    /**
     * 分类名称
     */
    @Excel(name = "分类名称")
    private String deptName;

    /**
     * 模型名称
     */
    @Excel(name = "模型名称")
    private String moduleName;

    /**
     * FMU 版本
     */
    @Excel(name = "FMU 版本")
    private String fmuVersion;

    /**
     * 作者
     */
    @Excel(name = "作者")
    private String author;

    /**
     * 模型版本号
     */
    @Excel(name = "模型版本号")
    private String versionNumber;

    /**
     * 开发日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开发日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date developmentDate;

    /**
     * 上传日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "上传日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date uploadDate;

    /**
     * 描述
     */
    @Excel(name = "描述")
    private String description;

    /**
     * 模型地址路径
     */
    @Excel(name = "模型地址路径")
    private String modulePath;

    /**
     * 参数列表
     */
    @Excel(name = "参数列表")
    private String parameterList;

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setFmuVersion(String fmuVersion) {
        this.fmuVersion = fmuVersion;
    }

    public String getFmuVersion() {
        return fmuVersion;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }

    public String getVersionNumber() {
        return versionNumber;
    }

    public void setDevelopmentDate(Date developmentDate) {
        this.developmentDate = developmentDate;
    }

    public Date getDevelopmentDate() {
        return developmentDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setModulePath(String modulePath) {
        this.modulePath = modulePath;
    }

    public String getModulePath() {
        return modulePath;
    }

    public void setParameterList(String parameterList) {
        this.parameterList = parameterList;
    }

    public String getParameterList() {
        return parameterList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("moduleId", getModuleId())
                .append("deptId", getDeptId())
                .append("deptName", getDeptName())
                .append("moduleName", getModuleName())
                .append("fmuVersion", getFmuVersion())
                .append("author", getAuthor())
                .append("versionNumber", getVersionNumber())
                .append("developmentDate", getDevelopmentDate())
                .append("uploadDate", getUploadDate())
                .append("description", getDescription())
                .append("modulePath", getModulePath())
                .append("parameterList", getParameterList())
                .toString();
    }
}
