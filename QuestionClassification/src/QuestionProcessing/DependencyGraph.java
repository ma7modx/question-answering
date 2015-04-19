package QuestionProcessing;
import QuestionProcessing.Structures.Pair;
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.trees.GrammaticalRelation;

import edu.stanford.nlp.util.CoreMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.*;
/**
 * Created by ma7mod on 4/12/15.
 */
public class DependencyGraph {
    private HashMap<IndexedWord, List<Pair<IndexedWord , GrammaticalRelation> > > dependencyGraph ;
    private SentenceStructure sentenceStructure ;

    public DependencyGraph()
    {
        sentenceStructure= new SentenceStructure();
        dependencyGraph = new HashMap<IndexedWord, List<Pair<IndexedWord, GrammaticalRelation>>>();
    }
    public DependencyGraph(SentenceStructure sentenceStructure)
    {
        this.sentenceStructure= sentenceStructure;
        dependencyGraph = new HashMap<IndexedWord, List<Pair<IndexedWord, GrammaticalRelation>>>();
    }
    public void setHeadWordExtractor(SentenceStructure sentenceStructure)
    {
        this.sentenceStructure= sentenceStructure;
    }
    public HashMap<IndexedWord, List<Pair<IndexedWord , GrammaticalRelation> > > getDependencyGraph()
    {
        return dependencyGraph = getDependencyGraph(sentenceStructure.GetSentences().get(0)); // on 1 sentence for now
    }

    public HashMap<IndexedWord, List<Pair<IndexedWord , GrammaticalRelation> > > getDependencyGraph(CoreMap sentence) {
        HashMap<IndexedWord, List<Pair<IndexedWord , GrammaticalRelation> > > dependencyGraph =
                new HashMap<IndexedWord, List<Pair<IndexedWord, GrammaticalRelation>>>();
        SemanticGraph dependencies = sentenceStructure.GetDependencyTree(sentence);


        HashMap<IndexedWord, Integer> words = sentenceStructure.Traverse(dependencies);

        List<Pair<IndexedWord, Integer>> WordsList = new LinkedList<Pair<IndexedWord, Integer>>();

        for (Map.Entry<IndexedWord, Integer> it : words.entrySet())
            WordsList.add(new Pair(it.getKey(), it.getValue()));

        for (int i = 0; i < WordsList.size(); ++i)
        {
            IndexedWord node = WordsList.get(i).first;
            List<edu.stanford.nlp.util.Pair<GrammaticalRelation, IndexedWord>> childs =
                    dependencies.childPairs(node) ;
            for (int j = 0; j < childs.size(); ++j)
            {
                edu.stanford.nlp.util.Pair<GrammaticalRelation, IndexedWord> child
                        = childs.get(j) ; // grammaratical relation, indexed word
                if(!dependencyGraph.containsKey(node))
                    dependencyGraph.put(node , new ArrayList<Pair<IndexedWord, GrammaticalRelation>>());

                String s = child.first().getLongName();
                System.out.println(s);
                dependencyGraph.get(node).add(new Pair<IndexedWord, GrammaticalRelation>(child.second() , child.first()));
            }
        }
        return dependencyGraph;
    }


}
