package com.norbcorp.hungary.datamapping.configuration;

/**
 * Placeholder getter of source class.
 *
 * @param <S> Type of the source class.
 * @param <V> Type of the return value
 */
public interface PropertyGetter<S,V> {
   V get(S sourceClass);
}
