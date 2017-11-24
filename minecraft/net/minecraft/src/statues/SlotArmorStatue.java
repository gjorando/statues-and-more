/**
 * Slot class for the slotArmorStatue
 */

package net.minecraft.src.statues;

import net.minecraft.src.*;
import net.minecraft.src.Block;
import net.minecraft.src.ContainerPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemArmor;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;

public class SlotArmorStatue extends Slot
{
    private final int armorType; //The armor type that can be placed on that slot, it uses the same values of armorType field on ItemArmor.

    SlotArmorStatue(IInventory par2IInventory, int par3, int par4, int par5, int par6)
    {
        super(par2IInventory, par3, par4, par5);
        this.armorType = par6;
    }

    /**
     * Returns the maximum stack size for a given slot (usually the same as getInventoryStackLimit(), but 1 in the case
     * of armor slots)
     */
    public int getSlotStackLimit()
    {
        return 1;
    }

    /**
     * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
     */
    public boolean isItemValid(ItemStack par1ItemStack)
    {
        return par1ItemStack.getItem() instanceof ItemArmor ? ((ItemArmor)par1ItemStack.getItem()).armorType == this.armorType : (par1ItemStack.getItem().shiftedIndex == Block.pumpkin.blockID ? this.armorType == 0 : false);
    }

    /**
     * Returns the icon index on items.png that is used as background image of the slot.
     */
    public int getBackgroundIconIndex()
    {
        return 15 + this.armorType * 16;
    }
}