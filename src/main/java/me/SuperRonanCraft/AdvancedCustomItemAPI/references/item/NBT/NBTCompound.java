package me.SuperRonanCraft.AdvancedCustomItemAPI.references.item.NBT;

import java.util.Set;

import org.bukkit.inventory.ItemStack;

public class NBTCompound
{
  private String compundname;
  private NBTCompound parent;
  
  protected NBTCompound(NBTCompound owner, String name)
  {
    this.compundname = name;
    this.parent = owner;
  }
  
  public String getName()
  {
    return this.compundname;
  }
  
  protected Object getCompound()
  {
    return this.parent.getCompound();
  }
  
  protected void setCompound(Object comp)
  {
    this.parent.setCompound(comp);
  }
  
  public NBTCompound getParent()
  {
    return this.parent;
  }
  
  protected void setItem(ItemStack item)
  {
    this.parent.setItem(item);
  }
  
  public void setString(String key, String value)
  {
    NBTReflection.setString(this, key, value);
  }
  
  public String getString(String key)
  {
    return NBTReflection.getString(this, key);
  }
  
  protected String getContent(String key)
  {
    return NBTReflection.getContent(this, key);
  }
  
  public void setInteger(String key, Integer value)
  {
    NBTReflection.setInt(this, key, value);
  }
  
  public Integer getInteger(String key)
  {
    return NBTReflection.getInt(this, key);
  }
  
  public void setDouble(String key, Double value)
  {
    NBTReflection.setDouble(this, key, value);
  }
  
  public Double getDouble(String key)
  {
    return NBTReflection.getDouble(this, key);
  }
  
  public void setByte(String key, Byte value)
  {
    NBTReflection.setByte(this, key, value);
  }
  
  public Byte getByte(String key)
  {
    return NBTReflection.getByte(this, key);
  }
  
  public void setShort(String key, Short value)
  {
    NBTReflection.setShort(this, key, value);
  }
  
  public Short getShort(String key)
  {
    return NBTReflection.getShort(this, key);
  }
  
  public void setLong(String key, Long value)
  {
    NBTReflection.setLong(this, key, value);
  }
  
  public Long getLong(String key)
  {
    return NBTReflection.getLong(this, key);
  }
  
  public void setFloat(String key, Float value)
  {
    NBTReflection.setFloat(this, key, value);
  }
  
  public Float getFloat(String key)
  {
    return NBTReflection.getFloat(this, key);
  }
  
  public void setByteArray(String key, byte[] value)
  {
    NBTReflection.setByteArray(this, key, value);
  }
  
  public byte[] getByteArray(String key)
  {
    return NBTReflection.getByteArray(this, key);
  }
  
  public void setIntArray(String key, int[] value)
  {
    NBTReflection.setIntArray(this, key, value);
  }
  
  public int[] getIntArray(String key)
  {
    return NBTReflection.getIntArray(this, key);
  }
  
  public void setBoolean(String key, Boolean value)
  {
    NBTReflection.setBoolean(this, key, value);
  }
  
  protected void set(String key, Object val)
  {
    NBTReflection.set(this, key, val);
  }
  
  public Boolean getBoolean(String key)
  {
    return NBTReflection.getBoolean(this, key);
  }
  
  public void setObject(String key, Object value)
  {
    NBTReflection.setObject(this, key, value);
  }
  
  public <T> T getObject(String key, Class<T> type)
  {
    return (T)NBTReflection.getObject(this, key, type);
  }
  
  public Boolean hasKey(String key)
  {
    return NBTReflection.hasKey(this, key);
  }
  
  public void removeKey(String key)
  {
    NBTReflection.remove(this, key);
  }
  
  public Set<String> getKeys()
  {
    return NBTReflection.getKeys(this);
  }
  
  public NBTCompound addCompound(String name)
  {
    NBTReflection.addNBTTagCompound(this, name);
    return getCompound(name);
  }
  
  public NBTCompound getCompound(String name)
  {
    NBTCompound next = new NBTCompound(this, name);
    if (NBTReflection.valideCompound(next).booleanValue()) {
      return next;
    }
    return null;
  }
  
  public NBTList getList(String name, NBTType type)
  {
    return NBTReflection.getList(this, name, type);
  }
  
  public NBTType getType(String name)
  {
    if (NBTVersion.getVersion() == NBTVersion.MC1_7_R4) {
      return NBTType.NBTTagEnd;
    }
    return NBTType.valueOf(NBTReflection.getType(this, name));
  }
  
  public String toString()
  {
    String str = "";
    for (String k : getKeys()) {
      str = str + toString(k);
    }
    return str;
  }
  
  public String toString(String key)
  {
    String s = "";
    NBTCompound c = this;
    while (c.getParent() != null)
    {
      s = s + "   ";
      c = c.getParent();
    }
    if (getType(key) == NBTType.NBTTagCompound) {
      return getCompound(key).toString();
    }
    return s + "-" + key + ": " + getContent(key) + System.lineSeparator();
  }
}
