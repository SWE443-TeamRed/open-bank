/**
 * Created by daniel on 4/11/17.
 */

import de.uniks.networkparser.list.BooleanList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static spark.Spark.*;

public class SparkServer {
    private static final Logger logger = Logger.getLogger(SparkServer.class.getName());

    static boolean BANKMODE = false;

    static Transaction transaction = null;
    static int i = 1;
    static int j = 1;

    static Map<Integer, Account> accountMap;
    static Map<String, User> userMap;

    static Bank bank;

    public static void main(String[] args) {

        Runtime.getRuntime().addShutdownHook(new ShutdownThread());

        bank = new Bank().withBankName("OpenBank");
        logger.info("Bank: " + bank.toString());

        apiLogSetup();
        accountMapSetup();
        userMapSetup();

        before("/*", (q, a) -> logger.info("Received api call from ip: " + q.ip()
                + "\n\t" + q.pathInfo()
                + "\n\tMessage Content"
                + "\n\tcontentType: " + q.contentType()
                + "\n\theaders: " + q.headers().toString()
                + "\n\tquery params: " + q.queryParams().toString()));

        path("/admin", () -> {
            get("", (request, response) -> {return "<HTML>\n" +
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
                    "</HTML>";
            });

            path("/passwordReset", () -> {
                post("", (request, response) -> {
                    JSONObject responseJSON = new JSONObject();

                    String username = request.queryParams("username");
                    String newPassword = request.queryParams("newPassword");

                    userMap.get(username).withPassword(newPassword);

                    if(userMap.get(username).getPassword().equals(newPassword))
                        responseJSON.put("request","successful");
                    else
                        responseJSON.put("request","failed");

                    return responseJSON;
                });
            });
        });


        path("/api", () -> {

            path("/login", () -> {
                post("", (Request request, Response response) -> {

                    JSONObject responseJSON = new JSONObject();

                    String username = request.queryParams("username");
                    String password = request.queryParams("password");
                    String id = request.queryParams("id");

                    if(BANKMODE) {
                        if (username != null & password != null) {
                            BooleanList login = bank.getCustomerUser().login(id, password);
                            if (bank.findUserByID(id).isLoggedIn())
                                responseJSON.put("authentication", true);
                            else
                                responseJSON.put("authentication", false);
                        } else {
                            responseJSON.put("authentication", false);
                            logger.info("Incorrect password");
                        }
                    }

                    if(!BANKMODE){
                        if (id != null & password != null) {
                            if (userMap.containsKey(username)) {
                                logger.info("Correct username");
                                if (userMap.get(username).login(id, password)) {
                                    logger.info("Correct password");
                                    responseJSON.put("authentication", true);
                                } else {
                                    responseJSON.put("authentication", false);
                                    logger.info("Incorrect password");
                                    logger.info("Correct Password: " + userMap.get(username).getPassword());
                                }
                            } else {
                                responseJSON.put("authentication", false);
                                logger.info("Incorrect username");
                            }
                        } else {
                            responseJSON.put("authentication", false);
                            logger.info("Username or Password not found");
                        }
                    }
                    return responseJSON;
                });
            });

            path("/logout", () -> {
                post("", (Request request, Response response) -> {
                    JSONObject responseJSON = new JSONObject();

                    String username = request.queryParams("username");
                    String id = request.queryParams("id");

                    if(BANKMODE) {
                        if (id != null) {
                            if (bank.findUserByID(id).logout())
                                responseJSON.put("request","successful");
                            else
                                responseJSON.put("request","failed");
                        } else {
                            responseJSON.put("request","failed");
                        }
                    }

                    if(!BANKMODE){
                        if (id != null & username != null) {
                            if (userMap.containsKey(username)) {
                                logger.info("Correct username");
                                if (userMap.get(username).logout()) {
                                    logger.info("Correct password");
                                    responseJSON.put("request","successful");
                                    return responseJSON;
                                } else {
                                    responseJSON.put("request","failed");
                                }
                            } else {
                                responseJSON.put("request","failed");

                            }
                        } else {
                            responseJSON.put("request","failed");
                        }
                    }
                    return responseJSON;
                });
            });

            path("/user", () -> {

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

                    User user = bank.createCustomerUser()
                            .withAccount()
                            .withName(name)
                            .withUsername(username)
                            .withPhone(phoneNumber)
                            .withUserID("" + j)
                            .withPassword(password)
                            .withIsAdmin(isAdmin);
                    userMap.put(username, user);
                    j++;

                    if(user != null) {
                        logger.info("Added User: " + user.toString());
                        responseJSON.put("request", "successful");
                        responseJSON.put("userID", user.getUserID());
                    }
                    else
                        responseJSON.put("request","failed");

                    return responseJSON;
                });
            });

            path("/account", () -> {

                get("", (request, response) -> {

                    JSONArray jsonArray = new JSONArray();
                    JSONObject accountJson = new JSONObject();

                    if(BANKMODE)
                    {
                        if (request.queryParams().contains("userID") && request.queryParams().contains("accountID")) {
                            String userID = request.queryParams("userID");
                            int accountID = Integer.parseInt(request.queryParams("accountID"));
                            Account account = bank.findAccountByID(accountID);

                            logger.info("User id: " + userID);
                            logger.info("User Accounts: " + bank.findUserByID(userID).getAccount());
                            logger.info("Account id: " + accountID);
                            logger.info("Account located: " + account);

                            if(account != null) {
                                accountJson.put("accountNumber", account.getAccountnum());
                                accountJson.put("balance", account.getBalance());
                            }else {
                                accountJson.put("request", "failed");
                            }
                        }else {
                            accountJson.put("request", "failed");
                        }
                    }
                    if(!BANKMODE) {
                        if (request.queryParams().contains("username")) {
                            String username = request.queryParams("username");

                            if (userMap.containsKey(username)) {
                                accountJson.put("request", "successful");
                                accountJson.put("accountNumber", userMap.get(username).getAccount().getAccountnum());
                                accountJson.put("balance", userMap.get(username).getAccount().getBalance());
                            }else {
                                accountJson.put("request", "failed");
                            }
                        }else {
                            accountJson.put("request", "failed");
                        }
                    }

                    jsonArray.add(accountJson);
                    return jsonArray;
                });
                post("", (request, response) -> {

                    JSONObject responseJSON = new JSONObject();

                    String username = "";
                    String id = "";

                    if(BANKMODE)
                    {
                        if (request.queryParams().contains("id")) {
                            id = request.queryParams("id");

                            if(bank.findUserByID(id) != null) {
                                Account account = bank.findUserByID(id).createAccount()
                                        .withAccountnum(i)
                                        .withOwner(userMap.get(username))
                                        .withBalance(100);
                                if (account != null) {
                                    responseJSON.put("request", "successful");
                                    responseJSON.put("account", account.toString());
                                    i++;
                                } else {
                                    responseJSON.put("request", "failed");
                                }
                            }else {
                                responseJSON.put("request", "failed");
                            }
                        }else {
                            responseJSON.put("request", "failed");
                        }
                    }

                    if(!BANKMODE) {
                        if (request.queryParams().contains("username")) {

                            username = request.queryParams("username");
                            Account account = null;

                            if (userMap.containsKey(username)) {
                                account = new Account()
                                        .withAccountnum(i)
                                        .withOwner(userMap.get(username))
                                        .withBalance(100);
                                accountMap.put(i, account);
                                if (accountMap.get(i) != null) {
                                    responseJSON.put("request", "successful");
                                    responseJSON.put("account", accountMap.get(i).toString());
                                    i++;
                                } else {
                                    responseJSON.put("request", "failed");
                                }

                            } else {
                                responseJSON.put("request", "failed");
                                return responseJSON;
                            }
                        } else {
                            responseJSON.put("request", "failed");
                        }
                    }
                    return responseJSON;
                });
            });
            path("/transaction", () -> {

                get("", (request, response) -> {

                    String id = "";
                    JSONObject responseJSON = new JSONObject();

                    if(request.queryParams().contains("id"))
                    {
                        id = request.queryParams("id");
                    } else {
                        responseJSON.put("request", "failed");
                        return responseJSON;
                    }

                    return responseJSON.put("request", "success");
                });

                post("", (request, response) -> {

                    JSONObject responseJSON = new JSONObject();

                    double amount = 0;
                    int fromAccountId = 0;
                    int toAccountId = 0;
                    String transferType = "";

                    if(request.queryParams().contains("amount")
                            && request.queryParams().contains("fromAccountId")
                            && request.queryParams().contains("toAccountId")
                            && request.queryParams().contains("transferType")) {
                        amount = Double.parseDouble(request.queryParams("amount"));
                        fromAccountId = Integer.parseInt(request.queryParams("fromAccountId"));
                        toAccountId = Integer.parseInt(request.queryParams("toAccountId"));
                        transferType = request.queryParams("transferType");

                        TransactionTypeEnum transactionTypeEnum = TransactionTypeEnum.valueOf(transferType);

                        logger.info("fromAccount: " + accountMap.get(fromAccountId).toString());
                        logger.info("toAccount: " + accountMap.get(toAccountId).toString());

//                        Transaction transaction = new Transaction()
//                                .withAmount(amount)
//                                .withCreationdate(new Date())
//                                .withFromAccount(accountMap.get(fromAccountId))
//                                .withToAccount(accountMap.get(toAccountId))
//                                .withTransType(transactionTypeEnum)
//                                .withTime(new Date());

                        Transaction transaction = accountMap.get(fromAccountId).createCredit()
                                .withAmount(amount)
                                .withCreationdate(new Date())
                                .withFromAccount(accountMap.get(fromAccountId))
                                .withToAccount(accountMap.get(toAccountId))
                                .withTransType(transactionTypeEnum)
                                .withTime(new Date());;

                        accountMap.get(fromAccountId).transferToAccount(amount, accountMap.get(toAccountId), "");

                        responseJSON.put("request", "successful");
                        responseJSON.put("transaction", transaction.toString());
                        responseJSON.put("transactionFrom", transaction.getFromAccount().toString());
                        responseJSON.put("transactionTo", transaction.getToAccount().toString());

                        return responseJSON;
                    }

                    responseJSON.put("request", "failed");
                    return responseJSON;
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