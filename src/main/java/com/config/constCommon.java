package com.config;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by ifly on 2017/3/13.
 */
public class ConstCommon {
    public static final Map<Object, Object> map = new TreeMap<Object, Object>();

    public final static int SUCCESS = 0;
    public final static int FAIL = -1;

    static {
        map.put(SUCCESS, "success");
        map.put(FAIL, "fail");
    }
}
