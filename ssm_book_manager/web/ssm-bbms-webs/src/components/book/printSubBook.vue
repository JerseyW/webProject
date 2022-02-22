<template>
   <el-dialog :visible.sync="isShow" title="打印已借阅书籍" center @close ="closeDialog" @open = "openDialog">
    <el-card style="width:100%" >
      <el-button v-print="'#print'"><i class="el-icon-printer"></i>&nbsp;打印</el-button><br><br>
      <div id ='print' style="margin:auto">
        <el-table v-loading="loading" :data="printData" stripe border size="mini">
          <el-table-column align="center" label="编号" prop="bid" :sortable="true" :sort-method="sortSubBook"></el-table-column>
          <el-table-column align="center" label="书名" prop="bookName"></el-table-column>
          <el-table-column align="center" label="作者" prop="author"></el-table-column>
          <el-table-column align="center" label="单价" prop="price"></el-table-column>
          <el-table-column align="center" label="借阅次数" prop="subNum"></el-table-column>
        </el-table>
      </div>
    </el-card>
  </el-dialog>
</template>

<script>
import { commonAPI } from '@/api/commonAPI'
export default {
  props: {
    option: Object
  },
  data () {
    return {
      printData: [],
      loading: false
    }
  },
  computed: {
    isShow: {
      // 修改用户弹窗是否显示
      get: function () {
        return this.option.isPrint;
      },
      set: function (newValue) {
        this.option.isPrint = newValue;
      }
    }
  },
  methods: {
    getSubBookData () {
      this.loading = true;
      commonAPI('querySub', { uId: parseInt(this.$store.getters.getUid), bookType: 1 }).then(res => {
            this.printData = res.data.data.row;
        })
      this.loading = false;
    },
    openDialog () {
      this.getSubBookData();
    },
    sortSubBook (a, b) {
      return a.bookType - b.bookType;
    },
    closeDialog () {
      this.option.isPrint = false;
    }
  }
}
</script>

<style>

</style>
