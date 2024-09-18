package designpattern.observer.reactive;

import io.reactivex.rxjava3.core.Observable;

public class Reactive {
    public static void main(String[] args) {
        Observable<String> observable = Observable.just(
                "Hello",
                "World");
        observable
                .map(a -> a.toUpperCase())
                .subscribe(System.out::println);

    }
}
