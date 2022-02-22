<template>
  <el-container>
    <el-header >
      <el-card>
        <el-form :inline="true" :model="sysLogInfo" class="demo-form-inline" size="mini">
          <el-form-item label="访问时间">
            <el-date-picker class="dateStyle" v-model="sysLogInfo.beginTime" value-format="yyyy-MM-dd HH:mm:ss" @change="dataFormat" type="datetime" placeholder="选择日期时间"></el-date-picker>
            至&nbsp;<el-date-picker class="dateStyle" v-model="sysLogInfo.endTime" value-format="yyyy-MM-dd HH:mm:ss" @change="dataFormat" type="datetime" placeholder="选择日期时间"></el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" class="btn" @click="queryLogList"><i class="el-icon-search"></i>查询</el-button>
          </el-form-item>
          <el-form-item>
            <el-button type="danger" class="btn" @click="resetting"><i class="el-icon-refresh"></i>重置</el-button>
          </el-form-item>
        </el-form>
      </el-card>

    </el-header>
    <el-main class="elMain">
      <el-card style="margin-top: 20px;">
        <el-table v-loading="loading" :data="sysLogList"  stripe border size="mini">
          <el-table-column align="center" label="访问时间" prop="accessDate"></el-table-column>
          <el-table-column align="center" label="请求方式" prop="requestType" ></el-table-column>
          <el-table-column align="center" label="请求接口" prop="accessInterface" ></el-table-column>
          <el-table-column header-align="center" align="left" label="接口参数" >
            <template slot-scope="scope" v-if="scope.row.interfaceParams">
              <el-popover v-if="scope.row.interfaceParams.length>10" placement="left-end"  trigger="hover" :content="scope.row.interfaceParams">
                <span slot="reference" style="text-overflow: ellipsis;white-space:nowrap;overflow: hidden;">{{scope.row.interfaceParams}}</span>
              </el-popover>
              <span v-else>{{scope.row.interfaceParams}}</span>
            </template>
          </el-table-column>
          <el-table-column align="center" label="访问来源" prop="accessSource" width="263"></el-table-column>
          <el-table-column align="center" label="访问IP" prop="accessIp" width="130"></el-table-column>
          <el-table-column align="center" label="执行时长" prop="executeTime" width="90"></el-table-column>
        </el-table>
      </el-card>
    </el-main>
    <el-footer>
      <div style="padding: 15px 0;text-align: right;">
        <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="sysLogInfo.pageNum"
                       :page-sizes="pageSizes" :page-size="sysLogInfo.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="pageTotal">
        </el-pagination>
      </div>
    </el-footer>
  </el-container>
</template>
<script>

import {commonAPI} from "../../api/commonAPI";
import {formatDate} from "../../utils/dateUtil";
export default {
  name: "sysLogList",
  data(){
    return {
      sysLogInfo:{
        beginTime:"",
        endTime:"",
        pageSize:15,
        pageNum:1

      },
      sysLogList:[],
      loading:false,
      pageTotal:0,
      pageSizes: [5, 10, 15, 20],
    }
  },methods:{
    handleSizeChange(val){
      this.sysLogInfo.pageNum = 1;
      this.sysLogInfo.pageSize = val;
      this.queryBook();
    },
    handleCurrentChange(val){
      this.sysLogInfo.pageNum = val;
      this.queryLogList();
    },
    queryLogList(){
      this.loading = true;
      commonAPI('querySysLogList', this.sysLogInfo).then(res => {
            this.loading = false;
            this.sysLogList = res.data.data.rows;
            this.pageTotal = res.data.data.total;
      })
    },
    resetting(){
      this.sysLogInfo.beginTime = formatDate(new Date(new Date().getTime() - 3600 * 1000 * 24 * 7), true)
      this.sysLogInfo.endTime = formatDate(new Date(), true)
      this.queryLogList();
    },
    dataFormat (val) {
      console.log(val);
    },
  },mounted() {
    this.queryLogList();
  }
}
</script>

<style scoped>

</style>