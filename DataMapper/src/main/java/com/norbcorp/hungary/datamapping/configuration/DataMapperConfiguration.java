package com.norbcorp.hungary.datamapping.configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.logging.Logger;

/**
 * Configuration of DataMapper class.
 */
class DataMapperConfiguration implements Configuration{

    private static Logger logger = Logger.getLogger(DataMapperConfiguration.class.getName());

    /**
     * Custom mappings of values for instance mapping.
     */
    private  Map<Supplier,Consumer> mappings = new HashMap<>();

    /**
     * Custom mappings of values for classes.
     */
    private Map<PropertySetter,PropertyGetter> customMappings = new HashMap<>();

    /**
     *  Parameter of eager loading of collections.
     *  Setters of collections are not invoked if it is false.
     */
    private boolean eagerLoadingAllowed=false;

    /**
     * Selected mapping type.
     *
     * The default option is normal.
     */
    private MappingType selectedMappingType = MappingType.NORMAL;

    /**
     * Eager loading of collections. If it is set to true, it will invoke getters of collections. Otherwise it will not.
     *
     * @return boolean of eagerLoadingAllowed parameter.
     */
    public boolean isEagerLoadingAllowed() {
        return eagerLoadingAllowed;
    }

    @Override
    public Configuration setEagerLoadingAllowed(boolean eagerLoadingAllowed) {
        this.eagerLoadingAllowed = eagerLoadingAllowed;
        return this;
    }

    @Override
    public  <S,D,E> void addMapping(PropertyGetter<E,S> propertyGetter, PropertySetter<D,S> propertySetter) {
        this.customMappings.put(propertySetter,propertyGetter);
    }

    public <T> void addMapping(Supplier<T> supplier, Consumer<T> consumer) {
        getMappings().put(supplier,consumer);
    }

    public Map<Supplier, Consumer> getMappings() {
        return mappings;
    }

    public MappingType getSelectedMappingType() {
        return selectedMappingType;
    }

    public void setSelectedMappingType(MappingType selectedMappingType) {
        this.selectedMappingType = selectedMappingType;
    }

    public Map<PropertySetter, PropertyGetter> getCustomMappings() {
        return customMappings;
    }
}
