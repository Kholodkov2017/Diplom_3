# Diplom_3
Задания к третьей части диплома

## Использованные технологии
- JUnit
- Selenium
- Selenium Grid
- RestAssured

## Описание проделанной работы

В рамках данного задания были реализованы тесты, покрывающие 
основные функциональности сайта [Stellar Burgers]("https://stellarburgers.nomoreparties.site/")


Внутри проекта находится файл _**docker-compose.yaml**_, в котором прописана конфигурация для запуска 
хаба и динамического подключения нужных нод. С помощью технологии _**Selenium Grid**_ осуществляется тестирование на
трех платформах:_
- __***Google Chrome ver. 111***__
- __***Microsoft Edge ver. 111***__
- __***Mozila Firefox ver. 111***__
- __***Yandex Browser ver. 23.3.3***__

Нода с __**Яндекс.Браузером**__ внутри в официальном _**docker-репозитории selenium**_ недоступна,
потому её необходимо собрать руками, для чего следует перейти в каталог `./selenium-grid-node-yandex` и выполнить 
следующую команду:
```shell
docker build -t yandex-browser .
```
где `yandex-browser` - имя будущего образа, который должен быть указан в свойстве `image` файла `docker-compose.yaml`:
```yaml
  selenium-yandex-browser:
    image: yandex-browser
    shm_size: 2gb
    depends_on:
      - selenium-hub
    environment:
      - SE_EVENT_BUS_HOST=selenium-hub
      - SE_EVENT_BUS_PUBLISH_PORT=4442
      - SE_EVENT_BUS_SUBSCRIBE_PORT=4443
      - SE_NODE_MAX_INSTANCES=4
      - SE_NODE_MAX_SESSIONS=4
```
После выполнения указанных выше манипуляций необходимо выполнить следующую команду:

```shell
docker-compose up -d
```
После чего будут подняты контейнеры со всеми необходимыми нодами и управляющим хабом.

_**Web-интерфейс Selenium Grid**_ будет доступен на хосте `http://localhost:4444/ui#/sessions`, в котором, 
с помощью технологии _**VNC**_ получится открыть _**ui контейнера**_ и просмотреть работу браузера, где 
выполняются тесты.

Использование плагина **maven-surefire-plugin** получилось реализовать конфигурацию, запускающую
тесты в параллельном режиме, что позволяет добиться существенного прироста скорости их выполнения. 

К данному проекту была подключена библиотека _**RestAssured**_, с помощью которой происходит обращение
на API сайта _**Stellar Burgers**_ и удаление созданных пользователей.

Параметризация тестов реализуется с использованием библиотеки [JUnitParams](https://mvnrepository.com/artifact/pl.pragmatists/JUnitParams/1.0.4).
Пример её использования выглядит следующим образом:

```java
@RunWith(JUnitParamsRunner.class)
public class SafeAdditionUtilTest {

    private SafeAdditionUtil serviceUnderTest
      = new SafeAdditionUtil();

    @Test
    @Parameters({ 
      "1, 2, 3", 
      "-10, 30, 20", 
      "15, -5, 10", 
      "-5, -10, -15" })
    public void whenWithAnnotationProvidedParams_thenSafeAdd(
      int a, int b, int expectedValue) {
 
        assertEquals(expectedValue, serviceUnderTest.safeAdd(a, b));
    }

}
```

При выполнении части задания, касающегося тестирования процесса перехода между типами ингредиентов на странице
конструктора бургеров, была предпринята попытка перехватить не только сам процесс перехода между табами
(изменение фокуса, которое перехватывается путём поиска ключевого слова "_**current**_" в **css-классах**
выбранного таба), но и работу скролла. При выполнении тест-кейса выбора последнего таба (секция **"Начинки"**) в среде
браузера _**Firefox**_, клик происходил некорректно, поэтому пришлось прибегнуть к использованию скриптов на языке
_**JavaScript**_ Так же вынужденно пришлось применить функцию ``Thread.sleep()`` для того, чтобы иметь возможность отслеживать
корректную работу скролла. 


