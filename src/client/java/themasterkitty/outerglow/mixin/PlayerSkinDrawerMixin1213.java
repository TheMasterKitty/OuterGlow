package themasterkitty.outerglow.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.PlayerSkinDrawer;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.util.SkinTextures;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import themasterkitty.outerglow.Main;

@Mixin(PlayerSkinDrawer.class)
public class PlayerSkinDrawerMixin1213 {
	@Inject(method = "draw(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/util/Identifier;IIIZZI)V", at = @At(value = "HEAD"))
	private static void draw(DrawContext context, Identifier texture, int x, int y, int size, boolean hatVisible, boolean upsideDown, int color, CallbackInfo ci) {
		if (!Main.enabled) return;
		if (shouldDrawOutline(texture.getPath())) {
			context.fill(x - 1, y - 1, x + size + 1, y + size + 1, Main.color.getRGB());
		}
	}

    @Unique
    private static boolean shouldDrawOutline(String skinPath) {
        if (MinecraftClient.getInstance().world == null) return false;

        return MinecraftClient.getInstance().world.getPlayers().stream()
                .filter(player -> player.isPartVisible(PlayerModelPart.HAT))
                .map(AbstractClientPlayerEntity::getSkinTextures)
                .map(SkinTextures::texture)
                .map(Identifier::getPath)
                .anyMatch(skinPath::equals);
    }
}
