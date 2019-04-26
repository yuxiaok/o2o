package com.o2o.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.o2o.dao.HeadLineDao;
import com.o2o.entity.HeadLine;
import com.o2o.service.HeadLineService;

/**
 * Created by yukai 2019/3/16 15:30
 */
@Service
public class HeadLineServiceImpl implements HeadLineService {
    @Autowired
    private HeadLineDao headLineDao;
    /*@Autowired
    private JedisUtil.Keys jedisKeys;
    @Autowired
    private JedisUtil.Strings jedisStrings;*/

    @Override
    public List<HeadLine> listHeadLine(HeadLine headLineCondition) throws Exception {
        // 定义Key的前缀
        String key = HLLISTKEY;
        List<HeadLine> headLineList = null;
        // 数据转换
        ObjectMapper mapper = new ObjectMapper();
        // 拼接Key
        if (null != headLineCondition && null != headLineCondition.getEnableStatus()) {
            key = key + "_" + headLineCondition.getEnableStatus();
        }
        // 判断Key是否存在
        /* if (!jedisKeys.exists(key)){*/
        headLineList = headLineDao.queryHeadLine(headLineCondition);
        /* String jsonString = mapper.writeValueAsString(headLineList);
        
         jedisStrings.set(key,jsonString);
        }else {
         String jsonString = jedisStrings.get(key);
         JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, HeadLine.class);
         headLineList = mapper.readValue(jsonString,javaType);
        }*/
        return headLineList;
    }
}
