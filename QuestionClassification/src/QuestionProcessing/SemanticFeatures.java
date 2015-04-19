package QuestionProcessing;

import QuestionProcessing.Enums.GRAM_TYPE;
import QuestionProcessing.Enums.SHAPE;
import QuestionProcessing.Structures.TokensNode;
import net.didion.jwnl.data.Synset;

import java.util.Vector;

/**
 * Created by Mahmoud on 23-Feb-15.
 */
public abstract class SemanticFeatures {
    protected Vector<String> headWords;
    protected Vector<TokensNode> tokensNodes;


    public SemanticFeatures(Vector<String> headWords, Vector<TokensNode> tokensNodes) {
        this.headWords = headWords;
        this.tokensNodes = tokensNodes;
    }

    public abstract Synset getBestSense();
    public abstract Vector<String> getHypernyms(Synset sense);
    public abstract GRAM_TYPE getBestGram();
    public abstract SHAPE getQuestionShape();

}
