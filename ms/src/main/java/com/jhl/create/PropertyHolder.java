package com.jhl.create;

/**
 * Created by Administrator on 2016/1/13.
 */
public class PropertyHolder {

    String properyName;
    String dataType;
    String properyDesr;
    String isEdit;
    String defaultVal;

    public PropertyHolder(String properyName, String dataType, String properyDesr, String isEdit, String defaultVal) {
        this.properyName = properyName;
        this.dataType = dataType;
        this.properyDesr = properyDesr;
        this.isEdit = isEdit;
        this.defaultVal = defaultVal;
    }

    public PropertyHolder(String properyName, String dataType, String properyDesr) {
        this.properyName = properyName;
        this.dataType = dataType;
        this.properyDesr = properyDesr;
        this.isEdit = "否";
        this.defaultVal = "否";
    }
}
