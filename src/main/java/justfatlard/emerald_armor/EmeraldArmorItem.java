package justfatlard.emerald_armor;

import eu.pb4.polymer.core.api.item.PolymerItem;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.util.Identifier;
import xyz.nucleoid.packettweaker.PacketContext;

public class EmeraldArmorItem extends Item implements PolymerItem {
	private final Identifier modelId;
	private final Item polymerItem;
	private final EquipmentType equipmentType;
	private final int defense;

	public EmeraldArmorItem(EquipmentType type, int defense, Item.Settings settings, String modelName, Item polymerItem) {
		super(settings.equippable(type.getEquipmentSlot()));
		this.modelId = Identifier.of(Main.MOD_ID, modelName);
		this.polymerItem = polymerItem;
		this.equipmentType = type;
		this.defense = defense;
	}

	@Override
	public Item getPolymerItem(ItemStack itemStack, PacketContext context) {
		return this.polymerItem;
	}

	@Override
	public Identifier getPolymerItemModel(ItemStack itemStack, PacketContext context) {
		return this.modelId;
	}
}
