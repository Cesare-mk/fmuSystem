<template>
  <div class="navbar">
    <hamburger id="hamburger-container" :is-active="sidebar.opened" class="hamburger-container"
               @toggleClick="toggleSideBar"/>

    <breadcrumb id="breadcrumb-container" class="breadcrumb-container" v-if="!topNav"/>
    <top-nav id="topmenu-container" class="topmenu-container" v-if="topNav"/>

    <div class="right-menu">
      <template v-if="device!=='mobile'">
        <search id="header-search" class="right-menu-item"/>
<!--        <el-tooltip :content="noticeContent" effect="dark" placement="bottom">
          <el-badge :value="noticeCount" class="right-menu-item hover-effect" :class="{'badge-custom':noticeCount>0}" >
            <i class="el-icon-message-solid" @click="toNoticePage"></i>
          </el-badge>
        </el-tooltip>-->
        <screenfull id="screenfull" class="right-menu-item hover-effect"/>
        <!--
                <el-tooltip content="布局大小" effect="dark" placement="bottom">
                  <size-select id="size-select" class="right-menu-item hover-effect"/>
                </el-tooltip>-->

      </template>

      <el-dropdown class="avatar-container right-menu-item hover-effect" trigger="click">
        <div class="avatar-wrapper">
          <img :src="avatar" class="user-avatar">
          <i class="el-icon-caret-bottom"/>
        </div>
        <el-dropdown-menu slot="dropdown">
          <router-link to="/user/profile">
            <el-dropdown-item>个人中心</el-dropdown-item>
          </router-link>
          <el-dropdown-item @click.native="setting = true">
            <span>布局设置</span>
          </el-dropdown-item>
          <el-dropdown-item divided @click.native="logout">
            <span>退出登录</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import {mapGetters} from 'vuex'
import Breadcrumb from '@/components/Breadcrumb'
import TopNav from '@/components/TopNav'
import Hamburger from '@/components/Hamburger'
import Screenfull from '@/components/Screenfull'
import SizeSelect from '@/components/SizeSelect'
import Search from '@/components/HeaderSearch'
import RuoYiGit from '@/components/RuoYi/Git'
import RuoYiDoc from '@/components/RuoYi/Doc'
import { listNotice } from "@/api/system/notice";
export default {
  components: {
    Breadcrumb,
    TopNav,
    Hamburger,
    Screenfull,
    SizeSelect,
    Search,
    RuoYiGit,
    RuoYiDoc
  },
  computed: {
    ...mapGetters([
      'sidebar',
      'avatar',
      'device'
    ]),
    setting: {
      get() {
        return this.$store.state.settings.showSettings
      },
      set(val) {
        this.$store.dispatch('settings/changeSetting', {
          key: 'showSettings',
          value: val
        })
      }
    },
    topNav: {
      get() {
        return this.$store.state.settings.topNav
      }
    }
  },
/*  data(){
    return{
      noticeContent:'',//通知内容
      noticeCount:0,//通知数量
      intervalId: null
    }
  },*/
/*  created(){
    this.poll();
  },*/
  /*mounted() {
    // 启动轮询
    this.startPolling();
  },*/
  /*beforeDestroy() {
    // 在组件销毁之前清除定时器，防止内存泄漏
    this.stopPolling();
  },*/
  methods: {
    toggleSideBar() {
      this.$store.dispatch('app/toggleSideBar')
    },
    async logout() {
      this.$confirm('确定注销并退出系统吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$store.dispatch('LogOut').then(() => {
          location.href = '/index';
        })
      }).catch(() => {
      });
    },
  /*  toNoticePage(){
      //前往通知公告管理页面
      this.$router.push("/system/notice");
    },
    startPolling() {
      // 每隔一定时间执行轮询任务
      this.intervalId = setInterval(() => {
        this.poll();
      }, 5000); // 5秒钟轮询一次，根据需要调整间隔时间
    },
    stopPolling() {
      // 清除定时器，停止轮询任务    ！！！！重要，防止内存泄露
      clearInterval(this.intervalId);
    },
    poll() {
      // 在这里执行轮询的任务，可以是发送请求或执行其他操作
      listNotice().then(response => {
        this.noticeCount=response.total;//获取信息条数
        this.noticeContent="您有"+this.noticeCount+"条未读的信息";//定制内容
      });
    }*/
  }
}
</script>

<style lang="scss" scoped>
.navbar {
  height: 50px;
  overflow: hidden;
  position: relative;
  background: #fff;
  box-shadow: 0 1px 4px rgba(0, 21, 41, .08);

  .hamburger-container {
    line-height: 46px;
    height: 100%;
    float: left;
    cursor: pointer;
    transition: background .3s;
    -webkit-tap-highlight-color: transparent;

    &:hover {
      background: rgba(0, 0, 0, .025)
    }
  }

  .breadcrumb-container {
    float: left;
  }

  .topmenu-container {
    position: absolute;
    left: 50px;
  }

  .errLog-container {
    display: inline-block;
    vertical-align: top;
  }

  .right-menu {
    float: right;
    height: 100%;
    line-height: 50px;

    &:focus {
      outline: none;
    }

    .right-menu-item {
      display: inline-block;
      padding: 0 8px;
      height: 100%;
      font-size: 18px;
      color: #5a5e66;
      vertical-align: text-bottom;

      &.hover-effect {
        cursor: pointer;
        transition: background .3s;

        &:hover {
          background: rgba(0, 0, 0, .025)
        }
      }
    }

    .avatar-container {
      margin-right: 30px;

      .avatar-wrapper {
        margin-top: 5px;
        position: relative;

        .user-avatar {
          cursor: pointer;
          width: 40px;
          height: 40px;
          border-radius: 10px;
        }

        .el-icon-caret-bottom {
          cursor: pointer;
          position: absolute;
          right: -20px;
          top: 25px;
          font-size: 12px;
        }
      }
    }
    ::v-deep .el-badge__content {
      margin-top: 9px;/* 调整一下上下左右你喜欢的位置 */
      margin-right: 7px;
    }

    .badge-custom {
      animation: blink-animation 0.5s infinite alternate; /* 设置闪烁动画 */
    }
    @keyframes blink-animation {
      0% { opacity: 1; } /* 定义动画起始状态 */
      100% { opacity: 0.1; } /* 定义动画结束状态 */
    }
  }
}
</style>
