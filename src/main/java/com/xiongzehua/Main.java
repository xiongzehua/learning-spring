package com.xiongzehua;

import java.sql.*;

import com.xiongzehua.pojo.Person;
import com.xiongzehua.service.MarryService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;

/**
 * Created by 31339 on 2018/5/11.
 */
public class Main {
    public static void main(String[] args) throws Exception{
        aopDemo();
    }
    public static void beanDemo() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-pojo.xml");

        Person p = (Person) ctx.getBean("xiaoming");
        System.out.println(p.getCars().getClass());
        System.out.println(ctx.getBean("xiaohong"));
    }
    public static void jdbcDemo() throws Exception {

        //A. 建立数据库连接的过程
        final String URL="jdbc:mysql://192.168.1.120:3306/management?useUnicode=true&amp;characterEncoding=utf-8";
        final String USER="xiongMysql";
        final String PASSWORD="123456";

        //1.加载驱动程序
        Class.forName("com.mysql.jdbc.Driver");
        //2.获得目标数据库的连接
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);

        //B. 执行sql的过程
        //3.获得此连接的
        Statement stmt = conn.createStatement();
        //4.执行sql语句
		/*
		 * ResultSet 光标最初位于第一行之前；
		 * 第一次调用 next 方法使第一行成为当前行；
		 * 第二次调用使第二行成为当前行，依此类推。
		 */

        ResultSet result = stmt.executeQuery("SELECT * FROM persons;");
        while (result.next()) {
            System.out.println(result.getString(1) + " " + result.getString(2) + " "
                    + result.getString(3));
        }
    }

    /*
    interface javax.sql.DataSource
    DataSource取代的是jdbc中A. 建立数据库连接的过程
    DataSource 接口由驱动程序供应商实现。共有三种类型的实现：
    1. 基本实现 - 生成标准的 Connection 对象
    2. 连接池实现 - 生成自动参与连接池的 Connection 对象。此实现与中间层连接池管理器一起使用。
    3. 分布式事务实现 - 生成一个 Connection 对象，该对象可用于分布式事务，大多数情况下总是参与连接池。此实现与中间层事务管理器一起使用，大多数情况下总是与连接池管理器一起使用。

    基本方法：
    java.sql.Connection getConnection();

    实现1：
    SimpleDriverDataSource。
    spring自带，但不属于核心框架，需要<artifactId>spring-jdbc</artifactId>
    org.springframework.jdbc.datasource.SimpleDriverDataSource implements javax.sql.DataSource

    实现2
    DBCP连接池。
    需要<artifactId>commons-dbcp</artifactId>
    org.apache.commons.dbcp.BasicDataSource implements javax.sql.DataSource
    */

    /*
    下面以1. 基本实现的方法实现dateSource

     */
    public static void dataSourceDemo1() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-dataSource.xml");

        DataSource dataSource = (DataSource) ctx.getBean("dataSource1");
        try {
            System.out.println(dataSource.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void aopDemo() {
        ApplicationContext pojoContext = new ClassPathXmlApplicationContext("applicationContext-pojo.xml");
        ApplicationContext aopContext = new ClassPathXmlApplicationContext("applicationContext-aop.xml");

        Person p1 = (Person)pojoContext.getBean("xiaoming");
        Person p2 = (Person)pojoContext.getBean("xiaohong");

        /*
        若不开启aop功能
            IOC容器头通过getbean()返回的是被代理者,即com.xiongzehua.service.impl.MarryServiceImpl对象
        若开启aop功能
            IOC容器头通过getbean()返回的是代理者,即com.sun.proxy.$Proxy12对象
        代理者为spring自动生成
            *其中代理者 = 被代理者 + 拦截器(即切面对象)
            所以被代理者bean和拦截器bean要在同一个IOC容器下
         */
        MarryService service = (MarryService) aopContext.getBean("marryServiceImpl");
        service.marry(p1, p2);
        System.out.print(service.getClass());
    }
}