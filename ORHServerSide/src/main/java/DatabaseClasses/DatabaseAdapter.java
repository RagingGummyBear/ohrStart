package DatabaseClasses;


import DatabaseClasses.DatabaseEntities.BasicUser;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.json.JSONObject;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Darko on 19.11.2016.
 */
public class DatabaseAdapter {


    DataSource ds;
    //Connection con = null;
    //Statement stmt = null;
    //ResultSet rs = null;



    public DatabaseAdapter()
    {
        ds = getDataSource();
    }



    public ArrayList<JSONObject> getAllElementsFromTableWithConditionsAndSorted(String tableName, String[] orderByFields,HashMap<String,String> conditions ){
        ArrayList<JSONObject> list = new ArrayList();


        //preparing the query with all of the necessary parameters

        String query = "select * from "+tableName;
        if(conditions != null)
        if(conditions.keySet().size()>0){

            StringBuilder sb = new StringBuilder();

            sb.append( " WHERE");
            /*
            for(int i = 0 ; i < conditions.keySet().size();i++){
                sb.append( " AND ? like ? ");
            }
            */
            boolean flag = true;
            for(String str : conditions.keySet()){

                if(flag)
                {
                    sb.append(" `" + str + "` LIKE ?");
                    flag = false;

                }
                else {
                    sb.append(" AND `" + str + "` LIKE ?");

                }
            }

            query += sb.toString();
        }

        if(orderByFields != null)
        if(orderByFields.length > 0) {
            StringBuilder sb = new StringBuilder();

            sb.append(" ORDER BY ");

            for (String str : orderByFields) {
                sb.append(str+',' );
            }
        }
        query += ";";



        Connection con = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;


        try {
            //Creating prepared statement

            con = ds.getConnection();

            pstmt = con.prepareStatement(query);

            int counter = 1;

            //pstmt.setString(counter,tableName);
            //counter++;

            if(conditions != null)
            for(String str : conditions.keySet()){
                //pstmt.setString(counter,str);
                //counter++;

                pstmt.setString(counter,conditions.get(str));

                counter++;
            }

            pstmt.execute();
            //executing the pstmt;

            System.out.println(pstmt);

            rs = pstmt.executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();

            //creating jsonObjects ready to be send

            JSONObject json = new JSONObject();

            while(rs.next()){

                json = new JSONObject();

                for(int i = 1 ; i < rsmd.getColumnCount() ; i++){

                    json.put(rsmd.getColumnName(i),rs.getString(i));
                }

                list.add(json);

            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            try {
                if(rs != null) rs.close();
                if(pstmt != null) pstmt.close();
                //if(con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }




        return list;
    }





    public void addNewObject(Object obj,String tableName){

        Connection con = null;
        Statement stmt = null;
        try {
            con = ds.getConnection();
            stmt = con.createStatement();

            StringBuilder query = new StringBuilder();
            Field fields[] = obj.getClass().getFields();

            query.append("insert into " + tableName + " " +
                            "(");

            boolean first = true;

            for(Field f: fields){
                if(first){

                    query.append(f.getName());
                    query.append("");
                    first = false;
                }
                else
                {
                    query.append(", ");
                    query.append(f.getName());
                    query.append(" ");
                }

            }

            query.append(") values(");
            first = true;
            for(int i = 0;i < fields.length; i++){
                if(first){
                    first = false;
                }
                else
                {
                    query.append(",");
                }
                query.append("?");
            }
                query.append(");");

            //System.out.println(query.toString());

            PreparedStatement pstmt = con.prepareStatement(query.toString());

            int counter = 1;
            for(Field f: fields){

                try {
                    pstmt.setString(counter, "" + f.get(obj));
                } catch (IllegalAccessException e) {
                    //
                }

                counter++;
            }

            pstmt.execute();

            //System.out.println(pstmt);

            //pstmt.executeQuery();


            /*
            stmt.executeUpdate("insert into seriesfromtoday " +
                    "(ID,SeriesName,CurrentSeriesName,ReleaseDate,SeriesPoster)" +

                    " values ("+" DEFAULT " +
                    " , '"+ sic.seriesName +
                    "','" + sic.seriesCurrentName +
                    "','" + sic.seriesTime +
                    "','" + sic.posterUrl +
                    "')");
            */


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            try {
                //if(rs != null) rs.close();
                if(stmt != null) stmt.close();
                if(con != null) con.close();
            } catch (SQLException e) {
                Logger.getLogger(DatabaseAdapter.class.getName()).log(Level.SEVERE, null, e);
                //e.printStackTrace();
            }

        }



    }





    /*
    public ArrayList<Object> getAllHotelsFromLocation(String channelName){

        System.out.println("Channel name : " + channelName);

        if(channelName.contains("//")) return new ArrayList<>();

        int hours = LocalDateTime.now().getHour();


        ArrayList<Object> list = new ArrayList<>();
        String query;
        if(hours < 10)
            query = "select * from allchannelsandmovies where MovieTime LIKE '" + "0" + hours + ":%' " ;
        else
            query = "select * from allchannelsandmovies where " + " AND " + " MovieTime LIKE '" + hours + ":%' " ;

        query = "select * from allchannelsandmovies where TVChannelName like '"+ channelName +"'  limit 50" ;


        System.out.println("Client requested : "+query);

        try {
            con = ds.getConnection();

            stmt = con.createStatement();

            rs = stmt.executeQuery(query);

            MovieInfoContainer mic = null;

            // rs.next();

            //(String movieTitle, String movieGenre, String movieActors, String movieYear, String moviePlot, String movieTime, String movieChannelName, String movieTranslatedName, String posterURL) {


            while(rs.next()){

                mic = new MovieInfoContainer(rs.getString("MovieNameOriginal"),rs.getString("MovieGenre"),rs.getString("MovieActors"),rs.getString("MovieYear"),rs.getString("MoviePlot"),rs.getString("MovieTime"),rs.getString("TVChannelName"),rs.getString("MovieNameTranslated"),rs.getString("MoviePosterUrl"));

                list.add(mic);
            }

        }
        catch (Exception e){

        }
        finally {
            try {
                if(rs != null) rs.close();
                if(stmt != null) stmt.close();
                if(con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    public ArrayList<String> getAllTVChannels()
    {
        ArrayList<String> tvChannels = new ArrayList<>();

        String query = "select distinct TVChannelName from allchannelsandmovies";


        try {
            con = ds.getConnection();

            stmt = con.createStatement();

            rs = stmt.executeQuery(query);

            // rs.next();

            //(String movieTitle, String movieGenre, String movieActors, String movieYear, String moviePlot, String movieTime, String movieChannelName, String movieTranslatedName, String posterURL) {

            while(rs.next()){

                // mic = new MovieInfoContainer(rs.getString("MovieNameOriginal"),rs.getString("MovieGenre"),rs.getString("MovieActors"),rs.getString("MovieYear"),rs.getString("MoviePlot"),rs.getString("MovieTime"),rs.getString("TVChannelName"),rs.getString("MovieNameTranslated"),rs.getString("MoviePosterUrl"));

                tvChannels.add(rs.getString("TVChannelName"));
            }

        }
        catch (Exception e){

        }
        finally {
            try {
                if(rs != null) rs.close();
                if(stmt != null) stmt.close();
                if(con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }



        return tvChannels;
    }

    public ArrayList<SeriesInfoContainer> getAllSeries(){
        ArrayList<SeriesInfoContainer> series = new ArrayList<>();

        String query = "select * from seriesfromtoday";


        try {
            con = ds.getConnection();

            stmt = con.createStatement();

            rs = stmt.executeQuery(query);

            // rs.next();

            //(String movieTitle, String movieGenre, String movieActors, String movieYear, String moviePlot, String movieTime, String movieChannelName, String movieTranslatedName, String posterURL) {

            while(rs.next()){

                // mic = new MovieInfoContainer(rs.getString("MovieNameOriginal"),rs.getString("MovieGenre"),rs.getString("MovieActors"),rs.getString("MovieYear"),rs.getString("MoviePlot"),rs.getString("MovieTime"),rs.getString("TVChannelName"),rs.getString("MovieNameTranslated"),rs.getString("MoviePosterUrl"));

                series.add(new SeriesInfoContainer(rs.getString("SeriesName"),rs.getString("CurrentSeriesName"),rs.getString("ReleaseDate"),rs.getString("SeriesPoster")));
            }

        }
        catch (Exception e){

        }
        finally {
            try {
                if(rs != null) rs.close();
                if(stmt != null) stmt.close();
                if(con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        series.sort((seriesInfoContainer, t1) -> seriesInfoContainer.getDay() - t1.getDay());
        return series;
    }

    public void addSeries(SeriesInfoContainer sic){

        try {
            con = ds.getConnection();
            stmt = con.createStatement();

            stmt.executeUpdate("insert into seriesfromtoday " +
                    "(ID,SeriesName,CurrentSeriesName,ReleaseDate,SeriesPoster)" +
                    " values ("+" DEFAULT " +
                    " , '"+ sic.seriesName +
                    "','" + sic.seriesCurrentName +
                    "','" + sic.seriesTime +
                    "','" + sic.posterUrl +
                    "')");



        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            try {
                if(rs != null) rs.close();
                if(stmt != null) stmt.close();
                if(con != null) con.close();
            } catch (SQLException e) {
                Logger.getLogger(DatabaseAdapter.class.getName()).log(Level.SEVERE, null, e);
                //e.printStackTrace();
            }

        }



    }

    public void addTheMovie(MovieInfoContainer mic){

        try {
            con = ds.getConnection();

            stmt = con.createStatement();

            stmt.executeUpdate("insert into allchannelsandmovies " +
                    "(ID,TVChannelName,MovieNameOriginal,MovieTime,MovieNameTranslated,MovieGenre,MovieActors,MovieYear,MoviePlot,MoviePosterUrl,DayDate)" +
                    " values ("+" DEFAULT " +
                    " , '"+mic.movieChannelName +
                    "','" + mic.movieTitle +
                    "','" + mic.movieTime +
                    "','" + mic.movieTranslatedName +
                    "','" + mic.movieGenre +
                    "','" + mic.movieActors +
                    "','" + mic.movieYear +
                    "','" + mic.moviePlot +
                    "','" + mic.posterURL +
                    "','" + "12.08.2016" +

                    "')");

        } catch (SQLException ex) {
            if(ex.getMessage().equals("Socket closed")){
                Logger.getLogger(DatabaseAdapter.class.getName()).log(Level.FINE, null, ex);
                // addTheMovie(mic);
            }
            else
                Logger.getLogger(DatabaseAdapter.class.getName()).log(Level.SEVERE, null, ex);

        } finally {

            try {
                if(rs != null) rs.close();
                if(stmt != null) stmt.close();
                if(con != null) con.close();
            } catch (SQLException e) {
                Logger.getLogger(DatabaseAdapter.class.getName()).log(Level.SEVERE, null, e);
                //e.printStackTrace();
            }

        }


    }
*/


    private MysqlDataSource getDataSource()
    {
        MysqlDataSource mysqlDS = null;
        mysqlDS = new MysqlDataSource();
        mysqlDS.setURL("jdbc:mysql://localhost:3306/ohrdatabase");
        mysqlDS.setUser("root");
        mysqlDS.setPassword("mico");
        mysqlDS.setAutoReconnect(true);
        // mysqlDS.setMaxReconnects(100);
        return mysqlDS;
    }



}
