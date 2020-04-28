package justfatlard.emerald_armor;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public class EmeraldArmorMaterial implements ArmorMaterial {
	private final static int[] durability = new int[]{420, 580, 550, 480};
	private final static int[] protection = new int[]{4, 7, 9, 4};

	@Override
	public int getDurability(EquipmentSlot slot){ return durability[slot.getEntitySlotId()]; }

	@Override
	public Ingredient getRepairIngredient(){ return Ingredient.ofItems(Items.EMERALD_BLOCK); }

	@Override
	public int getEnchantability(){ return 22; }

	@Override
	public int getProtectionAmount(EquipmentSlot slot){ return protection[slot.getEntitySlotId()]; }

	@Override
	public String getName(){ return "emerald"; }

	@Override
	public SoundEvent getEquipSound(){ return SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND; }

	@Override
	public float getToughness(){ return 1; }
}
