package Common;

import java.util.ArrayList;
import java.util.List;

public class Tree {

    private ArrayList<String> endedStrings;
    private ArrayList<Tree> childs;
    private char letter;

    Tree(char letter)
    {
        endedStrings = new ArrayList<String>();
        this.letter = letter;
    }

    public ArrayList<String> getEndedStrings()
    {
        return endedStrings;
    }

    public ArrayList<Tree> getChilds()
    {
        return childs;
    }

    public char getLetter()
    {
        return letter;
    }

    public void AddChild(Tree tree)
    {
        this.childs.add(tree);
    }

    public void AddEndedString(String index)
    {
        endedStrings.add(index);
    }


}
