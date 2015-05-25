/**
 * 
 */
package com.cc.hadoop.logic;

import java.io.File;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.cc.core.utils.FileUtils;
import com.cc.core.utils.StringUtils;
import com.cc.core.utils.WebUtils;
import com.thiscc.hdfs.interfaces.HdfsInterface;
import com.thiscc.hdfs.interfaces.impl.HdfsManager;
/**
 * @author HyNo
 * 
 */
@Component
public class FileUploadLogic {

	private static final Logger logger = Logger.getLogger(FileUploadLogic.class);
	private static final String PARAMETERS_UPLOAD_TYPE = "uploadType";
	private static final String UPLOAD_TEMP_DIR = "temp";
	
	private static final String[] fileTypes = new String[]{"gif","jpg","jpeg","bmp","png"};

	private String fileName = null;

	/**
	 * 文件上传
	 * 
	 * @param request
	 * @return 0表示操作异常，1表示上传失败，否则是文件路径
	 */
	public String uploadFile(HttpServletRequest request, String type) {
		// Check that we have a file upload request
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {
			// Create a factory for disk-based file items
			FileItemFactory factory = new DiskFileItemFactory(
					1024 * 1024 * 1024 * 10, com.cc.core.utils.FileUtils.createFolder(this
							.getRealPath(request, UPLOAD_TEMP_DIR)));

			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload(factory);

			// Set overall request size constraint
			upload.setSizeMax(1024 * 1024 * 1024);

			// Parse the request FileItem
			try {
				List<?> items = upload.parseRequest(request);
				// Process the uploaded items
				Iterator<?> iter = items.iterator();
				boolean flag = false;
				while (iter.hasNext()) {
					FileItem item = (FileItem) iter.next();
					System.out.println(item.getFieldName());
					System.out.println(item.isFormField());
					
					if (item.isFormField()
							&& StringUtils.equalsIgnoreCase(item.getFieldName(),
									PARAMETERS_UPLOAD_TYPE)) {
					} else if(!item.isFormField()&&!flag) {
						flag = true;
						if(FileUtils.checkFileType(item.getName(), fileTypes))
						{
							InputStream in = item.getInputStream();
							HdfsInterface hdfsInterface = new HdfsManager();
							
							this.fileName ="/cc/"+ System.currentTimeMillis()+FileUtils.FILE_FORMAT_CHAR+FileUtils.getFileFormat(item.getName());
							
							if(!hdfsInterface.upload(in, fileName)){
								return "1";
							}
						}
					}
				}
			} catch (Exception e) {
				logger.error(e.getLocalizedMessage(), e);
				return null;
			}
		}
		return this.fileName;
	}


	/**
	 * 获取文件绝对路径
	 * 
	 * @param request
	 * @param dir
	 * @return
	 */
	private String getRealPath(HttpServletRequest request, String dir) {
//		return request.getSession().getServletContext()
//				.getRealPath(File.separator)
//				+ dir;
		return WebUtils.getWebPath()+File.separator+dir;
//		return "C:\\space\\mySpace\\cowboy\\"+ dir;
	}

}
