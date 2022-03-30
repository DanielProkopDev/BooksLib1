package org.daniel.prokop.dev;

import org.daniel.prokop.dev.DAO.Person;
import org.daniel.prokop.dev.SERVICE.PersonService;
import org.daniel.prokop.dev.controllers.PersonController;
import org.daniel.prokop.dev.webexceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import javax.servlet.ServletContext;
import java.security.Provider;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@Disabled
@ContextConfiguration(classes = {WebInitializer.class,WebConfig.class,ServiceConfig.class})
@ExtendWith({MockitoExtension.class})
class PersonControllerTest {

    Logger logger = LoggerFactory.getLogger(PersonControllerTest.class);

    @Mock //Creates mock instance of the field it annotates
    private PersonService mockService;


    @InjectMocks
    private PersonController personController;



    //ApplicationContext ctx;






   /* @BeforeEach
    public void setApplicationContext(){
        this.ctx = new AnnotationConfigWebApplicationContext();

    }*/


    @SuppressWarnings("unchecked")
    @Test
    void testListHandler() {
        List<Person> list = new ArrayList<>();
        Person p = new Person();
        p.setId(1L);
        list.add(p);

       // when(mockService.findAll()).thenReturn(list);

        ExtendedModelMap model = new ExtendedModelMap();
        String viewName = personController.list(model);
        List<Person> persons = (List<Person>) model.get("persons");

        assertAll(
                () -> assertNotNull(persons),
                () -> assertEquals(1, persons.size()),
                () -> assertEquals("persons/list", viewName)
        );
    }

    @Test
    void testShowHandler() throws NotFoundException {
        Person p = new Person();
        p.setId(3L);

        //when(mockService.findById(any(Long.class))).thenReturn(Optional.of(p));

        ExtendedModelMap model = new ExtendedModelMap();
        String viewName = personController.show(3L, model);
        Person person = (Person) model.get("person");

        assertAll(
                () -> assertNotNull(person),
                () -> assertEquals(1L, person.getId()),
                () -> assertEquals("persons/show", viewName)
        );

        System.out.println(person);

    }
    @Test
    void testListPersonController() throws NotFoundException{
        ExtendedModelMap model1 = new ExtendedModelMap();

        String viewName = personController.list(model1);
        System.out.println(model1.getAttribute("persons").toString());


    }


  /*  @Test
    void beansControllerTest() throws Exception{

        // Optional<TestTestTest2> testTestTest2 =Optional.of(ctx.getBean(TestTestTest2.class));

        //System.out.println(testTestTest2.get().getTestNumber());
        // System.out.println(testClass.getTestNumber());
        System.out.println(personController.getTest());
    }*/
}
