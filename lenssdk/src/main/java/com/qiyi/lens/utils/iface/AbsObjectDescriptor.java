package com.qiyi.lens.utils.iface;

import com.qiyi.lens.utils.configs.DebugInfoConfig;

/**
 * 对象解释器：
 * Lens 中各个页面会对 对象 进行解释， 以展示客户需要的内容。 客户端可自行实现解释器， 以在Lens 面板上展示定制化的内容。
 *
 * @param <T>
 */
public abstract class AbsObjectDescriptor<T> implements IObjectDescriptor {
    private T value;

    public AbsObjectDescriptor(T var) {
        value = var;
    }

    public abstract String toString();

    public abstract String getTag();

    public String toString(Object o) {
        if (o != null) {
            return o.toString();
        }
        return null;
    }

    public T getObject() {
        return value;
    }


    public static String getDescription(Object object) {
        String desc = null;
        if (object instanceof IObjectDescriptor) {
            desc = object.toString();
        } else {// getDefault
            IObjectDescriptor descriptor = DebugInfoConfig.getInstance().getDefaultDescription();
            if (descriptor != null) {
                desc = descriptor.toString(object);
            }
        }
        if (desc == null && object != null) {
            return object.toString();
        }
        return desc;
    }
}
