/**
 * 作者：刘时明
 * 时间：2021/1/25
 */
package com.lsm1998.excel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExcelDemo
{
    public static void main(String[] args) throws Exception
    {
        String[] titleRow = new String[]{"姓名", "年龄"};
        List<Student> contentList = new ArrayList<>();

        contentList.add(Student.of("lsm", 24));
        contentList.add(Student.of("boot", 20));
        contentList.add(Student.of("mask", 22));

        createExcel("demo.xlsx", "sheet", titleRow);
        writeToExcel("demo.xlsx", "sheet", contentList);
    }

    public static void createExcel(String fileDir, String sheetName, String[] titleRow) throws Exception
    {
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 添加Worksheet（不添加sheet时生成的xls文件打开时会报错)
        workbook.createSheet(sheetName);
        // 新建文件
        try (FileOutputStream out = new FileOutputStream(fileDir))
        {
            // 添加表头,创建第一行
            HSSFRow row = workbook.getSheet(sheetName).createRow(0);
            for (short i = 0; i < titleRow.length; i++)
            {
                HSSFCell cell = row.createCell(i);
                cell.setCellValue(titleRow[i]);
            }
            workbook.write(out);
        } catch (Exception e)
        {
            throw e;
        }
    }

    public static void writeToExcel(String fileDir, String sheetName, List<Student> contentList) throws Exception
    {
        //创建workbook
        File file = new File(fileDir);
        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(file));
        HSSFSheet sheet = workbook.getSheet(sheetName);
        // 获取表格的总行数
        // int rowCount = sheet.getLastRowNum() + 1; // 需要加一
        // 获取表头的列数
        int columnCount = sheet.getRow(0).getLastCellNum() + 1;
        try (FileOutputStream out = new FileOutputStream(fileDir))
        {
            // 获得表头行对象
            HSSFRow titleRow = sheet.getRow(0);
            if (titleRow != null)
            {
                for (int rowId = 0; rowId < contentList.size(); rowId++)
                {
                    Student student = contentList.get(rowId);
                    HSSFRow newRow = sheet.createRow(rowId + 1);
                    for (short columnIndex = 0; columnIndex < columnCount; columnIndex++)
                    {
                        // 遍历表头
                        String mapKey = titleRow.getCell(columnIndex).toString().trim().trim();
                        HSSFCell cell = newRow.createCell(columnIndex);
                        cell.setCellValue(map.get(mapKey) == null ? null : map.get(mapKey).toString());
                    }
                }
            }
            workbook.write(out);
        } catch (Exception e)
        {
            throw e;
        }
    }
}
