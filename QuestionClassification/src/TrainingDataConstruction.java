import com.sun.corba.se.impl.encoding.CodeSetConversion;
import com.sun.rowset.internal.Row;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

/**
 * Created by Mahmoud on 02-Mar-15.
 */
public class TrainingDataConstruction {
    public HashMap<String, Vector<RowData>> map;
    private String filepath;

    public TrainingDataConstruction(String filePath) {
        this.filepath = filePath;
        this.map = new HashMap<String, Vector<RowData>>();
    }

    public void parse() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(this.filepath));

            int labelIndex = 0;
            int questionTypeIndex = 1;
            int categoryIndex = 2;
            int headWordIndex = 3;
            int hypernymsIndex = 4;

            String line = br.readLine();
            int count = 1;

            while(line != null) {
                String[] tokens = line.split(" ");
                String label = tokens[labelIndex];
                RowData rowData = new RowData();
                rowData.questionType =  tokens[questionTypeIndex];
                rowData.category = tokens[categoryIndex];
                rowData.headWord = tokens[headWordIndex];
                rowData.index = count++;

                if (!rowData.headWord.equals("#")) {
                    for (int i = hypernymsIndex; i < tokens.length; i++) {
                        rowData.hypernyms.add(tokens[i]);
                    }
                }

                if (this.map.containsKey(label)){
                    Vector<RowData> temp = this.map.get(label);
                    temp.add(rowData);
                    this.map.put(label, temp);
                }

                else{
                    Vector<RowData> temp = new Vector<RowData>();
                    temp.add(rowData);
                    this.map.put(label, temp);
                }

                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
