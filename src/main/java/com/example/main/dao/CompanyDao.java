package com.example.main.dao;

import com.example.main.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2018/12/6.
 */
@Repository
public interface CompanyDao extends JpaRepository<Company,Long>{
@Query(value = "select * from company c where c.c_username=:c_username and c.c_password =:password",nativeQuery = true)
    Company CLogin(@Param("c_username") String c_username, @Param("password") String password);
@Query(value = "select * from company c where c.c_id = :id",nativeQuery = true)
    Company findById(@Param("id") int id);
@Query(value = "select * from company c where c.c_check_status =:status",nativeQuery = true)
    List<Company> chenckcompany(@Param("status")String status);
@Query(value = "update company c set c.c_check_status =:status where c_id=:cid",nativeQuery = true)
    Company PassCheck(@Param("cid") int cid,@Param("status") String status);
}
