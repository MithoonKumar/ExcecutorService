import java.util.HashMap;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    TreeMap<String, HashMap<String, Integer>> resultMap;

    public static void main(String[] args) {
        Main main = new Main();
        main.resultMap = new TreeMap<>();
        int numberOfThreads = 5;
        int totalNumberOfFiles = 8;
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
        for (int i = 1; i <= totalNumberOfFiles; i++) {
            FileProcessor fileProcessor = new FileProcessor( main.resultMap, "/Users/mithoon.k/Desktop/Healofy/src/Files/" + i + ".txt");
            executor.execute(fileProcessor);
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        main.resultMap.forEach((key, val) -> {
            val.forEach((exceptionType, count)->{
                System.out.println(key + " " + exceptionType + " " + count);
            });
        });
    }
}
