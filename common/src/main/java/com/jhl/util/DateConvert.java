package com.jhl.util;

import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.beans.propertyeditors.CustomDateEditor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: xin.fang
 * Date: 14-3-10
 * Time: 下午5:07
 * .
 */
public class DateConvert implements PropertyEditorRegistrar {
	@Override
	public void registerCustomEditors(PropertyEditorRegistry registry) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		registry.registerCustomEditor(Date.class, new CustomDateEditor(df, false));
	}
}
