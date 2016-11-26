package com.sfw.anno.reflectiondemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.SQLOutput;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Person person = new Person();

        //通过对象实例调用getClass()方法获得Class对象
        Class pClass1 = person.getClass();
        System.out.println("Class获取方式1： " + pClass1.getName());

        //通过Class类的静态方法forName获得Class对象
        Class pClass2 = null;
        try {
            pClass2 = Class.forName("com.sfw.anno.reflectiondemo.Person");
            System.out.println("Class获取方式2： " + pClass2.getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //通过类字面常量获得Class对象,可用于普通类、接口、数组、基本数据类型
        Class pClass3 = null;
        pClass3 = Person.class;
        System.out.println("Class获取方式3： " + pClass3.getName());

        Class longClass1 = null;
        longClass1 = long.class;
        System.out.println(longClass1.getName());

        //基本数据类型还可以采取TYPE字段获得Class对象
        Class longClass2 = null;
        longClass2 = Long.TYPE;
        System.out.println(longClass2.getName());

        //通过Class对象获取类名
        System.out.println(pClass1.getName());
        System.out.println(pClass1.getSimpleName());

        //获得public、private、static等修饰符
        int modifiers = pClass1.getModifiers();
        System.out.println("isAbstract? " + Modifier.isAbstract(modifiers));
        System.out.println("isFinal? " + Modifier.isFinal(modifiers));
        System.out.println("isInterface? " + Modifier.isInterface(modifiers));
        System.out.println("isNative? " + Modifier.isNative(modifiers));
        System.out.println("isPrivate? " + Modifier.isPrivate(modifiers));
        System.out.println("isProtected? " + Modifier.isProtected(modifiers));
        System.out.println("isPublic? " + Modifier.isPublic(modifiers));
        System.out.println("isStatic? " + Modifier.isStatic(modifiers));
        System.out.println("isStrict? " + Modifier.isStrict(modifiers));
        System.out.println("isSynchronized? " + Modifier.isSynchronized(modifiers));
        System.out.println("isTransient? " + Modifier.isTransient(modifiers));
        System.out.println("isVolatile? " + Modifier.isVolatile(modifiers));

        //获得信息
        Package pkg = pClass1.getPackage();
        String packageName = pkg.getName();
        System.out.println("packageName: " + packageName);

        //获得父类
        Class studentClass = Student.class;
        System.out.println("父类： " + studentClass.getSuperclass().getSimpleName());

        //获取接口
        Class[] interfaces1 = pClass1.getInterfaces();
        System.out.println(interfaces1[0].getName());
        Class[] interfaces2 = studentClass.getSuperclass().getInterfaces();
        System.out.println(interfaces2[0].getName());

        //无参数构造方法
        Student student1 = null;
        try {
            Constructor constructor1 = studentClass.getConstructor();
            student1 = (Student) constructor1.newInstance();
            student1.setAge(27);
            System.out.println(student1.getAge());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //有参数构造方法
        Student student2 = null;
        try {
            Constructor constructor2 = studentClass.getConstructor(String.class, int.class);
            student2 = (Student) constructor2.newInstance("sfw", 27);
            System.out.println(student2.getAge());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //获取所有public方法,包括子类和父类
        Method[] methods1 = studentClass.getMethods();
        for (Method method : methods1) {
            System.out.println("methods1: " + method.getName());
        }

        //获取指定方法
        try {
            Method method1 = studentClass.getMethod("setAddress", String.class);
            System.out.println("method1: " + method1.getName());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        //调用参数为空算法
        try {
            Method getage = student2.getClass().getMethod("getAge");
            int age = (int) getage.invoke(student2);
            System.out.println(age);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //调用多参数方法
        Method setNameAndAge = null;
        try {
            setNameAndAge = student2.getClass().getMethod("setNameAndAge", String.class, int.class);
            setNameAndAge.invoke(student2, "wsf", 18);
            System.out.println("name " + student2.getName() + " age " + student2.getAge());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        //获取方法的参数
        Class[] paraTypes = setNameAndAge.getParameterTypes();
        for (Class paraType : paraTypes) {
            System.out.println(paraType.getName());
        }

        //获取本类所有方法
        Method[] declaredMethods = student2.getClass().getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            System.out.println(declaredMethod.getName());
        }

        try {
            Method ceshi =student2.getClass().getDeclaredMethod("ceshi");
            ceshi.setAccessible(true);
            ceshi.invoke(student2);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        //获取public字段
        Field [] fields = student2.getClass().getFields();
        for (Field field:fields){
            System.out.println(field.getName());
        }

        //获取指定public字段
        Field field = null;
        try {
            field = student2.getClass().getField("name");
            System.out.println(field.getName());
            //设置字段值
            field.set(student2,"shufengwu");
            //获取字段值
            System.out.println(field.get(student2));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        //获取字段
        Field[] declaredFields = student2.getClass().getDeclaredFields();
        for (Field declaredField:declaredFields){
            field.setAccessible(true);
            System.out.println(declaredField.getName());
        }

        //获取类上注解
        Annotation [] annotations = Student.class.getAnnotations();
        for (Annotation annotation:annotations){
            System.out.println(annotation);
        }

        //获取指定类上注解
        ClassAnno classAnno = Student.class.getAnnotation(ClassAnno.class);
        System.out.println(classAnno);

        //获取字段上注解及参数值
        try {
            FieldAnno fieldAnno = Student.class.getField("name").getAnnotation(FieldAnno.class);
            System.out.println(fieldAnno +" "+ fieldAnno.name());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        //获取方法的注解
        try {
            Method method = Student.class.getMethod("setNameAndAge",String.class,int.class);
            MethodAnno methodAnno = method.getAnnotation(MethodAnno.class);
            System.out.println(methodAnno);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        //获取方法参数的注解
        try {
            Method method = Student.class.getMethod("setNameAndAge",String.class,int.class);
            Annotation[][] parameterAnnotations = method.getParameterAnnotations();
            for (int i=0;i<parameterAnnotations.length;i++){
                Annotation[] annotations2 = parameterAnnotations[i];
                for (int j = 0;j<annotations2.length;j++){
                    System.out.print(annotations2[j]+" ");
                }
                System.out.println();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }


    }
}

interface Animal {

}

class Person implements Animal {
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

@ClassAnno
class Student extends Person {
    @FieldAnno
    public String name;
    @FieldAnno
    private int age;

    public Student() {

    }

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @MethodAnno
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @MethodAnno
    public void setNameAndAge(@ParaAnno @ParaAnno2 String name, @ParaAnno @ParaAnno2 int age) {
        this.setName(name);
        this.setAge(age);
    }

    private void ceshi(){
        System.out.println("private方法调用");
    }
}