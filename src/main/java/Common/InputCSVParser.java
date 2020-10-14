package Common;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class InputCSVParser {

    public static Tree Parse(ArrayList<String> strings, int column) throws IOException// думаю не стоит считывать все в память, надо читать прям здесь
    {
        Tree mainTree = new Tree('f');

        String fileName = "src/main/resources/airports.dat";// вытаскивать из настроек
        Path path = Paths.get(fileName);

        Scanner scanner = new Scanner(path);

        scanner.useDelimiter(System.getProperty("line.separator"));
        int counter = -1;
        while(scanner.hasNext()){
            String readStr = scanner.nextLine();
            counter++;
            readStr = readStr.replace("\"", "");// убираем кавычки у названий, возможно не стоит делать


            Tree currentTree = mainTree;
            String[] splitted = readStr.split(",");
            String searchStr = splitted[column];
            for (int i=0;i<searchStr.length();i++)
            {
                Boolean treeFound = false;
                for (Tree tree:currentTree.getChilds()) {
                    if(tree.getLetter()==searchStr.charAt(i))
                    {
                        treeFound = true;
                        currentTree = tree;
                        break;
                    }
                }
                if (!treeFound)
                {
                    Tree newTree = new Tree(searchStr.charAt(i));
                    currentTree.AddChild(newTree);
                    currentTree = newTree;
                }
            }
            currentTree.AddEndedString(counter);  // храним только индексы строк
        }
        scanner.close();

        return mainTree;
    }

}
