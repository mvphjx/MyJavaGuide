package com.guide.common.io;

import cn.hutool.core.util.ObjectUtil;
import com.guide.common.model.Student;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
/**
 * 一个存储学生信息的简单数据库
 *
 * @author hjx
 * @version 1.0
 * @date 2021/7/3 18:37
 */
public class StudentDBDemo
{
    private BasicDB db;

    public StudentDBDemo(BasicDB db)
    {
        this.db = db;
    }

    public void saveStudents(Student student) throws IOException
    {
        db.put(student.getId() + "", ObjectUtil.serialize(student));
    }

    public List<Student> listStudents() throws IOException
    {
        List<Student> list = new ArrayList<>();
        Set<String> keys = db.getKeys();
        for (String key : keys)
        {
            byte[] bytes = db.get(key);
            Student student = ObjectUtil.deserialize(bytes);
            list.add(student);
        }
        return list;
    }

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception
    {
        BasicDB db = new BasicDB("./", "students");
        StudentDBDemo studentDBDemo = new StudentDBDemo(db);
        Student student1 = new Student(11L, "aaa", 18, 95);
        studentDBDemo.saveStudents(student1);
        List<Student> students = studentDBDemo.listStudents();
        students.forEach(System.out::println);
        db.close();
    }

}
