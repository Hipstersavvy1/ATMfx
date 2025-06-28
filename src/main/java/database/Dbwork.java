package database;
import java.sql.*;

public class Dbwork {
    private Connection connect;
    public boolean ans;
    private Establish_Connection here = new Establish_Connection();
    public Dbwork(){
       try{
         connect = here.get_connection();
            System.out.println("Connection Established");
           ans=true;
       }catch (Exception ex){
           System.out.println(ex.getMessage());
           ans=false;
        }
    }

    public boolean compare_passkey(String user, String pass) {
        String str = null;
            try {
                PreparedStatement st = connect.prepareStatement("CALL getpass(?)");
                st.setString(1, user);

                ResultSet resultSet = st.executeQuery();
                while (resultSet.next()) {

                    str = resultSet.getString(1);
                }
                resultSet.close();
                st.close();
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
                return false;
            }
         return pass.equals(str);
    }

    public String getUsername(String id){
        try {
            PreparedStatement st=connect.prepareStatement("call getUser(?)");
            st.setString(1,id);
            ResultSet resultSet = st.executeQuery();
            while (resultSet.next()){
                System.out.println(resultSet.getString(1));
                return resultSet.getString(1);
            }

        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return "Username not found";
    }

    public int getBalance(String id){

        try {
            PreparedStatement st=connect.prepareStatement("call getBalance(?)");
            st.setString(1,id);
            ResultSet resultSet = st.executeQuery();
            while (resultSet.next()){
                return resultSet.getInt(1);
            }
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return 0;

    }

    public boolean exists(String id){
        try {
            PreparedStatement st = connect.prepareStatement("Select count(*) from Bankusers where Userid = ?");
            st.setString(1,id);
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                System.out.println(rs.getInt(1));
                return rs.getInt(1)>=1;
            }
        }catch (Exception ex){
            System.out.println(ex.getCause());
            return false;
        }
        return false;
    }

    public boolean comparePin(String id,int px){
        try {
            int pin = 0;
            PreparedStatement statement = connect.prepareStatement("Call getPin(?)");
            statement.setString(1,id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                 pin =resultSet.getInt(1);
            }
            return px==pin;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }


    private String changePin(String id,int oldPin,int newPin){
        if(!exists(id)){
            return "User does not exists";
        }
        if(comparePin(id, oldPin)){
            try {
                PreparedStatement statement = connect.prepareStatement("call newPin(?,?)");
                statement.setString(1,id);
                statement.setInt(2,newPin);
                int x = statement.executeUpdate();
                if(x==1){
                    return "Success";
                }else{
                    return "Internal Error occured";
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
                return "Connection problem";
            }
        }
        return "Pin is incorrect";
    }

    public String set_new_Pin(String id,int old_pin,int new_pin){
        return changePin(id,old_pin,new_pin);
    }


    private String Transact(String id,int amount){
        try
        {
            PreparedStatement x = connect.prepareStatement("call InsertTrans(?,?)");
            x.setString(1,id);
            x.setInt(2,amount);
            int z = x.executeUpdate();
            if(z >= 1){
                return "Success";
        }else {
                return "Failed";
            }
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return "Data Error";

    }

    private String UserTrans(String id,int pin,int amount){
        if(!exists(id)){
            return "User does not Exist";
        }
        if(comparePin(id,pin)){
            if(getBalance(id)>amount) {
                return Transact(id, amount);
            }else{
                return "Not enough Balance";
            }
        }else{
            return "PIN does not match";
        }
    }


    public String access_transaction(String id,int x,int y){
        return UserTrans(id,x,y);
    }


    private String Transfer_M(String id,int amount,String r_id,int pin){
        if(comparePin(id,pin)){
            try {
                PreparedStatement st = connect.prepareStatement("call TransMoney(?,?,?)");
                st.setString(1,id);
                st.setInt(2,amount);
                st.setString(3,r_id);
                int x = st.executeUpdate();
                if(x>=1) return "Success";
                else return "Internal Error";
            }catch (Exception ex){
                return "Data Error";
            }
        }else{
            return "Pin is Incorrect";
        }
    }

    public String TransferMoney(String id,int amount,String r_id,int pin){
        return Transfer_M(id,amount,r_id,pin);
    }

    public ResultSet getTransactions(String id)throws Exception {
            PreparedStatement st = connect.prepareStatement("select amount,t_date,t_time,t_type from transactions where Userid = ? ORDER BY t_date DESC, t_time DESC");
            st.setString(1,id);
            return st.executeQuery();
    }
    }