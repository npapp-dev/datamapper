package com.norbcorp.hungary.datamapping;

import com.norbcorp.hungary.datamapping.configuration.Configuration;
import com.norbcorp.hungary.datamapping.util.CustomAndNormalConfigurationDTO;
import com.norbcorp.hungary.datamapping.util.DTO;
import com.norbcorp.hungary.datamapping.util.Entity;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class DataMapperConfigurationTest {

    private static Logger logger = Logger.getLogger(DataMapperConfigurationTest.class.getName());

    private Entity entity;
    private DataMapper dataMapper;

    @Before
    public void setUpTest(){
        entity = new Entity();
        entity.setName("TestFirstName TestLastName");
        entity.setDateOfRegistration(Date.from(Instant.now()));
        dataMapper = DataMapper.newInstance();
    }


    @Test
    public void testCustomConfiguration(){
        DTO dto = new DTO();
        dataMapper.getConfiguration().addMapping(entity::getName, dto::setName);
        dataMapper.getConfiguration().addMapping(entity::getDateOfRegistration, dto::setDateOfRegistration);
        dataMapper.getConfiguration().setSelectedMappingType(Configuration.MappingType.CUSTOM);

        dataMapper.map(entity,dto);
        assertThat(entity.getName(), is(equalTo(dto.getName())));
        assertThat(entity.getDateOfRegistration(), is(equalTo(dto.getDateOfRegistration())));
    }

    @Test
    public void testNormalConfiguration(){
        dataMapper.getConfiguration().setSelectedMappingType(Configuration.MappingType.NORMAL);
        DTO dtoForNormalConfiguration = new DTO();
        dataMapper.map(entity,dtoForNormalConfiguration);
        assertThat(entity.getName(), is(equalTo(dtoForNormalConfiguration.getName())));
        assertThat(entity.getDateOfRegistration(), is(equalTo(dtoForNormalConfiguration.getDateOfRegistration())));
    }

    @Test
    public void testNormalAndCustomConfiguarion(){
        //Check normal and custom configuration
        dataMapper.getConfiguration().setSelectedMappingType(Configuration.MappingType.CUSTOM_AND_NORMAL);
        CustomAndNormalConfigurationDTO dtoForCustomAndNormalConfigurationDTO = new CustomAndNormalConfigurationDTO();
        dataMapper.getConfiguration().addMapping(()->entity.getName().split(" ")[0], dtoForCustomAndNormalConfigurationDTO::setFirstName);
        dataMapper.getConfiguration().addMapping(()->entity.getName().split(" ")[1], dtoForCustomAndNormalConfigurationDTO::setLastName);
        dataMapper.map(entity,dtoForCustomAndNormalConfigurationDTO);
        assertThat(entity.getName(), is(equalTo(dtoForCustomAndNormalConfigurationDTO.getFirstName()+" "+dtoForCustomAndNormalConfigurationDTO.getLastName())));
        assertThat(entity.getDateOfRegistration(), is(equalTo(dtoForCustomAndNormalConfigurationDTO.getDateOfRegistration())));
    }

    @Test
    public void testMapListNormalConfiguration(){
        List<Entity> entities=new LinkedList<>();
        entities.add(entity);
        entities.add(new Entity("test2", 20, Date.from(Instant.now())));
        entities.add(new Entity("test3", 20, Date.from(Instant.now())));

        List<DTO> result = dataMapper.mapList(entities, DTO.class);
        assertThat(3, is(equalTo(result.size())));
        assertThat(entities.get(0).getName(), is(equalTo(result.get(0).getName())));
        assertThat(entities.get(1).getName(), is(equalTo(result.get(1).getName())));
        assertThat(entities.get(2).getName(), is(equalTo(result.get(2).getName())));
        assertThat(entities.get(0).getDateOfRegistration(), is(equalTo(result.get(0).getDateOfRegistration())));
        assertThat(entities.get(1).getDateOfRegistration(), is(equalTo(result.get(1).getDateOfRegistration())));
        assertThat(entities.get(2).getDateOfRegistration(), is(equalTo(result.get(2).getDateOfRegistration())));
    }

    @Test
    public void testMapListCustomConfiguration(){
        List<Entity> entities=new LinkedList<>();
        entities.add(entity);
        entities.add(new Entity("test2", 20, Date.from(Instant.now())));
        entities.add(new Entity("test3", 20, Date.from(Instant.now())));

        dataMapper.getConfiguration().setSelectedMappingType(Configuration.MappingType.CUSTOM);
        dataMapper.getConfiguration().addMapping(Entity::getName, DTO::setName);
        List<DTO> result = dataMapper.mapList(entities, DTO.class);

        assertThat(3, is(equalTo(result.size())));
        assertThat(result.get(0).getName(), is(equalTo(entities.get(0).getName())));
        assertThat(result.get(1).getName(), is(equalTo(entities.get(1).getName())));
        assertThat(result.get(2).getName(), is(equalTo(entities.get(2).getName())));
    }

    @Test
    public void testMapListCustomAndNormalConfiguration(){
        List<Entity> entities=new LinkedList<>();
        entities.add(entity);
        entities.add(new Entity("test2", 21, Date.from(Instant.now())));
        entities.add(new Entity("test3", 22, Date.from(Instant.now())));

        dataMapper.getConfiguration().setSelectedMappingType(Configuration.MappingType.CUSTOM_AND_NORMAL);
        dataMapper.getConfiguration().addMapping(Entity::getName, DTO::setName);
        List<DTO> result = dataMapper.mapList(entities, DTO.class);

        assertThat(3, is(equalTo(result.size())));
        assertThat(result.get(0).getName(), is(equalTo(entities.get(0).getName())));
        assertThat(result.get(1).getName(), is(equalTo(entities.get(1).getName())));
        assertThat(result.get(2).getName(), is(equalTo(entities.get(2).getName())));

        assertThat(result.get(0).getDateOfRegistration(), is(equalTo(entities.get(0).getDateOfRegistration())));
        assertThat(result.get(1).getDateOfRegistration(), is(equalTo(entities.get(1).getDateOfRegistration())));
        assertThat(result.get(2).getDateOfRegistration(), is(equalTo(entities.get(2).getDateOfRegistration())));

        assertThat(result.get(0).getAge(), is(equalTo(entities.get(0).getAge())));
        assertThat(result.get(1).getAge(), is(equalTo(entities.get(1).getAge())));
        assertThat(result.get(2).getAge(), is(equalTo(entities.get(2).getAge())));
    }
}
