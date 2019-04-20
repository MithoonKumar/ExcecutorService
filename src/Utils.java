import Model.LineContent;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Utils {
    public static List<LineContent> parseFile(String filePath) {
        File file = new File(filePath);
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        List<LineContent> lineContents = new ArrayList<>();
        sc.nextLine();
        while (sc.hasNextLine()) {
            String str = sc.nextLine();
            List<String> stringList = processEachLine(str);
            LineContent lineContent = new LineContent();
            lineContent.setTimestamp(new Timestamp(Long.parseLong(stringList.get(1))));
            lineContent.setExceptionType(stringList.get(2));
            lineContents.add(lineContent);
        }
        return lineContents;
    }

    public static List<String> processEachLine(String str) {
        List<String> stringList = new ArrayList<>();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != ' ') {
                stringBuffer.append(str.charAt(i));
            } else {
                if (stringBuffer.length() > 0) {
                    String string = stringBuffer.toString();
                    stringList.add(string);
                }
                stringBuffer.delete(0, stringBuffer.length());
            }
        }
        if (stringBuffer.length() > 0) {
            String string = stringBuffer.toString();
            stringList.add(string);
        }
        return stringList;
    }


    public static String getHours(int val) {
        if (val < 9) {
            return "0" + val;
        } else {
            return String.valueOf(val);
        }
    }

    public static String getMinutesRange(Timestamp timestamp) {
        int hours = timestamp.getHours();
        String hrs = getHours(hours);
        Long timeStampLongValue = timestamp.getTime();
        String string;
        timeStampLongValue = (timeStampLongValue / 1000) * 1000;
        Timestamp timestamp2 = new Timestamp(timeStampLongValue);
        timestamp2.setMinutes(0);
        timestamp2.setSeconds(0);
        Long diff = timestamp.getTime() - timestamp2.getTime();
        Long fifteenMinutesInMilliseconds = 15 * 60 * 1000L;
        if (diff < fifteenMinutesInMilliseconds) {
            string = hrs + ":" + "00-" + hrs + ":" + "15";
        } else if (diff >= fifteenMinutesInMilliseconds && diff < 2 * fifteenMinutesInMilliseconds) {
            string = hrs + ":" + "15-" + hrs + ":" + "30";
        } else if (diff >= 2 * fifteenMinutesInMilliseconds && diff < 3 * fifteenMinutesInMilliseconds) {
            string = hrs + ":" + "30-" + hrs + ":" + "45";
        } else {
            string = hrs + ":" + "45-" + getHours(Integer.parseInt(hrs) + 1) + ":" + "00";
        }
        return string;
    }
}
