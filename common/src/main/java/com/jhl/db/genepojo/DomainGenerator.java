/**
 * Copyright  2013-7-18 第七大道-技术支持部-网站开发组
 * 自主运营平台WEB 下午4:45:06
 * 版本号： v1.0
 */

package com.jhl.db.genepojo;


import com.jhl.db.SQLUtils;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * <li>类描述：生成实体，在需要时进行配置纳入Spring管理</li> <li>创建者： amos.zhou</li> <li>项目名称： 7road-common</li> <li>创建时间：
 * 2013-7-18 下午4:45:06</li> <li>版本号： v1.0</li>
 */
@Component
public class DomainGenerator {

	/**
	 * 字段描述：
	 */

	private static final String DATA_TYPE = "DATA_TYPE";

	/**
	 * 字段描述：
	 */

	private static final String COLUMN_NAME = "COLUMN_NAME";

	private Logger log = Logger.getLogger(DomainGenerator.class);

	public static final String SHOW_ALL_TABLE = " show";

	public static final String SCHEMA_TYPE_TABLE = "TABLE";

	public static final String TABLE_NAME = "TABLE_NAME";

	public static final String REMARKS = "REMARKS";

	public static final String DEFAULT_DOMAIN_TEMPALTE_PATH = "classpath:domainTemplate.ftl";

	@Autowired
	private JdbcTemplate template;

	/**
	 * 
	 * <p>
	 * 创建时间： 2013-7-19 下午12:24:25
	 * </p>
	 * <p>
	 * 获取所有的表名
	 * </p>
	 * 
	 * @return
	 * @throws java.sql.SQLException
	 */
	private List<Table> getTables(String dbName) throws SQLException {
		List<Table> tables = new ArrayList<Table>();
		Connection conn = getConnection();
		DatabaseMetaData dbmd = conn.getMetaData();
		ResultSet rest = dbmd.getTables(dbName, null, null,
				new String[] { SCHEMA_TYPE_TABLE });
		while (rest.next()) {
			Table tb = new Table();
			tb.setTableName(rest.getString(TABLE_NAME));
			tb.setTableRemark(rest.getString(REMARKS));
			tables.add(tb);
		}
		conn.close();
		return tables;
	}


	/**
	 *
	 * <p>
	 * 创建时间： 2013-7-19 下午12:59:03
	 * </p>
	 * <p>
	 * 获取给定表名的FiledHolder，目前只支持Mysql
	 * </p>
	 *
	 * @param tableName
	 * @return
	 * @throws java.sql.SQLException
	 */
	private FieldHolder holdFieldFromTable(DatabaseMetaData dbmd,
			String tableName) throws SQLException {
		ResultSet columnSet = dbmd.getColumns(null, "%", tableName.toUpperCase(), "%");
		List<Column> columnList = new ArrayList<Column>();
		while (columnSet.next()) {
			Column c = new Column();
			c.setColumnName(columnSet.getString(COLUMN_NAME));
			c.setColumnType(columnSet.getInt(DATA_TYPE));
			c.setRemark(columnSet.getString(REMARKS));
			columnList.add(c);
		}
		return new FieldHolder(columnList);
	}

	/**
	 *
	 * <p>
	 * 创建时间： 2013-7-19 下午1:08:03
	 * </p>
	 * <p>
	 * 准备数据
	 * </p>
	 *
	 * @param tables
	 * @return
	 * @throws java.sql.SQLException
	 */
	private Map<Table, FieldHolder> resolveTables(List<Table> tables) throws SQLException {
		Map<Table, FieldHolder> map = new HashMap<Table, FieldHolder>();
		Connection conn = getConnection();
		DatabaseMetaData dbmd = conn.getMetaData();
		for (Table table : tables) {
			map.put(table, holdFieldFromTable(dbmd,table.getTableName()));
		}
		conn.close();
		return map;
	}

	public void generate(String dbName, String destPackage) {
		List<Table> tables = null;
		try {
			tables = getTables(dbName);
		} catch (SQLException e) {
			log.error("获取数据库表失败，请检查相关配置", e);
			e.printStackTrace();
		}
//		Assert.notNull(tables, dbName + "库中没有任何表");
		try {
			Map<Table, FieldHolder> map = resolveTables(tables);
			generateJavaFile(map, destPackage);
		} catch (IOException e) {
			log.error("生成java文件文件失败", e);
			e.printStackTrace();
		} catch (Exception e) {
			log.error("生成java文件文件失败", e);
			e.printStackTrace();
		}
		log.info("生成对象完成!");
		System.out.println("生成对象完成!");

	}

	/**
	 *
	 * <p>
	 * 创建时间： 2013-7-19 下午2:32:03
	 * </p>
	 * <p>
	 * 生成java文件
	 * </p>
	 *
	 * @param map
	 * @throws java.io.IOException
	 * @throws freemarker.template.TemplateException
	 */
	private void generateJavaFile(Map<Table, FieldHolder> map,
			String destPackage) throws IOException, TemplateException {
		Template ftl = loadTemplate();
		String javaSrcPath = System.getProperty("user.dir") + "/src/main/java";
		File fileDir = new File(javaSrcPath + "/" + packageToPath(destPackage));
		// 创建文件夹，不存在则创建
		FileUtils.forceMkdir(fileDir);

		Set<Table> tables = map.keySet();
		Iterator<Table> it = tables.iterator();
		while (it.hasNext()) {
			Table table = it.next();
			String tableName = table.getTableName();
			Map<String, Object> data = prepareData(table,
					map.get(table), destPackage);
			// 指定生成输出的文件
			File output = new File(fileDir + "/" + getClassName(tableName)
					+ ".java");
			Writer writer = new FileWriter(output);
			ftl.process(data, writer);
			writer.close();
		}
	}

	public String packageToPath(String packageName) {
		return StringUtils.replaceChars(packageName, ".", "/");
	}

	/**
	 *
	 * <p>
	 * 创建时间： 2013-7-19 下午3:15:41
	 * </p>
	 * <p>
	 * 获取要生成的文件名
	 * </p>
	 *
	 * @param tableName
	 * @return
	 */
	private String getClassName(String tableName) {
		if (!StringUtils.isBlank(SQLUtils.tablePrefix)) {
			tableName = tableName.substring(SQLUtils.tablePrefix.length());
		}
		String className = JdbcUtils
				.convertUnderscoreNameToPropertyName(tableName);
		return className.substring(0, 1).toUpperCase() + className.substring(1);
	}

	public Map<String, Object> prepareData(Table table,
			FieldHolder holder, String packageName) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("tableRemark", table.getTableRemark());
		root.put("createDate", new Date());
		root.put("package", packageName);
		root.put("class", getClassName(table.getTableName()));
		root.put("properties", holder.getFieldList());
		root.put("importTypes", holder.getImportType());
		return root;
	}

	/**
	 *
	 * <p>
	 * 创建时间： 2013-7-19 下午1:22:27
	 * </p>
	 * <p>
	 * 加载模板
	 * </p>
	 *
	 * @return
	 * @throws java.io.FileNotFoundException
	 * @throws java.io.IOException
	 */
	private Template loadTemplate() throws FileNotFoundException, IOException {
		Configuration cfg = new Configuration();
		cfg.setObjectWrapper(new DefaultObjectWrapper());
		cfg.setDefaultEncoding("UTF-8");
		File template = ResourceUtils.getFile(DEFAULT_DOMAIN_TEMPALTE_PATH);
		cfg.setDirectoryForTemplateLoading(template.getParentFile());
        //格式化数字
        cfg.setNumberFormat("#");
		Template ftl = cfg.getTemplate("DomainTemplate.ftl");
		return ftl;
	}

	/**
	 * 
	 * <p>
	 * 创建时间： 2013-7-19 下午12:27:13
	 * </p>
	 * <p>
	 * 获取连接
	 * </p>
	 * 
	 * @return
	 */
	public Connection getConnection() {
		try {
			return template.getDataSource().getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
