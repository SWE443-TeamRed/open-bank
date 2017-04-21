/**
 * Created by daniel on 4/11/17.
 */

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

    static boolean BANKMODE = true;

    static Transaction transaction = null;
    static int i = 1;
    static int j = 1;

    static Map<Integer, Account> accountMap;

    static Bank bank;

    public static void main(String[] args) {

        Runtime.getRuntime().addShutdownHook(new ShutdownThread());

        bank = new Bank().withBankName("OpenBank");
        logger.info("Bank: " + bank.toString());

        apiLogSetup();
        accountMapSetup();

        before("/*", (q, a) -> logger.info("Received api call from ip: " + q.ip()
                + "\n\t" + q.pathInfo()
                + "\n\tMessage Content"
                + "\n\tcontentType: " + q.contentType()
                + "\n\theaders: " + q.headers().toString()
                + "\n\tquery params: " + q.queryParams().toString()));

        path("/admin", () -> {
            get("", (Request request, Response response) -> {return "<HTML>\n" +
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
                post("", (Request request, Response response) -> {
                    JSONObject responseJSON = new JSONObject();

                    if(request.queryParams().contains("newPassword")
                            && request.queryParams().contains("id")) {

                        String newPassword = request.queryParams("newPassword");
                        String id = request.queryParams("id");

                        if(bank.findUserByID(id) != null) {

                            bank.findUserByID(id).withPassword(newPassword);

                            if (bank.findUserByID(id).getPassword().equals(newPassword))
                                responseJSON.put("request", "successful");
                            else {
                                responseJSON.put("request", "failed");
                                responseJSON.put("reason", "password not successfully changed");
                            }
                        }else {
                            responseJSON.put("request", "failed");
                            responseJSON.put("reason", "user could not be found");
                        }
                    }else {
                        responseJSON.put("request", "failed");
                        responseJSON.put("reason","missing required parameters in body");
                    }
                    return responseJSON;
                });
            });
        });


        path("/api", () -> {

            path("/login", () -> {

                get("", (Request request, Response response) -> {

                    JSONObject responseJSON = new JSONObject();

                    if(request.queryParams().contains("id")) {
                        String id = request.queryParams("id");

                        responseJSON.put("isUserLoggedIn", bank.findUserByID(id).isLoggedIn());
                    }

                    return responseJSON;
                });

                post("", (Request request, Response response) -> {

                    JSONObject responseJSON = new JSONObject();

                    if(request.queryParams().contains("username") && request.queryParams().contains("password")) {

                        String username = request.queryParams("username");
                        String password = request.queryParams("password");

                        String id = bank.Login(username, password);

                        if(id != null) {
                            bank.findUserByID(id).login(id, password);
                            if (bank.findUserByID(id).isLoggedIn()) {
                                responseJSON.put("authentication", true);
                                responseJSON.put("userID", id);
                            } else {
                                responseJSON.put("authentication", false);
                                responseJSON.put("reason", "failed to login the user");
                            }
                        } else {
                            responseJSON.put("authentication", false);
                            responseJSON.put("reason", "user could not be found");
                        }

                    } else {
                        responseJSON.put("authentication", false);
                        responseJSON.put("reason","missing required parameters in body");
                    }

                    return responseJSON;
                });
            });

            path("/logout", () -> {
                post("", (Request request, Response response) -> {
                    JSONObject responseJSON = new JSONObject();

                    if(request.queryParams().contains("id")) {
                        String id = request.queryParams("id");

                        if (id != null) {
                            if(bank.findUserByID(id) != null) {
                                if (bank.findUserByID(id).logout())
                                    responseJSON.put("request", "successful");
                                else {
                                    responseJSON.put("request", "failed");
                                    responseJSON.put("reason", "failed to logout the user");
                                }
                            }else {
                                responseJSON.put("request", "failed");
                                responseJSON.put("reason", "user could not be found");
                            }
                        } else {
                            responseJSON.put("request", "failed");
                            responseJSON.put("reason", "null user id");
                        }
                    }else {
                        responseJSON.put("authentication", false);
                        responseJSON.put("reason","missing required parameters in body");
                    }

                    return responseJSON;
                });
            });

            path("/user", () -> {

                get("", (Request request, Response response) -> {
                    JSONObject responseJSON = new JSONObject();

                    responseJSON.put("userList", bank.getCustomerUser());

                    return responseJSON;
                });


                post("", (Request request, Response response) -> {

                    JSONObject responseJSON = new JSONObject();

                    String name = "";
                    String username = "";
                    String password = "";
                    String phoneNumber = "";
                    boolean isAdmin = false;


                    if(request.queryParams().contains("name")
                            && request.queryParams().contains("username")
                            && request.queryParams().contains("password")
                            && request.queryParams().contains("isAdmin")
                            && request.queryParams().contains("phoneNumber")) {
                        name = request.queryParams("name");
                        username = request.queryParams("username");
                        password = request.queryParams("password");
                        isAdmin = Boolean.parseBoolean(request.queryParams("isAdmin"));
                        phoneNumber = request.queryParams("phoneNumber");

                        User user = new User()
                                .withAccount()
                                .withName(name)
                                .withUsername(username)
                                .withPhone(phoneNumber)
                                .withUserID("" + j)
                                .withPassword(password)
                                .withIsAdmin(isAdmin);
                        bank.withCustomerUser(user);
                        j++;

                        if(bank.findUserByID(user.getUserID()) != null) {
                            logger.info("Added User: " + user.toString());
                            responseJSON.put("request", "successful");
                            responseJSON.put("userID", bank.findUserByID(user.getUserID()).getUserID());
                        }
                        else {
                            responseJSON.put("request", "failed");
                            responseJSON.put("reason","bank failed to create user");
                        }
                    }else {
                        responseJSON.put("request","failed");
                        responseJSON.put("reason","missing required parameters in body");
                    }

                    return responseJSON;
                });
            });

            path("/account", () -> {

                get("", (Request request, Response response) -> {

                    JSONArray jsonArray = new JSONArray();
                    JSONObject accountJson = new JSONObject();

                    if (request.queryParams().contains("id")) {

                        String id = request.queryParams("id");

                        if(request.queryParams().contains("accountID")) {
                            int accountID = Integer.parseInt(request.queryParams("accountID"));
                            Account account = bank.findAccountByID(accountID);

                            if (account != null) {
                                accountJson.put("accountNumber", account.getAccountnum());
                                accountJson.put("accountType", account.getType());
                                accountJson.put("balance", account.getBalance());
                                jsonArray.add(accountJson);
                            } else {
                                accountJson.put("request", "failed");
                                accountJson.put("reason", "bank failed to locate account");
                                jsonArray.add(accountJson);
                            }
                        }else {
                            AccountSet accounts = bank.findUserByID(id).getAccount();

                            for(Account account : accounts){
                                if (account != null) {
                                    accountJson.put("accountNumber", account.getAccountnum());
                                    accountJson.put("accountType", account.getType());
                                    accountJson.put("balance", account.getBalance());
                                    jsonArray.add(accountJson);
                                }
                            }
                        }
                    }else {
                        accountJson.put("request", "failed");
                        accountJson.put("reason","missing required parameters in body");
                        jsonArray.add(accountJson);
                    }

                    return jsonArray;
                });
                post("", (Request request, Response response) -> {

                    JSONObject responseJSON = new JSONObject();

                    if (request.queryParams().contains("id")
                            && request.queryParams().contains("accountType")
                            && request.queryParams().contains("initialBalance")) {

                        String id = request.queryParams("id");
                        String accountType = request.queryParams("accountType");
                        Double initialBalance = Double.parseDouble(request.queryParams("initialBalance"));

                        AccountTypeEnum accountTypeEnum = AccountTypeEnum.valueOf(accountType);

                        if(bank.findUserByID(id) != null) {
                            Account account = bank.findUserByID(id).createAccount()
                                    .withAccountnum(i)
                                    .withOwner(bank.findUserByID(id))
                                    .withType(accountTypeEnum)
                                    .withBalance(initialBalance);
                            if (account != null) {
                                responseJSON.put("request", "successful");
                                responseJSON.put("accountNum", account.getAccountnum());
                                responseJSON.put("test", account.toString());
                                i++;
                            } else {
                                responseJSON.put("request", "failed");
                                responseJSON.put("reason","bank failed to create account");
                            }
                        }else {
                            responseJSON.put("request", "failed");
                            responseJSON.put("reason","bank id was null");
                        }
                    }else {
                        responseJSON.put("request", "failed");
                        responseJSON.put("reason","missing required parameters in body");
                    }
                    return responseJSON;
                });
            });
            path("/transaction", () -> {

                get("", (Request request, Response response) -> {

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

                post("", (Request request, Response response) -> {

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

    private static void apiLogSetup() {
        try {
            FileHandler fh;
            fh = new FileHandler("api.log", true);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            logger.addHandler(fh);

            logger.info("*************************************New Session Started*************************************");
        } catch (IOException ioe) {};
    }

    static class ShutdownThread extends Thread {
        ShutdownThread() {}

        public void run() {
            logger.info("*************************************Shutting Down Session*************************************");
        }
    }
}