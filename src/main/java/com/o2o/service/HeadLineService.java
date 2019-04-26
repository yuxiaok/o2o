package com.o2o.service;


import java.util.List;

import com.o2o.entity.HeadLine;

public interface HeadLineService {
String HLLISTKEY="headlinelist";

     /**
      * 获取 
      */
     List<HeadLine> listHeadLine(HeadLine headLineCondition) throws Exception;
}
