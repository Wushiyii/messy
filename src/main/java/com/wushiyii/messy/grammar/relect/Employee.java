package com.wushiyii.messy.grammar.relect;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Employee extends Person {
    private static Integer totalNum = 0; // 员工数
    private int empNo;   // 员工编号 公有
    private String position;  // 职位 保护
    private int salary; // 工资   私有

    public void sayHello() {
        System.out.println(String.format("员工:%s, 今年:%s 岁, 爱好:%s, 工作:%s, 月入:%s元 \n", name, age, getHobby(), position, salary));
    }

    private void work() {
        System.out.println(String.format("My name is %s, 工作中勿扰.", name));
    }

    public Employee(String name, Integer age, String hobby, int empNo, String position, int salary) {
        super(name, age, hobby);
        this.empNo = empNo;
        this.position = position;
        this.salary = salary;
        Employee.totalNum++;
    }

    public static void main(String[] args) {
        Person person = new Person("Tommy", 18, "swim");
        System.out.println(person);
        Class<? extends Person> personClass = person.getClass();
        System.out.println(personClass.getFields()[0].getType().getName());
        System.out.println(personClass.getConstructors());
    }
}
  