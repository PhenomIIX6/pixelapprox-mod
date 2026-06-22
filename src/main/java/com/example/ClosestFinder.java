package com.example;

import java.util.List;

public class ClosestFinder {
    public static MyBlock findClosestMyBlock(C3 color, List<MyBlock> myBlocks) {
        MyBlock block = myBlocks.getFirst();
        double minDistance = color.diff(block.getColor());

        for(MyBlock myBlock : myBlocks) {
            if (color.diff(myBlock.getColor()) < minDistance) {
                minDistance = color.diff(myBlock.getColor());
                block = myBlock;
            }
        }

        return block;
    }
}