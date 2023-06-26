package com.whitemaple.abs;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: kakajn
 * Date: 2023/06/26 7:30
 * Package_name: com.whitemaple.abs
 * Description:  Version: V1.0
 * Comment Before See:
 * 准备一个接口, 并且实现序列化
 */
@FunctionalInterface
public interface TGet<T> extends Serializable {

    void getProperties(T source);
}
