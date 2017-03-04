package com.service.impl;


import com.service.DubboService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ifly on 2017/3/4.
 */
public class DubboServiceImpl implements DubboService {

    /**
     * 测试dubbo接口实现
     * @return
     */
    public List<String> testGetData() {
        List<String> list = new ArrayList<String>();
        list.add("testManing");
        return list;
    }
}
