package com.controller;

import com.alibaba.fastjson.JSON;
import com.mapper.UserMapper;
import com.vo.UserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ifly on 2017/2/25.
 */

@RestController
public class UserController {

    protected static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserMapper userDao;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Resource(name = "stringRedisTemplate")
    ValueOperations<String, String> valueOperationStr;

    @RequestMapping(value = "/users", method = { RequestMethod.GET })
    @ResponseBody
    public String users(String userId, String groupId) {
        Map<String, String> map = new HashMap<String, String>();
        String userVo = "";
        if (userId != null && !userId.isEmpty()) {
            map.put("userId", userId);
        }
        if (groupId != null && !groupId.isEmpty()) {
            map.put("groupId", groupId);
        }

        // 使用外部缓存
        if (groupId != null && !groupId.isEmpty()) {

            if (valueOperationStr.get(groupId) != null && !valueOperationStr.get(groupId).isEmpty()) {

                userVo = valueOperationStr.get(groupId).toString();
            } else {
                List<UserVo> list = userDao.getUsersList(map);
                userVo = JSON.toJSONString(list);
                valueOperationStr.set(groupId, userVo);
            }
        }
        return userVo;
    }

    @RequestMapping("/redis/string/set")
    public String setKeyAndValue(String key, String value) {
        logger.debug("访问set:key={},value={}", key, value);
        valueOperationStr.set(key, value);
        return "Set Ok";
    }

    @RequestMapping("/redis/string/get")
    public String getKey(String key) {
        logger.debug("访问get:key={}", key);
        return valueOperationStr.get(key);
    }
}
