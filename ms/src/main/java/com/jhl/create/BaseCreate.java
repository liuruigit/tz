package com.jhl.create;

import com.jhl.util.*;

import java.util.*;

/**
 * Created by Administrator on 2016/1/13.
 * 项目路径切换时，需修改：projPath
 */
public abstract class BaseCreate {

    protected static String DB_TYPE_TINY_INT = "tinyint(4)";
    protected static String DB_TYPE_DOUBLE = "double";
    protected static String DB_TYPE_VARCHAR = "varchar(20)";
    protected static String DB_TYPE_DATETIME = "datetime";

    //表名前缀
    protected String tablePrefix = "t_";
    //项目输出路径

    protected  String storePath = "E:\\WorkSpace\\gitServer\\server\\";
    protected  String codeMsBase = "harry\\ms\\";
    protected  String fullCodeBase = storePath + codeMsBase;
    protected String entityPath =  storePath + "harry\\common\\src\\main\\java\\com\\jhl\\pojo\\biz\\";
    protected String codePath =  fullCodeBase + "src\\main\\java\\com\\jhl\\";
    protected String mapperPath = fullCodeBase + "src\\main\\resources\\mybatis\\web\\";
    protected String sqlPath = fullCodeBase + "sql\\biz\\";
    protected String jspPath = fullCodeBase + "src\\main\\webapp\\WEB-INF\\jsp\\";
    //属性Map
    protected List<PropertyHolder> propertyList = new ArrayList<PropertyHolder>();
    //Java数据类型映射Map
    protected static Map<String,String> transDataTypeMap = new HashMap<String, String>();

    public List<PropertyHolder> getPropertyList() {
        return propertyList;
    }

    /**
     * 设置属性对象
     */
    protected abstract void setPropertyList() ;
    protected abstract String getClassName() ;

    static {
        transDataTypeMap.put(DB_TYPE_TINY_INT,"Integer");
        transDataTypeMap.put(DB_TYPE_DOUBLE,"Double");
        transDataTypeMap.put(DB_TYPE_VARCHAR,"String");
        transDataTypeMap.put(DB_TYPE_DATETIME,"Date");
    }

    protected void createCode(){

        setPropertyList(); //子类设置属性值

        /* ============================================================================================= */
        String packageName = StringUtil.firstToLowerCase(getClassName());  			//包名				========1
        String objectName = getClassName();	   			//类名				========2
        String tabletop = tablePrefix;	   				//表前缀				========3
        tabletop = null == tabletop?"":tabletop.toUpperCase();		//表前缀转大写

        List<String[]> fieldList = new ArrayList<String[]>();   	//属性集合			========4
        for(PropertyHolder propertyHolder : getPropertyList()){
            fieldList.add(new String[]{
                    propertyHolder.properyName,                                 //0 属性名称
                    propertyHolder.dataType,                                    //1 数据库数据类型
                    propertyHolder.properyDesr,                                 //2 描述
                    propertyHolder.isEdit,                                      //3 是否在页面编辑
                    propertyHolder.defaultVal,                                  //4 默认值
                    transDataTypeMap.get(propertyHolder.dataType),              //5 Java数据类型
                    StringUtil.firstToUpperCase(propertyHolder.properyName),    //6 属性首字符大写
                    SQLUtils.convertStrToDBFormat(propertyHolder.properyName)   //7 转换为aa_bb_cc
            });
        }                                                           //属性放到集合里面

        Map<String,Object> root = new HashMap<String,Object>();		//创建数据模型
        root.put("fieldList", fieldList);
        root.put("packageName", packageName);						//包名
        root.put("objectName", objectName);							//类名
        root.put("objectNameLower", objectName.toLowerCase());		//类名(全小写)
        root.put("objectNameUpper", objectName.toUpperCase());		//类名(全大写)
        root.put("tabletop", tabletop);								//表前缀
        root.put("nowDate", new Date());							//当前日期

        DelAllFile.delFolder(PathUtil.getClasspath() + "admin/ftl"); //生成代码前,先清空之前生成的代码
        /* ============================================================================================= */

        String filePath = "admin/ftl/code/";						//存放路径
        String ftlPath = "createCode";								//ftl路径


        try {
            /*生成entity*/
            Freemarker.printFile("entity.ftl", root, entityPath + objectName + ".java", ftlPath);

            /*生成controller*/
            Freemarker.printFile("controllerTemplate.ftl", root, codePath+"controller/biz/"+objectName+"Controller.java", ftlPath);

            /*生成service*/
            Freemarker.printFile("serviceTemplate.ftl", root, codePath+"service/biz/"+objectName+"Service.java", ftlPath);

            /*生成mybatis xml*/
            Freemarker.printFile("mapperMysqlTemplate.ftl", root, mapperPath+objectName+"Mapper.xml", ftlPath);
//            Freemarker.printFile("mapperOracleTemplate.ftl", root, mapperPath+objectName+"Mapper.xml", ftlPath);

            /*生成SQL脚本*/
            Freemarker.printFile("mysql_SQL_Template.ftl", root, sqlPath+tabletop+objectName.toUpperCase()+".sql", ftlPath);
//            Freemarker.printFile("oracle_SQL_Template.ftl", root, sqlPath+tabletop+objectName.toUpperCase()+".sql", ftlPath);

            /*生成jsp页面*/
            Freemarker.printFile("jsp_list_Template.ftl", root, jspPath+objectName.toLowerCase()+"/"+objectName.toLowerCase()+"_list.jsp", ftlPath);
            Freemarker.printFile("jsp_edit_Template.ftl", root, jspPath+objectName.toLowerCase()+"/"+objectName.toLowerCase()+"_edit.jsp", ftlPath);

//            /*生成说明文档*/
//            Freemarker.printFile("docTemplate.ftl", root, "desc.doc", ftlPath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
