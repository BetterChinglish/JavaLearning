package com.betterchinglish.demo20_Exception.exception;

/**
 * 自定义年龄超出范围异常
 * -------------------------------------------------------------
 * 【自定义异常规范】：
 * 1. 类名：以 Exception 结尾，见名知意。
 * 2. 继承基类：
 *    - 继承 RuntimeException：表示自定义运行时异常（推荐，无需强制在方法上 throws，调用更灵活）。
 *    - 继承 Exception：表示自定义编译时异常（调用方必须显式 try-catch 或 throws）。
 * 3. 构造器：通常提供空参构造器与带 String message 的有参构造器，调用 super(message) 即可。
 */
public class AgeOutOfBoundsException extends RuntimeException {

    public AgeOutOfBoundsException() {
        super();
    }

    public AgeOutOfBoundsException(String message) {
        super(message);
    }

    public AgeOutOfBoundsException(String message, Throwable cause) {
        super(message, cause);
    }
}
