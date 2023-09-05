package com.redis.om.spring.metamodel;

import org.springframework.data.domain.Sort.Order;

import java.util.Comparator;
import java.util.function.Function;

public class MetamodelField<E, T> implements Comparator<E>, Function<E,T> {

  protected final SearchFieldAccessor searchFieldAccessor;
  protected final boolean indexed;
  protected final String alias;
  protected Class<?> targetClass;
  
  public MetamodelField(SearchFieldAccessor searchFieldAccessor, boolean indexed) {
    this.searchFieldAccessor = searchFieldAccessor;
    this.indexed = indexed;
    this.alias = null;
  }

  public MetamodelField(String alias, Class targetClass, boolean indexed) {
    this.searchFieldAccessor = null;
    this.indexed = indexed;
    this.alias = alias;
    this.targetClass = targetClass;
  }

  public MetamodelField(String alias, Class targetClass) {
    this.searchFieldAccessor = null;
    this.indexed = false;
    this.alias = alias;
    this.targetClass = targetClass;
  }
  
  public SearchFieldAccessor getSearchFieldAccessor() {
    return searchFieldAccessor;
  }

  @Override
  public int compare(E o1, E o2) {
    return 0;
  }

  @Override
  public T apply(E t) {
    return null;
  }
  
  public boolean isIndexed() {
    return indexed;
  }

  public String getSearchAlias() {
    return searchFieldAccessor != null ? searchFieldAccessor.getSearchAlias() : alias;
  }

  public Class<?> getTargetClass() {
    return searchFieldAccessor != null ? searchFieldAccessor.getTargetClass() : targetClass;
  }

  public Order asc() {
    return Order.asc("@" + getSearchAlias());
  }

  public Order desc() {
    return Order.desc("@" + getSearchAlias());
  }
}
