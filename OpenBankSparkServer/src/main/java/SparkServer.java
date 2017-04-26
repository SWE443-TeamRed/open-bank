/**
 * Created by daniel on 4/11/17.
 */

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import spark.Request;
import spark.Response;
import spark.utils.IOUtils;

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

    static PrintClass print = new PrintClass();

    static Transaction transaction = null;

    static private JsonPersistency jsonPersistency;

    static int i = 1;
    static int j = 1;

   static Map<Integer, Account> accountMap;

    static Bank bank;

    public static void main(String[] args) {

        Runtime.getRuntime().addShutdownHook(new ShutdownThread());

        jsonPersistency = new JsonPersistency();
        bank = jsonPersistency.bankFromJson("OpenBank");

        if(bank == null) {
            bank = new Bank().withBankName("OpenBank");
            logger.info("Creating new Bank: " + bank.getBankName());
        }
        else
            logger.info("Loaded bank: " + bank.getBankName());

        apiLogSetup();
        accountMapSetup();

        //sort(list, Comparable::<String>compareTo);
//        before("/*", (q, a) -> {
//            return logger.info("Received api call from ip: " + q.ip()
//                    + "\n\t" + q.pathInfo()
//                    + "\n\tMessage Content"
//                    + "\n\tcontentType: " + q.contentType()
//                    + "\n\theaders: " + q.headers().toString()
//                    + "\n\tquery params: " + q.queryParams().toString());
//        });
        get("/", (Request request, Response response) -> {
            return "TeamRed OpenBank";
        });

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

            path("/login", () -> {
                get("", (Request request, Response response) -> {

                    return IOUtils.toString(SparkServer.class.getResourceAsStream("var/www/html/index.html"));
                });
            });

            path("/viewTransactions", () -> {
                get("", (Request request, Response response) -> {

                    return IOUtils.toString(SparkServer.class.getResourceAsStream("var/www/html/viewTransactions.html"));
                });
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


        path("/api/v1", () -> {

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
                    String email = "";


                    if(request.queryParams().contains("name")
                            && request.queryParams().contains("username")
                            && request.queryParams().contains("password")
                            && request.queryParams().contains("isAdmin")
                            && request.queryParams().contains("phoneNumber")
                            && request.queryParams().contains("email")) {
                        name = request.queryParams("name");
                        username = request.queryParams("username");
                        password = request.queryParams("password");
                        isAdmin = Boolean.parseBoolean(request.queryParams("isAdmin"));
                        phoneNumber = request.queryParams("phoneNumber");
                        email = request.queryParams("email");

                        User user = new User()
                                .withName(name)
                                .withUsername(username)
                                .withPhone(phoneNumber)
                                .withUserID("" + j)
                                .withPassword(password)
                                .withIsAdmin(isAdmin)
                                .withEmail(email);
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

                            if(accounts != null) {
                                for (Account account : accounts) {
                                    JSONObject multAccountJson = new JSONObject();
                                    logger.info("Account: " + account.toString());
                                    if (account != null) {
                                        multAccountJson.put("accountNumber", account.getAccountnum());
                                        multAccountJson.put("accountType", account.getType());
                                        multAccountJson.put("balance", account.getBalance());
                                        jsonArray.add(multAccountJson);
                                    }
                                }
                            } else {
                                accountJson.put("request", "failed");
                                accountJson.put("reason","user with id " + id + " does not exist");
                                jsonArray.add(accountJson);
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


                        AccountTypeEnum accountTypeEnum = null;

                        if(accountType.equals("CHECKING")) {
                            logger.info("Checking found");
                            accountTypeEnum = AccountTypeEnum.CHECKING;
                        } else if(accountType.equals("SAVINGS")) {
                            logger.info("Savings found");
                            accountTypeEnum = AccountTypeEnum.SAVINGS;
                        } else {
                            logger.info("None found");
                        }

                        if(bank.findUserByID(id) != null) {
                            Account account = bank.findUserByID(id).createAccount()
                                    .withAccountnum(i)
                                    .withOwner(bank.findUserByID(id))
                                    .withType(accountTypeEnum)
                                    .withBalance(initialBalance);
                            bank.withCustomerAccounts(account);
                            if (account != null) {
                                responseJSON.put("request", "successful");
                                responseJSON.put("accountNum", account.getAccountnum());
//                                responseJSON.put("test", account.toString());
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
                    int accountId = 0;
                    //TransactionSet allTransactions = new TransactionSet();
                    JSONObject responseJSON = new JSONObject();

                    if(request.queryParams().contains("accountID"))
                    {
                        accountId = Integer.parseInt(request.queryParams("accountID"));
                        Account account = bank.findAccountByID(accountId);
                      /*  for( int i=0; i< allTransactions.size(); i++)
                        {
                            responseJSON.put("Amount", allTransactions.get(i).getAmount());
                            responseJSON.put("Note",allTransactions.get(i).getNote());
                        }*/
                        responseJSON.put("request", "success");
                        //responseJSON.put("transactions", account.getCredit());
                        responseJSON.put("transactions", account.getCredit());

                    } else
                    {
                        responseJSON.put("request", "failed");
                    }
                    return responseJSON;
                });

                post("", (Request request, Response response) -> {
                    JSONObject responseJSON = new JSONObject();

                    double amount = 0;
                    int fromAccountId = 0;
                    int toAccountId = 0;
                    String transferType = "";
                    if(request.queryParams().contains("transferType"))
                    {
                        transferType = request.queryParams("transferType");
                        if (transferType.equals("Transfer")) {
                            if (request.queryParams().contains("amount")
                                && request.queryParams().contains("fromAccountId")
                                && request.queryParams().contains("toAccountId")) {
                                //  && request.queryParams().contains("transferType")) {
                                amount = Double.parseDouble(request.queryParams("amount"));
                                fromAccountId = Integer.parseInt(request.queryParams("fromAccountId"));
                                toAccountId = Integer.parseInt(request.queryParams("toAccountId"));
                                // transferType = request.queryParams("transferType");
                                TransactionTypeEnum transactionTypeEnum = TransactionTypeEnum.valueOf(transferType);

                                Account accountFrom = bank.findAccountByID(fromAccountId);
                                Account accountTo = bank.findAccountByID(toAccountId);

                                if (accountFrom != null && accountTo != null) {

                                    if (accountFrom.getBalance() > amount) {
                                        Transaction transaction = bank.createTransaction();
                                        transaction.withAmount(amount)
                                                .withCreationdate(new Date())
                                                .withFromAccount(accountFrom)
                                                .withToAccount(accountTo)
                                                .withTransType(transactionTypeEnum)
                                                .withTime(new Date())
                                                .withNote(accountFrom.getOwner().getName() + " has transferred money to " + accountTo.getOwner().getName());
                                        accountFrom.transferToAccount(amount, accountTo, accountFrom.getOwner().getName() + "has transfer money to" + accountTo.getOwner().getName());
                                        // have to add to the transactionSet

                                        responseJSON.put("request", "success");
                                        responseJSON.put("note", transaction.getNote());
                                        responseJSON.put("balance", (accountFrom.getBalance()));
                                        responseJSON.put("transactionFrom", transaction.getFromAccount().getAccountnum());
                                        responseJSON.put("transactionTo", transaction.getToAccount().getAccountnum());
                                    }
                                    else{
                                        responseJSON.put("request", "fail");
                                        responseJSON.put("reason", "you do not have enough funds to transfer");
                                    }
                                }
                                else
                                {
                                    responseJSON.put("request", "fail");
                                    responseJSON.put("reason", "requested account does not exist");
                                }
                            }
                        }
                        else if (transferType.equals("Deposit")) {
                                int toAccount;
                                if (request.queryParams().contains("amount") && request.queryParams().contains("toAccount"))
                                {
                                    amount = Double.parseDouble(request.queryParams("amount"));
                                    toAccount = Integer.parseInt(request.queryParams("toAccount"));
                                    Account depositToAccount  = bank.findAccountByID(toAccount);
                                    depositToAccount.deposit(amount);
                                    responseJSON.put("request", "success");
                                    responseJSON.put("balance", (depositToAccount.getBalance()));
                                }
                                else
                                {
                                    responseJSON.put("request", "fail");
                                    responseJSON.put("reason", "requested account does not exist");
                                }
                        }
                        else if (transferType.equals("Withdraw")){
                            int fromAccount;
                            if (request.queryParams().contains("amount") && request.queryParams().contains("fromAccount"))
                            {
                                amount = Double.parseDouble(request.queryParams("amount"));
                                fromAccount = Integer.parseInt(request.queryParams("fromAccount"));
                                Account withdrawFromAccount  = bank.findAccountByID(fromAccount);
                                withdrawFromAccount.withdraw(amount);
                                responseJSON.put("request", "success");
                                responseJSON.put("balance", (withdrawFromAccount.getBalance()));
                            }
                            else
                            {
                                responseJSON.put("request", "fail");
                                responseJSON.put("reason", "requested account does not exist");
                            }
                        }
                     }
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
            jsonPersistency.bankToJson(bank);
            logger.info("*************************************Shutting Down Session*************************************");
        }
    }

    static class PrintClass {
        public void print(String printString) {
            System.out.println(printString);
        }
    }
}