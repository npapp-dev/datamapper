package com.norbcorp.hungary.datamapping;

import com.norbcorp.hungary.datamapping.configuration.Configuration;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Class for back and forth mapping. It maps values between entities and data transfer objects.
 *
 * Created by nor on 2017.05.06.
 */
public class DataMapper{

    private static Logger logger = Logger.getLogger(DataMapper.class.getName());

    /**
     * Create a DataMapper instance with default configuration.
     */
    private DataMapper(){
    }

    /**
     * Static factory method for creating a new instance of DataMapper class.
     *
     * @return new DataMapper instance.
     */
    public static DataMapper newInstance(){
        DataMapper dataMapper = new DataMapper();
        dataMapper.configuration = Configuration.getConfiguration();
        return dataMapper;
    }

    /**
     * Configuration of a DataMapper instance.
     */
    private Configuration configuration;

    /**
     * Map list of object to another type of objects.
     *
     * @param fromList list of source object
     * @param to class of destination object.
     * @param <T> destination type
     * @param <F> source type
     * @return list of destination entities
     */
    public <T,F> List<T> mapList(List<F> fromList, Class<T> to) {
        List<T> tos=new LinkedList<>();
        try {
            for(F entity : fromList) {
                    T to1 = to.newInstance();
                    if(configuration.getSelectedMappingType() == Configuration.MappingType.CUSTOM || configuration.getSelectedMappingType() == Configuration.MappingType.CUSTOM_AND_NORMAL)
                        getConfiguration().getCustomMappings().forEach((setter, getter) -> {setter.set(to1, getter.get(entity)); });
                    if(configuration.getSelectedMappingType() == Configuration.MappingType.CUSTOM_AND_NORMAL || configuration.getSelectedMappingType() == Configuration.MappingType.NORMAL)
                        map(entity, to1);
                    tos.add(to1);
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return tos;
    }


    /**
     * Mapping of values of two different object.
     *
     * @param from is an object from which its values are copied.
     * @param to is an object to which the values of <i>from</i> object are copied.
     * @param <F> type of the source object
     * @param <T> type of the return object
     * @return with an object with T type containing the mapped values.
     */
    public <F,T> T map(F from,T to) {
        if(getConfiguration().getSelectedMappingType()==Configuration.MappingType.NORMAL || getConfiguration().getSelectedMappingType()== Configuration.MappingType.CUSTOM_AND_NORMAL)
            for(Method m : from.getClass().getDeclaredMethods()){
                if(m.getName().startsWith("get")){
                    String name=m.getName().substring(3);
                    for(Method methodOfEntity : to.getClass().getDeclaredMethods()){
                        if(methodOfEntity.getName().substring(3).equals(name) && methodOfEntity.getName().startsWith("set")){
                            try {
                                if(configuration.isEagerLoadingAllowed() && (Collection.class.isAssignableFrom(m.getReturnType()))){
                                    methodOfEntity.invoke(to, m.invoke(from));
                                } else if(!Collection.class.isAssignableFrom(m.getReturnType())) {
                                    methodOfEntity.invoke(to, m.invoke(from));
                                }
                            } catch (IllegalAccessException|InvocationTargetException e) {
                                e.printStackTrace();
                            }  catch (NullPointerException npe){
                                System.out.println("NullPointerException happened");
                            }
                        }
                    }
                }
            }
        if(getConfiguration().getSelectedMappingType()==Configuration.MappingType.CUSTOM || getConfiguration().getSelectedMappingType() == Configuration.MappingType.CUSTOM_AND_NORMAL){
            getConfiguration().getMappings().forEach((supplier, consumer) ->  consumer.accept(supplier.get()));
        }
        return to;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

}
