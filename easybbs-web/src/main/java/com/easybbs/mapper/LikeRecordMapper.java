package com.easybbs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easybbs.entity.LikeRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 45083
 * @description 针对表【like_record(点赞记录)】的数据库操作Mapper
 * @createDate 2023-08-21 14:40:27
 * @Entity com.easybbs.entity.LikeRecord
 */
@Mapper
public interface LikeRecordMapper extends BaseMapper<LikeRecord> {

}




