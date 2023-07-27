package com.example.crm.helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.example.crm.entity.Customer;

public class ExcelHelper {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERs = { "ADDRESS", "BIRTHDAY", "CODE", "EMAIL", "FIRST_NAME", "GENDER", "LAST_NAME",
            "PHONE_NUMBER", "TYPE_ID" };
    static String[] COLORs = { "LIGHT_BLUE", "LIGHT_CORNFLOWER_BLUE" };
    static String SHEET = "customer";

    public static boolean checkExcelFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType()) ? true : false;
    }

    public static short getRandomColorIndex() {
        int numIndexedColors = IndexedColors.values().length;
        Random random = new Random();
        int randomIndex = random.nextInt(numIndexedColors);

        return IndexedColors.values()[randomIndex].getIndex();
    }

    public static CellStyle HieuCellStyle(Workbook workbook, String type) {
        CellStyle styleHeader = workbook.createCellStyle();
        Font font = workbook.createFont();
        if (type.equals("headers")) {
            font.setFontName("Courier New");
            font.setBold(true);
            font.setColor(HSSFColorPredefined.WHITE.getIndex());
            styleHeader.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        } else if (type.equals("odd")) {
            font.setColor(HSSFColorPredefined.GREY_80_PERCENT.getIndex());
            styleHeader.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.getIndex());
        } else if (type.equals("even")) {
            font.setColor(HSSFColorPredefined.GREY_80_PERCENT.getIndex());
            styleHeader.setFillForegroundColor(IndexedColors.WHITE1.getIndex());
        } 
        //get random for fun
        // styleHeader.setFillForegroundColor(getRandomColorIndex()); 
        styleHeader.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styleHeader.setFont(font);

        return styleHeader;
    }

    public static ByteArrayInputStream customersToExcel(List<Customer> customers) {

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            Sheet sheet = workbook.createSheet(SHEET);

            // Header
            Row headerRow = sheet.createRow(0);

            for (int col = 0; col < HEADERs.length; col++) {
                CellStyle styleHeader = HieuCellStyle(workbook, "headers");

                Cell cell = headerRow.createCell(col);
                cell.setCellStyle(styleHeader);
                cell.setCellValue(HEADERs[col]);

            }

            int rowIdx = 1;
            for (Customer customer : customers) {
                Row row = sheet.createRow(rowIdx++);

                CellStyle style = HieuCellStyle(workbook, "even");
                CellStyle Datestyle = HieuCellStyle(workbook, "even");
                if (rowIdx%2 == 1){
                    style = HieuCellStyle(workbook, "odd");
                    Datestyle = HieuCellStyle(workbook, "odd");
                }

                Cell cell0 = row.createCell(0);
                cell0.setCellStyle(style);
                cell0.setCellValue(customer.getAddress());

                Cell cell1 = row.createCell(1);
                // fix date format errol
                Datestyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("dd-MMM-yyyy")); 
                //continue
                cell1.setCellStyle(Datestyle);
                cell1.setCellValue(customer.getBirthday());

                Cell cell2 = row.createCell(2);
                cell2.setCellStyle(style);
                cell2.setCellValue((Long)customer.getCode());

                Cell cell3 = row.createCell(3);
                cell3.setCellStyle(style);
                cell3.setCellValue(customer.getEmail());

                Cell cell4 = row.createCell(4);
                cell4.setCellStyle(style);
                cell4.setCellValue(customer.getFirstName());

                Cell cell5 = row.createCell(5);
                cell5.setCellStyle(style);
                cell5.setCellValue(customer.getGender());

                Cell cell6 = row.createCell(6);
                cell6.setCellStyle(style);
                cell6.setCellValue(customer.getLastName());

                Cell cell7 = row.createCell(7);
                cell7.setCellStyle(style);
                cell7.setCellValue(String.join(",", customer.getPhoneNumber()));

                Cell cell8 = row.createCell(8);
                cell8.setCellStyle(style);
                cell8.setCellValue(customer.getTypeId());

            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }

    // convert excel to list customer
    public static List<Customer> excelToCustomer(InputStream is) throws IOException {
        try {
            List<Customer> customers = new ArrayList<Customer>();

            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheet(SHEET);
            Iterator<Row> rows = sheet.iterator();
            int rowNumber = 0;

            while (rows.hasNext()) {
                Row currentRow = rows.next();

                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                Customer c = new Customer();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell cell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0:
                            c.setAddress(cell.getStringCellValue());
                            break;
                        case 1:
                            c.setBirthday(cell.getDateCellValue());
                            break;
                        case 2:
                            c.setCode((long) cell.getNumericCellValue());
                            break;
                        case 3:
                            c.setEmail(cell.getStringCellValue());
                            break;
                        case 4:
                            c.setFirstName(cell.getStringCellValue());
                            break;
                        case 5:
                            c.setGender(cell.getStringCellValue());
                            break;
                        case 6:
                            c.setLastName(cell.getStringCellValue());
                            break;
                        case 7:
                            c.setPhoneNumber(Arrays.asList(cell.getStringCellValue().split(",")));
                            break;
                        case 8:
                            c.setTypeId(cell.getStringCellValue());
                            break;
                        default:
                            break;
                    }
                    cellIdx++;
                }
                customers.add(c);
            }
            workbook.close();
            return customers;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }
}
