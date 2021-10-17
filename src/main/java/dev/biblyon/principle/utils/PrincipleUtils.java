package dev.biblyon.principle.utils;

import java.util.logging.Logger;

public class PrincipleUtils {

    public static Logger getLogger() {
        return Logger.getLogger(getClassName());
    }

    /**
     * https://stackoverflow.com/questions/11306811/how-to-get-the-caller-class-in-java
     *
     * @return
     */
    public static String getClassName() {
        var stacks = Thread.currentThread().getStackTrace();
        for (int q = 1; q < stacks.length; q++) {
            var stackElement = stacks[q];
            if (!stackElement.getClassName().equals(PrincipleUtils.class.getName())
                    && stackElement.getClassName().indexOf("java.lang.Thread")!=0) {
                return stackElement.getClassName();
            }
        }
        return null;
    }
}
