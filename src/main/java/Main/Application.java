package Main;

import Common.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.cli.*;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private YAMLconfig myConfig;

    public static void main(String[] args) {

        Options options = new Options();

        Option input = new Option("m", "mode", true, "indexing mode");
        input.setRequired(false);
        options.addOption(input);

        Option output = new Option("c", "colon", true, "colon to index");
        output.setRequired(false);
        options.addOption(output);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("utility-name", options);

            System.exit(1);
        }

        String mode = cmd.getOptionValue("mode");
        String colon = cmd.getOptionValue("colon");
        if (mode==null)
            mode = "";
        if (colon==null)
            colon = "";

        SpringApplication app = new SpringApplication(Application.class);
        String[] parsedArgs = {mode, colon};
        app.run(parsedArgs);

    }

    public void run(String... args) throws Exception {
        for (String str: args)
        {
            System.out.println(str);
        }
        ApplicationContext context =
                new AnnotationConfigApplicationContext("Common");

        CSVreader reader = context.getBean(CSVreader.class);

        int indexColumn = myConfig.getColumn();

        if (args[1].length()!=0)
        {
            indexColumn = Integer.parseInt(args[1]);//проверить на корректность
        }

        Isearchable indexer;


        if (args[0]=="m")// более memory-эффективный вариант
        {
            Tree tree = InputCSVParser.ParseToTree(indexColumn);
            indexer = new SearchTree(tree);
        }
        else // аргумента нет или 's'
        {
            HashMap<String, ArrayList<Integer>> dict = InputCSVParser.ParseToDictionary(indexColumn);
            indexer = new SearchDictionary(dict);
        }

        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        String line = "";
        while ((line = br.readLine()) != null)
        {
            long time = System.currentTimeMillis();
            ArrayList<Integer> foundIndexes = indexer.Search(line);
            ArrayList<String> foundStr = InputCSVParser.GetLines(foundIndexes);
            for (String str: foundStr)
            {
                System.out.println(str);
            }
            System.out.println(foundStr.size());
            System.out.println(System.currentTimeMillis() - time);// проверить поиск, при Forbes находит пустую строку
        }
    }

}
