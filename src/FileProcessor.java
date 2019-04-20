import Model.LineContent;

import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class FileProcessor implements Runnable {
    private TreeMap<String, HashMap<String, Integer>> resultMap;
    private String filePath;

    public FileProcessor(TreeMap<String, HashMap<String, Integer>> resultFileMap,
                         String filePath) {
        this.resultMap = resultFileMap;
        this.filePath = filePath;
    }

    @Override
    public void run() {
        List<LineContent> lineContents = Utils.parseFile(filePath);
        for (int i = 0; i < lineContents.size(); i++) {
            String timeRange = Utils.getMinutesRange(lineContents.get(i).getTimestamp());
            String exceptionType = lineContents.get(i).getExceptionType();
            synchronized (resultMap) {
                if (resultMap.containsKey(timeRange)) {
                    if (resultMap.get(timeRange).containsKey(exceptionType)) {
                        int val = resultMap.get(timeRange).get(exceptionType) + 1;
                        resultMap.get(timeRange).put(exceptionType, val);
                    } else {
                        resultMap.get(timeRange).put(exceptionType, 1);
                    }
                } else {
                    HashMap<String, Integer> exceptionHash = new HashMap<>();
                    exceptionHash.put(exceptionType, 1);
                    resultMap.put(timeRange, exceptionHash);
                }
            }
        }

    }
}
