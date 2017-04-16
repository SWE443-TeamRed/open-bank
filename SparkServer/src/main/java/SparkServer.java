package main.java; /**
 * Created by daniel on 4/11/17.
 */

import com.google.gson.Gson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static spark.Spark.*;

public class SparkServer {
    private static final Logger logger = Logger.getLogger(SparkServer.class.getName());
    private static final Gson GSON = new Gson();

    static Transaction transaction = null;
    static int i = 1;
    static int j = 1;

    static Map<Integer, Account> accountMap;
    static Map<String, User> userMap;

    public static void main(String[] args) {

        Runtime.getRuntime().addShutdownHook(new ShutdownThread());

        apiLogSetup();
        accountMapSetup();
        userMapSetup();

        before("/*", (q, a) -> logger.info("Received api call from ip: " + q.ip()
                + "\n\t" + q.pathInfo()
                + "\n\tMessage Content"
                + "\n\tcontentType: " + q.contentType()
                + "\n\theaders: " + q.headers().toString()
                + "\n\tquery params: " + q.queryParams().toString()));

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

                    JSONObject responseJSON = new JSONObject();

                    String username = request.queryParams("username");
                    String password = request.queryParams("password");

                    if(username != null & password != null) {
                        if(userMap.containsKey(username)) {
                            logger.info("Correct username");
                            if (password.equals(userMap.get(username).getPassword())) {
                                logger.info("Correct password");
                                responseJSON.put("authentication", true);
                                responseJSON.put("accountNumber", userMap.get(username).getAccount().getAccountnum());

                                return responseJSON;
                            } else {
                                logger.info("Incorrect password");
                            }
                        } else {
                            logger.info("Incorrect username");
                        }
                    }

                    responseJSON.put("authentication", false);
                    return responseJSON;
                });
            });

            path("/user", () -> {
                get("", (request, response) -> {return "";});

                post("", (request, response) -> {

                    JSONObject responseJSON = new JSONObject();

                    String name = "";
                    String username = "";
                    String password = "";
                    String phoneNumber = "";
                    boolean isAdmin = false;


                    if(request.queryParams().contains("name"))
                        name = request.queryParams("name");
                    if(request.queryParams().contains("username"))
                        username = request.queryParams("username");
                    if(request.queryParams().contains("password"))
                        password = request.queryParams("password");
                    if(request.queryParams().contains("isAdmin"))
                        isAdmin = Boolean.parseBoolean(request.queryParams("isAdmin").toString());
                    if(request.queryParams().contains("phoneNumber"))
                        phoneNumber = request.queryParams("phoneNumber").toString();

                    User user = null;

                    user = new User()
                            .withName(name)
                            .withUsername(username)
                            .withPhone(phoneNumber)
                            .withUserID(""+j)
                            .withPassword(password)
                            .withIsAdmin(isAdmin);
                    userMap.put(username, user);
                    j++;

                    responseJSON.put("request","successful");

                    return responseJSON;
                });
            });

            path("/account", () -> {

                get("", (request, response) -> {

                    JSONArray jsonArray = new JSONArray();
                    JSONObject accountJson = new JSONObject();

                    if(request.queryParams().contains("username")) {
                        String useranme = request.queryParams("username");

                        if(userMap.containsKey(useranme)) {
                            accountJson.put("accountNumber", userMap.get(useranme).getAccount().getAccountnum());
                            accountJson.put("balance", userMap.get(useranme).getAccount().getBalance());
                        }
                    }

                    jsonArray.add(accountJson);

                    return jsonArray;
                });
                post("", (request, response) -> {

                    JSONObject responseJSON = new JSONObject();

                    String username = "";

                    if(request.queryParams().contains("username")) {

                        username = request.queryParams("username");
                        Account account = null;

                        if (userMap.containsKey(username)) {
                            account = new Account()
                                    .withAccountnum(i)
                                    .withOwner(userMap.get(username))
                                    .withBalance(100);
                        } else {
                            responseJSON.put("request", "failure");
                            return responseJSON;
                        }

                        accountMap.put(i, account);
                        i++;

                        responseJSON.put("request", "successful");
                    }else{
                        responseJSON.put("request", "failure");
                    }

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

    private static void accountMapSetup() {
        accountMap = new HashMap<>();
    }

    private static void userMapSetup() {
        userMap = new HashMap<>();
    }

    private static void apiLogSetup() {
        try {
            FileHandler fh;
            fh = new FileHandler("api.log", true);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            logger.addHandler(fh);

            logger.info("*************************************New session started*************************************");
        } catch (IOException ioe) {};
    }

    static class ShutdownThread extends Thread {
        ShutdownThread() {}

        public void run() {
            logger.info("*************************************Shutting Down Session*************************************");
        }
    }
}