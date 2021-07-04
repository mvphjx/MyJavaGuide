package com.guide.common.model;

import java.io.Serializable;

public class Student implements Serializable
{
    private Long id;
    private String name;
    private int age;
    private double score;

    public Student()
    {
        super();
    }

    public Student(Long id, String name, int age, double score)
    {
        super();
        this.id = id;
        this.name = name;
        this.age = age;
        this.score = score;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    public double getScore()
    {
        return score;
    }

    public void setScore(double score)
    {
        this.score = score;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    @Override
    public String toString()
    {
        return "Student{" + "id=" + id + ", name='" + name + '\'' + ", age=" + age + ", score=" + score + '}';
    }
}
