package com.lt.win.backend.configuration;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 全局事务管理器
 * 解决问题:was not registered for synchronization because synchronization is not active
 * </p>
 *
 * @author andy
 * @since 2020/10/6
 */
@Configuration
@EnableTransactionManagement
public class TransactionConfig {

    /**
     * 配置全局事务的切点为com.lt.win包及子包下的所有方法  AOP切面表达式 可参考（https://blog.csdn.net/ycf921244819/article/details/106599489）
     */
    private static final String AOP_POINTCUT_EXPRESSION = "execution ( * com.lt.win.dao.generator..*.*(..))"
            + " || execution ( * com.lt.win.backend..*.*(..)) ";

    /**
     * 注入事务管理器
     */
    @Resource
    private TransactionManager transactionManager;


    /**
     * 配置事务拦截器
     */
    @Bean
    public TransactionInterceptor txAdvice() {

        RuleBasedTransactionAttribute txAttrRequired = new RuleBasedTransactionAttribute();
        txAttrRequired.setName("REQUIRED事务");
        // 设置事务传播机制，默认是PROPAGATION_REQUIRED：如果当前存在事务，则加入该事务；如果当前没有事务，则创建一个新的事务
        txAttrRequired.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        // 设置异常回滚为Exception  默认是RuntimeException
        List<RollbackRuleAttribute> rollbackRuleAttributes = new ArrayList<>();
        rollbackRuleAttributes.add(new RollbackRuleAttribute(Exception.class));
        txAttrRequired.setRollbackRules(rollbackRuleAttributes);

        RuleBasedTransactionAttribute txAttrRequiredReadOnly = new RuleBasedTransactionAttribute();
        txAttrRequiredReadOnly.setName("SUPPORTS事务");
        // 设置事务传播机制，PROPAGATION_SUPPORTS：如果当前存在事务，则加入该事务；如果当前没有事务，则以非事务的方式继续运行
        txAttrRequiredReadOnly.setPropagationBehavior(TransactionDefinition.PROPAGATION_SUPPORTS);
        // 设置异常回滚为Exception  默认是RuntimeException
        txAttrRequiredReadOnly.setRollbackRules(rollbackRuleAttributes);
        txAttrRequiredReadOnly.setReadOnly(true);

        /* 事务管理规则，声明具备事务管理的方法名 */
        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
        // 非查询方法 -> PROPAGATION_REQUIRED:如果当前存在事务，则加入该事务；如果当前没有事务，则创建一个新的事务
        source.addTransactionalMethod("save*", txAttrRequired);
        source.addTransactionalMethod("insert*", txAttrRequired);
        source.addTransactionalMethod("add*", txAttrRequired);
        source.addTransactionalMethod("update*", txAttrRequired);
        source.addTransactionalMethod("exec*", txAttrRequired);
        source.addTransactionalMethod("del*", txAttrRequired);
        source.addTransactionalMethod("remove*", txAttrRequired);


        // 查询方法 -> PROPAGATION_SUPPORTS:如果当前存在事务，则加入该事务；如果当前没有事务，则以非事务的方式继续运行
        source.addTransactionalMethod("get*", txAttrRequiredReadOnly);
        source.addTransactionalMethod("select*", txAttrRequiredReadOnly);
        source.addTransactionalMethod("query*", txAttrRequiredReadOnly);
        source.addTransactionalMethod("list*", txAttrRequiredReadOnly);
        source.addTransactionalMethod("*List", txAttrRequiredReadOnly);
        source.addTransactionalMethod("count*", txAttrRequiredReadOnly);
        source.addTransactionalMethod("*Count", txAttrRequiredReadOnly);
        return new TransactionInterceptor(transactionManager, source);
    }

    /**
     * 设置切面
     */
    @Bean
    public Advisor txAdviceAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
        return new DefaultPointcutAdvisor(pointcut, txAdvice());
    }
}