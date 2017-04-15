package main.java; /**
 * Created by daniel on 4/11/17.
 */
import static spark.Spark.get;
import static spark.Spark.post;

public class SparkServer {
    public static void main(String[] args) {

        //Example demonstrating spark returning an html string
        get("/admin", (request, response) -> {return "<HTML>\n" +
                "<HEAD>\n" +
                "<TITLE>Your Title Here</TITLE>\n" +
                "</HEAD>\n" +
                "<BODY BGCOLOR=\"FFFFFF\">\n" +
                "<CENTER><IMG SRC=\"clouds.jpg\" ALIGN=\"BOTTOM\"> </CENTER>\n" +
                "<HR>\n" +
                "<a href=\"http://somegreatsite.com\">Link Name</a>\n" +
                "is a link to another nifty site\n" +
                "<H1>This is a Header</H1>\n" +
                "<H2>This is a Medium Header</H2>\n" +
                "Send me mail at <a href=\"mailto:support@yourcompany.com\">\n" +
                "support@yourcompany.com</a>.\n" +
                "<P> This is a new paragraph!\n" +
                "<P> <B>This is a new paragraph!</B>\n" +
                "<BR> <B><I>This is a new sentence without a paragraph break, in bold italics.</I></B>\n" +
                "<HR>\n" +
                "</BODY>\n" +
                "</HTML>";});

        get("/getAccount", (request, response) -> {return "AccountInfo";});
        get("/getTransaction", (request, response) -> {return "TransactionInfo";});

        post("/createTransaction", (request, response) -> {return "Create Transaction";});
        post("/createAccount", (request, response) -> {return "Create Account";});

    }
}