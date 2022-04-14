package me.SuperRonanCraft.AdvancedCustomItemAPI.references.item.NBT;

import org.bukkit.inventory.ItemStack;

public class NBTItem extends NBTCompound {
	private ItemStack bukkitItem;

	public NBTItem(ItemStack item) {
		super(null, null);
		this.bukkitItem = item.clone();
	}

	protected Object getCompound() {
		return NBTReflection.getItemRootNBTTagCompound(NBTReflection.getNMSItemStack(this.bukkitItem));
	}

	protected void setCompound(Object tag) {
		this.bukkitItem = NBTReflection
				.getBukkitItemStack(NBTReflection.setNBTTag(tag, NBTReflection.getNMSItemStack(this.bukkitItem)));
	}

	public ItemStack getItem() {
		return this.bukkitItem;
	}

	protected void setItem(ItemStack item) {
		this.bukkitItem = item;
	}
}
