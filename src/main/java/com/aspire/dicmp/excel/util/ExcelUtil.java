package com.aspire.dicmp.excel.util;

import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

/**
 * 项目名称:新零售-合作伙伴管理
 * 包名称:  com.aspire.dicmp.excel.util
 * 类名称:  ExcelUtil
 * 类描述:  ${功能描述}
 * 创建人:  zlx
 * 创建时间:2018年10月18日 14:55
 */
public class ExcelUtil {

    private static final Logger log = LoggerFactory.getLogger(ExcelUtil.class);

    public static void export(HttpServletResponse response,Workbook workbook) {
        OutputStream os = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + URLEncoder.encode("error.xls", "UTF-8"));
            os = response.getOutputStream();
            workbook.write(os);
        } catch (IOException e) {
            log.error("导出异常");
        } finally {
            try {
                if(os != null) {
                    os.close();
                }
                if(workbook != null) {
                    workbook.close();
                }
            } catch (IOException e) {
                log.error("流关闭异常");
            }
        }
    }
}
