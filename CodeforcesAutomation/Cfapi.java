package CodeforcesAutomation;
import java.io.IOException;
import java.util.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;



public class Cfapi {
    public static void main(String[] args) {
        // Your Codeforces API key
        String apiKey = "YOUR_API_KEY_HERE";

        // Your Codeforces handle (username)
        String handle = "YOUR_HANDLE_HERE";

        // Your preferred rating range
        int minRating = 1500;
        int maxRating = 2000;

        // Create an HTTP client
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // Construct the API URL to get upcoming contests
            String apiUrl = "https://codeforces.com/api/contest.list?gym=false";

            // Make the HTTP GET request
            HttpGet httpGet = new HttpGet(apiUrl);
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();

            // Check if the response entity is not null
            if (entity != null) {
                String responseBody = EntityUtils.toString(entity);
                JSONObject jsonObject = new JSONObject(responseBody);

                // Check if the API request was successful
                if (jsonObject.getString("status").equals("OK")) {
                    JSONArray contests = jsonObject.getJSONArray("result");

                    // Iterate through contests
                    for (int i = 0; i < contests.length(); i++) {
                        JSONObject contest = contests.getJSONObject(i);

                        // Check if the contest is upcoming and in the preferred rating range
                        if (contest.getBoolean("phase").equals("BEFORE") &&
                                minRating <= contest.getInt("minRating") &&
                                maxRating >= contest.getInt("maxRating")) {
                            System.out.println("Contest Name: " + contest.getString("name"));
                            System.out.println("Contest ID: " + contest.getInt("id"));
                            // You can add registration logic here
                            // Remember to handle registration confirmation
                        }
                    }
                } else {
                    System.out.println("Error: " + jsonObject.getString("comment"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
