import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader bi = new BufferedReader(new FileReader("data/data.txt"));
        String line = null;
        JSONArray routs = new JSONArray();
        JSONArray normalSpeeds = new JSONArray();
        JSONArray abnormalSpeeds = new JSONArray();
        JSONArray ids = new JSONArray();
        JSONArray streetNames = new JSONArray();
        JSONObject map = new JSONObject();
        class Tool{
            void putJSON(Object[] out){
                JSONArray rout = new JSONArray();
                JSONArray pos = new JSONArray();
                pos.add(Double.parseDouble((String) out[2]));
                pos.add(Double.parseDouble((String) out[3]));
                rout.add(pos);
                pos = new JSONArray();
                pos.add(Double.parseDouble((String) out[4]));
                pos.add(Double.parseDouble((String) out[5]));
                rout.add(pos);
                routs.add(rout);

                ids.add(out[0]);
                streetNames.add(out[1]);
                Double d = Double.parseDouble((String) out[6]);
                normalSpeeds.add(d.intValue());
                d = Double.parseDouble((String) out[7]);
                abnormalSpeeds.add(d.intValue());
            }
        }
        boolean isTwo = false;
        StringBuffer twoLine = new StringBuffer();
        int k = 0;
        while ((line=bi.readLine())!=null){
            if(line.equals("")) continue;
            if(line.equals("{$twoline}")){ isTwo = true; continue;}
            if(isTwo){
                twoLine.append(line);
                if(k==1) {
                    twoLine.append(line);
                    Object[] out = makeObjects(twoLine.toString());
                    System.out.println(Arrays.toString(out));
                    new Tool().putJSON(out);
                    twoLine.delete(0,twoLine.length());
                }
                k = 1-k;
                continue;
            }
            Object[] out = makeObjects(line);
            System.out.println(Arrays.toString(out));
            new Tool().putJSON(out);
        }
        bi.close();
        map.put("routes", routs);
        map.put("normalSpeeds",normalSpeeds);
        map.put("abnormalSpeeds",abnormalSpeeds);
        map.put("streetIds",ids);
        map.put("streetNames", streetNames);
        PrintWriter pw = new PrintWriter("data/out.json");
        pw.print(map);
        pw.close();
    }
    static Object[] makeObjects(String line){
        System.out.println(line);
        Object[] out = new Object[8];
        String[] frag = line.split(";");
        for (int i = 0,j=0; i < frag.length; i++,j=2*i) {
            String[] ss = frag[i].split(",");
            out[j] = ss[0];
            out[j+1] = ss.length<2?"":ss[1];
        }
        return out;
    }
}
