package dev.terminalmc.autoreconnectrf.mixin;

import dev.terminalmc.autoreconnectrf.util.ScreenMixinUtil.DisconnectedScreenTransfer;
import net.minecraft.client.gui.screens.DisconnectedScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientCommonPacketListenerImpl;
import net.minecraft.network.DisconnectionDetails;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ClientCommonPacketListenerImpl.class)
public abstract class MixinClientCommonPacketListenerImpl {
    @Shadow
    protected boolean isTransferring;

    @Redirect(
            at = @At(
                    value = "NEW",
                    target = "Lnet/minecraft/client/gui/screens/DisconnectedScreen;"
            ),
            method = "createDisconnectScreen"
    )
    private DisconnectedScreen createDisconnectScreen(Screen $$0, Component $$1, DisconnectionDetails $$2) {
        DisconnectedScreen screen = new DisconnectedScreen($$0, $$1, $$2);
        ((DisconnectedScreenTransfer) screen).setAutoReconnect$transferring(this.isTransferring);
        return screen;
    }
}
