package com.controller;

import com.alibaba.fastjson.JSON;
import com.mapper.UserMapper;
import com.vo.UserVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    RedisTemplate redisTemplate;

    @ApiOperation(value="获得用户列表", notes="根据用户id和群组id搜索用户列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, paramType = "query" ,dataType = "String"),
            @ApiImplicitParam(name = "groupId", value = "群组id", required = true, paramType = "query" ,dataType = "String")
    })
    @RequestMapping(value = "/users", method = { RequestMethod.GET })
    @ResponseBody
    public String users(@RequestParam String userId, @RequestParam String groupId) {
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

//            if (valueOperationStr.get(groupId) != null && redisTemplate.hasKey(groupId)) {
//                userVo = valueOperationStr.get(groupId).toString();
//            } else {
                List<UserVo> list = userDao.getUsersList(map);
                userVo = JSON.toJSONString(list);
                valueOperationStr.set(groupId, userVo);
//            }
        }
        return userVo;
    }

    @ApiOperation(value="设置redis数据", notes="kv设置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "用户ID", required = true, dataType = "String"),
            @ApiImplicitParam(name = "value", value = "群组id", required = true, dataType = "String")
    })
    @RequestMapping(value="/redis/string/set", method = { RequestMethod.GET })
    public String setKeyAndValue(
            @RequestParam String key,
            @RequestParam String value) {
        logger.debug("访问set:key={},value={}", key, value);
        valueOperationStr.set(key, value);
        return "Set Ok";
    }

    @ApiOperation(value="获取redis数据", notes="通过key获取")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "数据key", required = true, dataType = "String"),
    })
    @RequestMapping(value="/redis/string/get/{key}", method = { RequestMethod.POST })
    public String getKey(
            @PathVariable String key) {
        logger.debug("访问get:key={}", key);
        return valueOperationStr.get(key);
    }
}
