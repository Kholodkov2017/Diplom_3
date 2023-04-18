package pageobejct;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;
import java.util.List;
import java.util.Map;

import static helpers.Constants.DEFAULT_WAITING;
import static helpers.Constants.INGREDIENT_TYPES_MAP;

public class OrderPageObject {
    private final WebDriver driver;
    private final By ingredientsSection =
            By.xpath(".//section[contains(@class,'BurgerIngredients')]/div");
    @FindBy(xpath = ".//button[text()='Войти в аккаунт']")
    private WebElement logInToAccButton;

    @FindBy(xpath = ".//p[text()='Личный Кабинет']")
    private WebElement personalCabinetButton;

    @FindBy(xpath = ".//h1[text()='Соберите бургер']")
    private WebElement mainPageHeader;

    @FindBy(xpath = ".//div[@id='root']/div/section")
    private WebElement rootLoader;
    private final Map<String, String> ingredientsHeaderLocators = Map.of(
            "Булки", ".//h2[text()='Булки']",
            "Соусы", ".//h2[text()='Соусы']",
            "Начинки", ".//h2[text()='Начинки']"
    );


    public OrderPageObject(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void clickByLoginToPersonalCabinetButton() {
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("arguments[0].click()", personalCabinetButton);
    }

    public void clickByLoginToAccountButton() {
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("arguments[0].click()", logInToAccButton);
    }

    public boolean isAccessTokenExistsInLocalStorage() {

        JavascriptExecutor js = ((JavascriptExecutor) driver);
        String token = (String) js.executeScript(
                String.format("return window.localStorage.getItem('%s');", "accessToken"));
        return token != null && !token.isBlank() && !token.isEmpty();
    }

    public String getAccessTokenFromLocalStorage(WebDriver driver) {

        JavascriptExecutor js = ((JavascriptExecutor) driver);
        return (String) js.executeScript(
                String.format("return window.localStorage.getItem('%s');", "accessToken"));
    }

    public Double clickByIngredientTypeButton(String ingredientType) {
        // Удаляем на всякий случай модалки, которые могут перекрыть клики
        removeAllModals();

        List<WebElement> ingredientSectionElements = driver.findElements(ingredientsSection);
        WebElement ingredientsTypeDiv = ingredientSectionElements.get(0);
        int indexOfIngredient = INGREDIENT_TYPES_MAP.indexOf(ingredientType);

        // Добавляем id к табам чтоб потом с их помощью сделать хак (клик через js метод .click())
        addIdsToIngredients(indexOfIngredient, ingredientType);

        // Находим элемент с помощью id
        WebElement ingredientButton = ingredientsTypeDiv.findElement(By.xpath(String.format(".//div[@id=\"%s\"]", ingredientType)));

        // Удаляем стиль pointer-events для того чтобы можно было кликнуть по первому элементу и проверить скролл
        removeNoClickable(ingredientButton);

        // Кликаем по элементу через js, пришлось так сделать потому-что на последнем табе (Начинки) firefox клики выполняет некорректно
        clickToTheNavElement(ingredientType);

        return getPositionOfElementByXpath(ingredientsHeaderLocators.get(ingredientType));
    }

    private void removeAllModals() {
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("return document.querySelectorAll(\"div[class^='Modal']\").forEach(function(element) {element.remove();})");
    }

    private void addIdsToIngredients(Integer indexOfElement, String ingredientType) {
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        String jsScript = String.format("return document.querySelector(\"section[class^='BurgerIngredients_ingredients'] > div\").children[\"%s\"].setAttribute('id', \"%s\")",
                indexOfElement, ingredientType);
        js.executeScript(jsScript);
    }

    private void removeNoClickable(WebElement element) {
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("arguments[0].setAttribute(\"style\", \"pointer-events: auto\");", element);
    }

    private void clickToTheNavElement(String ingredientType) {
        String jsScript = String.format("return setTimeout(() => {document.querySelector(\"div[id='%s']\").click()}, 100)", ingredientType);
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript(jsScript);
    }


    private Double getPositionOfElementByXpath(String elementXPath) {
        String jsScript = String.format("return document.evaluate(\"%s\",document,null," +
                        "XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.getBoundingClientRect().y;",
                elementXPath);

        JavascriptExecutor js = ((JavascriptExecutor) driver);
        return (Double) js.executeScript(jsScript);
    }

    public boolean isIngredientsBlockChangedPosition(String ingredientType, Double oldElementPosition) {
        Double actualIngredientTypePosition = getPositionOfElementByXpath(ingredientsHeaderLocators.get(ingredientType));
        return oldElementPosition.intValue() > actualIngredientTypePosition.intValue();
    }

    public boolean isMainPageHeaderDisplayed() {
        return mainPageHeader.isDisplayed();
    }

    public void waitUntilMainPageHeaderWillBeLoaded() {
        new WebDriverWait(driver, DEFAULT_WAITING)
                .until(driver1 -> !rootLoader.isDisplayed() && mainPageHeader.isDisplayed());
    }

    public void waitUntilIngredientTypeWillBeChanged(String ingredientType) {
        String xpath = String.format(".//div[contains(@class,'current')]/span[text()='%s']", ingredientType);
        new WebDriverWait(driver, DEFAULT_WAITING).until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(xpath))));
    }
}
