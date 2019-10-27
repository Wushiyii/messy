package com.wushiyii.messy.design.pattern;

import java.io.*;

public class Decorator_Case {

    public interface Component{
        void fun();
    }

    public static class ConcreteDecorator implements Component{

        @Override
        public void fun() {
            System.out.println("Concrete 实现业务");
        }
    }

    public static class Decorator{

        private Component component;

        public Decorator(Component component) {
            this.component = component;
        }

        public void fun() {
            component.fun();
        }
    }

    public static class DecoratorA extends Decorator{

        public DecoratorA(Component component) {
            super(component);
        }

        @Override
        public void fun() {
            //委托调用
            System.out.println("DecorateA 实现业务");
        }
    }

    public static class DecoratorB extends Decorator{

        public DecoratorB(Component component) {
            super(component);
        }

        @Override
        public void fun() {
            //委托调用
            super.fun();
            System.out.println("DecorateB 实现业务");
        }
    }

    public static void main(String[] args) throws Exception {
        Decorator a = new DecoratorA(new ConcreteDecorator());
        Decorator b = new DecoratorB(new ConcreteDecorator());

        a.fun();
        b.fun();

        //DataInputStream、BufferedInputStream、PushBackInputStream等等流都为IO装饰者模式

//        DataInputStream ds = new DataInputStream(new FileInputStream(new File("/Users/qudian/Desktop/test.txt")));
//        int available = ds.available();
//        byte[] stream = new byte[available];
//        ds.read(stream);
//        System.out.println(new String(stream));


        BufferedInputStream bf = new BufferedInputStream(new FileInputStream(new File("/Users/qudian/Desktop/test.txt")));
        byte[] buf = new byte[bf.available()];
        bf.read(buf);
        System.out.println(new String(buf));
    }
}
