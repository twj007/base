package com.framework.web;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Ordering;
import com.google.common.collect.Range;
import com.google.common.primitives.Ints;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;

import static java.lang.Math.sqrt;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/07/29
 **/
public class TestGuava {


    public static void main(String[] args) {
        //testNull();
        //testPreCondition(1.0f);
        //testOrdering();
        //testObjects();
        //testRange();
//        testThrowables();
        ArrayList l = new ArrayList(Arrays.asList(1, 3, 4, 5));
        ArrayList l2 = (ArrayList) l.clone();
        System.out.println(l.equals(l2));
        System.out.println(l == l2);
        System.out.println(l);
        System.out.println(l2);


    }


    /***
     * 异常工具类
     */
    public static void testThrowables(){
        try {
            showcaseThrowables();
        } catch (InvalidInputException e) {
            //get the root cause
            System.out.println(Throwables.getRootCause(e));
        }catch (Exception e) {
            //get the stack trace in string format
            System.out.println(Throwables.getStackTraceAsString(e));
        }


    }

    public static void showcaseThrowables() throws InvalidInputException{
        try {
            int a = 1 / 0;
        } catch (Throwable e) {
            //check the type of exception and throw it
            Throwables.propagateIfPossible(e, InvalidInputException.class);

        }
    }

    static class InvalidInputException extends Exception{}


    /***
     * 获取一组数据或范围的数值
     */
    public static void testRange(){
        Range<Integer> range1 = Range.closed(0, 9);
        System.out.print("[0,9] : ");
        printRange(range1);
        System.out.println("5 is present: " + range1.contains(5));
        System.out.println("(1,2,3) is present: " + range1.containsAll(Ints.asList(1, 2, 3)));
        System.out.println("Lower Bound: " + range1.lowerEndpoint());
        System.out.println("Upper Bound: " + range1.upperEndpoint());

        //create a range (a,b) = { x | a < x < b}
        Range<Integer> range2 = Range.open(0, 9);
        System.out.print("(0,9) : ");
        printRange(range2);

        //create a range (a,b] = { x | a < x <= b}
        Range<Integer> range3 = Range.openClosed(0, 9);
        System.out.print("(0,9] : ");
        printRange(range3);

        //create a range [a,b) = { x | a <= x < b}
        Range<Integer> range4 = Range.closedOpen(0, 9);
        System.out.print("[0,9) : ");
        printRange(range4);

        //create an open ended range (9, infinity
        Range<Integer> range5 = Range.greaterThan(9);
        System.out.println("(9,infinity) : ");
        System.out.println("Lower Bound: " + range5.lowerEndpoint());
        System.out.println("Upper Bound present: " + range5.hasUpperBound());

        Range<Integer> range6 = Range.closed(3, 5);
        printRange(range6);

        //check a subrange [3,5] in [0,9]
        System.out.println("[0,9] encloses [3,5]:" + range1.encloses(range6));

        Range<Integer> range7 = Range.closed(9, 20);
        printRange(range7);
        //check ranges to be connected
        System.out.println("[0,9] is connected [9,20]:" + range1.isConnected(range7));

        Range<Integer> range8 = Range.closed(5, 15);

        //intersection
        printRange(range1.intersection(range8));

        //span
        printRange(range1.span(range8));


    }


    private static void printRange(Range<Integer> range){
        System.out.print("[ ");
        for(int grade : ContiguousSet.create(range, DiscreteDomain.integers())) {
            System.out.print(grade +" ");
        }
        System.out.println("]");
    }




    /****
     * Objects工具类
     */
    public static void testObjects(){
        Object a = null;
        Object b = null;
        System.out.println("equals" + Objects.equals(a, b));
        System.out.println("non null Object" + Objects.nonNull(a));
        System.out.println("is null Object" + Objects.isNull(b));
        System.out.println("全null返回true，有一个为null返回false， 对于数组有深层的元素比较" + Objects.deepEquals(a, b));
        System.out.println("对多个元素返回hashcode" + Objects.hash(a, b));
        System.out.println(Objects.requireNonNull(a, "the object should not be null"));
    }


    /****
     * 对空值的预先处理
     */
    public static void testNull(){
        Optional<Integer> a = Optional.of(null);
        Optional<Integer> b = Optional.of(1);
        System.out.println(a.get() + b.get());
    }

    /***
     * 对参数合法性预先校验
     * @param arg
     */
    public static void testPreCondition(float arg){
        Preconditions.checkNotNull(arg, "this arg should not be null");
        Preconditions.checkArgument(arg > 0.0f, "float input should not be less than 0");
        System.out.println(arg);
        int[] a = new int[]{1, 2, 3, 4};
        Preconditions.checkElementIndex(a.length+1, a.length, "out of bound");

    }

    /****
     * 排序类
     */
    public static void testOrdering(){
        List<Integer> numbers = new ArrayList<Integer>();
        numbers.add(new Integer(5));
        numbers.add(new Integer(2));
        numbers.add(new Integer(15));
        numbers.add(new Integer(51));
        numbers.add(new Integer(53));
        numbers.add(new Integer(35));
        numbers.add(new Integer(45));
        numbers.add(new Integer(32));
        numbers.add(new Integer(43));
        numbers.add(new Integer(16));

        Ordering ordering = Ordering.natural();
        System.out.println("Input List: ");
        System.out.println(numbers);

        Collections.sort(numbers,ordering );
        System.out.println("Sorted List: ");
        System.out.println(numbers);

        System.out.println("======================");
        System.out.println("List is sorted: " + ordering.isOrdered(numbers));
        System.out.println("Minimum: " + ordering.min(numbers));
        System.out.println("Maximum: " + ordering.max(numbers));

        Collections.sort(numbers,ordering.reverse());
        System.out.println("Reverse: " + numbers);

        numbers.add(null);
        System.out.println("Null added to Sorted List: ");
        System.out.println(numbers);

        Collections.sort(numbers,ordering.nullsFirst());
        System.out.println("Null first Sorted List: ");
        System.out.println(numbers);
        System.out.println("======================");

        List<String> names = new ArrayList<String>();
        names.add("Ram");
        names.add("Shyam");
        names.add("Mohan");
        names.add("Sohan");
        names.add("Ramesh");
        names.add("Suresh");
        names.add("Naresh");
        names.add("Mahesh");
        names.add(null);
        names.add("Vikas");
        names.add("Deepak");

        System.out.println("Another List: ");
        System.out.println(names);

        Collections.sort(names,ordering.nullsFirst().reverse());
        System.out.println("Null first then reverse sorted list: ");
        System.out.println(names);


    }


}
