package me.hujinbo.boot.example.controller;

import com.alibaba.druid.stat.DruidStatManagerFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Druid监控数据相关接口
 *
 * @author hujinbo
 */
@RestController
@RequestMapping("/api/druid")
public class DruidStatController {

    /**
     * 获取所有数据源的监控数据
     */
    @GetMapping("/data-source/stat")
    public List<Map<String, Object>> getDataSourceStatDataList() {
        return DruidStatManagerFacade.getInstance().getDataSourceStatDataList();
    }

}
