package com.poi;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

/***
 **@project: base
 **@description: poi common
 **@Author: twj
 **@Date: 2019/05/08
 **/
public class PoiCommon {

    public static void main(String[] args) {
//        try {
//            Field field = Model.class.getDeclaredField("intValue");
//            System.out.println(field.getGenericType().getTypeName());
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        }

        //importExcel();
        //useFunction();
        String regix = "([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8])))";
        String pattren = "[0-9][0-9][0-9][0-9](-|/)[0-1][0-9](-|/)[0-9][0-9]";

        System.out.println(Pattern.matches(regix, "2019-12-08"));
    }

    public static void importExcel() {
        File file = new File("poi.xls");
        try {
            FileInputStream is = new FileInputStream(file);
            HSSFWorkbook book = new HSSFWorkbook(is);
            HSSFSheet sheet = book.getSheet("sheet_1");
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                HSSFRow row = sheet.getRow(i);
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    HSSFCell cell = row.getCell(j);
                    CellType cellType = cell.getCellType();
                    if (cellType.compareTo(CellType.STRING) == 0) {
                        System.out.print("  Row: " + i + " , cell: " + j + " , value: " + cell.getStringCellValue());
                    }
                    if (cellType.compareTo(CellType.BOOLEAN) == 0) {
                        System.out.print("  Row: " + i + " , cell: " + j + " , value: " + cell.getBooleanCellValue());
                    }
                    if (cellType.compareTo(CellType.NUMERIC) == 0) {
                        if (cell.toString().contains("-") || cell.toString().contains("/")) {
                            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
                            String time = format.format(cell.getDateCellValue());
                            System.out.print("  Row: " + i + " , cell: " + j + " , value: " + time);
                        } else {
                            System.out.print("  Row: " + i + " , cell: " + j + " , value: " + cell.getNumericCellValue());
                        }

                    }
                    if (cellType.compareTo(CellType.FORMULA) == 0) {
                        System.out.print("  Row: " + i + " , cell: " + j + " , value: " + cell.getCellFormula());
                    }


                }
                System.out.println(" ");

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //生成excel文件
    public static void export() {

        FileOutputStream out = null;
        try {
            //1. 创建HSSFWorkbook对象
            HSSFWorkbook book = new HSSFWorkbook();
            //2. 创建单sheet
            HSSFSheet sheet = book.createSheet("sheet_1");
            //3，创建 页头
            HSSFHeader haeder = sheet.getHeader();
            haeder.setCenter("POI EXCEL HEAD");
            //4. 创建页尾
            HSSFFooter footer = sheet.getFooter();
            footer.setCenter("POI EXCEL FOOTER");
            //设值列宽度
            sheet.setColumnWidth(0, 4000);
            sheet.setColumnWidth(1, 6000);
            sheet.setColumnWidth(2, 10000);
            //添加行，设置样式
            HSSFCellStyle headerStyle = book.createCellStyle();
            HSSFCellStyle cellStyle = book.createCellStyle();

            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            //设置前景色
            headerStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.YELLOW.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            //style.setFillForegroundColor(HSSFColor.YELLOW.index2);
            //设置字体
            HSSFFont cellFont = book.createFont();
            cellFont.setBold(true);
            //cellFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
            cellFont.setFontName(HSSFFont.FONT_ARIAL);
            cellFont.setCharSet(HSSFFont.ANSI_CHARSET);
            cellStyle.setFont(cellFont);
            HSSFFont headerFont = book.createFont();
            headerFont.setBold(true);
            // headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            headerFont.setFontName(HSSFFont.FONT_ARIAL);
            headerFont.setCharSet(HSSFFont.ANSI_CHARSET);
            headerStyle.setFont(headerFont);

            //创建标题
            HSSFRow row = sheet.createRow(0);
            HSSFCell cell = row.createCell(0);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("测试1");

            cell = row.createCell(1);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("测试2");

            cell = row.createCell(2);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("测试3");
            row.setHeight((short) 400);
            HSSFRow rows = null;
            HSSFCell cells = null;
            //设置数据
            for (int i = 0; i < 10; i++) {
                rows = sheet.createRow(i + 1);
                cells = rows.createCell(0);
                cells.setCellStyle(cellStyle);
                cells.setCellValue(i);
                cells = rows.createCell(1);
                cells.setCellStyle(cellStyle);
                cells.setCellValue(i + 1);
                cells = rows.createCell(2);
                cells.setCellStyle(cellStyle);
                cells.setCellValue(i + 2);
            }
            rows = sheet.createRow(11);
            cells = rows.createCell(2);
            cells.setCellStyle(cellStyle);
            cells.setCellValue("SUM =");
            cells.setCellFormula("SUM(A2:C11)");
            out = new FileOutputStream(new File("poi.xls"));
            book.write(out);
            System.out.println("导出成功");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }

    /***
     * 使用poi调用excel函数
     * 和创建普通cell一样，会解析
     */
    public static void useFunction() {


            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet spreadsheet = workbook.createSheet("formula");
            HSSFRow row = spreadsheet.createRow(1);
            HSSFCell cell = row.createCell(1);
            cell.setCellValue("A =");
            cell = row.createCell(2);
            cell.setCellValue(2);
            row = spreadsheet.createRow(2);
            cell = row.createCell(1);
            cell.setCellValue("B =");
            cell = row.createCell(2);
            cell.setCellValue(4);
            row = spreadsheet.createRow(3);
            cell = row.createCell(1);
            cell.setCellValue("Total =");
            cell = row.createCell(2);
            // Create SUM formula
            cell.setCellType(CellType.FORMULA);
            cell.setCellFormula("SUM(C2:C3)");
            cell = row.createCell(3);
            cell.setCellValue("SUM(C2:C3)");
            row = spreadsheet.createRow(4);
            cell = row.createCell(1);
            cell.setCellValue("POWER =");
            cell = row.createCell(2);
            // Create POWER formula
            cell.setCellType(CellType.FORMULA);
            cell.setCellFormula("POWER(C2,C3)");
            cell = row.createCell(3);
            cell.setCellValue("POWER(C2,C3)");
            row = spreadsheet.createRow(5);
            cell = row.createCell(1);
            cell.setCellValue("MAX =");
            cell = row.createCell(2);
            // Create MAX formula
            cell.setCellType(CellType.FORMULA);
            cell.setCellFormula("MAX(C2,C3)");
            cell = row.createCell(3);
            cell.setCellValue("MAX(C2,C3)");
            row = spreadsheet.createRow(6);
            cell = row.createCell(1);
            cell.setCellValue("FACT =");
            cell = row.createCell(2);
            // Create FACT formula
            cell.setCellType(CellType.FORMULA);
            cell.setCellFormula("FACT(C3)");
            cell = row.createCell(3);
            cell.setCellValue("FACT(C3)");
            row = spreadsheet.createRow(7);
            cell = row.createCell(1);
            cell.setCellValue("SQRT =");
            cell = row.createCell(2);
            // Create SQRT formula
            cell.setCellType(CellType.FORMULA);
            cell.setCellFormula("SQRT(C5)");
            cell = row.createCell(3);
            cell.setCellValue("SQRT(C5)");
            workbook.getCreationHelper()
                    .createFormulaEvaluator();

            FileOutputStream out = null;
        try {
            out = new FileOutputStream(
                    new File("formula.xls"));
            workbook.write(out);

            System.out.println("fromula.xlsx written successfully");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
}
