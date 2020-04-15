package justfatlard.emerald_armor;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemGroup;

public class EmeraldArmorItem extends ArmorItem {
	public EmeraldArmorItem(EquipmentSlot slot) {
		super(Main.EMERALD_ARMOR_MATERIAL, slot, new Settings().maxCount(1).group(ItemGroup.COMBAT));
	}
}
