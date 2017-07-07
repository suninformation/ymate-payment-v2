/*
 * Copyright 2007-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.ymate.payment.wxpay.base;

import com.alibaba.fastjson.JSONObject;

/**
 * 场景信息
 *
 * @author 刘镇 (suninformation@163.com) on 2017/7/7 上午10:05
 * @version 1.0
 */
public class WxPaySceneInfo {

    /**
     * @param wapName 网站名
     * @param wapUrl  网站URL地址
     * @return WAP网站应用场景
     */
    public static String H5_WAP(String wapName, String wapUrl) {
        JSONObject _returnValue = new JSONObject();
        //
        JSONObject _params = new JSONObject();
        _params.put("type", "Wap");
        _params.put("wap_name", wapName);
        _params.put("wap_url", wapUrl);
        _returnValue.put("h5_info", _params);
        //
        return _returnValue.toJSONString();
    }

    /**
     * @param appName  应用名
     * @param bundleId bundle_id
     * @return IOS移动应用场景
     */
    public static String H5_IOS(String appName, String bundleId) {
        JSONObject _returnValue = new JSONObject();
        //
        JSONObject _params = new JSONObject();
        _params.put("type", "IOS");
        _params.put("app_name", appName);
        _params.put("bundle_id", bundleId);
        _returnValue.put("h5_info", _params);
        //
        return _returnValue.toJSONString();
    }

    /**
     * @param appName     应用名
     * @param packageName 包名
     * @return 安卓移动应用场景
     */
    public static String H5_ANDROID(String appName, String packageName) {
        JSONObject _returnValue = new JSONObject();
        //
        JSONObject _params = new JSONObject();
        _params.put("type", "Android");
        _params.put("app_name", appName);
        _params.put("package_name", packageName);
        _returnValue.put("h5_info", _params);
        //
        return _returnValue.toJSONString();
    }

    /**
     * @param storeId   门店唯一标识
     * @param storeName 门店名称
     * @return 上报实际门店信息
     */
    public static String STORE(String storeId, String storeName) {
        JSONObject _returnValue = new JSONObject();
        _returnValue.put("store_id", storeId);
        _returnValue.put("store_name", storeName);
        //
        return _returnValue.toJSONString();
    }
}
