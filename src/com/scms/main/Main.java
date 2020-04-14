/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scms.main;
import com.scms.employees.*;
import com.scms.finances.Finance;
import com.scms.rocket.Rocket;
import javafx.application.Application;
import javafx.stage.Stage;
 import javafx.scene.image.ImageView;

import javafx.animation.*;
import javafx.scene.paint.Color;
import javafx.scene.*;
import javafx.scene.text.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.collections.*;
import javafx.event.*;
 import javafx.scene.image.Image;
import javafx.scene.media.*;  
import javafx.scene.control.cell.*;
import javafx.scene.paint.Color;
import javafx.beans.property.*;
import javafx.beans.property.*;
import java.util.*;
import java.io.File;  
/**
 *
 * @author Admin
 */
public class Main extends Application {
    

    /**
     * @param args the command line arguments
     */
    public static ObservableList<Employee> employees = FXCollections.observableArrayList();
    public static ObservableList<Rocket> rockets= FXCollections.observableArrayList();
    private static TableView<Employee> table;
    private static TableView<Rocket> rocketsTable;
    private TextField nameInput;
    private TextField ageInput;
    private TextField salaryInput;
    private ComboBox typeInput;
    //This string property updates on noOfEMployees change, works but I still have a tinybug n the listener
    static IntegerProperty noOfEmployees = new SimpleIntegerProperty();
    public static void main(String[] args) {
        // TODO code application logic here
      DbService.fetchEmployeesOnStart();
      DbService.fetchRocketsOnStart();
      DbService.fetchFinanceOnStart();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
       ///PlayGround
        ///PlayGround
        //employees has all our data of employees    
 ///////////////////MAIN LAYOUT////////////////////////
 //////////////////////////// //// // / / / / / / /  /
 //BorderPane-the main layout borderpane->splitPane-> 2vboxes one foreach Plus set orientation to horizontal 
        BorderPane parentContainer = new BorderPane();
  //split pane splits the program into two parts.Management and Launch
        SplitPane splitContainer = new SplitPane();
        splitContainer.setId("splitContainer");
        splitContainer.setOrientation(Orientation.HORIZONTAL);
        splitContainer.setDividerPositions();
        splitContainer.setDividerPosition(01, 360); 
//generating the menu bar
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        Menu aboutMenu = new Menu("About");
            MenuItem aboutMenuList =new MenuItem("about me");
            aboutMenu.getItems().add(aboutMenuList);
            aboutMenuList.setOnAction(e->{
            alertBox("About", "Made By Abdul Rauf sp19-bse-043 @Comsats, Ceo && Founder @ Technocrats");
            });
        MenuItem suggestionsItem = new MenuItem("Suggestions");
            suggestionsItem.setOnAction(e->{
            alertBox("Suggestion","Launch commercialRockets to make money");
            });
        
        MenuItem saveItem = new MenuItem("save");
        saveItem.setOnAction(e->{
                DbService.saveProgressToDb();
            });
        fileMenu.getItems().addAll(suggestionsItem, saveItem);
        menuBar.getMenus().addAll(fileMenu, aboutMenu);
//generating the managing part of the splitContainer
        //hbox for manage option
        //styling is also included
        HBox manageHb = new HBox();
        Label manageLabel = new Label("Manage");
        manageLabel.getStyleClass().add("manage");
        manageLabel.setPadding(new Insets(0,515,0,0));
        manageLabel.setGraphic(new ImageView(new Image("com/scms/images/manage.png")));
        manageHb.getChildren().addAll(manageLabel);
//Table view of persons
            Finance.updateTotalEmployeesExpenditure();
            noOfEmployees.set(employees.size());
//            setTimeout(() -> data.add(new Engineer("Captain Kirk",28,999)), 5000);
//setting up table
            //buttons
         //Hire Btn
                Button hireBtn = new Button("Hire");
                hireBtn.setOnAction(e->{
                hireBtnClicked();
                }); 
         //hire in bulk
                Button hireInBulk = new Button("Hire in Bulk");
                hireInBulk.setOnAction(e->{
                hireInBulkClicked();
                });
         //fireBtn
                Button fireBtn = new Button("Fire");
                fireBtn.setOnAction(e-> fireBtnClicked());
               
         //fire alll(incase u had massive addition on bulk
                Button fireAllBtn = new Button("Fire All");
                fireAllBtn.setOnAction(e->{ 
                employees.clear();
                noOfEmployees.set(employees.size());
                });
//hbox for puutng all the stuuff in
                HBox inputHbox = new HBox();
                inputHbox.setPadding(new Insets(10,10,10,10));
                inputHbox.setSpacing(10);
                inputHbox.getChildren().addAll(hireBtn,hireInBulk,fireBtn, fireAllBtn);
//adding table collumns
//id colum
            TableColumn <Employee, Integer> idColumn = new TableColumn<>("Id");
            idColumn.setId("idColumn");
            idColumn.setMinWidth(125);
            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
//name COlumn
            TableColumn<Employee, String> nameColumn = new TableColumn<>("Name");
            nameColumn.setMinWidth(125);
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
//agEColumn
            TableColumn<Employee, Integer> ageColumn = new TableColumn<>("Age");
            ageColumn.setMinWidth(125);
            ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
//salaryColumn
            TableColumn<Employee, Double> salaryColumn = new TableColumn<>("Salary");
            salaryColumn.setMinWidth(125);
            salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
//typeColumn
            TableColumn<Employee, String> typeColumn =  new TableColumn<>("Type");
            typeColumn.setMinWidth(125);
            typeColumn.setCellValueFactory(new PropertyValueFactory<>("className"));
            
            table = new TableView<>();
            table.setMaxWidth(630);
            table.setMaxHeight(250);
            table.setItems(employees);
            table.getColumns().addAll(idColumn,nameColumn,ageColumn,salaryColumn, typeColumn);
//MAKING FINANCES PART OF THE MANAGE PART
///////////////////////////////////////////////////////////////$$$$$$$$$$$$$//////////////
//-----------FINANCES-----------////////////////
            //we display burn rate.total employees , expenditure, remaining capital
            VBox financeContainer =new VBox();//everythingrelatedtofinance is here 
  //Finance Label Below.           
            Label financesLabel = new Label("Finances");
            financesLabel.setGraphic(new ImageView(new Image("com/scms/images/finance.png")));
            financesLabel.getStyleClass().add("finances");
            financesLabel.setPadding(new Insets(0,515,0,0));
  //burn rate hbox inside fncontainer          
            HBox burnRateHb= new HBox();
                Label burnRateTitle = new Label("Burn Rate:");   
                burnRateTitle.setGraphic(new ImageView(new Image("com/scms/images/burn.png")));
                burnRateTitle.getStyleClass().add("finances");
//JUST some css classes                 
                Label burnRateLabel = new Label(""+Finance.burnRate.get());
                burnRateLabel.getStyleClass().add("digits");
// burnrate hbox is now built               
            burnRateHb.getChildren().addAll(burnRateTitle, burnRateLabel);
//No of employees HBox    
            HBox employeesHb = new HBox();
                Label employeesTitle = new Label("No. Employees: ");
                Label employeesLabel = new Label(""+noOfEmployees.get());
                employeesLabel.getStyleClass().add("digits");
                employeesHb.getChildren().addAll(employeesTitle,employeesLabel);
                employeesTitle.setGraphic(new ImageView(new Image("com/scms/images/employees1.png")));
                employeesTitle.getStyleClass().add("finances");
//employees expenditure Hbox                
            HBox expenditureHb = new HBox();
                Label expenditureTitle = new Label("Employees Expenditure: ");
                Label expenditureLabel = new Label(""+Finance.totalEmployeesExpenditure.get());
                expenditureLabel.getStyleClass().add("digits");
                expenditureTitle.setGraphic(new ImageView(new Image("com/scms/images/employees2.png")));
                expenditureTitle.getStyleClass().add("finances");
                expenditureHb.getChildren().addAll(expenditureTitle,expenditureLabel);
//total funds Hbox            
                HBox capitalHb = new HBox();
                Label capitalTitle = new Label("Funds: ");
                Label capitalLabel = new Label(""+Finance.currentFunds.get());
                capitalLabel.getStyleClass().add("digits");
                capitalTitle.setGraphic(new ImageView(new Image("com/scms/images/dollar.png")));
                capitalTitle.getStyleClass().add("finances");
                capitalHb.getChildren().addAll(capitalTitle,capitalLabel);
            /*
            this is where everything remains reactive , adding listeners for changing UI of the finances
            edit . //now this is 100% built but some listeners are still not workinmg . Have no time tofix it plus nits not  a biog deal
                */
//adding listeners 
              //WARNING MUST READ!!!! I chose v1 v2 v3 as listener parameters
                /*
                v2 = old  value, v3= new value
                */
                
            capitalLabel.setText(String.format("%.0f",Finance.getCurrentFunds()));
            Finance.currentFunds.addListener((v1,v2,v3)->{
                capitalLabel.setText(String.format("%.0f", v3));
                 });
            Finance.totalEmployeesExpenditure.addListener((v1,v2,v3)->{
                expenditureLabel.setText(String.format("%.0f", v3));
                System.out.println("232424");
            });
            Finance.burnRate.addListener((v1,v2,v3)->{
               burnRateLabel.setText(String.format("%.0f", v3));
            });
            noOfEmployees.addListener((v1,v2,v3)->{
               employeesLabel.setText(String.format("%d", v3));
            });
            financeContainer.getChildren().addAll(financesLabel,burnRateHb, employeesHb,expenditureHb,capitalHb);
//            System.out.println(employees.size());
/////LAUNCH VBox this is where the second part of the split pane is built 
            //The launch part of the app will be built here
            VBox launchVb= new VBox();
            launchVb.setSpacing(5);
           //We have Rockets section which is quite selfexplanatory 
            Label rocketLabel = new Label("Rockets");
            rocketLabel.setPadding(new Insets(0,522,0,0));
            rocketLabel.setGraphic(new ImageView(new Image("com/scms/images/rocket.png")));
                rocketLabel.getStyleClass().add("finances");
                HBox rocketOptionsHb = new HBox();
                rocketOptionsHb.setSpacing(10);
                    Button buildBtn = new Button("Build");
                        buildBtn.setOnAction(e->{ buildRocketClicked(); });
                    Button buyBtn = new Button("Buy");
                        buyBtn.setOnAction(e->{ buyRocketClicked();});
                rocketOptionsHb.getChildren().addAll(buildBtn, buyBtn);
            //launch a mission
                Label missionLabel = new Label("Mission");
                missionLabel.setGraphic(new ImageView(new Image("com/scms/images/mission.jpg")));
                missionLabel.getStyleClass().add("finances");
                missionLabel.setPadding(new Insets(0,525,0,0));
                    Button missionBtn = new Button("Launch Mission");
                    missionBtn.setOnAction(e->{ launchMission();});
             //launch a commerical rocket
              //just the UI of the commercialrocket launch      
                    Label commercialLabel = new Label("Commercial Launch");
                     commercialLabel.setGraphic(new ImageView(new Image("com/scms/images/commercial.png")));
                commercialLabel.getStyleClass().add("finances");
                commercialLabel.setPadding(new Insets(0,430,0,0));
//ROCKETS TABLE
                ///ROCKETS TABLE coming below
                    TableColumn<Rocket, Integer> rIdColumn = new TableColumn<>("Id");
                    rIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
                    
                    TableColumn<Rocket, String> rNameColumn = new TableColumn<>("Name");
                    rNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
                    
                    TableColumn<Rocket, Double> rCapacityColumn = new TableColumn<>("Capacity(lbs)");
                    rCapacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
                    
                    TableColumn<Rocket, Double> rPPLColumn = new TableColumn<>("PricePerLaunch");
                    rPPLColumn.setCellValueFactory(new PropertyValueFactory<>("pricePerLaunch"));
                    
                    rocketsTable = new TableView<>();
                    rocketsTable.setMaxHeight(300);
                    rocketsTable.setMinWidth(630);//because I madethe programfor 1280*720 so they'refixedcouldnb't find a way to makeit responsive 
                    rocketsTable.setItems(rockets);
                    rocketsTable.getColumns().addAll(rIdColumn,rNameColumn, rCapacityColumn, rPPLColumn);
                    HBox rocketCbHbox = new HBox();
                            rocketCbHbox.getChildren().add(rocketsTable);
                  //capacity of the selected rocket         \
                            //Setting price per pound will tellhow much you can make
                        Label setPriceLabel = new Label("Set Price per pound");
                        setPriceLabel.getStyleClass().add("manage");
                        
                        TextField inputPrice = new TextField();
                        inputPrice.setPromptText("EnterAmount");
                        inputPrice.setPadding(new Insets(5,30,5, 15));
                        HBox setHb = new HBox();
                        setHb.getChildren().addAll(setPriceLabel, inputPrice);
                      //launch button finally completes the commerical launch
                        Button launchBtn = new Button("Launch");
                        //eeventListeners for launch sectioin
                        launchBtn.setOnAction(e-> {
                         ObservableList<Rocket> sel,all;
                        all = rocketsTable.getItems();
                        sel = rocketsTable.getSelectionModel().getSelectedItems();
                        try{
                        Finance.currentFunds.set(Finance.currentFunds.get() + (sel.get(0).getCapacity()* Double.parseDouble(inputPrice.getText())));  
                        rockets.remove(sel.get(0));
                        }catch(NumberFormatException  numExp){
                            alertBox("Error", "Please enter a number errMsg: "+  numExp.getMessage());
                        }         
                        });             
            //need 2 buttons for
            //FINALIZE launchVb
                        launchVb.getChildren().addAll(rocketLabel,rocketOptionsHb);
                        launchVb.getChildren().addAll(missionLabel, missionBtn);
                        launchVb.getChildren().addAll(commercialLabel, rocketCbHbox);
                        launchVb.getChildren().addAll(setHb, launchBtn);
             ///PLAYGROUND
        ///PLAYGROUND            
//joining everything together
        parentContainer.setTop(menuBar);
        parentContainer.setCenter(splitContainer);
//manage Part of the splitcontainer is being joined below
//manage part is acutally made up of a VBOX so to avoid confusing the "to whom it may concern" I've made that container right here        
        VBox manageVb=  new VBox();
        manageVb.getChildren().addAll(manageHb,inputHbox,table, financeContainer);
//splitContainer will now add the manageVB and LanuchVB
        //two big portions of the app are added below 
        splitContainer.getItems().add(manageVb);
        splitContainer.getItems().add(launchVb);
        //parenet container contains spli container
        Scene scene = new Scene(parentContainer, 1280,720);
//adding css to the scene
        scene.getStylesheets().add("com/scms/main/styles.css");
//        we also need to switch scenes in the
        //adding video entry
        Media media = new Media(new File("E:\\Documents\\Semester 2\\Java OOP\\Project\\TechnoSpace\\src\\com\\scms\\video\\intro.mp4").toURI().toString());  
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);  
        mediaPlayer.setAutoPlay(true);
        Group root = new Group();  
        root.getChildren().add(mediaView);  
        
        Scene videoScene = new Scene(root,1280,720);
  //introvideoscene
        primaryStage.setScene(videoScene);
        //setScene on mouse click on tghe video
         root.setOnMouseClicked(e->{
       primaryStage.setScene(scene);
       });
         //final
        primaryStage.show();     
    }
    public static void hireInBulkClicked(){
        //hire in bulk opens a window forstuff to be done 
        GridPane layout = new GridPane();

        Label numberOfEmployees = new Label("Specify number of employees: ");
        TextField inputNumber = new TextField();
        Label salaryLabel = new Label("Salary of the employees: ");
        TextField inputSalary = new TextField();
        
        Label typeLabel = new Label("Type of employees");
        ComboBox typeCb = new ComboBox();
        typeCb.getItems().addAll("Engineer", "Cook", "Astronaut", "Doctor", "Executive");
        typeCb.setValue("Engineer");
        Button hireBtn = new Button("HIRE!!!!");
        
        hireBtn.setOnAction(e->{
            //looking for the number format exception so that if the user enters the wrongvalue we showana aolert box 
           try{
                if(Finance.currentFunds.get() < Double.parseDouble(inputNumber.getText()) * Double.parseDouble(inputSalary.getText())){
                
                alertBox("$$$$$", "Not enough money");
                
                } 
                else {
                    //if the users selectes a specific choice fromthe options then add toemployees,it runs a hire in bulkfn and hires them forn times 
                    for(int i = 0; i <Integer.parseInt(inputNumber.getText()); i++){
                        if(typeCb.getSelectionModel().getSelectedItem().equals("Engineer")){
                            employees.add(new Engineer(generateRandomName(), (int)(Math.random()*20) + 18, Double.parseDouble(inputSalary.getText())));
                        }else if(typeCb.getSelectionModel().getSelectedItem().equals("Doctor")){
                            employees.add(new Doctor(generateRandomName(), (int)Math.random()*20 + 18, Double.parseDouble(inputSalary.getText())));
                        }else if(typeCb.getSelectionModel().getSelectedItem().equals("Astronaut")){
                            employees.add(new Astronaut(generateRandomName(), (int)Math.random()*20 + 18, Double.parseDouble(inputSalary.getText())));
                        }else if(typeCb.getSelectionModel().getSelectedItem().equals("Cook")){
                            employees.add(new Cook(generateRandomName(), (int)Math.random()*20 + 18, Double.parseDouble(inputSalary.getText())));
                        }else{
                            employees.add(new Executive(generateRandomName(), (int)Math.random()*20 + 18, Double.parseDouble(inputSalary.getText())));
                        }
                    }
                }
            }catch(NumberFormatException exp){
              
                    alertBox("Error", "Invalid value entered + errMessage : "+ exp.getMessage());
                    }
          Finance.burnRate.set(Finance.getBurnRate() );
           Main.noOfEmployees.set(employees.size());
          Finance.totalEmployeesExpenditure.set(Finance.getTotalEmployeesExpenditure());
         
        });
        //simpple jfx layuout below 
        layout.add(numberOfEmployees, 0,0);
        layout.add(inputNumber, 1,0);
        layout.add(salaryLabel, 0,1);
        layout.add(inputSalary, 1,1);
        layout.add(typeCb, 0,2);
        layout.add(typeLabel, 1,2);
        layout.add(hireBtn, 1,3);
        
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(layout, 250,250);
        stage.setScene(scene);
        stage.showAndWait();
    }
    //this function fires on handling add button in the hiring proces
    public void hireBtnClicked(){
        
       //hiring is a bit tricky, let's create add a combobox for type
        Stage inputPopUp = new Stage();
         //To input employees
               //name input
                nameInput = new TextField();
                nameInput.setPromptText("Name");
                nameInput.setMinWidth(80);
               //age input 
                ageInput = new TextField();
                ageInput.setPromptText("Age");
               //salary input 
                salaryInput = new TextField();
                salaryInput.setPromptText("Salary");
                //typeInput
                typeInput = new ComboBox();
                typeInput.getItems().addAll("Engineer", "Astronaut", "Cook", "Doctor", "Executive");
                typeInput.setPromptText("Employee Type");
                typeInput.setValue("Engineer");
//vbox will be the layout for our pop up window to get user niput
        VBox  vb = new VBox();
        //name hbox
        //I have also created a grid that makes sure the fields are alligned
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
     //addng name input to grid    
        Label nameText =new Label("Name: ");
        grid.add(nameText, 0,0);
        grid.add(nameInput, 1,0);
     //adding age input to grid  
        Label ageText =new Label("Age: ");
        grid.add(ageText, 0,1);
        grid.add(ageInput,1,1);
     //adding name salaryinput below
        Label salaryText =  new Label("Salary: ");
        grid.add(salaryText,0,2);
        grid.add(salaryInput,1,2);
     //adding salaryInput below to the grid
        Label typeText = new Label("Type: ");
        grid.add(typeText,0,3);
        grid.add(typeInput,1,3);
        //HireButton in popup, and also if the user chooses to close
        Button hireBtnPopUp = new Button("Hire");
        Button close = new Button("Close");
            close.setOnAction(e->inputPopUp.close());
          //gridpane for the buttons
        GridPane buttons = new GridPane();
        buttons.add(hireBtnPopUp,0,0);
        buttons.add(close,1,0);
        buttons.setHgap(10);
        buttons.setAlignment(Pos.CENTER);
        
        grid.add(buttons,1,4);
        
        //then we add all the hboxses to our main laout for the popup
        vb.getChildren().addAll(grid);
        
        //handler for the popuphire button to hire the employee/process the data entered
        hireBtnPopUp.setOnAction(e->{
        String selectedType = ""+ typeInput.getValue();
        String name =  nameInput.getText();
        try{
            int id = employees.size() + 1;
        int age = Integer.parseInt(ageInput.getText());
        double salary = Double.parseDouble(salaryInput.getText());
      //if funds are here only buy then
        if(Finance.currentFunds.get() >=salary){
           
        if(selectedType.equals("Engineer") ){
            employees.add(new Engineer(name,age,salary));
        }else if(selectedType.equals("Cook") ){
            employees.add(new Cook(name,age,salary));
        }else if(selectedType.equals("Astronaut")  ){
            employees.add(new Astronaut( name,age,salary));
        }else if(selectedType.equals("Doctor")  ){
            employees.add(new Doctor( name,age,salary));
        }else{
            //exec
            employees.add(new Executive(name,age,salary));
        }
                Finance.updateTotalEmployeesExpenditure();
                noOfEmployees.set(employees.size());
       }
        else {
            alertBox("ALERT", "Not enough funds :( Declare bankrupcy lol ;)");
        }
        nameInput.clear();
        ageInput.clear();
        salaryInput.clear();
     }catch(NumberFormatException exp){
         alertBox("Wrong input", "Age or Salary is not a number \nError Message:"+exp.getMessage() );
     }
        
  });
        
        Scene scene = new Scene(vb,200,200);
        inputPopUp.initModality(Modality.APPLICATION_MODAL);
        inputPopUp.setScene(scene);
        inputPopUp.showAndWait();
    }   
    //deletes the selected
    public void fireBtnClicked(){
        ObservableList<Employee> selectedEmployees,allEmployees;
        allEmployees = table.getItems();
        selectedEmployees =  table.getSelectionModel().getSelectedItems();
        //allEmployees.remove, :: operatoir is the method reference operator, goes through list of selected employees and removes them from allEmployees
        ///like
        System.out.println(selectedEmployees.get(0).getName());
        //selectedEmployees.forEach(allEmployees::  remove);
        allEmployees.remove(selectedEmployees.get(0));
        //
        Finance.updateTotalEmployeesExpenditure();
                noOfEmployees.set(employees.size());
        
    }
    //creating an alertbox
    public static void alertBox(String title, String message) {
        Stage stage = new Stage();

        //Must close this
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);
        stage.setMinWidth(250);

        Label label = new Label();
        label.setText(message);
        label.setFont(Font.font("consolas", 15));
        VBox vb = new VBox();
        vb.getChildren().addAll(label);
        vb.setAlignment(Pos.CENTER);

        //close button
        Button close = new Button("Close");
        vb.getChildren().add(close);
        close.setOnAction(e->stage.close());
        Scene scene = new Scene(vb,700,200 );
        stage.setScene(scene);
        stage.showAndWait();
    }
    
    //Build a rocket Window
    public static void buildRocketClicked(){
        //name/capacity/priceTag
        GridPane layout = new GridPane();
        Label name = new Label("Name");
        TextField nameInput = new TextField();
        
        Label capacity = new Label("Capacity");
        TextField capacityInput = new TextField();
        
        Button priceTag = new Button("Money Required to build");
        Label price = new Label();
        
        Button buildBtn = new Button("Build");
        
        
        
        buildBtn.setOnAction(e->{
        try{
            //check if user entered wrong stuff 
            String constructorName = nameInput.getText();
            double constructorCap = Double.parseDouble(capacityInput.getText());
            double constructorPriceTag = constructorCap * 44000;

            int engineersRequired =  Integer.parseInt(capacityInput.getText())/10;

            int engineersPresent = 0;
            for(Employee employee: employees){ 
            if(employee instanceof Engineer){
                engineersPresent++;
            }
            }
            if(engineersPresent < engineersRequired ){
                alertBox("ERROR", "You don't have enough Engineers to build the rocket!!!");
            }
            else if(Finance.currentFunds.get()-constructorPriceTag < 0){
                alertBox("Error", "You Don't have enough Money to build a rocket");
            }
            else{
            
                rockets.add(new Rocket(constructorName, constructorCap, 36000)); 
                alertBox("Rocket Built", "Ignition Sequence Starts.... 10- 9- 8 - 9 "+constructorName+" is go for launch");
                Finance.buildRocket(constructorPriceTag);
            }
  
            
        }catch(NumberFormatException numExp){
            alertBox("Error", "You entered wrong Values Error msg = " +numExp.getMessage() );
        }
        });
         priceTag.setOnAction(eventPriceTag->{price.setText(String.format("%f", Double.parseDouble(capacityInput.getText()) * 44000));});
        //finalize the layout 
        layout.add(name, 0,0);
        layout.add(nameInput,1,0);
        
        layout.add(capacity, 0,1);
        layout.add(capacityInput,1,1);

        layout.add(priceTag, 0,2);
        layout.add(price,1,2);
        
        layout.add(buildBtn, 1,3);
//now finalize the scene
        
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(layout,300,300);
        stage.setScene(scene);
        stage.showAndWait();
        
        
 }       
    public static void launchRocketClicked(){
       
    }
    /////BUYROCKET
    public static void buyRocketClicked(){
        GridPane layout = new GridPane();
       
        Label falconH = new Label("SpaceX Falcon Heavy Capactiy : 37000 lbsPrice : 90,000,000 $");
        Button fhBuy = new Button("Buy Now");
       
        Label falcon9 = new Label("SpaceX Falcon 9 Price : 62,000,000 $");
        Button f9Buy = new Button("Buy Now");
       
        Label starShip = new Label("SpaceX StarShipPrice : 5,000,000,000 $");
        Button starShipBuy = new Button("Buy Now");
       
        //90 million falcon h, 62 M falcon 9
        fhBuy.setOnAction((e)->{
        if(Finance.currentFunds.get() < 90000000){
            alertBox("Error", "Not enough funds");
        }
        else {
            rockets.add(new Rocket("Falcon Heavy",140_000,56000  ));
            Finance.buildRocket(90_000_000);
            alertBox("GOOD PURCHASE", "Falcon Heavy bought for 140,000,000 $");
        }
        });
        //falcon 9
        //90 million falcon h, 62 M falcon 9
        f9Buy.setOnAction((e)->{
        if(Finance.currentFunds.get() < 63000000){
            alertBox("Error", "Not enough funds");
        }
        else {
            rockets.add(new Rocket("Falcon 9",55_000,56000));
            Finance.buildRocket(63_000_000);
            alertBox("GOOD PURCHASE", "Falcon 9 bought for 63,000,000 $");
        }
        });
        
        //90 million falcon h, 62 M falcon 9
        starShipBuy.setOnAction((e)->{
        if(Finance.currentFunds.get() < 5000000000.0){
            alertBox("Error", "Not enough funds");
        }
        else {
            rockets.add(new Rocket("StarShip",330_000,56000  ));
            Finance.buildRocket(5000000000.0);
            alertBox("GOOD PURCHASE", "StarShip bought for 5,000,000,000 $");
        }
        });
        
        layout.add(falconH, 0,0);
        layout.add(fhBuy,1,0);
        
        layout.add(falcon9,0,1);
        layout.add(f9Buy, 1,1);
        
        layout.add(starShip, 0,2);
        layout.add(starShipBuy,1,2);
        
     
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(layout,500,300);
        stage.setScene(scene);
        stage.showAndWait();
    
    }
  //buiding the launch new mIsson window psss,"My hands were freezing when I wrote this"
    public static void launchMission(){
        GridPane layout = new GridPane ();
        Label destinationLabel= new Label("Destination");
        ComboBox destinationCb= new ComboBox();
        destinationCb.getItems().addAll("Mercury","Moon", "Mars", "Jupiter", "Saturn");
        destinationCb.setValue("Mars");
        
        Button launch  = new Button("LAUNCH");
        
        layout.add(destinationLabel, 0 , 0);
        layout.add(destinationCb, 0,1);
        layout.add(launch, 0,2);
        layout.add(new Label("First Select the Rocket from the Main Rockets Table"),0 ,3);
       
        launch.setOnAction(e->{
                ObservableList<Rocket> sel,all;
                all = rocketsTable.getItems();
                sel = rocketsTable.getSelectionModel().getSelectedItems();
                try{
                    
               if(destinationCb.getValue().equals("Mercury")){
                  //1 $ per distance in space even though it will delete by a function or maybe exponentially but this isn't a program for nasa
                   if(Finance.currentFunds.get() < 271_000_000 || rockets.size() == 0){
                       alertBox("not enough money", "You can't go there you don't have enough resources");
                   }
                   else if(sel.size() == 0){
                       alertBox("Not selected", "please select a rocket from the main window first");
                   }
                   else {
                       Finance.buildRocket(271_000_000);
                       rockets.remove(sel.get(0));
                   }
               } else if(destinationCb.getValue().equals("Moon")){
                   
                    if(Finance.currentFunds.get() < 238_000 || rockets.size() == 0){
                       alertBox("not enough money", "You can't go there you don't have enough resources");
                   }
                   else if(sel.size() == 0){
                       alertBox("Not selected", "please select a rocket from the main window first");
                   }
                   else {
                       Finance.buildRocket(238_000);
                       rockets.remove(sel.get(0));
                   }
               }else if(destinationCb.getValue().equals("Mars")){
                   
                     if(Finance.currentFunds.get() < 244_000_000 || rockets.size() == 0){
                       alertBox("not enough money", "You can't go there you don't have enough resources");
                   }
                   else if(sel.size() == 0){
                       alertBox("Not selected", "please select a rocket from the main window first");
                   }
                   else {
                       Finance.buildRocket(244_000_000);
                       rockets.remove(sel.get(0));
                   }
                   
               }else if(destinationCb.getValue().equals("Jupiter")){
                   
                     if(Finance.currentFunds.get() < 782_000_000 || rockets.size() == 0){
                       alertBox("not enough money", "You can't go there you don't have enough resources");
                   }
                   else if(sel.size() == 0){
                       alertBox("Not selected", "please select a rocket from the main window first");
                   }
                   else {
                       Finance.buildRocket(782_000_000);
                       rockets.remove(sel.get(0));
                   }
                   
               }else if(destinationCb.getValue().equals("Saturn")){
                   
                     if(Finance.currentFunds.get() < 1_444_000_000 || rockets.size() == 0){
                       alertBox("not enough money", "You can't go there you don't have enough resources");
                   }
                   else if(sel.size() == 0){
                       alertBox("Not selected", "please select a rocket from the main window first");
                   }
                   else {
                       Finance.buildRocket(1_444_000_000);
                       rockets.remove(sel.get(0));
                   }
                   
               }
                
                }catch(NumberFormatException  numExp){
                    alertBox("Error", "Please enter a number errMsg: "+  numExp.getMessage());
                }
        });
        
        Stage stage = new Stage();
       
        Scene scene = new Scene(layout,500,300);
        stage.setScene(scene);
        stage.showAndWait();
    
    }
    /////////////AUXILIARIES
    public static String generateRandomName(){
        String name = "";
        for(int i = 0; i < 7;i ++){
            name = name + (char)((Math.random()*(26) )+ 97);
        }
        
        return name;
    }
    public static void setTimeout(Runnable runnable, int delay){
    new Thread(() -> {
        try {
            Thread.sleep(delay);
            runnable.run();
        }
        catch (Exception e){
            System.err.println(e);
        }
    }).start();
}
  
    
}
