package com.guide.common.lambda;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.guide.common.model.Student;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Stream API
 * 常见的集合数据处理
 * 流定义了很多数据处理的基本函数，对于一个具体的数据处理问题，
 * 解决的主要思路就是组合利用这些基本函数，以声明式的方式简洁地实现期望的功能，
 * 这种思路就是函数式数据处理思维，相比直接利用容器类API的命令式思维，思考的层次更高。
 *
 * @author hjx
 * @version 1.0
 * @date 2021/7/7 23:24
 */
public class StreamDemo
{
    private List<Student> students = new ArrayList<>();

    {
        students.add(new Student(0L, "小明1", 20, 95));
        students.add(new Student(0L, "小明2", 19, 66));
        students.add(new Student(0L, "小明3", 20, 88));
        students.add(new Student(0L, "小明4", 21, 99));
        students.add(new Student(0L, "小明5", 19, 70));
    }

    /**
     * 返回90分以上的学生名称列表,按照分数降序排列
     * <p>
     * 1）过滤：得到90分以上的学生列表。
     * 2）排序：默认是升序，想要降序排列 o2-o1
     * 3）转换：将学生列表转换为名称列表。
     */
    @Test
    public void above90NamesLambda()
    {
        System.out.println("\nabove90NamesLambda");
        List<String> names = students.stream().filter(t -> t.getScore() > 90)
                .sorted(Comparator.comparing(Student::getScore).reversed()).map(Student::getName)
                .collect(Collectors.toList());
        names.forEach(System.out::println);
    }

    /**
     * 传统写法
     */
    @Test
    public void above90NamesFunction()
    {
        System.out.println("\nabove90NamesFunction");
        List<String> names = new ArrayList<>();
        List<Student> list = new ArrayList<>();
        for (Student student : students)
        {
            if (student.getScore() > 90)
            {
                list.add(student);
            }
        }
        list.sort(new Comparator<Student>()
        {
            @Override
            public int compare(Student o1, Student o2)
            {
                return (int) (o2.getScore() - o1.getScore());
            }
        });
        for (Student student : list)
        {
            names.add(student.getName());
        }
        for (String name : names)
        {
            System.out.println(name);
        }

    }

    /**
     * 分组类似于数据库查询语言SQL中的group by语句，
     * 它将元素流中的每个元素分到一个组，可以针对分组再进行处理和收集。
     *
     */
    @Test
    public void groupByAgeLambda()
    {
        Map<Integer, List<Student>> groups = students.stream().collect(Collectors.groupingBy(Student::getAge));
        JSON json = JSONUtil.parse(groups);
        System.out.println(JSONUtil.toJsonStr(json, 2));
        /**
         * groupingBy 参数如下，缺省为
         * Function   生成分组主键
         * Supplier   创建空Map HashMap::new
         * Collector  收集器 Collectors.toList()
         */
        Map<Integer, List<Student>> groupsBase = students.stream().collect(Collectors.groupingBy(Student::getAge));
        System.out.println(JSONUtil.toJsonStr(JSONUtil.parse(groupsBase), 2));
    }

    /**
     * 按照年龄分组，成绩降序排序
     * <p>
     * 高版本jdk会更方便一些，有对应的API
     * filter/sort/skip/limit
     */
    @Test
    public void groupByAgeSortScoreLambda()
    {
        Map<Integer, List<Student>> groups = students.stream().collect(Collectors.groupingBy(Student::getAge, Collectors
                .collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Student::getScore).reversed())),
                        ArrayList::new)));
        JSON json = JSONUtil.parse(groups);
        System.out.println(JSONUtil.toJsonStr(json, 2));
    }

    /**
     * 按照年龄分组，取最高分学生
     */
    @Test
    public void groupByAgeMaxScoreLambda()
    {
        Map<Integer, Optional<Student>> groups = students.stream().collect(
                Collectors.groupingBy(Student::getAge, Collectors.maxBy(Comparator.comparing(Student::getScore))));
        JSON json = JSONUtil.parse(groups);
        System.out.println(JSONUtil.toJsonStr(json, 2));
    }

    /**
     * 按照年龄分组，对成绩进行统计、分析
     */
    @Test
    public void groupByAgeSumScoreLambda()
    {
        Map<Integer, DoubleSummaryStatistics> collect = students.stream()
                .collect(Collectors.groupingBy(Student::getAge, Collectors.summarizingDouble(Student::getScore)));
        JSON json = JSONUtil.parse(collect);
        System.out.println(JSONUtil.toJsonStr(json, 2));
        //计算总数
        Map<Integer, Long> collectCount = students.stream()
                .collect(Collectors.groupingBy(Student::getAge, Collectors.counting()));
        System.out.println(JSONUtil.toJsonStr(collectCount));

    }

}
