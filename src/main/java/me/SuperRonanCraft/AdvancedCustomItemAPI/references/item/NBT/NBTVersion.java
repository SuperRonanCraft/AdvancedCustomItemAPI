package me.SuperRonanCraft.AdvancedCustomItemAPI.references.item.NBT;

public enum NBTVersion 
	{
	  Unknown(0),  MC1_7_R4(174),  MC1_8_R3(183),  MC1_9_R1(191),  MC1_9_R2(192),  MC1_10_R1(1101),  MC1_11_R1(1111),  MC1_12_R1(1121);
	  
	  private static NBTVersion version;
	  private final int id;
	  
	  private NBTVersion(int id)
	  {
	    this.id = id;
	  }
	  
	  public int getId()
	  {
	    return this.id;
	  }
	  
	  public static NBTVersion getVersion()
	  {
	    if (version != null) {
	      return version;
	    }
	    String ver = org.bukkit.Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
	    System.out.println("[NBTAPI] Found Spigot: " + ver + "! Trying to find NMS support");
	    try
	    {
	      version = valueOf(ver.replace("v", "MC"));
	    }
	    catch (IllegalArgumentException ex)
	    {
	      version = Unknown;
	    }
	    if (version != Unknown) {
	      System.out.println("[NBTAPI] NMS support '" + version.name() + "' loaded!");
	    } else {
	      System.out.println("[NBTAPI] Wasn't able to find NMS Support! Some functions will not work!");
	    }
	    return version;
	  }
	  
	  private static Boolean cache = null;
	  
	  public static boolean hasGson()
	  {
	    if (cache != null) {
	      return cache.booleanValue();
	    }
	    cache = Boolean.valueOf(false);
	    try
	    {
	      System.out.println("Found Gson: " + Class.forName("com.google.gson.Gson"));
	      cache = Boolean.valueOf(true);
	      return cache.booleanValue();
	    }
	    catch (Exception ex) {}
	    return cache.booleanValue();
	  }
	}
