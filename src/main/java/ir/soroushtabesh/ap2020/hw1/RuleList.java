package main.java.ir.soroushtabesh.ap2020.hw1;

import java.util.*;

public class RuleList {
    //IN THIS CLASS, YOU NEED TO DEFINE THE APPROPRIATE FIELD(S)
    private Map<String, Set<String>> exclusions;

    /**
     * Constructs a new Taboo using the given rules
     *
     * @param rules rules for new Taboo
     */
    public RuleList(List<String> rules) {
        exclusions = new HashMap<>();
        for (int i = 0; i < rules.size() - 1; i++) {
            Set<String> nxs = new HashSet<>();
            Set<String> tmp = exclusions.putIfAbsent(rules.get(i), nxs);
            if (tmp != null)
                nxs = tmp;
            nxs.add(rules.get(i + 1));
        }
    }

    /**
     * Returns the set of elements which should not follow
     * the given element.
     *
     * @param elem
     * @return elements which should not follow the given element
     */
    public Set<String> noFollow(String elem) {
        return exclusions.getOrDefault(elem, Collections.emptySet()); // YOUR CODE HERE
    }

    /**
     * Removes elements from the given list that
     * violate the rules
     *
     * @param list collection to reduce
     */
    public void reduce(List<String> list) {
        for (int i = list.size() - 2; i >= 0; i--) {
            while (i+1 < list.size()
                    && exclusions.getOrDefault(list.get(i),Collections.emptySet()).contains(list.get(i + 1))) {
                list.remove(i + 1);
            }
        }
    }
}
