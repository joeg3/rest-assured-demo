# Demo of Rest Assured for testing REST APIs

This demo tests against the fake api at: https://jsonplaceholder.typicode.com/

## To Create a Project Like This From Scratch
- Install `gradle`.
- Create project folder, in this case `rest-assured-demo` and `cd` into it.
- Run `gradle init --type java-library`. I selected defaults for all the prompts except Source Package, which I named `org.example`.

##  Run the tests
- From command line: `./gradlew clean test -Pplatform=test`
- From IntelliJ: Create a run configuration for the `test` folder and run Gradle tasks `:cleanTest :test` with arguments `--tests * -Pplatform=test`

## Task List
- [ ] Handle incorrect project properties with error handling and/or defaults.
- [ ] Decide on the best place for the config that will reside in a file. The current location of `src/test/resources/config.properties`, or `gradle.properties` or somewhere else? Regardless, some properties will probably have to be passed in via command line or environment varialbe.
- [ ] Add logging?

## Helpful Links
- https://linchpiner.github.io/gradle-for-devops-2.html
