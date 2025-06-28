package org.example.atmfx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import database.Dbwork;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import java.sql.ResultSet;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class LoginController {
    @FXML
    private AnchorPane MainPane,minis;
    //panes
    @FXML
    private Pane pane4, pane2, pane3, Loginpane, paneb, panec,pane_trans;
    @FXML
    private ScrollPane paned;

    //elements of login pane
    @FXML
    public TextField t_user,t_pass;
    @FXML
    public Label login_error;
    //Date Day Label
    @FXML
    private Label dxt_tmt;


    //Element of Main Pane
    @FXML
    public Label balance,user_x;
    @FXML
    private Button show_balance;
    @FXML
    public Button Trans;
    @FXML
    public TextField wit_Pin;


    //Elements of Transfer Panel
    @FXML
    public Label Transfer_Error;
    @FXML
    public Button transferbutton;
    @FXML
    public TextField Transfer_id,Transfer_Amount,Transfer_Pin;


    //Elements of pane b
    @FXML
    public RadioButton c1, c2,c3,c4, c5,c6;
    @FXML
    public Button withdraw,confirmation;
    @FXML
    private Label quick_Error,qPin, select;
    @FXML
    public TextField quick_Pin;
    @FXML
    private int quick_amount;


    //Elements of Pane3
    @FXML
    public Button Exit_out;


    //Elements of change pin
    @FXML
    private Label m1,m2,m3,change_error_msg;
    @FXML
    private TextField current_pin,pin1,pin2;
    @FXML
    private Button Change_pin;

    //elements of pane_trans
    @FXML
    private Label l1,l2,error_trans;
    @FXML
    private TextField trans_amount;
    @FXML
    private Button withdraw_money;


    //other objects used incode
    private String UserId;

    private Dbwork dbwork = new Dbwork();
    private int bal_xe;



    //Function to Initialize the Stage
    @FXML
    public void initialize() {
        dxt_tmt.setText(Datetime());
        Loginpane.setVisible(true);
        pane4.setVisible(false);
        pane2.setVisible(false);
        pane3.setVisible(false);
        paneb.setVisible(false);
        panec.setVisible(false);
        paned.setVisible(false);
        pane_trans.setVisible(false);
        confirmation.setVisible(false);
        qPin.setVisible(false);
        quick_Pin.setVisible(false);
    }

    private void makeResponsive() {
        Loginpane.prefWidthProperty().bind(MainPane.widthProperty().multiply(0.6));
        Loginpane.prefHeightProperty().bind(MainPane.heightProperty().multiply(0.6));


        Loginpane.layoutXProperty().bind(MainPane.widthProperty().subtract(Loginpane.widthProperty()).divide(2));
        Loginpane.layoutYProperty().bind(MainPane.heightProperty().subtract(Loginpane.heightProperty()).divide(2));

        // Adjust text size based on window size
        dxt_tmt.styleProperty().bind(MainPane.widthProperty().divide(30).asString("-fx-font-size: %.0fpx;"));
    }


    //Function to set the Date and time
    public String Datetime(){
        LocalDate date = LocalDate.now();
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        DateTimeFormatter  dxt =DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String txt = dayOfWeek+" , "+dxt.format(date)+"  ";
        return txt;
    }

    //Function to Login_Pane
    @FXML
    public void LoginEnter(ActionEvent e){

        UserId = t_user.getText();

        if(t_user.getText().trim().isEmpty()||t_pass.getText().trim().isEmpty()){
            login_error.setText("Fields can't be empty.");
            return;
        }
        if(t_user.getText().length()<=10&&dbwork.exists(UserId))
        {
            if (dbwork.compare_passkey(t_user.getText(), t_pass.getText()))
            {
                Loginpane.setVisible(false);
                pane4.setVisible(true);
                pane2.setVisible(true);
                pane3.setVisible(true);
                t_user.setText("");
                t_pass.setText("");
                login_error.setText("");
                user_x.setText(dbwork.getUsername(UserId));
                bal_xe = dbwork.getBalance(UserId);
                balance.setText("RS.********");
                show_balance.setText("SHOW");
            }
            else
            {
                login_error.setText("Wrong Credentials");
                t_pass.setText("");
            }
        }
            else {
            login_error.setText("User does not Exist");
            t_pass.setText("");
            t_user.setText("");
        }
    }


    //Function of Pane1
    @FXML
    public void showBalance(ActionEvent e){
       if (show_balance.getText().equals("SHOW")) {
            balance.setText("RS. "+ String.valueOf(bal_xe));
          show_balance.setText("HIDE");
        } else if(show_balance.getText().equals("HIDE")) {
            balance.setText("RS.********");
          show_balance.setText("SHOW");
        }
    }


    //Function to switch the Panes after Login
    public void actionPane1(ActionEvent e){
        pane2.setVisible(true);
        paneb.setVisible(false);
        panec.setVisible(false);
        paned.setVisible(false);
        pane_trans.setVisible(false);
        minis.getChildren().clear();
    }

    public void actionPane2(ActionEvent e){
        pane2.setVisible(false);
        paneb.setVisible(true);
        panec.setVisible(false);
        paned.setVisible(false);
        pane_trans.setVisible(false);
        minis.getChildren().clear();
    }

    public void actionPane3(ActionEvent e){
        pane2.setVisible(false);
        paneb.setVisible(false);
        panec.setVisible(true);
        paned.setVisible(false);
        pane_trans.setVisible(false);
        minis.getChildren().clear();
    }

    public void actionPane4(ActionEvent e) {
        int prefheight = 200;
        int x =25;
        int y = 50;
        int i = 1;

        Button button = new Button();
        button.setText("X");
        button.setTextFill(Color.WHITE);
        button.setStyle("-fx-background-color: red; -fx-background-radius: 6; -fx-border-width: 2; -fx-border-radius: 6;-fx-font-weight: bold;");
        button.setPrefHeight(26);
        button.setPrefWidth(37);
        button.setLayoutX(500);
        button.setLayoutY(8);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                pane2.setVisible(true);
                minis.getChildren().clear();
                paned.setVisible(false);
            }
        });
        minis.getChildren().add(button);

        try {
            Pane newpane1 = new Pane();
            newpane1.setLayoutX(20);
            newpane1.setLayoutY(y);
            newpane1.setPrefWidth(500);
            newpane1.setPrefHeight(50);
            newpane1.setStyle("-fx-background-radius: 7; -fx-border-radius: 7; -fx-border-color: black; -fx-border-width: 1;");

            Label serialNoLabel = new Label("S. no");
            serialNoLabel.setLayoutX(15.0);
            serialNoLabel.setLayoutY(15.0);
            serialNoLabel.setPrefHeight(20.0);
            serialNoLabel.setPrefWidth(36.0);
            serialNoLabel.setFont(new Font("Arial Black", 12.0));
            newpane1.getChildren().add(serialNoLabel);

            Label dateLabel = new Label("Date");
            dateLabel.setLayoutX(90.0);
            dateLabel.setLayoutY(15.0);
            dateLabel.setPrefHeight(20.0);
            dateLabel.setPrefWidth(36.0);
            dateLabel.setFont(new Font("Arial Black", 12.0));
            newpane1.getChildren().add(dateLabel);

            Label timeLabel = new Label("Time");
            timeLabel.setLayoutX(180.0);
            timeLabel.setLayoutY(15.0);
            timeLabel.setPrefHeight(20.0);
            timeLabel.setPrefWidth(36.0);
            timeLabel.setFont(new Font("Arial Black", 12.0));
            newpane1.getChildren().add(timeLabel);

            Label balanceLabel = new Label("Balance");
            balanceLabel.setLayoutX(400.0);
            balanceLabel.setLayoutY(15.0);
            balanceLabel.setPrefHeight(20.0);
            balanceLabel.setPrefWidth(56.0);
            balanceLabel.setFont(new Font("Arial Black", 12.0));
            newpane1.getChildren().add(balanceLabel);

            Label amountLabelx = new Label("Amount");
            amountLabelx.setLayoutX(290.0);
            amountLabelx.setLayoutY(15.0);
            amountLabelx.setPrefHeight(20.0);
            amountLabelx.setPrefWidth(63.0);
            amountLabelx.setFont(new Font("Arial Black", 12.0));
            newpane1.getChildren().add(amountLabelx);

            minis.getChildren().add(newpane1);
            ResultSet resultSet = dbwork.getTransactions(UserId);
            int balance = dbwork.getBalance(UserId);


            while (resultSet.next()) {
                if (i > 2) {
                    prefheight+=56;
                }
                y+=55;
                Pane newpane = new Pane();
                newpane.setLayoutX(20);
                newpane.setLayoutY(y);
                newpane.setPrefWidth(500);
                newpane.setPrefHeight(50);
                newpane.setStyle("-fx-background-radius: 7; -fx-border-radius: 7; -fx-border-color: black; -fx-border-width: 1;");

                Label sNoLabel = new Label(String.valueOf(i));
                sNoLabel.setLayoutX(15.0);
                sNoLabel.setLayoutY(15.0);
                sNoLabel.setPrefHeight(20.0);
                sNoLabel.setPrefWidth(36.0);
                sNoLabel.setFont(new Font("Arial Black", 12.0));
                newpane.getChildren().add(sNoLabel);

                String this_label =String.valueOf(resultSet.getDate(2));
                Label datLabel = new Label(this_label);
                datLabel.setLayoutX(90.0);
                datLabel.setLayoutY(15.0);
                datLabel.setPrefHeight(20.0);
                datLabel.setPrefWidth(100.0);
                datLabel.setFont(new Font("Arial Black", 11.0));
                newpane.getChildren().add(datLabel);

                String this_time = String.valueOf(resultSet.getTime(3));
                Label timeLabelx = new Label(this_time);
                timeLabelx.setLayoutX(180.0);
                timeLabelx.setLayoutY(15.0);
                timeLabelx.setPrefHeight(20.0);
                timeLabelx.setPrefWidth(100.0);
                timeLabelx.setFont(new Font("Arial Black", 11.0));
                newpane.getChildren().add(timeLabelx);

                int amount_xxx = resultSet.getInt(1);

                String this_amount = String.valueOf(amount_xxx);
                Label amountLabel = new Label(String.valueOf(this_amount));
                amountLabel.setLayoutX(290.0);
                amountLabel.setLayoutY(15.0);
                amountLabel.setPrefHeight(20.0);
                amountLabel.setPrefWidth(100.0);
                amountLabel.setFont(new Font("Arial Black", 12.0));
                newpane.getChildren().add(amountLabel);

                Label balanceLabelx = new Label();
                if(!resultSet.getBoolean(4)){
                    datLabel.setTextFill(Color.RED);
                    sNoLabel.setTextFill(Color.RED);
                    timeLabelx.setTextFill(Color.RED);
                    amountLabel.setTextFill(Color.RED);
                    balanceLabelx.setTextFill(Color.RED);
                    balance+=amount_xxx;
                }else{
                    balance-=amount_xxx;
                    datLabel.setTextFill(Color.GREEN);
                    sNoLabel.setTextFill(Color.GREEN);
                    timeLabelx.setTextFill(Color.GREEN);
                    amountLabel.setTextFill(Color.GREEN);
                    balanceLabelx.setTextFill(Color.GREEN);
                }

                balanceLabelx.setText(String.valueOf(String.valueOf(balance)));
                balanceLabelx.setLayoutX(400.0);
                balanceLabelx.setLayoutY(15.0);
                balanceLabelx.setPrefHeight(20.0);
                balanceLabelx.setPrefWidth(100.0);
                balanceLabelx.setFont(new Font("Arial Black", 10.0));
                newpane.getChildren().add(balanceLabelx);

                minis.setPrefHeight(prefheight);
                minis.getChildren().add(newpane);
                ++i;
            }
        }
        catch (Exception ex){
            Label label = new Label();
            label.setText("Error Fetching");
            label.setTextFill(Color.RED);
            label.setLayoutX(250);
            label.setLayoutY(200);
            minis.getChildren().add(label);
        }
        paned.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        paned.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        paned.setPannable(true);
        pane2.setVisible(false);
        paneb.setVisible(false);
        panec.setVisible(false);
        paned.setVisible(true);
        pane_trans.setVisible(false);
    }

    @FXML
    public void switch_trans(ActionEvent e){
        pane2.setVisible(false);
        paneb.setVisible(false);
        panec.setVisible(false);
        paned.setVisible(false);
        pane_trans.setVisible(true);
    }

    //Event on Transfer
    public void transfer_money(ActionEvent e){
        String id = Transfer_id.getText();
        String amount = Transfer_Amount.getText();
        String pin = Transfer_Pin.getText();
        int amxnt = 0, pxn = 0;
        if(id.equals(UserId)){
            Transfer_Error.setText("You cant send Money to yourself.");
        }
        if(id.trim().isEmpty()||amount.trim().isEmpty()||pin.trim().isEmpty()){
            Transfer_Error.setText("Fields can't be empty");
            return;
        }
        try{
            amxnt = Integer.parseInt(amount);
            pxn= Integer.parseInt(pin);
        }catch (Exception ex){
            Transfer_Error.setText("Illegal Format");
            return;
        }
        if(!dbwork.exists(id)){
            Transfer_Error.setText("User does not exist");
            return;
        }
        String sx = dbwork.TransferMoney(UserId,amxnt,id,pxn);
        Transfer_Error.setText(sx);
        if(sx.equals("Success")){
            Transfer_Error.setTextFill(Color.GREEN);
            Transfer_Pin.setText("");
            Transfer_Amount.setText("");
            Transfer_id.setText("");
            Transfer_Pin.setEditable(false);
            Transfer_id.setEditable(false);
            Transfer_Amount.setEditable(false);
            transferbutton.setDisable(true);
            bal_xe= dbwork.getBalance(UserId);
            balance.setText("RS."+String.valueOf(bal_xe));
        }
    }


    //For quick withdraw
    public void withdraw_money(ActionEvent e){
        if(c1.isSelected()){
            quick_amount=500;
        }else if(c2.isSelected()){
            quick_amount=1000;
        }else if(c3.isSelected()){
            quick_amount=2500;
        }else if(c4.isSelected()) {
            quick_amount=5000;
        }else if(c5.isSelected()){
            quick_amount=10000;
        }else if(c6.isSelected()){
            quick_amount=50000;
        }else{
            return;
        }
        c1.setVisible(false);
        c2.setVisible(false);
        c3.setVisible(false);
        c4.setVisible(false);
        c5.setVisible(false);
        c6.setVisible(false);
        select.setVisible(false);
        withdraw.setVisible(false);
        confirmation.setVisible(true);
        qPin.setVisible(true);
        quick_Pin.setVisible(true);
    }

    @FXML
    public void quick_confirm(ActionEvent event){
        int x=0;
        try{
            x = Integer.parseInt(quick_Pin.getText());
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            quick_Error.setText("Illegal Format");
            return;
        }
        String msq = dbwork.access_transaction(UserId,x,quick_amount);
        quick_Error.setText(msq);
        if(msq.equals("Success")) {
            quick_Error.setTextFill(Color.GREEN);
            System.out.println(quick_amount);
            quick_Pin.setText("");
            quick_Pin.setEditable(false);
            confirmation.setDisable(true);
            bal_xe=dbwork.getBalance(UserId);
            balance.setText("RS."+String.valueOf(bal_xe));
        }else{
            quick_Pin.setText("");
        }
    }

    @FXML
    public void changePIN(ActionEvent e) {
        int p1 = 0;
        int p2 = 0;
        if (pin1.getText().isEmpty() || pin2.getText().isEmpty() || current_pin.getText().isEmpty()) {
            change_error_msg.setText("Fields cannot be empty");
        } else {
            if (pin1.getText().equals(pin2.getText())) {
                if(pin1.getText().length()>5||current_pin.getText().length()>5)
                {
                    change_error_msg.setText("Pin is long");
                }
                try{
                    p2 = Integer.parseInt(current_pin.getText());
                    p1 = Integer.parseInt(pin1.getText());
                }catch (Exception ex){
                    change_error_msg.setText("Illegal Format");
                    System.out.println(ex.getMessage());
                    return;
                }
                String msg = dbwork.set_new_Pin(UserId,p2,p1);
                change_error_msg.setText(msg);
                if(msg.equals("Success")){
                    change_error_msg.setTextFill(Color.GREEN);
                    pin2.setText("");
                    pin1.setText("");
                    current_pin.setText("");
                    pin1.setEditable(false);
                    pin2.setEditable(false);
                    current_pin.setEditable(false);
                    Change_pin.setDisable(true);
                }
            } else change_error_msg.setText("The passwords don't match");
        }
    }

    @FXML
    public void withdraw_Amount(ActionEvent e)
    {
        int amount=0,pin=0;
        if(wit_Pin.getText().length()>5){
            error_trans.setText("Pin is long");
            wit_Pin.setText("");
            return;
        }
        if(trans_amount.getText().trim().isEmpty()||wit_Pin.getText().trim().isEmpty()){
            error_trans.setText("Field is Empty");
            return;
        }
        try{
            amount=Integer.parseInt(trans_amount.getText());
            pin =Integer.parseInt(wit_Pin.getText());
        }catch (Exception err){
            error_trans.setText("Illegal Format");
            System.out.println(err.getMessage());
            return;
        }
        if(amount>250000){
            error_trans.setText("Amount is greater than 2500000");
            return;
        }
        String msk = dbwork.access_transaction(UserId,pin,amount);
        error_trans.setText(msk);
        if(msk.equals("Success")){
            error_trans.setTextFill(Color.GREEN);
            withdraw_money.setDisable(true);
            trans_amount.setText("");
            wit_Pin.setText("");
            trans_amount.setEditable(false);
            wit_Pin.setEditable(false);
            bal_xe = dbwork.getBalance(UserId);
            balance.setText("RS."+String.valueOf(bal_xe));
        }
    }

    public void Exit_App(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Confirmation");
        alert.setContentText("Are you sure you want to Exit");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent()&&result.get()==ButtonType.OK){
            pane2.setVisible(false);
            pane3.setVisible(false);
            pane4.setVisible(false);
            paneb.setVisible(false);
            panec.setVisible(false);
            paned.setVisible(false);
            pane_trans.setVisible(false);
            Loginpane.setVisible(true);
            trans_amount.setEditable(true);
            wit_Pin.setEditable(true);
            error_trans.setText("");
            error_trans.setTextFill(Color.RED);
            withdraw_money.setDisable(false);
            current_pin.setEditable(true);
            pin1.setEditable(true);
            pin2.setEditable(true);
            Change_pin.setDisable(false);
            change_error_msg.setText("");
            change_error_msg.setTextFill(Color.RED);
            select.setVisible(true);
            c1.setVisible(true);
            c2.setVisible(true);
            c3.setVisible(true);
            c4.setVisible(true);
            c5.setVisible(true);
            c6.setVisible(true);
            confirmation.setVisible(false);
            quick_Pin.setVisible(false);
            quick_Pin.setEditable(true);
            qPin.setVisible(false);
            withdraw.setVisible(true);
            confirmation.setDisable(false);
            quick_Error.setText("");
            quick_Error.setTextFill(Color.RED);
            Transfer_id.setEditable(true);
            Transfer_Amount.setEditable(true);
            Transfer_Pin.setEditable(true);
            transferbutton.setDisable(false);
            Transfer_Error.setText("");
            Transfer_Error.setTextFill(Color.RED);
            UserId="";
            bal_xe=0;
        }
    }

}
