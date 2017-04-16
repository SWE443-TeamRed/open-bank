package main.java; /**
 * Created by daniel on 4/11/17.
 */

import com.google.gson.Gson;
import de.uniks.networkparser.IdMap;
import de.uniks.networkparser.json.JsonArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static spark.Spark.*;

public class SparkServer {
    private static final Logger logger = Logger.getLogger(SparkServer.class.getName());
    private static final Gson GSON = new Gson();

    static User user = null;
    static Account account = null;
    static Transaction transaction = null;
    static JSONObject responseJSON;

    public static void main(String[] args) {
        try {
            FileHandler fh;
            fh = new FileHandler("api.log");
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            logger.addHandler(fh);
        } catch (IOException ioe) {};

        before("/*", (q, a) -> {
            responseJSON = new JSONObject();
            logger.info("Received api call: " + q.pathInfo()
                    + "\n\tcontentType: " + q.contentType()
                    + "\n\theaders: " + q.headers().toString()
                    + "\n\tquery params: " + q.queryParams().toString());
        });

        get("/admin", (request, response) -> {return "<HTML>\n" +
                "<HEAD>\n" +
                "<TITLE>Admin Page Place Holder</TITLE>\n" +
                "</HEAD>\n" +
                "<BODY BGCOLOR=\"FFFFFF\">\n" +
                "<HR>\n" +
                "This is a place holder for the admin page\n" +
                "<H1>Header Example</H1>\n" +
                "<H2>Medium Header Example</H2>\n" +
                "<P>Paragraph Example\n" +
                "<P><B>Bold Paragraph Example</B>\n" +
                "<BR><B><I>This is a new sentence without a paragraph break, in bold italics.</I></B>\n" +
                "<HR>\n" +
                "</BODY>\n" +
                "</HTML>";});

        path("/api", () -> {

            path("/login", () -> {
                post("", (request, response) -> {

                    String username = request.queryParams("username");
                    String password = request.queryParams("password");

                    if(username != null & password != null) {
                        if (username.equals(account.getOwner().getUsername())) {
                            logger.info("Correct username");
                            if (password.equals(account.getOwner().getPassword())) {
                                logger.info("Correct password");
                                responseJSON.put("Authentication", true);
                            } else {
                                logger.info("Incorrect password");
                                responseJSON.put("Authentication", false);
                            }
                        }else {
                            logger.info("Incorrect username");
                            responseJSON.put("Authentication", false);
                        }
                    }else{
                        logger.info("Null username and/or password");
                        responseJSON.put("Authentication", false);
                    }
                    return responseJSON;
                });
            });

            path("/account", () -> {

                get("", (request, response) -> {

                    String jsonText = "";

                    IdMap idMap = AccountCreator.createIdMap("demo");
                    JsonArray jsonArray = idMap.toJsonArray(account);

                    return jsonArray;
                });
                post("", (request, response) -> {

                    user = new User()
                            .withName("Tina")
                            .withUsername("tinaUser")
                            .withUserID("tina1")
                            .withPassword("tinapass")
                            .withIsAdmin(false);
                    account = new Account()
                            .withAccountnum(1)
                            .withOwner(user)
                            .withBalance(100);
                    logger.info("User: " + user.toString());
                    logger.info("Account: " + account.toString());

                    responseJSON.put("Request","Successful");

                    return responseJSON;
                });

            });
            path("/transaction", () -> {

                get("/", (request, response) -> {
                    return "TransactionInfo";
                });

                post("", (request, response) -> {

                    String name = "No name";

                    if(request.queryParams().contains("owner"))
                        name = request.queryParams("owner");

//                    JSONParser parser = new JSONParser();
//                    Object obj = parser.parse(request.body());
//                    JSONArray array = (JSONArray)obj;

                    return name;
                });
            });
        });
    }
}