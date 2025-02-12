package learn.qzy.rpc.springboot.starter.annotation;

import learn.qzy.rpc.springboot.starter.bootstrap.RpcConsumerBootstrap;
import learn.qzy.rpc.springboot.starter.bootstrap.RpcInitBootstrap;
import learn.qzy.rpc.springboot.starter.bootstrap.RpcProviderBootstrap;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author qzy
 * @time 2025年1月25日 15:32 星期六
 * @title 启用 Rpc 注解
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({RpcInitBootstrap.class, RpcProviderBootstrap.class, RpcConsumerBootstrap.class})
public @interface EnableRpc {
    /**
     * 需要启动 server
     */
    boolean needServer() default true;
}
