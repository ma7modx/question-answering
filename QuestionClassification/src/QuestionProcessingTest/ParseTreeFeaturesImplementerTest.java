package QuestionProcessingTest;

import QuestionProcessing.Enums.QUESTION_TYPE;
import QuestionProcessing.QuestionAnalysis;
import QuestionProcessing.SyntacticFeatures;
import QuestionProcessing.SyntacticFeaturesImplementer;
import edu.stanford.nlp.trees.Tree;
import org.junit.Test;

import java.util.Vector;

import static org.junit.Assert.*;

public class ParseTreeFeaturesImplementerTest {
    String question = "what is the sales tax in Minnesota";
    //@Test
    public void testGetQuestionType() throws Exception {

        QuestionAnalysis qa = new QuestionAnalysis(question);
        Tree tree = qa.parser();

        SyntacticFeatures ptf = new SyntacticFeaturesImplementer(tree,question);

        QUESTION_TYPE qt = ptf.getQuestionType();
        System.out.println(qt);
    }

    //@Test
    public void testGetHeadWord() throws Exception {
        QuestionAnalysis qa = new QuestionAnalysis(question);
        Tree tree = qa.parser();

        SyntacticFeatures ptf = new SyntacticFeaturesImplementer(tree,question);

        QUESTION_TYPE qt = ptf.getQuestionType();
        Vector<String> hw = ptf.getHeadWord();
        for (String s : hw)
        {
            System.out.println(s);
        }
    }
}