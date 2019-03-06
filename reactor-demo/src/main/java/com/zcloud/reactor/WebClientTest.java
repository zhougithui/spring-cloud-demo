package com.zcloud.reactor;

import org.junit.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.Arrays;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class WebClientTest {

    public static void main(String[] args) throws InterruptedException {
        Integer[] integers = new Integer[]{1,2,3,4,5,6};

        //订阅并指定对数据进行处理的方式
        Flux.fromArray(integers).subscribe(System.out::print);
        //订阅并触发数据流，但是不做任何事情
        Flux.fromArray(integers).subscribe();

        //定义对正常数据和错误信号的处理
        try {
            Flux.fromIterable(Arrays.asList(integers)).subscribe(j -> System.out.println(j/0), i -> {
                throw new NullPointerException(i + "发生了未知异常");
            });
        }catch (Exception e){
            //System.out.println(e.getCause().getMessage());
        }

        Flux.fromStream(Arrays.stream(integers)).subscribe(j -> System.out.println(j), i -> {
            throw new NullPointerException(i + "发生了未知异常");
        }, ()->System.out.println("数据处理完毕。。。。")
        , (obj) -> System.out.println(obj.getClass().getName()));



        //只有完成信号的
        Flux.just();
        Flux.empty();

        Mono.empty();
        Mono.justOrEmpty(Optional.empty());

        //只有错误信号
        Flux.error(new NullPointerException("abc"));
        Mono.error(new NullPointerException("abc"));
    }

    private Flux<Integer> generateFluxFrom1To6() {
        return Flux.just(1, 2, 3, 4, 5, 6);
    }
    private Mono<Integer> generateMonoWithError() {
        return Mono.error(new Exception("some error"));
    }

    @Test
    public void testViaStepVerifier() {
        /*StepVerifier.create(generateFluxFrom1To6())
                .expectNext(1, 2, 3, 4, 5, 6)
                .expectComplete()
                .verify();
        StepVerifier.create(generateMonoWithError())
                .expectErrorMessage("some error")
                .verify();*/

        StepVerifier.create(generateFluxFrom1To6())
                .expectNext(1,2,3,4,5,6)
                .expectComplete()
                .verify();
        StepVerifier.create(generateMonoWithError())
                .expectErrorMessage("some error?")
                .verify();
    }

    @Test
    public void test1(){
        Flux.just(1,2,3,4,5)
                .subscribe(
                        System.out::println,
                        System.err::println,
                        () -> System.out.println("completed!")
                );

        Mono.error(new Exception("some error"))
                .subscribe(
                        System.out::println,
                        System.err::println,
                        () -> System.out.println("completed!")
                );
    }

    @Test
    public void test2(){
        StepVerifier.create(Flux.range(1,5).map((x) -> x * x))
                .expectNext(1,4,9,16,25)
                .expectComplete()
                .verify();
    }

    @Test
    public void test3(){
        StepVerifier.create(
                Flux.just("a", "efgxh")
                    .flatMap((s) -> Flux.fromArray(s.split("\\s*")))
                    .delayElements(Duration.ofMillis(100))
                    .doOnNext(System.out::println)
        ).expectNextCount(6)
                .expectComplete().verify();
    }


    @Test
    public void test4(){
        StepVerifier.create(Flux.range(1,3).filter(integer -> integer / 2 == 1).doOnNext(System.out::println))
                .expectNextCount(2)
                .expectComplete()
                .verify();
    }


    @Test
    public void test5() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        String desc = "Zip two sources together, that is to say wait for all the sources to emit one element and combine these elements once into a Tuple2.";
        Flux<String> flux = Flux.fromArray(desc.split("\\s+"));
        Flux.zip(flux, Flux.interval(Duration.ofMillis(100))).subscribe(t -> {
            System.out.println(t.getT1() + "-" + t.getT2());
        }, null , countDownLatch::countDown);
        countDownLatch.await(10, TimeUnit.SECONDS);
    }

    @Test
    public void test6() throws InterruptedException {
        long start = System.currentTimeMillis();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Mono.fromCallable(() -> {
            TimeUnit.SECONDS.sleep(2);
            return "2 seconds";
        })
        .subscribeOn(Schedulers.elastic())
        .zipWith(Mono.delay(Duration.ofSeconds(3)))
        .zipWith(Mono.delay(Duration.ofSeconds(3)))
        .subscribe((t) -> {
            System.out.println(t.getT1() + "=" + t.getT2());
        }, (e)->{
            e.printStackTrace();
        }, () -> {
            countDownLatch.countDown();
            System.out.println("time consumes:" + (System.currentTimeMillis() - start));
        });

        countDownLatch.await(5, TimeUnit.SECONDS);
    }

    @Test
    public void test7(){
        //增加Flux监听，如果抛出异常则打印堆栈
        Hooks.onOperatorDebug();
        Flux.range(1, 6)
                .map(i -> 10/(i-3))
                .onErrorReturn(0)   // 1
                .map(i -> i*i)
                .subscribe(System.out::println, System.err::println);
    }

    @Test
    public void test8(){
        Flux.range(1, 6)
                .map(i -> 10/(i-3))
                .onErrorResume(e -> Mono.just(new Random().nextInt(6))) // 提供新的数据流
                .map(i -> i*i)
                .subscribe(System.out::println, System.err::println);

    }

    @Test
    public void testBackpressure() {
        Flux.range(1, 6)    // 1
                .doOnRequest(n -> System.out.println("Request " + n + " values..."))    // 2
                .subscribe(new BaseSubscriber<Integer>() {  // 3
                    @Override
                    protected void hookOnSubscribe(Subscription subscription) { // 4
                        System.out.println("Subscribed and make a request...");
                        request(2); // 5
                    }

                    @Override
                    protected void hookOnNext(Integer value) {  // 6
                        try {
                            TimeUnit.SECONDS.sleep(1);  // 7
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Get value [" + value + "]");    // 8
                        request(2); // 9
                    }
                });
    }
}
