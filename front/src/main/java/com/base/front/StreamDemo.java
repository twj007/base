package com.base.front;

import javafx.concurrent.Task;
import javafx.concurrent.Worker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/03/28
 **/
public class StreamDemo {

    public static void main(String[] args) {
        List<String> l = new ArrayList<>(Arrays.asList("1,3,5,7,9"));
        Stream s = Stream.of(l);
        //构建流数据
        Stream.generate(Math::random).limit(5).forEach(System.out::println);
        //将数据已流的方式迭代输出
        //s.forEach((x) -> System.out.println(x));
        Stream.iterate(0, (x)-> x+1).limit(10).forEach(System.out::println);
    }


}
