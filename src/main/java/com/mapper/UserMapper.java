package com.mapper;

import com.vo.UserVo;

import java.util.List;
import java.util.Map;

/**
 * Created by ifly on 2017/2/25.
 */
public interface UserMapper {
    public List<UserVo> getUsersList (Map<String, String> params);
}
