package com.guide.common.lambda;

import com.guide.common.util.PrintUtil;
import lombok.Data;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Map Stream API
 * 常见的集合数据处理
 * 流定义了很多数据处理的基本函数，对于一个具体的数据处理问题，
 * 解决的主要思路就是组合利用这些基本函数，以声明式的方式简洁地实现期望的功能，
 * 这种思路就是函数式数据处理思维，相比直接利用容器类API的命令式思维，思考的层次更高。
 *
 * @author hjx
 * @version 1.0
 * @date 2021/7/7 23:24
 */
public class MapApiDemo
{

    @Test
    public void mapMergeTest()
    {
        List<StudentScore> studentScoreList = buildATestList();
        // 按照学生分组，求得每个学生的总分
        // 常规做法
        Map<String, Integer> studentScoreMap = new HashMap<>();
        studentScoreList.forEach(studentScore -> {
            if (studentScoreMap.containsKey(studentScore.getStuName()))
            {
                studentScoreMap.put(studentScore.getStuName(),
                        studentScoreMap.get(studentScore.getStuName()) + studentScore.getScore());
            }
            else
            {
                studentScoreMap.put(studentScore.getStuName(), studentScore.getScore());
            }
        });
        System.out.println("forEach");
        PrintUtil.println(studentScoreMap);
        // merge() 方法
        Map<String, Integer> studentScoreMap2 = new HashMap<>();
        studentScoreList.forEach(
                studentScore -> studentScoreMap2.merge(studentScore.getStuName(), studentScore.getScore(),
                        Integer::sum));
        System.out.println("merge");
        PrintUtil.println(studentScoreMap2);
        // lambda
        Map<String, Integer> collect = studentScoreList.stream().collect(
                Collectors.groupingBy(StudentScore::getStuName, Collectors.summingInt(StudentScore::getScore)));
        System.out.println("lambda");
        PrintUtil.println(collect);
    }

    @Test
    public void mapComputeTest()
    {
        String k = "key";
        Map<String, Integer> map = new HashMap<String, Integer>()
        {{
            put(k, 1);
        }};
        // 2
        System.out.println(map.compute(k, (key, oldVal) -> oldVal + 1));
    }

    private List<StudentScore> buildATestList()
    {
        List<StudentScore> studentScoreList = new ArrayList<>();
        StudentScore studentScore1 = new StudentScore()
        {{
            setStuName("张三");
            setSubject("语文");
            setScore(70);
        }};
        StudentScore studentScore2 = new StudentScore()
        {{
            setStuName("张三");
            setSubject("数学");
            setScore(80);
        }};
        StudentScore studentScore3 = new StudentScore()
        {{
            setStuName("张三");
            setSubject("英语");
            setScore(65);
        }};
        StudentScore studentScore4 = new StudentScore()
        {{
            setStuName("李四");
            setSubject("语文");
            setScore(68);
        }};
        StudentScore studentScore5 = new StudentScore()
        {{
            setStuName("李四");
            setSubject("数学");
            setScore(70);
        }};
        StudentScore studentScore6 = new StudentScore()
        {{
            setStuName("李四");
            setSubject("英语");
            setScore(90);
        }};
        StudentScore studentScore7 = new StudentScore()
        {{
            setStuName("王五");
            setSubject("语文");
            setScore(80);
        }};
        StudentScore studentScore8 = new StudentScore()
        {{
            setStuName("王五");
            setSubject("数学");
            setScore(85);
        }};
        StudentScore studentScore9 = new StudentScore()
        {{
            setStuName("王五");
            setSubject("英语");
            setScore(70);
        }};

        studentScoreList.add(studentScore1);
        studentScoreList.add(studentScore2);
        studentScoreList.add(studentScore3);
        studentScoreList.add(studentScore4);
        studentScoreList.add(studentScore5);
        studentScoreList.add(studentScore6);
        studentScoreList.add(studentScore7);
        studentScoreList.add(studentScore8);
        studentScoreList.add(studentScore9);

        return studentScoreList;
    }

    @Data
    private class StudentScore
    {
        private String stuName;
        private String subject;
        private Integer score;
    }
}
