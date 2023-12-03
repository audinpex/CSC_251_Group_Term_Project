package csc251.team.project;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class CarLotFx extends Application {

    //Shapes
    //Creates a hamburger icon for navigation.
    private Shape hamburgerIcon(VBox navigationMenu){
        SVGPath hamburgerIcon = new SVGPath();
        hamburgerIcon.setContent("M120-240v-80h720v80H120Zm0-200v-80h720v80H120Zm0-200v-80h720v80H120Z");
        hamburgerIcon.setFill(Color.web("#00b5e2"));
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
    private StackPane bestCard(String str, Shape icon){
        //Style the Stack Pane which will hold an vbox for text and image.
        StackPane card = new StackPane();
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(10));
        card.setMaxSize(200, 350);
        card.setMinSize(200, 350);
        card.getStylesheets().add("/Resources/ControlStyle.css");
        card.getStyleClass().add("stat-card");

        //Set image VBox attributes.
        VBox imageVBox = new VBox();
        imageVBox.setAlignment(Pos.CENTER);
        Group imageGroup = new Group();
        imageGroup.getChildren().add(icon);
        imageGroup.setTranslateY(-100);
        imageVBox.getChildren().add(imageGroup);

        //Set text Vbox attributes.
        VBox textVBox = new VBox();
        textVBox.setAlignment(Pos.CENTER);
        Font cardFont = Font.font("San Francisco", FontWeight.EXTRA_BOLD, 24);
        Label titleLabel = new Label(str);
        titleLabel.setFont(cardFont);
        Shape line = new Line();
        line.setStroke(Color.web("#333"));
        line.setStrokeLineCap(StrokeLineCap.SQUARE);
        line.setScaleX(160);
        textVBox.getChildren().addAll(titleLabel, line);
        textVBox.setSpacing(10);

        //Add the text and image vboxes to  the stack pane
        card.getChildren().addAll(textVBox, imageVBox);

        return card;

    }

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

    private ComboBox<String> comboBoxTemplate( ){
        final ComboBox<String> comboBox = new ComboBox<String>();
        comboBox.setMinSize(180, 40);
        comboBox.setMaxSize(180, 40);
        comboBox.setEditable(true);
        comboBox.getStylesheets().add("/Resources/ControlStyle.css");
        comboBox.getStyleClass().add("combo-box");

        return comboBox;
    }

    private BorderPane toggleBox(ToggleButton... buttons){
        //Create Border Pane for button
        BorderPane toggleButtonBox= new BorderPane();
        toggleButtonBox.getStylesheets().add("/Resources/ControlStyle.css");
        toggleButtonBox.getStyleClass().add("toggle-box");
        buttons[0].getStyleClass().add("toggle-button");
        buttons[1].getStyleClass().add("toggle-button");

        toggleButtonBox.setLeft(buttons[0]);
        toggleButtonBox.setCenter(null);
        toggleButtonBox.setRight(buttons[1]);
        toggleButtonBox.setMinSize(60, 400);
        toggleButtonBox.setMaxSize(60, 400);
        BorderPane.setAlignment(buttons[0], Pos.CENTER);
        BorderPane.setAlignment(buttons[1], Pos.CENTER);

        return toggleButtonBox;
    }

    private void returnHome(AnchorPane root) {
        int childNodes = root.getChildren().size();
        if (childNodes > 2) {
            for (int i = childNodes; i > 2; i--) {
                root.getChildren().remove(1);
            }
        }
    }

    //Creates the compare screen.
    private void compareBorderPane(AnchorPane root){
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
        combobuttonHBox.getStylesheets().add("/Resources/ControlStyle.css");
       
        //Create Combo Buttons
        ComboBox<String> firstComboBox = comboBoxTemplate();
        ComboBox<String> secondComboBox = comboBoxTemplate();
        
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
        GridPane gridPane = new GridPane();
        gridPane.setHgap(40);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setMaxSize(700, 400);
        StackPane bestMPGCard = bestCard("Best MPG", gasIcon());
        StackPane minCostCard = bestCard("Most Affordable", savingsIcon());
        StackPane minMilesCard = bestCard("Least Miles", distanceIcon());
        gridPane.add(bestMPGCard, 0, 0);
        gridPane.add(minCostCard, 1, 0);
        gridPane.add(minMilesCard, 2, 0 );

        //Create Cards for grid pane
        bestStatsButton.setOnMouseClicked( e -> {
            compareBorderPane.setCenter(gridPane);
            combobuttonHBox.setVisible(false);
            bestStatsButton.setDisable(true);
            head2headButton.setDisable(false);
        });

        head2headButton.setOnMouseClicked( e -> {
            combobuttonHBox.setVisible(true);
            gridPane.setVisible(false);
            head2headButton.setDisable(true);
            bestStatsButton.setDisable(false);
        });



    }

    //Creates the compare inventory screen.
    private void inventoryBorderPane(AnchorPane root){
        BorderPane inventoryBorderPane = new BorderPane();
        inventoryBorderPane.setStyle("-fx-background-color: whitesmoke;");
        inventoryBorderPane.setMinSize(800, 600);

        //Create HBox that will be placed at the top of the Pane.
        HBox topHBox = new HBox();
        topHBox.setMinSize(800, 100);
        topHBox.setMaxSize(800, 100);

        //Child HBox to add Padding to HBox.
        HBox leftPaddingHBox = new HBox();
        leftPaddingHBox.setMinSize(300, 100);
        leftPaddingHBox.setMaxSize(300, 100);

        //HBox for the controls. Added last to topHBox.
        HBox controlsHBox = controlsHBox(root, inventoryBorderPane);
        topHBox.getChildren().addAll(leftPaddingHBox,controlsHBox);
        inventoryBorderPane.setTop(topHBox);


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

        root.getChildren().addAll(inventoryBorderPane);

    }

    //Creates the history screen.
    private void historyBorderPane(AnchorPane root){
        BorderPane historyBorderPane = new BorderPane();
        historyBorderPane.setStyle("-fx-background-color: whitesmoke;");
        historyBorderPane.setMinSize(800, 600);


        HBox bottomHBox = new HBox();
        bottomHBox.setMinSize(800, 100);
        bottomHBox.setMaxSize(800, 100);
        ToggleGroup historyToggleGroup = new ToggleGroup();
        ToggleButton pastTransactionsButton = new ToggleButton("Past Transactions");
        ToggleButton profitTrackerButton = new ToggleButton("Profit Tracker");
        pastTransactionsButton.setToggleGroup(historyToggleGroup);
        profitTrackerButton.setToggleGroup(historyToggleGroup);
        historyToggleGroup.selectToggle(pastTransactionsButton);
        BorderPane historyToggleBox = toggleBox(pastTransactionsButton, profitTrackerButton);
        bottomHBox.getChildren().add(historyToggleBox);
        bottomHBox.setAlignment(Pos.BASELINE_CENTER);
        bottomHBox.setTranslateY(21);
        historyBorderPane.setBottom(bottomHBox);

        root.getChildren().add(historyBorderPane);

    }

    //Creates the sell screen.
    private void sellBorderPane(AnchorPane root){
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
        combobuttonHBox.setMinSize(500, 100);
        combobuttonHBox.setMaxSize(500, 100);
        combobuttonHBox.setSpacing(80);
        combobuttonHBox.getStylesheets().add("csc251/team/project/ControlStyle.css");
        ComboBox<String> sellComboBox = comboBoxTemplate();
        combobuttonHBox.getChildren().addAll(sellComboBox);


        sellComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Selected item: " + observable);
            System.out.println("Selected item: " + oldValue);
            System.out.println("Selected item: " + newValue);

        });

        combobuttonHBox.setAlignment(Pos.CENTER);

        //HBox for the controls. Added last to topHBox.
        HBox controlsHBox = controlsHBox(root, sellBorderPane);
        topHBox.getChildren().addAll(leftPaddingHBox,combobuttonHBox, controlsHBox);
        sellBorderPane.setTop(topHBox);

        root.getChildren().add(sellBorderPane);
    }
    
    @Override//Override the start method in the Application class
    public void start(Stage primaryStage){

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
        System.out.println(root.getChildren().size());

        //Define button and card on click behaviour to display new Vbox.
        sellButton.setOnMouseClicked(e -> {sellBorderPane(root); menuStackPane.toFront();});
        compareButton.setOnMouseClicked(e ->  { compareBorderPane(root); menuStackPane.toFront();});
        inventoryButton.setOnMouseClicked(e -> {inventoryBorderPane(root); menuStackPane.toFront();});
        historyButton.setOnMouseClicked(e -> { historyBorderPane(root); menuStackPane.toFront();});
        sellCard.setOnMouseClicked(e -> {sellBorderPane(root); menuStackPane.toFront();});
        compareCard.setOnMouseClicked(e -> {compareBorderPane(root); menuStackPane.toFront();} );
        inventoryCard.setOnMouseClicked(e -> {inventoryBorderPane(root); menuStackPane.toFront();});
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
