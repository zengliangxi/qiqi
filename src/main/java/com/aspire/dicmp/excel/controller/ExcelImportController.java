package com.aspire.dicmp.excel.controller;

import com.aspire.dicmp.excel.handler.UserExcelHandler;
import com.aspire.dicmp.excel.model.User;
import com.aspire.dicmp.excel.util.ExcelUtil;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import cn.afterturn.easypoi.handler.inter.IExcelDataHandler;


@RestController
@RequestMapping({"/excel/"})
public class ExcelImportController {

	private static final Logger log = LoggerFactory.getLogger(ExcelImportController.class);

	@PostMapping("import")
	public void excelImport(@RequestParam("file") MultipartFile file,HttpServletResponse response) {
		ImportParams importParams = new ImportParams();
		// 需要验证
		IExcelDataHandler<User> handler = new UserExcelHandler();
		handler.setNeedHandlerFields(new String[] {"id", "姓名","年龄" });//
		// 注意这里对应的是excel的列名。也就是对象上指定的列名。
		importParams.setDataHandler(handler);

		// 需要验证
		importParams.setNeedVerfiy(true);

		//校验模板列顺序
		importParams.setNeedCheckOrder(true);
		String[] fields =  new String[]{"id","姓名","年龄","生日"};
		importParams.setImportFields(fields);
		try {
			ExcelImportResult<User> result = ExcelImportUtil.importExcelMore(file.getInputStream(), User.class,
					importParams);

			List<User> successList = result.getList();
			List<User> failList = result.getFailList();

			log.info("是否存在验证未通过的数据:" + result.isVerfiyFail());
			log.info("验证通过的数量:" + successList.size());
			log.info("验证未通过的数量:" + failList.size());

			for (User user : successList) {
				log.info("成功列表信息:ID=" + user.getId() + user.getName());
			}
			for (User user : failList) {
				log.info("失败列表信息:" + user.getName());
			}
			if(result.isVerfiyFail()) {
				Workbook failWorkBook = result.getFailWorkbook();
				for(int i=0;i<10;i++) {
					Cell cell = failWorkBook.getSheetAt(0).getRow(i).getCell(4);
					if(cell != null) {
						System.out.println("cell内容:"+cell.getStringCellValue());
					}

				}

				ExcelUtil.export(response,failWorkBook);
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	@GetMapping("helloWorld")
	public String helloWorld() {

		return "helloWorld";
	}

	@PostMapping("export")
	public void exportExcel(HttpServletResponse response) {
		List<User> list = new ArrayList<>();
		for(int i=0; i<10;i++) {
			User u = new User();
			u.setId(i+"");
			u.setAge(20-i);
			u.setBirthday(new Date());
			u.setName("李白"+i);
			list.add(u);
		}
		ExportParams exportParams = new ExportParams();
		/*short titleHeight = 50;
		short secondTitleHeight = 20;
		exportParams.setTitle("第一级");
		exportParams.setTitleHeight(titleHeight);
		exportParams.setSecondTitle("第二级");
		exportParams.setSecondTitleHeight(secondTitleHeight);
		exportParams.setColor(HSSFColor.RED.index);
		exportParams.setHeaderColor(HSSFColor.RED.index);*/
		Workbook workbook = ExcelExportUtil.exportExcel(exportParams,User.class,list);
		ExcelUtil.export(response,workbook);
	}
}
