package com.atguigu.mybatis.mapper;

import com.atguigu.mybatis.pojo.Emp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DynamicSQLMapper {

    /**
     * 根据条件查询员工信息
     * @param emp
     * @return
     */
    List<Emp> getEmpByCondition(Emp emp);

    /**
     * where标签查询员工信息
     * @param emp
     * @return
     */
    List<Emp> getEmpByConditionOne(Emp emp);

    /**
     * trim标签查询员工信息
     * @param emp
     * @return
     */
    List<Emp> getEmpByConditionTwo(Emp emp);

    /**
     * choose标签查询员工信息
     * @param emp
     * @return
     */
    List<Emp> getEmpByChoose(Emp emp);

    /**
     * 批量添加员工信息
     * @param emps
     */
    void insertMoreEmp(@Param("emps") List<Emp> emps);

    /**
     * 批量删除员工信息
     * @param empIds
     */
    void deleteMoreEmp(@Param("empIds") Integer[] empIds);
}
