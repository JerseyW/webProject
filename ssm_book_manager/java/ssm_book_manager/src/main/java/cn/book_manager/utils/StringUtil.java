package cn.book_manager.utils;

import eu.bitwalker.useragentutils.UserAgent;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: Jersey
 * @create: 2022-02-12 14:26
 * @version: V1.0
 * @slogan: 业精于勤, 荒于嬉;行成于思,毁于随。
 * @caution: 所有学不会都是给懒找借口！
 * @description:
 */
public class StringUtil {

    public  static  String getBrowserSystemInfo(HttpServletRequest request){

        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("user-agent"));

        return userAgent.getOperatingSystem() + "-" + userAgent.getBrowser() + "(" + userAgent.getBrowserVersion() + ")";

    }
}
