package com.itheima;


import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/*
    用户测试类
 */
public class UserTest {

    @Test//测试pooled
    public void testPooled() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("SQLMapConfig.xml");
        SqlSessionFactory ssf = new SqlSessionFactoryBuilder().build(inputStream);
        for (int i = 0; i < 5; i++) {
            SqlSession ss = ssf.openSession();
            System.out.println(ss.getConnection().hashCode());
            ss.close();
        }
    }

    @Test//插入用户列表
    public void insertTest() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("SQLMapConfig.xml");
        SqlSessionFactory ssf = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession ss = ssf.openSession();
        //执行插入语句
        User user = new User();
        user.setUsername("jack");
        user.setSex("男");
        user.setAddress("北京");

        int count = ss.insert("userMapper.insert", user);
        System.out.println("user表增加的行数："+ count);

        //user对象，已经存入到数据库中，说明user的属性id值已经有了，打印一下id值
        System.out.println("user Id值 =" + user.getId());
        //提交事务
        ss.commit();
        ss.close();
    }

    @Test//删除语句
    public void deleteTest() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("SQLMapConfig.xml");
        SqlSessionFactory ssf = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession ss = ssf.openSession();
        //执行删除语句
        ss.delete("userMapper.delete",10);
        //提交事务
        ss.commit();
        ss.close();
    }



    @Test//查询所有用户
    public void findALLTest() throws IOException {
        //加载核心配置文件SQLMapConfig.xml
        InputStream inputStream = Resources.getResourceAsStream("SQLMapConfig.xml");
        //构造SQLSessionFactory对象
        SqlSessionFactory ssf = new SqlSessionFactoryBuilder().build(inputStream);
        //构造SQLSession对象
        SqlSession ss = ssf.openSession();
        //执行查询语句
        List<User> userList = ss.selectList("userMapper.findALL");
        for (User user : userList) {
            System.out.println("user = "+ user);
            
        }
        //释放资源
        ss.close();
    }
    @Test//更新用户
    public void updateTest() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("SQLMapConfig.xml");
        SqlSessionFactory ssf = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession ss = ssf.openSession();
        //执行更新语句
        User user = new User();
        user.setId(7);
        user.setUsername("jack");
        user.setSex("男");
        user.setAddress("日本本");

        ss.update("userMapper.update",user);
        //提交事务
        ss.commit();
        ss.close();
    }

    @Test//根据用户Id查询用户
    public void findByIdTest() throws IOException {
        //加载核心配置文件SQLMapConfig.xml
        InputStream inputStream = Resources.getResourceAsStream("SQLMapConfig.xml");
        //构造SQLSessionFactory对象
        SqlSessionFactory ssf = new SqlSessionFactoryBuilder().build(inputStream);
        //构造SQLSession对象
        SqlSession ss = ssf.openSession();
        //执行查询语句
        User user = ss.selectOne("userMapper.findById", 7);
        System.out.println("user = "+ user);

        //释放资源
        ss.close();
    }

    @Test//根据用户名，模糊查询用户
    public void findByNameTest() throws IOException {
        //加载核心配置文件SQLMapConfig.xml
        InputStream inputStream = Resources.getResourceAsStream("SQLMapConfig.xml");
        //构造SQLSessionFactory对象
        SqlSessionFactory ssf = new SqlSessionFactoryBuilder().build(inputStream);
        //构造SQLSession对象
        SqlSession ss = ssf.openSession();
        //执行模糊查询语句
        List<User> userList = ss.selectList("userMapper.findByName", "j");
        for (User user : userList) {
            System.out.println("user = "+ user);
        }
        //释放资源
        ss.close();
    }
}
