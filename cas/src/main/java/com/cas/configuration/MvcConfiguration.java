package com.cas.configuration;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/09/24
 **/
@Configuration
public class MvcConfiguration extends WebMvcConfigurationSupport {

    private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
    private static final String DEFAULT_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String DEFAULT_TIME_PATTERN = "HH:mm:ss";
    private static final String EXCEL_PATTERN = "yyyy/MM/dd";


    @Override
    protected void addFormatters(FormatterRegistry registry) {
        registry.addConverter(converter());
        registry.addConverter(concurrentConverter());
        registry.addConverter(datetimeConverter());
        registry.addConverter(timeConverter());
        super.addFormatters(registry);
    }

    /***
     * 线程不安全的转换器
     * @return
     */
    @Bean
    public Converter converter(){
        Converter<String, Date> dateConverter = new Converter<String, Date>() {
            @Override
            public Date convert(String source) {
                try {
                    return new SimpleDateFormat(DEFAULT_DATE_PATTERN).parse(source);
                } catch (ParseException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };

        return dateConverter;
    }

    @Bean
    public Converter concurrentConverter(){
        Converter<String, LocalDate> localDateConverter = new Converter<String, LocalDate>() {
            @Override
            public LocalDate convert(String source) {
                return  LocalDate.parse(source, DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_PATTERN));
            }
        };

        return  localDateConverter;
    }


    @Bean
    public Converter datetimeConverter(){
        Converter<String, LocalDateTime> timeConverter = new Converter<String, LocalDateTime>() {
            @Override
            public LocalDateTime convert(String source) {
                return LocalDateTime.parse(source, DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_PATTERN));
            }
        };

        return timeConverter;
    }

    @Bean
    public Converter timeConverter(){
        Converter<String, LocalTime> timeConverter = new Converter<String, LocalTime>() {
            @Override
            public LocalTime convert(String source) {
                return LocalTime.parse(source, DateTimeFormatter.ofPattern(DEFAULT_TIME_PATTERN));
            }
        };

        return timeConverter;
    }


//    public static void main(String[] args) {
//        MvcConfiguration c = new MvcConfiguration();
//        Converter cc = c.converter();
//        System.out.println(cc.convert("2019-09-10"));
//        Converter ccc = c.concurrentConverter();
//        System.out.println(ccc.convert("2019-09-10 11:20:00"));
//        Converter cccc = c.datetimeConverter();
//        System.out.println(cccc.convert("2019-09-10 11:20:00"));
//        Converter ccccc = c.timeConverter();
//        System.out.println(ccccc.convert("11:20:00"));
//    }
}
