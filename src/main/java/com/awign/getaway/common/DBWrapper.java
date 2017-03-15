package com.awign.getaway.common;

import java.util.List;

/**
 * Created by nitesh on 11/3/17.
 */
public interface DBWrapper {

  boolean save() throws DBRedis.InitializeException;

  boolean update() throws DBRedis.InitializeException;

  boolean delete() throws DBRedis.InitializeException;

  DBWrapper getOne() throws DBRedis.InitializeException;

  List<DBWrapper> getAll() throws DBRedis.InitializeException;
}
