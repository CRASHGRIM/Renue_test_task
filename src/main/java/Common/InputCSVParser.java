package Common;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class InputCSVParser {

    public static Tree ParseToTree(int column) throws IOException// думаю не стоит считывать все в память, надо читать прям здесь
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
            if (searchStr.length()==0)
            {
                currentTree.AddEndedString(counter);
                continue;
            }

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

    public static ArrayList<String> GetLines(ArrayList<Integer> indexList) throws IOException
    {
        ArrayList<String> outList = new ArrayList<>();
        String fileName = "src/main/resources/airports.dat";// вытаскивать из настроек
        Path path = Paths.get(fileName);
        Scanner scanner = new Scanner(path);

        scanner.useDelimiter(System.getProperty("line.separator"));// здесь мы вытаскиваем из файла строки по индексам
        Collections.sort(indexList);
        int counter = 0;
        for (Integer index: indexList)
        {
            while (counter<index)
            {
                scanner.nextLine();
                counter++;
            }
            outList.add(scanner.nextLine());
            counter++;

        }

        return outList;
    }

    public static HashMap<String, ArrayList<Integer>> ParseToDictionary(int column) throws IOException {

        HashMap<String, ArrayList<Integer>> outDict = new HashMap<String, ArrayList<Integer>>();
        String fileName = "src/main/resources/airports.dat";// вытаскивать из настроек
        Path path = Paths.get(fileName);

        Scanner scanner = new Scanner(path);

        scanner.useDelimiter(System.getProperty("line.separator"));
        int counter = -1;
        while (scanner.hasNext()) {
            String readStr = scanner.nextLine();
            counter++;
            readStr = readStr.replace("\"", "");// убираем кавычки у названий, возможно не стоит делать
            String[] splitted = readStr.split(",");
            String searchStr = splitted[column];
            if (searchStr.length()==0)
            {
                if (!outDict.containsKey(""))
                    outDict.put("", new ArrayList<Integer>());
                outDict.get("").add(counter);
                continue;
            }
            for(int i=0;i<=searchStr.length();i++)
            {
                String prefix = searchStr.substring(0, i);
                if (!outDict.containsKey(prefix))
                    outDict.put(prefix, new ArrayList<Integer>());
                outDict.get(prefix).add(counter);
            }
        }

        return outDict;
    }

}
