import DatabaseClasses.DatabaseAdapter;
import DatabaseClasses.DatabaseEntities.BasicUser;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Darko on 23.11.2016.
 */
public class TestingClass {

    public static void main(String[] args) {

        DatabaseAdapter adapter = new DatabaseAdapter();


        //"TVChannelName":"HBO3"
        HashMap<String,String> conditions = new HashMap();
        //conditions.put("TVChannelName","HBO3");
        //conditions.put("MoviePlot","N/A");
        String []sortBy = new String[1];
        //sortBy[0] = "TVChannelName";
        ArrayList<JSONObject> list = adapter.getAllElementsFromTableWithConditionsAndSorted("users",sortBy,conditions);

        list.stream().forEach(elem-> System.out.println(elem));

        BasicUser user = new BasicUser();
        user.password = "pass";
        user.role = "admin";
        user.username = "k00ldude12345";

        //adapter.addNewObject(user,"users");

    }

}
