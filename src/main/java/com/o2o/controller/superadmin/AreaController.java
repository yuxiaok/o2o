package com.o2o.controller.superadmin;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.o2o.entity.Area;
import com.o2o.service.AreaService;

/**
 * @Author yukai
 * @Date 2018年7月13日
 */
@Controller
@RequestMapping("/superadmin")
public class AreaController {
	Logger logger = LoggerFactory.getLogger(AreaController.class);
	@Autowired
	private AreaService areaService;
	
	@GetMapping("/listarea")
	@ResponseBody//返回json格式
	public Map<String, Object> listArea(){
		logger.info("start");
		long startTime = System.currentTimeMillis();
		Map<String, Object> map = new HashedMap();
		try {
			List<Area> areas = areaService.getAreaList();
			map.put("rows", areas);
			map.put("total", areas.size());
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("errMsg", e.toString());
		}
		logger.error("error");
		long endTime = System.currentTimeMillis();
		logger.debug("时间：{}",endTime-startTime);
		logger.info("end");
		return map;
	}
	
}
