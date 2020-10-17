package Common;

import java.util.ArrayList;
import java.util.List;

public class Tree {

    private ArrayList<Integer> endedStrings;
    private ArrayList<Tree> childs;
    private final char letter;

    Tree(char letter)
    {
        endedStrings = new ArrayList<Integer>();
        childs = new ArrayList<Tree>();
        this.letter = letter;
    }

    public ArrayList<Integer> getEndedStrings()
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

    public void AddEndedString(Integer index)
    {
        endedStrings.add(index);
    }


}
