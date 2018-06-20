package ve.com.abicelis.cryptomaster.util;

/**
 * Created by abicelis on 20/6/2018.
 */
public class IntUtil {

    public static int[] ToIntArray(String[] input) {
        int[] intArray = new int[input.length];
        for (int i = 0; i < input.length; i++)
            intArray[i] = Integer.parseInt(input[i]);
        return intArray;
    }
}
