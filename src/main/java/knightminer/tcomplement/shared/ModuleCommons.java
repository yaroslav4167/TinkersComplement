package knightminer.tcomplement.shared;

import com.google.common.eventbus.Subscribe;

import knightminer.tcomplement.common.CommonProxy;
import knightminer.tcomplement.common.Config;
import knightminer.tcomplement.common.PulseBase;
import knightminer.tcomplement.library.TCompRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import slimeknights.mantle.item.ItemMetaDynamic;
import slimeknights.mantle.pulsar.pulse.Pulse;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.smeltery.item.CastCustom;

@Pulse(id = ModuleCommons.pulseID, description = "Core feature for all the modules", forced = true)
public class ModuleCommons extends PulseBase {
	public static final String pulseID = "ModuleCommons";

	@SidedProxy(clientSide = "knightminer.tcomplement.shared.CommonsClientProxy", serverSide = "knightminer.tcomplement.common.CommonProxy")
	public static CommonProxy proxy;

	public static ItemMetaDynamic materials;

	public static CastCustom cast, castClay;
	public static ItemStack stoneBucket;
	public static ItemStack castBucket, castBucketClay;

	@Subscribe
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit();
	}

	@SubscribeEvent
	public void registerItems(Register<Item> event) {
		IForgeRegistry<Item> r = event.getRegistry();

		// materials
		materials = registerItem(r, new ItemMetaDynamic(), "materials");
		materials.setCreativeTab(TCompRegistry.tabGeneral);

		// custom casts
		cast = registerItem(r, new CastCustom(), "cast");
		cast.setCreativeTab(TCompRegistry.tabGeneral);

		castClay = registerItem(r, new CastCustom(), "cast_clay");
		castClay.setCreativeTab(TCompRegistry.tabGeneral);

		if(Config.general.bucketCast) {
			stoneBucket = materials.addMeta(0, "stone_bucket");
			castBucket = cast.addMeta(0, "bucket", Material.VALUE_Ingot);
			castBucketClay = castClay.addMeta(0, "bucket", Material.VALUE_Ingot);
		}
	}

	@Subscribe
	public void init(FMLInitializationEvent event) {
		proxy.init();
	}
}
