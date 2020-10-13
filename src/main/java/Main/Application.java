package Main;

import Common.CSVreader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.util.HashSet;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private YAMLconfig myConfig;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.run();

    }

    public void run(String... args) throws Exception {
        ApplicationContext context =
                new AnnotationConfigApplicationContext("Common");

        CSVreader reader = context.getBean(CSVreader.class);

        HashSet<String> res = null;
        try {
            res = reader.Read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String a:res)
        {
            System.out.println(a);
        }
        System.out.println("using index: " + myConfig.getColumn());
    }

}
