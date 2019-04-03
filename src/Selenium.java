import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class Selenium {

    // Поиск данных по таблицам
    public static void parse() {

    }

    // Фиксация данных из таблиц
    public static void commit() {

    }

    // Главный метод
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "D:\\ProgrammFiles\\Selenium-java-3.141.59\\chromedriver.exe");

        // Все таблицы
        List<WebElement> tables;
        // Все строки таблиц
        List<WebElement> rows;
        // Ячейки первой строки
        List<WebElement> cells;

        // Доступ к сайту
        ChromeDriver chrome_driver = new ChromeDriver();
        //chrome_driver.get("http://demo.guru99.com/");
        chrome_driver.get("http://moneta-russia.ru/soyuz/");

        // Получение данных таблиц
        tables = chrome_driver.findElements(By.xpath("//table"));

        for (WebElement table : tables) {
            // Получение данны строк
            rows = table.findElements(By.tagName("tr"));

            for (WebElement row : rows) {
                // Получение данны ячеек
                cells = row.findElements(By.tagName("td"));

                // Вывод в консоль содержимого ячейки
                for (WebElement cell : cells) {
                    System.out.printf("%-7s", cell.getText());

                    // Выбор разделителя
                    if (cell.equals(cells.get(cells.size() - 1))) {
                        // Разделитель консольного вывода строк
                        System.out.println("");
                    } else {
                        // Разделитель консольного вывода ячеек
                        System.out.print(" ");
                    }
                }
            }

            // Разделитель консольного вывода таблиц
            System.out.println("");
        }

        chrome_driver.close();
    } // Конец main()
}
