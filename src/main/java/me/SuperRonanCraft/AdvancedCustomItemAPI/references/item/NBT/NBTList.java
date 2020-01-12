package me.SuperRonanCraft.AdvancedCustomItemAPI.references.item.NBT;

import java.lang.reflect.Method;

public class NBTList {
	private String listname;
	private NBTCompound parent;
	private NBTType type;
	private Object listobject;

	protected NBTList(NBTCompound owner, String name, NBTType type, Object list) {
		this.parent = owner;
		this.listname = name;
		this.type = type;
		this.listobject = list;
		if ((type != NBTType.NBTTagString) && (type != NBTType.NBTTagCompound)) {
			System.err.println("List types != String/Compound are currently not implemented!");
		}
	}

	protected void save() {
		this.parent.set(this.listname, this.listobject);
	}

	public NBTListCompound addCompound() {
		if (this.type != NBTType.NBTTagCompound) {
			new Throwable("Using Compound method on a non Compound list!").printStackTrace();
			return null;
		}
		try {
			Method m = this.listobject.getClass().getMethod("add", new Class[] { NBTReflection.getNBTBase() });
			Object comp = NBTReflection.getNBTTagCompound().newInstance();
			m.invoke(this.listobject, new Object[] { comp });
			return new NBTListCompound(this, comp);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public NBTListCompound getCompound(int id) {
		if (this.type != NBTType.NBTTagCompound) {
			new Throwable("Using Compound method on a non Compound list!").printStackTrace();
			return null;
		}
		try {
			Method m = this.listobject.getClass().getMethod("get", new Class[] { Integer.TYPE });
			Object comp = m.invoke(this.listobject, new Object[] { Integer.valueOf(id) });
			return new NBTListCompound(this, comp);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public String getString(int i) {
		if (this.type != NBTType.NBTTagString) {
			new Throwable("Using String method on a non String list!").printStackTrace();
			return null;
		}
		try {
			Method m = this.listobject.getClass().getMethod("getString", new Class[] { Integer.TYPE });
			return (String) m.invoke(this.listobject, new Object[] { Integer.valueOf(i) });
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public void addString(String s) {
		if (this.type != NBTType.NBTTagString) {
			new Throwable("Using String method on a non String list!").printStackTrace();
			return;
		}
		try {
			Method m = this.listobject.getClass().getMethod("add", new Class[] { NBTReflection.getNBTBase() });
			m.invoke(this.listobject, new Object[] { NBTReflection.getNBTTagString()
					.getConstructor(new Class[] { String.class }).newInstance(new Object[] { s }) });
			save();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void setString(int i, String s) {
		if (this.type != NBTType.NBTTagString) {
			new Throwable("Using String method on a non String list!").printStackTrace();
			return;
		}
		try {
			Method m = this.listobject.getClass().getMethod("a",
					new Class[] { Integer.TYPE, NBTReflection.getNBTBase() });
			m.invoke(this.listobject, new Object[] { Integer.valueOf(i), NBTReflection.getNBTTagString()
					.getConstructor(new Class[] { String.class }).newInstance(new Object[] { s }) });
			save();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void remove(int i) {
		try {
			Method m = this.listobject.getClass().getMethod(NBTNames.getremoveMethodName(),
					new Class[] { Integer.TYPE });
			m.invoke(this.listobject, new Object[] { Integer.valueOf(i) });
			save();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public int size() {
		try {
			Method m = this.listobject.getClass().getMethod("size", new Class[0]);
			return ((Integer) m.invoke(this.listobject, new Object[0])).intValue();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return -1;
	}

	public NBTType getType() {
		return this.type;
	}
}
