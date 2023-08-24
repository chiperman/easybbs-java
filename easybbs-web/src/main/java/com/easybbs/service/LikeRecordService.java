package com.easybbs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.easybbs.entity.LikeRecord;
import com.easybbs.enums.OperRecordOpTypeEnum;

/**
 * @author 45083
 * @description 针对表【like_record(点赞记录)】的数据库操作Service
 * @createDate 2023-08-21 14:40:27
 */
public interface LikeRecordService extends IService<LikeRecord> {
    void doLike(String objectId, String userId, String nickName, OperRecordOpTypeEnum opTypeEnum);
}
