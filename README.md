# datamapper

DataMapper is a simple and easy-to-use tool for object mapping. DataMapper aims to solve the problem of DTO and Entity synchronization.

Usage:

```java
dataMapper.map(entity,dto); //mapping the values of entity object to dto
```
 
Both the source and destination object have to comply with JavaBean specification.

Custom mappings can be defined:

```java
dataMapper.getConfiguration().addMapping(()->entity.getName().split(" ")[0], dtoForCustomAndNormalConfigurationDTO::setFirstName);
dataMapper.getConfiguration().addMapping(()->entity.getName().split(" ")[1], dtoForCustomAndNormalConfigurationDTO::setLastName);
```
  
Configuration file provides three way of object mapping:
+ Custom: object mapping using predefined custom mappings.
+ Normal: object mapping using getters and setters.
+ Custom and normal: object mapping with custom and normal.

```java
dataMapper.getConfiguration().setSelectedMappingType(Configuration.MappingType.NORMAL);
dataMapper.getConfiguration().setSelectedMappingType(Configuration.MappingType.CUSTOM_AND_NORMAL);
dataMapper.getConfiguration().setSelectedMappingType(Configuration.MappingType.CUSTOM);
```

It is possible to create mapping between classes. It is very useful for mapping lists. For example,

```java
dataMapper.getConfiguration().setSelectedMappingType(Configuration.MappingType.CUSTOM_AND_NORMAL);
dataMapper.getConfiguration().addMapping(Entity::getName, DTO::setName);
List<DTO> result = dataMapper.mapList(entities, DTO.class);
```
