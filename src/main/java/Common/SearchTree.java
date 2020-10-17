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
                return new ArrayList<>();// не смогли спуститься дальше по дереву, такого префикса нет
        }
        Stack<Tree> stack = new Stack<>();
        stack.add(currentTree);
        while (!stack.empty())
        {
            Tree popTree = stack.pop();
            for (Integer index:popTree.getEndedStrings())
            {
                indexList.add(index);
            }
            ArrayList<Tree> childs = popTree.getChilds();
            Collections.sort(childs, (s1, s2) -> Character.compare(s2.getLetter(), s1.getLetter()));//  в обратном порядке добавляем
            for (int i=0; i<childs.size();i++)
                stack.push(childs.get(i));
        }

        return indexList;
    }

}
