// ======================================
// Project Name:core
// Package Name:cn.meddb.core.exception
// File Name:ParameterIllegalException.java
// Create Date:2017年09月21日  14:41
// ======================================
package cn.meddb.core.exception;

/**
 * 参数错误
 *
 * @author 李旭光
 * @version 2017年09月21日  14:41
 */
public class ParameterIllegalException extends BaseBizException {

    public ParameterIllegalException() {
        super();
    }

    public ParameterIllegalException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParameterIllegalException(String message) {
        super(message);
    }

    public ParameterIllegalException(Throwable cause) {
        super(cause);
    }

}
