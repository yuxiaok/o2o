package com.o2o.service;

import com.o2o.entity.PersonInfo;

/**
 * Created by yukai
 * 2019/4/7 15:38
 */
public interface PersonInfoService {
    PersonInfo getPersonInfoById(Long userId);
}
