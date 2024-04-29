<template>
  <div>
    <el-dialog v-bind="$attrs" v-on="$listeners" visible.sync="openUploadDialog" @open="onOpen" @close="onClose"
               :before-close="handleClose" title="fmu">
      <el-form ref="elForm" :model="formData" :rules="rules" size="medium" label-width="100px"
               label-position="left">
        <el-form-item label="上传模型" prop="file" required>
          <el-upload ref="upload" :on-success="uploadSuccess" :on-error="uploadError" :file-list="upload.fileList"
                     :limit="1" accept=".fmu,.zip,.jpg,.doc,.docx" :headers="upload.headers"
                     :on-progress="handleFileUploadProgress"
                     :auto-upload="false" :action="upload.url" multiple
                     :before-upload="fileBeforeUpload">
            <el-button size="small" type="primary" icon="el-icon-upload">选取文件</el-button>
            <div slot="tip" class="el-upload__tip">只能上传fmu且不超过 5MB 的文件</div>
          </el-upload>
        </el-form-item>
        <el-button style="margin-left: 100px;" size="small" type="success" :loading="upload.isUploading"
                   @click="submitUpload">上传到服务器
        </el-button>
      </el-form>
      <div slot="footer">
        <el-button @click="close">取消</el-button>
        <el-button type="primary" @click="handleConfirm">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import {getToken} from "@/utils/auth";

export default {
  name: 'ModelUploadDialog',
  inheritAttrs: false,
  components: {},
  props: ['openUploadDialog'],
  data() {
    return {
      formData: {
        filepath: null,
      },
      rules: {required: true, message: '请上传文件', trigger: 'change'},
      // 上传参数
      upload: {
        // 是否禁用上传
        isUploading: false,
        // 设置上传的请求头部
        headers: {Authorization: "Bearer " + getToken()},
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/common/upload",
        // 上传的文件列表
        fileList: []
      }
    }
  },
  computed: {},
  watch: {},
  created() {
  },
  mounted() {

  },
  methods: {
    onOpen() {
    },
    onClose() {
      this.$refs['elForm'].resetFields();

    },
    close() {
      console.log("子组件关闭")
      this.$emit('update:visible', false);
    },
    handleConfirm() {
      /*this.$refs['elForm'].validate(valid => {
        if (!valid) return
        this.close()
      })*/
      this.close();
    },
    fileBeforeUpload(file) {
      let isRightSize = file.size / 1024 / 1024 < 5
      if (!isRightSize) {
        this.$message.error('文件大小超过 5MB')
      }
      return isRightSize
    },
    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          done();
        })
        .catch(_ => {
        });
    },
    // 文件提交处理
    submitUpload() {
      this.$refs.upload.submit();
    },
// 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
// 文件上传成功处理
    uploadSuccess(response, file, fileList) {
      this.upload.isUploading = false;
      this.upload.fileList = [];
      this.formData.filePath = response.url;
      this.$modal.msgSuccess(response.msg);
      //this.msgSuccess(response.msg);
    },
    uploadError(err, file, fileList) {
      // 文件上传失败时的操作
      this.$message.error('文件上传失败，请重试');
      // 清空文件列表，或者根据需求进行其他操作
      this.fileList = [];
    },
  }
}

</script>
<style>
.el-upload__tip {
  line-height: 1.2;
}

</style>
