package Common;

import java.util.ArrayList;

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
        // здесь нужно сделать очередь и BFS обойти все поддеревья
        return outList;
    }

}
