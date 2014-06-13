package org.kelvin.minexp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * @author Shashikiran
 */
public class AssociationGenrator
{

    private List<String> tokenStrings;

    public AssociationGenrator(String... tokens)
    {
        tokenStrings = new ArrayList<>();
        tokenStrings.addAll(Arrays.asList(tokens));
    }

    public List<String> getAssociations()
    {
        return getAssociations_(tokenStrings);
    }

    protected List<String> getAssociations_(List<String> tokens)
    {
        List<String> res = new ArrayList<>();
        if (1 == tokens.size()) {
            res.add(tokens.get(0));
        } else {
            for (int i = 0; i < tokens.size(); ++i) {
                List<String> left = tokens.subList(0, i),
                        right = tokens.subList(i, tokens.size());
                for (String l : getAssociations_(left)) {
                    for (String r : getAssociations_(right)) {
                        res.add("(".concat(l).concat(" %s ").concat(r).concat(")"));
                    }
                }
            }
        }
        return res;
    }
}
