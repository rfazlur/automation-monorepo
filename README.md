# FDN QA Automation Monorepo

Maven multi-module automation framework untuk Web + Android + iOS + API testing.

## Arsitektur

```
fdn-automation/
├── common          # Framework core (config, driver, reporting, utils)
├── api             # REST API automation
├── web             # Web UI automation (Selenium)
├── android         # Android automation (Appium)
└── ios             # iOS automation (Appium)
```

**Dependency flow:**
```
common
  ↑
  ├── api
  ├── web
  ├── android
  └── ios
```

## Tech Stack

| Area | Technology |
|------|-----------|
| Language | Java 21 |
| Build | Maven |
| Test Framework | TestNG |
| Web | Selenium 4.18.1 |
| Mobile | Appium 8.6.0 |
| API | REST Assured 5.5.0 |
| Reporting | Allure 2.29.0 |
| Logging | SLF4J + Logback |
| JSON | Jackson |

## Prerequisites

- Java 21
- Maven 3.8+
- Chrome/Firefox (untuk Web)
- Appium Server (untuk Mobile)
- Android SDK + Emulator (untuk Android)
- Xcode + iOS Simulator (untuk iOS, macOS only)

## Build

```bash
# Compile semua module
mvn clean compile

# Package
mvn clean package
```

## Execution

### Per Module

```bash
# API tests
mvn test -pl api -DsuiteXmlFile=suites/api.xml -Denv=dev

# Web tests
mvn test -pl web -DsuiteXmlFile=suites/web.xml -Denv=dev

# Android tests
mvn test -pl android -DsuiteXmlFile=suites/android.xml -Denv=dev

# iOS tests
mvn test -pl ios -DsuiteXmlFile=suites/ios.xml -Denv=dev
```

### Via Scripts

```bash
./scripts/run-api.sh
./scripts/run-web.sh
./scripts/run-android.sh
./scripts/run-ios.sh
./scripts/run-smoke.sh
```

### Semua Module

```bash
mvn clean test -Denv=dev
```

## Configuration

Environment config di `config/`:
- `dev.properties`
- `staging.properties`
- `production.properties`

Pilih environment:
```bash
mvn test -Denv=staging
```

## Test Suites

TestNG suites di `suites/`:
- `api.xml` - API tests
- `web.xml` - Web tests
- `android.xml` - Android tests
- `ios.xml` - iOS tests
- `smoke.xml` - Smoke tests (API + Web parallel)

## Project Structure

```
common/
└── src/main/java/com/femaledaily/qa/
    ├── config/         # ConfigManager
    ├── driver/         # DriverManager (Web + Mobile)
    ├── reporting/      # ScreenshotManager
    ├── listeners/      # TestListener
    ├── utils/          # WaitUtils, etc
    └── constants/      # FrameworkConstants

api/
└── src/
    ├── main/java/com/femaledaily/qa/api/
    │   ├── client/     # BaseApiClient, AuthClient
    │   ├── endpoints/  # API endpoints
    │   └── models/     # Request/Response models
    └── test/java/      # API tests

web/
└── src/
    ├── main/java/com/femaledaily/qa/web/
    │   ├── pages/      # Page Objects
    │   ├── components/ # Reusable components
    │   └── flows/      # Business flows
    └── test/java/      # Web tests

android/
└── src/
    ├── main/java/com/femaledaily/qa/android/
    │   ├── screens/    # Screen Objects
    │   ├── components/ # Reusable components
    │   └── flows/      # Business flows
    └── test/java/      # Android tests

ios/
└── src/
    ├── main/java/com/femaledaily/qa/ios/
    │   ├── screens/    # Screen Objects
    │   ├── components/ # Reusable components
    │   └── flows/      # Business flows
    └── test/java/      # iOS tests
```

## Design Pattern

**Page Object Model + Flow Layer:**

```java
// Page Object - UI interaction
public class LoginPage {
    public LoginPage enterUsername(String username) { ... }
    public LoginPage enterPassword(String password) { ... }
    public HomePage clickLogin() { ... }
}

// Flow Layer - Business logic
public class LoginFlow {
    public HomePage login(String username, String password) {
        return new LoginPage(driver)
            .enterUsername(username)
            .enterPassword(password)
            .clickLogin();
    }
}

// Test - Simple & readable
@Test
public void testValidLogin() {
    HomePage home = new LoginFlow(driver).login("user", "pass");
    assertTrue(home.isWelcomeMessageDisplayed());
}
```

## Reporting

- Test results: `target/surefire-reports/`
- Screenshots (on failure): `reports/screenshots/`
- Logs: `logs/automation.log`

## Key Principles

1. **DRY** - Common framework code di `common` module
2. **Independence** - Setiap module bisa dijalankan terpisah
3. **Separation** - Page/Screen vs Flow vs Test
4. **Configuration** - Environment-aware via properties
5. **Parallel** - Modules independent, bisa parallel execution

## Next Steps

Phase 2:
- Parallel execution per module
- Allure report integration
- CI/CD pipeline (GitHub Actions)

Phase 3:
- Docker containerization
- Cloud device/browser grid
- Test data management
