/**
 * Created by daniel on 4/11/17.
 */

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import spark.Request;
import spark.Response;
import spark.utils.IOUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static spark.Spark.*;

public class SparkServer {
    private static final Logger logger = Logger.getLogger(SparkServer.class.getName());
    static PrintClass print = new PrintClass();

    static private JsonPersistency jsonPersistency;
    static Bank bank;
    static FileHandler fh;

    public static void main(String[] args) {

        Runtime.getRuntime().addShutdownHook(new ShutdownThread());

        apiLogSetup();
        loadBank();

        before("/*", (q, a) -> {
            logger.info("Received api call from ip: " + q.ip()
                    + "\n\t" + q.pathInfo()
                    + "\n\tMessage Content"
                    + "\n\tcontentType: " + q.contentType()
                    + "\n\theaders: " + q.headers().toString()
                    + "\n\tquery params: " + q.queryParams().toString());
        });

        get("/", (Request request, Response response) -> {
            return "TeamRed OpenBank";
        });

        path("/admin", () -> {

            path("/www/var/js", () -> {
                get("/ui-bootstrap-tpls-0.11.2.min.js", (Request request, Response response) -> {
                    return IOUtils.toString(SparkServer.class.getResourceAsStream("var/www/js/ui-bootstrap-tpls-0.11.2.min.js"));
                });
                get("/app.js", (Request request, Response response) -> {
                    return IOUtils.toString(SparkServer.class.getResourceAsStream("var/www/js/app.js"));
                });
                get("/routing.js", (Request request, Response response) -> {
                    return IOUtils.toString(SparkServer.class.getResourceAsStream("var/www/js/routing.js"));
                });
                get("/globalFunctions.js", (Request request, Response response) -> {
                    return IOUtils.toString(SparkServer.class.getResourceAsStream("var/www/js/globalFunctions.js"));
                });
                get("/authInterceptor.js", (Request request, Response response) -> {
                    return IOUtils.toString(SparkServer.class.getResourceAsStream("var/www/js/authInterceptor.js"));
                });
                get("/parentController.js", (Request request, Response response) -> {
                    return IOUtils.toString(SparkServer.class.getResourceAsStream("var/www/js/parentController.js"));
                });
                get("/login.js", (Request request, Response response) -> {
                    return IOUtils.toString(SparkServer.class.getResourceAsStream("var/www/js/login.js"));
                });
                get("/auth.js", (Request request, Response response) -> {
                    return IOUtils.toString(SparkServer.class.getResourceAsStream("var/www/js/auth.js"));
                });
                get("/session.js", (Request request, Response response) -> {
                    return IOUtils.toString(SparkServer.class.getResourceAsStream("var/www/js/session.js"));
                });
            });

            path("/index", () -> {

                path(".html", () -> {
                    get("", (Request request, Response response) -> {
                        return IOUtils.toString(SparkServer.class.getResourceAsStream("var/www/html/index.html"));
                    });
                });
            });

            path("/home", () -> {

                path(".html", () -> {
                    get("", (Request request, Response response) -> {
                        return IOUtils.toString(SparkServer.class.getResourceAsStream("var/www/html/home.html"));
                    });
                });
            });

            path("/login", () -> {

                path(".html", () -> {
                    get("", (Request request, Response response) -> {
                        return IOUtils.toString(SparkServer.class.getResourceAsStream("var/www/html/login.html"));
                    });

                    post("", (Request request, Response response) -> {
                        JSONObject responseJSON = new JSONObject();

                        if(request.queryParams().contains("username") && request.queryParams().contains("password")) {

                            String username = request.queryParams("username");
                            String password = request.queryParams("password");

                            String id = bank.Login(username, password);

                            if(id != null) {
                                if (bank.findUserByID(id).isLoggedIn()) {
                                    responseJSON.put("authentication", true);
                                    responseJSON.put("userID", id);
                                    return IOUtils.toString(SparkServer.class.getResourceAsStream("var/www/html/index.html"));
                                } else {
                                    responseJSON.put("authentication", false);
                                    responseJSON.put("reason", "failed to login the user");
                                    response.status(401);
                                    return responseJSON;
                                }
                            } else {
                                responseJSON.put("authentication", false);
                                responseJSON.put("reason", "user could not be found");
                                response.status(401);
                                return responseJSON;
                            }

                        } else {
                            responseJSON.put("authentication", false);
                            responseJSON.put("reason","missing required parameters in body");
                            return responseJSON;

                        }
                    });
                });

                post("", (Request request, Response response) -> {
                    JSONObject responseJSON = new JSONObject();

                    if(request.queryParams().contains("username") && request.queryParams().contains("password")) {

                        String username = request.queryParams("username");
                        String password = request.queryParams("password");

                        String id = bank.Login(username, password);

                        if(id != null) {
                            if (bank.findUserByID(id).isLoggedIn()) {
                                responseJSON.put("authentication", true);
                                responseJSON.put("userID", id);
                                return IOUtils.toString(SparkServer.class.getResourceAsStream("var/www/html/index.html"));
                            } else {
                                responseJSON.put("authentication", false);
                                responseJSON.put("reason", "failed to login the user");
                                response.status(401);
                                return responseJSON;
                            }
                        } else {
                            responseJSON.put("authentication", false);
                            responseJSON.put("reason", "user could not be found");
                            response.status(401);
                            return responseJSON;
                        }

                    } else {
                        responseJSON.put("authentication", false);
                        responseJSON.put("reason","missing required parameters in body");
                        return responseJSON;

                    }
                });
            });

            path("/info", () -> {
                get("/log", (Request request, Response response) -> {

                    return readFileAsString("api.log");
                });
            });

            path("/viewTransactions", () -> {
                get("", (Request request, Response response) -> {

                    return IOUtils.toString(SparkServer.class.getResourceAsStream("var/www/html/viewTransactions.html"));
                });
            });

            path("/api/v1", () -> {
                path("/login", () -> {
                    post("", (Request request, Response response) -> {
                        JSONObject responseJSON = new JSONObject();

                        if(request.queryParams().contains("username") && request.queryParams().contains("password")) {

                            String username = request.queryParams("username");
                            String password = request.queryParams("password");

                            String id = bank.Login(username, password);

                            if(id != null) {
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

                path("/createUser", () -> {
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
                                && request.queryParams().contains("phoneNumber")
                                && request.queryParams().contains("isAdmin")
                                && request.queryParams().contains("email")) {
                            name = request.queryParams("name");
                            username = request.queryParams("username");
                            password = request.queryParams("password");
                            phoneNumber = request.queryParams("phoneNumber");
                            isAdmin = Boolean.parseBoolean(request.queryParams("isAdmin"));
                            email = request.queryParams("email");

                            StringBuilder msg = new StringBuilder();
                            try {
                                String userId = bank.createUser(username, password, name, phoneNumber, email, isAdmin, msg);
                                logger.info("Message: " + msg.toString());
                                logger.info("userId: " + userId.toString());

                                if(bank.findUserByID(userId) != null) {
                                    responseJSON.put("request", "successful");
                                    responseJSON.put("userID", bank.findUserByID(userId));
                                }
                                else {
                                    responseJSON.put("request", "failed");
                                    responseJSON.put("reason","bank failed to create user");
                                }
                            }catch (Exception e) {
                                responseJSON.put("request", "failed");
                                responseJSON.put("reason","username already exists");
                            }
                        }else {
                            responseJSON.put("request","failed");
                            responseJSON.put("reason","missing required parameters in body");
                        }
                        return responseJSON;
                    });
                });

                path("/listOf", () -> {
                    path("/users", () -> {
                        get("", (Request request, Response response) -> {
                            JSONArray responseJSON = new JSONArray();

                            UserSet customerUser = bank.getCustomerUser();

                            for (User user : customerUser) {
                                JSONObject multUserJson = new JSONObject();
                                if (user != null) {
                                    multUserJson.put("name", user.getName());
                                    multUserJson.put("id", user.getUserID());
                                    multUserJson.put("email", user.getEmail());
                                    multUserJson.put("phoneNumber", user.getPhone());
                                    multUserJson.put("username", user.getUsername());
                                    multUserJson.put("password", user.getPassword());

                                    responseJSON.add(multUserJson);
                                }
                            }

                            return responseJSON;
                        });
                    });
                    path("/accounts", () -> {
                        get("", (Request request, Response response) -> {
                            JSONArray responseJSON = new JSONArray();

                            AccountSet customerAccounts = bank.getCustomerAccounts();

                            for (Account account : customerAccounts) {
                                JSONObject multAccountJson = new JSONObject();
                                if (account != null) {
                                    multAccountJson.put("accountNum", account.getAccountnum());
                                    multAccountJson.put("balance", account.getBalance());
                                    multAccountJson.put("creationDate", account.getCreationdate());
                                    multAccountJson.put("owner", account.getOwner());
                                    multAccountJson.put("type", account.getType());
                                    responseJSON.add(multAccountJson);
                                }
                            }

                            return responseJSON;
                        });
                    });
                    path("/transactions", () -> {
                        get("", (Request request, Response response) -> {
                            JSONArray responseJSON = new JSONArray();
                            JSONObject placeHolder = new JSONObject();

                            placeHolder.put("request", "failed");
                            placeHolder.put("reason","bank failed to create user");

                            responseJSON.add(placeHolder);

                            //TODO add TransactionSet
//                        TransactionSet customerTransactions = bank.getTransaction();
//
//                        for (Transaction transaction : customerTransactions) {
//                            JSONObject multTransactionJson = new JSONObject();
//                            logger.info("User: " + transaction.toString());
//                            if (transaction != null) {
//                                multTransactionJson.put("from", transaction.getFromAccount());
//                                multTransactionJson.put("to", transaction.getToAccount());
//                                multTransactionJson.put("ammount", transaction.getAmount());
//                                multTransactionJson.put("creationDate", transaction.getCreationdate());
//                                multTransactionJson.put("note", transaction.getNote());
//
//                                responseJSON.add(multTransactionJson);
//                            }
//                        }

                            return responseJSON;
                        });
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

                //TODO this will be edited in the future to only allow a user to get their own info
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
                    String email = "";

                    if(request.queryParams().contains("name")
                            && request.queryParams().contains("username")
                            && request.queryParams().contains("password")
                            && request.queryParams().contains("phoneNumber")
                            && request.queryParams().contains("email")) {
                        name = request.queryParams("name");
                        username = request.queryParams("username");
                        password = request.queryParams("password");
                        phoneNumber = request.queryParams("phoneNumber");
                        email = request.queryParams("email");

                        StringBuilder msg = new StringBuilder();
                        try {
                            String userId = bank.createUser(username, password, name, phoneNumber, email, false, msg);
                            logger.info("Message: " + msg.toString());
                            logger.info("userId: " + userId.toString());

                            if(bank.findUserByID(userId) != null) {
                                responseJSON.put("request", "successful");
                                responseJSON.put("userID", userId.toString());
                            }
                            else {
                                responseJSON.put("request", "failed");
                                responseJSON.put("reason","bank failed to create user");
                            }
                        }catch (Exception e) {
                            responseJSON.put("request", "failed");
                            responseJSON.put("reason","username already exists");
                        }
                    }else {
                        responseJSON.put("request","failed");
                        responseJSON.put("reason","missing required parameters in body");
                    }
                    return responseJSON;
                });

                path("/update", () -> {
                    post("", (Request request, Response response) -> {

                        JSONObject responseJSON = new JSONObject();

                        String id = "";
                        String name = "";
                        String username = "";
                        String password = "";
                        String phoneNumber = "";
                        String email = "";

                        if(request.queryParams().contains("id")) {

                            id = request.queryParams("id");
                            User user = bank.findUserByID(id);

                            if(user != null) {

                                if (request.queryParams().contains("name")) {
                                    name = request.queryParams("name");
                                    user.setName(name);
                                }

                                if (request.queryParams().contains("username")) {
                                    username = request.queryParams("username");
                                    user.setUsername(username);
                                }

                                //TODO may remove this in the future
                                if (request.queryParams().contains("password")) {
                                    password = request.queryParams("password");
                                    user.setPassword(password);
                                }

                                if (request.queryParams().contains("phoneNumber")) {
                                    phoneNumber = request.queryParams("phoneNumber");
                                    user.setPhone(phoneNumber);
                                }

                                if (request.queryParams().contains("email")) {
                                    email = request.queryParams("email");
                                    user.setEmail(email);
                                }

                                responseJSON.put("request", "success");

                            }else {
                                responseJSON.put("request", "failed");
                                responseJSON.put("reason", "user could not be found");
                            }
                        }else {
                            responseJSON.put("request", "failed");
                            responseJSON.put("reason", "missing required parameters in body (id)");
                        }
                        return responseJSON;
                    });
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

                            User user = bank.findUserByID(id);

                            if(user != null) {
                                Account account = bank.findAccountByID(accountID);

                                if (account != null) {
                                    accountJson.put("request", "successful");
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
                                accountJson.put("request", "failed");
                                accountJson.put("reason", "user with id " + id + " does not exist");
                                jsonArray.add(accountJson);
                            }
                        }else {
                            User user = bank.findUserByID(id);

                            if(user != null) {
                                AccountSet accounts = user.getAccount();

                                if (accounts != null) {
                                    if(accounts.size() > 0) {
                                        accountJson.put("request", "successful");
                                        jsonArray.add(accountJson);
                                        JSONArray tempJsonArray = new JSONArray();
                                        for (Account account : accounts) {
                                            JSONObject multAccountJson = new JSONObject();
                                            if (account != null) {
                                                multAccountJson.put("accountNumber", account.getAccountnum());
                                                multAccountJson.put("accountType", account.getType());
                                                multAccountJson.put("balance", account.getBalance());
                                                tempJsonArray.add(multAccountJson);
                                            } else {
                                                accountJson.put("request", "failed");
                                                accountJson.put("reason", "user account null error");
                                                jsonArray.add(accountJson);
                                            }
                                        }
                                        jsonArray.add(tempJsonArray);
                                    }else {
                                        accountJson.put("request", "failed");
                                        accountJson.put("reason", "user does not have any accounts");
                                        jsonArray.add(accountJson);
                                    }
                                } else {
                                    accountJson.put("request", "failed");
                                    accountJson.put("reason", "user account set is null");
                                    jsonArray.add(accountJson);
                                }
                            }else {
                                accountJson.put("request", "failed");
                                accountJson.put("reason", "user with id " + id + " does not exist");
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
                        String initialBalance = request.queryParams("initialBalance");
                        double convertedInitialBalance = Double.parseDouble(initialBalance);
                        BigInteger bigInteger = new BigDecimal(convertedInitialBalance).toBigInteger();

                        AccountTypeEnum accountTypeEnum = AccountTypeEnum.valueOf(accountType);

                        if(bank.findUserByID(id) != null) {
                            //TODO add accountType and have the method return the accountID
                            StringBuilder msg = new StringBuilder();
                            String accountID = bank.createAccount(id, false, new BigInteger(bigInteger.toString()), accountTypeEnum, msg);

                            logger.info("Message: " + msg.toString());
                            logger.info("userId: " + accountID.toString());

                            if (accountID != null) {
                                responseJSON.put("request", "successful");
                                responseJSON.put("accountNum", accountID.toString());
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
                        responseJSON.put("transactions", bank.getTransaction());

                    } else
                    {
                        responseJSON.put("request", "failed");
                    }
                    return responseJSON;
                });

                post("", (Request request, Response response) -> {
                    JSONObject responseJSON = new JSONObject();

                    String amount = "";
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
                                amount = request.queryParams("amount");
                                fromAccountId = Integer.parseInt(request.queryParams("fromAccountId"));
                                toAccountId = Integer.parseInt(request.queryParams("toAccountId"));
                                // transferType = request.queryParams("transferType");
                                TransactionTypeEnum transactionTypeEnum = TransactionTypeEnum.valueOf(transferType);

                                Account accountFrom = bank.findAccountByID(fromAccountId);
                                Account accountTo = bank.findAccountByID(toAccountId);

                                if (accountFrom != null && accountTo != null) {

                                    BigInteger bigInteger = new BigInteger(amount);

                                    if (accountFrom.getBalance().compareTo(bigInteger) == -1){
                                        Transaction transaction = bank.createTransaction();
                                        transaction.withAmount(bigInteger)
                                                .withCreationdate(new Date())
                                                .withFromAccount(accountFrom)
                                                .withToAccount(accountTo)
                                                .withTransType(transactionTypeEnum)
                                                .withTime(new Date())
                                                .withNote(accountFrom.getOwner().getName() + " has transferred money to " + accountTo.getOwner().getName());
                                        if(accountFrom.transferToAccount(bigInteger, accountTo, accountFrom.getOwner().getName() + "has transfer money to" + accountTo.getOwner().getName()))
                                        {
                                            // have to add to the transactionSet
                                            responseJSON.put("request", "success");
                                            responseJSON.put("note", transaction.getNote());
                                            responseJSON.put("balance", (accountFrom.getBalance()));
                                            responseJSON.put("transactionFrom", transaction.getFromAccount().getAccountnum());
                                            responseJSON.put("transactionTo", transaction.getToAccount().getAccountnum());
                                        }else {
                                            responseJSON.put("request", "failed");
                                            responseJSON.put("reason", "user may not be logged in");
                                        }
                                    }
                                    else{
                                        responseJSON.put("request", "failed");
                                        responseJSON.put("reason", "you do not have enough funds to transfer");
                                    }
                                }
                                else
                                {
                                    responseJSON.put("request", "failed");
                                    responseJSON.put("reason", "requested account does not exist");
                                }
                            }
                        }
                        else if (transferType.equals("Deposit")) {
                            int toAccount;
                            if (request.queryParams().contains("amount") && request.queryParams().contains("toAccountId"))
                            {
                                amount = request.queryParams("amount");
                                toAccount = Integer.parseInt(request.queryParams("toAccountId"));
                                Account depositToAccount  = bank.findAccountByID(toAccount);
                                if (depositToAccount != null)
                                {
                                    depositToAccount.deposit(new BigDecimal(amount).toBigInteger());
                                    responseJSON.put("request", "success");
                                    responseJSON.put("balance", (depositToAccount.getBalance()));
                                }else
                                {
                                    responseJSON.put("request", "failed");
                                    responseJSON.put("reason", "requested account does not exist");
                                }
                            }
                            else
                            {
                                responseJSON.put("request", "failed");
                                responseJSON.put("reason","missing required parameters in body");
                            }
                        }
                        else if (transferType.equals("Withdraw")){
                            int fromAccount;
                            if (request.queryParams().contains("amount") && request.queryParams().contains("fromAccountId"))
                            {
                                amount = request.queryParams("amount");
                                fromAccount = Integer.parseInt(request.queryParams("fromAccountId"));
                                Account withdrawFromAccount  = bank.findAccountByID(fromAccount);
                                if(withdrawFromAccount != null) {
                                    withdrawFromAccount.withdraw(new BigDecimal(amount).toBigInteger());
                                    responseJSON.put("request", "success");
                                    responseJSON.put("balance", (withdrawFromAccount.getBalance()));
                                }else
                                {
                                    responseJSON.put("request", "failed");
                                    responseJSON.put("reason", "requested account does not exist");
                                }
                            }
                            else
                            {
                                responseJSON.put("request", "failed");
                                responseJSON.put("reason","missing required parameters in body");
                            }
                        }
                    }
                    return responseJSON;
                });
            });
        });
    }

    private static void loadBank() {
        jsonPersistency = new JsonPersistency();

        try {
            bank = jsonPersistency.bankFromJson("OpenBank");

            if (bank == null) {
                bank = new Bank().withBankName("OpenBank");
                logger.info("Creating new Bank: " + bank.getBankName());
            } else {
                logger.info("Loaded bank: " + bank.getBankName());
            }
        }catch(Exception ex) {
            logger.info("Bank json failed to load." + "\n\tReason: " + ex.toString());
            bank = new Bank().withBankName("OpenBank");
            logger.info("Creating new Bank: " + bank.getBankName());
        }
    }

    private static void apiLogSetup() {
        try {
            fh = new FileHandler("api.log", true);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            logger.addHandler(fh);

            logger.info("*************************************New Session Started*************************************");
        } catch (IOException ioe) {};
    }

    private static void apiLogClose() {
        fh.close();
    }

    static class ShutdownThread extends Thread {
        ShutdownThread() {}

        public void run() {
            logger.info("*************************************Shutting Down Session*************************************");
            apiLogClose();
            jsonPersistency.bankToJson(bank);
        }
    }

    static class PrintClass {
        public void print(String printString) {
            System.out.println(printString);
        }
    }

    static private String readFileAsString(String filePath) throws IOException {
        StringBuffer fileData = new StringBuffer();
        BufferedReader reader = new BufferedReader(
                new FileReader(filePath));
        char[] buf = new char[1024];
        int numRead=0;
        while((numRead=reader.read(buf)) != -1){
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
        }
        reader.close();
        return fileData.toString();
    }
}