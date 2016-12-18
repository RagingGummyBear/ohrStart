package NetworkClasses;

import DatabaseClasses.DatabaseAdapter;
import DatabaseClasses.DatabaseEntities.BasicUser;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Darko on 19.11.2016.
 *
 * The server should look like api;
 *
 */
public class WebSocketServerHandler extends WebSocketServer {

    HashMap<String,WebSocket> currentConnections = new HashMap<>(); //probably not needed
    private final DatabaseAdapter databaseAdapter = new DatabaseAdapter();

    public WebSocketServerHandler(InetSocketAddress address) {
        super(address);
    }

    public void onOpen(WebSocket conn, ClientHandshake handshake) {

        System.out.println("User got connected");

    }

    public void onClose(WebSocket conn, int code, String reason, boolean remote) {

    }

    public void onMessage(WebSocket conn, String message) {

        JSONObject msg = new JSONObject(message);

        System.out.println("Received message from user: "+message);

        switch((String)msg.get("command")){

            case "allHotelsFromLocationRequest" : {

                break;
            }
            case "allHotelsFromLocationSortedByRequest" : {

                break;
            }
            case "loginUser":{
                tryToLogInUser(msg,conn);
                break;
            }
            case "registerUser":{
                tryToRegisterUser(msg,conn);
                break;
            }

            ////////////////////////////
            ///////TESTING CASES////////
            case "requestDummyHotels":{

                sendDummyHotelList(conn);

                break;
            }
            case "requestAddingDummyHotel":{

                break;
            }

            ////////////////////////////


            default: {

                sendInvalidMessageResponse(conn,msg.get("command"));
                break;
            }
        }

    }

    private void tryToRegisterUser(JSONObject msg, WebSocket conn) {

        JSONArray content = msg.getJSONArray("content");

        BasicUser user = new BasicUser();

        user.username = (String) content.get(0);
        user.password = (String) content.get(1);
        user.role = (String) content.get(2);

        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("username",user.username);
        if(databaseAdapter.getAllElementsFromTableWithConditionsAndSorted("users",null,hashMap).size() > 0){

            System.out.println("There is user with such username");
            conn.send(constructJSONMessage("usernameExists",null).toString());

        }
        else
        {
            databaseAdapter.addNewObject(user,"users");
            conn.send(constructJSONMessage("AccountCreated",null).toString());
            System.out.println("User was registered");
        }


    }

    private void tryToLogInUser(JSONObject msg, WebSocket conn) {


        JSONArray content = msg.getJSONArray("content");

        BasicUser user = new BasicUser();

        user.username = (String) content.get(0);
        user.password = (String) content.get(1);
        user.role = (String) content.get(2);

        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("username",user.username);
        hashMap.put("password",user.password);
        ArrayList<JSONObject> list = databaseAdapter.getAllElementsFromTableWithConditionsAndSorted("users",null,hashMap);

        if(list.size() > 0){

            System.out.println("User login successful");

            conn.send(constructJSONMessage("loginSuccessful",null).toString());

        }
        else
        {
            conn.send(constructJSONMessage("invalidEntry",null).toString());
            System.out.println("Invalid input");
        }


    }


    private JSONObject constructJSONMessage(String command, ArrayList<?> content){

        JSONObject msg = new JSONObject();
        msg.put("command",command);
       // JSONArray array = new JSONArray();
        //array.put(content);
        msg.put("content",content);
        return msg;
    }




    private void sendInvalidMessageResponse(WebSocket conn, Object command) {
        //"invalidMessageResponse"
        //"Invalid command received " + command
        ArrayList<String> content = new ArrayList<>();
        content.add("Invalid command received : " + command);
        conn.send(constructJSONMessage("invalidMessageResponse",content).toString());
    }


    private void sendDummyHotelList(WebSocket conn){

        ArrayList<JSONObject> hotels = new ArrayList<>();

        JSONObject hotel = new JSONObject();

        hotel.put("hotelName","Cool Hotlz");
        hotel.put("hotelDescription","Doing the coolin on the maXz");
        hotel.put("hotelAddress","It's easier to find than nemo");
        hotel.put("hotelNumberOfStars",3.5);

        hotels.add(hotel);

        hotel = new JSONObject();

        hotel.put("hotelName","Quite serious hotel");
        hotel.put("hotelDescription","We are only dealing with the serious here");
        hotel.put("hotelAddress","Next to the serious cafe");
        hotel.put("hotelNumberOfStars",99.7);

        hotels.add(hotel);

        hotel = new JSONObject();

        hotel.put("hotelName","I can't even");
        hotel.put("hotelDescription","What is even? How to even? Even if I knew how to even I even wouldn't even");
        hotel.put("hotelAddress","Evening Street");
        hotel.put("hotelNumberOfStars",-13);

        hotels.add(hotel);

        //hotels.add(constructJSONMessage("sendingHotel",hotels));

        conn.send(constructJSONMessage("dummyHotelsRequest",hotels).toString());

    }

    private void sendAllHotelsFromLocation(WebSocket conn,String location){

        // TODO: 19.11.2016 change ArrayList<Object> and implement a toString(), which will return json;
        ArrayList<Object> hotels = new ArrayList<>();
        
        hotels.stream().forEach(elem->{conn.send(elem.toString());});
        
    }


    private void sendAllHotelsFromLocationSortedBy(WebSocket conn, String location, String args){

        // TODO: 19.11.2016 implements this :D
        
    }
    
    public void onError(WebSocket conn, Exception ex) {


    }



}
