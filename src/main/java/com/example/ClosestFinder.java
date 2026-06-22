package com.example;

import java.util.List;

public class ClosestFinder {
    public static MyBlock findClosestMyBlock(C3 color, List<MyBlock> myBlocks) {
        MyBlock block = myBlocks.getFirst();
        double minDistance = CIEDE2000.calculateDeltaE(
            color.getL(), 
            color.getA(), 
            color.getB(), 
            block.getColor().getL(), 
            block.getColor().getA(), 
            block.getColor().getB()
        );

        for(MyBlock myBlock : myBlocks) {
            double l1 = color.getL();
            double a1 = color.getA();
            double b1 = color.getB();
            double l2 = myBlock.getColor().getL();
            double a2 = myBlock.getColor().getA();
            double b2 = myBlock.getColor().getB();
            if (CIEDE2000.calculateDeltaE(l1, a1, b1, l2, a2, b2) < minDistance) {
                minDistance = CIEDE2000.calculateDeltaE(l1, a1, b1, l2, a2, b2);
                block = myBlock;
            }
        }

        return block;
    }
}
