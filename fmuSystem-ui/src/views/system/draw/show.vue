<!--
<template>
<div>
<el-dialog :visible.sync="isOpen" title="模型输出展示">
  <div></div>
  <div slot="footer">
    <el-button type="primary" @click="handleClose">确定</el-button>
  </div>
</el-dialog>
</div>
</template>

<script>
import * as echarts from "echarts";
import {
  TitleComponent,
  TooltipComponent,
  GridComponent
} from 'echarts/components';
import { LineChart } from 'echarts/charts';
import { UniversalTransition } from 'echarts/features';
import { CanvasRenderer } from 'echarts/renderers';
echarts.use([
  TitleComponent,
  TooltipComponent,
  GridComponent,
  LineChart,
  CanvasRenderer,
  UniversalTransition
]);
export default {
  data(){
    return{
      isOpen: false,
    }
  },
  name: "show",
  mounted(){
    this.myEcharts();
  },
  methods:{
    myEcharts(){
      var chartDom = document.getElementById('main');
      var myChart = echarts.init(chartDom);
      var option = {
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
      }
//将配置项设置给echarts实例对象
      myChart.setOption(option);
    },

    handleClose(){
      this.isOpen = false;
    }
  }
}

</script>

<style scoped>

</style>
-->
