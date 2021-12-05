package com.java.guide.common.uml.demo;

import java.util.List;

public class Cat extends Animal
{
    private BasicInfo basicInfo;
    private List<Hobby> hobbyList;
    @Override
    public void Sound()
    {
        System.out.println("喵喵");
    }
}
