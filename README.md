# Mancala

This repository contains the files for three modules:
- Object orientated programming: make your own implementation of the mancala game.
- Model view controller: build a website for your own mancala game (or use the sloppy default implementation).
- CI/CD: run your tests automatically when pushing code to Gitlab.

The skeleton project for the MVC is found on the mvc branch. On that branch, the README is updated with additional information.

## Repository structure

- Main folder (this folder): contains the files relevant for the whole project. For example the Gradle-wrapper files, the .gitignore, and this readme.
- domain/: contains the files that model the business domain (game rules). This is the folder you develop your OO mancala case in.

## Java project structure

A Java project is generally structured as follows:

```
build.gradle
src/
   main/
       (package)/
            (Java files)
   test/
       (package)/
            (Java test files)
```

In the project root folder (for example, the domain/ folder), a project definition is found. As we use Gradle, we have a build.gradle file. For Maven (another commonly used build tool), we would have a pom.xml. These files contain roughly the same: the project metadata and its dependencies. The build tool can then resolve those dependencies by downloading them from an online registry or compile related projects and link the resulting jar files.

A build tool also makes sure multiple Java files inside the same project are compiled in one go. Basically it acts as a wrapper around the compiler. You can invoke the compiler manually by typing `javac mancala/domain/Foo.java mancala/domain/Bar.java` over and over, or let your build tool generate and execute the command for you.

To tell the build tool which files to compile, the above structure is used. In src/main/ you place your application code, the actual implementation of the game rules. You should also adhere to the Java file structure, meaning that your folder structure should match your package definition (mancala/domain/) and your filename should match your class name (Foo.java). In src/test/ you place the files that test your main code. By convention, the test file structure mimics the main file structure (mancala/domain/FooTest.java).

## Using Gradle

You can either install Gradle on your machine and use the installation or use the Gradle wrapper files found next to this README. Replace the `./gradlew` command with `gradle` if using the globally installed Gradle or `.\gradlew.bat` if you're running the Windows batch script.

```bash
# Building
./gradlew build
# Testing (will fail with the initial code)
./gradlew test
# Running (only relevant for the MVC case)
./gradlew run
```

When you run the test, you will see a build failure. In `domain/src/test/java/mancala/domain.FooTest.java`, there is a failing test. If you fix the failing test, the build will succeed.

## Assignment

For the lectures, see [the drive](https://drive.google.com/drive/u/0/folders/1NK95KK9Ev1yZAz1vLoQSO8rEkZq-A9AC).

Design an object-oriented model for mancala that can handle the following scenarios:

- All small bowls start with 4 beads
- Players take turns making a move
- A player moves by selecting a small bowl on their own side, take all the beads and distribute them anti-clockwise one at a time
- When distributing, players include their own kalaha but not the opponent's
- If a move ends in the own kalaha, the player can make another move.
- If the last bead ends in an empty bowl on the own side, the player can take that bowl's bead and the direct opposite bowl's bead and put them in their kalaha.
- The game ends if all bowls of the turn player are empty
- The winner is the player with the most beads on their territory (all bowls).

Implement the game rules test-driven. Don't be afraid to delete or modify the Foo(Test) files, as they are just an example.