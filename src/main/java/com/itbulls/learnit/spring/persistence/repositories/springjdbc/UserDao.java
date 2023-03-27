package com.itbulls.learnit.spring.persistence.repositories.springjdbc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.itbulls.learnit.spring.persistence.entities.User;
import com.itbulls.learnit.spring.persistence.rowmappers.UserRowMapper;

@Repository
public class UserDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired 
	private DataSource dataSource;
	
	public Integer getTotalUsersCount() {
		return jdbcTemplate.queryForObject(
			    "SELECT COUNT(*) FROM user", Integer.class);
	}
	
	public int addSampleUser() {
	    return jdbcTemplate.update(
	      "INSERT INTO user (first_name, last_name, enabled) VALUES (?, ?, ?)", "John", "Doe", 1);
	}
	
	public String getLastNameOfUserById(Integer id) {
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", id);
		return namedParameterJdbcTemplate.queryForObject(
				"SELECT last_name FROM user WHERE ID = :id", namedParameters, String.class);
	}
	
	public String getLastNameOfUserByUserBean(User user) {
		SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(user);
		return namedParameterJdbcTemplate.queryForObject(
				"SELECT last_name FROM user WHERE ID = :id", namedParameters, String.class);
		
	}
	
	public int[] batchInsertDemo(List<User> users) {
		SqlParameterSource[] batch = 
				SqlParameterSourceUtils.createBatch(users.toArray());
		int[] updateCounts = namedParameterJdbcTemplate.batchUpdate(
		    "INSERT INTO user_test (first_name, last_name) "
		    + "VALUES (:firstName, :lastName)", batch);
		return updateCounts;
	}
	
	public User getUserById(Integer id) {
		return jdbcTemplate.queryForObject(
				  "SELECT * FROM user WHERE ID = ?", new Object[] {id}, 
				  new int[] {java.sql.Types.INTEGER}, new UserRowMapper());
	}
	
	
	public int addSampleUserWithSimpleJdbcInsertDemo() {
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
					.withTableName("user_test");
		
		Map<String, Object> parameters = new HashMap<String, Object>();
	    parameters.put("first_name", "John");
	    parameters.put("last_name", "Doe");
	    parameters.put("enabled", 1);

//	    simpleJdbcInsert.usingGeneratedKeyColumns("id");
//	    simpleJdbcInsert.executeAndReturnKey(parameters);
	    
	    return simpleJdbcInsert.execute(parameters);
	}
	
	
	
	/*
	Create Stored Procedure in MySQL first: 
	
	DELIMITER //
	
	CREATE PROCEDURE SelectFirstLastNameByID (IN id decimal(30), OUT out_first_name VARCHAR(45), OUT out_last_name VARCHAR(45))
	BEGIN
	SELECT first_name INTO out_first_name FROM user u WHERE u.id = id;
	SELECT last_name INTO out_last_name FROM user u WHERE u.id = id;
	END //
	
	DELIMITER ;


	*/
	
	public User getUserWithFirstLastNameByIdFromStoredProcedure(Integer id) {
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(dataSource)
                .withProcedureName("SelectFirstLastNameByID");
		
		SqlParameterSource in = new MapSqlParameterSource().addValue("id", id);
	    Map<String, Object> out = simpleJdbcCall.execute(in);

	    User user = new User();
	    user.setFirstName((String) out.get("out_first_name"));
	    user.setLastName((String) out.get("out_last_name"));

	    return user;
	}
}
