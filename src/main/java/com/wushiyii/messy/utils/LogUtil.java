package com.wushiyii.messy.utils;

import java.text.MessageFormat;
import java.util.UUID;

import org.slf4j.Logger;

/**
 * 日志处理通用类
 */
public class LogUtil {


    private static final String LOG_START = "###### ";
    private static final String LOG_END = " ######";

    private static ThreadLocal<String> identity = ThreadLocal.withInitial(() -> UUID.randomUUID().toString().replace("-", ""));

    private static String logPrefix() {
        StringBuilder sb = new StringBuilder();
        sb.append("#UUID[");
        sb.append(identity.get());
        sb.append("]#");
        return "";
    }

    /**
     * INFO日志
     *
     * @param log    log
     * @param format 日志格式，可以不包含参数
     * @param args   日志格式中包含的参数对应值
     */
    public static void info(Logger log, String format, Object... args) {
        log.info(logPrefix() + LOG_START + MessageFormat.format(format, args) + LOG_END);
    }

    /**
     * INFO日志
     *
     * @param log     log
     * @param message 日志内容
     */
    public static void info(Logger log, String message) {
        log.info(logPrefix() + LOG_START + message + LOG_END);
    }

    /**
     * DEBUG日志
     *
     * @param log    log
     * @param format 日志格式，可以不包含参数
     * @param args   日志格式中包含的参数对应值
     */
    public static void debug(Logger log, String format, Object... args) {
        log.debug(logPrefix() + LOG_START + MessageFormat.format(format, args) + LOG_END);
    }

    /**
     * DEBUG日志
     *
     * @param log     log
     * @param message 日志内容
     */
    public static void debug(Logger log, String message) {
        log.debug(logPrefix() + LOG_START + message + LOG_END);
    }

    /**
     * @param log    log
     * @param format 日志格式，可以不包含参数
     * @param args   日志格式中包含的参数对应值
     */
    public static void warn(Logger log, String format, Object... args) {
        log.warn(logPrefix() + LOG_START + MessageFormat.format(format, args) + LOG_END);
    }

    /**
     * WARN日志
     *
     * @param log     log
     * @param message 日志内容
     */
    public static void warn(Logger log, String message) {
        log.warn(logPrefix() + LOG_START + message + LOG_END);
    }

    /**
     * ERROR日志
     *
     * @param log    log
     * @param format 日志格式，可以不包含参数
     * @param args   日志格式中包含的参数对应值
     */
    public static void error(Logger log, String format, Object... args) {
        log.error(logPrefix() + LOG_START + MessageFormat.format(format, args) + LOG_END);
    }


    /**
     * ERROR日志
     *
     * @param log     log
     * @param message 日志内容
     */
    public static void error(Logger log, String message) {
        log.error(logPrefix() + LOG_START + message + LOG_END);
    }

    /**
     * ERROR日志
     *
     * @param log    log
     * @param cause  抛出的异常
     * @param format 日志格式，可以不包含参数
     * @param args   日志格式中包含的参数对应值
     */
    public static void error(Logger log, Throwable cause, String format, Object... args) {
        log.error(logPrefix() + LOG_START + MessageFormat.format(format, args) + LOG_END, cause);
    }


    /**
     * ERROR日志
     *
     * @param log     log
     * @param cause   抛出异常
     * @param message 日志内容
     */
    public static void error(Logger log, Throwable cause, String message) {
        log.error(logPrefix() + LOG_START + message + LOG_END, cause);
    }

}
