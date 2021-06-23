package org.limbo.utils.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Objects;

/**
 * @author Brozen
 * @since 2021-06-23
 */
public class ExceptionUtils {


    /**
     * 获取异常的堆栈信息，以字符串形式返回。
     * @param throwable 异常
     * @return 异常堆栈
     */
    public static String getStackTrace(Throwable throwable) {
        Objects.requireNonNull(throwable);

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);

        return sw.toString();
    }



}
