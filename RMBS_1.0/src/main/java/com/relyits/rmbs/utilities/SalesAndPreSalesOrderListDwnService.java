package com.relyits.rmbs.utilities;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.relyits.rmbs.beans.sales.SalesOrderBean1;
import com.relyits.rmbs.beans_preparation.sales.SalesOrderBeanPreparation1;
import com.relyits.rmbs.model.sales.SalesOrderModel;
import com.relyits.rmbs.service.ReportsService;
@Service
public class SalesAndPreSalesOrderListDwnService {
	public static final String TEMPLATE = "/salesReports.jrxml";
	//protected static Logger logger = Logger.getLogger("service");

	@Autowired
	private ReportsService reportsService;
	
	@Autowired
	private ExporterService exporter;
	
	@Autowired
	private TokenService tokenService;
	
	private int sales;
	
	@Value("${category.sales}") String categorySales;
	private Map<String, String> properties;
	public Map<String, String> initializeProperties(){
		sales = Integer.parseInt(categorySales);
		return properties;
	}
	String fileName=null;
	public void download(String user,String type, String token, HttpServletResponse response,Date fromDate,Date toDate,SalesOrderModel salesOrderModel) {
		 
		try {
			// 1. Add report parameters
			initializeProperties();
			HashMap<String, Object> params = new HashMap<String, Object>();
			if(salesOrderModel.getCategoryModel().getId().equals(sales)){
				params.put("Title", "Sales Order Reports");
				params.put("fromDate", fromDate);
				params.put("toDate", toDate);
				params.put("user", user);
				fileName = "Sales Order Reports";
			}
			else{
				params.put("fromDate", fromDate);
				params.put("toDate", toDate);
				params.put("user", user);
				params.put("Title", "PreSales Order Reports");
				fileName = "PreSales Order Reports";
			}
			 System.out.println("********DownloadService calling********");
			// 2.  Retrieve template
			InputStream reportStream = this.getClass().getResourceAsStream(TEMPLATE); 
			 
			// 3. Convert template to JasperDesign
			JasperDesign jd = JRXmlLoader.load(reportStream);
			 
			// 4. Compile design to JasperReport
			JasperReport jr = JasperCompileManager.compileReport(jd);
			 
		System.out.println("sales and presales "+SalesAndPreSalesOrderListDwnService.class.getMethods());
			
			List<SalesOrderBean1> list=SalesOrderBeanPreparation1.prepareListOfSalesOrderBean1(reportsService.getSalesOrderReportsList(fromDate, toDate,salesOrderModel));
			
			JRDataSource jrd = new JRBeanCollectionDataSource(list);
			// 5. Create the JasperPrint object
			// Make sure to pass the JasperReport, report parameters, and data source
			JasperPrint jp = JasperFillManager.fillReport(jr, params,jrd);
			 
			// 6. Create an output byte stream where data will be written
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			 
			// 7. Export report
			exporter.export(type, jp, response, baos, fileName);
			 
			// 8. Write to reponse stream
			write(token, response, baos);
		
		} catch (JRException jre) {
			//logger.error("Unable to process download");
			throw new RuntimeException(jre);
		}
	}
	
	/**
	* Writes the report to the output stream
	*/
	private void write(String token, HttpServletResponse response,
			ByteArrayOutputStream baos) {
		 
		try {
			//logger.debug(baos.size());
			
			//Retrieve output stream
			ServletOutputStream outputStream = response.getOutputStream();
			// Write to output stream
			baos.writeTo(outputStream);
			// Flush the stream
			outputStream.flush();
			
			// Remove download token
			tokenService.remove(token);
			
		} catch (Exception e) {
			//logger.error("Unable to write report to the output stream");
			throw new RuntimeException(e);
		}
	}
}
