package com.example;

import net.minecraft.world.level.block.Block;

public class MyBlock {
    private final Block block;
    private C3 color;

    public MyBlock(Block block, C3 color) {
        this.block = block;
        this.color = color;
    }

    public Block getBlock() { return block; }
    public C3 getColor() { return color; }
}