package tests;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тестирование распковки zip архива")
public class ZipTests {
    private final ClassLoader cl = ZipTests.class.getClassLoader();

    @Test
    @DisplayName("Прочтение PDF файла из zip архива")
    void checkPdfFileFromZipFileTest() throws Exception {
        try (ZipInputStream zis = new ZipInputStream(cl.getResourceAsStream("arhive.zip"))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().equals("testpdf.pdf")) {
                    PDF pdf = new PDF(zis);
                    assertThat(pdf.text).contains("Тестовый PDF-документ");
                    break;
                }
            }
        }
    }

    @Test
    @DisplayName("Прочтение XLSX файла из zip архива")
    void checkXlsxFileFromZipTest() throws Exception {
        try (ZipInputStream zis = new ZipInputStream(cl.getResourceAsStream("arhive.zip"))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().equals("test.xlsx")) {
                    XLS xls = new XLS(zis);
                    String value = xls.excel.getSheetAt(0).getRow(45).getCell(1).getStringCellValue();
                    assertThat(value).contains("EGOR CHUBKOV");
                }
            }
        }
    }

    @Test
    @DisplayName("Прочтение CSV файла из zip архива")
    void checkCsvFileFromZipTest() throws Exception {
        try (ZipInputStream zis = new ZipInputStream(cl.getResourceAsStream("arhive.zip"))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().equals("test - cinema.csv")) {
                    CSVReader csvReader = new CSVReader(new InputStreamReader(zis));
                    List<String[]> rows = csvReader.readAll();
                    assertThat(rows).hasSize(5);
                }
            }
        }
    }
}
