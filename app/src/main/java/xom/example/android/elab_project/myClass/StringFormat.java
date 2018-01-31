package xom.example.android.elab_project.myClass;

/**
 * Created by Mawinda on 31-Jan-18.
 */

public class StringFormat {
    /**
     * Convert strings to sentence case
     *
     * @param string to be edited
     * @return sentence case string
     */
    public static String sentCase(String string) {
        if (string == null || string.length() == 0) {
            return string;
        }
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }
}
