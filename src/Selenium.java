import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.io.IOException;
import java.util.*;

// Главнй класс
public class Selenium {
    // Сохранённые таблицы
    static HashMap<Integer, HashMap> allTables;
    // Количество таблиц
    static Integer counterTables;

    // Текущая таблица
    static HashMap<Integer, ArrayList> crntTable;
    // Текущая строка
    static ArrayList<String> crntRow;
    // Текущая ячейка
    static String crntCell;

    // Получить данные таблиц
    private static void getTables(String web_page) {
        System.setProperty("webdriver.chrome.driver", "D:\\ProgrammFiles\\Selenium-java-3.141.59\\chromedriver.exe");

        allTables = new HashMap<Integer, HashMap>();
        counterTables = 0;

        // Все таблицы
        List<WebElement> tables;
        // Все строки таблиц
        List<WebElement> rows;
        // Все Ячейки строки
        List<WebElement> cells;

        // Доступ к сайту
        ChromeDriver chrome_driver = new ChromeDriver();
        chrome_driver.get(web_page);

        /*
         *  Получение данных таблиц
         */
        // Найденные таблицы
        tables = chrome_driver.findElements(By.xpath("//table"));

        // Буффер найденных таблиц
        crntTable = new HashMap<Integer, ArrayList>();
        // Счётчик строк
        int crntTableRows = 0;

        // Проход по таблицам
        for (WebElement table : tables) {

            /*
             *  Получение данны строк
             */
            // Найденные строки
            rows = table.findElements(By.tagName("tr"));
            // Буффер найденных строк
            crntRow = new ArrayList<String>();
            // Счётчик ячеек
            int crntRowCells;

            // Проход по строкам
            for (WebElement row : rows) {

                /*
                 *  Получение данны ячеек
                 */
                // Найденные ячейки
                cells = row.findElements(By.tagName("td"));
                // Буффер найденной ячейки
                crntCell = "_*_";
                // Количество найденых ячеек
                crntRowCells = cells.size();

                // Проход по ячейкам
                for (WebElement cell : cells) {

                    /*
                     *  Сохранеие даннных ячейки
                     */
                    try {
                        crntCell = cell.getText();
                    } finally {
                        crntRow.add(crntCell);
                    }

                    /*
                     *  Вывод в консоль содержимого ячейки
                     */
                    //System.out.printf("%-7s", cell.getText());
                    System.out.printf("%-7s", crntCell);

                    // Выбор консольного разделителя
                    if (cell.equals(cells.get(crntRowCells - 1))) {
                        // Разделитель консольного вывода строк
                        System.out.println();
                    } else {
                        // Разделитель консольного вывода ячеек
                        System.out.print(" ");
                    }
                } // Конец прохода по ячейкам

                /*
                 * Сохранение данных строки
                 */
                try {
                    crntTable.put(crntTableRows++, crntRow);
                } catch (Exception e) {
                    // ПУСТО
                }
            } // Конец прохода по строкам

            /*
             *  Сохранение данных таблицы
             */
            try {
                allTables.put(counterTables++, crntTable);
            } catch (Exception e) {
                // ПУСТО
            }

            // Разделитель консольного вывода таблиц
            System.out.println();
        } // Конец прохода по таблицам

        /*
         *  Сохранить таблицы в файле
         */
        setTables("D:\\Projects__ALL\\Projects__Java\\IJI\\selenium\\selenium.xls");

        chrome_driver.close();
    } // Конец getTable

    // Сохранить таблицы в файл
    private static void setTables(String book_path) {
        // Пустая книга
        HSSFWorkbook xlsBook = new HSSFWorkbook();

        /*
         *  Заполнение книги
         */
        for(Integer keyTable : allTables.keySet()) {
            // Пустая страница
            HSSFSheet xlsSheet = xlsBook.createSheet("Parsed table " + keyTable);
            // Текущая таблица
            crntTable = allTables.get(keyTable);

            /*
             *  Заполнение страницы
             */
            for(Integer keyRow : crntTable.keySet()) {
                // Пустая строка
                HSSFRow xlsLine = xlsSheet.createRow(keyRow);
                // Текущая строка
                crntRow = crntTable.get(keyRow);

                int cntrCell = 0;

                /*
                 *  Заполнение строки
                 */
                for(String text : crntRow) {
                    xlsLine.createCell(cntrCell++).setCellValue(text);
                }
            }
        }

        /*
         *  Сохранение в файл
         */
        try {
            FileOutputStream outputFile = new FileOutputStream(new File(book_path));
            xlsBook.write(outputFile);
            outputFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    } // Конец setTable

    // Главный метод
    public static void main(String[] args) {
        // Получить данные со страницы
        try {
            getTables("http://moneta-russia.ru/soyuz/");
        } finally {
            System.out.println("Программа завершила работу");
        }
    }
}
