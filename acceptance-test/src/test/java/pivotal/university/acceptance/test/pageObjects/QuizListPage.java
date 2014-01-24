package pivotal.university.acceptance.test.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pivotal.university.acceptance.test.config.Settings;
import pivotal.university.acceptance.test.pageObjects.pageElements.Table;

import java.util.List;

public class QuizListPage extends Page{

    private static String pageUrl = Settings.applicationUrl + "/?q=admin/content";

    public QuizListPage(WebDriver webDriver) {
        super(pageUrl, webDriver);
    }

    private By finishQuizButton = By.id("edit-submit");
    private Table answers;

    public void selectAnswer(String correctAnswer) throws InterruptedException {
        int COL_ANSWER = 1;
        int COL_SELECT = 0;

        List<WebElement> tableList = webDriver.findElements(By.tagName("table"));
        for(WebElement table:tableList) {
            if(table.getText().contains(correctAnswer)) {
                answers = new Table(table);
                for(int i=0; i<answers.getRowCount();i++) {

                    if (answers.getCell(i, COL_ANSWER).getText().equals(correctAnswer)) {
                        answers.getInputCell(i, COL_SELECT).click();

                        webDriver.findElement(finishQuizButton).click();

                        break;
                    }
                }
            }

        }
    }

    public void goToQuiz001(){
        webDriver.get(Settings.applicationUrl + "/?q=node/36/take");
    }
}
