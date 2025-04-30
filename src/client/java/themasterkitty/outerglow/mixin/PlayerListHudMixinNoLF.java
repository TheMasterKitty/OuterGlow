package themasterkitty.outerglow.mixin;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import themasterkitty.outerglow.Main;

@Mixin(PlayerListHud.class)
public class PlayerListHudMixinNoLF {
	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/Text;III)I"))
	private int draw(DrawContext instance, TextRenderer textRenderer, Text text, int x, int y, int color) {
		if (!Main.enabled) return instance.drawTextWithShadow(textRenderer, text, x, y, color);
		return instance.drawTextWithShadow(textRenderer, text, x + 1, y, color);
	}
}
