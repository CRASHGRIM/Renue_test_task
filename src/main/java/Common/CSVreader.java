package Common;

import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

@Component
public class CSVreader {

    public ArrayList<String> Read() throws IOException {
        ArrayList<String> outList = new ArrayList<String>();

        String fileName = "src/main/resources/airports.dat";// вытаскивать из настроек
        Path path = Paths.get(fileName);

        Scanner scanner = new Scanner(path);

        scanner.useDelimiter(System.getProperty("line.separator"));
        int counter = 0;
        while(scanner.hasNext() && counter<100){
            String readStr = scanner.nextLine();
            readStr = readStr.replace("\"", "");
            outList.add(readStr);
            counter++;
        }
        scanner.close();
        return outList;
    }


}
