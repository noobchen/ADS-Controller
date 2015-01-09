package com.ads.cm.domain.consumer;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.lang.reflect.Method;

/**
 * Author: cyc
 * Date: 12-3-13
 * Time: 下午9:04
 * Description: to write something
 */
public class ConsumerMethodHolder {
    private String className;
    private Method method;

    public ConsumerMethodHolder(String className, Method method) {
        super();
        this.className = className;
        this.method = method;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ConsumerMethodHolder)) {
            return false;
        } else {
            ConsumerMethodHolder other = (ConsumerMethodHolder) o;
            if (this.className.equals(other.getClassName()) && this.method.equals(other.getMethod())) {
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).
                append("className", className).
                append("method", method).
                toString();
    }
}
