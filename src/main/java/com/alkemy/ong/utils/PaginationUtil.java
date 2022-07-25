package com.alkemy.ong.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public class PaginationUtil {
    private Integer page;
    private static final Integer PAGE_SIZE = 10;
    private String path;
    private Page<?> pageObject;

    /**
     * Constructor to use query with pagination
     *
     * @param repository receives an object of type JpaRepository
     * @param page receives an integer value to indicate the page number
     * @param path receives a string value to indicate the path of the page
     */
    public PaginationUtil(JpaRepository repository, Integer page, String path) {
        this.page = page;
        this.path = path;
        this.pageObject = repository.findAll(PageRequest.of(page-1, PAGE_SIZE));
    }


    /**
     * Method to get the page object
     *
     * @return a page object
     */
    public Page<?> getPage() {
        return pageObject;
    }

    /**
     * Method to get the previous page url
     *
     * @return a string value
     */
    public String getPrevious() {
        if(page > 1){
            return String.format(path, page-1);
        }
        return null;
    }

    /**
     * Method to get the next page url
     *
     * @return a string value
     */
    public String getNext() {
        if(pageObject.hasNext()){
            return String.format(path, page+1);
        }
        return null;
    }
}
