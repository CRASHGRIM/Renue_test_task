package Main;

import Common.CSVreader;
import Common.InputCSVParser;
import Common.SearchTree;
import Common.Tree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private YAMLconfig myConfig;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.run(args);

    }

    public void run(String... args) throws Exception {
        for (String str: args)
        {
            System.out.println(str);
        }
        ApplicationContext context =
                new AnnotationConfigApplicationContext("Common");

        CSVreader reader = context.getBean(CSVreader.class);

        ArrayList<String> res = null;
        try {
            res = reader.Read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Tree tree = InputCSVParser.Parse(res, myConfig.getColumn());
        SearchTree searchTree = new SearchTree(tree);

        //for (String a:res)
        //{
        //    System.out.println(a);
        //}
        System.out.println("using index: " + myConfig.getColumn());

        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        String line = "";
        while ((line = br.readLine()) != null)
        {
            ArrayList<String> foundStr = searchTree.Search(line);
            for (String str: foundStr)
            {
                System.out.println(str);
            }
        }
    }

}
