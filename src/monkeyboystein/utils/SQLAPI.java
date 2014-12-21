package monkeyboystein.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.*;

/**
 * Created by Andrew on 11/29/2014.
 */
public class SQLAPI {

    public SQLAPI(String u, String user, String pass, int port, String db)
    {
        this.url =  "jdbc:mysql://" + u + ":" + port + "/" + db;
        this.user = user;
        this.password = pass;
        try
        {
            update();
        }catch(Exception ex){ex.printStackTrace();}

    }


    public int getGems(String name)
    {
        int g = 0;
        try
        {


            String query = "SELECT * FROM player_data.players";
            // create the java statement
            Statement st = con.createStatement();
            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(query);

            // iterate through the java resultset
            while (rs.next()) {
                // int id = rs.getInt("id");
                String playerName = rs.getString("playerName");
                int gems = rs.getInt("gems");
//            String rank = rs.getString("rank");


                if(name.equalsIgnoreCase(playerName))
                {
                    g = gems;



                    //  sender.sendMessage(ChatColor.BLUE + "INFORMATION: playerName(" + playerName + ") timeOnline(" + timeOnline + ") coins("+ coins + ") dateDay(" + dateDay + ") commandRun(" + hasBeenRun + ")");
                }

            }
            rs.close();

        }catch(SQLException ex){}
        return g;
    }


    public void addPurchase(String purchase, String playername)
    {

        String purchases = getPurchases(playername);

        try
        {
            Statement statement = con.createStatement();
            Player p = Bukkit.getPlayer(playername);
            //    String i = rank;
            purchases += ", " + purchase;

            statement.executeUpdate("UPDATE `player_data`.`purchases` SET `purchases`='" + purchases + "' WHERE `playerName`='" + p.getName() + "';");
            statement.close();
            //  sender.sendMessage(ChatColor.BLUE + "Set %player%'s coins to %i%".replaceAll("%player%",p.getName()).replaceAll("%i%",i + ""));
        }catch (Exception ex){}
    }
    public String getPurchases(String player)
    {
        try
        {


            String query = "SELECT * FROM player_data.purchases";
            // create the java statement
            Statement st = con.createStatement();
            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(query);

            // iterate through the java resultset
            while (rs.next()) {
                // int id = rs.getInt("id");
                String playerName = rs.getString("playerName");
                //    int gems = rs.getInt("gems");
                String rank = rs.getString("purchases");


                if(player.equalsIgnoreCase(playerName))
                {
                    return rank;



                    //  sender.sendMessage(ChatColor.BLUE + "INFORMATION: playerName(" + playerName + ") timeOnline(" + timeOnline + ") coins("+ coins + ") dateDay(" + dateDay + ") commandRun(" + hasBeenRun + ")");
                }

            }
            rs.close();

        }catch(SQLException ex){}
        return "NULL";
    }


    public String getRank(String name)
    {
        String g = "Default";
        try
        {


            String query = "SELECT * FROM player_data.players";
            // create the java statement
            Statement st = con.createStatement();
            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(query);

            // iterate through the java resultset
            while (rs.next()) {
                // int id = rs.getInt("id");
                String playerName = rs.getString("playerName");
                //    int gems = rs.getInt("gems");
                String rank = rs.getString("rank");


                if(name.equalsIgnoreCase(playerName))
                {
                    g = rank;



                    //  sender.sendMessage(ChatColor.BLUE + "INFORMATION: playerName(" + playerName + ") timeOnline(" + timeOnline + ") coins("+ coins + ") dateDay(" + dateDay + ") commandRun(" + hasBeenRun + ")");
                }

            }
            rs.close();

        }catch(SQLException ex){}
        return g;
    }


    public void setRank(String name, String rank)
    {
        try
        {
            Statement statement = con.createStatement();
            Player p = Bukkit.getPlayer(name);
            String i = rank;

            statement.executeUpdate("UPDATE `player_data`.`players` SET `rank`='" + i + "' WHERE `playerName`='" + p.getName() + "';");
            statement.close();
            //  sender.sendMessage(ChatColor.BLUE + "Set %player%'s coins to %i%".replaceAll("%player%",p.getName()).replaceAll("%i%",i + ""));
        }catch (Exception ex){}
    }
    public void setGems(String name, int gems)
    {
        try
        {
            Statement statement = con.createStatement();
            Player p = Bukkit.getPlayer(name);
            int i = gems;

            statement.executeUpdate("UPDATE `player_data`.`players` SET `gems`='" + i + "' WHERE `playerName`='" + p.getName() + "';");
            statement.close();
            // sender.sendMessage(ChatColor.BLUE + "Set %player%'s coins to %i%".replaceAll("%player%",p.getName()).replaceAll("%i%",i + ""));
        }catch (Exception ex){}
    }

    public boolean doesExist(String playername) throws SQLException
    {
        DatabaseMetaData md = con.getMetaData();
        ResultSet rs = md.getColumns(null, null, "players", playername);
        if (rs.next()) {
            return true;
        }
        else
        {
            return false;
        }
    }




    public void insert(String player, int gems)
    {
        //   String valuess = " VALUES ( '" +  new java.util.Date().toString() + "', '" + sender + "', '" + message + "', '" + "RECEIVE" + "');";
        String dateDay = "";
        dateDay = new java.util.Date().getDate() + "";
        String valuess = " VALUES ( '" + player + "', '" + gems + "', '" + "Default"  + "');";
        //     Log.i("APP", "INSERT INTO `text_messages`.`texts` (`sender`, `message`, `time`)" + valuess + ");");

        try {


            if(con==null) {
                con = DriverManager.getConnection(url, user, password);
            }
            //  Log.i("AAA", con.isClosed() + ":" + con.getClientInfo());
            Statement st = (Statement) con.createStatement();


            //  st.executeUpdate("INSERT INTO " + table + valuess);
            st.executeUpdate("INSERT INTO `player_data`.`players` (`playerName`, `gems`, `rank`)" + valuess);


            valuess = " VALUES ( '" + player+ "', 'Default');";
            st.executeUpdate("INSERT INTO `player_data`.`purchases` (`playerName`,`purchases`)" + valuess);
            st.close();




        }

        catch (SQLException ex) {
            // Logger lgr = Logger.getLogger(deliveryMain.class.getName());
            //lgr.log(Level.SEVERE, ex.getMessage(), ex);
            //      Log.i("AAA", Log.getStackTraceString(ex));
//            for(int i = 0; i<=10; i++)
//            {
//                if(i==5)
//                {
//                    System.out.print(ex.getMessage());
//                }
//                System.out.print(" ");
//            }
//            for(StackTraceElement t : ex.getStackTrace())
//            {
//                System.out.print(t.toString());
//            }
//            System.out.print(ex.getCause());
        }
    }









    String url = "";
    String user = "";
    String password = "";

    Connection con;


    public void generateTable() throws Exception
    {

        /*
        CREATE TABLE `player_data`.`players` (
  `playerName` VARCHAR(17) NOT NULL,
  `gems` INT NOT NULL,
  `rank` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`playerName`));

         */
        Statement st = con.createStatement();
        st.executeUpdate("CREATE TABLE `player_data`.`players` (" +
                "  `playerName` VARCHAR(17) NOT NULL," +
                "  `gems` INT NOT NULL," +
                "  `rank` VARCHAR(45) NOT NULL," +
                "  PRIMARY KEY (`playerName`));");


        st.executeUpdate("CREATE TABLE `player_data`.`purchases` (" +
                "  `playerName` VARCHAR(17) NOT NULL," +
                "  `purchases` LONGTEXT NOT NULL," +
                "  PRIMARY KEY (`playerName`));");
        st.close();


    }


    public void update() throws Exception
    {
        if(con==null) {
            con = DriverManager.getConnection(url, user, password);
        }
        if(con.isClosed())
        {
            con = DriverManager.getConnection(url, user, password);
        }
    }



}
