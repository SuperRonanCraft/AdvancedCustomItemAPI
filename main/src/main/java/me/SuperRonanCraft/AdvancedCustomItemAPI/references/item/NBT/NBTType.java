package me.SuperRonanCraft.AdvancedCustomItemAPI.references.item.NBT;

public enum NBTType {
	NBTTagEnd(0), NBTTagByte(1), NBTTagShort(2), NBTTagInt(3), NBTTagLong(4), NBTTagFloat(5), NBTTagDouble(
			6), NBTTagByteArray(7), NBTTagIntArray(11), NBTTagString(8), NBTTagList(9), NBTTagCompound(10);

	private final int id;

	private NBTType(int i) {
		this.id = i;
	}

	public int getId() {
		return this.id;
	}

	public static NBTType valueOf(int id) {
		NBTType[] arrayOfNBTType;
		int j = (arrayOfNBTType = values()).length;
		for (int i = 0; i < j; i++) {
			NBTType t = arrayOfNBTType[i];
			if (t.getId() == id) {
				return t;
			}
		}
		return NBTTagEnd;
	}
}
