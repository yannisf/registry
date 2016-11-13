package fraglab.web;

import org.apache.commons.lang3.CharEncoding;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;

public final class Utils {

    private Utils() {
    }

    public static final String reEncodeString(String input) {
        String output = StringUtils.EMPTY;
        try {
            output = new String(input.getBytes(CharEncoding.ISO_8859_1), CharEncoding.UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return output;
    }

}
