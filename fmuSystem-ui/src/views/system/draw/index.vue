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
    <el-table v-loading="loading" :data="modulesInfoList">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column fixed label="模型id" align="center" prop="moduleId"/>
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
      <el-table-column width="300" label="参数列表" align="center" prop="parameterList"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['modules:modulesInfo:edit']"
          >绘制
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

    <!-- 模型信息参数对话框 -->
    <el-dialog :title="title" :visible.sync="isOpen" width="1000px" @open="open()" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="参数列表(可修改)">
          <template>
            <el-form :model="formData">
              <el-table style="width: 900px"
                        :data="formData.tableData"
                        border
                        @cell-dblclick="handleCellDoubleClick"
              >
                <el-table-column
                  v-for="column in tableColumns"
                  :key="column.prop"
                  :prop="column.prop"
                  :label="column.label"
                  align="center"
                  header-align="center"
                >
                  <template slot-scope="scope">
                    <el-form-item :prop="'tableData.' + scope.$index + '.' + column.prop">
                      <el-input
                        v-if="scope.row.editing"
                        style="text-align: center;"
                        v-model="scope.row[column.prop]"
                        @blur="handleInputBlur(scope.row, column.prop)"
                        @keyup.enter.native="handleInputEnter(scope.row, column.prop)"
                      />
                      <span v-else style="display: inline-block;width: 100%;text-align: center;color: #1d61d6;">
              <el-button
                type="primary"
                plain
                text
                @click="handleEditClick(scope.row, column.prop)"
              >
                {{ scope.row[column.prop] }}
              </el-button>
            </span>
                    </el-form-item>
                  </template>
                </el-table-column>
              </el-table>
            </el-form>
          </template>
        </el-form-item>
      </el-form>
      <div  class="bordered-div" style="height: 70px; width: 970px;" ></div>
      <div ref="charts" class="bordered-div" style="width: 970px; height: 300px"></div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确定参数</el-button>
        <el-button type="primary" @click="draw">模型可视化</el-button>
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
  drawModule
} from "@/api/modules/modulesInfo";
import {deepClone} from '@/utils/index.js'; // 假设你有一个工具函数用于深拷贝对象
import Vue from 'vue';
import * as echarts from 'echarts';
Vue.prototype.$echarts = echarts
//import show from '@views/draw/show.vue';
export default {
  name: "Draw",
  components: {},
  data() {
    return {
      //画图属性
      option:{
        title: {
          text: "h和v的变化",
          left: 'center',
          top: 0
        },
        grid: [
          { left: '7%', top: '7%', width: '38%', height: '38%' },
          { right: '7%', top: '7%', width: '38%', height: '38%' },
          { left: '7%', bottom: '7%', width: '38%', height: '38%' },
          { right: '7%', bottom: '7%', width: '38%', height: '38%' }
        ],
        tooltip: {
          formatter: '{a}: ({c})'
        },
        xAxis: [
          { gridIndex: 0, min: 0, max: 20 },
          { gridIndex: 1, min: 0, max: 20 }
        ],
        yAxis: [
          { gridIndex: 0, min: 0, max: 15 },
          { gridIndex: 1, min: 0, max: 15 }
        ],
        series: [
          {
            name: 'h',
            type: 'line',
            xAxisIndex: 0,
            yAxisIndex: 0,
            data: [1,2,5,7,10]
          },

          {
            name: 'v',
            type: 'line',
            xAxisIndex: 1,
            yAxisIndex: 1,
            data: [1,2,3,6,9,12]
          }
        ]
      },
      //获取模型输出结果
      h: [],
      v: [],
      // 控制表格显示的变量
      isTableVisible: true,
      // 表格列定义
      tableColumns: [
        {prop: "序号", label: "序号", editable: false},
        {prop: "变量名", label: "变量名", editable: false},
        {prop: "初始值", label: "初始值", editable: true},
        {prop: "因果关系", label: "因果关系", editable: false},
        {prop: "描述", label: "描述", editable: true},
        {prop: "是否变量", label: "是否变量", editable: true},
      ],
      // 原始数据的深拷贝，用于在取消编辑时恢复数据
      originalData: null,
      formData: {
        tableData: [], // 表格数据
      },
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
      isOpen: false,
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
  watch: {},
  created() {
    this.getList();
  },
  mounted() {
  },
  methods: {
    //绘制模型结果
    MgCharts() {
      let myCharts = this.$echarts.init(this.$refs.charts);
      myCharts.setOption(this.option, true);
      window.addEventListener('resize', () => {
        myCharts.resize();
      })
    },
    open() {
      this.$nextTick(() => {
        this.MgCharts();
      })
    },
    draw() {
      const modulePath = (this.form.modulePath);
      if (modulePath) {
        drawModule({modulePath: modulePath}).then(response => {
          this.h = response.h;
          this.v = response.v;
        })
      }
    },
    initParameterList() {
      let params = this.queryParams.parameterList || this.form.parameterList;
      if (params) {
        try {
          // 尝试将参数字符串转换为JSON对象
          this.originalData = deepClone(params);
          this.formData.tableData = JSON.parse((params));
        } catch (error) {
          console.error('Error parsing parameterList JSON:', error);
          // 如果转换失败，使用空数组作为默认值
          this.formData.tableData = [];
        }
      } else {
        // 如果都没有值，使用空数组作为默认值
        this.formData.tableData = [];
      }
    },

    handleCellDoubleClick(scope) {
      // 双击单元格进入编辑状态
      this.handleEditClick(scope.row, scope.column.prop);
    },
    handleInputBlur(row, prop) {
      // 输入框失去焦点时，结束编辑状态
      row.editing = false;
      // 在这里可以添加数据验证逻辑
      // 更新 formData 对象
      this.formData.tableData = JSON.parse(JSON.stringify(this.formData.tableData));
    },
    handleInputEnter(row, prop) {
      // 输入框按下 Enter 时，结束编辑状态
      this.handleInputBlur(row, prop);
    },
    handleEditClick(row, prop) {
      // 点击按钮进入编辑状态
      this.$set(row, 'editing', true);
      Vue.nextTick(() => {
        // 自动获取焦点并选中内容
        const input = this.$el.querySelector(`input[data-row-key="${row._key}"][data-col-key="${prop}"]`);
        if (input) {
          input.focus();
          input.select();
        }
      });
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
      this.isOpen = false;
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
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const moduleId = row.moduleId || this.ids
      getModulesInfo(moduleId).then(response => {
        this.form = response.data;
        this.isOpen = true;
        this.title = "修改模型参数";
        this.initParameterList();

      });
    },
    /** 提交按钮 */
    submitForm() {
      //将编辑的数据实时转为tableData
      this.formData.tableData = JSON.parse(JSON.stringify(this.formData.tableData));
      //this.isOpen = false;
    },
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
  }
}
</script>
<style>
.bordered-div {
  border: 1px solid black; /* 设置1像素的实线黑色边框 */
}
</style>
