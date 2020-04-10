// ======================================
// Project Name:core
// Package Name:cn.meddb.core.exception
// File Name:BaseBizException.java
// Create Date:2017年09月21日  14:40
// ======================================
package cn.meddb.core.exception;

import java.text.MessageFormat;
import java.util.Arrays;

/**
 * 业务逻辑异常
 *
 * @author 李旭光
 * @version 2017年09月21日  14:40
 */
public class BaseBizException extends Exception {

    public BaseBizException() {
        super();
    }

    public BaseBizException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseBizException(String message) {
        super(message);
    }

    public BaseBizException(Throwable cause) {
        super(cause);
    }

    /**
     * {@link MessageFormat} 所有参数转为字符串处理
     *
     * @param msgFormatter 例如:XXX{0}XXX{1}XXX{2}XXX,
     */
    public BaseBizException(String msgFormatter, Object... params) {
        super(MessageFormat.format(msgFormatter, Arrays.stream(params).map(String::valueOf).toArray()));
    }

}