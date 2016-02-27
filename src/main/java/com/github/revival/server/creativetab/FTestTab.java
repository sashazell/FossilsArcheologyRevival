package com.github.revival.server.creativetab;

import com.github.revival.server.block.FABlockRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class FTestTab extends CreativeTabs {

    public FTestTab(String title) {
        super(title);
    }

    @Override
    public Item getTabIconItem() {
        return Item.getItemFromBlock(FABlockRegistry.blockTimeMachine);
    }

}