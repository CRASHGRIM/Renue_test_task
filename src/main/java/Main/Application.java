package Main;

import Common.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.cli.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private YAMLconfig ymlConfig;

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
            if (!mode.equals("s") && !mode.equals("m"))
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

        ApplicationContext context =
                new AnnotationConfigApplicationContext("Common");

        IinOut IOobj = context.getBean(IinOut.class);

        String filename = ymlConfig.getFilename();
        if (filename==null)
            filename = "airports.dat";
        int indexColumn = ymlConfig.getColumn();

        IOobj.WriteLine("filename: "+filename);
        IOobj.WriteLine("Column: "+indexColumn);

        if (args[1].length()!=0)
        {
            indexColumn = Integer.parseInt(args[1]);// ошибки не будет тк в main проверили что парсится
        }

        Isearchable indexer;

        //args[0]="s";

        if (args[0].equals("m"))// более memory-эффективный вариант
        {
            IOobj.WriteLine("mode m");
            Tree tree = InputCSVParser.ParseToTree(indexColumn, filename);
            indexer = new SearchTree(tree);
        }
        else // аргумента нет или 's'
        {
            IOobj.WriteLine("mode s");
            HashMap<String, ArrayList<Integer>> dict = InputCSVParser.ParseToDictionary(indexColumn, filename);
            indexer = new SearchDictionary(dict);
        }
        String line = "";
        while ((line = IOobj.ReadLine()) != null)
        {
            long time = System.currentTimeMillis();
            ArrayList<Integer> foundIndexes = indexer.Search(line);
            ArrayList<String> foundStr = InputCSVParser.GetLines(foundIndexes, filename);
            long searchTime = System.currentTimeMillis() - time;
            for (String str: foundStr)
            {
                IOobj.WriteLine(str);
            }
            IOobj.WriteLine("Количество строк "+foundStr.size());
            IOobj.WriteLine(String.format("Затраченное время %d ms", searchTime));
            IOobj.WriteLine("Введите строку");
        }
    }

}
