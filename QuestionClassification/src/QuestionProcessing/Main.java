

package QuestionProcessing;

import QuestionProcessing.Structures.Pair;
import QuestionProcessing.Structures.SIPair;
import edu.stanford.nlp.dcoref.CorefChain;
import edu.stanford.nlp.dcoref.CorefCoreAnnotations;
import edu.stanford.nlp.io.EncodingPrintWriter;
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.ling.tokensregex.CoreMapNodePattern;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.process.Morphology;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.trees.Dependency;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.IntTuple;

import java.awt.event.MouseMotionAdapter;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.*;



public class Main {


    public static void main(String[] args) {
        // write your code here
        String txt = "who is the first presidnet of america?" ;
        SentenceStructure hwd = new SentenceStructure() ;
        hwd.PrepareText(txt);
        List<CoreMap> sentences= hwd.GetSentences();
        DependencyGraph dependencyGraph = new DependencyGraph(hwd);
        for(CoreMap sentence : sentences)
        {
            dependencyGraph.getDependencyGraph(sentence);
        //    hwd.getDependencyGraph(sentence);
        }

        //headwords , keywords
        // hypernyms
        // relations matching

        // b3d kda nmatch l relation bt3thm beb3d w n7ott 3lehm weight 3aly
    }
}
