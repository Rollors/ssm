import com.atguigu.mybatis.mapper.CacheMapper;
import com.atguigu.mybatis.pojo.Emp;
import com.atguigu.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.apache.ibatis.io.Resources;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class CacheMapperTest {

    /**
     * mybatis一级缓存（默认开启）：
     *  SqlSession级别，即通过同一个SqlSession查询的数据会被缓存
     *  再次使用同一个SqlSession查询同一个数据，会从缓存中获取
     * 一级缓存失效情况：
     *  1) 不同的SqlSession对应不同的一级缓存
     *  2) 同一个SqlSession但是查询条件不同
     *  3) 同一个SqlSession两次查询期间执行了任何一次增删改操作
     *  4) 同一个SqlSession两次查询期间手动清空了缓存
     *
     * mybatis二级缓存：
     *  SqlSessionFactory级别，即通过同一个SqlSessionFactory所获取的SqlSession对象
     *  查询的数据会被缓存，再同一个SqlSessionFactory所获取的另一个SqlSession查询的数据会从缓存中获取
     * 二级缓存开启条件：
     *  a>在核心配置文件中，设置全局配置属性cacheEnabled="true"，默认为true，不需要设置
     *  b>在映射文件中设置标签<cache/>
     *  c>二级缓存必须在SqlSession关闭或提交之后有效
     *  d>查询的数据所转换的实体类类型必须实现序列化的接口
     * 使二级缓存失效的情况：
     *  两次查询之间执行了任意的增删改，会使一级和二级缓存同时失效
     *
     * 二级缓存的相关配置：
     *   在mapper配置文件中添加的cache标签可以设置一些属性：
     *      1、eviction属性：缓存回收策略，默认的是 LRU。
     *          LRU（Least Recently Used） – 最近最少使用的：移除最长时间不被使用的对象。
     *          FIFO（First in First out） – 先进先出：按对象进入缓存的顺序来移除它们。
     *          SOFT – 软引用：移除基于垃圾回收器状态和软引用规则的对象。
     *          WEAK – 弱引用：更积极地移除基于垃圾收集器状态和弱引用规则的对象。
     *      2、flushInterval属性：刷新间隔，单位毫秒
     *          默认情况是不设置，也就是没有刷新间隔，缓存仅仅调用语句时刷新
     *      3、size属性：引用数目，正整数
     *          代表缓存最多可以存储多少个对象，太大容易导致内存溢出
     *      4、readOnly属性：只读， true/false
     *          true：只读缓存；会给所有调用者返回缓存对象的相同实例。因此这些对象不能被修改。这提供了 很重
     *          要的性能优势。
     *          false：读写缓存；会返回缓存对象的拷贝（通过序列化）。这会慢一些，但是安全，因此默认是
     *          false。
     * 缓存查询顺序：
     *   先查询二级缓存，因为二级缓存中可能会有其他程序已经查出来的数据，可以拿来直接使用。
     *   如果二级缓存没有命中，再查询一级缓存
     *   如果一级缓存也没有命中，则查询数据库
     *   SqlSession关闭之后，一级缓存中的数据会写入二级缓存
     */

    @Test
    public void testCache() throws IOException {
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        SqlSession sqlSession1 = sqlSessionFactory.openSession(true);
        CacheMapper cacheMapper = sqlSession.getMapper(CacheMapper.class);
        Emp emp = cacheMapper.getEmpById(1);
        System.out.println(emp);
        sqlSession.close();
        CacheMapper cacheMapper1 = sqlSession1.getMapper(CacheMapper.class);
        Emp emp1 = cacheMapper1.getEmpById(1);
        System.out.println(emp1);
        sqlSession1.close();
    }

    @Test
    public  void testGetEmpById(){
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        CacheMapper cachemapper = sqlSession.getMapper(CacheMapper.class);
        Emp emp = cachemapper.getEmpById(1);
        System.out.println(emp);
        //手动清空一级缓存
        sqlSession.clearCache();
        //插入数据
//        cachemapper.insertEmp(new Emp(null,"ajsd",23,"男"));
        Emp emp1 = cachemapper.getEmpById(1);
        System.out.println(emp1);
        sqlSession.close();
        SqlSession sqlSession1 = SqlSessionUtil.getSqlSession();
        CacheMapper cachemapper1 = sqlSession1.getMapper(CacheMapper.class);
        Emp emp2 = cachemapper1.getEmpById(1);
        System.out.println(emp2);
        sqlSession1.close();
    }

}
