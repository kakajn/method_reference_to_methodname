package com.whitemaple.utuls;

import com.whitemaple.abs.TGet;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: kakajn
 * Date: 2023/06/26 7:29
 * Package_name: com.whitemaple.utuls
 * Description:  Version: V1.0
 * Comment Before See:
 */
public class Utils {
    /**
     * 通过这个方法拿到getter这个方法名字, 然后变成对应的属性名字
     * @param getter
     * @param <T>
     *     如果一个序列化类中含有Object writeReplace()方法，那么实际序列化的对象将是作为writeReplace方法
     *     返回值的对象，而且序列化过程的依据是实际被序列化对象的序列化实现。
     *     可以参考这边文章: https://blog.csdn.net/BlackCutter/article/details/46802989
     */
    public static<T> void getFiledName(TGet<T> getter) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method writeReplace = getter.getClass().getDeclaredMethod("writeReplace");
        //设置这个方法可以被访问
        writeReplace.setAccessible(true);
        //调用这个方法拿到对应的序列化对象
        SerializedLambda lambda = (SerializedLambda)writeReplace.invoke(getter);
        //拿到这个被序列化的方法之后, 我们就可以得到相关的方法名称了
        final String getterName = lambda.getImplMethodName();
        //输出这个方法的名字
        //如果要验证的话,可以经过判断这个方法是不是以get开头的, 这样的话可可以实现代码的安全
        System.out.println("得到的get方法是: "+getterName + "得到的数据库列的名字是: "+transferJavaFileNameToColumnName(getterName));
    }

    /**
     *
     * @param getterMethodName
     * @return
     */
    public static String transferJavaFileNameToColumnName(String getterMethodName){
        //判断是不是以get开头的方法
        if (!getterMethodName.startsWith("get")){
            throw new RuntimeException("MethodName not start with get!");
        }
        //创建一个StringBuilder用来拼接数据库Column的名字
        StringBuilder column = new StringBuilder();

        //获取到getterMethodName 的 char数组
        char[] chars = getterMethodName.toCharArray();

        //上面已经判断过是以get开头的了, 所以直接跳过前3个字母
        int endIndex = chars.length;
        for (int index = 3 ; index < endIndex; index++){
            char temp = chars[index];
            if (isUpperCaseAlpha(temp)){
                char c = transACharToLowerCase(temp);
                column.append(c);
            }else {
                column.append(temp);
            }

            //如果下一个字符的长度小于chars数组的长度 再判断下一个字母是不是大写字母
            if (index+1 < endIndex && isUpperCaseAlpha(chars[index+1])){
                column.append('_');
            }
        }
        return column.toString();
    }

    /**
     * 转换一个 大写的char 到 小写的char
     * @param upperCaseChar
     * @return
     */
    public static char transACharToLowerCase(char upperCaseChar){
        //如果char > 127 直接返回
        if ( upperCaseChar > 127){
            return upperCaseChar;
        }else{
            //那么再判断是不是落在了 大写字母的范围内
            if ( upperCaseChar >= 65 && upperCaseChar <=90){
                //97 - 65
                return (char)(upperCaseChar + 32);
            }
        }
        return  upperCaseChar;
    }

    public static boolean isUpperCaseAlpha(char alpha) {
        //如果char > 127 直接返回
        if (alpha > 127) {
            return false;
        } else {
            //那么再判断是不是落在了 大写字母的范围内
            if (alpha >= 65 && alpha <= 90) {
                //97 - 65
                return true;
            }
        }
        return false;
    }

}
