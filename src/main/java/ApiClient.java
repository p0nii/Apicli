// Dessa paket använder vi för att läsa information från och skriva information till HTTP-anslutningar
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;

// Dessa paket hjälper oss konvertera JSON till Java-objekt och tvärt om
// De används när vi skickar information till servern och när vi hämtar information från servern
import com.github.cliftonlabs.json_simple.JsonObject;
import com.fasterxml.jackson.databind.*;

// Detta används för att skicka datan med UTF-8 - en teckenuppsättning som låter oss använda ÅÄÖ och massa andra tecken
import java.nio.charset.StandardCharsets;

import java.util.ArrayList;

// Paket vi använder för att göra HTTP-anslutningar
import java.net.URL;
import java.net.HttpURLConnection;


public class ApiClient {

    // Adressen till vår server, exempelvis https://127.0.0.1:8080/api/v1 (Important: Without the / at the end)
    private String apiAddress;
    HttpURLConnection connection;

    // Vår konstruktor
    public ApiClient(String apiAddress) {
        this.apiAddress = apiAddress;
    }

    public ArrayList<String> getStringArray(String target) {
        JsonObject countryObj = new JsonObject();

        ArrayList<String> myArrayOfStrings = new ArrayList<>();

        return myArrayOfStrings;
    }

    //Metod för att hämta alla blogginlägg
    public Blog[] getBlogPosts() {
        Blog[] blogPosts = {};

        String target = "/list";

        //System.out.println("Getting movies from " + apiAddress + target);

        // Se kommentarer i metoden addMovie()
        BufferedReader reader;
        String line;
        StringBuilder responseContent = new StringBuilder();

        // Se kommentarer i metoden addMovie()
        try {
            URL url = new URL(apiAddress + target);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("accept", "application/json");

            int status = connection.getResponseCode();

            if (status >= 300) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            }

            //System.out.println(responseContent.toString());
            String jsonStr = responseContent.toString();

            // Se kommentarer i metoden addMovie()
            ObjectMapper mapper = new ObjectMapper();
            blogPosts = mapper.readValue(jsonStr, Blog[].class);

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            connection.disconnect();
        }

        return blogPosts;
    }

    //Metod för att rensa alla blogginlägg
    public boolean clearBlogPosts() {
        String target = "/clear"; // http://127.0.0.1:8080/api/v1/blog/clear

        //System.out.println("Clearing movies from " + apiAddress + target);

        boolean success = false;

        try {
            URL url = new URL(apiAddress + target);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");

            int status = connection.getResponseCode();

            System.out.println("Http status " + status);

            if (status < 300) {
                success = true;
            }

            //System.out.println(responseContent.toString());
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            connection.disconnect();
        }

        return success;
    }

    //Metod för att skapa ett nytt blogginlägg
    public boolean addBlogPost(Blog newPost) {
        String target = "/create";

        //System.out.println("Adding movie at " + apiAddress + target);

        boolean success = false;

        try {
            // Skapa ett URL-objekt och säg vilken adress vi vill skicka information till
            URL url = new URL(apiAddress + target);

            // Öppna nätverksanslutningen
            connection = (HttpURLConnection) url.openConnection();

            // Ange metod
            connection.setRequestMethod("POST");

            // Lägg till header (säg att vi vill skicka JSON-data)
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setDoOutput(true);

            // Konvertera vårt Java-objekt (Movie) till JSON med hjälp av .toJSON-metoden i klassen Movie,
            // och skriv den JSON-datan till vår nätverksanslutning med hjälp av en OutputStream
            try (OutputStream os = connection.getOutputStream()) {
                // Skapa en byte-array som innehåller JSON-datan
                byte[] input = newPost.toJson().getBytes(StandardCharsets.UTF_8);

                // Skriv byte-arrayen till nätverksanslutningen (vi måste också ange hur lång strängen är)
                os.write(input, 0, input.length);
            }

            // Vad fick vi för svar? Vad var HTTP-statuskoden vi fick tillbaka?
            int status = connection.getResponseCode();

            // Generellt om HTTP-koden är över 300 har något gått fel
            // Om den är 299 eller lägre har det gått bra
            // (Exempelvis är "200 OK" bra och "404 Not Found" inte bra)
            if (status < 300) {
                success = true;
            }

            //System.out.println(responseContent.toString());
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            connection.disconnect();
        }

        return success;
    }

    //Metod för att ta bort ett specifikt inlägg
    public boolean deleteBlogPost(int id) {
        String target = "/delete/" + id;

        boolean success = false;

        try {
            URL url = new URL(apiAddress + target);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");

            int status = connection.getResponseCode();

            System.out.println(status);
            if (status < 300) {
                success = true;
            }

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            connection.disconnect();
        }

        return success;

        }

    //Metod för lista specifikt inlägg
    public Blog GetBlogPost(int id) {
        Blog blog = new Blog ();
        String target = "/get/" + id;

        BufferedReader reader;
        String line;
        StringBuilder responseContent = new StringBuilder();

        try {
            URL url = new URL(apiAddress + target);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("accept", "application/json");

            int status = connection.getResponseCode();

            if (status >= 300) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            }

            String jsonStr = responseContent.toString();


            ObjectMapper mapper = new ObjectMapper();
            blog = mapper.readValue(jsonStr, Blog.class);

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            connection.disconnect();
        }

        return blog;



    }

    //Metod för att uppdatera specifikt inlägg
    public boolean updateBlogPost (int id, Blog update) {
        String target = "/update/" + id;

        boolean success = false;

        try {

            URL url = new URL(apiAddress + target);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setDoOutput(true);


            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = update.toJson().getBytes(StandardCharsets.UTF_8);

                os.write(input, 0, input.length);
            }

            int status = connection.getResponseCode();

            if (status < 300) {
                success = true;
            }

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            connection.disconnect();
        }

        return success;



    }


    }

