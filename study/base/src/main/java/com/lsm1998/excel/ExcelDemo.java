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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelDemo
{
    public static void main(String[] args) throws Exception
    {
        List<Student> contentList = new ArrayList<>();

        contentList.add(Student.of("lsm", 24, "第一列"));
        contentList.add(Student.of("boot", 20, "第二列"));
        contentList.add(Student.of("mask", 22, "第三列"));

        String[] titleRow = new String[contentList.size()];
        for (int i = 0; i < titleRow.length; i++)
        {
            titleRow[i] = contentList.get(i).getTitle();
        }
        List<Map<String, Object>> mapList = studentConvertMap(contentList);
        createExcel("demo.xlsx", "sheet", titleRow);
        writeToExcel("demo.xlsx", "sheet", mapList);
    }

    /**
     * 行转列
     *
     * @param contentList
     * @return
     */
    private static List<Map<String, Object>> studentConvertMap(List<Student> contentList)
    {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (int i = 0; i < 2; i++)
        {
            Map<String, Object> tempMap = new HashMap<>();
            for (int j = 0; j < contentList.size(); j++)
            {
                Object value = i == 0 ? contentList.get(j).getName() : contentList.get(j).getAge();
                tempMap.put(contentList.get(j).getTitle(), value);
            }
            mapList.add(tempMap);
        }
        return mapList;
    }


    /**
     * 创建Excel文件
     *
     * @param fileDir
     * @param sheetName
     * @param titleRow
     * @throws Exception
     */
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

    /**
     * 写入内容
     *
     * @param fileDir
     * @param sheetName
     * @param contentList
     * @throws Exception
     */
    public static void writeToExcel(String fileDir, String sheetName, List<Map<String, Object>> contentList) throws Exception
    {
        //创建workbook
        File file = new File(fileDir);
        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(file));
        HSSFSheet sheet = workbook.getSheet(sheetName);
        // 获取表头的列数
        int columnCount = sheet.getRow(0).getLastCellNum();
        try (FileOutputStream out = new FileOutputStream(fileDir))
        {
            // 获得表头行对象
            HSSFRow titleRow = sheet.getRow(0);
            if (titleRow != null)
            {
                for (int rowId = 0; rowId < contentList.size(); rowId++)
                {
                    Map<String, Object> studentMap = contentList.get(rowId);
                    HSSFRow newRow = sheet.createRow(rowId + 1);
                    for (short columnIndex = 0; columnIndex < columnCount; columnIndex++)
                    {
                        // 遍历表头
                        String mapKey = titleRow.getCell(columnIndex).toString().trim();
                        HSSFCell cell = newRow.createCell(columnIndex);
                        cell.setCellValue(studentMap.get(mapKey) == null ? null : studentMap.get(mapKey).toString());
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
