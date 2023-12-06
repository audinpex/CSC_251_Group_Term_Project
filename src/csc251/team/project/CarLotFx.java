package csc251.team.project;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static javafx.collections.FXCollections.observableArrayList;

public class CarLotFx extends Application {

    private CarDAO model;

    //Shapes

    private Shape carIcon(){
        SVGPath carIcon = new SVGPath();
        carIcon.setContent("M240-200v40q0 17-11.5 28.5T200-120h-40q-17 0-28.5-11.5T120-160v-320l84-240q6-18 " +
                "21.5-29t34.5-11h440q19 0 34.5 11t21.5 29l84 240v320q0 17-11.5 28.5T800-120h-40q-17 " +
                "0-28.5-11.5T720-160v-40H240Zm-8-360h496l-42-120H274l-42 120Zm-32 80v200-200Zm100 160q25 0 " +
                "42.5-17.5T360-380q0-25-17.5-42.5T300-440q-25 0-42.5 17.5T240-380q0 25 17.5 42.5T300-320Zm360 0q25 " +
                "0 42.5-17.5T720-380q0-25-17.5-42.5T660-440q-25 0-42.5 17.5T600-380q0 25 17.5 42.5T660-320Zm-460 " +
                "40h560v-200H200v200Z");
        carIcon.setFill(Color.web("#003351"));
        carIcon.setScaleX(0.15);
        carIcon.setScaleY(0.15);

        return carIcon;
    }

    //Creates a hamburger icon for navigation.
    private Shape hamburgerIcon(VBox navigationMenu){
        SVGPath hamburgerIcon = new SVGPath();
        hamburgerIcon.setContent("M120-240v-80h720v80H120Zm0-200v-80h720v80H120Zm0-200v-80h720v80H120Z");
        hamburgerIcon.setFill(Color.web("#187DB9"));
        hamburgerIcon.setScaleX(0.05);
        hamburgerIcon.setScaleY(0.05);

        //Define hamburger icon behaviour
        hamburgerIcon.setOnMouseClicked( e -> navigationMenu.setVisible(!navigationMenu.isVisible()));
        hamburgerIcon.setOnMouseEntered( e -> hamburgerIcon.setFill(Color.web("#003351")));
        hamburgerIcon.setOnMouseExited( e -> hamburgerIcon.setFill(Color.web("#00b5e2")));

        return hamburgerIcon;
    }

    private Shape homeIcon(AnchorPane root){
        SVGPath homeIcon = new SVGPath();
        homeIcon.setContent("M 240 -200 h 120 v -240 h 240 v 240 h 120 v -360 L 480 -740 L 240 " +
                "-560 v 360 Z m -80 80 v -480 l 320 -240 l 320 240 v 480 H 520 v -240 h -80 v 240 H " +
                "160 Z m 320 -350 Z");
        homeIcon.setFill(Color.web("#333"));
        homeIcon.setScaleX(0.05);
        homeIcon.setScaleY(0.05);
        homeIcon.setOnMouseEntered( e -> { homeIcon.setFill(Color.web("#999"));} );
        homeIcon.setOnMouseExited( e -> { homeIcon.setFill(Color.web("#333")); } );
        homeIcon.setOnMouseClicked( e -> { returnHome(root); root.toBack();});

        return homeIcon;
    }

    //Creates a backward facing arrow image which returns the user to the home pane.
    private Shape navigationIcon(AnchorPane root, BorderPane borderPane){
        SVGPath navigationIcon = new SVGPath();
        navigationIcon.setContent("m313-440 224 224-57 56-320-320 320-320 57 56-224 224h487v80H313Z");
        navigationIcon.setFill(Color.web("#333"));
        navigationIcon.setScaleX(0.05);
        navigationIcon.setScaleY(0.05);
        navigationIcon.setOnMouseEntered( e -> { navigationIcon.setFill(Color.web("#999"));} );
        navigationIcon.setOnMouseExited( e -> { navigationIcon.setFill(Color.web("#333")); } );
        navigationIcon.setOnMouseClicked( e -> { root.getChildren().remove(borderPane); } );

        return navigationIcon;
    }

    //Creates a magnifying glass image for use with the inventory card.
    private Shape searchIcon(){
        SVGPath searchIcon = new SVGPath();
        searchIcon.setContent("M 15.5 14 h -0.79 l -0.28 -0.27 C 15.41 12.59 16 11.11 16 9.5 C 16 5.91 13.09 3 9.5 " +
                "3 S 3 5.91 3 9.5 S 5.91 16 9.5 16 c 1.61 0 3.09 -0.59 4.23 -1.57 l 0.27 0.28 v 0.79 l 5 4.99 L " +
                "20.49 19 l -4.99 -5 Z m -6 0 C 7.01 14 5 11.99 5 9.5 S 7.01 5 9.5 5 S 14 7.01 14 9.5 S 11.99 14 " +
                "9.5 14 Z");
        searchIcon.setFill(Color.web("#003351"));
        searchIcon.setScaleX(5.0);
        searchIcon.setScaleY(5.0);

        return searchIcon;
    }

    //Creates a sell tag image for use with the sell card.
    private Shape sellIcon(){
        SVGPath sellIcon = new SVGPath();
        sellIcon.setContent("M 21.41 11.41 l -8.83 -8.83 C 12.21 2.21 11.7 2 11.17 2 H 4 C 2.9 2 2 2.9 2 4 v " +
                "7.17 c 0 0.53 0.21 1.04 0.59 1.41 l 8.83 8.83 c 0.78 0.78 2.05 0.78 2.83 0 l 7.17 -7.17 C 22.2 " +
                "13.46 22.2 12.2 21.41 11.41 Z M 6.5 8 C 5.67 8 5 7.33 5 6.5 S 5.67 5 6.5 5 S 8 5.67 8 6.5 S 7.33 " +
                "8 6.5 8 Z");
        sellIcon.setFill(Color.web("#003351"));
        sellIcon.setScaleX(4.0);
        sellIcon.setScaleY(4.0);

        return sellIcon;
    }

    //Creates a history image for the history card.
    private Shape historyIcon(){
        SVGPath historyIcon = new SVGPath();
        historyIcon.setContent("M 13 3 c -4.97 0 -9 4.03 -9 9 H 1 l 3.89 3.89 l 0.07 0.14 L 9 12 H 6 c 0 -3.87 " +
                "3.13 -7 7 -7 s 7 3.13 7 7 s -3.13 7 -7 7 c -1.93 0 -3.68 -0.79 -4.94 -2.06 l -1.42 1.42 C" +
                " 8.27 19.99 10.51 21 13 21 c 4.97 0 9 -4.03 9 -9 s -4.03 -9 -9 -9 Z m -1 5 v 5 l 4.28 " +
                "2.54 l 0.72 -1.21 l -3.5 -2.08 V 8 H 12 Z");
        historyIcon.setFill(Color.web("#003351"));
        historyIcon.setScaleX(4.5);
        historyIcon.setScaleY(4.5);

        return historyIcon;
    }

    private Shape comparisonIcon(){
        SVGPath comparisonIcon = new SVGPath();
        comparisonIcon.setContent("M9.01,14H2v2h7.01v3L13,15l-3.99-4V14z M14.99,13v-3H22V8h-7.01V5L11,9L14.99,13z");
        comparisonIcon.setFill(Color.web("#003351"));
        comparisonIcon.setScaleX(5.0);
        comparisonIcon.setScaleY(5.0);

        return comparisonIcon;
    }

    private Shape gasIcon(){
        SVGPath gasIcon = new SVGPath();
        gasIcon.setContent("M 160 -120 v -640 q 0 -33 23.5 -56.5 T 240 -840 h 240 q 33 0 56.5 23.5 T 560 -760" +
                " v 280 h 40 q 33 0 56.5 23.5 T 680 -400 v 180 q 0 17 11.5 28.5 T 720 -180 q 17 0 28.5 -11.5 T " +
                "760 -220 v -288 q -9 5 -19 6.5 t -21 1.5 q -42 0 -71 -29 t -29 -71 q 0 -32 17.5 -57.5 T 684 -694 " +
                "l -84 -84 l 42 -42 l 148 144 q 15 15 22.5 35 t 7.5 41 v 380 q 0 42 -29 71 t -71 29 q -42 0 -71 -29 " +
                "t -29 -71 v -200 h -60 v 300 H 160 Z m 80 -440 h 240 v -200 H 240 v 200 Z m 480 0 q 17 0 28.5 -11.5 " +
                "T 760 -600 q 0 -17 -11.5 -28.5 T 720 -640 q -17 0 -28.5 11.5 T 680 -600 q 0 17 11.5 28.5 T 720 -560 " +
                "Z M 240 -200 h 240 v -280 H 240 v 280 Z m 240 0 H 240 h 240 Z");
        gasIcon.setFill(Color.web("#003b5c"));
        gasIcon.setScaleX(0.1);
        gasIcon.setScaleY(0.1);

        return gasIcon;
    }

    private Shape distanceIcon(){
        SVGPath distanceIcon = new SVGPath();
        distanceIcon.setContent("M120-240q-33 0-56.5-23.5T40-320q0-33 23.5-56.5T120-400h10.5q4.5 0 9.5 " +
                "2l182-182q-2-5-2-9.5V-600q0-33 23.5-56.5T400-680q33 0 56.5 23.5T480-600q0 2-2 20l102 102q5-2 " +
                "9.5-2h21q4.5 0 9.5 2l142-142q-2-5-2-9.5V-640q0-33 23.5-56.5T840-720q33 0 56.5 23.5T920-640q0 " +
                "33-23.5 56.5T840-560h-10.5q-4.5 0-9.5-2L678-420q2 5 2 9.5v10.5q0 33-23.5 56.5T600-320q-33 " +
                "0-56.5-23.5T520-400v-10.5q0-4.5 2-9.5L420-522q-5 2-9.5 2H400q-2 0-20-2L198-340q2 5 2 9.5v10.5q0 " +
                "33-23.5 56.5T120-240Z");
        distanceIcon.setFill(Color.web("#003351"));
        distanceIcon.setScaleX(0.1);
        distanceIcon.setScaleY(0.1);


        return distanceIcon;
    }

    private Shape savingsIcon(){
        SVGPath savingsIcon = new SVGPath();
        savingsIcon.setContent("M640-520q17 0 28.5-11.5T680-560q0-17-11.5-28.5T640-600q-17 0-28.5 11.5T600-560q0 " +
                "17 11.5 28.5T640-520Zm-320-80h200v-80H320v80ZM180-120q-34-114-67-227.5T80-580q0-92 " +
                "64-156t156-64h200q29-38 70.5-59t89.5-21q25 0 42.5 17.5T720-820q0 6-1.5 12t-3.5 11q-4 " +
                "11-7.5 22.5T702-751l91 91h87v279l-113 37-67 224H480v-80h-80v80H180Zm60-80h80v-80h240v80h80l62-206 " +
                "98-33v-141h-40L620-720q0-20 2.5-38.5T630-796q-29 8-51 27.5T547-720H300q-58 0-99 41t-41 99q0 98 27 " +
                "191.5T240-200Zm240-298Z");
        savingsIcon.setFill(Color.web("#003351"));
        savingsIcon.setScaleX(0.1);
        savingsIcon.setScaleY(0.1);

        return savingsIcon;

    }

    //Objects
    //Template for button creation which attempts to follow material 3 standards.
    private Button materialButton(String str){
        Button button = new Button(" ".repeat(4).concat(str));
        button.getStylesheets().add("csc251/team/project/ControlStyle.css");
        button.getStyleClass().add("button");
        button.setAlignment(Pos.BASELINE_LEFT);

        return button;
    }

    //Template for creating cards.
    private VBox menuCard(String str, Shape icon){
        VBox menuCard = new VBox();
        menuCard.getStylesheets().add("csc251/team/project/ControlStyle.css");
        menuCard.getStyleClass().add("menu-card");
        menuCard.setMinWidth(250);
        menuCard.setMinHeight(180);
        menuCard.setSpacing(30);
        menuCard.setPadding(new Insets(10));
        Font cardFont = Font.font("San Francisco", FontWeight.EXTRA_BOLD, 24);
        Group image = new Group(icon);
        Label cardTitle = new Label(str);
        cardTitle.setFont(cardFont);
        menuCard.getChildren().addAll(image, cardTitle);
        menuCard.setAlignment(Pos.BOTTOM_CENTER);

        return menuCard ;
    }


    //Creates card for best section.
    private StackPane bestCard(String str, Shape icon, Car car){
        //Style the Stack Pane which will hold an vbox for text and image.
        StackPane card = new StackPane();
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(10));
        card.setMaxSize(200, 310);
        card.setMinSize(200, 310);
        card.getStylesheets().add("csc251/team/project/ControlStyle.css");
        card.getStyleClass().add("stat-card");

        //Set image VBox attributes.
        VBox imageVBox = new VBox();
        imageVBox.setAlignment(Pos.CENTER);
        Group imageGroup = new Group();
        imageGroup.getChildren().add(icon);
        imageVBox.getChildren().add(imageGroup);

        //Set text Vbox attributes.
        VBox titleVBox = new VBox();
        titleVBox.setAlignment(Pos.CENTER);
        Font cardFont = Font.font("San Francisco", FontWeight.EXTRA_BOLD, 24);
        Label titleLabel = new Label(str);
        titleLabel.setFont(cardFont);
        Shape line = new Line();
        line.setStroke(Color.web("#333"));
        line.setStrokeLineCap(StrokeLineCap.SQUARE);
        line.setScaleX(160);
        titleVBox.getChildren().addAll(titleLabel, line);

        VBox contentVBox = new VBox();
        contentVBox.setMaxSize(160, 120);
        contentVBox.setMinSize(160, 120);
        HBox carIdHBox =  bestLabelHBox("ID:", car.getId());
        HBox milesHBox =  bestLabelHBox("Mileage:", "" + car.getMileage());
        HBox mpgHBox =  bestLabelHBox("MPG:" , "" + car.getMpg());
        HBox priceHBox =  bestLabelHBox("Price:", "$" + Math.round(car.getSalesPrice()));
        contentVBox.getChildren().addAll(carIdHBox, milesHBox, mpgHBox,priceHBox);
        contentVBox.setAlignment(Pos.BOTTOM_CENTER);

        //Add the text and image vboxes to  the stack pane
        card.setAlignment(Pos.CENTER);
        card.getChildren().addAll(titleVBox, imageVBox);
        titleVBox.setTranslateY(-20);
        imageVBox.setTranslateY(-100);
        card.setAlignment(Pos.BOTTOM_CENTER);
        card.getChildren().add(contentVBox);

        return card;
    }

    private HBox bestLabelHBox(String str, String str2){
        //Create main content HBox
        HBox contentHBox = new HBox();
        contentHBox.setMaxSize(160, 30);
        contentHBox.setMinSize(160, 30);

        //Create and Style Labels
        Font labelFont = Font.font("San Francisco", FontWeight.EXTRA_BOLD, 18);

        Label titleLabel =  new Label(str);
        titleLabel.setFont(labelFont);
        Label dataLabel = new Label(str2);
        dataLabel.setFont(labelFont);

        //Add Labels to HBox node.
        HBox leftHbox = new HBox(titleLabel);
        leftHbox.setMaxSize(80, 50);
        leftHbox.setMinSize(80, 50);
        HBox rightHBox = new HBox(dataLabel);
        rightHBox.setMaxSize(80, 50);
        rightHBox.setMinSize(80, 50);
        rightHBox.setAlignment(Pos.BASELINE_RIGHT);

        //Add right and left HBox to contentHBox
        contentHBox.getChildren().addAll(leftHbox,rightHBox);

        return contentHBox;
    };

    private HBox controlsHBox(AnchorPane root, BorderPane borderPane){
        HBox controlsHBox = new HBox();
        controlsHBox.setMinSize(150, 100);
        controlsHBox.setMaxSize(150, 100);
        controlsHBox.setSpacing(30);
        Shape navigationIcon = navigationIcon(root, borderPane);
        Shape homeIcon = homeIcon(root);
        Group navigationGroup = new Group();
        navigationGroup.getChildren().add(navigationIcon);
        Group homeGroup =  new Group();
        homeGroup.getChildren().add(homeIcon);
        controlsHBox.getChildren().addAll(navigationGroup, homeGroup);
        controlsHBox.setAlignment(Pos.CENTER_LEFT);
        controlsHBox.setTranslateY(-2);
        controlsHBox.setTranslateX(10);

        return controlsHBox;
    }

    private ComboBox<String> comboBoxTemplate(CarDAO model){
        final ComboBox<String> comboBox = new ComboBox<String>();
        comboBox.setMinSize(180, 40);
        comboBox.setMaxSize(180, 40);
        comboBox.setEditable(true);
        comboBox.setPromptText("Select All boxes");
        comboBox.getStylesheets().add("csc251/team/project/ControlStyle.css");
        comboBox.getStyleClass().add("combo-box");

        List<Car> inventory = model.getInventory();

        List<String> carIds = inventory.stream()
                .map(Car::getId)
                .collect(Collectors.toList());

        ObservableList<String> options = observableArrayList(carIds);
        comboBox.setItems(options);

        return comboBox;
    }

    private ComboBox<String> formComboBoxTemplate(CarDAO model){
        final ComboBox<String> comboBox = new ComboBox<String>();
        comboBox.setMinSize(180, 40);
        comboBox.setMaxSize(180, 40);
        comboBox.setEditable(true);
        comboBox.setPromptText("Select All boxes");
        comboBox.getStylesheets().add("csc251/team/project/ControlStyle.css");
        comboBox.getStyleClass().add("combo-box");

        List<Car> inventory = model.getSalesHistory();

        List<String> carIds = inventory.stream()
                .map(Car::getId)
                .collect(Collectors.toList());

        ObservableList<String> options = observableArrayList(carIds);
        comboBox.setItems(options);

        return comboBox;
    }


    private HBox purchaseLabelHBox(String str, String str2){
        //Create main content HBox
        HBox contentHBox = new HBox();
        contentHBox.setMaxSize(200, 50);
        contentHBox.setMinSize(200, 50);

        //Create and Style Labels
        Font labelFont = Font.font("San Francisco", FontWeight.EXTRA_BOLD, 18);

        Label titleLabel =  new Label(str);
        titleLabel.setFont(labelFont);
        Label dataLabel = new Label(str2);
        dataLabel.setFont(labelFont);


        //Add Labels to HBox node.
        HBox leftHbox = new HBox(titleLabel);
        leftHbox.setMaxSize(100, 50);
        leftHbox.setMinSize(100, 50);
        leftHbox.setAlignment(Pos.BOTTOM_LEFT);
        HBox rightHBox = new HBox(dataLabel);
        rightHBox.setMaxSize(100, 50);
        rightHBox.setMinSize(100, 50);
        rightHBox.setAlignment(Pos.BOTTOM_RIGHT);

        //Add right and left HBox to contentHBox
        contentHBox.getChildren().addAll(leftHbox,rightHBox);

        return contentHBox;
    };

    private VBox purchaseCard(Car car, CarDAO model, AnchorPane root, BorderPane borderPane){
        //Create VBox Container.
        VBox purchaseCard = new VBox();
        purchaseCard.getStylesheets().add("csc251/team/project/ControlStyle.css");
        purchaseCard.getStyleClass().add("purchase-card");
        purchaseCard.setMaxSize(340, 440);
        purchaseCard.setMinSize(340, 440);
        purchaseCard.setTranslateY(-20);
        purchaseCard.setAlignment(Pos.CENTER);
        purchaseCard.setSpacing(20);

        //Create car Icon and line.
        Shape carIcon = carIcon();
        Group carImageGroup = new Group(carIcon);
        purchaseCard.getChildren().addAll(carImageGroup);

        //Create Label VBox.
        VBox contentVBox = new VBox();
        contentVBox.setMaxSize(200, 200);
        contentVBox.setMinSize(200, 200);
        HBox carIdHBox = purchaseLabelHBox("ID:", car.getId());
        HBox milesHBox = purchaseLabelHBox("Mileage:", "" + car.getMileage());
        HBox mpgHBox = purchaseLabelHBox("MPG:", "" + car.getMpg());
        HBox priceHBox = purchaseLabelHBox("Price:", " $" + Math.round(car.getSalesPrice()) );
        contentVBox.getChildren().addAll(carIdHBox, milesHBox,mpgHBox,priceHBox);
        purchaseCard.getChildren().add(contentVBox);

        //Create Line
        Line line = new Line();
        line.setStroke(Color.web("#333"));
        line.setStrokeLineCap(StrokeLineCap.SQUARE);
        line.setScaleX(200);

        //Create confirmation button and add it to vbox.
        Button confirmationButton = new Button("Confirm Purchase");
        confirmationButton.getStylesheets().add("csc251/team/project/ControlStyle.css");
        confirmationButton.getStyleClass().add("confirmation-button");
        confirmationButton.setMaxSize(200, 60);
        confirmationButton.setMinSize(200, 60);
        purchaseCard.getChildren().addAll(line, confirmationButton);

        confirmationButton.setOnMouseClicked(e -> {model.sellCar(car.getId(), ((float)car.getSalesPrice()));
            root.getChildren().remove(borderPane);
        });

        return purchaseCard;
    }

    private TableView<Car> customerTableView(){
        TableView<Car> table = new TableView<>();
        TableColumn<Car, String> idColumn = new TableColumn<>("CarId");
        idColumn.setCellValueFactory(new PropertyValueFactory<Car, String>("id"));
        TableColumn<Car, Integer> mileageColumn = new TableColumn<>("Mileage");
        mileageColumn.setCellValueFactory(new PropertyValueFactory<Car, Integer>("mileage"));
        TableColumn<Car,Integer> mpgColumn = new TableColumn<>("Mpg");
        mpgColumn.setCellValueFactory(new PropertyValueFactory<Car, Integer>("mpg"));
        TableColumn<Car, Double> salesPriceColumn = new TableColumn<>("Sales Price");
        salesPriceColumn.setCellValueFactory(new PropertyValueFactory<Car, Double>("salesPrice"));
        table.getStylesheets().add("csc251/team/project/ControlStyle.css");
        table.getStyleClass().add("table-view");
        table.setMaxSize(400, 300);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.getColumns().addAll(idColumn, mileageColumn, mpgColumn, salesPriceColumn);

        return table;
    }

    private TableView<Car> historyTableView(){
        TableView<Car> table = new TableView<>();
        TableColumn<Car, String> idColumn = new TableColumn<>("CarId");
        idColumn.setCellValueFactory(new PropertyValueFactory<Car, String>("id"));
        TableColumn<Car, Integer> mileageColumn = new TableColumn<>("Mileage");
        mileageColumn.setCellValueFactory(new PropertyValueFactory<Car, Integer>("mileage"));
        TableColumn<Car,Integer> mpgColumn = new TableColumn<>("Mpg");
        mpgColumn.setCellValueFactory(new PropertyValueFactory<Car, Integer>("mpg"));
        TableColumn<Car, Double> costPriceColumn = new TableColumn<>("Cost");
        costPriceColumn.setCellValueFactory(new PropertyValueFactory<Car, Double>("cost"));
        TableColumn<Car, Double> salesPriceColumn = new TableColumn<>("Sales Price");
        salesPriceColumn.setCellValueFactory(new PropertyValueFactory<Car, Double>("salesPrice"));
        TableColumn<Car, String> soldColumn = new TableColumn<>("Sold");
        soldColumn.setCellValueFactory(new PropertyValueFactory<Car, String>("sold"));
        TableColumn<Car, String> priceSoldColumn = new TableColumn<>("PriceSold");
        priceSoldColumn.setCellValueFactory(new PropertyValueFactory<Car, String>("PriceSold"));
        table.getStylesheets().add("csc251/team/project/ControlStyle.css");
        table.getStyleClass().add("table-view");
        table.setMinSize(700, 300);
        table.setMaxSize(700, 300);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.getColumns().addAll(idColumn, mileageColumn, mpgColumn, costPriceColumn,
                salesPriceColumn, soldColumn, priceSoldColumn);

        return table;
    }

    private BorderPane toggleBox(ToggleButton... buttons){
        //Create Border Pane for button
        BorderPane toggleButtonBox= new BorderPane();
        toggleButtonBox.getStylesheets().add("csc251/team/project/ControlStyle.css");
        toggleButtonBox.getStyleClass().add("toggle-box");
        buttons[0].getStyleClass().add("toggle-button");
        buttons[1].getStyleClass().add("toggle-button");

        toggleButtonBox.setLeft(buttons[0]);
        toggleButtonBox.setCenter(null);
        toggleButtonBox.setRight(buttons[1]);
        toggleButtonBox.setMinSize(50, 400);
        toggleButtonBox.setMaxSize(50, 400);
        BorderPane.setAlignment(buttons[0], Pos.CENTER);
        BorderPane.setAlignment(buttons[1], Pos.CENTER);

        return toggleButtonBox;
    }

    private BorderPane formToggleBox(ToggleButton... buttons){
        //Create Border Pane for button
        BorderPane formToggleBox= new BorderPane();
        formToggleBox.getStylesheets().add("csc251/team/project/ControlStyle.css");
        formToggleBox.getStyleClass().add("segmented-button-bar");
        buttons[0].setMaxSize(100,40);
        buttons[1].setMaxSize(100,40);
        buttons[2].setMaxSize(100,40);

        formToggleBox.setLeft(buttons[0]);
        formToggleBox.setCenter(buttons[1]);
        formToggleBox.setRight(buttons[2]);
        formToggleBox.setMinSize(300, 40);
        formToggleBox.setMaxSize(300, 40);
        BorderPane.setAlignment(buttons[0], Pos.CENTER);
        BorderPane.setAlignment(buttons[1], Pos.CENTER);
        BorderPane.setAlignment(buttons[2], Pos.CENTER);

        return formToggleBox;
    }

    private void returnHome(AnchorPane root) {
        int childNodes = root.getChildren().size();
        if (childNodes > 2) {
            for (int i = childNodes; i > 2; i--) {
                root.getChildren().remove(1);
            }
        }
    }

    private Boolean validateSelection(ComboBox<String> comboBox, CarDAO model){
        List<String> carIds = model.getInventory().stream()
                .map(Car::getId)
                .toList();

        return carIds.contains(comboBox.getValue());
    }

    private Boolean validateSelectionTextField(String str, CarDAO model){
        return model.getInventory().stream()
                .anyMatch(car -> car.toString().contains(str));
    }

    private Boolean validateSoldSelection(ComboBox<String> comboBox, CarDAO model){
        List<String> carIds = model.getSalesHistory().stream()
                .map(Car::getId)
                .toList();

        return carIds.contains(comboBox.getValue());
    }

    private Boolean validateSoldSelectionTextField(String str, CarDAO model){
        return  model.getSalesHistory().stream()
                .anyMatch(car -> car.toString().contains(str));
    }

    private Boolean checkValues(String id, String mileage, String mpg, String salesPrice, String cost) {

        try {
            Boolean idCheck = id instanceof String && !id.isEmpty();
            Integer mileageCheck = Integer.parseInt(mileage);
            Integer mpgCheck = Integer.parseInt(mpg);
            Double salesPriceCheck = Double.parseDouble(salesPrice);
            Double costCheck = Double.parseDouble(cost);
            return true;
        } catch (NumberFormatException exception) {
            return false;
        }
    }

    private Boolean checkSoldValues(String id, String mileage, String mpg, String salesPrice, String cost,
                                    String sold, String priceSold) {

        try {
            Boolean idCheck = id instanceof String && !id.isEmpty();
            Integer mileageCheck = Integer.parseInt(mileage);
            Integer mpgCheck = Integer.parseInt(mpg);
            Double salesPriceCheck = Double.parseDouble(salesPrice);
            Double costCheck = Double.parseDouble(cost);
            Boolean soldCheck = Boolean.parseBoolean(sold);
            Double priceSoldCheck = Double.parseDouble(priceSold);

            return true;
        } catch (NumberFormatException exception) {
            return false;
        }
    }


    private VBox formVBox(ComboBox<String> comboBox, CarDAO model){
        VBox fieldsVBox = new VBox();
        fieldsVBox.setMaxSize(400, 300);
        fieldsVBox.setMinSize(400, 300);
        fieldsVBox.setAlignment(Pos.CENTER);
        fieldsVBox.setSpacing(20);

        ToggleGroup formToggleGroup = new ToggleGroup();
        ToggleButton addButton = new ToggleButton("Add");
        ToggleButton editButton = new ToggleButton("Edit");
        ToggleButton removeButton = new ToggleButton("Remove");
        addButton.setToggleGroup(formToggleGroup);
        editButton.setToggleGroup(formToggleGroup);
        removeButton.setToggleGroup(formToggleGroup);
        formToggleGroup.selectToggle(addButton);
        BorderPane compareToggleBox = formToggleBox(addButton, editButton, removeButton);

        TextField idTextField = new TextField();
        idTextField.setPromptText("Enter Car Id");
        idTextField.getStylesheets().add("csc251/team/project/ControlStyle.css");
        idTextField.getStyleClass().add("search-field");
        TextField mileageTextField  = new TextField();
        mileageTextField.setPromptText("Enter Mileage");
        mileageTextField.getStylesheets().add("csc251/team/project/ControlStyle.css");
        mileageTextField.getStyleClass().add("search-field");
        TextField mpgTextField  = new TextField();
        mpgTextField.setPromptText("Enter MPG");
        mpgTextField.getStylesheets().add("csc251/team/project/ControlStyle.css");
        mpgTextField.getStyleClass().add("search-field");
        TextField salesPriceTextField  = new TextField();
        salesPriceTextField.setPromptText("Price");
        salesPriceTextField.getStylesheets().add("csc251/team/project/ControlStyle.css");
        salesPriceTextField.getStyleClass().add("search-field");
        TextField costTextField  = new TextField();
        costTextField.setPromptText("Price");
        costTextField.getStylesheets().add("csc251/team/project/ControlStyle.css");
        costTextField.getStyleClass().add("search-field");

        comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (validateSelection(comboBox, model)){
                Car selectedCar = model.getInventory().stream()
                        .filter(car -> car.getId().equals(comboBox.getValue()))
                        .findFirst().get();

                idTextField.setText(selectedCar.getId());
                mileageTextField.setText(selectedCar.getMileage() + "");
                mpgTextField.setText(selectedCar.getMpg()+ "");
                salesPriceTextField.setText(selectedCar.getSalesPrice() + "");
                costTextField.setText(selectedCar.getCost() + "");
            }

        });

        Button savebutton = new Button("Save Changes");
        savebutton.getStylesheets().add("csc251/team/project/ControlStyle.css");
        savebutton.getStyleClass().add("confirmation-button");
        savebutton.setMaxSize(40, 300);


        fieldsVBox.getChildren().addAll(compareToggleBox, idTextField,
                mileageTextField, mpgTextField, salesPriceTextField, costTextField, savebutton);

        savebutton.setOnMouseClicked(e -> {
            Boolean valuesCheck = checkValues(idTextField.getText(), mileageTextField.getText(), mpgTextField.getText(),
                    salesPriceTextField.getText(), costTextField.getText());
            if (valuesCheck){
                if (editButton.isSelected() && validateSelectionTextField(idTextField.getText(), model)){
                    model.updateCar(idTextField.getText(), Integer.parseInt(mileageTextField.getText()),
                            Integer.parseInt(mpgTextField.getText()), Double.parseDouble(salesPriceTextField.getText()),
                            Double.parseDouble(costTextField.getText()));

                } else if (removeButton.isSelected() && validateSelectionTextField(idTextField.getText(), model)){
                    model.removeCar(idTextField.getText());

                } else{
                    Car newcar = new Car(idTextField.getText(), Integer.parseInt(mileageTextField.getText()),
                            Integer.parseInt(mpgTextField.getText()), Double.parseDouble(salesPriceTextField.getText()),
                            Double.parseDouble(costTextField.getText()));
                    model.addCarToLot(newcar);
                }
            }

        });

        return fieldsVBox;

    }

    private VBox historyFormVBox(ComboBox<String> comboBox, CarDAO model){
        VBox fieldsVBox = new VBox();
        fieldsVBox.setMaxSize(400, 300);
        fieldsVBox.setMinSize(400, 300);
        fieldsVBox.setAlignment(Pos.CENTER);
        fieldsVBox.setSpacing(10);

        ToggleGroup formToggleGroup = new ToggleGroup();
        ToggleButton addButton = new ToggleButton("Add");
        ToggleButton editButton = new ToggleButton("Edit");
        ToggleButton removeButton = new ToggleButton("Remove");
        addButton.setToggleGroup(formToggleGroup);
        editButton.setToggleGroup(formToggleGroup);
        removeButton.setToggleGroup(formToggleGroup);
        formToggleGroup.selectToggle(addButton);
        BorderPane compareToggleBox = formToggleBox(addButton, editButton, removeButton);

        TextField idTextField = new TextField();
        idTextField.setPromptText("Enter Car Id");
        idTextField.getStylesheets().add("csc251/team/project/ControlStyle.css");
        idTextField.getStyleClass().add("search-field");
        TextField mileageTextField  = new TextField();
        mileageTextField.setPromptText("Enter Mileage");
        mileageTextField.getStylesheets().add("csc251/team/project/ControlStyle.css");
        mileageTextField.getStyleClass().add("search-field");
        TextField mpgTextField  = new TextField();
        mpgTextField.setPromptText("Enter MPG");
        mpgTextField.getStylesheets().add("csc251/team/project/ControlStyle.css");
        mpgTextField.getStyleClass().add("search-field");
        TextField costTextField  = new TextField();
        costTextField.setPromptText("Cost Price");
        costTextField.getStylesheets().add("csc251/team/project/ControlStyle.css");
        costTextField.getStyleClass().add("search-field");
        TextField salesPriceTextField  = new TextField();
        salesPriceTextField.setPromptText("Sales Price");
        salesPriceTextField.getStylesheets().add("csc251/team/project/ControlStyle.css");
        salesPriceTextField.getStyleClass().add("search-field");
        TextField soldTextField  = new TextField();
        soldTextField.setPromptText("Sold - true. Unsold False");
        soldTextField.getStylesheets().add("csc251/team/project/ControlStyle.css");
        soldTextField.getStyleClass().add("search-field");
        TextField priceSoldTextField  = new TextField();
        priceSoldTextField.setPromptText("Enter Price Sold");
        priceSoldTextField.getStylesheets().add("csc251/team/project/ControlStyle.css");
        priceSoldTextField.getStyleClass().add("search-field");

        comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (validateSoldSelection(comboBox, model)){
                Car selectedCar = model.getSalesHistory().stream()
                        .filter(car -> car.getId().equals(comboBox.getValue()))
                        .findFirst().get();

                idTextField.setText(selectedCar.getId());
                mileageTextField.setText(selectedCar.getMileage() + "");
                mpgTextField.setText(selectedCar.getMpg()+ "");
                costTextField.setText(selectedCar.getCost() + "");
                salesPriceTextField.setText(selectedCar.getSalesPrice() + "");
                soldTextField.setText(selectedCar.isSold() + "");
                priceSoldTextField.setText(selectedCar.getPriceSold() + "");
            }

        });

        Button savebutton = new Button("Save Changes");
        savebutton.getStylesheets().add("csc251/team/project/ControlStyle.css");
        savebutton.getStyleClass().add("confirmation-button");
        savebutton.setMaxSize(40, 300);

        HBox firstHBox = new HBox();
        firstHBox.setSpacing(40);
        firstHBox.setAlignment(Pos.BOTTOM_CENTER);
        firstHBox.setMaxSize(400, 60);
        firstHBox.setMinSize(400, 60);
        HBox secondHBox = new HBox();
        secondHBox.setMaxSize(400, 60);
        secondHBox.setMinSize(400, 60);
        secondHBox.setSpacing(40);
        secondHBox.setAlignment(Pos.BOTTOM_CENTER);
        HBox thirdHBox = new HBox();
        thirdHBox.setMaxSize(400, 60);
        thirdHBox.setMinSize(400, 60);
        thirdHBox.setSpacing(40);
        thirdHBox.setAlignment(Pos.BOTTOM_CENTER);
        HBox fourthHBox = new HBox();
        fourthHBox.setMaxSize(400, 60);
        fourthHBox.setMinSize(400, 60);
        fourthHBox.setSpacing(40);
        fourthHBox.setAlignment(Pos.BOTTOM_CENTER);

        firstHBox.getChildren().addAll(idTextField, mileageTextField);
        secondHBox.getChildren().addAll(mpgTextField, salesPriceTextField);
        thirdHBox.getChildren().addAll(costTextField, soldTextField);
        fourthHBox.getChildren().addAll(priceSoldTextField, savebutton);

        savebutton.setTranslateY(10);
        fieldsVBox.getChildren().addAll(compareToggleBox,firstHBox,secondHBox,thirdHBox,fourthHBox, savebutton);

        savebutton.setOnMouseClicked(e -> {
            Boolean valuesCheck = checkSoldValues(idTextField.getText(), mileageTextField.getText(), mpgTextField.getText(),
                    salesPriceTextField.getText(), costTextField.getText(), soldTextField.getText(),
                    priceSoldTextField.getText());
            if (valuesCheck){
                if (editButton.isSelected() && validateSoldSelectionTextField(idTextField.getText(), model)){
                    model.updateCar(idTextField.getText(), Integer.parseInt(mileageTextField.getText()),
                            Integer.parseInt(mpgTextField.getText()), Double.parseDouble(salesPriceTextField.getText()),
                            Double.parseDouble(costTextField.getText()), Boolean.parseBoolean(soldTextField.getText()),
                            Double.parseDouble(priceSoldTextField.getText()));

                } else if (removeButton.isSelected() && validateSoldSelectionTextField(idTextField.getText(), model)){
                    model.removeCar(idTextField.getText());
                } else{
                    Car newcar = new Car(idTextField.getText(), Integer.parseInt(mileageTextField.getText()),
                            Integer.parseInt(mpgTextField.getText()), Double.parseDouble(salesPriceTextField.getText()),
                            Double.parseDouble(costTextField.getText()),Boolean.parseBoolean(soldTextField.getText()),
                            Double.parseDouble(priceSoldTextField.getText()));
                    System.out.println(newcar.toCSVString());
                    model.addCarToLot(newcar);
                }
            }

        });

        return fieldsVBox;

    }


    //Creates the compare screen.
    private void compareBorderPane(AnchorPane root, CarDAO model){
        //Create Border Pane.
        BorderPane compareBorderPane = new BorderPane();
        compareBorderPane.setStyle("-fx-background-color: whitesmoke");
        compareBorderPane.setMinSize(800, 600);
        compareBorderPane.setMaxSize(800, 600);

        //Create HBox that will be placed at the top of the Pane.
        HBox topHBox = new HBox();
        topHBox.setMinSize(800, 100);
        topHBox.setMaxSize(800, 100);

        //Child HBox to add Padding to HBox.
        HBox leftPaddingHBox = new HBox();
        leftPaddingHBox.setMinSize(150, 100);
        leftPaddingHBox.setMaxSize(150, 100);

        //Combo button HBox holder. Child to topHBox
        HBox combobuttonHBox = new HBox();
        combobuttonHBox.setMinSize(500, 100);
        combobuttonHBox.setMaxSize(500, 100);
        combobuttonHBox.setSpacing(80);
        combobuttonHBox.getStylesheets().add("csc251/team/project/ControlStyle.css");

        //Create Combo Buttons
        ComboBox<String> firstComboBox = comboBoxTemplate(model);
        ComboBox<String> secondComboBox = comboBoxTemplate(model);

        combobuttonHBox.getChildren().addAll(firstComboBox, secondComboBox);
        combobuttonHBox.setAlignment(Pos.CENTER);

        //HBox for the controls. Added last to topHBox.
        HBox controlsHBox = controlsHBox(root, compareBorderPane);
        topHBox.getChildren().addAll(leftPaddingHBox,combobuttonHBox, controlsHBox);
        compareBorderPane.setTop(topHBox);

        //Creates the HBox that will be assigned to the bottom.
        HBox bottomHBox = new HBox();
        bottomHBox.setMinSize(800, 100);
        bottomHBox.setMaxSize(800, 100);

        ToggleGroup compareToggleGroup = new ToggleGroup();
        ToggleButton head2headButton = new ToggleButton("Head to Head");
        ToggleButton bestStatsButton = new ToggleButton("Best Stats");
        head2headButton.setToggleGroup(compareToggleGroup);
        bestStatsButton.setToggleGroup(compareToggleGroup);
        compareToggleGroup.selectToggle(head2headButton);
        BorderPane compareToggleBox = toggleBox(head2headButton, bestStatsButton);
        bottomHBox.getChildren().add(compareToggleBox);
        bottomHBox.setAlignment(Pos.BASELINE_CENTER);
        bottomHBox.setTranslateY(21);
        compareBorderPane.setBottom(bottomHBox);
        root.getChildren().add(compareBorderPane);

        //Create Cards for grid pane
        Car bestMPGCar = model.getInventory().stream()
                .max(Comparator.comparing(Car::getMpg))
                .get();

        Car leastMilesCar = model.getInventory().stream()
                .min(Comparator.comparing(Car::getMileage))
                .get();

        Car lowestPriceCar = model.getInventory().stream()
                .min(Comparator.comparing(Car::getSalesPrice))
                .get();


        StackPane centerPane = new StackPane();
        GridPane gridPane = new GridPane();
        gridPane.setHgap(40);
        gridPane.setMaxSize(800, 600);
        gridPane.setAlignment(Pos.CENTER);
        StackPane bestMPGCard = bestCard("Best MPG", gasIcon(), bestMPGCar);
        StackPane minCostCard = bestCard("Most Affordable", savingsIcon(), lowestPriceCar);
        StackPane minMilesCard = bestCard("Least Miles", distanceIcon(), leastMilesCar);
        gridPane.add(bestMPGCard, 0, 0);
        gridPane.add(minCostCard, 1, 0);
        gridPane.add(minMilesCard, 2, 0 );

        gridPane.setVisible(false);


        //Create table
        TableView<Car> comparisonTable = customerTableView();

        firstComboBox.valueProperty().addListener((Observable, oldValue, newValue) -> {
            if (validateSelection(firstComboBox, model) && validateSelection(secondComboBox, model) ){
                Car firstSelectedCar = model.getInventory().stream()
                        .filter(car -> car.getId().equals(firstComboBox.getValue()))
                        .findFirst().get();

                Car secondSelectedCar = model.getInventory().stream()
                        .filter(car -> car.getId().equals(secondComboBox.getValue()))
                        .findFirst().get();

                comparisonTable.getItems().setAll(firstSelectedCar,secondSelectedCar);
            }
        });

        secondComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (validateSelection(firstComboBox, model) && validateSelection(secondComboBox, model) ){
                Car firstSelectedCar = model.getInventory().stream()
                        .filter(car -> car.getId().equals(firstComboBox.getValue()))
                        .findFirst().get();

                Car secondSelectedCar = model.getInventory().stream()
                        .filter(car -> car.getId().equals(secondComboBox.getValue()))
                        .findFirst().get();

                comparisonTable.getItems().setAll(firstSelectedCar,secondSelectedCar);
            }
        });

        centerPane.getChildren().addAll(comparisonTable, gridPane);
        compareBorderPane.setCenter(centerPane);


        //Create Cards for grid pane
        bestStatsButton.setOnMouseClicked( e -> {
            comparisonTable.setVisible(false);
            gridPane.setVisible(true);
            combobuttonHBox.setVisible(false);
            bestStatsButton.setDisable(true);
            head2headButton.setDisable(false);
        });

        head2headButton.setOnMouseClicked( e -> {
            comparisonTable.setVisible(true);
            combobuttonHBox.setVisible(true);
            gridPane.setVisible(false);
            head2headButton.setDisable(true);
            bestStatsButton.setDisable(false);
        });



    }

    //Creates the compare inventory screen.
    private void inventoryBorderPane(AnchorPane root, CarDAO model){
        BorderPane inventoryBorderPane = new BorderPane();
        inventoryBorderPane.setStyle("-fx-background-color: whitesmoke;");
        inventoryBorderPane.setMinSize(800, 600);
        inventoryBorderPane.setMaxSize(800, 600);

        //Create HBox that will be placed at the top of the Pane.
        HBox topHBox = new HBox();
        topHBox.setMinSize(800, 100);
        topHBox.setMaxSize(800, 100);

        //Child HBox to add Padding to HBox.
        HBox leftPaddingHBox = new HBox();
        leftPaddingHBox.setMinSize(150, 100);
        leftPaddingHBox.setMaxSize(150, 100);

        //Combo button HBox holder. Child to topHBox
        StackPane searchHBox = new StackPane();
        searchHBox.setAlignment(Pos.CENTER);
        searchHBox.setMinSize(500, 100);
        searchHBox.setMaxSize(500, 100);

        //Create textfield
        TextField searchField = new TextField();
        searchField.getStylesheets().add("csc251/team/project/ControlStyle.css");
        searchField.getStyleClass().add("search-field");
        searchField.setMinSize(180, 40);
        searchField.setMaxSize(180, 40);

        //Create ComboButton
        ComboBox<String> editComboBox = comboBoxTemplate(model);
        editComboBox.setVisible(false);

        searchHBox.getChildren().addAll(searchField, editComboBox);

        //HBox for the controls. Added last to topHBox.
        HBox controlsHBox = controlsHBox(root, inventoryBorderPane);
        topHBox.getChildren().addAll(leftPaddingHBox,searchHBox, controlsHBox);
        inventoryBorderPane.setTop(topHBox);

        //Create table
        TableView<Car> inventoryTable = customerTableView();
        inventoryTable.getItems().addAll(model.getInventory());


        searchField.textProperty().addListener((observable, oldValue, newValue) -> {

            if (validateSelectionTextField(searchField.getText(), model) ){


                inventoryTable.getItems().setAll(
                        model.getInventory().stream()
                                .filter(car -> car.toString().contains(searchField.getText()))
                                .collect(Collectors.toList())
                );
            }
        });


        //Create Stack Pane
        StackPane centerPane = new StackPane();
        centerPane.setMinSize(400, 300);
        centerPane.setMaxSize(400, 300);
        centerPane.setAlignment(Pos.CENTER);

        VBox formVbox = formVBox(editComboBox, model);
        formVbox.setVisible(false);
        centerPane.getChildren().addAll(inventoryTable, formVbox);
        inventoryBorderPane.setCenter(centerPane);


        //Create toggle Bar for bottom.
        HBox bottomHBox = new HBox();
        bottomHBox.setMinSize(800, 100);
        bottomHBox.setMaxSize(800, 100);
        ToggleGroup inventoryToggleGroup = new ToggleGroup();
        ToggleButton listOptionButton = new ToggleButton("List");
        ToggleButton editOptionButton = new ToggleButton("Edit");
        listOptionButton.setToggleGroup(inventoryToggleGroup);
        editOptionButton.setToggleGroup(inventoryToggleGroup);
        inventoryToggleGroup.selectToggle(listOptionButton);
        BorderPane inventoryToggleBox = toggleBox(listOptionButton,editOptionButton);
        bottomHBox.getChildren().add(inventoryToggleBox);
        bottomHBox.setAlignment(Pos.BASELINE_CENTER);
        bottomHBox.setTranslateY(21);
        inventoryBorderPane.setBottom(bottomHBox);


        listOptionButton.setOnMouseClicked( e -> {
            editComboBox.setVisible(false);
            searchField.setVisible(true);
            formVbox.setVisible(false);
            inventoryTable.setVisible(true);
            listOptionButton.setDisable(true);
            editOptionButton.setDisable(false);
        });

        editOptionButton.setOnMouseClicked( e -> {
            editComboBox.setVisible(true);
            searchField.setVisible(false);
            formVbox.setVisible(true);
            inventoryTable.setVisible(false);
            editOptionButton.setDisable(true);
            listOptionButton.setDisable(false);
        });


        root.getChildren().add(inventoryBorderPane);

    }

    //Creates the history screen.
    private void historyBorderPane(AnchorPane root){
        BorderPane historyBorderPane = new BorderPane();
        historyBorderPane.setStyle("-fx-background-color: whitesmoke;");
        historyBorderPane.setMinSize(800, 600);
        historyBorderPane.setMaxSize(800, 600);

        //Create HBox that will be placed at the top of the Pane.
        HBox topHBox = new HBox();
        topHBox.setMinSize(800, 100);
        topHBox.setMaxSize(800, 100);

        //Child HBox to add Padding to HBox.
        HBox leftPaddingHBox = new HBox();
        leftPaddingHBox.setMinSize(150, 100);
        leftPaddingHBox.setMaxSize(150, 100);

        //Combo button HBox holder. Child to topHBox
        StackPane searchHBox = new StackPane();
        searchHBox.setAlignment(Pos.CENTER);
        searchHBox.setMinSize(500, 100);
        searchHBox.setMaxSize(500, 100);

        //Create textfield
        TextField searchField = new TextField();
        searchField.getStylesheets().add("csc251/team/project/ControlStyle.css");
        searchField.getStyleClass().add("search-field");
        searchField.setMinSize(180, 40);
        searchField.setMaxSize(180, 40);

        //Create ComboButton
        ComboBox<String> editComboBox = formComboBoxTemplate(model);
        editComboBox.setVisible(false);

        searchHBox.getChildren().addAll(searchField, editComboBox);

        //HBox for the controls. Added last to topHBox.
        HBox controlsHBox = controlsHBox(root, historyBorderPane);
        topHBox.getChildren().addAll(leftPaddingHBox,searchHBox, controlsHBox);
        historyBorderPane.setTop(topHBox);

        //Create table
        TableView<Car> historyTable = historyTableView();
        historyTable.getItems().addAll(model.getSalesHistory());

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {

            if (validateSoldSelectionTextField(searchField.getText(), model) ){

                historyTable.getItems().setAll(
                        model.getSalesHistory().stream()
                                .filter(car -> car.toString().contains(searchField.getText()))
                                .collect(Collectors.toList())
                );
            }
        });

        //Create Stack Pane
        StackPane centerPane = new StackPane();
        centerPane.setMinSize(400, 300);
        centerPane.setMaxSize(400, 300);
        centerPane.setAlignment(Pos.CENTER);

        VBox historyFormVBox = historyFormVBox(editComboBox, model);
        historyFormVBox.setVisible(false);
        centerPane.getChildren().addAll(historyTable, historyFormVBox);
        historyBorderPane.setCenter(centerPane);


        //Create toggle Bar for bottom.
        HBox bottomHBox = new HBox();
        bottomHBox.setMinSize(800, 100);
        bottomHBox.setMaxSize(800, 100);
        ToggleGroup inventoryToggleGroup = new ToggleGroup();
        ToggleButton listOptionButton = new ToggleButton("List");
        ToggleButton editOptionButton = new ToggleButton("Edit");
        listOptionButton.setToggleGroup(inventoryToggleGroup);
        editOptionButton.setToggleGroup(inventoryToggleGroup);
        inventoryToggleGroup.selectToggle(listOptionButton);
        BorderPane inventoryToggleBox = toggleBox(listOptionButton,editOptionButton);
        bottomHBox.getChildren().add(inventoryToggleBox);
        bottomHBox.setAlignment(Pos.BASELINE_CENTER);
        bottomHBox.setTranslateY(21);
        historyBorderPane.setBottom(bottomHBox);


        listOptionButton.setOnMouseClicked( e -> {
            editComboBox.setVisible(false);
            searchField.setVisible(true);
            historyFormVBox.setVisible(false);
            historyTable.setVisible(true);
            listOptionButton.setDisable(true);
            editOptionButton.setDisable(false);
        });

        editOptionButton.setOnMouseClicked( e -> {
            editComboBox.setVisible(true);
            searchField.setVisible(false);
            historyFormVBox.setVisible(true);
            historyTable.setVisible(false);
            editOptionButton.setDisable(true);
            listOptionButton.setDisable(false);
        });



        root.getChildren().add(historyBorderPane);

    }

    //Creates the sell screen.
    private void sellBorderPane(AnchorPane root, CarDAO model){

        //Create Border Pane.
        BorderPane sellBorderPane = new BorderPane();
        sellBorderPane.setStyle("-fx-background-color: whitesmoke");
        sellBorderPane.setMinSize(800, 600);
        sellBorderPane.setMaxSize(800, 600);

        //Create HBox that will be placed at the top of the Pane.
        HBox topHBox = new HBox();
        topHBox.setMinSize(800, 100);
        topHBox.setMaxSize(800, 100);

        //Child HBox to add Padding to HBox.
        HBox leftPaddingHBox = new HBox();
        leftPaddingHBox.setMinSize(150, 100);
        leftPaddingHBox.setMaxSize(150, 100);

        //Combo button HBox holder. Child to topHBox
        HBox combobuttonHBox = new HBox();
        combobuttonHBox.setAlignment(Pos.CENTER);
        combobuttonHBox.setMinSize(500, 100);
        combobuttonHBox.setMaxSize(500, 100);

        //Create ComboButton
        ComboBox<String> sellComboBox = comboBoxTemplate(model);
        combobuttonHBox.getChildren().addAll(sellComboBox);

        sellComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (validateSelection(sellComboBox, model)){
                Car selectedCar = model.getInventory().stream()
                        .filter(car -> car.getId().equals(sellComboBox.getValue()))
                        .findFirst().get();
                sellBorderPane.setCenter(purchaseCard(selectedCar, model, root, sellBorderPane));
            }

        });


        //HBox for the controls. Added last to topHBox.
        HBox controlsHBox = controlsHBox(root, sellBorderPane);
        topHBox.getChildren().addAll(leftPaddingHBox,combobuttonHBox, controlsHBox);
        sellBorderPane.setTop(topHBox);

        //Add border pane to root.
        root.getChildren().add(sellBorderPane);

    }


    @Override//Override the start method in the Application class
    public void start(Stage primaryStage){
        //Initialize the database connection.
        model = new CarDAO();
        model.createTableIfNotExists();

        //Create root anchor pane
        AnchorPane root = new AnchorPane();

        //Create VBox home pane.
        VBox homePane = new VBox();
        homePane.setMinWidth(800);

        //Create and style grid pane to hold cards.
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(40);
        gridPane.setVgap(40);
        gridPane.setPadding(new Insets(90));

        //Create home pane cards.
        VBox sellCard = menuCard("Sell", sellIcon());
        VBox inventoryCard = menuCard("Inventory", searchIcon());
        VBox compareCard = menuCard("Compare", comparisonIcon());
        VBox historyCard = menuCard("History",historyIcon());

        //Add cards to grid pane.
        gridPane.add(sellCard, 0, 0);
        gridPane.add(compareCard, 1, 0);
        gridPane.add(inventoryCard, 0, 1 );
        gridPane.add(historyCard, 1,1);

        //Add the navigation menu to the anchor pane.
        homePane.getChildren().add(gridPane);

        //Instantiate a navigation drawer, hamburger menu, and card images.
        VBox navigationMenu = new VBox();
        navigationMenu.setStyle("-fx-background-color: #333; -fx-background-radius: 10;");
        navigationMenu.setVisible(false);
        navigationMenu.setPrefWidth(180);
        navigationMenu.setPrefHeight(600);
        navigationMenu.setAlignment(Pos.BASELINE_CENTER);

        //Create and style nodes for the navigation menu
        VBox navigationHeader = new VBox();
        navigationHeader.setStyle("-fx-background-color: #333");
        navigationHeader.setMinSize(40, 66);
        navigationHeader.setMaxSize(40, 66);
        Button compareButton = materialButton("Compare");
        Button historyButton = materialButton("History");
        Button inventoryButton = materialButton("Inventory");
        Button sellButton = materialButton("Sell");

        //Adds nodes to navigation object.
        navigationMenu.setSpacing(1);
        navigationMenu.getChildren().addAll(navigationHeader, sellButton,
                inventoryButton, compareButton, historyButton);

        Shape hamburgerIcon = hamburgerIcon(navigationMenu);
        Group hamburgerGroup = new Group(hamburgerIcon);
        hamburgerGroup.setTranslateY(34);
        hamburgerGroup.setTranslateX(30);
        StackPane menuStackPane= new StackPane();
        menuStackPane.setAlignment(Pos.TOP_LEFT);
        menuStackPane.getChildren().addAll(navigationMenu, hamburgerGroup);
        root.getChildren().addAll(homePane, menuStackPane);

        //Define button and card on click behaviour to display new Vbox.
        sellButton.setOnMouseClicked(e -> {sellBorderPane(root, model); menuStackPane.toFront();});
        compareButton.setOnMouseClicked(e ->  { compareBorderPane(root, model); menuStackPane.toFront();});
        inventoryButton.setOnMouseClicked(e -> {inventoryBorderPane(root, model); menuStackPane.toFront();});
        historyButton.setOnMouseClicked(e -> { historyBorderPane(root); menuStackPane.toFront();});
        sellCard.setOnMouseClicked(e -> {sellBorderPane(root, model); menuStackPane.toFront();});
        compareCard.setOnMouseClicked(e -> {compareBorderPane(root, model); menuStackPane.toFront();} );
        inventoryCard.setOnMouseClicked(e -> {inventoryBorderPane(root, model); menuStackPane.toFront();});
        historyCard.setOnMouseClicked(e -> {historyBorderPane(root); menuStackPane.toFront();});

        //Create and show the primary scene.
        primaryStage.setTitle("Car Lot Application - Team #3");
        Scene primaryScene = new Scene(root, 800,600);
        primaryStage.setScene(primaryScene);
        primaryStage.show();

    }

    //Main
    public static void main(String[] args){
        launch(args);
    }
}
