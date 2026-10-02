# QA Automation Monorepo — Java + Maven

## 1. Architecture Overview

Untuk kebutuhan **Web + Android + iOS + API dalam satu repository**, dengan **Java + Maven**, gunakan **Maven multi-module monorepo**.

Struktur high-level:

```text
qa-automation/
├── pom.xml
├── README.md
├── .gitignore
│
├── common/
├── api/
├── web/
├── android/
├── ios/
├── test-data/
├── config/
├── suites/
├── scripts/
└── .github/
    └── workflows/
```

Arsitektur dependency:

```text
                  ┌──────────────┐
                  │    common    │
                  └──────┬───────┘
                         │
          ┌──────────────┼──────────────┐
          │              │              │
       ┌──▼──┐        ┌──▼───┐       ┌──▼─────┐
       │ API │        │ Web  │       │ Mobile │
       └─────┘        └──────┘       └───┬─────┘
                                         │
                                  ┌──────┴──────┐
                                  │             │
                              Android          iOS
```

Dengan demikian:

```text
common
   ↑
   ├── api
   ├── web
   ├── android
   └── ios
```

Masing-masing automation project tetap independent, tetapi dapat menggunakan utility/framework yang sama.

---

# 2. Final Project Structure

Struktur yang direkomendasikan:

```text
qa-automation/
│
├── pom.xml
│
├── common/
│   ├── pom.xml
│   └── src/main/java/
│       └── com/company/qa/
│           ├── config/
│           ├── constants/
│           ├── driver/
│           ├── listeners/
│           ├── reporting/
│           └── utils/
│
├── api/
│   ├── pom.xml
│   └── src/
│       ├── main/java/
│       │   └── com/company/qa/api/
│       │       ├── client/
│       │       ├── endpoints/
│       │       ├── models/
│       │       └── utils/
│       │
│       └── test/java/
│           └── com/company/qa/api/tests/
│
├── web/
│   ├── pom.xml
│   └── src/
│       ├── main/java/
│       │   └── com/company/qa/web/
│       │       ├── pages/
│       │       ├── components/
│       │       ├── flows/
│       │       └── utils/
│       │
│       └── test/java/
│           └── com/company/qa/web/tests/
│
├── android/
│   ├── pom.xml
│   └── src/
│       ├── main/java/
│       │   └── com/company/qa/android/
│       │       ├── screens/
│       │       ├── components/
│       │       ├── flows/
│       │       └── utils/
│       │
│       └── test/java/
│           └── com/company/qa/android/tests/
│
├── ios/
│   ├── pom.xml
│   └── src/
│       ├── main/java/
│       │   └── com/company/qa/ios/
│       │       ├── screens/
│       │       ├── components/
│       │       ├── flows/
│       │       └── utils/
│       │
│       └── test/java/
│           └── com/company/qa/ios/tests/
│
├── test-data/
│   ├── common/
│   ├── api/
│   ├── web/
│   ├── android/
│   └── ios/
│
├── config/
│   ├── dev.properties
│   ├── staging.properties
│   └── production.properties
│
├── suites/
│   ├── smoke.xml
│   ├── sanity.xml
│   └── regression.xml
│
├── scripts/
│   ├── run-api.sh
│   ├── run-web.sh
│   ├── run-android.sh
│   ├── run-ios.sh
│   └── run-all.sh
│
├── .github/
│   └── workflows/
│       ├── api.yml
│       ├── web.yml
│       ├── android.yml
│       ├── ios.yml
│       └── regression.yml
│
├── .gitignore
└── README.md
```

---

# 3. Parent `pom.xml`

Root `pom.xml` bertindak sebagai **parent sekaligus aggregator**.

Contoh:

```xml
<?xml version="1.0" encoding="UTF-8"?>

<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="
            http://maven.apache.org/POM/4.0.0
            https://maven.apache.org/xsd/maven-4.0.0.xsd">

    <modelVersion>4.0.0</modelVersion>

    <groupId>com.company.qa</groupId>
    <artifactId>qa-automation</artifactId>
    <version>1.0.0-SNAPSHOT</version>

    <packaging>pom</packaging>

    <name>QA Automation Monorepo</name>

    <modules>
        <module>common</module>
        <module>api</module>
        <module>web</module>
        <module>android</module>
        <module>ios</module>
    </modules>

    <properties>
        <java.version>21</java.version>
        <maven.compiler.source>${java.version}</maven.compiler.source>
        <maven.compiler.target>${java.version}</maven.compiler.target>

        <selenium.version>4.35.0</selenium.version>
        <appium.version>10.0.0</appium.version>
        <testng.version>7.11.0</testng.version>
        <restassured.version>5.5.6</restassured.version>
    </properties>

</project>
```

Untuk project baru, gunakan **Java 21 LTS**.

---

# 4. `common` Module

`common` adalah reusable framework/core layer.

Jangan melakukan copy-paste terhadap:

- DriverFactory
- ConfigReader
- WaitUtils
- ScreenshotUtils
- TestListener
- Reporting utilities

ke empat project.

Gunakan:

```text
common/
└── src/main/java/com/company/qa/

    ├── config/
    │   ├── ConfigManager.java
    │   └── Environment.java
    │
    ├── driver/
    │   ├── DriverManager.java
    │   ├── BrowserDriverFactory.java
    │   └── MobileDriverFactory.java
    │
    ├── reporting/
    │   ├── ReportManager.java
    │   └── ScreenshotManager.java
    │
    ├── listeners/
    │   └── TestListener.java
    │
    ├── utils/
    │   ├── JsonUtils.java
    │   ├── DateUtils.java
    │   ├── WaitUtils.java
    │   └── FileUtils.java
    │
    └── constants/
        └── FrameworkConstants.java
```

Semua module berikut menggunakan `common`:

```text
api
web
android
ios
```

---

# 5. Web Automation

## Technology

- Selenium
- TestNG
- Page Object Model
- Component Object
- Flow/Business Layer

Struktur:

```text
web/
└── src/
    ├── main/java/com/company/qa/web/
    │
    │   ├── pages/
    │   │   ├── LoginPage.java
    │   │   ├── HomePage.java
    │   │   └── ProductPage.java
    │   │
    │   ├── components/
    │   │   ├── Header.java
    │   │   └── Sidebar.java
    │   │
    │   └── flows/
    │       ├── LoginFlow.java
    │       └── CheckoutFlow.java
    │
    └── test/java/com/company/qa/web/tests/
        ├── LoginTest.java
        ├── CheckoutTest.java
        └── ProductTest.java
```

## Page Object

```java
public class LoginPage {

    private final WebDriver driver;

    private By username = By.id("username");
    private By password = By.id("password");
    private By loginButton = By.id("login");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public LoginPage enterUsername(String value) {
        driver.findElement(username).sendKeys(value);
        return this;
    }

    public LoginPage enterPassword(String value) {
        driver.findElement(password).sendKeys(value);
        return this;
    }

    public HomePage clickLogin() {
        driver.findElement(loginButton).click();
        return new HomePage(driver);
    }
}
```

## Flow Layer

Business flow dipisahkan dari Page Object:

```java
public class LoginFlow {

    private final WebDriver driver;

    public LoginFlow(WebDriver driver) {
        this.driver = driver;
    }

    public HomePage login(String username, String password) {

        return new LoginPage(driver)
                .enterUsername(username)
                .enterPassword(password)
                .clickLogin();
    }
}
```

Test menjadi lebih sederhana:

```java
@Test
public void validLogin() {

    new LoginFlow(driver)
            .login("user@test.com", "password");
}
```

---

# 6. Android Automation

## Technology

- Appium
- UiAutomator2
- TestNG

Struktur:

```text
android/
└── src/
    ├── main/java/com/company/qa/android/
    │
    │   ├── screens/
    │   │   ├── LoginScreen.java
    │   │   ├── HomeScreen.java
    │   │   └── ProfileScreen.java
    │   │
    │   ├── components/
    │   │   ├── BottomNavigation.java
    │   │   └── Header.java
    │   │
    │   └── flows/
    │       ├── LoginFlow.java
    │       └── CheckoutFlow.java
    │
    └── test/java/com/company/qa/android/tests/
        ├── LoginTest.java
        └── CheckoutTest.java
```

Contoh screen:

```java
public class LoginScreen {

    private final AndroidDriver driver;

    private By username =
        AppiumBy.id("com.company.app:id/username");

    private By password =
        AppiumBy.id("com.company.app:id/password");

    private By loginButton =
        AppiumBy.id("com.company.app:id/login");

    public LoginScreen(AndroidDriver driver) {
        this.driver = driver;
    }

    public LoginScreen enterUsername(String value) {
        driver.findElement(username).sendKeys(value);
        return this;
    }

    public LoginScreen enterPassword(String value) {
        driver.findElement(password).sendKeys(value);
        return this;
    }

    public HomeScreen login() {
        driver.findElement(loginButton).click();
        return new HomeScreen(driver);
    }
}
```

---

# 7. iOS Automation

Gunakan:

```text
iOS
 ↓
Appium
 ↓
XCUITest
 ↓
iOS Simulator / Real Device
```

Struktur dibuat konsisten dengan Android:

```text
ios/
└── src/
    ├── main/java/com/company/qa/ios/
    │   ├── screens/
    │   ├── components/
    │   ├── flows/
    │   └── utils/
    │
    └── test/java/com/company/qa/ios/tests/
```

Jangan memaksa Android dan iOS menggunakan implementation yang sama karena locator dan native behavior sering berbeda.

Contoh konsep:

```text
Login
├── AndroidLoginScreen
└── IOSLoginScreen
```

---

# 8. API Automation

## Technology

- REST Assured
- TestNG
- Jackson

Struktur:

```text
api/
└── src/
    ├── main/java/com/company/qa/api/
    │
    │   ├── client/
    │   │   ├── BaseApiClient.java
    │   │   ├── AuthClient.java
    │   │   └── UserClient.java
    │   │
    │   ├── endpoints/
    │   │   ├── UserEndpoints.java
    │   │   └── ProductEndpoints.java
    │   │
    │   ├── models/
    │   │   ├── User.java
    │   │   └── LoginRequest.java
    │   │
    │   └── utils/
    │
    └── test/java/com/company/qa/api/tests/
        ├── AuthTest.java
        ├── UserTest.java
        └── ProductTest.java
```

Jangan menaruh seluruh REST Assured setup langsung di setiap test.

Gunakan:

```text
Test
 ↓
API Client
 ↓
Endpoint
 ↓
REST Assured
```

Contoh:

```java
public class UserClient {

    public Response getUser(String userId) {

        return given()
                .spec(BaseApiClient.requestSpec())
                .when()
                .get("/users/" + userId);
    }
}
```

Test:

```java
@Test
public void getUserShouldReturn200() {

    Response response =
        new UserClient().getUser("123");

    response.then()
            .statusCode(200);
}
```

---

# 9. Test Data

Pisahkan test data dari Java code:

```text
test-data/
├── common/
│   ├── users.json
│   └── credentials.json
│
├── api/
│   ├── users.json
│   └── products.json
│
├── web/
│   └── login.json
│
├── android/
│   └── login.json
│
└── ios/
    └── login.json
```

Data sensitif seperti:

- password
- API key
- token
- client secret

jangan dimasukkan ke Git.

Gunakan:

```text
Environment Variable
        ↓
CI/CD Secret
        ↓
ConfigManager
```

---

# 10. Environment Configuration

Gunakan:

```text
config/
├── dev.properties
├── staging.properties
└── production.properties
```

Contoh:

```properties
web.baseUrl=https://staging.example.com

api.baseUrl=https://api-staging.example.com

android.appium.url=http://127.0.0.1:4723
android.device.name=emulator-5554
android.app.package=com.company.app

ios.appium.url=http://127.0.0.1:4723
ios.device.name=iPhone 16
ios.bundle.id=com.company.app
```

Eksekusi:

```bash
mvn test -Denv=staging
```

---

# 11. CLI Execution

Karena menggunakan Maven multi-module, automation dapat dijalankan berdasarkan module.

## API

```bash
mvn test -pl api
```

## Web

```bash
mvn test -pl web
```

## Android

```bash
mvn test -pl android
```

## iOS

```bash
mvn test -pl ios
```

## Semua

```bash
mvn test
```

## Module + Environment

```bash
mvn test -pl web -Denv=staging
```

```bash
mvn test -pl android -Ddevice=emulator-5554
```

---

# 12. Maven Profiles

Profiles dapat digunakan jika kebutuhan execution menjadi lebih kompleks.

Contoh:

```xml
<profiles>

    <profile>
        <id>api</id>
        <modules>
            <module>common</module>
            <module>api</module>
        </modules>
    </profile>

    <profile>
        <id>web</id>
        <modules>
            <module>common</module>
            <module>web</module>
        </modules>
    </profile>

    <profile>
        <id>android</id>
        <modules>
            <module>common</module>
            <module>android</module>
        </modules>
    </profile>

    <profile>
        <id>ios</id>
        <modules>
            <module>common</module>
            <module>ios</module>
        </modules>
    </profile>

</profiles>
```

Namun pada tahap awal, `-pl` sudah cukup. Hindari over-engineering Maven sebelum benar-benar dibutuhkan.

---

# 13. Test Suites

Buat suite berdasarkan tujuan dan platform:

```text
suites/
├── smoke.xml
├── regression.xml
├── sanity.xml
├── api.xml
├── web.xml
├── android.xml
└── ios.xml
```

Konsep:

```text
Smoke
 ├── API
 ├── Web
 ├── Android
 └── iOS

Regression
 ├── API
 ├── Web
 ├── Android
 └── iOS
```

Contoh execution:

```bash
mvn test -Dsuite=smoke
```

---

# 14. Hindari `BaseTest` yang Terlalu Besar

Jangan membuat:

```text
BaseTest
 ├── WebDriver
 ├── AndroidDriver
 ├── IOSDriver
 ├── API client
 ├── database
 ├── screenshot
 ├── login
 ├── config
 └── everything
```

Karena pada akhirnya dapat berubah menjadi:

```text
BaseTest.java = 2,000+ lines
```

Lebih baik gunakan context dan komponen terpisah:

```text
TestContext
   │
   ├── Config
   ├── Driver
   ├── API
   └── Reporting
```

Setiap module hanya mengambil dependency yang dibutuhkan.

---

# 15. Dependency Management

Versi dependency sebaiknya dikontrol dari root POM.

Contoh:

```xml
<dependencyManagement>

    <dependencies>

        <dependency>
            <groupId>org.seleniumhq.selenium</groupId>
            <artifactId>selenium-java</artifactId>
            <version>${selenium.version}</version>
        </dependency>

        <dependency>
            <groupId>io.appium</groupId>
            <artifactId>java-client</artifactId>
            <version>${appium.version}</version>
        </dependency>

        <dependency>
            <groupId>io.rest-assured</groupId>
            <artifactId>rest-assured</artifactId>
            <version>${restassured.version}</version>
        </dependency>

        <dependency>
            <groupId>org.testng</groupId>
            <artifactId>testng</artifactId>
            <version>${testng.version}</version>
        </dependency>

    </dependencies>

</dependencyManagement>
```

Child module tidak perlu menentukan version dependency lagi.

---

# 16. Reporting

Reporting dibuat sebagai framework capability dan digunakan lintas platform.

Struktur:

```text
test-results/
├── api/
├── web/
├── android/
└── ios/

reports/
├── allure-results/
├── allure-report/
└── screenshots/
```

Recommended stack:

```text
TestNG
   ↓
Allure
   ↓
HTML Report
```

Untuk failed test:

```text
Failed Web Test
       ↓
Screenshot

Failed Android Test
       ↓
Screenshot + Logcat

Failed iOS Test
       ↓
Screenshot + Device Log
```

---

# 17. CI/CD

Karena monorepo, jangan membuat satu pipeline yang selalu menjalankan semua platform.

Gunakan pipeline terpisah:

```text
.github/workflows/

├── api.yml
├── web.yml
├── android.yml
├── ios.yml
└── regression.yml
```

Contoh workflow:

```text
Pull Request
     │
     ├── API smoke
     └── Web smoke

Merge to main
     │
     ├── API regression
     ├── Web regression
     ├── Android regression
     └── iOS regression
```

Untuk mobile:

### Android runner

Membutuhkan:

```text
Android SDK
Android Emulator
Appium
```

### iOS runner

Membutuhkan macOS:

```text
macOS runner
Xcode
iOS Simulator
Appium
```

---

# 18. Dependency Rules

Dependency ideal:

```text
                 common
                /  |  \
               /   |   \
              /    |    \
            API   WEB   MOBILE
                       /     \
                  Android    iOS
```

Hindari dependency silang:

```text
Android → Web
Web → API
iOS → Android
API → Web
```

kecuali terdapat kebutuhan arsitektur yang benar-benar jelas.

---

# 19. Mobile Abstraction

Karena Android dan iOS memiliki konsep yang sama, beberapa infrastructure dapat diletakkan di `common`.

Contoh:

```text
common/
└── mobile/
    ├── MobileDriverManager.java
    ├── MobileElement.java
    └── MobileWait.java
```

Tetapi screen implementation tetap dipisahkan:

```text
android/
└── screens/

ios/
└── screens/
```

Contoh:

```text
Login
├── AndroidLoginScreen
└── IOSLoginScreen
```

Tujuannya adalah reuse infrastructure tanpa memaksa platform berbeda menggunakan implementation yang sama.

---

# 20. Recommended Technology Stack

| Area | Stack |
|---|---|
| Language | Java 21 |
| Build | Maven |
| Test Runner | TestNG |
| Web | Selenium |
| Android | Appium + UiAutomator2 |
| iOS | Appium + XCUITest |
| API | REST Assured |
| Design | POM / Screen Object + Flow |
| Config | `.properties` + Environment Variables |
| Reporting | Allure |
| Logging | SLF4J + Logback |
| JSON | Jackson |
| CI | GitHub Actions / Jenkins |
| Code Quality | Checkstyle + SpotBugs |
| Dependency | Maven |
| Version Control | Git |

---

# 21. Key Architecture Principles

Prinsip utama framework:

### 1. `common` = Framework Core

`common` menangani reusable infrastructure.

### 2. Platform Module = Automation Implementation

```text
api
web
android
ios
```

menangani test dan implementation masing-masing.

### 3. Jangan Duplicate Infrastructure

Driver, config, reporting, logging, utility dan listener yang reusable sebaiknya tidak di-copy ke setiap module.

### 4. Jangan Buat God Object

Hindari `BaseTest` yang mengurus semuanya.

### 5. API, Web, Android dan iOS Harus Bisa Dijalankan Independent

Contoh:

```bash
mvn test -pl api
mvn test -pl web
mvn test -pl android
mvn test -pl ios
```

### 6. Configuration Harus Environment-Aware

```bash
mvn test -pl web -Denv=staging
```

### 7. CI/CD Harus Bisa Menjalankan Platform Secara Terpisah

Hal ini penting untuk mempercepat feedback cycle.

---

# 22. Target Framework Evolution

Framework ini dapat dikembangkan secara bertahap:

```text
Phase 1
Java + Maven
        ↓
Multi-module structure
        ↓
common + API + Web + Android + iOS

Phase 2
        ↓
TestNG
        ↓
Parallel execution
        ↓
Allure reporting

Phase 3
        ↓
CLI
        ↓
Environment management
        ↓
Tag-based execution

Phase 4
        ↓
CI/CD
        ↓
Browser/device matrix
        ↓
Remote Appium

Phase 5
        ↓
Docker
        ↓
Grid / cloud device
        ↓
Centralized dashboard

Phase 6
        ↓
AI-assisted testing
        ↓
Test generation
        ↓
Failure analysis
        ↓
Self-healing / intelligent automation
```

---

# 23. Recommended Starting Point

Untuk tahap pertama, jangan langsung membangun semua fitur.

Mulai dari:

```text
qa-automation/
├── pom.xml
├── common/
├── api/
├── web/
├── android/
└── ios/
```

Pastikan terlebih dahulu:

```bash
mvn clean test
```

berhasil.

Kemudian implementasikan satu happy-path test untuk masing-masing:

```text
API
 └── Login API

Web
 └── Login Web

Android
 └── Login Android

iOS
 └── Login iOS
```

Setelah architecture terbukti stabil, baru tambahkan:

```text
Test Data
↓
Environment
↓
Reporting
↓
Parallel Execution
↓
CLI
↓
CI/CD
↓
Device/Browser Matrix
```

Dengan pendekatan ini, framework tetap sederhana di awal tetapi memiliki fondasi yang cukup kuat untuk berkembang menjadi **enterprise-grade QA automation framework**.
