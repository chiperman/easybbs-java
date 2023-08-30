package com.easybbs.mapper;

import com.easybbs.entity.LikeRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Master-Z
* @description 针对表【like_record(点赞记录)】的数据库操作Mapper
* @createDate 2023-08-29 16:59:18
* @Entity com.easybbs.entity.LikeRecord
*/
@Mapper
public interface LikeRecordMapper extends BaseMapper<LikeRecord> {

}




