package me.SuperRonanCraft.AdvancedCustomItemAPI.references.item.NBT;

public class NBTNames {
	  protected static String getTiledataMethodName()
	  {
	    NBTVersion v = NBTVersion.getVersion();
	    if (v == NBTVersion.MC1_8_R3) {
	      return "b";
	    }
	    return "save";
	  }
	  
	  protected static String getTypeMethodName()
	  {
	    NBTVersion v = NBTVersion.getVersion();
	    if (v == NBTVersion.MC1_8_R3) {
	      return "b";
	    }
	    return "d";
	  }
	  
	  @SuppressWarnings("unused")
	protected static String getEntitynbtgetterMethodName()
	  {
	    NBTVersion v = NBTVersion.getVersion();
	    return "b";
	  }
	  
	  @SuppressWarnings("unused")
	protected static String getEntitynbtsetterMethodName()
	  {
	    NBTVersion v = NBTVersion.getVersion();
	    return "a";
	  }
	  
	  protected static String getremoveMethodName()
	  {
	    NBTVersion v = NBTVersion.getVersion();
	    if (v == NBTVersion.MC1_8_R3) {
	      return "a";
	    }
	    return "remove";
	  }
}
