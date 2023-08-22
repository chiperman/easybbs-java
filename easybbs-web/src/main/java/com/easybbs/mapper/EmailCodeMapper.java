package com.easybbs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easybbs.entity.EmailCode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author 45083
 * @description 针对表【email_code(邮箱验证码)】的数据库操作Mapper
 * @createDate 2023-08-21 17:58:12
 * @Entity com.easybbs.entity.EmailCode
 */
@Mapper
public interface EmailCodeMapper extends BaseMapper<EmailCode> {
    void disableEmailCode(@Param("email") String email);
}




