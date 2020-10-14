package Common;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class SearchTree {

    private Tree tree;


    public SearchTree(Tree tree)
    {
        this.tree = tree;
    }

    public ArrayList<String> Search(String prefix) throws IOException {
        ArrayList<Integer> indexList = new ArrayList<>();
        ArrayList<String> outList = new ArrayList<>();
        Tree currentTree = tree;
        for(int i=0; i<prefix.length();i++)
        {
            Boolean foundFlag = false;
            for (Tree tree:currentTree.getChilds()) {
                if(tree.getLetter()==prefix.charAt(i))
                {
                    foundFlag = true;
                    currentTree = tree;
                    break;
                }
            }
            if (!foundFlag)
                return new ArrayList<String>();// не смогли спуститься дальше по дереву, такого префикса нет
        }
        for (Integer index:currentTree.getEndedStrings()) {
            indexList.add(index);
        }
        ArrayDeque<Tree> queue = new ArrayDeque<Tree>();
        queue.add(currentTree);
        while(!queue.isEmpty())
        {
            Tree queueTree = queue.pollFirst();
            for (Integer index:queueTree.getEndedStrings())
            {
                indexList.add(index);
            }
            for (Tree child:queueTree.getChilds())
            {
                queue.add(child);
            }
        }

        String fileName = "src/main/resources/airports.dat";// вытаскивать из настроек
        Path path = Paths.get(fileName);
        Scanner scanner = new Scanner(path);

        scanner.useDelimiter(System.getProperty("line.separator"));// здесь мы вытаскиваем из файла строки по индексам
        int counter = 0;
        Collections.sort(indexList);
        for (Integer index: indexList)
        {
            for (int i=counter;i<index-1;i++)
            {
                scanner.nextLine();
            }
            outList.add(scanner.nextLine());
            counter = index;

        }

        return outList;
    }

}
