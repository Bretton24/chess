# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[![Sequence Diagram](10k-architecture.png)](https://sequencediagram.org/index.html#initialData=C4S2BsFMAIGEAtIGckCh0AcCGAnUBjEbAO2DnBElIEZVs8RCSzYKrgAmO3AorU6AGVIOAG4jUAEyzAsAIyxIYAERnzFkdKgrFIuaKlaUa0ALQA+ISPE4AXNABWAexDFoAcywBbTcLEizS1VZBSVbbVc9HGgnADNYiN19QzZSDkCrfztHFzdPH1Q-Gwzg9TDEqJj4iuSjdmoMopF7LywAaxgvJ3FC6wCLaFLQyHCdSriEseSm6NMBurT7AFcMaWAYOSdcSRTjTka+7NaO6C6emZK1YdHI-Qma6N6ss3nU4Gpl1ZkNrZwdhfeByy9hwyBA7mIT2KAyGGhuSWi9wuc0sAI49nyMG6ElQQA)

## IntelliJ Support

Open the project directory in IntelliJ in order to develop, run, and debug your code using an IDE.

## Maven Support

You can use the following commands to build, test, package, and run your code.

| Command                    | Description                                     |
| -------------------------- | ----------------------------------------------- |
| `mvn compile`              | Builds the code                                 |
| `mvn package`              | Run the tests and build an Uber jar file        |
| `mvn package -DskipTests`  | Build an Uber jar file                          |
| `mvn install`              | Installs the packages into the local repository |
| `mvn test`                 | Run all the tests                               |
| `mvn -pl shared test`     | Run all the shared tests                        |
| `mvn -pl client exec:java` | Build and run the client `Main`                 |
| `mvn -pl server exec:java` | Build and run the server `Main`                 |

These commands are configured by the `pom.xml` (Project Object Model) files. There is a POM file in the root of the project, and one in each of the modules. The root POM defines any global dependencies and references the module POM files.

### Running the program using Java

Once you have compiled your project into an uber jar, you can execute it with the following command.

```sh
java -jar client/target/client-jar-with-dependencies.jar

♕ 240 Chess Client: chess.ChessPiece@7852e922
```


# ♕ BYU CS 240 Chess Phase2 Code
](https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWOZVYSnfoccKQCLAwwAIIgQKAM4TMAE0HAARsAkoYMhZkwBzKBACu2GAGI0wKgE8YAJRRakEsFEFIIaYwHcAFkjAdEqUgBaAD4WakoALhgAbQAFAHkyABUAXRgAej0VKAAdNABvLMpTAFsUABoYXCl3aBlKlBLgJAQAX0wKcNgQsLZxKKhbe18oAAoiqFKKquUJWqh6mEbmhABKDtZ2GB6BIVFxKSitFDAAVWzx7Kn13ZExSQlt0PUosgBRABk3uCSYCamYAAzXQlP7ZTC3fYPbY9Tp9FBRNB6BAIDbULY7eRQw4wECDQQoc6US7FYBlSrVOZ1G5Y+5SJ5qBRRACSADl3lZfv8ydNKfNFssWjA2Ul4mDaHCMaFIXSJFE8SgCcI9GBPCTJjyaXtZQyXsL2W9OeKNeSYMAVZ4khAANbofWis0WiG0g6PQKwzb9R2qq22tBo+Ew0JwyLey029AByhB+DIdBgKIAJgADMm8vlzT6I2h2ugZJodPpDEZoLxjjAPhA7G4jF4fH440Fg6xQ3FEqkMiopC40Onuaa+XV2iHus30V6EFWkGh1VMKbN+etJeIGTLXUcTkSxv2UFq7q7dUzyJ9vlyrjygSDjc7tQf3WP4YjkaiA1KYGuHvL8b5larZ5qb33aEej1NkOTPUlTUzcM-XtMVoNfFdMVvT9cW-FAKG7VxRmg310D3bE3WeI8wMNX5cOzOCYC7CQe0AwiY2XBEwzw-0mMYlsoCiCi-Sjboen8eMk1TdMePQXM0HzbRdAMYwdBQO1Ky0fRmFrbxfEwQSm16Vs+BPJI3jSdIaJ7PIxLYziGSYqJJ2UlUcItVil09VRkKAnEZBQBATgwh4ewcrM-QInUQKPPSvgMljKOBCBQUw2jXEQ1zpRdVDPO8n9HOzAKYPw+iQuIqJwreSKd0qcyYAAMSseIAFkw3yu8PXHZiU2TJKOK6YT2pHGMtITGA2swPMCxk4tBhkCthhgABxHlHjU+tNMbZhmpoLiYhmkqjK0HkzKy3jerW9gbOGOaygkHKnI6tzCI3MBzskK7s2Cu9CuPL4fhgAAqS9YpgXaLsa4Djq9QHJButbQ3B6QjoElbuvTGGJKkwtZKMbA9CgbBvPgdDZovRaNP6qzOKiNtkh2vbTAO9AkZ5VkeWHSzQeYhUCUe0ZwcZ8lzOclrV1SnFjgenkuYZnlyj54H6VC14Ty+7nJYq6q6oB+aZbdVmnxRG6UpQnF2d8Tmld52m0Fe4D3tIo1TYqCqRTFGHNc6x91bKZk+Ehh9oZ5T2+L6hHBpEgpwc9lHRqLYxzC8yd3BgAApCBpwJspjAUBBQGtZaAlWn2NriU4O3ScH9sCumCn6uAIEnKBKjDvhma6UmWqiAArZO0E5qua+gcoG6l83+cDW7ZXuk2-b4Qfy4tl25Y+093ZQT3p9ytxVfq52P1l7WYCRXX2NH9czRkGQt2ECQkkVEpq9r8WPannva+Ht9t7lE+z+yC-4gUbJ1vv5ej8Vq32gDAAAvDAVkz5Lay3elYN4Ntfh21XqxKiW8hZawfCdYOPUXKu1DENOGoR+rdWGpJSO6MdDAEsIgRUsBgDYBxoQZwrgPDqQbLnFu61ybFQMkZdQAdd4gG8ngYQjDkAgBYTOF+SF9buXfulHywgUSjBgURRkRV9JvB+n9UE6CDbqL1Lw7Rv0YqggmHPd6xidFmIam-V22DCF4PvDpDaTjm4uJITgzQI0gA)https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWOZVYSnfoccKQCLAwwAIIgQKAM4TMAE0HAARsAkoYMhZkwBzKBACu2GAGI0wKgE8YAJRRakEsFEFIIaYwHcAFkjAdEqUgBaAD4WakoALhgAbQAFAHkyABUAXRgAej0VKAAdNABvLMpTAFsUABoYXCl3aBlKlBLgJAQAX0wKcNgQsLZxKKhbe18oAAoiqFKKquUJWqh6mEbmhABKDtZ2GB6BIVFxKSitFDAAVWzx7Kn13ZExSQlt0PUosgBRABk3uCSYCamYAAzXQlP7ZTC3fYPbY9Tp9FBRNB6BAIDbULY7eRQw4wECDQQoc6US7FYBlSrVOZ1G5Y+5SJ5qBRRACSADl3lZfv8ydNKfNFssWjA2Ul4mDaHCMaFIXSJFE8SgCcI9GBPCTJjyaXtZQyXsL2W9OeKNeSYMAVZ4khAANbofWis0WiG0g6PQKwzb9R2qq22tBo+Ew0JwyLey029AByhB+DIdBgKIAJgADMm8vlzT6I2h2ugZJodPpDEZoLxjjAPhA7G4jF4fH440Fg6xQ3FEqkMiopC40Onuaa+XV2iHus30V6EFWkGh1VMKbN+etJeIGTLXUcTkSxv2UFq7q7dUzyJ9vlyrjygSDjc7tQf3WP4YjkaiA1KYGuHvL8b5larZ5qb33aEej1NkOTPUlTUzcM-XtMVoNfFdMVvT9cW-FAKG7VxRmg310D3bE3WeI8wMNX5cOzOCYC7CQe0AwiY2XBEwzw-0mMYlsoCiCi-Sjboen8eMk1TdMePQXM0HzbRdAMYwdBQO1Ky0fRmFrbxfEwQSm16Vs+BPJI3jSdIaJ7PIxLYziGSYqJJ2UlUcItVil09VRkKAnEZBQBATgwh4ewcrM-QInUQKPPSvgMljKOBCBQUw2jXEQ1zpRdVDPO8n9HOzAKYPw+iQuIqJwreSKd0qcyYAAMSseIAFkw3yu8PXHZiU2TJKOK6YT2pHGMtITGA2swPMCxk4tBhkCthhgABxHlHjU+tNMbZhmpoLiYhmkqjK0HkzKy3jerW9gbOGOaygkHKnI6tzCI3MBzskK7s2Cu9CuPL4fhgAAqS9YpgXaLsa4Djq9QHJButbQ3B6QjoElbuvTGGJKkwtZKMbA9CgbBvPgdDZovRaNP6qzOKiNtkh2vbTAO9AkZ5VkeWHSzQeYhUCUe0ZwcZ8lzOclrV1SnFjgenkuYZnlyj54H6VC14Ty+7nJYq6q6oB+aZbdVmnxRG6UpQnF2d8Tmld52m0Fe4D3tIo1TYqCqRTFGHNc6x91bKZk+Ehh9oZ5T2+L6hHBpEgpwc9lHRqLYxzC8yd3BgAApCBpwJspjAUBBQGtZaAlWn2NriU4O3ScH9sCumCn6uAIEnKBKjDvhma6UmWqiAArZO0E5qua+gcoG6l83+cDW7ZXuk2-b4Qfy4tl25Y+093ZQT3p9ytxVfq52P1l7WYCRXX2NH9czRkGQt2ECQkkVEpq9r8WPannva+Ht9t7lE+z+yC-4gUbJ1vv5ej8Vq32gDAAAvDAVkz5Lay3elYN4Ntfh21XqxKiW8hZawfCdYOPUXKu1DENOGoR+rdWGpJSO6MdDAEsIgRUsBgDYBxoQZwrgPDqQbLnFu61ybFQMkZdQAdd4gG8ngYQjDkAgBYTOF+SF9buXfulHywgUSjBgURRkRV9JvB+n9UE6CDbqL1Lw7Rv0YqggmHPd6xidFmIam-V22DCF4PvDpDaTjm4uJITgzQI0gA
