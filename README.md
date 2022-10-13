# CS308 Assignment

This is the Boston Metro system application for Group 2. It uses JavaFX for the UI and JUnit for testing.

## Installation
### Install JavaFX
Download the JavaFX SDK from https://gluonhq.com/products/javafx/ . We used version 18.

Extract the contents to any path on your drive.

In IntelliJ, open the Edit Configurations menu, and select `frontend.MetroUI`.

Click on "VM Options", then add the following VM options:

`--module-path <JavaFX lib path> --add-modules=javafx.controls,javafx.fxml`

Where `<JavaFX path>` is the path to your extracted JavaFX folder.

### Install JUnit
Go to File -> Project Structure -> Libraries

Go to libraries, click on + on the top left, type JUnit in the search bar

Download JUnit 5.7.


####---
It should now be possible to run the application by selecting the MetroUI configuration.
