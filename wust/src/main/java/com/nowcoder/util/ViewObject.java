package com.nowcoder.util;

import com.fasterxml.jackson.databind.util.ObjectIdMap;
import sun.awt.SunHints;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/26 0026.
 */
public class ViewObject {
    private Map map=new HashMap<Object,Object>();

    public void set(String str,Object object){
        map.put(str,object);

    }





}