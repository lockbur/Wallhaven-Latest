package com.anhao.spring.dao;

import org.apache.ibatis.annotations.Insert;

import com.anhao.spring.domain.Tag;

/**
 * INSERT into xx_tag(id,name,member_id,create_date,modify_date) VALUES('kf8081814fe8474a014fe84789830000','test','1','2015-09-20 09:04:06','2015-09-20 09:04:06');

 * @author Administrator
 *
 */
public interface TagDao {
	public static final String SQL = "INSERT INTO biz.xx_tag "
			+ "(id,name,member_id,create_date,modify_date)"
			+ " VALUES (#{id},#{name},#{member_id},#{create_date},#{modify_date})";
	
	@Insert(SQL)
	public int add(Tag Tag);
}
