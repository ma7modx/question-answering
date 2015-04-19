package QuestionProcessing;
import QuestionProcessing.Structures.SIPair;
import edu.stanford.nlp.dcoref.CorefChain;
import edu.stanford.nlp.dcoref.CorefCoreAnnotations;
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.*;

/**
 * Created by ma7mod on 3/27/15.
 */
public class SentenceStructure {

    public HashMap<IndexedWord,Integer> headWords = new HashMap<IndexedWord, Integer>() ;
    private Properties props ;
    private Annotation document;
    private StanfordCoreNLP pipeline ;
    private List<CoreMap> sentences ;
    String text = "" ;

    public SentenceStructure()
    {
        props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
        pipeline = new StanfordCoreNLP(props);

    }
    public void PrepareText(String text)
    {
        this.text = text ;

        headWords = new HashMap<IndexedWord, Integer>() ;

        document = new Annotation(text);

        pipeline.annotate(document);

        sentences = document.get(CoreAnnotations.SentencesAnnotation.class);

    }
    public List<CoreMap> GetSentences ()
    {
        return sentences ;
    }
    public Tree GetParseTree(CoreMap sentence)
    {
        // this is the parse tree of the current sentence
        return sentence.get(TreeCoreAnnotations.TreeAnnotation.class);

    }
    public SemanticGraph GetDependencyTree(CoreMap sentence)
    {
        return sentence.get(SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation.class);
    }

    private HashMap<IndexedWord,Integer> Traverse(SemanticGraph dependencies , IndexedWord word , HashMap<IndexedWord,Integer> headWords )
    {
        if (!headWords.containsKey(word.value())) {
            int weight = 0;
            headWords.put(word, dependencies.getChildList(word).size());
            // return headWords;
        }

        if (!dependencies.getChildList(word).isEmpty())
            for (IndexedWord child : dependencies.getChildList(word)) {
                if (!headWords.containsKey(child.value())) {
                    headWords.putAll(Traverse(dependencies, child, headWords)); //baby :D
                }

            }
        return headWords;
    }
    public HashMap<IndexedWord,Integer> Traverse( SemanticGraph dependencies )
    {
        return Traverse(dependencies, dependencies.getFirstRoot(), new HashMap<IndexedWord, Integer>());
    }
    public HashMap<IndexedWord,Integer> Traverse( SemanticGraph dependencies , IndexedWord word)
    {
        return Traverse(dependencies, dependencies.getFirstRoot(), new HashMap<IndexedWord, Integer>());
    }
    //

    private HashMap<IndexedWord,Integer> RankOnDepenencyTree( SemanticGraph dependencies , IndexedWord word )
    {
        return Traverse(dependencies, word, new HashMap<IndexedWord, Integer>());
    }

    private void RankOnPos(CoreMap sentence)
    {
        for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
            // this is the text of the token
            String word = token.get(CoreAnnotations.TextAnnotation.class);
            // this is the POS tag of the token
            String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
            //  if(pos.equals("NN") || pos.equals("JJ") || pos.equals("JJS") )
            //       headWords.replace(word , headWords.get(word)+10);

            // this is the NER label of the token
            String ne = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);
            System.out.println(word + " " + pos + " " + ne);
        }
    }

    public void ExecuteAll()
    {

        PrepareText(text);

        for(CoreMap sentence: sentences) {
            // traversing the words in the current sentence
            // a CoreLabel is a CoreMap with additional token-specific methods
            Tree tree = GetParseTree(sentence) ;


            // this is the Stanford dependency graph of the current sentence
            SemanticGraph dependencies = GetDependencyTree(sentence) ;

            // ranking accoarding on the number of edges
            headWords = RankOnDepenencyTree(dependencies, dependencies.getFirstRoot());


            //ranking headwords according on POS
            RankOnPos(sentence);

        }



    }
    public List<SIPair> GetCandidateHeadWords()
    {
        List<SIPair> headWordsList = new LinkedList<SIPair>();

        for(Map.Entry<IndexedWord , Integer> it : headWords.entrySet())
        {
            // System.out.println("F= " + it.getKey() + " _ " + it.getValue());
            headWordsList.add(new SIPair(it.getKey().value(), it.getValue())) ;
        }

        headWordsList.sort(SIPair.SIPairComparator);

        return headWordsList;
    }

    public List<SIPair> GetCandidateHeadWords(HashMap<String,Integer> headWords)
    {
        List<SIPair> headWordsList = new LinkedList<SIPair>();

        for(Map.Entry<String , Integer> it : headWords.entrySet())
        {
            // System.out.println("F= " + it.getKey() + " _ " + it.getValue());
            headWordsList.add(new SIPair(it.getKey(), it.getValue())) ;
        }

        headWordsList.sort(SIPair.SIPairComparator);

        return headWordsList;
    }
    public List<String> GetHeadWords()
    {
        List<SIPair> headWordsList = GetCandidateHeadWords();

        List<String> output = new ArrayList<String>();

        for(int i = headWordsList.size()-1 ; i >= 0 ; --i)
        {
            output.add(headWordsList.get(i).first());
            System.out.println(headWordsList.get(i).first() + " " + headWordsList.get(i).second());
        }

        Map<Integer, CorefChain> graph =
                document.get(CorefCoreAnnotations.CorefChainAnnotation.class);


        return output;

    }

    public List<String> GetHeadWords(List<SIPair> CandidateHeadWords)
    {
        List<SIPair> headWordsList = CandidateHeadWords;

        List<String> output = new ArrayList<String>();

        for(int i = headWordsList.size()-1 ; i >= 0 ; --i)
        {
            output.add(headWordsList.get(i).first());
            System.out.println(headWordsList.get(i).first() + " " + headWordsList.get(i).second());
        }

        Map<Integer, CorefChain> graph =
                document.get(CorefCoreAnnotations.CorefChainAnnotation.class);


        return output;

    }

    public static List<String> ExtractHeadWords(String text)
    {
        SentenceStructure hwd = new SentenceStructure() ;
        hwd.PrepareText(text);
        hwd.ExecuteAll();
        return  hwd.GetHeadWords();
    }

}
