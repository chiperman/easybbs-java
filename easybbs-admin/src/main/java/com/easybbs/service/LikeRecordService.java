package com.easybbs.service;

import com.easybbs.entity.LikeRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import com.easybbs.enums.OperRecordOpTypeEnum;

/**
* @author Master-Z
* @description 针对表【like_record(点赞记录)】的数据库操作Service
* @createDate 2023-08-29 16:59:18
*/
public interface LikeRecordService extends IService<LikeRecord> {

    void doLike(String objectId, String userId, String nickName, OperRecordOpTypeEnum opTypeEnum);
}
