package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@SpringBootTest
class BatchApplicationTest {


	@Autowired
	JdbcTemplate jdbcTemplate;

	@Test
	public void testDbRepo(){
		List list = jdbcTemplate.queryForList("select * from batch_job_instance");
		System.out.println("jdbctemplate:"+list.get(0).toString());
	}
}
