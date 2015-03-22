package com.gmail.mooman219;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Joseph Cumbo (mooman219)
 */
public class Dictionary {

    private final List<String> words = new ArrayList<>();

    public Dictionary(String filename) throws FileNotFoundException, IOException {
        RandomAccessFile file = new RandomAccessFile(filename, "r");
        FileChannel chan = file.getChannel();
        ByteBuffer buf = ByteBuffer.allocate(1024);
        StringBuilder line = new StringBuilder();
        while (chan.read(buf) > 0) {
            buf.flip();
            for (int i = 0; i < buf.limit(); i++) {
                char c = (char) buf.get();
                if (c == '\n' || c == '\r') {
                    if (line.length() > 0) {
                        words.add(line.toString());
                        line.delete(0, line.length());
                    }
                } else {
                    line.append(c);
                }
            }
            buf.clear();
        }
        if (line.length() > 0) {
            words.add(line.toString());
        }
        chan.close();
        file.close();
        Collections.sort(words);
    }

    public Dictionary(List<String> words) {
        this.words.addAll(words);
        Collections.sort(this.words);
    }

    public String getTopWord() {
        return words.get(0);
    }

    public List<String> getList(final String word) {
        Collections.sort(words, new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                int wa = getLevenshteinDistance(word, a);
                int wb = getLevenshteinDistance(word, b);
                wa -= getRunningDistance(word, a);
                wb -= getRunningDistance(word, b);
                return wa - wb;
            }
        });
        return Collections.unmodifiableList(words);
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        for (String word : words) {
            ret.append(word).append(',');
        }
        if (ret.length() > 0) {
            ret.deleteCharAt(ret.length() - 1);
        }
        return ret.toString();
    }

    /**
     * Gets the number of matching characters between each string. If there
     * isn't a match as the method iterates through the letters, then the
     * current value is returned.
     *
     * @param s Source string
     * @param t Target string
     * @return The number of matching characters from the beginning of both
     * strings until the first mismatch.
     */
    public static int getRunningDistance(String s, String t) {
        int m = 0;
        for (int i = 0; i < s.length() && i < t.length(); i++) {
            if (s.charAt(i) == t.charAt(i)) {
                m++;
            } else {
                break;
            }
        }
        return m;
    }

    /**
     * Gets the number of edits that have to be made to turn String 's' into
     * String 't'.
     *
     * @param s Source string
     * @param t Target string
     * @return The number of edits that have to be made to turn String 's' into
     * String 't'.
     */
    public static int getLevenshteinDistance(String s, String t) {
        int ls = s.length(); // Length of s
        int lt = t.length(); // Length of t

        // If either string is empty, return the other.
        if (ls == 0) {
            return lt;
        } else if (lt == 0) {
            return ls;
        }

        int p[] = new int[ls + 1];
        int d[] = new int[ls + 1];
        int temp[]; // Used for swapping p and d
        int cost;

        for (int i = 0; i <= ls; i++) {
            p[i] = i;
        }
        for (int j = 1; j <= lt; j++) {
            d[0] = j;
            for (int i = 1; i <= ls; i++) {
                cost = s.charAt(i - 1) == t.charAt(j - 1) ? 0 : 1;
                d[i] = Math.min(Math.min(d[i - 1] + 1, p[i] + 1), p[i - 1] + cost);
            }
            temp = p;
            p = d;
            d = temp;
        }
        return p[ls];
    }
}
