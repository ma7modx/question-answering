package QuestionProcessingTest;

import QuestionProcessing.QuestionFormat;
import org.junit.Test;

import static org.junit.Assert.*;

public class QuestionFormatTest {

    //@Test
    public void testFormat() throws Exception {
        QuestionFormat q = QuestionFormat.getInstance();

        String s = q.format("Who is Playing with ahani.fcis@gmail.com mail and his \"facebook\" ?");

        System.out.print(s);
    }
}