# 📦 Generic library

[![Java](https://img.shields.io/badge/Java-21%2B-blue.svg)](https://www.oracle.com/java/)
[![Gradle](https://img.shields.io/badge/Gradle-Build-brightgreen.svg)](https://gradle.org/)
[![Lombok](https://img.shields.io/badge/Lombok-Required-orange.svg)](https://projectlombok.org/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

A lightweight and flexible Java library for working with generics.  
This utility class `Generic<T, S>` allows you to store and operate on two elements of type `T` and two of type `S`, providing a set of helpful methods for validation, transformation, and numeric computation.

---

## ✨ Features

- ✅ Generic class with support for two types (`T`, `S`)
- 🔁 Compare elements as strings or numbers
- 🧮 Parse delimited strings into double arrays
- 📈 Get the maximum numeric value from an array
- 🧼 Clean code with Lombok and JetBrains annotations

---

## 📦 Installation

Add the library to your project by cloning or importing it as a local dependency:

```bash
git clone https://github.com/your-username/generic-library.git
```

Make sure you have [Lombok](https://projectlombok.org/) and Java 21+ configured in your IDE and build tool.

---

## 📘 Usage

### ✅ Basic Example

```java
import io.github.imecuadorian.library.Generic;

public class Main {
    public static void main(String[] args) {
        Generic<String, String> g = new Generic<>("10,20,30", ",");
        double[] values = g.getValuesFromWords(g.getT1(), g.getS1());

        for (double d : values) {
            System.out.println(d);
        }
    }
}
```

---

## 📚 API Overview

### 🧠 `Generic<T, S>`

| Method                   | Description                                                                 |
|--------------------------|-----------------------------------------------------------------------------|
| `validateWord(values)`   | Compares two `T` elements using their string representations               |
| `validateNumber(values)` | Compares two `Number` elements by their numeric value                      |
| `numberMax(values)`      | Returns the max value in a `T[]` array (must be a subclass of `Number`)    |
| `getValuesFromWords()`   | Splits a delimited string and parses each element into a `double` array    |

---

## 🧪 Testing

This project is testable using JUnit 5.

To run tests:

```bash
./gradlew test
```

🧷 Ensure your test files are located under:

```
src/test/java/io/github/imecuadorian/library/GenericTest.java
```

Example test method:

```java
@Test
void testGetValuesFromWords() {
    Generic<String, String> g = new Generic<>("5.5, 7.3, 2.1", ",");
    double[] result = g.getValuesFromWords(g.getT1(), g.getS1());

    assertArrayEquals(new double[]{5.5, 7.3, 2.1}, result);
}
```

---

## 📂 Project Structure

```
generic-library/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── io/github/imecuadorian/library/
│   │           └── Generic.java
│   └── test/
│       └── java/
│           └── io/github/imecuadorian/library/
│               └── GenericTest.java
├── build.gradle
└── README.md
```

---

## 🧰 Requirements

- Java 21+
- Gradle
- Lombok
- (Optional) JetBrains Annotations for `@NotNull`

---

## 📄 License

This project is licensed under the [MIT License](LICENSE).  
Feel free to use, modify, and contribute.

---

## 🤝 Contributing

Contributions are welcome!  
If you find a bug or have an idea for an improvement, feel free to open an issue or submit a pull request.

---

## 🙌 Credits

Created by [@ImEcuadorian](https://github.com/imecuadorian) – Ecuadorian developer passionate about clean and reusable code. Free software