package Common;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public class SearchTree {

    private Tree tree;


    public SearchTree(Tree tree)
    {
        this.tree = tree;
    }

    public ArrayList<String> Search(String prefix)
    {
        ArrayList<String> outList = new ArrayList<>();
        Tree currentTree = tree;
        Boolean breakFlag = false;
        for(int i=0; i<prefix.length();i++)
        {
            if (breakFlag)
                return outList;
            for (Tree tree:currentTree.getChilds()) {
                if(tree.getLetter()==prefix.charAt(i))
                {
                    currentTree = tree;
                    break;
                }
                breakFlag = true; // не смогли спуститься дальше по дереву, такого префикса нет
            }
        }
        for (String str:currentTree.getEndedStrings()) {
            outList.add(str);
        }
        ArrayDeque<Tree> queue = new ArrayDeque<Tree>();
        queue.add(currentTree);
        while(!queue.isEmpty())
        {
            Tree queueTree = queue.pollFirst();
            for (String str:queueTree.getEndedStrings())
            {
                outList.add(str);
            }
            for (Tree child:queueTree.getChilds())
            {
                queue.add(child);
            }
        }
        return outList;
    }

}
