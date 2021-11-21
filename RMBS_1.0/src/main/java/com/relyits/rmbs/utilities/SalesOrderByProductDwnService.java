package com.relyits.rmbs.utilities;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
import org.springframework.stereotype.Service;

import com.relyits.rmbs.beans.sales.SalesLineItemsBean1;
import com.relyits.rmbs.beans_preparation.sales.SalesLineItemsBeanPreparation1;
import com.relyits.rmbs.model.sales.SalesLineItemsModel;
import com.relyits.rmbs.service.ReportsService;
import com.relyits.rmbs.service.SalesReturnsService;

@Service
public class SalesOrderByProductDwnService {
	public static final String TEMPLATE = "/SalesOrderByProductReports.jrxml";
	//protected static Logger logger = Logger.getLogger("service");

	@Autowired
	private ReportsService reportsService;
	
	@Autowired
	private ExporterService exporter;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private SalesReturnsService salesReturnsService;
	public void download(String user,String type, String token, HttpServletResponse response,Date fromDate,Date toDate, SalesLineItemsModel salesLineItemsModel) {
		 
		try {
			// 1. Add report parameters
			HashMap<String, Object> params = new HashMap<String, Object>(); 
			params.put("Title1", "SalesOrder By product Reports");
			params.put("fromDate", fromDate);
			params.put("toDate", toDate);
			params.put("user", user);
			String fileName = "SalesOrder By product Reports";
			 System.out.println("********DownloadService calling********");
			// 2.  Retrieve template
			InputStream reportStream = this.getClass().getResourceAsStream(TEMPLATE); 
			 
			// 3. Convert template to JasperDesign
			JasperDesign jd = JRXmlLoader.load(reportStream);
			 
			// 4. Compile design to JasperReport
			JasperReport jr = JasperCompileManager.compileReport(jd);
			 
		
			
		//	List<PurchaseOrderBean> list=reportsService.purchaseOrderListReports(branchId);
			List<SalesLineItemsBean1> list=SalesLineItemsBeanPreparation1.prepareListOfSalesLineItemsBean1(reportsService.getProductSales(fromDate,toDate,salesLineItemsModel));
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
		//	logger.debug(baos.size());
			
			// Retrieve output stream
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
