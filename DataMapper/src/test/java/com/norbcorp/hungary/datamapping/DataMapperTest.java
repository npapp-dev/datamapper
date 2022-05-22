package com.norbcorp.hungary.datamapping;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.norbcorp.hungary.datamapping.util.DTO;
import com.norbcorp.hungary.datamapping.util.DTOWithPrivateConstructor;
import com.norbcorp.hungary.datamapping.util.Entity;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by nor on 2017.05.06..
 */
public class DataMapperTest {

    private SimpleDateFormat sdf=new SimpleDateFormat("YYYY-MM-DD HH:mm");

    @Test
    public void testMappingEntityToDTO(){
        DTO dto=new DTO();
        Entity entity=new Entity();
        DataMapper dataMapper = DataMapper.newInstance();
        assertThat(dataMapper.map(entity,dto).getName(),is(equalTo("Test")));
        entity.setName("test 2");
        assertThat(dataMapper.map(entity,dto).getName(), is(equalTo("test 2")));

        entity.setAge(123);
        assertEquals(dataMapper.map(entity, dto).getAge().intValue(),123);

        entity.setAge(200);
        assertEquals(dataMapper.map(entity, dto).getAge().intValue(),200);
        try {
            entity.setDateOfRegistration(sdf.parse("2017-05-06 20:00"));
            Date date=sdf.parse("2017-05-06 20:00");
            assertEquals(dataMapper.map(entity,dto).getDateOfRegistration(),date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        DTO dto1 = new DTO();
        assertNull(dataMapper.map(entity,dto1).getStrings());
        dataMapper.getConfiguration().setEagerLoadingAllowed(true);
        DTO dto2 = new DTO();
        Assert.assertNotNull(dataMapper.map(entity,dto2).getStrings());
    }

    @Test
    public void testMappingDTOToEntity(){
        DTO  dto = new DTO();
        Entity entity = new Entity();
        entity.setName(null);
        entity.setAge(null);
        entity.setDateOfRegistration(null);
        entity.setDescription(null);
        entity.setStrings(null);

        dto.setName("Test");
        dto.setAge(10);
        dto.setStrings(new LinkedList<String>());

        DataMapper dataMapper = DataMapper.newInstance();
        assertEquals(dataMapper.map(dto, entity).getName(),"Test");
        assertEquals(dataMapper.map(dto, entity).getAge().intValue(),10);
        assertNull(dataMapper.map(dto, entity).getStrings());
        dataMapper.getConfiguration().setEagerLoadingAllowed(true);
        assertNotNull(dataMapper.map(dto, entity));
    }

    @Test
    public void testMappingEntitiesToDTOs(){
        List<Entity> entities=new LinkedList<Entity>();
        entities.add(new Entity());

        DataMapper dataMapper = DataMapper.newInstance();

        assertEquals(1,dataMapper.mapList(entities, DTO.class).size());
        Entity entity = new Entity();
        entity.setName("Entity mapping test");
        entities.add(entity);
        assertEquals("Entity mapping test",dataMapper.mapList(entities, DTO.class).get(1).getName());
        assertEquals(2,dataMapper.mapList(entities, DTO.class).size());
    }

    @Test
    public void testMappingToDTOWithPrivateConstructor(){
        DataMapper dataMapper = DataMapper.newInstance();
        Entity entity = new Entity();
        entity.setName("TestName");
        entity.setAge(10);
        entity.setDateOfRegistration(Date.from(Instant.now()));
        entity.setDescription("Test description");

        dataMapper.map(entity, DTOWithPrivateConstructor.class);
    }
}
