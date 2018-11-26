package com.aspire.dicmp.excel.handler;

import com.aspire.dicmp.excel.model.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.afterturn.easypoi.handler.impl.ExcelDataHandlerDefaultImpl;

public class UserExcelHandler extends ExcelDataHandlerDefaultImpl<User> {
	
	private static final Logger log = LoggerFactory.getLogger(UserExcelHandler.class);

	
	@Override
	public Object importHandler(User obj, String name, Object value) {
		log.info(name+":"+value);
		return super.importHandler(obj, name, value);
	}

}
