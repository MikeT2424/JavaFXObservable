package sample;

import javafx.application.Application;
import javafx.collections.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/*
 The JavaFX Observable interface is the base interface for many container classes and interfaces in the JavaFX Collection
 Framework. For example, ObservableList<E> and ObservableArray<T> are two common implementations of the base
 Observable interface. The Java core Collection API already contains many useful container classes to represent
 the generic data structure of lists, set, and maps. For example, java.util.ArrayList<E> is a re-sizable array
 implementation of the java.util.List<E> interface to contain a list of objects. However, they are incapable of
 working seamlessly when synchronous functionality is required between the list model and the view
 component in a GUI scenario
*/

/*
 Because ObservableList<E> adheres to the rules of the observable and observer paradigm of MVC, such as providing
 notification to its interested observer regarding any updation, addition or removal of objects from the model list,
 it became a de-facto container for using any lists in the JavaFX arena. Here, data representation in the model and
 view are synchronized seamlessly. JavaFX ObserverList<E> is typically used in UI controls such as ListView and TableView.
 Let's go through a quick example to see how ObservableList<E> is actually used.
*/

/*
 This is a simple example where a list of data is displayed in a JavaFX UI control called ListView.
 The underlying data model used is ObservableList. There are two ListView controls used to move data from one control
 to another, and two ObservableLists represent the data of the underlying model.
 The data not only moves from one control to another visually, but it also moves from one model to another.
 This makes data transition between the view and the model layers synchronous.
*/

public class ObservableListdemo extends Application {

    private final ObservableList<String> countries;
    private final ObservableList<String> capitals;

    private final ListView<String> countriesListView;
    private final ListView<String> capitalsListView;

    private final Button leftButton;
    private final Button rightButton;

    public ObservableListdemo() {
        //instantiate countries with data.
        countries = FXCollections.observableArrayList("Australia",
                "Vienna", "Canberra", "Austria", "Belgium", "Santiago",
                "Chile", "Brussels", "San Jose", "Finland", "India");
        //Or add the data after instance is created.
        countries.add("Italy");
        countries.add("Japan");

        //Create the FX ListView to display the countries
        countriesListView = new ListView<>(countries);

        //Instantiate the capitals in an observableArrayList.
        capitals = FXCollections.observableArrayList("Costa Rica",
                "New Delhi", "Washington DC", "USA", "UK", "London",
                "Helsinki", "Taiwan", "Taipei", "Sweden", "Stockholm");

        //Or, add the individual capitals.
        capitals.add("Rome");
        capitals.add("Tokyo");

        //Create an instance of the FX ListView to display the capitals.
        capitalsListView = new ListView<>(capitals);

        //Create button instances
        leftButton = new Button(" < ");
        leftButton.setOnAction(new ButtonHandler());

        rightButton = new Button(" > ");
        rightButton.setOnAction(new ButtonHandler());

    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Arrange Countries and Capitals");
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 500, 450);

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(5));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        ColumnConstraints column1 = new ColumnConstraints(150, 150,
                Double.MAX_VALUE);
        ColumnConstraints column2 = new ColumnConstraints(50);
        ColumnConstraints column3 = new ColumnConstraints(150, 150,
                Double.MAX_VALUE);
        column1.setHgrow(Priority.ALWAYS);
        column3.setHgrow(Priority.ALWAYS);
        gridPane.getColumnConstraints().addAll(column1, column2, column3);

        Label countriesLabel = new Label("Countries");
        GridPane.setHalignment(countriesLabel, HPos.CENTER);
        gridPane.add(countriesLabel, 0, 0);

        Label capitalsLabel = new Label("Capitals");
        GridPane.setHalignment(capitalsLabel, HPos.CENTER);
        gridPane.add(capitalsLabel, 2, 0);

        gridPane.add(countriesListView, 0, 1);
        gridPane.add(capitalsListView, 2, 1);

        VBox vbox = new VBox();
        vbox.getChildren().addAll(rightButton, leftButton);

        gridPane.add(vbox, 1, 1);

        root.setCenter(gridPane);
        GridPane.setVgrow(root, Priority.ALWAYS);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private class ButtonHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {

            //If the left button is clicked, take the selected item and remove it from the capitals list,
            // and add it to the countries list.
            if (event.getSource().equals(leftButton)) {
                String str = capitalsListView.getSelectionModel()
                        .getSelectedItem();
                if (str != null) {
                    capitals.remove(str);
                    countries.add(str);
                }
            //If the right button is clicked take the string and move it from the countries to the capitals list.
            } else if (event.getSource().equals(rightButton)) {
                String str = countriesListView.getSelectionModel()
                        .getSelectedItem();
                if (str != null) {
                    countriesListView.getSelectionModel().clearSelection();
                    countries.remove(str);
                    capitals.add(str);
                }
            }
        }
    }
}