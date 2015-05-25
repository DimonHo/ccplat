package com.cc.core.utils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 生成excel文件
 * @author coco
 * @createTime 2014.08.30
 */
public class ExportExcelUtil {

    private HttpServletResponse response = null;
    public static Log log = LogFactory.getLog(ExportExcelUtil.class);

    public ExportExcelUtil(HttpServletResponse response) {

        this.response = response;
    }

    /**
     * @param jsonArray jsonArray 结果json对象
     * @param xlsName  excel文件名
     * @param sheetName  excel表名
     * @param list  如果存在则将此设置为列名
     * @param list1  排列顺序（与sql语句中的列名字段一致）
     */
    @SuppressWarnings( { "deprecation", "unchecked" })
    public void jsonToExcel(JSONArray jsonArray, String xlsName, String sheetName, List<String> list, List<String> list1) {

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, sheetName);
        HSSFRow row = sheet.createRow((short) 0);
        HSSFCell cell;
        JSONObject jsonObject = null;
        Iterator keyIter = null;
        jsonObject = JSONObject.fromObject(jsonArray.get(0));
        keyIter = jsonObject.keys();

        try {
            //写入各个字段的名称
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    cell = row.createCell((short) (i));
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    cell.setCellValue(list.get(i));
                }
            } else {
                int i = 0;
                while (keyIter.hasNext()) {
                    cell = row.createCell((short) (i));
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    cell.setCellValue(keyIter.next().toString());
                    i++;
                }

            }

            int iRow = 1;
            String str = "";
            String colName = "";
            for (int i = 0; i < jsonArray.size(); i++) {
                row = sheet.createRow((short) iRow);
                jsonObject = JSONObject.fromObject(jsonArray.get(i));
                for (int j = 0; j < list1.size(); j++) {
                    keyIter = jsonObject.keys();
                    while (keyIter.hasNext()) {
                        colName = keyIter.next().toString();
                        if (list1.get(j).equals(colName)) { //排序
                            cell = row.createCell((short) (j));
                            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                            if (jsonObject.get(colName) != null) {
                                str = jsonObject.get(colName).toString();
                            }
                            cell.setCellValue(str);
                        }
                    }
                }
                iRow++;
            }

            //弹出下载框
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + xlsName + "");

            workbook.write(response.getOutputStream());
            response.flushBuffer(); //刷新流操作

        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }

    /**
     * @param jsonArray jsonArray 结果json对象
     * @param xlsName  excel文件名
     * @param sheetName  excel表名
     * @param list  如果存在则将此设置为列名
     * @param list1  排列顺序（与sql语句中的列名字段一致）
     */
    @SuppressWarnings( { "deprecation", "unchecked" })
    public void jsonToExcel(JSONArray jsonArray, String xlsName, String sheetName, String[] strArray, String[] strArray1) {

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, sheetName);
        HSSFRow row = sheet.createRow((short) 0);
        HSSFCell cell;
        JSONObject jsonObject = null;
        Iterator keyIter = null;
        jsonObject = JSONObject.fromObject(jsonArray.get(0));
        keyIter = jsonObject.keys();

        try {
            //写入各个字段的名称
            if (strArray != null && strArray.length > 0) {
                for (int i = 0; i < strArray.length; i++) {
                    cell = row.createCell((short) (i));
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    cell.setCellValue(strArray[i]);
                }
            } else {
                int i = 0;
                while (keyIter.hasNext()) {
                    cell = row.createCell((short) (i));
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    cell.setCellValue(keyIter.next().toString());
                    i++;
                }

            }

            int iRow = 1;
            String str = "";
            String colName = "";
            for (int i = 0; i < jsonArray.size(); i++) {
                row = sheet.createRow((short) iRow);
                jsonObject = JSONObject.fromObject(jsonArray.get(i));
                for (int j = 0; j < strArray1.length; j++) {
                    keyIter = jsonObject.keys();
                    while (keyIter.hasNext()) {
                        colName = keyIter.next().toString();
                        if (strArray1[j].equals(colName)) { //排序
                            cell = row.createCell((short) (j));
                            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                            if (jsonObject.get(colName) != null) {
                                str = jsonObject.get(colName).toString();
                            }
                            cell.setCellValue(str);
                        }
                    }
                }
                iRow++;
            }

            //弹出下载框
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + xlsName + "");

            workbook.write(response.getOutputStream());
            response.flushBuffer(); //刷新流操作

        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }

    /**
    * @param rs resultset 查询结果对象
    * @param xlsName  excel文件名
    * @param sheetName  excel表名
    * @param list  如果存在则将此设置为列名
    * @param list1  排列顺序
    */

    @SuppressWarnings("deprecation")
    public void resultSetToExcel(ResultSet rs, String xlsName, String sheetName, List<String> list, List<String> list1) {

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, sheetName);
        HSSFRow row = sheet.createRow((short) 0);
        HSSFCell cell;

        try {
            ResultSetMetaData md = rs.getMetaData();
            int nColumn = md.getColumnCount();
            //写入各个字段的名称
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    cell = row.createCell((short) (i));
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    cell.setCellValue(list.get(i));
                }
            } else {
                for (int i = 1; i <= nColumn; i++) {
                    cell = row.createCell((short) (i - 1));
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    cell.setCellValue(md.getColumnLabel(i));
                }
            }

            int iRow = 1;
            //写入各条记录，每条记录对应Excel中的一行
            String str = "";
            while (rs.next()) {
                row = sheet.createRow((short) iRow);
                for (int i = 0; i < list1.size(); i++) {
                    for (int j = 1; j <= nColumn; j++) {
                        if (list1.get(i).equals(md.getColumnLabel(j))) { //排序
                            cell = row.createCell((short) (j - 1));
                            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                            if (rs.getObject(j) != null) {
                                str = rs.getObject(j).toString();
                            }
                            cell.setCellValue(str);
                        }
                        iRow++;
                    }
                }
            }

            //弹出下载框
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + xlsName + "");

            workbook.write(response.getOutputStream());
            response.flushBuffer(); //刷新流操作

        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }

    /**
     * @param jsonArray jsonArray 结果json对象
     * @param xlsName  excel文件名
     * @param sheetName  excel表名
     */
    @SuppressWarnings( { "deprecation", "unchecked" })
    public void jsonToExcel(JSONArray jsonArray, String xlsName, String sheetName) {

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, sheetName);
        HSSFRow row = sheet.createRow((short) 0);
        HSSFCell cell;
        JSONObject jsonObject = null;
        Iterator keyIter = null;
        jsonObject = JSONObject.fromObject(jsonArray.get(0));
        keyIter = jsonObject.keys();

        try {
            int k = 0;
            while (keyIter.hasNext()) {
                cell = row.createCell((short) (k));
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell.setCellValue(keyIter.next().toString());
                k++;
            }

            int iRow = 1;
            String str = "";
            String colName = "";
            for (int i = 0; i < jsonArray.size(); i++) {
                row = sheet.createRow((short) iRow);
                jsonObject = JSONObject.fromObject(jsonArray.get(i));
                keyIter = jsonObject.keys();
                int j = 0;
                while (keyIter.hasNext()) {
                    colName = keyIter.next().toString();
                    cell = row.createCell((short) (j));
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    if (jsonObject.get(colName) != null) {
                        str = jsonObject.get(colName).toString();
                    }
                    cell.setCellValue(str);
                    j++;
                }
                iRow++;
            }

            //弹出下载框
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + xlsName + "");

            workbook.write(response.getOutputStream());
            response.flushBuffer(); //刷新流操作

        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }

}
