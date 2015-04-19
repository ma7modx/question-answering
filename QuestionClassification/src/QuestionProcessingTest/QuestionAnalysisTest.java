package QuestionProcessingTest;

import QuestionProcessing.QuestionAnalysis;
import QuestionProcessing.Structures.TokensNode;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.trees.Tree;
import org.junit.Test;

import java.util.List;
import java.util.Vector;

import static org.junit.Assert.*;

public class QuestionAnalysisTest {

    QuestionAnalysis qa = new QuestionAnalysis("Who killed Gandhi");

    //@Test
    public void testParser() throws Exception {

        Tree tree = qa.parser();
      //  tree.pennPrint();
    }

    //@Test
    public void testTokenizer() throws Exception {
        Vector<TokensNode> tokens = qa.tokenizer();

        for (TokensNode toke : tokens){
            System.out.println(toke.word + " " + toke.lemma + " " + toke.tag + " " + toke.namedEntity);
        }
    }

}