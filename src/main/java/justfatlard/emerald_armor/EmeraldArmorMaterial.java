package justfatlard.emerald_armor;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public class EmeraldArmorMaterial implements ArmorMaterial {
	private final static int[] BASE_DURABILITY = new int[]{6, 7, 8, 5};
	private final static int[] PROTECTION_AMOUNTS = new int[]{3, 6, 8, 3};
	private final int durabilityMultiplier = 72;

	@Override
	public int getDurability(EquipmentSlot slot) {
		return BASE_DURABILITY[slot.getEntitySlotId()] * durabilityMultiplier;
	}

	@Override
	public Ingredient getRepairIngredient() {
		return Ingredient.ofItems(Items.EMERALD_BLOCK);
	}

	@Override
	public int getEnchantability() {
		return 22;
	}

	@Override
	public int getProtectionAmount(EquipmentSlot slot) {
		return PROTECTION_AMOUNTS[slot.getEntitySlotId()];
	}

	@Override
	public String getName() {
		return "emerald";
	}

	@Override
	public SoundEvent getEquipSound() {
		return SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND;
	}

	@Override
	public float getToughness() {
		return 1;
	}
}
