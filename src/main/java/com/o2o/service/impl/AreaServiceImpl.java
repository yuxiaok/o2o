package com.o2o.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.o2o.dao.AreaDao;
import com.o2o.entity.Area;
import com.o2o.service.AreaService;

/**
 * @Author yukai
 * @Date 2018年7月13日
 */
@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaDao areaDao;
    /* @Autowired
    private JedisUtil.Keys jedisKeys;
    @Autowired
    private JedisUtil.Strings jedisStrings;*/

    private static Logger logger = LoggerFactory.getLogger(AreaServiceImpl.class);

    @Override
    public List<Area> getAreaList() throws Exception {
        List<Area> areaList = null;
        ObjectMapper mapper = new ObjectMapper();
        // 如果没有被缓存
        /*if (!jedisKeys.exists(AREALISTKEY)) {*/
        areaList = areaDao.queryArea();
        /*  String jsonString = mapper.writeValueAsString(areaList);
        
          jedisStrings.set(AREALISTKEY, jsonString);
        } else {
          String jsonString = jedisStrings.get(AREALISTKEY);
          JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, Area.class);
          areaList = mapper.readValue(jsonString, javaType);
        
        }*/
        return areaList;
    }

}
