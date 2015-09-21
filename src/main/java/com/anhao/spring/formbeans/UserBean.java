package com.anhao.spring.formbeans;

import java.io.Serializable;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;


public class UserBean implements Serializable{

	private static final long serialVersionUID = 4483174362028796828L;

	@Size(min=2, max=30) 
	private String name;
	
	@NotEmpty @Email
	private String email;
	
	@Digits(fraction = 0, integer = 3) //限制12位int数字,小数部分为0位.
	private Integer age;
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "UserBean [name=" + name + ", email=" + email + ", age=" + age
				+ "]";
	}
	
	
}
