package com.chatapp.chatapppractice.models.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Tuple<A, B> {
    private A firstObject;
    private B secondObject;

    /**
     * Creates a tuple with two objects of any type A and B.
     * @param objectA first object that you want.
     * @param objectB second object that you want.
     */
    public Tuple(final A objectA, final B objectB) {
        this.firstObject = objectA;
        this.secondObject = objectB;
    }
}
