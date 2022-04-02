module BooksLib.main {

    requires org.hibernate.orm.core;

    requires org.apache.commons.lang3;
    requires java.sql;
    requires org.slf4j;
    requires java.naming;
    requires java.annotation;




    requires spring.data.jpa;
    requires spring.context;
    requires spring.beans;
    requires spring.core;
    requires spring.jdbc;
    requires spring.tx;
    requires spring.orm;
    requires java.persistence;
    requires spring.data.commons;
    requires com.h2database;

    requires java.validation;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires com.zaxxer.hikari;
    requires spring.webmvc;
    requires spring.web;
    requires javax.servlet.api;


    opens org.daniel.prokop.dev to spring.core;

}