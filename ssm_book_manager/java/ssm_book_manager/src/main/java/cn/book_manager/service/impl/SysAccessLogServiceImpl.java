package cn.book_manager.service.impl;

import cn.book_manager.mapper.SysAccessLogMapper;
import cn.book_manager.service.SysAccessLogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Jersey
 * @create: 2022-02-12 14:10
 * @version: V1.0
 * @slogan: 业精于勤, 荒于嬉;行成于思,毁于随。
 * @caution: 所有学不会都是给懒找借口！
 * @description:
 */
@Service
public class SysAccessLogServiceImpl implements SysAccessLogService {

    private final SysAccessLogMapper sysAccessLogMapper;

    @Autowired
    public SysAccessLogServiceImpl(SysAccessLogMapper sysAccessLogMapper) {
        this.sysAccessLogMapper = sysAccessLogMapper;
    }

    @Override
    public int saveSysLog(Map<String, Object> map) {
        return sysAccessLogMapper.saveSysLog(map);
    }

    @Override
    public Map<String, Object> querySysLogList(Map<String, Object> map) {

        int pageNum= Integer.parseInt(map.get("pageNum").toString());
        int pageSize= Integer.parseInt(map.get("pageSize").toString());
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> maps = sysAccessLogMapper.querySysLogList(map);


        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(maps);
        long total = pageInfo.getTotal();

        Map<String,Object> resultMap = new HashMap<>();

        resultMap.put("total",total);
        resultMap.put("rows",maps);
        return resultMap;
    }
}
