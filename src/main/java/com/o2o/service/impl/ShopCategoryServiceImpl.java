package com.o2o.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.o2o.dao.ShopCategoryDao;
import com.o2o.entity.ShopCategory;
import com.o2o.service.ShopCategoryService;

/**
 * @Author yukai
 * @Date 2018年7月15日
 */
@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {

    @Autowired
    private ShopCategoryDao shopCategoryDao;
    /*@Autowired
    private JedisUtil.Keys jedisKeys;
    @Autowired
    private JedisUtil.Strings jedisStrings;*/

    private static Logger logger = LoggerFactory.getLogger(ShopCategoryServiceImpl.class);

    @Override
    public List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition) throws Exception {
        // 定义redis，key的前缀
        String key = SCLISTKEY;
        List<ShopCategory> shopCategoryList = null;
        ObjectMapper mapper = new ObjectMapper();
        // 拼接redis的key
        if (null == shopCategoryCondition) {
            // 若查询条件为空，则列出所有首页大类，即parentId为空的店铺类别
            key = key + "_allfirstlevel";
        } else if (null != shopCategoryCondition && null != shopCategoryCondition.getParent()
            && null != shopCategoryCondition.getParent().getShopCategoryId()) {
            // 若parentId为非空，则列出该parendId下的所有子类别
            key = key + "_parent" + shopCategoryCondition.getParent().getShopCategoryId();
        } else if (null != shopCategoryCondition) {
            // 列出所有子类别，不管其属于哪个类，都列出来
            key = key + "_allsecondlevel";
        }
        // 判断key是否存在
        /*if (!jedisKeys.exists(key)){*/
        shopCategoryList = shopCategoryDao.queryShopCategory(shopCategoryCondition);
        /*
        				String jsonString = mapper.writeValueAsString(shopCategoryList);
        				jedisStrings.set(key,jsonString);
        
        		}else {
        			String jsonString = jedisStrings.get(key);
        			JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, ShopCategory.class);
        			shopCategoryList = mapper.readValue(jsonString,javaType);
        		}*/
        return shopCategoryList;
    }

}
