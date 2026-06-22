package com.example;

// Source - https://stackoverflow.com/a/61343807
// Posted by Vlad  Badalyan, modified by community. See post 'Timeline' for change history
// Retrieved 2026-06-21, License - CC BY-SA 4.0

public class CIEDE2000 {

/**
 * Calculate the colour difference value between two colours in lab space.
 * @param L1 first colour's L component
 * @param a1 first colour's a component
 * @param b1 first colour's b component
 * @param L2 second colour's L component
 * @param a2 second colour's a component
 * @param b2 second colour's b component
 * @return the CIE 2000 colour difference
 */
public static double calculateDeltaE(double L1, double a1, double b1, double L2, double a2, double b2) {
    double Lmean = (L1 + L2) / 2.0;
    double C1 =  Math.sqrt(a1*a1 + b1*b1);
    double C2 =  Math.sqrt(a2*a2 + b2*b2);
    double Cmean = (C1 + C2) / 2.0;

    double G =  ( 1 - Math.sqrt( Math.pow(Cmean, 7) / (Math.pow(Cmean, 7) + Math.pow(25, 7)) ) ) / 2; //ok
    double a1prime = a1 * (1 + G);
    double a2prime = a2 * (1 + G);

    double C1prime =  Math.sqrt(a1prime*a1prime + b1*b1);
    double C2prime =  Math.sqrt(a2prime*a2prime + b2*b2);
    double Cmeanprime = (C1prime + C2prime) / 2;

    double h1prime =  Math.atan2(b1, a1prime) + 2*Math.PI * (Math.atan2(b1, a1prime)<0 ? 1 : 0);
    double h2prime =  Math.atan2(b2, a2prime) + 2*Math.PI * (Math.atan2(b2, a2prime)<0 ? 1 : 0);
    double Hmeanprime =  ((Math.abs(h1prime - h2prime) > Math.PI) ? (h1prime + h2prime + 2*Math.PI) / 2 : (h1prime + h2prime) / 2);

    double T =  1.0 - 0.17 * Math.cos(Hmeanprime - Math.PI/6.0) + 0.24 * Math.cos(2*Hmeanprime) + 0.32 * Math.cos(3*Hmeanprime + Math.PI/30) - 0.2 * Math.cos(4*Hmeanprime - 21*Math.PI/60);

    double deltahprime =  ((Math.abs(h1prime - h2prime) <= Math.PI) ? h2prime - h1prime : (h2prime <= h1prime) ? h2prime - h1prime + 2*Math.PI : h2prime - h1prime - 2*Math.PI);

    double deltaLprime = L2 - L1;
    double deltaCprime = C2prime - C1prime;
    double deltaHprime =  2.0 * Math.sqrt(C1prime*C2prime) * Math.sin(deltahprime / 2.0);
    double SL =  1.0 + ( (0.015*(Lmean - 50)*(Lmean - 50)) / (Math.sqrt( 20 + (Lmean - 50)*(Lmean - 50) )) );
    double SC =  1.0 + 0.045 * Cmeanprime;
    double SH =  1.0 + 0.015 * Cmeanprime * T;

    double deltaTheta =  (30 * Math.PI / 180) * Math.exp(-((180/Math.PI*Hmeanprime-275)/25)*((180/Math.PI*Hmeanprime-275)/25));
    double RC =  (2 * Math.sqrt(Math.pow(Cmeanprime, 7) / (Math.pow(Cmeanprime, 7) + Math.pow(25, 7))));
    double RT =  (-RC * Math.sin(2 * deltaTheta));

    double KL = 1;
    double KC = 1;
    double KH = 1;

    double deltaE = Math.sqrt(
            ((deltaLprime/(KL*SL)) * (deltaLprime/(KL*SL))) +
            ((deltaCprime/(KC*SC)) * (deltaCprime/(KC*SC))) +
            ((deltaHprime/(KH*SH)) * (deltaHprime/(KH*SH))) +
            (RT * (deltaCprime/(KC*SC)) * (deltaHprime/(KH*SH)))
            );

    return deltaE;
}
}
