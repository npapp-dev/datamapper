package com.norbcorp.hungary.datamapping.configuration;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Interface for DataMapper configuration. Provides methods for class and object mapping.
 */
public interface Configuration{

    /**
     * If it is allowed, setters of lists are also invoked.
     *
     * @return <i>true</i> if eager loading is allowed.
     */
    boolean isEagerLoadingAllowed();

    /**
     * Defines class mapping between the source and the destination class.
     *
     * @param propertyGetter getter of source class
     * @param propertySetter setter of destination class
     * @param <V> transported value
     * @param <D> Destination class
     * @param <S> Source class
     */
    <V,D,S> void addMapping(PropertyGetter<S, V> propertyGetter, PropertySetter<D, V> propertySetter);

    /**
     * Defines object mapping between the source and the destination object.
     *
     * @param supplier supplier of source object
     * @param consumer consumer of destination object
     * @param <T> type of the transported value
     */
    <T> void addMapping(Supplier<T> supplier, Consumer<T> consumer);

    /**
     * List of object mappings.
     *
     * @param <T> type of the transported
     * @return Map of the supplier and consumer mappings.
     */
    <T> Map<Supplier<T>, Consumer<T>> getMappings();

    /**
     * @return selected mapping type.
     */
    MappingType getSelectedMappingType();

    /**
     * Map of static mappings.
     *
     * @return Map of static mappings.
     */
    Map<PropertySetter, PropertyGetter> getCustomMappings();

    /**
     * Selected mapping type.
     *
     * The default option is normal.
     */
    void setSelectedMappingType(MappingType mappingType);

    /**
     * If it is allowed, it invokes getter methods of Collections.
     *
     * @param eagerLoadingAllowed boolean value
     * @return the datamapper instance.
     */
    Configuration setEagerLoadingAllowed(boolean eagerLoadingAllowed);

    /**
     * Type of the mapping.
     *
     * <i>ONLY_CUSTOM</i>: only custom mappings.
     * <i>NORMAL</i>: only normal JavaBean method mapping.
     * <i>CUSTOM_AND_NORMAL</i>: combination of the previous two.
     */
    enum MappingType {
        CUSTOM, NORMAL, CUSTOM_AND_NORMAL
    }

     static Configuration getConfiguration(){
        return new DataMapperConfiguration();
    }
}
