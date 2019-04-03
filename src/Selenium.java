import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class Selenium {

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
        chrome_driver.get("http://demo.guru99.com/");

        // Данные таблиц
        /*
            rows = chrome_driver.findElements(By.xpath("//table//tr"));
            cells = rows.get(1).findElements(By.tagName("td"));
        */

        // Получение данных таблиц
        tables = chrome_driver.findElements(By.xpath("//table"));
        //tables = chrome_driver.findElements(By.tagName("table"));

        for (WebElement table : tables) {
            // Получение данны строк
            rows = table.findElements(By.tagName("tr"));

            for (WebElement row : rows) {
                // Получение данны ячеек
                cells = row.findElements(By.tagName("td"));

                // Вывод в консоль содержимого ячейки
                for (WebElement cell : cells) {
                    System.out.print(cell.getText());

                    /*
                    TODO
                        Проверка на последнюю ячейку в строке
                        Добавить пробел или нет?
                     */
                    //if (cell.equals(cells.get(cells.last))) { }
                }

                System.out.println("");
            }

            System.out.println("");
        }

    } // Конец main()
}
