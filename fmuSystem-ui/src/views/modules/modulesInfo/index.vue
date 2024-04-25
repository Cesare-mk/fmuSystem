<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="80px">
      <el-form-item label="分类ID" prop="deptId">
        <el-input
          v-model="queryParams.deptId"
          placeholder="请输入分类ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="分类名称" prop="deptName">
        <el-input
          v-model="queryParams.deptName"
          placeholder="请输入分类名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="模型名称" prop="moduleName">
        <el-input style="width: 220px"
                  v-model="queryParams.moduleName"
                  placeholder="请输入模型名称"
                  clearable
                  @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="FMU版本" prop="fmuVersion">
        <el-input style=""
                  v-model="queryParams.fmuVersion"
                  placeholder="请输入FMU 版本"
                  clearable
                  @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="作者" prop="author">
        <el-input
          v-model="queryParams.author"
          placeholder="请输入作者"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item style="" label="模型版本" prop="versionNumber">
        <el-input
          v-model="queryParams.versionNumber"
          placeholder="请输入模型版本号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="开发日期" prop="developmentDate">
        <el-date-picker clearable
                        v-model="queryParams.developmentDate"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择开发日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="上传日期" prop="uploadDate">
        <el-date-picker clearable
                        v-model="queryParams.uploadDate"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择上传日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['modules:modulesInfo:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['modules:modulesInfo:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['modules:modulesInfo:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['modules:modulesInfo:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="modulesInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="模型id" align="center" prop="moduleId"/>
      <el-table-column label="分类ID" align="center" prop="deptId"/>
      <el-table-column label="分类名称" align="center" prop="deptName"/>
      <el-table-column label="模型名称" align="center" prop="moduleName"/>
      <el-table-column label="FMU 版本" align="center" prop="fmuVersion"/>
      <el-table-column label="作者" align="center" prop="author"/>
      <el-table-column label="模型版本号" align="center" prop="versionNumber"/>
      <el-table-column label="开发日期" align="center" prop="developmentDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.developmentDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="上传日期" align="center" prop="uploadDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.uploadDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="描述" align="center" prop="description"/>
      <el-table-column label="模型地址路径" align="center" prop="modulePath"/>
      <el-table-column label="参数列表" align="center" prop="parameterList"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['modules:modulesInfo:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['modules:modulesInfo:remove']"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改模型信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="分类ID" prop="deptId">
          <el-input v-model="form.deptId" placeholder="请输入分类ID" />
        </el-form-item>
        <el-form-item label="分类名称" prop="deptName">
          <el-cascader
            :options="options"
            v-model="selectedValue"
            @change="handleChange"
            :props="{ checkStrictly: true }"
            clearable>
            <template slot-scope="{ node, data }">
              <span>{{ data.label }}</span>
              <span v-if="!node.isLeaf"> ({{ data.children.length }}) </span>
            </template>
          </el-cascader>
        </el-form-item>
        <el-form-item label="模型名称" prop="moduleName">
          <el-input v-model="form.moduleName" placeholder="请输入模型名称"/>
        </el-form-item>
        <el-form-item label="FMU版本" prop="fmuVersion">
          <el-input v-model="form.fmuVersion" placeholder="请输入FMU 版本"/>
        </el-form-item>
        <el-form-item label="作者" prop="author">
          <el-input v-model="form.author" placeholder="请输入作者"/>
        </el-form-item>
        <el-form-item label="模型版本" prop="versionNumber">
          <el-input v-model="form.versionNumber" placeholder="请输入模型版本号"/>
        </el-form-item>
        <el-form-item label="开发日期" prop="developmentDate">
          <el-date-picker clearable
                          v-model="form.developmentDate"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择开发日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="上传日期" prop="uploadDate">
          <el-date-picker clearable
                          v-model="form.uploadDate"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择上传日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="描述">
          <editor v-model="form.description" :min-height="192"/>
        </el-form-item>
        <el-form-item label="模型路径" prop="modulePath">
          <file-upload v-model="form.modulePath" @handleUploadSuccess="handleFileUploadSuccess"/>
          <el-button type="primary" icon="el-icon-s-finance" @click="readInfo" plain>读取模型信息</el-button>
        </el-form-item>
        <el-form-item label="参数列表">
          <editor v-model="form.parameterList" :min-height="192"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listModulesInfo,
  getModulesInfo,
  delModulesInfo,
  addModulesInfo,
  updateModulesInfo,
  readModulesInfo
} from "@/api/modules/modulesInfo";

export default {
  name: "ModulesInfo",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 模型信息表格数据
      modulesInfoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        deptId: null,
        deptName: null,
        moduleName: null,
        fmuVersion: null,
        author: null,
        versionNumber: null,
        developmentDate: null,
        uploadDate: null,
        description: null,
        modulePath: null,
        parameterList: null
      },
      categoryMap: [
        { deptId: 103, deptName: '齿轮泵' },
        { deptId: 104, deptName: '柱塞泵' },
        { deptId: 201, deptName: '传输带' },
        { deptId: 202, deptName: '其他' },
        ],
      options: [{
        value: '液压模型',
        label: '液压模型',
        children: [
          {
            value: '泵',
            label: '泵',
            children: [
              {
                value: '齿轮泵',
                label: '齿轮泵'
              }, {
                value: '柱塞泵',
                label: '柱塞泵'
              }]
          }, {
            value: '传动',
            label: '传动',
            children: [
              {
                value: '传输带',
                label: '传输带'
              }, {
                value: '其他',
                label: '其他'
              }]
          }],
      }],
      selectedValue: [],//储存级联表中选择的值
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        moduleName: [{required: true, message: "模型名称不能为空", trigger: "blur"}],
        fmuVersion: [{required: true, message: "fmu版本不能为空", trigger: "blur"}],
        author: [{required: true, message: "作者不能为空", trigger: "blur"}],
        versionNumber: [{required: true, message: "模型版本不能为空", trigger: "blur"}],
        parameterList: [{required: true, message: "参数不能为空，类型为JSON数据", trigger: "blur"}],

      }
    };
  },
  created() {
    this.getList();
  },
  methods: {

    handleFileUploadSuccess(){

      console.log(this.form.modulePath)
    },
    /** 读取模型信息*/
    readInfo(){
      readModulesInfo(this.form.modulePath).then(response=>{
        this.form.moduleName= response.moduleName;
        this.form.author= response.author;
        this.form.fmuVersion = response.fmuVersion;
        this.form.description = response.description;
        this.form.parameterList= response.parameterList;
      });
    },
    /** 级联表选择操作选择最后一项 */
      handleChange(value){
        if (value && value.length > 0) {
          // 获取最后一个值作为分类名称
          this.form.deptName = value[value.length - 1];
          // 通过分类名称查找对应的分类ID
          const selectedCategory = this.categoryMap.find(category => category.deptName === this.form.deptName);
          if (selectedCategory) {
            // 更新表单模型中的分类ID
            this.form.deptId = selectedCategory.deptId;
          } else {
            // 如果找不到匹配项，则清空表单模型中的分类ID
            this.form.deptId = null;
          }
        } else {
          // 如果没有选中的值，则清空表单模型中的分类ID
          this.form.deptId = null;
        }
        this.selectedValue = value; // 更新selectedValue以反映当前选择
      },
    /** 查询模型信息列表 */
    getList() {
      this.loading = true;
      listModulesInfo(this.queryParams).then(response => {
        this.modulesInfoList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        moduleId: null,
        deptId: null,
        deptName: null,
        moduleName: null,
        fmuVersion: null,
        author: null,
        versionNumber: null,
        developmentDate: null,
        uploadDate: null,
        description: null,
        modulePath: null,
        parameterList: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.moduleId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加模型信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const moduleId = row.moduleId || this.ids
      getModulesInfo(moduleId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改模型信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.moduleId != null) {
            updateModulesInfo(this.form).then(response => {
              //this.form.description = this.removeAllTags(this.form.description);
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addModulesInfo(this.form).then(response => {
              //this.form.parameterList = this.removeAllTags(this.form.parameterList);
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 去除富文本多余的标签*/
    /*removeAllTags(htmlContent) {
      /!** 匹配所有结果*!/
      //return htmlContent.replace(/<[^>]*>/g,'');
      const regex = /<[^>]*>/g;
      const matches = [];
      let match;
      if (htmlContent.indexOf('<p>') === -1) {
        /!** 如果没有 <p> 标签，则直接将整个字符串作为匹配结果*!/
        matches.push(htmlContent);
      } else {
        while ((match = regex.exec(htmlContent)) !== null) {
          /!** 将匹配到的内容添加到数组中*!/
          matches.push(match[1]);
        }
      }
      return matches;
    },*/
    /** 删除按钮操作 */
    handleDelete(row) {
      const moduleIds = row.moduleId || this.ids;
      this.$modal.confirm('是否确认删除模型信息编号为"' + moduleIds + '"的数据项？').then(function () {
        return delModulesInfo(moduleIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('modules/modulesInfo/export', {
        ...this.queryParams
      }, `modulesInfo_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
<style>

</style>
