-- DROP TABLE IF EXISTS sys_modules;
-- CREATE TABLE sys_modules (
--     module_id BIGINT AUTO_INCREMENT COMMENT '模型id',
-- 		dept_id BIGINT COMMENT '分类ID',
--     dept_name VARCHAR(255) COMMENT '分类名称',
--     module_name VARCHAR(255) NOT NULL COMMENT '模型名称',
--     fmu_version VARCHAR(50) COMMENT 'FMU 版本',
--     author VARCHAR(100) COMMENT '作者',
--     version_number VARCHAR(20) COMMENT '模型版本号',
--     development_date DATE COMMENT '开发日期',
--     upload_date DATE COMMENT '上传日期',
--     description TEXT COMMENT '描述',
-- 		module_path VARCHAR(255) COMMENT '模型地址路径',
--     parameter_list JSON COMMENT '参数列表',
--     PRIMARY KEY(module_id),
--     FOREIGN KEY (dept_id) REFERENCES sys_dept(dept_id)
-- );
-- 
#
查询json数据

select parameter_list - > '$[*].序号'   AS initial_value,
       parameter_list - > '$[*].变量名' AS vavrible
from sys_modules
where parameter_list is not null;

