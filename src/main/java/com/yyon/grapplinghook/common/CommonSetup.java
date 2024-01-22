package com.yyon.grapplinghook.common;

import com.yyon.grapplinghook.blocks.modifierblock.BlockGrappleModifier;
import com.yyon.grapplinghook.blocks.modifierblock.TileEntityGrappleModifier;
import com.yyon.grapplinghook.client.ClientProxy;
import com.yyon.grapplinghook.enchantments.DoublejumpEnchantment;
import com.yyon.grapplinghook.enchantments.SlidingEnchantment;
import com.yyon.grapplinghook.enchantments.WallrunEnchantment;
import com.yyon.grapplinghook.entities.grapplehook.GrapplehookEntity;
import com.yyon.grapplinghook.grapplemod;
import com.yyon.grapplinghook.items.EnderStaffItem;
import com.yyon.grapplinghook.items.ForcefieldItem;
import com.yyon.grapplinghook.items.GrapplehookItem;
import com.yyon.grapplinghook.items.LongFallBoots;
import com.yyon.grapplinghook.items.upgrades.BaseUpgradeItem;
import com.yyon.grapplinghook.items.upgrades.DoubleUpgradeItem;
import com.yyon.grapplinghook.items.upgrades.ForcefieldUpgradeItem;
import com.yyon.grapplinghook.items.upgrades.LimitsUpgradeItem;
import com.yyon.grapplinghook.items.upgrades.MagnetUpgradeItem;
import com.yyon.grapplinghook.items.upgrades.MotorUpgradeItem;
import com.yyon.grapplinghook.items.upgrades.RocketUpgradeItem;
import com.yyon.grapplinghook.items.upgrades.RopeUpgradeItem;
import com.yyon.grapplinghook.items.upgrades.StaffUpgradeItem;
import com.yyon.grapplinghook.items.upgrades.SwingUpgradeItem;
import com.yyon.grapplinghook.items.upgrades.ThrowUpgradeItem;
import com.yyon.grapplinghook.network.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Optional;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class CommonSetup {

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "grapplemod");
	public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, "grapplemod");
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, "grapplemod");
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, "grapplemod");
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, "grapplemod");

    public static RegistryObject<GrapplehookItem> grapplingHookItem = ITEMS.register("grapplinghook", GrapplehookItem::new);
    public static RegistryObject<EnderStaffItem> enderStaffItem = ITEMS.register("launcheritem", EnderStaffItem::new);
    public static RegistryObject<ForcefieldItem> forcefieldItem = ITEMS.register("repeller", ForcefieldItem::new);

    public static RegistryObject<BaseUpgradeItem> baseUpgradeItem = ITEMS.register("baseupgradeitem", BaseUpgradeItem::new);
    public static RegistryObject<DoubleUpgradeItem> doubleUpgradeItem = ITEMS.register("doubleupgradeitem", DoubleUpgradeItem::new);
    public static RegistryObject<ForcefieldUpgradeItem> forcefieldUpgradeItem = ITEMS.register("forcefieldupgradeitem", ForcefieldUpgradeItem::new);
    public static RegistryObject<MagnetUpgradeItem> magnetUpgradeItem = ITEMS.register("magnetupgradeitem", MagnetUpgradeItem::new);
    public static RegistryObject<MotorUpgradeItem> motorUpgradeItem = ITEMS.register("motorupgradeitem", MotorUpgradeItem::new);
    public static RegistryObject<RopeUpgradeItem> ropeUpgradeItem = ITEMS.register("ropeupgradeitem", RopeUpgradeItem::new);
    public static RegistryObject<StaffUpgradeItem> staffUpgradeItem = ITEMS.register("staffupgradeitem", StaffUpgradeItem::new);
    public static RegistryObject<SwingUpgradeItem> swingUpgradeItem = ITEMS.register("swingupgradeitem", SwingUpgradeItem::new);
    public static RegistryObject<ThrowUpgradeItem> throwUpgradeItem = ITEMS.register("throwupgradeitem", ThrowUpgradeItem::new);
    public static RegistryObject<LimitsUpgradeItem> limitsUpgradeItem = ITEMS.register("limitsupgradeitem", LimitsUpgradeItem::new);
    public static RegistryObject<RocketUpgradeItem> rocketUpgradeItem = ITEMS.register("rocketupgradeitem", RocketUpgradeItem::new);

    public static RegistryObject<Item> longFallBootsItem = ITEMS.register("longfallboots", ()->new LongFallBoots(ArmorMaterials.DIAMOND, 3));
    
    public static RegistryObject<WallrunEnchantment> wallrunEnchantment = ENCHANTMENTS.register("wallrunenchantment", WallrunEnchantment::new);
    public static RegistryObject<DoublejumpEnchantment> doubleJumpEnchantment = ENCHANTMENTS.register("doublejumpenchantment", DoublejumpEnchantment::new);
    public static RegistryObject<SlidingEnchantment> slidingEnchantment = ENCHANTMENTS.register("slidingenchantment", SlidingEnchantment::new);

	public static SimpleChannel network;    // used to transmit your network messages
	public static final ResourceLocation simpleChannelRL = new ResourceLocation("grapplemod", "channel");

	public static RegistryObject<Block> grappleModifierBlock = BLOCKS.register("block_grapple_modifier", BlockGrappleModifier::new);
	public static RegistryObject<BlockItem> grappleModifierBlockItem = ITEMS.register("block_grapple_modifier", ()->new BlockItem(grappleModifierBlock.get(),new Item.Properties().stacksTo(64)));
	

	
	public static CommonEventHandlers eventHandlers = new CommonEventHandlers();;

	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
		network = ChannelBuilder.named(simpleChannelRL)
			.networkProtocolVersion(1)
			.acceptedVersions((status, version) -> true)
			.simpleChannel()
			.messageBuilder(PlayerMovementMessage.class, NetworkDirection.PLAY_TO_SERVER)
			.encoder(PlayerMovementMessage::encode)
			.decoder(PlayerMovementMessage::new)
			.consumerMainThread(BaseMessageServer::onMessageReceived)
			.add()
			.messageBuilder(GrappleEndMessage.class, NetworkDirection.PLAY_TO_SERVER)
			.encoder(GrappleEndMessage::encode)
			.decoder(GrappleEndMessage::new)
			.consumerMainThread(GrappleEndMessage::onMessageReceived)
			.add()
			.messageBuilder(GrappleModifierMessage.class, NetworkDirection.PLAY_TO_SERVER)
			.encoder(GrappleModifierMessage::encode)
			.decoder(GrappleModifierMessage::new)
			.consumerMainThread(GrappleModifierMessage::onMessageReceived)
			.add()
			.messageBuilder(KeypressMessage.class, NetworkDirection.PLAY_TO_SERVER)
			.encoder(KeypressMessage::encode)
			.decoder(KeypressMessage::new)
			.consumerMainThread(KeypressMessage::onMessageReceived)
			.add()
			.messageBuilder(GrappleAttachMessage.class, NetworkDirection.PLAY_TO_CLIENT)
			.encoder(GrappleAttachMessage::encode)
			.decoder(GrappleAttachMessage::new)
			.consumerMainThread(GrappleAttachMessage::onMessageReceived)
			.add()
			.messageBuilder(GrappleDetachMessage.class, NetworkDirection.PLAY_TO_CLIENT)
			.encoder(GrappleDetachMessage::encode)
			.decoder(GrappleDetachMessage::new)
			.consumerMainThread(GrappleDetachMessage::onMessageReceived)
			.add()
			.messageBuilder(DetachSingleHookMessage.class, NetworkDirection.PLAY_TO_CLIENT)
			.encoder(DetachSingleHookMessage::encode)
			.decoder(DetachSingleHookMessage::new)
			.consumerMainThread(DetachSingleHookMessage::onMessageReceived)
			.add()
			.messageBuilder(GrappleAttachPosMessage.class, NetworkDirection.PLAY_TO_CLIENT)
			.encoder(GrappleAttachPosMessage::encode)
			.decoder(GrappleAttachPosMessage::new)
			.consumerMainThread(GrappleAttachPosMessage::onMessageReceived)
			.add()
			.messageBuilder(SegmentMessage.class, NetworkDirection.PLAY_TO_CLIENT)
			.encoder(SegmentMessage::encode)
			.decoder(SegmentMessage::new)
			.consumerMainThread(SegmentMessage::onMessageReceived)
			.add()
			.messageBuilder(LoggedInMessage.class, NetworkDirection.PLAY_TO_CLIENT)
			.encoder(LoggedInMessage::encode)
			.decoder(LoggedInMessage::new)
			.consumerMainThread(LoggedInMessage::onMessageReceived)
			.add();
	}
	


	public static RegistryObject<BlockEntityType<TileEntityGrappleModifier>> grappleModifierTileEntityType = BLOCK_ENTITY_TYPES.register("block_grapple_modifier",()->BlockEntityType.Builder.of(TileEntityGrappleModifier::new, grappleModifierBlock.get()).build(null));

	public static RegistryObject<EntityType<GrapplehookEntity>> grapplehookEntityType = ENTITY_TYPES.register("grapplehook", ()->EntityType.Builder.<GrapplehookEntity>of(GrapplehookEntity::new, MobCategory.MISC)
			.sized(0.25F, 0.25F)
			.build("grapplemod:grapplehook"));



	public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, grapplemod.MODID);
	public static final RegistryObject<CreativeModeTab> GRAPPLE_TAB = TABS.register("grapplemod", () -> CreativeModeTab.builder().displayItems(
			(itemDisplayParameters,output)-> {
				ITEMS.getEntries().forEach((registryObject)-> output.accept(new ItemStack(registryObject.get()))
				);
				ClientProxy.proxy.fillGrappleVariants(output);
			}).icon(()->new ItemStack(grapplingHookItem.get())).title(Component.translatable("itemGroup.tabGrapplemod")).build());


//	public static CreativeModeTab tabGrapplemod;
//	@SubscribeEvent
//	public static void registerTabs(CreativeModeTabEvent.Register event)
//	{
//		tabGrapplemod = event.registerCreativeModeTab(new ResourceLocation(grapplemod.MODID, "grapplemod"),builder -> builder
//				.icon(() -> new ItemStack(grapplingHookItem.get()))
//				.title(Component.translatable("tabs.grapplemod.main_tab"))
//				.displayItems((featureFlags, output) -> {
//					ITEMS.getEntries().stream().map(RegistryObject::get).forEach(output::accept);
//					if(ClientProxyInterface.proxy != null) {
//						ClientProxyInterface.proxy.fillGrappleVariants(output);
//					}
//					LongFallBoots.addToTab(output);
//				})
//		);
//	}
	

}
