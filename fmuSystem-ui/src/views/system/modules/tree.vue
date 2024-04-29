<template>
  <div>
    <el-dialog v-bind="$attrs" v-on="$listeners" @open="onOpen" @close="onClose" title="Dialog Title">
      <el-form ref="elForm" :model="formData" :rules="rules" size="medium" label-width="100px">
        <el-form-item label="上传" prop="upload" required>
          <el-upload ref="upload" :file-list="uploadfileList" :action="uploadAction" :auto-upload="false"
                     multiple :before-upload="uploadBeforeUpload">
            <el-button size="small" type="primary" icon="el-icon-upload">点击上传</el-button>
            <div slot="tip" class="el-upload__tip">只能上传不超过 5MB 的文件</div>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="close">取消</el-button>
        <el-button type="primary" @click="handleConfirm">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
export default {
  inheritAttrs: false,
  components: {},
  props: [],
  data() {
    return {
      formData: {
        upload: null,
      },
      rules: {},
      uploadAction: ' process.env.VUE_APP_BASE_API + "/common/upload',
      uploadfileList: [],
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
      this.$refs['elForm'].resetFields()
    },
    close() {
      this.$emit('update:visible', false)
    },
    handleConfirm() {
      this.$refs['elForm'].validate(valid => {
        if (!valid) return
        this.close()
      })
    },
    uploadBeforeUpload(file) {
      let isRightSize = file.size / 1024 / 1024 < 5
      if (!isRightSize) {
        this.$message.error('文件大小超过 5MB')
      }
      return isRightSize
    },
    submitUpload() {
      this.$refs['upload'].submit()
    },
  }
}

</script>
<style>
.el-upload__tip {
  line-height: 1.2;
}

</style>
