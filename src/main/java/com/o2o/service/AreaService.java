package com.o2o.service;

import java.util.List;

import com.o2o.entity.Area;

/**
 * @Author yukai
 * @Date 2018年7月13日
 */
public interface AreaService {
	String AREALISTKEY = "arealist";
	List<Area> getAreaList() throws Exception;
}
