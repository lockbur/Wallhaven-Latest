package com.anhao.spring.dao;

import org.apache.ibatis.annotations.Insert;

import com.anhao.spring.domain.Photos;
import org.apache.ibatis.annotations.Select;
/**
 * INSERT INTO biz.xx_photos (
	id,
	create_date,
	modify_date,
	large,
	MEDIUM,
	orders,
	thumbnail,
	source,
	title,
	album_id,
	member_id,
	wallhaven,
	storage_host
)
VALUES
	(
		'kf8081814fe8474a014fe84789830000',
		'2015-09-20 09:04:06',
		'2015-09-20 09:04:06',
		'http://:8888/M00/00/00/eznwC1X-Bg2AP4_cABRsZsJ6vmM675.png',
		'http://:8888/M00/00/00/eznwC1X-Bg2AP4_cABRsZsJ6vmM675.png',
		'1',
		'http://:8888/M00/00/00/eznwC1X-Bg2AP4_cABRsZsJ6vmM675.png',
		'http://:8888/M00/00/00/eznwC1X-Bg2AP4_cABRsZsJ6vmM675.png',
		'2015-05-17 20:32:54 ����Ļ��ͼ.png',
		'ff8081814f7e13d8014f7e18a95a0000',
		'1',
		NULL,
		"http://aboutdata.me"
	);
 * @author Administrator
 *
 */
public interface JobPhotosDAO {
	
	public static final String SQL = "INSERT INTO biz.xx_photos "
			+ "(id,create_date,modify_date,large,MEDIUM,orders,thumbnail,source,title,album_id,member_id,wallhaven,storage_host,status)"
			+ " VALUES (#{id},#{create_date},#{modify_date},#{large},#{medium},#{orders},#{thumbnail},#{source},#{title},#{album_id},#{member_id},#{wallhaven},#{storage_host},'UNASSIGNED')";
	
	@Insert(SQL)
	public int add(Photos photos);
        
        @Select("SELECT id FROM biz.xx_photos WHERE wallhaven = #{wallhaven}")
	public String findByWallpaperId(String wallhaven);
        
}
