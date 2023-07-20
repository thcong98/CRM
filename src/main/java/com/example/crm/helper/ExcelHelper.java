package com.example.crm.helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.example.crm.entity.Customer;

public class ExcelHelper {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERs = { "address", "birthday", "code", "email", "first_name", "gender", "last_name",
            "phone_number", "type_id" };
    static String SHEET = "customer";

    public static boolean checkExcelFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType()) ? true : false;
    }

    public static ByteArrayInputStream customersToExcel(List<Customer> customers) {

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            Sheet sheet = workbook.createSheet(SHEET);
            // Header
            Row headerRow = sheet.createRow(0);

            for (int col = 0; col < HEADERs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERs[col]);
            }

            int rowIdx = 1;
            for (Customer customer : customers) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(customer.getAddress());
                row.createCell(1).setCellValue(customer.getBirthday());
                row.createCell(2).setCellValue(customer.getCode());
                row.createCell(3).setCellValue(customer.getEmail());
                row.createCell(4).setCellValue(customer.getFirstName());
                row.createCell(5).setCellValue(customer.getGender());
                row.createCell(6).setCellValue(customer.getLastName());
                row.createCell(7).setCellValue(customer.getPhoneNumber());
                row.createCell(8).setCellValue(customer.getTypeId());
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
                        case 1:
                            c.setAddress(cell.getStringCellValue());
                            break;
                        case 2:
                            c.setBirthday(cell.getDateCellValue());
                            break;
                        case 3:
                            c.setCode((long) cell.getNumericCellValue());
                            break;
                        case 4:
                            c.setEmail(cell.getStringCellValue());
                            break;
                        case 5:
                            c.setFirstName(cell.getStringCellValue());
                            break;
                        case 6:
                            c.setGender(cell.getStringCellValue());
                            break;
                        case 7:
                            c.setLastName(cell.getStringCellValue());
                            break;
                        case 8:
                            c.setPhoneNumber(cell.getStringCellValue());
                            break;
                        case 9:
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
