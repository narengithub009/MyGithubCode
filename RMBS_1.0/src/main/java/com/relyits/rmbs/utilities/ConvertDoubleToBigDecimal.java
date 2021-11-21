package com.relyits.rmbs.utilities;


public class ConvertDoubleToBigDecimal {

	private static final int DECIMAL_PLACES = 2;
	public static java.math.BigDecimal twoDecimalPlaces(final double d) {
		java.math.BigDecimal bd= new java.math.BigDecimal(d).setScale(DECIMAL_PLACES, 
	        java.math.RoundingMode.HALF_UP);
	    return bd;
	}
}
