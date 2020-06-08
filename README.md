# Demo of Rest Assured for testing REST and GraphWL APIs

- The REST demo tests against the fake api at: https://jsonplaceholder.typicode.com
- The GraphQL demo tests against a GitHub account at https://api.github.com/graphql using a personal access token
- To create a token: https://help.github.com/en/github/authenticating-to-github/creating-a-personal-access-token-for-the-command-line
- GitHub GraphQL API: https://developer.github.com/v4/

## To Create a Project Like This From Scratch
- Install `gradle`.
- Create project folder, in this case `rest-assured-demo` and `cd` into it.
- Run `gradle init --type java-library`. I selected defaults for all the prompts except Source Package, which I named `org.example`.

##  Run the tests
- From command line: `./gradlew clean test -Pgithub.token=3443344...`
- From IntelliJ: Create a run configuration for the `test` folder and run Gradle tasks `:cleanTest :test` with arguments `--tests "org.example.*" -Pgithub.token=123...`

## Task List
- [ ] Decide on the best place for the config that will reside in a file. The current location of `src/test/resources/config.properties`, or `gradle.properties` or somewhere else? Regardless, some properties will probably have to be passed in via command line or environment variable.
- [ ] Add logging?

## Helpful Links
- https://linchpiner.github.io/gradle-for-devops-2.html
