package com.aldebaran.util;

import java.util.HashMap;
import java.util.Map;

public class MapBuilder<K, V>
{
private Map<K, V> map = new HashMap<>();
  
  public com.aldebaran.util.MapBuilder<K, V> add(K k, V v) {
     this.map.put(k, v);
     return this;
  }
  
  public com.aldebaran.util.MapBuilder<K, V> reset() {
     this.map = new HashMap<>();
     return this;
  }
  
  public Map<K, V> map() {
     return this.map;
  }
  
  public static <K, V> Map<K, V> map(K k, V v) {
     com.aldebaran.util.MapBuilder<K, V> b = new com.aldebaran.util.MapBuilder<>();
     b.add(k, v);
     return b.map;
  }
}


