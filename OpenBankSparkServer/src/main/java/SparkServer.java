/**
 * Created by daniel on 4/11/17.
 */


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import spark.Request;
import spark.Response;
import spark.servlet.SparkApplication;
import spark.utils.IOUtils;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static spark.Spark.*;

public class SparkServer implements SparkApplication {
    private static final Logger logger = Logger.getLogger(SparkServer.class.getName());
    static PrintClass print = new PrintClass();

    static private JsonPersistency jsonPersistency;
    static Bank bank;
    static FileHandler fh;

    //Change to true when using authentication
    boolean authentication = false;

    private class SMTPAuth extends Authenticator {
        public PasswordAuthentication authenticateSender() {
            return new PasswordAuthentication("username@gmail.com", "password");
        }
    }

    static public void main(String args[]) {
        SparkApplication sparkApplication = new SparkServer();
        sparkApplication.init();
    }

    public void init() {
        Runtime.getRuntime().addShutdownHook(new ShutdownThread());

        apiLogSetup();
        loadBank();
        createAccounts();

        before("/*", (q, a) -> {
            logger.info("Received api call from ip: " + q.ip()
                    + "\n\t" + q.pathInfo()
                    + "\n\tMessage Content"
                    + "\n\tcontentType: " + q.contentType()
                    + "\n\theaders: " + q.headers().toString()
                    + "\n\tquery params: " + q.queryParams().toString());

            if (authentication) {
                if (!q.pathInfo().equals("/api/v1/user")
                        && !q.pathInfo().equals("/api/v1/login")
                        && !q.pathInfo().equals("/admin/api/v1/login")
                        && !q.pathInfo().equals("/admin/index.html")
                        && !q.pathInfo().equals("/admin/login.html")
                        && !q.pathInfo().equals("/api/v1/account")) {

                    String base64Credentials = null;
                    String credentials = null;

                    if (q.headers().contains("Authorization")) {

                        base64Credentials = q.headers("Authorization").toString().substring("Basic".length()).trim();
                        credentials = new String(Base64.getDecoder().decode(base64Credentials),
                                Charset.forName("UTF-8"));
                        final String[] values = credentials.split(":", 2);

                        String authId = values[0];
                        String authSessionID = values[1];

                        if (authId != null && authSessionID != null) {
                            if (bank.findUserByID(authId) != null) {
                                if (bank.findUserByID(authId).getSessionId().equals(authSessionID)) {

                                    //Extra check below to ensure that the user is logged in
                                    if (bank.findUserByID(authId).isLoggedIn())
                                        logger.info("Authorized Access");
                                    else {
                                        logger.info("Unauthorized Access");
                                        halt(401, "User is not logged in");
                                    }
                                } else {
                                    logger.info("Unauthorized Access");
                                    halt(401, "Unauthorized Access");
                                }
                            } else {
                                logger.info("Unauthorized Access");
                                halt(401, "Unauthorized Access");
                            }
                        } else {
                            logger.info("Unauthorized Access");
                            halt(401, "Unauthorized Access");
                        }
                    } else {
                        logger.info("Unauthorized Access");
                        halt(401, "Unauthorized Access");
                    }
                }
            }
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

            path("/state1", () -> {

                path(".html", () -> {
                    get("", (Request request, Response response) -> {
                        return IOUtils.toString(SparkServer.class.getResourceAsStream("var/www/html/state1.html"));
                    });
                });
            });

            path("/state2", () -> {

                path(".html", () -> {
                    get("", (Request request, Response response) -> {
                        return IOUtils.toString(SparkServer.class.getResourceAsStream("var/www/html/state2.html"));
                    });
                });
            });

            path("/state3", () -> {

                path(".html", () -> {
                    get("", (Request request, Response response) -> {
                        return IOUtils.toString(SparkServer.class.getResourceAsStream("var/www/html/state3.html"));
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

                        if (request.queryParams().contains("username") && request.queryParams().contains("password")) {

                            String username = request.queryParams("username");
                            String password = request.queryParams("password");

                            String id = bank.Login(username, password);

                            if (id != null) {
                                if (bank.findUserByID(id).isLoggedIn()) {
                                    responseJSON.put("authentication", true);
                                    responseJSON.put("userID", id);
                                    responseJSON.put("sessionID", bank.findUserByID(id).getSessionId());
                                    response.status(100);
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
                            responseJSON.put("reason", "missing required parameters in body");
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

                        if (request.queryParams().contains("name")
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

                                if (bank.findUserByID(userId) != null) {
                                    responseJSON.put("request", "successful");
                                    responseJSON.put("userID", bank.findUserByID(userId));
                                } else {
                                    responseJSON.put("request", "failed");
                                    responseJSON.put("reason", "bank failed to create user");
                                }
                            } catch (Exception e) {
                                responseJSON.put("request", "failed");
                                responseJSON.put("reason", "username already exists");
                            }
                        } else {
                            responseJSON.put("request", "failed");
                            responseJSON.put("reason", "missing required parameters in body");
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
                                    multAccountJson.put("accountNum", account.getAccountnum() + "");
                                    multAccountJson.put("balance", account.getBalance().toString());

                                    if(account.getCreationdate() != null)
                                        multAccountJson.put("creationDate", account.getCreationdate().toString());
                                    else
                                        multAccountJson.put("creationDate", "");

                                    if(account.getOwner() != null)
                                        multAccountJson.put("owner", account.getOwner().getName());
                                    else
                                        multAccountJson.put("owner", "");
                                    multAccountJson.put("type", account.getType().toString());
                                    responseJSON.add(multAccountJson);
                                }
                            }

                            return responseJSON;
                        });
                    });


                    path("/transactions", () -> {
                        get("", (Request request, Response response) -> {
                            JSONArray responseJSON = new JSONArray();

                            int accountNum = 0;
                            if(request.queryParams().contains("accountID"))
                                accountNum = Integer.parseInt(request.queryParams("accountID"));

                            Set<TransactionSet> tranlst = bank.getTransactions(accountNum, new BigInteger("0"), null);

                            for (Set s : tranlst) {
                                Iterator itr = s.iterator();
                                JSONObject transactionItem = new JSONObject();

                                if (tranlst.size() == 1) {
                                    Transaction tran = (Transaction) itr.next();
                                    if(tran.getNextTransitive().getDate().first() != null)
                                        transactionItem.put("date", tran.getNextTransitive().getDate().first().toString());
                                    else
                                        transactionItem.put("date", "");
                                    transactionItem.put("transAmount", tran.getNextTransitive().getAmount().first());
                                    transactionItem.put("transType", tran.getNextTransitive().getTransType().first().name());
                                    if(tran.getNextTransitive().getCreationdate().first() != null)
                                        transactionItem.put("creationDate", tran.getNextTransitive().getCreationdate().first().toString());
                                    else
                                        transactionItem.put("creationDate", "");
                                    transactionItem.put("toUserName", tran.getNextTransitive().getToAccount().getOwner().getName().toString()
                                            .substring(1,tran.getNextTransitive().getToAccount().getOwner().getName().toString().length()-1));
                                    if(tran.getNextTransitive().getToAccount().first().getType() != null)
                                        transactionItem.put("toAccountType", tran.getNextTransitive().getToAccount().first().getType().toString());
                                    else
                                        transactionItem.put("toAccountType", "");
                                    transactionItem.put("balanceAfter", tran.getNextTransitive().getToAccount().getBalance());
                                    transactionItem.put("note", tran.getNextTransitive().getNote().first());
                                    responseJSON.add(transactionItem);

                                } else if (tranlst.size() > 1) {
                                    while (itr.hasNext()) {
                                        Transaction tran = (Transaction) itr.next();
                                        if(tran.getNextTransitive().getDate().first() != null)
                                            transactionItem.put("date", tran.getNextTransitive().getDate().first().toString());
                                        else
                                            transactionItem.put("date", "");
                                        transactionItem.put("transAmount", tran.getNextTransitive().getAmount().first());
                                        transactionItem.put("transType", tran.getNextTransitive().getTransType().first().name());
                                        if(tran.getNextTransitive().getCreationdate().first() != null)
                                            transactionItem.put("creationDate", tran.getNextTransitive().getCreationdate().first().toString());
                                        else
                                            transactionItem.put("creationDate", "");
                                        transactionItem.put("toUserName", tran.getNextTransitive().getToAccount().getOwner().getName().toString()
                                                .substring(1,tran.getNextTransitive().getToAccount().getOwner().getName().toString().length()-1));
                                        if(tran.getNextTransitive().getToAccount().first().getType() != null)
                                            transactionItem.put("toAccountType", tran.getNextTransitive().getToAccount().first().getType().toString());
                                        else
                                            transactionItem.put("toAccountType", "");
                                        transactionItem.put("balanceAfter", tran.getNextTransitive().getToAccount().getBalance());
                                        transactionItem.put("note", tran.getNextTransitive().getNote().first());
                                        responseJSON.add(transactionItem);
                                    }
                                }
                            }

                            return responseJSON;
                        });
                    });
                });
            });
        });


        path("/api/v1", () -> {

            path("/login", () -> {

                get("", (Request request, Response response) -> {

                    JSONObject responseJSON = new JSONObject();

                    if (request.queryParams().contains("id")) {
                        String id = request.queryParams("id");

                        responseJSON.put("isUserLoggedIn", bank.findUserByID(id).isLoggedIn());
                    }

                    return responseJSON;
                });

                post("", (Request request, Response response) -> {
                    JSONObject responseJSON = new JSONObject();

                    if (request.queryParams().contains("username") && request.queryParams().contains("password")) {

                        String username = request.queryParams("username");
                        String password = request.queryParams("password");

                        String id = bank.Login(username, password);

                        if (id != null) {
                            if (bank.findUserByID(id).isLoggedIn()) {
                                responseJSON.put("authentication", true);
                                responseJSON.put("userID", id);
                                responseJSON.put("sessionID", bank.findUserByID(id).getSessionId());

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
                        responseJSON.put("reason", "missing required parameters in body");
                    }
                    return responseJSON;
                });
            });

            path("/logout", () -> {
                post("", (Request request, Response response) -> {
                    JSONObject responseJSON = new JSONObject();

                    if (request.queryParams().contains("id")) {
                        String id = request.queryParams("id");

                        if (id != null) {
                            if (bank.findUserByID(id) != null) {
                                if (bank.findUserByID(id).logout()) {
                                    responseJSON.put("request", "successful");
                                    responseJSON.put("sessionID", bank.findUserByID(id).getSessionId());
                                } else {
                                    responseJSON.put("request", "failed");
                                    responseJSON.put("reason", "failed to logout the user");
                                }
                            } else {
                                responseJSON.put("request", "failed");
                                responseJSON.put("reason", "user could not be found");
                            }
                        } else {
                            responseJSON.put("request", "failed");
                            responseJSON.put("reason", "null user id");
                        }
                    } else {
                        responseJSON.put("authentication", false);
                        responseJSON.put("reason", "missing required parameters in body");
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

                    if (request.queryParams().contains("name")
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

                            if (bank.findUserByID(userId) != null) {
                                responseJSON.put("request", "successful");
                                responseJSON.put("userID", userId.toString());
                            } else {
                                responseJSON.put("request", "failed");
                                responseJSON.put("reason", "bank failed to create user");
                            }
                        } catch (Exception e) {
                            responseJSON.put("request", "failed");
                            responseJSON.put("reason", "username already exists");
                        }
                    } else {
                        responseJSON.put("request", "failed");
                        responseJSON.put("reason", "missing required parameters in body");
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

                        if (request.queryParams().contains("id")) {

                            id = request.queryParams("id");
                            User user = bank.findUserByID(id);

                            if (user != null) {

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

                            } else {
                                responseJSON.put("request", "failed");
                                responseJSON.put("reason", "user could not be found");
                            }
                        } else {
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

                        if (request.queryParams().contains("accountID")) {
                            int accountID = Integer.parseInt(request.queryParams("accountID"));

                            User user = bank.findUserByID(id);

                            if (user != null) {
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
                            } else {
                                accountJson.put("request", "failed");
                                accountJson.put("reason", "user with id " + id + " does not exist");
                                jsonArray.add(accountJson);
                            }
                        } else {
                            User user = bank.findUserByID(id);

                            if (user != null) {
                                AccountSet accounts = user.getAccount();

                                if (accounts != null) {
                                    if (accounts.size() > 0) {
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
                                    } else {
                                        accountJson.put("request", "failed");
                                        accountJson.put("reason", "user does not have any accounts");
                                        jsonArray.add(accountJson);
                                    }
                                } else {
                                    accountJson.put("request", "failed");
                                    accountJson.put("reason", "user account set is null");
                                    jsonArray.add(accountJson);
                                }
                            } else {
                                accountJson.put("request", "failed");
                                accountJson.put("reason", "user with id " + id + " does not exist");
                                jsonArray.add(accountJson);
                            }
                        }
                    } else {
                        accountJson.put("request", "failed");
                        accountJson.put("reason", "missing required parameters in body");
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

                        AccountTypeEnum accountTypeEnum = AccountTypeEnum.valueOf(accountType);

                        if (bank.findUserByID(id) != null) {
                            //TODO add accountType and have the method return the accountID
                            StringBuilder msg = new StringBuilder();
                            String accountID = bank.createAccount(id, false, new BigInteger(initialBalance), accountTypeEnum, msg);

                            logger.info("Message: " + msg.toString());
                            logger.info("userId: " + accountID.toString());

                            if (accountID != null) {
                                responseJSON.put("request", "successful");
                                responseJSON.put("accountNum", accountID.toString());
                            } else {
                                responseJSON.put("request", "failed");
                                responseJSON.put("reason", "bank failed to create account");
                            }
                        } else {
                            responseJSON.put("request", "failed");
                            responseJSON.put("reason", "bank id was null");
                        }
                    } else {
                        responseJSON.put("request", "failed");
                        responseJSON.put("reason", "missing required parameters in body");
                    }
                    return responseJSON;
                });
            });

            path("/transaction", () -> {
                get("", (Request request, Response response) -> {
                    int accountId = 0;
                    JSONObject responseJSON = new JSONObject();

                    if (request.queryParams().contains("accountID")) {
                        logger.info("Test: " + request.queryParams("accountID"));
                        accountId = Integer.parseInt(request.queryParams("accountID"));

                        if (bank.findAccountByID(accountId) != null) {

                            Set<TransactionSet> tranlst = bank.getTransactions(accountId, new BigInteger("0"), null);
                            JSONArray tempJsonArray = new JSONArray();

                            for (Set s : tranlst) {
                                Iterator itr = s.iterator();
                                JSONObject transactionItem = new JSONObject();

                                if (tranlst.size() == 1) {
                                    Transaction tran = (Transaction) itr.next();
                                    transactionItem.put("creationDate", tran.getDate());
                                    transactionItem.put("transAmount", tran.getAmount());
                                    transactionItem.put("transType", tran.getTransType().toString());
                                    if(tran.getToAccount() != null) {
                                        transactionItem.put("toUserName", tran.getToAccount().getOwner().getName());
                                        transactionItem.put("toAccountType", tran.getToAccount().getType().toString());
                                        transactionItem.put("balanceAfter", tran.getToAccount().getBalance());
                                    }
                                    else {
                                        transactionItem.put("toUserName", "");
                                        transactionItem.put("toAccountType", "");
                                        transactionItem.put("balanceAfter", tran.getFromAccount().getBalance());
                                    }
                                    transactionItem.put("note", tran.getNote());
                                    tempJsonArray.add(transactionItem);

                                } else if (tranlst.size() > 1) {
                                    while (itr.hasNext()) {
                                        Transaction tran = (Transaction) itr.next();
                                        transactionItem.put("creationDate", tran.getDate());
                                        transactionItem.put("transAmount", tran.getAmount());
                                        transactionItem.put("transType", tran.getTransType().toString());
                                        if(tran.getToAccount() != null) {
                                            transactionItem.put("toUserName", tran.getToAccount().getOwner().getName());
                                            transactionItem.put("toAccountType", tran.getToAccount().getType().toString());
                                            transactionItem.put("balanceAfter", tran.getToAccount().getBalance());
                                        }
                                        else {
                                            transactionItem.put("toUserName", "");
                                            transactionItem.put("toAccountType", "");
                                            transactionItem.put("balanceAfter", tran.getFromAccount().getBalance());
                                        }
                                        transactionItem.put("note", tran.getNote());
                                        tempJsonArray.add(transactionItem);
                                    }
                                }
                            }
                            responseJSON.put("request", "success");
                            responseJSON.put("transactions", tempJsonArray);
                        } else {
                            responseJSON.put("request", "failed");
                            responseJSON.put("reason", "account id was null");
                        }
                    } else {
                        responseJSON.put("request", "failed");
                        responseJSON.put("reason", "missing required parameters in body(accoutID)");
                    }
                    return responseJSON;
                });

                post("", (Request request, Response response) -> {
                    JSONObject responseJSON = new JSONObject();

                    String amount = "";
                    int fromAccountId = 0;
                    int toAccountId = 0;
                    String transferType = "";
                    if (request.queryParams().contains("transferType")) {
                        transferType = request.queryParams("transferType");
                        TransactionTypeEnum transactionTypeEnum = TransactionTypeEnum.valueOf(transferType);

                        if (transferType.equals("TRANSFER")) {
                            if (request.queryParams().contains("amount")
                                    && request.queryParams().contains("fromAccount")
                                    && request.queryParams().contains("toAccount")) {
                                amount = request.queryParams("amount");
                                fromAccountId = Integer.parseInt(request.queryParams("fromAccount"));
                                toAccountId = Integer.parseInt(request.queryParams("toAccount"));

                                Account accountFrom = bank.findAccountByID(fromAccountId);
                                Account accountTo = bank.findAccountByID(toAccountId);

                                if (accountFrom != null && accountTo != null) {

                                    BigInteger bigInteger = new BigInteger(amount);

                                    if (accountFrom.getBalance().compareTo(bigInteger) == 1) {


                                        Transaction transaction = bank.createTransaction();
                                        transaction.withAmount(bigInteger)
                                                .withDate(new Date())
                                                .withCreationdate(new Date())
                                                .withFromAccount(accountFrom)
                                                .withToAccount(accountTo)
                                                .withTransType(transactionTypeEnum)
                                                .withTime(new Date())
                                                .withNote(accountFrom.getOwner().getName() + " has transfer money to " + accountTo.getOwner().getName());

//                                        StringBuilder msg = new StringBuilder();
//                                        if (accountFrom.transferToAccount(bigInteger, accountTo, accountFrom.getOwner().getName() + "has transfer money to" + accountTo.getOwner().getName())) {
//                                            accountFrom.getToTransaction().add(transaction);
                                        StringBuilder msg2 = new StringBuilder();
//                                            bank.recordTransaction(accountFrom.getAccountnum(), accountTo.getAccountnum(), TransactionTypeEnum.TRANSFER, bigInteger, accountFrom.getOwner() + " is transfers to " + accountTo.getOwner(), false, msg2);

                                        responseJSON.put("request", "success");
                                        responseJSON.put("note", transaction.getNote());
                                        responseJSON.put("balance", (accountFrom.getBalance()));
                                        responseJSON.put("transactionFrom", transaction.getFromAccount().getAccountnum());
                                        responseJSON.put("transactionTo", transaction.getToAccount().getAccountnum());

//                                        } else {
//                                            responseJSON.put("request", "failed");
//                                            responseJSON.put("reason", "user may not be logged in");
//                                        }
                                    } else {
                                        responseJSON.put("request", "failed");
                                        responseJSON.put("reason", "you do not have enough funds to transfer");
                                    }
                                } else {
                                    responseJSON.put("request", "failed");
                                    responseJSON.put("reason", "requested account does not exist");
                                }
                            } else {
                                responseJSON.put("request", "failed");
                                responseJSON.put("reason", "missing required parameters in body");
                            }
                        } else if (transferType.equals("DEPOSIT")) {
                            int toAccount;
                            if (request.queryParams().contains("amount") && request.queryParams().contains("toAccount")) {

                                amount = request.queryParams("amount");
                                toAccount = Integer.parseInt(request.queryParams("toAccount"));

                                Account depositToAccount = bank.findAccountByID(toAccount);
                                BigInteger bigInteger = new BigInteger(amount);

                                if (depositToAccount != null) {
                                    StringBuilder msg = new StringBuilder();
                                    bank.depositFunds(toAccount, bigInteger, msg);
                                    StringBuilder msg2 = new StringBuilder();
//                                    bank.recordTransaction(-1, toAccount, TransactionTypeEnum.DEPOSIT, bigInteger, bank.findAccountByID(toAccount).getOwner() + " Deposits", false, msg2);

                                    responseJSON.put("request", "success");
                                    responseJSON.put("balance", (depositToAccount.getBalance()));
                                } else {
                                    responseJSON.put("request", "fail");
                                    responseJSON.put("reason", "requested account does not exist");
                                }
                            }
                        } else if (transferType.equals("WITHDRAW")) {
                            int fromAccount;
                            if (request.queryParams().contains("amount")
                                    && request.queryParams().contains("fromAccount")) {
                                amount = request.queryParams("amount");
                                fromAccount = Integer.parseInt(request.queryParams("fromAccount"));
                                Account withdrawFromAccount = bank.findAccountByID(fromAccount);
                                BigInteger bigInteger = new BigInteger(amount);
                                logger.info("bigInteger: " + bigInteger.toString());

                                if(withdrawFromAccount!= null) {

                                    try {
                                        StringBuilder msg = new StringBuilder();
                                        bank.withDrawFunds(fromAccount, bigInteger, msg);
//                                        StringBuilder msg2 = new StringBuilder();
//                                        bank.recordTransaction(fromAccount, -1, TransactionTypeEnum.DEPOSIT, bigInteger, bank.findAccountByID(fromAccount).getOwner() + " Withdraws", false, msg2);

                                        responseJSON.put("request", "success");
                                        responseJSON.put("balance", (withdrawFromAccount.getBalance()));
                                    } catch (Exception e) {
                                        logger.info("Error: " + e.toString());
                                        responseJSON.put("request", "fail");
                                        responseJSON.put("reason", "you do not have enough funds to ");
                                    }
                                }else {
                                    responseJSON.put("request", "failed");
                                    responseJSON.put("reason", "account does not exist");
                                }
                            }else {
                                responseJSON.put("request", "failed");
                                responseJSON.put("reason", "missing required parameters in body");
                            }
                        } else {
                            responseJSON.put("request", "failed");
                            responseJSON.put("reason", "incorrect transfer type");
                        }
                    } else {
                        responseJSON.put("request", "failed");
                        responseJSON.put("reason", "missing required parameters in body");
                    }

                    return responseJSON;
                });
            });
            path("/passwordReset", () -> {
                post("", (Request request, Response response) -> {
                    JSONObject responseJSON = new JSONObject();
                    if (request.queryParams().contains("newPassword")
                            && request.queryParams().contains("id")) {

                        String newPassword = request.queryParams("newPassword");
                        String id = request.queryParams("id");

                        if (bank.findUserByID(id) != null) {

                            bank.findUserByID(id).withPassword(newPassword);

                            if (bank.findUserByID(id).getPassword().equals(newPassword))
                                responseJSON.put("request", "successful");
                            else {
                                responseJSON.put("request", "failed");
                                responseJSON.put("reason", "password not successfully changed");
                            }
                        } else {
                            responseJSON.put("request", "failed");
                            responseJSON.put("reason", "user could not be found");
                        }
                    } else {
                        responseJSON.put("request", "failed");
                        responseJSON.put("reason", "missing required parameters in body");
                    }
                    return responseJSON;
                });
            });
            //Send an email to the user if the user provided an email that
            //is registered with the system.
            //Verified that /sendEmail works - Daniel
            path("/sendEmail", () -> {
                post("", (Request request, Response response) -> {
                    JSONObject responseJSON = new JSONObject();
                    Set<User> users;
                    if (request.queryParams().contains("email")) {
                        String email = request.queryParams("email");
                        boolean foundEmail = false;
                        users = bank.getCustomerUser();

                        for (Iterator<User> iterator = users.iterator(); iterator.hasNext(); ) {
                            User userItem = iterator.next();
                            if (userItem.getEmail().equals(email)) {
                                foundEmail = true;
                                String code = bank.generateCode();
                                String userID = userItem.getUserID();
                                EmailHandler handler = new EmailHandler();
                                String subject = "OPEN BANK Password reset code";
                                String body = "Password Reset Code \n\n Hello, \n\n We received a request to change "
                                        + "your Open Bank password. You can change your password by using "
                                        + "the following code " + code + ". "
                                        + "Please, copy and paste the code to the provided area to continue the "
                                        + "process. If you did not made this request, let us know by "
                                        + "replying to this email \n\n Thank You! \n\n -Open Bank";
                                handler.createEmail(body, subject, "openbankservice@gmail.com", email);
                                responseJSON.put("request", "successful");
                                responseJSON.put("id", userID);
                                break;
                            }
                        }
                        if (!foundEmail) {
                            responseJSON.put("request", "failed");
                            responseJSON.put("reason", "email not registered to a user");
                        }
                    } else {
                        responseJSON.put("request", "failed");
                        responseJSON.put("reason", "missing required parameters in body");
                    }
                    return responseJSON;
                });
            });

            //To verify that the code received is the one created by the bank system.
            //Verified that /verifyCode works - Daniel
            path("/verifyCode", () -> {
                post("", (Request request, Response response) -> {
                    JSONObject responseJSON = new JSONObject();
                    Set<User> users;
                    if (request.queryParams().contains("code")) {
                        String code = request.queryParams("code");
                        if (bank.confirmCode(code))
                            responseJSON.put("request", "successful");
                        else {
                            responseJSON.put("request", "failed");
                            responseJSON.put("reason", "code does not match");
                        }
                    } else {
                        responseJSON.put("request", "failed");
                        responseJSON.put("reason", "missing required parameters in body");
                    }

                    return responseJSON;
                });
            });

            //Can get rid of this later.
//            path("/changePassword", () -> {
//                post("", (Request request, Response response) -> {
//                    JSONObject responseJSON = new JSONObject();
//                    if(request.queryParams().contains("username")
//                            && request.queryParams().contains("password")) {
//
//                        String newPassword = request.queryParams("password");
//                        String username = request.queryParams("username");
//                        boolean foundUsername = false;
//                        Set<User> users = bank.getCustomerUser();
//
//                        for(Iterator<User> iterator = users.iterator(); iterator.hasNext(); ) {
//                            User userItem = iterator.next();
//                            if (userItem.getUsername().equals(username)) {
//                                foundUsername = true;
//                                userItem.withPassword(newPassword);
//                                responseJSON.put("request", "successful");
//                            }
//                        }
//                        if(!foundUsername) {
//                            responseJSON.put("request", "failed");
//                            responseJSON.put("reason","wrong username");
//                        }
//                    }
//                    else {
//                        responseJSON.put("request", "failed");
//                        responseJSON.put("reason","missing required parameters in body");
//                    }
//                    return responseJSON;
//                });
//            });
        });
    }

    private static void createAccounts() {

        Account admin = null;

        if (bank.getAdminAccounts().size() < 1) {
            logger.info("Creating initial Admin account");
            Account adminAccount = bank.createAdminAccounts();
            adminAccount.setIsConnected(true);
            adminAccount.setType(AccountTypeEnum.CHECKING);
            adminAccount.setAccountnum(bank.getNextID());
            adminAccount.setBalance(new BigInteger("10000000000000"));
            admin = adminAccount;
        }

        if (bank.getCustomerUser().size() == 0) {
            logger.info("Creating initial User Accounts account");
            StringBuilder msg1 = new StringBuilder();
            String id1 = bank.createUser("AntEater", "AntsAreTasty",
                    "Tom Eater", "5712342393", "antEatingAntEater1@gmail.com",
                    false, msg1);
            StringBuilder msg2 = new StringBuilder();
            String id2 = bank.createUser("soyAdmin", "sauceAdmin",
                    "Soy Sauce", "7777777777", "soySauceStaple@gmail.com",
                    true, msg2);

            StringBuilder msg3 = new StringBuilder();
            String accountID = bank.createAccount(id1, false, new BigInteger("0"), AccountTypeEnum.CHECKING, msg3);

            logger.info("1: " + msg1);
            logger.info("2: " + msg2);
            logger.info("3: " + msg3);

//            Transaction transaction1 = bank.createTransaction();
//            transaction1.withAmount(new BigInteger("500000000000"))
//                    .withDate(new Date())
//                    .withCreationdate(new Date())
//                    .withFromAccount(admin)
//                    .withToAccount(bank.findAccountByID(Integer.parseInt(accountID)))
//                    .withTransType(TransactionTypeEnum.SEED)
//                    .withTime(new Date())
//                    .withNote("Seeding account");
//
//            Transaction transaction2 = bank.createTransaction();
//            transaction2.withAmount(new BigInteger("50000000"))
//                    .withDate(new Date())
//                    .withCreationdate(new Date())
//                    .withFromAccount(bank.findAccountByID(Integer.parseInt(accountID)))
//                    .withToAccount(admin)
//                    .withTransType(TransactionTypeEnum.FEE)
//                    .withTime(new Date())
//                    .withNote("Fee");

            bank.recordTransaction(admin.getAccountnum(), Integer.parseInt(accountID), TransactionTypeEnum.SEED, new BigInteger("50000000"), "Seeding Account", false, msg2);

        }
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
        } catch (Exception ex) {
            logger.info("Bank json failed to load." + "\n\tReason: " + ex.toString());
            bank = new Bank().withBankName("OpenBank");
            logger.info("Creating new Bank: " + bank.getBankName());

            FeeValue f1,f2,f3,f4;

            f1 = new FeeValue()
                    .withBank(bank)
                    .withTransType(TransactionTypeEnum.DEPOSIT)
                    .withPercent(new BigInteger("50000000")); //.05
            f2 = new FeeValue()
                    .withBank(bank)
                    .withTransType(TransactionTypeEnum.WITHDRAW)
                    .withPercent(new BigInteger("50000000")); //.05
            f3 = new FeeValue()
                    .withBank(bank)
                    .withTransType(TransactionTypeEnum.TRANSFER)
                    .withPercent(new BigInteger("50000000")); //.05
            f4 = new FeeValue()
                    .withBank(bank)
                    .withTransType(TransactionTypeEnum.SEED)
                    .withPercent(new BigInteger("50000000")); //.05
        }
    }

    private static void apiLogSetup() {
        try {
            fh = new FileHandler("api.log", true);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            logger.addHandler(fh);

            logger.info("*************************************New Session Started*************************************");
        } catch (IOException ioe) {
        }
        ;
    }

    private static void apiLogClose() {
        fh.close();
    }

    static class ShutdownThread extends Thread {
        ShutdownThread() {
        }

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
        int numRead = 0;
        while ((numRead = reader.read(buf)) != -1) {
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
        }
        reader.close();
        return fileData.toString();
    }
}