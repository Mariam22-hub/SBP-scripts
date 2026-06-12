# SBP-scripts

## Requirements
- Java 17 (or the Java version used by your Alloy installation)
- Alloy 6.x standalone JAR

## Project Structure
```text
alloy-api-test/
├── alloy.jar
├── CountAlloyInstances.java
└── model.als
```

## Compile
Compile the program against the Alloy JAR:

```bash
javac -cp alloy.jar CountAlloyInstances.java file.als symmetry-breaking-num
```

Example output:
```text
Running command: run$1
Instance #1
Instance #2
Instance #3
...
Total instances: 8
```
