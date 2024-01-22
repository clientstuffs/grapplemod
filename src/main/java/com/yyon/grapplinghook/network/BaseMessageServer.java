package com.yyon.grapplinghook.network;

import com.yyon.grapplinghook.grapplemod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.fml.LogicalSide;

public abstract class BaseMessageServer {
	public BaseMessageServer(FriendlyByteBuf buf) {
		this.decode(buf);
	}
	
	public BaseMessageServer() {
	}
	
	public abstract void decode(FriendlyByteBuf buf);
	
	public abstract void encode(FriendlyByteBuf buf);

    public abstract void processMessage(CustomPayloadEvent.Context ctx);
    
    public void onMessageReceived(CustomPayloadEvent.Context ctx) {
        LogicalSide sideReceived = ctx.getDirection().getReceptionSide();
        if (sideReceived != LogicalSide.SERVER) {
			grapplemod.LOGGER.warn("message received on wrong side:" + ctx.getDirection().getReceptionSide());
			return;
        }
        
        ctx.setPacketHandled(true);
        
        final ServerPlayer sendingPlayer = ctx.getSender();
        if (sendingPlayer == null) {
        	grapplemod.LOGGER.warn("EntityPlayerMP was null when message was received");
        }

        ctx.enqueueWork(() -> processMessage(ctx));
    }
}
