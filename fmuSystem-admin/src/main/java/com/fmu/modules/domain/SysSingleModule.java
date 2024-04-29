package com.fmu.modules.domain;


/**
 * @author 马蒙
 * @date 2024/4/24 15:39
 */
public class SysSingleModule {
    /**
     * 模型名称
     */
    private String moduleName;

    /**
     * FMU 版本
     */
    private String fmuVersion;

    /**
     * 作者
     */
    private String author;

    /**
     * 模型版本号
     */
    private String versionNumber;

    /**
     * 描述
     */

    private String description;

    /**
     * 参数列表
     */

    private String parameterList;

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getFmuVersion() {
        return fmuVersion;
    }

    public void setFmuVersion(String fmuVersion) {
        this.fmuVersion = fmuVersion;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParameterList() {
        return parameterList;
    }

    public void setParameterList(String parameterList) {
        this.parameterList = parameterList;
    }
}
