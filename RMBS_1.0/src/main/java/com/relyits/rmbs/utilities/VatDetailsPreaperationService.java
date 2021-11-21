package com.relyits.rmbs.utilities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.relyits.rmbs.beans.sales.VatBean;

public class VatDetailsPreaperationService {

	
	@SuppressWarnings("rawtypes")
	private static Map vatMap=null;
	private static BigDecimal vat_percent=null;
	private static BigDecimal vat_applied_amount=null;
	private static BigDecimal vat_amount=null;
	private static BigDecimal[] vat_Array = null;
	private static BigDecimal divisor=null;
	private static BigDecimal dicounted_amount=null;
	private static ArrayList<String> vatDetails=null;
	
		
	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	public static ArrayList prepareVatMap(List<VatBean> vatBeanList){
		
		vatMap=new HashMap();
		vat_percent=new BigDecimal(0);
		vat_applied_amount=new BigDecimal(0);
		vat_amount=new BigDecimal(0);
		
		for(VatBean vatBean:vatBeanList){
			vat_percent=vatBean.getVat();
			divisor=new BigDecimal(100);
			//MathContext mc = new MathContext(2);
			dicounted_amount=(vatBean.getAmount()).multiply(vatBean.getDiscount().divide(divisor)).setScale(2,0);
			vat_applied_amount=(vatBean.getAmount().subtract(dicounted_amount));
			vat_amount=(vat_applied_amount.multiply(vat_percent.divide(divisor))).setScale(2,0);

			if(vatMap.size()>0){
				if(!vatMap.containsKey(vat_percent)){
					vat_Array=new BigDecimal[3];
					vat_Array[0]=vat_percent;
					vat_Array[1]=vat_applied_amount;
					vat_Array[2]=vat_amount;
				}else{
					vat_Array=new BigDecimal[3];
				     vat_Array=(BigDecimal[]) vatMap.get(vat_percent);
				     if(vat_Array[0].equals(vat_percent)){
				      vat_Array[1]=vat_Array[1].add(vat_applied_amount);
				      vat_Array[2]=vat_Array[2].add(vat_amount);
				     
					}
				}
			}else{
				vat_Array=new BigDecimal[3];

				vat_Array[0]=vat_percent;
				vat_Array[1]=vat_applied_amount;
				vat_Array[2]=vat_amount;
			}
			vatMap.put(vat_percent,vat_Array); 
		}
		
		vatDetails=new ArrayList<String>();
		Iterator entries = vatMap.entrySet().iterator();
		BigDecimal total_Vat=new BigDecimal(0);
		BigDecimal total_Vat_Appaly_Amount=new BigDecimal(0);
		while (entries.hasNext()) {
			Map.Entry entry = (Map.Entry) entries.next();
			BigDecimal key = (BigDecimal)entry.getKey();
			vat_Array = (BigDecimal[])entry.getValue();
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			System.out.println("vat on " + vat_Array[1] + " @ "+vat_Array[0]+" is "+ vat_Array[2] );
			if(vat_Array[0]!=null && vat_Array[0].compareTo(BigDecimal.ZERO) != 0.00){
			//vatDetails.put(""+vat_Array[0]+"", "vat on " + Double.parseDouble(new DecimalFormat("##.##").format(vat_Array[1])) + " @ "+vat_Array[0]+" is "+ Double.parseDouble(new DecimalFormat("##.##").format(vat_Array[2])));
			//vatDeatils=vatDeatils+"<div>vat on " + Double.parseDouble(new DecimalFormat("##.##").format(vat_Array[1])) + " @ "+vat_Array[0]+" is "+ Double.parseDouble(new DecimalFormat("##.##").format(vat_Array[2]))+"</div>";
			
			vatDetails.add("vat on " + vat_Array[1].setScale(2,0) + " @ "+vat_Array[0]+" is "+ vat_Array[2].setScale(2,0));
			total_Vat_Appaly_Amount=total_Vat_Appaly_Amount.add(vat_Array[1]);
			total_Vat=total_Vat.add(vat_Array[2]);
			}
		}//)
		//vatDeatils=vatDeatils+"<div>-------------------------------------------------</div>";
		//vatDeatils=vatDeatils+"<div>total vat on "+total_Vat_Appaly_Amount+ " is "+total_Vat+"</div>";
		//vatDetails.put("Total", "total vat on "+total_Vat_Appaly_Amount+ " is "+Double.parseDouble(new DecimalFormat("##.##").format(total_Vat))+"");

		//System.out.println(">>>>>>>>>>>>>>>>>>>>>>>> vatDeatils >>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+vatDetails);
		vatDetails.add("-------------------------------------");
		vatDetails.add("total vat on "+total_Vat_Appaly_Amount.setScale(2)+ " is "+total_Vat.setScale(2)+"");
		if (total_Vat.compareTo(BigDecimal.ZERO) == 0 || total_Vat==null){
			vatDetails=new ArrayList<>();
		}
		return vatDetails;
	}

}
