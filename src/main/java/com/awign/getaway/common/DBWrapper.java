package com.awign.getaway.common;

import java.util.List;

/**
 * Created by nitesh on 11/3/17.
 */
public interface DBWrapper {

  boolean save() throws DBMongo.InitializeException;

  boolean update() throws DBMongo.InitializeException;

  boolean delete() throws DBMongo.InitializeException;

  DBWrapper getOne() throws DBMongo.InitializeException;

  List<DBWrapper> getAll() throws DBMongo.InitializeException;
}
