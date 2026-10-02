# FDN QA Automation Monorepo

Multi-platform test automation framework for Female Daily Network. Covers API, Web, Android, and iOS testing in a single Maven monorepo with shared utilities.

## Tech Stack

| Component | Technology | Version |
|-----------|------------|---------|
| Language | Java | 21 |
| Build | Maven | 3.9+ |
| Test Framework | TestNG | 7.10.2 |
| Web Automation | Selenium | 4.50.0 |
| Mobile Automation | Appium Java Client | 9.4.0 |
| API Automation | REST Assured | 5.5.0 |
| Reporting | Allure TestNG | 2.29.0 |
| Logging | SLF4J + Logback | 2.0.16 / 1.5.8 |
| JSON Serialization | Jackson | 2.18.0 |

## Project Structure

```
fdn-automation/
├── common/                 # Shared framework core
│   └── src/main/java/
│       ├── config/         # ConfigManager (env-based properties loader)
│       ├── constants/      # FrameworkConstants (timeouts, paths)
│       ├── driver/         # DriverManager (ThreadLocal WebDriver lifecycle)
│       ├── listeners/      # TestListener (TestNG hooks, screenshot on failure)
│       ├── reporting/      # ScreenshotManager
│       └── utils/          # WaitUtils (explicit waits)
├── api/                    # REST API test module
│   └── src/
│       ├── main/java/
│       │   ├── client/     # BaseApiClient, AuthClient
│       │   └── models/     # Request/Response DTOs
│       └── test/java/      # API test classes
├── web/                    # Web UI test module (Selenium)
│   └── src/
│       ├── main/java/
│       │   ├── pages/      # Page Objects (LoginPage, HomePage)
│       │   └── flows/      # Business flows (LoginFlow)
│       └── test/java/      # Web test classes
├── android/                # Android test module (Appium + UiAutomator2)
│   └── src/
│       ├── main/java/
│       │   ├── screens/    # Screen Objects (LoginScreen, HomeScreen)
│       │   └── flows/      # Business flows (LoginFlow)
│       └── test/java/      # Android test classes
├── ios/                    # iOS test module (Appium + XCUITest)
│   └── src/
│       ├── main/java/
│       │   ├── screens/    # Screen Objects (LoginScreen, HomeScreen)
│       │   └── flows/      # Business flows (LoginFlow)
│       └── test/java/      # iOS test classes
├── config/                 # Environment properties (dev, staging, production)
├── suites/                 # TestNG suite XML files
├── scripts/                # Shell scripts for running tests
└── test-data/              # Test data files
```

**Module dependency:**
```
common ← api
common ← web
common ← android
common ← ios
```

## Prerequisites

- **Java 21+**
- **Maven 3.9+**
- **Chrome** or **Firefox** (for web tests)
- **Appium Server** (for mobile tests)
- **Android SDK + Emulator** (for Android tests)
- **Xcode + iOS Simulator** (for iOS tests, macOS only)

## Getting Started

### 1. Clone the repository

```bash
git clone <repository-url>
cd fdn-automation
```

### 2. Build all modules

```bash
mvn clean install -Dmaven.test.skip=true
```

This compiles all modules and installs artifacts to local Maven repository. Required before running tests for the first time.

### 3. Verify the build

```bash
mvn clean compile
```

All 5 modules (common, api, web, android, ios) should show `BUILD SUCCESS`.

## Configuration

Environment-specific properties are stored in `config/`:

| File | Environment |
|------|-------------|
| `config/dev.properties` | Development |
| `config/staging.properties` | Staging |
| `config/production.properties` | Production |

Each file contains platform-specific settings:

```properties
web.baseUrl=https://dev.femaledaily.com
web.browser=chrome

api.baseUrl=https://api-dev.femaledaily.com

android.appium.url=http://127.0.0.1:4723
android.device.name=emulator-5554
android.app.package=com.femaledaily.app
android.app.activity=.MainActivity

ios.appium.url=http://127.0.0.1:4723
ios.device.name=iPhone 16
ios.bundle.id=com.femaledaily.app
```

Switch environment using `-Denv`:

```bash
mvn test -Denv=staging
```

Default is `dev` if not specified.

## Running Tests

### Per Module

```bash
# API tests
mvn test -pl api -DsuiteXmlFile=../suites/api.xml -Denv=dev

# Web tests
mvn test -pl web -DsuiteXmlFile=../suites/web.xml -Denv=dev

# Android tests
mvn test -pl android -DsuiteXmlFile=../suites/android.xml -Denv=dev

# iOS tests
mvn test -pl ios -DsuiteXmlFile=../suites/ios.xml -Denv=dev
```

### Using Shell Scripts

```bash
./scripts/run-api.sh
./scripts/run-web.sh
./scripts/run-android.sh
./scripts/run-ios.sh
```

### Smoke Suite (API + Web)

The smoke suite runs API and Web tests in parallel:

```bash
./scripts/run-smoke.sh
```

Or directly:

```bash
mvn test -DsuiteXmlFile=suites/smoke.xml -Denv=dev
```

> **Note:** The smoke suite currently covers API and Web modules only.

## Test Suites

TestNG suite definitions in `suites/`:

| Suite | File | Modules | Parallel |
|-------|------|---------|----------|
| API | `api.xml` | api | No |
| Web | `web.xml` | web | No |
| Android | `android.xml` | android | No |
| iOS | `ios.xml` | ios | No |
| Smoke | `smoke.xml` | api + web | Yes (thread-count=4) |

## Design Patterns

**Page/Screen Object + Flow Layer:**

```
Test → Flow → Page/Screen
```

- **Page/Screen Objects** handle UI element interaction
- **Flow Layer** composes page actions into business workflows
- **Tests** call flows and assert outcomes

```java
// Test — concise and readable
@Test
public void testValidLogin() {
    HomePage home = new LoginFlow(driver)
            .login("user@example.com", "password123");
    assertTrue(home.isWelcomeMessageDisplayed());
}
```

## Reporting

| Output | Location |
|--------|----------|
| TestNG results | `target/surefire-reports/` |
| Screenshots (on failure) | `reports/screenshots/` |
| Logs | `logs/automation.log` |

Screenshots are automatically captured when a test fails, via the `TestListener`.
