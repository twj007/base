package com.framework.web;

import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/08/07
 **/
public class TestBuilder {


    public static void main(String[] args) {
        RealBuilder builder = new RealBuilder();
        Commander commander = new Commander(builder);
        Product product = commander.build();
        System.out.println(product.getPartA());
        System.out.println(product.getPartB());
        System.out.println(product.getPartC());

    }
}

class Product{
    private String partA;

    private String partB;

    private String partC;

    public String getPartA() {
        return partA;
    }

    public void setPartA(String partA) {
        this.partA = partA;
    }

    public String getPartB() {
        return partB;
    }

    public void setPartB(String partB) {
        this.partB = partB;
    }

    public String getPartC() {
        return partC;
    }

    public void setPartC(String partC) {
        this.partC = partC;
    }
}

abstract class Builder{

    protected static Product product = new Product();

    abstract void buildA();

    abstract void buildB();

    abstract void buildC();

    public static Product build(){
        return product;
    }
}

class RealBuilder extends Builder{

    @Override
    void buildA() {
        product.setPartA("build part a");
    }

    @Override
    void buildB() {
        product.setPartB("build part b");
    }

    @Override
    void buildC() {
        product.setPartC("build part c");
    }
}

class Commander{

    private Builder builder;

    public Commander(Builder builder){
        this.builder = builder;
    }

    public Product build(){
        builder.buildA();
        builder.buildB();
        builder.buildC();
        return Builder.product;
    }
}