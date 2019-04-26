package com.o2o.util;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * Created by yukai
 * 2019/4/5 15:34
 * 后置处理器进行解密
 */
public class EncryptPropertyPlaceHolderConfigurer extends PropertyPlaceholderConfigurer {
//需要加密的数组字段
    private String[] encryptPropNames = {"jdbc.username","jdbc.password"};

    //找到加密字段进行解密
    @Override
    protected String convertProperty(String propertyName, String propertyValue) {
        if (isEncryptProp(propertyName)){
            return DESUtil.getDecryptString(propertyValue);
        }
        return propertyValue;
    }

    /**
     * 是否属于加密字段
     * @param propertyName
     * @return
     */
    private boolean isEncryptProp(String propertyName){
        for (String prop:encryptPropNames
             ) {
            if (prop.equals(propertyName)){
                return true;
            }
        }
        return false;
    }
}
