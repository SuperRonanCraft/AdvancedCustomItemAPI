package me.SuperRonanCraft.AdvancedCustomItemAPI.references.item.NBT;

import java.util.HashSet;
import java.util.Set;

public class NBTListCompound {
	private NBTList owner;
	private Object compound;

	protected NBTListCompound(NBTList parent, Object obj) {
		this.owner = parent;
		this.compound = obj;
	}

	public void setString(String key, String val) {
		if (val == null) {
			remove(key);
			return;
		}
		try {
			this.compound.getClass().getMethod("setString", new Class[] { String.class, String.class })
					.invoke(this.compound, new Object[] { key, val });
			this.owner.save();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void setInteger(String key, int val) {
		try {
			this.compound.getClass().getMethod("setInt", new Class[] { String.class, Integer.TYPE })
					.invoke(this.compound, new Object[] { key, Integer.valueOf(val) });
			this.owner.save();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public int getInteger(String key) {
		try {
			return ((Integer) this.compound.getClass().getMethod("getInt", new Class[] { String.class })
					.invoke(this.compound, new Object[] { key })).intValue();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return 0;
	}

	public void setDouble(String key, double val) {
		try {
			this.compound.getClass().getMethod("setDouble", new Class[] { String.class, Double.TYPE })
					.invoke(this.compound, new Object[] { key, Double.valueOf(val) });
			this.owner.save();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public double getDouble(String key) {
		try {
			return ((Double) this.compound.getClass().getMethod("getDouble", new Class[] { String.class })
					.invoke(this.compound, new Object[] { key })).doubleValue();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return 0.0D;
	}

	public String getString(String key) {
		try {
			return (String) this.compound.getClass().getMethod("getString", new Class[] { String.class })
					.invoke(this.compound, new Object[] { key });
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}

	public boolean hasKey(String key) {
		try {
			return ((Boolean) this.compound.getClass().getMethod("hasKey", new Class[] { String.class })
					.invoke(this.compound, new Object[] { key })).booleanValue();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public Set<String> getKeys() {
		try {
			return (Set<String>) this.compound.getClass().getMethod("c", new Class[0]).invoke(this.compound, new Object[0]);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new HashSet<String>();
	}

	public void remove(String key) {
		try {
			this.compound.getClass().getMethod("remove", new Class[] { String.class }).invoke(this.compound,
					new Object[] { key });
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
