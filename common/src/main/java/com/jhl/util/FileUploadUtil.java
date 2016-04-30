package com.jhl.util;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.Assert;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 上传文件tool
 */
public class FileUploadUtil {

	private static List<String> allowImageTypeList;

	private static  String TYPE_LIMIT_MSSAGE = "上传图片格式为gif,jpg,jpeg,png,bmp";

	private static String MAX_SIZE_LIMIT_MASSAGE = "上传图片最大不超过maxSizeKB";

	static{
		allowImageTypeList = new ArrayList<String>();
		allowImageTypeList.add(".jpg");
		allowImageTypeList.add(".bmp");
		allowImageTypeList.add(".jpeg");
		allowImageTypeList.add(".gif");
		allowImageTypeList.add(".png");
		allowImageTypeList.add(".ico");
		allowImageTypeList.add(".JPG");
		allowImageTypeList.add(".BMP");
		allowImageTypeList.add(".JPEG");
		allowImageTypeList.add(".GIF");
		allowImageTypeList.add(".PNG");
		allowImageTypeList.add(".ICO");
	}

	/**
	 * 上传图片的限制
	 * @param maxSize 图片最大大小
	 * @param fileSize 上传的图片大小
	 * @param extendName 扩展名（带有.）
	 * @return
	 */
	public static String  uploadImageLimit(long maxSize, long fileSize, String extendName){
		if(maxSize < fileSize){
			return MAX_SIZE_LIMIT_MASSAGE.replace("maxSize", String.valueOf(maxSize/1024));
		}
		if(!allowImageTypeList.contains(extendName.toLowerCase())){
			return TYPE_LIMIT_MSSAGE;
		}
		return  "success";
	}

	/**
	 * 保存上传文件
	 * @param uploadFile 上传的文件
	 * @param dir 保存目录
	 * @return
	 * @throws java.io.IOException
	 */
	public static String saveUploadFile(MultipartFile uploadFile, String dir) throws IOException {
		Assert.isNotNull(uploadFile, "上传文件不能为空");

		String name = uploadFile.getOriginalFilename();
		String newName = null;
		if (StringUtils.isNotBlank(name)) {
			String extendName = name.substring(name.lastIndexOf("."));
			newName = MD5Util.bytesToHex(String.valueOf(new Date().getTime()).getBytes()) + extendName;
			File file = new File(dir + "/" + newName);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			FileCopyUtils.copy(uploadFile.getBytes(), file);
		}

		return newName;
	}

	/**
	 * 查询文件是否存在
	 * @param tempateFile 文件路径
	 * @return
	 */
	public static boolean fileExists(String tempateFile) {
		if (StringUtils.isBlank(tempateFile)) {
			return false;
		}
		File file = new File(tempateFile);
		if (file.exists()) {
			return true;
		}
		return false;
	}
}
