package ve.com.abicelis.cryptomaster.util;

/**
 * Created by abicelis on 20/6/2018.
 */
public class LongUtil {

    public static long[] ToLongArray(String[] input) {
        long[] arr = new long[input.length];
        for (int i = 0; i < input.length; i++)
            arr[i] = Long.parseLong(input[i]);
        return arr;
    }
}
