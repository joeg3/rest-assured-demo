# rest-assured-demo
Demo of Rest Assured for testing REST APIs

This demo runs tests against the fake api at: https://jsonplaceholder.typicode.com

## To Create a Project Like This From Scratch
- Install `gradle`.
- Create project folder, in this case `rest-assured-demo` and `cd` into it.
- Run `gradle init --type java-library`. I selected defaults for all the prompts except Source Package, which I named `org.example`.

## Gradle Project Properties
This project uses a Gradle project property called `platform` to show how a test suite can be configured for different api endpoints to test against (test, staging, prod etc.). Multiple platform names and their corresponding REST endpoint URLs can be setup in `src/test/resources/config.properties` At runtime, you can select which endpoint from the configuration to run the tests against.

Here are the ways to pass the `platform` property to the Gradle build, in order of precedence:
1. Command line default: `./gradlew build`
2. Command line, specify property: `./gradlew build -Pplatform=staging`
3. Command line, Java system property: `./gradlew build -Dorg.gradle.project.platform=staging`. However, I read that Gradle may fork the build in a new JVM, so the -D argument may not be passed along.
4. Command line, environment variable: `ORG_GRADLE_PROJECT_platform=staging ./gradlew build`
5. Set/change default in user `gradle.properties` file, usually in `~/.gradle/gradle.properties`, add value to file: `platform=staging`
6. Set/change default in project `gradle.properties` file, in project root directory, add value to file: `platform=staging`

For this project, I set a default value of `platform=test` in the project root directory `gradle.properties` file.

##  Run the tests
- From command line, default platform: `./gradlew build` will run the tests on the default `test` platform.
- From command line, specify platform: `./gradlew build -Pplatform=staging` will run the tests on the `staging` platform.
- From command line, run all tests in one class: `./gradlew test --tests org.example.restdemo.BasicExamples`
- From command line, run one test: `./gradlew test --tests org.example.restdemo.BasicExamples.getTodoByIdInPath`
- From command line: `./gradlew clean test -Pgithub.token=3443344...`
- From IntelliJ, to run all tests, create a run configuration for the `src/test` folder and run Gradle task `:test` with arguments `--tests * -Pplatform=test`
