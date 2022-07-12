package com.alkemy.ong.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * This class allows us to avoid unparameterized raw type conversions. The use of raw types will make us lose all the advantages of security and expressiveness of the generic ones.
 * @param <T> Entity object
 * @param <ID> Data type used in the entity ID
 * @param <R> Repository created for the entity
 */
public abstract class ClassUtil<T, ID, R extends JpaRepository<T, ID>> {

   @Autowired
   private R repository;
   private static final Integer PAGE_SIZE = 10;

   /**
    * Method to get the page object
    * @param page Number page
    * @return Entity page
    */
   protected Page<T> getPage(Integer page) {
      return repository.findAll(PageRequest.of(page-1, PAGE_SIZE));
   }

   /**
    * Method to get the previous page url
    * @param path Path
    * @param page Number page
    * @return String value
    */
   protected String getPrevious(String path, Integer page) {
      if(page > 1){
         return String.format(path, page-1);
      }
      return null;
   }

   /**
    * Method to get the next page url
    * @param tPage Entity page
    * @param path Path
    * @param page Number page
    * @return String value
    */
   protected String getNext(Page<T> tPage, String path, Integer page) {
      if(tPage.hasNext()){
         return String.format(path, page+1);
      }
      return null;
   }

   protected void save(T t) { repository.save(t); }

   protected List<T> findAll() { return repository.findAll(); }

   /**
    * Find entity by id
    * @param id Entity id
    * @param entity Entity name
    * @return Entity or throw an exception
    */
   protected T findById(ID id, String entity) {
      return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(entity + " not found with id: " + id));
   }

   protected void delete(T t) { repository.delete(t); }
}
