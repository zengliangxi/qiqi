package com.aspire.dicmp.excel.model;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Pattern;

import javax.validation.constraints.NotBlank;

import cn.afterturn.easypoi.excel.annotation.Excel;

public class User {
	@Excel(name = "id",orderNum = "0")
	@NotBlank(message = "该字段不能为空")
	private String id;

	@Excel(name = "姓名",orderNum = "1")
	//@Pattern(regexp = "[\\u4E00-\\u9FA5]{2,5}", message = "姓名中文2-5位")
	private String name;

	@Max(value=20,message = "最大不能超过20")
	@Excel(name = "年龄",orderNum = "2")
	private Integer age;

	@Excel(name = "生日", importFormat = "yyyy-MM-dd",orderNum = "3",exportFormat = "yyyy-MM-dd")
	private Date birthday;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
}
