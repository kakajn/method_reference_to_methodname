package com.whitemaple.App;

import com.whitemaple.entity.User;
import com.whitemaple.utuls.Utils;

import java.lang.reflect.InvocationTargetException;

/**
 * Created with IntelliJ IDEA.
 * User: kakajn
 * Date: 2023/06/26 7:33
 * Package_name: com.whitemaple.App
 * Description:  Version: V1.0
 * Comment Before See:
 */
public class App {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Utils.getFiledName(User::getUserName);
        Utils.getFiledName(User::getUserAge);
    }
}
