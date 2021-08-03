package net.coderbot.iris.gui.element;

import net.coderbot.iris.Iris;
import net.coderbot.iris.gui.GuiUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.util.math.MatrixStack;


public abstract class ShaderScreenEntryListWidget<E extends AlwaysSelectedEntryListWidget.Entry<E>> extends AlwaysSelectedEntryListWidget<E> implements Element {
    protected int scrollbarFade = 0;
    protected boolean hovered = false;

    public ShaderScreenEntryListWidget(MinecraftClient minecraftClient, int width, int height, int top, int bottom, int left, int right, int itemHeight) {
        super(minecraftClient, width, height, top, bottom, itemHeight);
        this.left = left;
        this.right = right;
    }

    @Override
    public int getRowWidth() {
        return width - 6;
    }

    public void tick() {
        if (hovered) {
            if (scrollbarFade < 3) scrollbarFade++;
        } else if (scrollbarFade > 0) scrollbarFade--;
    }

    @Override
    protected final int getScrollbarPositionX() {
        return 32767; // Make the vanilla scrollbar not visible
    }

    protected int getScrollbarXOffset() {
        return -2;
    }

    protected int getScrollbarTopMargin() {
        return 2;
    }

    protected int getScrollbarBottomMargin() {
        return 2;
    }

    public void select(int entry) {
		setSelected(this.getEntry(entry));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);
        GuiUtil.drawCompactScrollBar(this.left + this.width + getScrollbarXOffset(), this.top + getScrollbarTopMargin(), this.bottom - getScrollbarBottomMargin(), this.getMaxScroll(), this.getScrollAmount(), this.getMaxPosition(), Math.max(0, Math.min(3, this.scrollbarFade + (hovered ? delta : -delta))) / 3);
        this.hovered = this.isMouseOver(mouseX, mouseY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        this.setScrollAmount(this.getScrollAmount() - amount * (double)this.itemHeight / 2.0D * (Iris.getIrisConfig().getScrollSpeed() / 100D));
        return true;
    }
}
