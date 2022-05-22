package com.norbcorp.hungary.datamapping.configuration;

/**
 * Placeholder setter method of the source class.
 *
 * @param <D> Type of the destination class.
 * @param <V> Type of the parameter of setter method.
 */
public interface PropertySetter<D,V> {
  void set(D destinationClass, V s);
}