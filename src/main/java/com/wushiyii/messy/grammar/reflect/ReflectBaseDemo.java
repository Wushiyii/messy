package com.wushiyii.messy.grammar.reflect;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class ReflectBaseDemo {

    interface Base {
        String baseMethod(String base);
    }

    public static class Super {

    }

    @Getter
    @Setter
    public static class Sub extends Super implements Base{

        public String name;

        public Sub(String name) {
            this.name = name;
        }

        @Override
        public String baseMethod(String base) {
            return "operator : " + this.name + " ,input : " + base;
        }

        public static Integer count(Integer age) {
            return age * 2;
        }

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
        }
    }

    public static void main(String[] args) throws Exception {
        // 获取Class对象
        Class<Sub> subClazz = Sub.class;

        // 打印类名
        System.out.println(subClazz.getSimpleName());//无包名
        System.out.println(subClazz.getName());//包括包名

        // 打印包名
        System.out.println(subClazz.getPackage());

        // 获取修饰符
        System.out.println(subClazz.getModifiers());
        System.out.println(Modifier.isPublic(subClazz.getModifiers()));
        System.out.println(Modifier.isPrivate(subClazz.getModifiers()));

        // 获取父类Class对象
        Class<? super Sub> superClazz = Sub.class.getSuperclass();
        System.out.println(superClazz.getName());

        // 获取接口信息
        Class<?>[] interfaces = subClazz.getInterfaces();
        System.out.println(interfaces[0].getSimpleName()); // Base

//        获取所有public构造器信息
        Constructor<?>[] constructors = subClazz.getConstructors();
        //通过指定类型获取构造函数（也只能获取public型）
//        Constructor<Sub> constructor = subClazz.getDeclaredConstructor(String.class);
//        Sub tom = constructor.newInstance(new String[]{"tom"});
//        System.out.println(tom);


        //获取所有public方法，包括其继承类的公用方法，也包括实现接口的方法
        Method[] methods = subClazz.getMethods();
        System.out.println(Arrays.toString(methods));
        //获取类或接口的所有方法，包括公共、保护、默认（包）访问和私有方法以及实现接口的方法，但不包括继承的方法。
        Method[] declaredMethods = subClazz.getDeclaredMethods();
        System.out.println(Arrays.toString(declaredMethods));

        //获取指定方法
        Method baseMethod = subClazz.getMethod("baseMethod", String.class);
        System.out.println(baseMethod.toString());
        Method declaredMethod = subClazz.getDeclaredMethod("baseMethod", String.class);

        //获取方法参数
        Class<?>[] parameterTypes = declaredMethod.getParameterTypes();
        System.out.println("参数类型: " + Arrays.toString(parameterTypes));
        Class<?> returnType = declaredMethod.getReturnType();
        System.out.println("返回类型: " + returnType.toString());

        //调用
        Constructor<Sub> constructor = subClazz.getConstructor(String.class);
        Method method = subClazz.getMethod("baseMethod", String.class);
        //调用普通方法
        System.out.println(method.invoke(constructor.newInstance("Tom"), "Mike"));
        //调用static方法,invoke的obj传null
        Method count = subClazz.getMethod("count", Integer.class);
        System.out.println(count.invoke(null, 10));

        //获取成员变量
        Field[] fields = subClazz.getFields();
        System.out.println(Arrays.toString(fields));
        Field[] declaredFields = subClazz.getDeclaredFields();
        System.out.println(Arrays.toString(declaredFields));
        Field name = subClazz.getField("name");
        Field declaredName = subClazz.getDeclaredField("name");
        System.out.println("name:" + name.getName() +", declaredName : " + declaredName.getName());

        //成员变量赋值
        Sub sub = new Sub("TOM");
        Object nameVal = name.get(sub);
        name.set(sub, nameVal);
        System.out.println(sub);




    }



}
