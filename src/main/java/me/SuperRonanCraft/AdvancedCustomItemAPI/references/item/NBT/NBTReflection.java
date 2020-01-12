package me.SuperRonanCraft.AdvancedCustomItemAPI.references.item.NBT;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.Stack;

import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

public class NBTReflection {
	private static final String version = org.bukkit.Bukkit.getServer().getClass().getPackage().getName()
			.replace(".", ",").split(",")[3];

	private static Class<?> getCraftItemStack() {
		try {
			return Class.forName("org.bukkit.craftbukkit." + version + ".inventory.CraftItemStack");
		} catch (Exception ex) {
			System.out.println("Error in ItemNBTAPI! (Outdated plugin?)");
			ex.printStackTrace();
		}
		return null;
	}

	private static Class<?> getCraftEntity() {
		try {
			return Class.forName("org.bukkit.craftbukkit." + version + ".entity.CraftEntity");
		} catch (Exception ex) {
			System.out.println("Error in ItemNBTAPI! (Outdated plugin?)");
			ex.printStackTrace();
		}
		return null;
	}

	protected static Class<?> getNBTBase() {
		try {
			return Class.forName("net.minecraft.server." + version + ".NBTBase");
		} catch (Exception ex) {
			System.out.println("Error in ItemNBTAPI! (Outdated plugin?)");
			ex.printStackTrace();
		}
		return null;
	}

	protected static Class<?> getNBTTagString() {
		try {
			return Class.forName("net.minecraft.server." + version + ".NBTTagString");
		} catch (Exception ex) {
			System.out.println("Error in ItemNBTAPI! (Outdated plugin?)");
			ex.printStackTrace();
		}
		return null;
	}

	protected static Class<?> getNBTTagCompound() {
		try {
			return Class.forName("net.minecraft.server." + version + ".NBTTagCompound");
		} catch (Exception ex) {
			System.out.println("Error in ItemNBTAPI! (Outdated plugin?)");
			ex.printStackTrace();
		}
		return null;
	}

	protected static Class<?> getNBTCompressedStreamTools() {
		try {
			return Class.forName("net.minecraft.server." + version + ".NBTCompressedStreamTools");
		} catch (Exception ex) {
			System.out.println("Error in ItemNBTAPI! (Outdated plugin?)");
			ex.printStackTrace();
		}
		return null;
	}

	protected static Class<?> getTileEntity() {
		try {
			return Class.forName("net.minecraft.server." + version + ".TileEntity");
		} catch (Exception ex) {
			System.out.println("Error in ItemNBTAPI! (Outdated plugin?)");
			ex.printStackTrace();
		}
		return null;
	}

	protected static Class<?> getCraftWorld() {
		try {
			return Class.forName("org.bukkit.craftbukkit." + version + ".CraftWorld");
		} catch (Exception ex) {
			System.out.println("Error in ItemNBTAPI! (Outdated plugin?)");
			ex.printStackTrace();
		}
		return null;
	}

	public static Object getNewNBTTag() {
		String version = org.bukkit.Bukkit.getServer().getClass().getPackage().getName().replace(".", ",")
				.split(",")[3];
		try {
			Class<?> c = Class.forName("net.minecraft.server." + version + ".NBTTagCompound");
			return c.newInstance();
		} catch (Exception ex) {
			System.out.println("Error in ItemNBTAPI! (Outdated plugin?)");
			ex.printStackTrace();
		}
		return null;
	}

	private static Object getnewBlockPosition(int x, int y, int z) {
		String version = org.bukkit.Bukkit.getServer().getClass().getPackage().getName().replace(".", ",")
				.split(",")[3];
		try {
			Class<?> c = Class.forName("net.minecraft.server." + version + ".BlockPosition");
			return c.getConstructor(new Class[] { Integer.TYPE, Integer.TYPE, Integer.TYPE })
					.newInstance(new Object[] { Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(z) });
		} catch (Exception ex) {
			System.out.println("Error in ItemNBTAPI! (Outdated plugin?)");
			ex.printStackTrace();
		}
		return null;
	}

	public static Object setNBTTag(Object NBTTag, Object NMSItem) {
		try {
			Method method = NMSItem.getClass().getMethod("setTag", new Class[] { NBTTag.getClass() });
			method.invoke(NMSItem, new Object[] { NBTTag });
			return NMSItem;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static Object getNMSItemStack(ItemStack item) {
		Class<?> cis = getCraftItemStack();
		try {
			Method method = cis.getMethod("asNMSCopy", new Class[] { ItemStack.class });
			return method.invoke(cis, new Object[] { item });
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Object getNMSEntity(Entity entity) {
		Class<?> cis = getCraftEntity();
		try {
			Method method = cis.getMethod("getHandle", new Class[0]);
			return method.invoke(getCraftEntity().cast(entity), new Object[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Object readNBTFile(FileInputStream stream) {
		Class<?> cis = getNBTCompressedStreamTools();
		try {
			Method method = cis.getMethod("a", new Class[] { InputStream.class });
			return method.invoke(cis, new Object[] { stream });
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Object saveNBTFile(Object nbt, FileOutputStream stream) {
		Class<?> cis = getNBTCompressedStreamTools();
		try {
			Method method = cis.getMethod("a", new Class[] { getNBTTagCompound(), OutputStream.class });
			return method.invoke(cis, new Object[] { nbt, stream });
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static ItemStack getBukkitItemStack(Object item) {
		Class<?> cis = getCraftItemStack();
		try {
			Method method = cis.getMethod("asCraftMirror", new Class[] { item.getClass() });
			Object answer = method.invoke(cis, new Object[] { item });
			return (ItemStack) answer;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Object getItemRootNBTTagCompound(Object nmsitem) {
		Class<? extends Object> c = nmsitem.getClass();
		try {
			Method method = c.getMethod("getTag", new Class[0]);
			return method.invoke(nmsitem, new Object[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Object getEntityNBTTagCompound(Object nmsitem) {
		Class<? extends Object> c = nmsitem.getClass();
		try {
			Method method = c.getMethod(NBTNames.getEntitynbtgetterMethodName(),
					new Class[] { getNBTTagCompound() });
			Object nbt = getNBTTagCompound().newInstance();
			Object answer = method.invoke(nmsitem, new Object[] { nbt });
			if (answer == null) {
			}
			return nbt;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Object setEntityNBTTag(Object NBTTag, Object NMSItem) {
		try {
			Method method = NMSItem.getClass().getMethod(NBTNames.getEntitynbtsetterMethodName(),
					new Class[] { getNBTTagCompound() });
			method.invoke(NMSItem, new Object[] { NBTTag });
			return NMSItem;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static Object getTileEntityNBTTagCompound(BlockState tile) {
		try {
			Object pos = getnewBlockPosition(tile.getX(), tile.getY(), tile.getZ());
			Object cworld = getCraftWorld().cast(tile.getWorld());
			Object nmsworld = cworld.getClass().getMethod("getHandle", new Class[0]).invoke(cworld, new Object[0]);
			Object o = nmsworld.getClass().getMethod("getTileEntity", new Class[] { pos.getClass() }).invoke(nmsworld,
					new Object[] { pos });
			Method method = getTileEntity().getMethod(NBTNames.getTiledataMethodName(),
					new Class[] { getNBTTagCompound() });
			Object tag = getNBTTagCompound().newInstance();
			Object answer = method.invoke(o, new Object[] { tag });
			if (answer == null) {
			}
			return tag;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void setTileEntityNBTTagCompound(BlockState tile, Object comp) {
		try {
			Object pos = getnewBlockPosition(tile.getX(), tile.getY(), tile.getZ());
			Object cworld = getCraftWorld().cast(tile.getWorld());
			Object nmsworld = cworld.getClass().getMethod("getHandle", new Class[0]).invoke(cworld, new Object[0]);
			Object o = nmsworld.getClass().getMethod("getTileEntity", new Class[] { pos.getClass() }).invoke(nmsworld,
					new Object[] { pos });
			Method method = getTileEntity().getMethod("a", new Class[] { getNBTTagCompound() });
			method.invoke(o, new Object[] { comp });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Object getSubNBTTagCompound(Object compound, String name) {
		Class<? extends Object> c = compound.getClass();
		try {
			Method method = c.getMethod("getCompound", new Class[] { String.class });
			return method.invoke(compound, new Object[] { name });
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void addNBTTagCompound(NBTCompound comp, String name) {
		if (name == null) {
			remove(comp, name);
			return;
		}
		Object nbttag = comp.getCompound();
		if (nbttag == null) {
			nbttag = getNewNBTTag();
		}
		if (!valideCompound(comp).booleanValue()) {
			return;
		}
		Object workingtag = gettoCompount(nbttag, comp);
		try {
			Method method = workingtag.getClass().getMethod("set", new Class[] { String.class, getNBTBase() });
			method.invoke(workingtag, new Object[] { name, getNBTTagCompound().newInstance() });
			comp.setCompound(nbttag);
			return;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static Boolean valideCompound(NBTCompound comp) {
		Object root = comp.getCompound();
		if (root == null) {
			root = getNewNBTTag();
		}
		if (gettoCompount(root, comp) != null) {
			return Boolean.valueOf(true);
		}
		return Boolean.valueOf(false);
	}

	private static Object gettoCompount(Object nbttag, NBTCompound comp) {
		Stack<String> structure = new Stack<String>();
		while (comp.getParent() != null) {
			structure.add(comp.getName());
			comp = comp.getParent();
		}
		while (!structure.isEmpty()) {
			nbttag = getSubNBTTagCompound(nbttag, (String) structure.pop());
			if (nbttag == null) {
				return null;
			}
		}
		return nbttag;
	}

	public static void setString(NBTCompound comp, String key, String text) {
		if (text == null) {
			remove(comp, key);
			return;
		}
		Object rootnbttag = comp.getCompound();
		if (rootnbttag == null) {
			rootnbttag = getNewNBTTag();
		}
		if (!valideCompound(comp).booleanValue()) {
			return;
		}
		Object workingtag = gettoCompount(rootnbttag, comp);
		try {
			Method method = workingtag.getClass().getMethod("setString", new Class[] { String.class, String.class });
			method.invoke(workingtag, new Object[] { key, text });
			comp.setCompound(rootnbttag);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static String getString(NBTCompound comp, String key) {
		Object rootnbttag = comp.getCompound();
		if (rootnbttag == null) {
			rootnbttag = getNewNBTTag();
		}
		if (!valideCompound(comp).booleanValue()) {
			return null;
		}
		Object workingtag = gettoCompount(rootnbttag, comp);
		try {
			Method method = workingtag.getClass().getMethod("getString", new Class[] { String.class });
			return (String) method.invoke(workingtag, new Object[] { key });
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static String getContent(NBTCompound comp, String key) {
		Object rootnbttag = comp.getCompound();
		if (rootnbttag == null) {
			rootnbttag = getNewNBTTag();
		}
		if (!valideCompound(comp).booleanValue()) {
			return null;
		}
		Object workingtag = gettoCompount(rootnbttag, comp);
		try {
			Method method = workingtag.getClass().getMethod("get", new Class[] { String.class });
			return method.invoke(workingtag, new Object[] { key }).toString();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static void setInt(NBTCompound comp, String key, Integer i) {
		if (i == null) {
			remove(comp, key);
			return;
		}
		Object rootnbttag = comp.getCompound();
		if (rootnbttag == null) {
			rootnbttag = getNewNBTTag();
		}
		if (!valideCompound(comp).booleanValue()) {
			return;
		}
		Object workingtag = gettoCompount(rootnbttag, comp);
		try {
			Method method = workingtag.getClass().getMethod("setInt", new Class[] { String.class, Integer.TYPE });
			method.invoke(workingtag, new Object[] { key, i });
			comp.setCompound(rootnbttag);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static Integer getInt(NBTCompound comp, String key) {
		Object rootnbttag = comp.getCompound();
		if (rootnbttag == null) {
			rootnbttag = getNewNBTTag();
		}
		if (!valideCompound(comp).booleanValue()) {
			return null;
		}
		Object workingtag = gettoCompount(rootnbttag, comp);
		try {
			Method method = workingtag.getClass().getMethod("getInt", new Class[] { String.class });
			return (Integer) method.invoke(workingtag, new Object[] { key });
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static void setByteArray(NBTCompound comp, String key, byte[] b) {
		if (b == null) {
			remove(comp, key);
			return;
		}
		Object rootnbttag = comp.getCompound();
		if (rootnbttag == null) {
			rootnbttag = getNewNBTTag();
		}
		if (!valideCompound(comp).booleanValue()) {
			return;
		}
		Object workingtag = gettoCompount(rootnbttag, comp);
		try {
			Method method = workingtag.getClass().getMethod("setByteArray", new Class[] { String.class, byte[].class });
			method.invoke(workingtag, new Object[] { key, b });
			comp.setCompound(rootnbttag);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static byte[] getByteArray(NBTCompound comp, String key) {
		Object rootnbttag = comp.getCompound();
		if (rootnbttag == null) {
			rootnbttag = getNewNBTTag();
		}
		if (!valideCompound(comp).booleanValue()) {
			return null;
		}
		Object workingtag = gettoCompount(rootnbttag, comp);
		try {
			Method method = workingtag.getClass().getMethod("getByteArray", new Class[] { String.class });
			return (byte[]) method.invoke(workingtag, new Object[] { key });
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static void setIntArray(NBTCompound comp, String key, int[] i) {
		if (i == null) {
			remove(comp, key);
			return;
		}
		Object rootnbttag = comp.getCompound();
		if (rootnbttag == null) {
			rootnbttag = getNewNBTTag();
		}
		if (!valideCompound(comp).booleanValue()) {
			return;
		}
		Object workingtag = gettoCompount(rootnbttag, comp);
		try {
			Method method = workingtag.getClass().getMethod("setIntArray", new Class[] { String.class, int[].class });
			method.invoke(workingtag, new Object[] { key, i });
			comp.setCompound(rootnbttag);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static int[] getIntArray(NBTCompound comp, String key) {
		Object rootnbttag = comp.getCompound();
		if (rootnbttag == null) {
			rootnbttag = getNewNBTTag();
		}
		if (!valideCompound(comp).booleanValue()) {
			return null;
		}
		Object workingtag = gettoCompount(rootnbttag, comp);
		try {
			Method method = workingtag.getClass().getMethod("getIntArray", new Class[] { String.class });
			return (int[]) method.invoke(workingtag, new Object[] { key });
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static void setFloat(NBTCompound comp, String key, Float f) {
		if (f == null) {
			remove(comp, key);
			return;
		}
		Object rootnbttag = comp.getCompound();
		if (rootnbttag == null) {
			rootnbttag = getNewNBTTag();
		}
		if (!valideCompound(comp).booleanValue()) {
			return;
		}
		Object workingtag = gettoCompount(rootnbttag, comp);
		try {
			Method method = workingtag.getClass().getMethod("setFloat", new Class[] { String.class, Float.TYPE });
			method.invoke(workingtag, new Object[] { key, Float.valueOf(f.floatValue()) });
			comp.setCompound(rootnbttag);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static Float getFloat(NBTCompound comp, String key) {
		Object rootnbttag = comp.getCompound();
		if (rootnbttag == null) {
			rootnbttag = getNewNBTTag();
		}
		if (!valideCompound(comp).booleanValue()) {
			return null;
		}
		Object workingtag = gettoCompount(rootnbttag, comp);
		try {
			Method method = workingtag.getClass().getMethod("getFloat", new Class[] { String.class });
			return (Float) method.invoke(workingtag, new Object[] { key });
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static void setLong(NBTCompound comp, String key, Long f) {
		if (f == null) {
			remove(comp, key);
			return;
		}
		Object rootnbttag = comp.getCompound();
		if (rootnbttag == null) {
			rootnbttag = getNewNBTTag();
		}
		if (!valideCompound(comp).booleanValue()) {
			return;
		}
		Object workingtag = gettoCompount(rootnbttag, comp);
		try {
			Method method = workingtag.getClass().getMethod("setLong", new Class[] { String.class, Long.TYPE });
			method.invoke(workingtag, new Object[] { key, Long.valueOf(f.longValue()) });
			comp.setCompound(rootnbttag);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static Long getLong(NBTCompound comp, String key) {
		Object rootnbttag = comp.getCompound();
		if (rootnbttag == null) {
			rootnbttag = getNewNBTTag();
		}
		if (!valideCompound(comp).booleanValue()) {
			return null;
		}
		Object workingtag = gettoCompount(rootnbttag, comp);
		try {
			Method method = workingtag.getClass().getMethod("getLong", new Class[] { String.class });
			return (Long) method.invoke(workingtag, new Object[] { key });
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static void setShort(NBTCompound comp, String key, Short f) {
		if (f == null) {
			remove(comp, key);
			return;
		}
		Object rootnbttag = comp.getCompound();
		if (rootnbttag == null) {
			rootnbttag = getNewNBTTag();
		}
		if (!valideCompound(comp).booleanValue()) {
			return;
		}
		Object workingtag = gettoCompount(rootnbttag, comp);
		try {
			Method method = workingtag.getClass().getMethod("setShort", new Class[] { String.class, Short.TYPE });
			method.invoke(workingtag, new Object[] { key, Short.valueOf(f.shortValue()) });
			comp.setCompound(rootnbttag);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static Short getShort(NBTCompound comp, String key) {
		Object rootnbttag = comp.getCompound();
		if (rootnbttag == null) {
			rootnbttag = getNewNBTTag();
		}
		if (!valideCompound(comp).booleanValue()) {
			return null;
		}
		Object workingtag = gettoCompount(rootnbttag, comp);
		try {
			Method method = workingtag.getClass().getMethod("getShort", new Class[] { String.class });
			return (Short) method.invoke(workingtag, new Object[] { key });
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static void setByte(NBTCompound comp, String key, Byte f) {
		if (f == null) {
			remove(comp, key);
			return;
		}
		Object rootnbttag = comp.getCompound();
		if (rootnbttag == null) {
			rootnbttag = getNewNBTTag();
		}
		if (!valideCompound(comp).booleanValue()) {
			return;
		}
		Object workingtag = gettoCompount(rootnbttag, comp);
		try {
			Method method = workingtag.getClass().getMethod("setByte", new Class[] { String.class, Byte.TYPE });
			method.invoke(workingtag, new Object[] { key, Byte.valueOf(f.byteValue()) });
			comp.setCompound(rootnbttag);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static Byte getByte(NBTCompound comp, String key) {
		Object rootnbttag = comp.getCompound();
		if (rootnbttag == null) {
			rootnbttag = getNewNBTTag();
		}
		if (!valideCompound(comp).booleanValue()) {
			return null;
		}
		Object workingtag = gettoCompount(rootnbttag, comp);
		try {
			Method method = workingtag.getClass().getMethod("getByte", new Class[] { String.class });
			return (Byte) method.invoke(workingtag, new Object[] { key });
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static void setDouble(NBTCompound comp, String key, Double d) {
		if (d == null) {
			remove(comp, key);
			return;
		}
		Object rootnbttag = comp.getCompound();
		if (rootnbttag == null) {
			rootnbttag = getNewNBTTag();
		}
		if (!valideCompound(comp).booleanValue()) {
			return;
		}
		Object workingtag = gettoCompount(rootnbttag, comp);
		try {
			Method method = workingtag.getClass().getMethod("setDouble", new Class[] { String.class, Double.TYPE });
			method.invoke(workingtag, new Object[] { key, d });
			comp.setCompound(rootnbttag);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static Double getDouble(NBTCompound comp, String key) {
		Object rootnbttag = comp.getCompound();
		if (rootnbttag == null) {
			rootnbttag = getNewNBTTag();
		}
		if (!valideCompound(comp).booleanValue()) {
			return null;
		}
		Object workingtag = gettoCompount(rootnbttag, comp);
		try {
			Method method = workingtag.getClass().getMethod("getDouble", new Class[] { String.class });
			return (Double) method.invoke(workingtag, new Object[] { key });
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static byte getType(NBTCompound comp, String key) {
		Object rootnbttag = comp.getCompound();
		if (rootnbttag == null) {
			rootnbttag = getNewNBTTag();
		}
		if (!valideCompound(comp).booleanValue()) {
			return 0;
		}
		Object workingtag = gettoCompount(rootnbttag, comp);
		try {
			Method method = workingtag.getClass().getMethod(NBTNames.getTypeMethodName(),
					new Class[] { String.class });
			return ((Byte) method.invoke(workingtag, new Object[] { key })).byteValue();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return 0;
	}

	public static void setBoolean(NBTCompound comp, String key, Boolean d) {
		if (d == null) {
			remove(comp, key);
			return;
		}
		Object rootnbttag = comp.getCompound();
		if (rootnbttag == null) {
			rootnbttag = getNewNBTTag();
		}
		if (!valideCompound(comp).booleanValue()) {
			return;
		}
		Object workingtag = gettoCompount(rootnbttag, comp);
		try {
			Method method = workingtag.getClass().getMethod("setBoolean", new Class[] { String.class, Boolean.TYPE });
			method.invoke(workingtag, new Object[] { key, d });
			comp.setCompound(rootnbttag);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static Boolean getBoolean(NBTCompound comp, String key) {
		Object rootnbttag = comp.getCompound();
		if (rootnbttag == null) {
			rootnbttag = getNewNBTTag();
		}
		if (!valideCompound(comp).booleanValue()) {
			return null;
		}
		Object workingtag = gettoCompount(rootnbttag, comp);
		try {
			Method method = workingtag.getClass().getMethod("getBoolean", new Class[] { String.class });
			return (Boolean) method.invoke(workingtag, new Object[] { key });
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static void set(NBTCompound comp, String key, Object val) {
		if (val == null) {
			remove(comp, key);
			return;
		}
		Object rootnbttag = comp.getCompound();
		if (rootnbttag == null) {
			rootnbttag = getNewNBTTag();
		}
		if (!valideCompound(comp).booleanValue()) {
			new Throwable("InvalideCompound").printStackTrace();
			return;
		}
		Object workingtag = gettoCompount(rootnbttag, comp);
		try {
			Method method = workingtag.getClass().getMethod("set", new Class[] { String.class, getNBTBase() });
			method.invoke(workingtag, new Object[] { key, val });
			comp.setCompound(rootnbttag);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static NBTList getList(NBTCompound comp, String key, NBTType type) {
		Object rootnbttag = comp.getCompound();
		if (rootnbttag == null) {
			rootnbttag = getNewNBTTag();
		}
		if (!valideCompound(comp).booleanValue()) {
			return null;
		}
		Object workingtag = gettoCompount(rootnbttag, comp);
		try {
			Method method = workingtag.getClass().getMethod("getList", new Class[] { String.class, Integer.TYPE });
			return new NBTList(comp, key, type,
					method.invoke(workingtag, new Object[] { key, Integer.valueOf(type.getId()) }));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static void setObject(NBTCompound comp, String key, Object value) {
		if (!NBTVersion.hasGson()) {
			return;
		}
		try {
			String json = NBTWrapper.getString(value);
			setString(comp, key, json);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static <T> T getObject(NBTCompound comp, String key, Class<T> type) {
		if (!NBTVersion.hasGson()) {
			return null;
		}
		String json = getString(comp, key);
		if (json == null) {
			return null;
		}
		return (T) NBTWrapper.deserializeJson(json, type);
	}

	public static void remove(NBTCompound comp, String key) {
		Object rootnbttag = comp.getCompound();
		if (rootnbttag == null) {
			rootnbttag = getNewNBTTag();
		}
		if (!valideCompound(comp).booleanValue()) {
			return;
		}
		Object workingtag = gettoCompount(rootnbttag, comp);
		try {
			Method method = workingtag.getClass().getMethod("remove", new Class[] { String.class });
			method.invoke(workingtag, new Object[] { key });
			comp.setCompound(rootnbttag);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static Boolean hasKey(NBTCompound comp, String key) {
		Object rootnbttag = comp.getCompound();
		if (rootnbttag == null) {
			rootnbttag = getNewNBTTag();
		}
		if (!valideCompound(comp).booleanValue()) {
			return null;
		}
		Object workingtag = gettoCompount(rootnbttag, comp);
		try {
			Method method = workingtag.getClass().getMethod("hasKey", new Class[] { String.class });
			return (Boolean) method.invoke(workingtag, new Object[] { key });
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static Set<String> getKeys(NBTCompound comp) {
		Object rootnbttag = comp.getCompound();
		if (rootnbttag == null) {
			rootnbttag = getNewNBTTag();
		}
		if (!valideCompound(comp).booleanValue()) {
			return null;
		}
		Object workingtag = gettoCompount(rootnbttag, comp);
		try {
			Method method = workingtag.getClass().getMethod("c", new Class[0]);
			return (Set<String>) method.invoke(workingtag, new Object[0]);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
