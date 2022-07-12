package com.alkemy.ong.utils;

public interface ApiConstants {

   String ROLE_ADMIN = "hasRole('ROLE_ADMIN')";
   String ROLE_USER = "hasRole('ROLE_USER')";
   String BOTH = "hasAnyRole('ROLE_ADMIN','ROLE_USER')";

   //Path

   String PATH_MEMBERS = "/get-all?page=%d";

}
