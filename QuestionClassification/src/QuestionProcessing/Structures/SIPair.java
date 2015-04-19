package QuestionProcessing.Structures;

import java.util.Comparator;
import java.lang.Comparable;
/**
 * Created by ma7mod on 3/27/15.
 */

public class SIPair implements Comparable<SIPair> {
    Pair<String, Integer> pair;

    public SIPair(String s, int i) {
        pair = new Pair<String, Integer>(s, i);
    }

    public String first() {
        return pair.first;
    }

    public Integer second() {
        return pair.second;
    }

    @Override
    public int compareTo(SIPair o1) {
      return second() - o1.second() ;
    }

    // to sort
    public static Comparator<SIPair> SIPairComparator = new Comparator<SIPair>() {

        public int compare(SIPair o1, SIPair o2) {

            return o1.compareTo(o2);
        }
    };

}