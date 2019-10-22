package com.framework.web.pojo;

import lombok.Data;

import java.lang.management.*;
import java.util.List;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/10/10
 **/
@Data
public class JmxMessage {
    private OperatingSystemMXBean systemMXBean;
    private RuntimeMXBean runtimeMXBean;
    private MemoryMXBean memoryMXBean;
    private List<GarbageCollectorMXBean> garbageCollectorMXBeans;
}
