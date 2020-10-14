package Common;

import java.util.ArrayList;

public class InputCSVParser {

    public static Tree Parse(ArrayList<String> strings, int column)// думаю не стоит считывать все в память, надо читать прям здесь
    {
        Tree mainTree = new Tree('f');
        for (String str:strings) {
            Tree currentTree = mainTree;
            String[] splitted = str.split(",");
            int index = Integer.parseInt(splitted[0]);
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
            currentTree.AddEndedString(str);


        }
        return mainTree;
    }

}
