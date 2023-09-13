package com.lt.win.function;

import io.swagger.models.auth.In;
import org.junit.jupiter.api.Test;

import javax.validation.constraints.NotNull;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author: wells
 * @date: 2020/6/6
 * @description:
 */

public class ConsumerSelfTest {
    @Test
    void testConsumer() {
        try {
            consumer(x -> {
                        assertNotNull(1, "");
                        System.out.println(x);
                    }
            );
            Consumer<Integer> consumer1 = x -> System.out.println("x=" + x);
            consumer1.andThen(x -> System.out.println("....."));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testFunction() {
        try {
            FunctionInterface functionInterface = (x, y, z) -> x + y + z;
            // var c = functionInterface.add(5, 8);
            assertNotNull(1, "");
            var c = add(functionInterface);
            //System.out.println("c" + c);
            Supplier supplier = () -> 5;
            supplier(supplier);
            Predicate<Integer> predicate = i -> i > 5;
            Function<Integer, Integer> function = i -> i + 3;
            function(function);
            predicate(predicate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 消费
     *
     * @param consumer
     */
    void consumer(Consumer<Integer> consumer) {
        consumer.accept(5);
    }

    /**
     * 自定义函数
     *
     * @param f
     * @return
     */
    int add(FunctionInterface f) {
        return f.add(6, 8, 9);
    }

    /**
     * 提供函数
     *
     * @param supplier
     * @return
     */
    int supplier(Supplier<Integer>... supplier) {
        System.out.println("s=" + supplier[0].get());
        return supplier[0].get();
    }

    /**
     * 断言
     *
     * @param predicate
     * @return
     */
    boolean predicate(Predicate<Integer> predicate) {
        var b = predicate.test(9);
        System.out.println("b==" + b);
        return b;
    }

    /**
     * 输出函数
     *
     * @param function
     * @return
     */
    Integer function(Function<Integer, Integer> function) {
        var f = function.apply(8);
        var identity = Function.identity();
        System.out.println("identity=" + identity);
        System.out.println("f=" + f);
        return f;
    }


}
