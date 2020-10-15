package Common;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;

public class SearchDictionary implements Isearchable{

    HashMap<String, ArrayList<Integer>> prefixDictionary;

    public SearchDictionary(HashMap<String, ArrayList<Integer>> dict)
    {
        this.prefixDictionary = dict;
    }

    public ArrayList<Integer> Search(String prefix)
    {
        if (prefixDictionary.containsKey(prefix))
            return prefixDictionary.get(prefix);
        return new ArrayList<Integer>();
    }

}
