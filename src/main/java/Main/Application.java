package Main;

import Common.*;
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
import java.util.HashMap;

import org.apache.commons.cli.*;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private YAMLconfig myConfig;

    public static void main(String[] args) {

        Options options = new Options();

        Option modeOpt = new Option("m", "mode", true, "indexing mode");
        modeOpt.setRequired(false);
        options.addOption(modeOpt);

        Option colonOpt = new Option("c", "colon", true, "colon to index");
        colonOpt.setRequired(false);
        options.addOption(colonOpt);

        Option help = new Option("h", "help", false, "print help");
        help.setRequired(false);
        options.addOption(help);

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

        if (mode!=null)
        {
            if (mode!="s" || mode!="m")
            {
                System.out.println("Wrong mode");
                formatter.printHelp("utility-name", options);
                System. exit(1);
            }
        }
        else
        {
            mode = "";
        }


        if (colon!=null)
        {
            try{
                Integer.parseInt(colon);
            }
            catch (NumberFormatException e)
            {
                System.out.println("Wrong index column");
                formatter.printHelp("utility-name", options);
                System. exit(1);
            }
        }
        else
        {
            colon = "";
        }

        SpringApplication app = new SpringApplication(Application.class);
        String[] parsedArgs = {mode, colon};
        app.run(parsedArgs);

    }

    public void run(String... args) throws Exception {
        for (String str: args)
        {
            System.out.println(str);
        }


        String filename = myConfig.getFilename();
        if (filename==null)
            filename = "airports.dat";// думаю не стоит подсовывать какое то значение а лучше свалиться с ошибкой
        int indexColumn = myConfig.getColumn();

        System.out.println("filename: "+filename);
        System.out.println("Column: "+indexColumn);

        if (args[1].length()!=0)
        {
            indexColumn = Integer.parseInt(args[1]);// проверили что парсится в main
        }

        Isearchable indexer;

        if (args[0]=="m")// более memory-эффективный вариант
        {
            System.out.println("mode m");
            Tree tree = InputCSVParser.ParseToTree(indexColumn, filename);
            indexer = new SearchTree(tree);
        }
        else // аргумента нет или 's'
        {
            System.out.println("mode s");
            HashMap<String, ArrayList<Integer>> dict = InputCSVParser.ParseToDictionary(indexColumn, filename);
            indexer = new SearchDictionary(dict);
        }

        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        String line = "";
        while ((line = br.readLine()) != null)
        {
            long time = System.currentTimeMillis();
            ArrayList<Integer> foundIndexes = indexer.Search(line);
            ArrayList<String> foundStr = InputCSVParser.GetLines(foundIndexes, filename);
            long searchTime = System.currentTimeMillis() - time;
            for (String str: foundStr)
            {
                System.out.println(str);
            }
            System.out.println("Количество строк "+foundStr.size());// посортить ответы когда поиск по словарю (не работает)
            System.out.println("Затраченное время "+searchTime);// проверить поиск, при Forbes в дереве находит пустую строку
            System.out.println("Введите строку");// в дереве надо тоже посортить, делать обход в глубину по алфавиту
            // когда пишешь S все падает
        }
    }

}
