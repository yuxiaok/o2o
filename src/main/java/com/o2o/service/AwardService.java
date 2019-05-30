package com.o2o.service;

import com.o2o.dto.AwardExecution;
import com.o2o.dto.ImageHolder;
import com.o2o.entity.Award;

public interface AwardService {

    /**
     * 
     * @param awardCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    AwardExecution getAwardList(Award awardCondition, int pageIndex, int pageSize);

    /**
     * 
     * @param awardId
     * @return
     */
    Award getAwardById(long awardId);

    /**
     * 
     * @param award
     * @param thumbnail
     * @return
     */
    AwardExecution addAward(Award award, ImageHolder thumbnail);

    /**
     * 
     * @param award
     * @param thumbnail
     * @param awardImgs
     * @return
     */
    AwardExecution modifyAward(Award award, ImageHolder thumbnail);

}
