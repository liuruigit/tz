package com.jhl.pojo;

import com.jhl.annotation.ForbidUpdate;
import com.jhl.annotation.PrimaryKey;

/**
 * Created by vic wu on 2015/8/30.
 */
public class Adver {
    @PrimaryKey
    private int id;
    private String url;
    private String desr;
    private String name;
    private String position;
    private String order;
    @ForbidUpdate
    private Integer deleteState = 1;
}
