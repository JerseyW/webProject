package cn.book_manager.aspect;


import cn.book_manager.common.DataSource;
import cn.book_manager.common.DbSourceHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Aspect
public class DataSourceAspect {
    private static final Logger logger = LoggerFactory.getLogger(DataSourceAspect.class);

    // 指定切入点表单式
    @Pointcut("execution(* cn.book_manager.mapper.*.*(..))")
    public void pointCut_() {
    }

    // 前置通知 : 在执行目标方法之前执行
    @Before("pointCut_()")
    public void begin(JoinPoint point) {
        System.err.println("正在设置数据源。。。");
        //获取由@DataSource指定的数据源标识，设置到线程存储中以便切换数据源
        Class<?> target = point.getTarget().getClass();
        MethodSignature signature = (MethodSignature) point.getSignature();
        // 默认使用目标类型的注解，如果没有则使用其实现接口的注解
        for (Class<?> clazz : target.getInterfaces()) {
            resolveDataSource(clazz, signature.getMethod());
        }
        resolveDataSource(target, signature.getMethod());
    }


    // 后置/最终通知：在执行目标方法之后执行 【无论是否出现异常最终都会执行】
    @After("pointCut_()")
    public void after(JoinPoint point) {
        DbSourceHolder.clearDbType();
        System.out.println("切换默认数据源成功");
    }

    /**
     * 提取目标对象方法注解和类型注解中的数据源标识
     */
    private void resolveDataSource(Class<?> clazz, Method method) {
        try {
            Class<?>[] types = method.getParameterTypes();
            // 默认使用类型注解
            if (clazz.isAnnotationPresent(DataSource.class)) {
                DataSource source = clazz.getAnnotation(DataSource.class);
                DbSourceHolder.setDbType(source.value());
                logger.debug("当前数据源1："+source.value());
            }
            // 方法注解可以覆盖类型注解
            Method m = clazz.getMethod(method.getName(), types);
            if (m.isAnnotationPresent(DataSource.class)) {
                DataSource source = m.getAnnotation(DataSource.class);
                DbSourceHolder.setDbType(source.value());
                logger.debug("当前数据源2："+source.value());
            }
        }catch (Exception e) {
            logger.error(clazz + ":" + e.getMessage());
        }
    }


}
