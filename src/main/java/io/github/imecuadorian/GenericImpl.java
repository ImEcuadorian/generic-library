package io.github.imecuadorian;

import lombok.*;

import java.util.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class GenericImpl<T, S> implements Generic<T, S> {

    private T t1;
    private T t2;
    private S s1;
    private S s2;
    private T[] arrayT;
    private S[] arrayS;
    private List<T> tList = new ArrayList<>();
    private List<S> sList = new ArrayList<>();

    public GenericImpl(T t1) {
        this.t1 = t1;
    }

    public GenericImpl(T t1, S s1) {
        this.t1 = t1;
        this.s1 = s1;
    }

    public GenericImpl(T t1, T t2, S s1) {
        this.t1 = t1;
        this.t2 = t2;
        this.s1 = s1;
    }

    public GenericImpl(T t1, T t2, S s1, S s2) {
        this.t1 = t1;
        this.t2 = t2;
        this.s1 = s1;
        this.s2 = s2;
    }

    public GenericImpl(T t1, S[] arrayS) {
        this.t1 = t1;
        this.arrayS = arrayS;
    }

    @Override
    public void addElementListT(T t) {
        this.tList.add(t);
    }

    @Override
    public void addElementListS(S s) {
        this.sList.add(s);
    }
}
