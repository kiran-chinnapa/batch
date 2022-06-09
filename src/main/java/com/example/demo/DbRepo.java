package com.example.demo;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;

@Repository
@Log4j2
public class DbRepo {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void validateJdbcTemplate(){
        List list = jdbcTemplate.queryForList("select * from batch_job_instance");
        log.info("jdbctemplate:"+list.get(0).toString());
    }
}
