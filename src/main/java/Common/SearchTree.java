package Common;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class SearchTree implements Isearchable {

    private Tree tree;


    public SearchTree(Tree tree)
    {
        this.tree = tree;
    }

    public ArrayList<Integer> Search(String prefix){
        ArrayList<Integer> indexList = new ArrayList<>();
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
                return new ArrayList<Integer>();// не смогли спуститься дальше по дереву, такого префикса нет
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

        return indexList;
    }

}
