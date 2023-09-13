package com.lt.win.utils;

import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.sl.usermodel.Resources;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import static com.alibaba.fastjson.JSON.toJSONString;

/**
 * @author suki
 */
public class readExcelUtils {
    @SneakyThrows
    public static  List<String> readExcel(String sourceFilePath) {
        Workbook workbook = null;
        List<String> contents = Lists.newArrayList();
        List<String> list = Lists.newArrayList();
        try {
            workbook = getReadWorkBookType(sourceFilePath);

            //获取第一个sheet
            Sheet sheet = workbook.getSheetAt(1);
            //第0行是表名，忽略，从第二行开始读取
            for (int rowNum = 10; rowNum < sheet.getLastRowNum(); rowNum++) {
                Row row = sheet.getRow(rowNum);
                for (int cellNum=0;cellNum<7;cellNum++){
                    Cell cell = row.getCell(cellNum);
                    contents.add(getCellStringVal(cell).trim());
                }
                list.add(rowNum-10, toJSONString(contents));
                contents.clear();
            }
        } finally {
            IOUtils.closeQuietly(workbook);
        }
        return list;
    }

    private static Workbook getReadWorkBookType(String filePath) throws IOException {
        FileInputStream is = null;
        try {
            is = new FileInputStream(filePath);
            if (filePath.toLowerCase().endsWith("xlsx")) {
                return new XSSFWorkbook(is);
            } else if (filePath.toLowerCase().endsWith("xls")) {
                return new HSSFWorkbook(is);
            } else {
                //  抛出自定义的业务异常
            }
        } catch (IOException e) {
            //  抛出自定义的业务异常
            throw e;
        } finally {
            IOUtils.closeQuietly(is);
        }
        return null;
    }

    private static String getCellStringVal(Cell cell) {
        CellType cellType = cell.getCellTypeEnum();
        switch (cellType) {
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case STRING:
                return cell.getStringCellValue();
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return "";
            case ERROR:
                return String.valueOf(cell.getErrorCellValue());
            default:
                return StringUtils.EMPTY;
        }
    }

    public static void main(String[] args) {
        URL resource = Resources.class.getClassLoader().getResource("MG/MGMasterGamesList.xlsx");
        String sourceFilePath = resource.getPath();
        List<String> strings = readExcel(sourceFilePath);
        System.out.println(strings);
    }

}