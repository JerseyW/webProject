package cn.book_manager.common;

/**
 * @author: Jersey
 * @create: 2022-02-22 17:03
 * @version: V1.0
 * @slogan: 业精于勤, 荒于嬉;行成于思,毁于随。
 * @caution: 所有学不会都是给懒找借口！
 * @description:
 */
public class DbSourceHolder {
    /**
       避免多线程操作数据源时互相干扰
     */
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    //设置当前数据源
    public static void setDbType(String dbType) {
        contextHolder.set(dbType);
    }

    // 获取当前数据源
    public static String getDbType() {
        return contextHolder.get();
    }

    // 清空上下文数据源
    public static void clearDbType() {
        contextHolder.remove();
    }
}
