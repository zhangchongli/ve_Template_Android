package com.volcengine.mira;

import java.util.HashMap;
import java.util.Map;

/**
 * 一个简单的ServiceManager实现
 */
public class ServiceManager {

    private static Map<String, Object> services = new HashMap<>();

    public static <T> void resisterService(Class<T> clazz, T t) {
        if (!services.containsKey(clazz.getName())) {
            services.put(clazz.getName(), t);
        }
    }

    public static <T> T getService(Class<T> serviceType) {
        return (T) services.get(serviceType.getName());
    }
}
