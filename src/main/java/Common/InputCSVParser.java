package Common;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class InputCSVParser {

    public static Tree ParseToTree(int column, String fileName) throws IOException
    {
        Tree mainTree = new Tree('f');

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
            if (splitted.length<column+1)  // возможно стоит падать с ошибкой
                continue;
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

    public static ArrayList<String> GetLines(ArrayList<Integer> indexList, String fileName) throws IOException
    {
        ArrayList<String> outList = new ArrayList<>();
        HashMap<Integer, String> foundStrings = new HashMap<>();
        Path path = Paths.get(fileName);
        Scanner scanner = new Scanner(path);

        scanner.useDelimiter(System.getProperty("line.separator"));// здесь мы вытаскиваем из файла строки по индексам
        ArrayList<Integer> defaultorderindexList = new ArrayList<>(indexList);
        Collections.sort(indexList);// сортим исходный список чтоб вытащить все строки за один проход, но запоминаем стары порядок потому что там отсортировано
        int counter = 0;
        for (Integer index: indexList)
        {
            while (counter<index)
            {
                scanner.nextLine();
                counter++;
            }
            String line = scanner.nextLine();
            foundStrings.put(counter, line);
            counter++;

        }

        scanner.close();

        for (int i=0; i<defaultorderindexList.size();i++)
        {
            outList.add(foundStrings.get(defaultorderindexList.get(i))); // добавляем найденные строки в том порядке который был в аргументах метода
        }

        return outList;
    }

    public static HashMap<String, ArrayList<Integer>> ParseToDictionary(int column, String fileName) throws IOException {

        HashMap<String, ArrayList<Integer>> outDict = new HashMap<String, ArrayList<Integer>>();
        Path path = Paths.get(fileName);

        HashMap<Integer, String> lineIndexToColumn = new HashMap<>();

        Scanner scanner = new Scanner(path);

        scanner.useDelimiter(System.getProperty("line.separator"));
        int counter = -1;
        while (scanner.hasNext()) {
            String readStr = scanner.nextLine();
            counter++;
            readStr = readStr.replace("\"", "");// убираем кавычки у названий, возможно не стоит делать
            String[] splitted = readStr.split(",");
            if (splitted.length<column+1)  // возможно стоит падать с ошибкой
                continue;
            String searchStr = splitted[column];
            lineIndexToColumn.put(counter, searchStr);
            if (searchStr.length()==0)
            {
                if (!outDict.containsKey(""))
                    outDict.put("", new ArrayList<Integer>());
                outDict.get("").add(counter);
                continue;
            }
            for(int i=1;i<=searchStr.length();i++)
            {
                String prefix = searchStr.substring(0, i);
                if (!outDict.containsKey(prefix))
                    outDict.put(prefix, new ArrayList<Integer>());
                outDict.get(prefix).add(counter);
            }
        }
        scanner.close();
        //далее в каждом префиксе сортируем строки по колонке

        for (String key:outDict.keySet())
        {
            ArrayList<Integer> original = outDict.get(key);


            HashMap<String, ArrayList<Integer>> searchstrToLineIndexes = new HashMap<>();
            HashSet<String> searchStrings = new HashSet<String>();

            for (int i=0;i<original.size();i++)
            {
                Integer index = original.get(i);
                String searchstr = lineIndexToColumn.get(index);
                if (!searchstrToLineIndexes.containsKey(searchstr))
                    searchstrToLineIndexes.put(searchstr, new ArrayList<>());
                searchstrToLineIndexes.get(searchstr).add(index); // запоминаем у каких подстрок какие индексы чтоб после сортировки восстановить индексы
                searchStrings.add(lineIndexToColumn.get(index)); // собираем хэшсет который потом будем сортить
            }
            ArrayList<String> sortedSearchstrings = new ArrayList<>(searchStrings);
            Collections.sort(sortedSearchstrings); // ортируем подстроки
            ArrayList<Integer> sortedOriginal = new ArrayList<>();

            for (int i=0;i<sortedSearchstrings.size();i++)
            {
                for (Integer index:searchstrToLineIndexes.get(sortedSearchstrings.get(i)))
                {
                    sortedOriginal.add(index); // добавляем в список все индексы которые соответствуют данной подстроке
                }
            }




            outDict.replace(key, sortedOriginal);// заменяем несортированный список сортированным

        }



        return outDict;
    }

}
