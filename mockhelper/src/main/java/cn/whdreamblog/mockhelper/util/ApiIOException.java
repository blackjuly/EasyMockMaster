package cn.whdreamblog.mockhelper.util;

import java.io.IOException;

/**
 * <pre>
 *     author : blackjuly
 *     e-mail : blackjuly@outlook.com
 *     time   : 2017年10月16日
 *     desc   : 自定义业务异常类
 *     version: v1.0
 * </pre>
 */

public class ApiIOException extends IOException {

  public ApiIOException(String errorMessage) {
    super(errorMessage);
  }
}
