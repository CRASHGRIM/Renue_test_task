package Common;

import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Scanner;

@Component
public class CSVreader {

    public HashSet<String> Read() throws IOException {
        HashSet<String> outList = new HashSet<String>();

        String fileName = "src/main/resources/airports.dat";// вытаскивать из настроек
        Path path = Paths.get(fileName);

        Scanner scanner = new Scanner(path);

        scanner.useDelimiter(System.getProperty("line.separator"));
        int counter = 0;
        while(scanner.hasNext() && counter<10){
            outList.add(scanner.nextLine());
            counter++;
        }
        scanner.close();
        return outList;
    }


}
