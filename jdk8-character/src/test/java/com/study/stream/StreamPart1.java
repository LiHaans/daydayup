package com.study.stream;

import com.study.bean.Person;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @Auther: lihang
 * @Date: 2021-07-01 14:17
 * @Description: Stream
 */
@Slf4j
public class StreamPart1 {
    private List<Person> personList = new ArrayList<Person>();

    @Before
    public void createData(){

        personList.add(new Person("Tom", 8900, 23, "male", "New York"));
        personList.add(new Person("Jack", 7000, 34, "male", "Washington"));
        personList.add(new Person("Lily", 7800, 43, "female", "Washington"));
        personList.add(new Person("Anni", 8200, 32, "female", "New York"));
        personList.add(new Person("Owen", 9500, 24, "male", "New York"));
        personList.add(new Person("Alisa", 7900, 34, "female", "New York"));

    }

    @Test
    public void createStream(){
        List<String> list = Arrays.asList("a", "b", "c");

        // 创建一个顺序流
        Stream<String> stream = list.stream();
        // 创建一个并行流
        Stream<String> parallelStream = list.parallelStream();

        int[] arrays = {1, 2, 3, 4, 5, 6};
        IntStream arrayStream = Arrays.stream(arrays);

        Stream<Integer> integerStream = Stream.of(1, 2, 3, 4, 5, 6);

        Stream<Integer> limit = Stream.iterate(0, (x) -> x + 3).limit(3);
        limit.forEach(System.out::println);

        Stream<Double> stream1 = Stream.generate(Math::random).limit(3);

        stream1.forEach(System.out::println);

    }

    // forEach  find  match
    @Test
    public void test(){
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Stream<Integer> stream = list.stream();

        // 遍历输出符合条件的元素
        stream.filter(x->x>6).forEach(System.out::println);
        // 匹配第一个
        Optional<Integer> first = list.stream().filter(x -> x > 6).findFirst();
        // 匹配任意一个
        Optional<Integer> any = list.stream().filter(x -> x > 6).findAny();
        // 匹配任意一个(适用于并行流)
        Optional<Integer> any1 = list.parallelStream().filter(x -> x > 6).findAny();

        // 是否包含符合特定条件的元素
        boolean result = list.stream().anyMatch(x -> x < 6);

        log.info("first {}" ,first.get());
        log.info("stream any {}", any.get());
        log.info("parallel stream any {}",any1.get());
        log.info("anyMatch {}", result);
    }

    // filter
    @Test
    public void filterTest() {
        // 筛选员工中工资高于8000的人，并形成新的集合
        List<String> names = personList.stream().filter(x -> x.getSalary() > 8000)
                .map(Person::getName).collect(Collectors.toList());
        names.forEach(System.out::println);
    }

    // max min avg
    @Test
    public void aggregationTest() {
        // 获取String集合中最长的元素
        List<String> list = Arrays.asList("adnm", "admmt", "pot", "xbangd", "weoujgsd");
        Optional<String> max = list.stream().max(Comparator.comparing(String::length));
        log.info("max {} " , max.get());

        // 获取Integer集合中的最大值
        List<Integer> list1 = Arrays.asList(7, 6, 9, 4, 11, 6);
        Optional<Integer> max1 = list1.stream().max(Integer::compareTo);
        log.info("integer max {}", max1.get());

        // 获取员工工资最高的人
        Optional<Person> max2 = personList.stream().max(Comparator.comparing(Person::getSalary));
        log.info("person salary max {}", max2.get());

        // 计算Integer集合中大于6的元素的个数
        List<Integer> list2 = Arrays.asList(7, 6, 4, 8, 2, 11, 9);
        long count = list2.stream().filter(x -> x > 6).count();
        log.info("count {}", count);

    }

    // map flatMap
    @Test
    public void mappingTest() {
        // 英文字符串数组的元素全部改为大写。
        String[] strArr = { "abcd", "bcdd", "defde", "fTr" };
        List<String> collect = Arrays.stream(strArr).map(x -> x.toUpperCase())
                .collect(Collectors.toList());
        collect.forEach(System.out::println);

        // 整数数组每个元素+3
        List<Integer> intList = Arrays.asList(1, 3, 5, 7, 9, 11);
        List<Integer> collect1 = intList.stream().map(x -> x + 3).collect(Collectors.toList());
        collect1.forEach(System.out::println);

        // 将员工的薪资全部增加1000
        List<Integer> collect2 = personList.stream().map(x -> {
            x.setSalary(x.getSalary() + 1000);
            return x.getSalary();
        }).collect(Collectors.toList());
        collect2.forEach(System.out::println);

        log.info("二次改动前：" + personList.get(0).getName() + "-->" + personList.get(0).getSalary());

        // 将两个字符数组合并成一个新的字符数组
        List<String> list = Arrays.asList("m,k,l,a", "1,3,5,7");
        List<String> collect3 = list.stream().flatMap(x -> {
            String[] split = x.split(",");
            Stream<String> stream = Arrays.stream(split);
            return stream;
        }).collect(Collectors.toList());

        log.info("source list {} ", list.toString());
        log.info("new list {} ", collect3.toString());

        System.out.println(list);
        System.out.println(collect3);



    }

    // reduce
    @Test
    public void reduceTest() {
        // 求Integer集合的元素之和、乘积和最大值
        List<Integer> list = Arrays.asList(1, 3, 2, 8, 11, 4);
        Optional<Integer> reduce = list.stream().reduce((x, y) -> x + y);
        log.info("reduce 1 sum {} ", reduce.get());
        Optional<Integer> reduce1 = list.stream().reduce(Integer::sum);
        log.info("reduce 2 sum {}", reduce1.get());
        Integer reduce2 = list.stream().reduce(0, Integer::sum);
        log.info("reduce 3 sum {} ", reduce2);

        // 乘积
        Optional<Integer> reduce3 = list.stream().reduce((x, y) -> x * y );
        log.info("reduce 1 product {} ", reduce3.get());
        // 最大值
        Optional<Integer> reduce4 = list.stream().reduce((x, y) -> x > y ? x : y);
        log.info("reduce 1 max {} ", reduce4.get());
        // 最大值
        Integer reduce5 = list.stream().reduce(1, Integer::max);
        log.info("reduce 2 max {}" , reduce5);

        // 求所有员工的工资之和和最高工资
        Optional<Integer> sumSalary1 = personList.stream().map(Person::getSalary).reduce((x, y) -> x + y);
        Integer sumSalary2 = personList.stream().reduce(0, (sum, p) -> sum += p.getSalary(), (sum1, sum2) -> sum1 + sum2);
        Integer sumSalary3 = personList.stream().reduce(0, (sum, p) -> sum += p.getSalary(), Integer::sum);
        log.info("sumSalary1 {}", sumSalary1.get());
        log.info("sumSalary2 {}", sumSalary2);
        log.info("sumSalary3 {}", sumSalary3);

        Optional<Integer> max1 = personList.stream().map(Person::getSalary).reduce(Integer::max);
        log.info("max 1 {}", max1.get());
        Integer max2 = personList.stream().reduce(0, (max, p) -> max > p.getSalary() ? max : p.getSalary(), Integer::max);

        Integer max5 = personList.stream().reduce(0, (max, p) -> max > p.getSalary() ? max : p.getSalary(), (max3, max4) -> max3 > max4 ? max3 : max4);

        log.info("max2 {}", max2);
        log.info("max4 {}", max5);

    }

    // count averaging
    @Test
    public void summarizingTest(){
        // 求总数
        Long count = personList.stream().collect(Collectors.counting());
        // 求平均工资
        Double avg = personList.stream().collect(Collectors.averagingDouble(Person::getSalary));
        // 求最高工资
        Optional<Integer> max = personList.stream().map(Person::getSalary).collect(Collectors.maxBy(Integer::compareTo));
        // 求工资之和
        Double sum = personList.stream().collect(Collectors.summingDouble(Person::getSalary));
        // 一次性统计所有信息
        DoubleSummaryStatistics statistics = personList.stream().collect(Collectors.summarizingDouble(Person::getSalary));

        log.info("count {}" ,count);
        log.info("avg {}" ,avg);
        log.info("max {}" ,max.get());
        log.info("sum {}" ,sum);
        log.info("statistics {}" ,statistics.toString());


    }

    // partitioningBy  groupingBy
    @Test
    public void partitioningByAndGroupingByTest() {
        // 将员工按薪资是否高于8000分组
        Map<Boolean, List<Person>> p1List = personList.stream()
                .collect(Collectors.partitioningBy(x -> x.getSalary() > 8000));
        System.out.println(p1List);
        // 将员工按性别分组
        Map<String, List<Person>> sexList1 = personList.stream().collect(Collectors.groupingBy(x -> x.getSex()));
        System.out.println(sexList1);
        // 将员工先按性别分组，再按地区分组
        Map<String, Map<String, List<Person>>> map = personList.stream()
                .collect(Collectors.groupingBy(p -> p.getSex(),
                        Collectors.groupingBy(p -> p.getArea())));

        System.out.println(map);
    }

    // joining
    @Test
    public void joiningTest() {
        String names = personList.stream().map(Person::getName).collect(Collectors.joining(","));
        log.info(names);
    }

    // reducing
    @Test
    public void reducingTest() {
        // 每个员工减去起征点后的薪资之和
        Integer collect = personList.stream().collect(Collectors.reducing(0, Person::getSalary, (p1, p2) -> p1 + p2 - 5000));
        log.info("collection {}" , collect);

        // stream的reduce
        Optional<Integer> reduce = personList.stream().map(Person::getSalary).reduce(Integer::sum);
        log.info("sum {} ", reduce.get());
    }


    // sorted
    @Test
    public void sortedTest() {
        // 按工资升序排序（自然排序）
        List<String> list = personList.stream().sorted(Comparator.comparing(Person::getSalary))
                .map(Person::getName)
                .collect(Collectors.toList());

        // 按工资倒序排序
        personList.stream().sorted(Comparator.comparing(Person::getSalary).reversed())
                .map(Person::getName)
                .collect(Collectors.toList());

        // 先按工资再按年龄升序排序
        List<String> list2 = personList.stream().sorted(Comparator.comparing(Person::getSalary)
                .thenComparing(Person::getAge)).map(Person::getName)
                .collect(Collectors.toList());

        // 先按工资再按年龄自定义排序（降序）
        List<String> list3 = personList.stream().sorted((p1, p2) -> {
            if (p1.getSalary() == p2.getSalary()) {
                return p2.getAge() - p1.getAge();
            } else {
                return p2.getSalary() - p1.getSalary();
            }
        }).map(Person::getName).collect(Collectors.toList());
    }

    // skip limit concat distinct
    @Test
    public void test1() {
        String[] arr1 = { "a", "b", "c", "d" };
        String[] arr2 = { "d", "e", "f", "g" };

        Stream<String> stream1 = Stream.of(arr1);
        Stream<String> stream2 = Stream.of(arr2);

        // concat 合并  distinct 去重
        List<String> list = Stream.concat(stream1, stream2).distinct().collect(Collectors.toList());
        System.out.println(list);

        List<Integer> list1 = Stream.iterate(0, x -> x + 2).skip(2).limit(4).collect(Collectors.toList());
        System.out.println(list1);
    }
}
