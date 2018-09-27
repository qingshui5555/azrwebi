package com.movit.rwe.modules.bi.etl.service;

import java.io.InputStream;
import java.util.*;

import com.movit.rwe.modules.bi.base.entity.mysql.EtlResultConfig;
import com.movit.rwe.modules.sys.utils.UserUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.movit.rwe.modules.bi.base.dao.mysql.EtlResultConfigDao;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class EtlResultConfigService {

	@Autowired
	private EtlResultConfigDao etlResultConfigDao;

	/**
	 * 获取ETL配置数据，字段和数据对应的map
	 * @return
	 */
	public List<EtlResultConfig> queryListEtlResultConfig() {
		Long userId = Long.parseLong(UserUtils.getPrincipal().getId());
		List<EtlResultConfig> etlResultConfigList = etlResultConfigDao.queryListEtlResultConfig(userId);
		return etlResultConfigList;
	}

	/**
	 * 删除单条配置
	 * @param id
	 * @return
	 */
	public int removeConfigRow(Long id) {
		Long userId = Long.parseLong(UserUtils.getPrincipal().getId());
		return etlResultConfigDao.removeConfigRow(id, userId);
	}

	/**
	 * 更新单条配置
	 * @param jsonData
	 * @return
	 */
	public int updateConfigRow(String jsonData) {
		int result = -1;
		if(StringUtils.isBlank(jsonData) || jsonData.equals("{}")){
			return result;
		}
		JSONObject json = JSONObject.fromObject(jsonData);
		Long id = null;
		Map<String,String> dataMap = new HashMap<String, String>();
		for (Iterator iterator = json.keys(); iterator.hasNext();) {
			String key = (String) iterator.next();
			String value = json.getString(key);
			if ("id".equalsIgnoreCase(key)) {
				id = Long.parseLong(value);
			} else {
				dataMap.put(key, value);
			}
		}

		if(id == null){
			return result;
		}
		Long userId = Long.parseLong(UserUtils.getPrincipal().getId());
		result = etlResultConfigDao.updateConfigRow(id, userId, dataMap);

		return result;
	}

	/**
	 * 上传文件
	 * @param file
	 * @return
	 */
	public int importByExcel(MultipartFile file){
		int result = 0;
		List<List<Object>> dataListList = new ArrayList<List<Object>>();
		List<List<Object>> columnListList = new ArrayList<List<Object>>();
		boolean isE2007 = false;
		if (file.getOriginalFilename().endsWith("xlsx")) {
			isE2007 = true;
		}
		try {
			InputStream input = file.getInputStream();
			Workbook wb = null;
			if (isE2007) {
				wb = new XSSFWorkbook(input);
			} else {
				wb = new HSSFWorkbook(input);
			}
			Sheet sheet = wb.getSheetAt(0);
			Date currentDate = new Date();
			Long userId = Long.parseLong(UserUtils.getPrincipal().getId());
			Long version = etlResultConfigDao.getMaxVersion(userId);
			if(version == null){
				version = 1L;
			} else {
				version += 1;
			}
			Row rowColumn = sheet.getRow(sheet.getFirstRowNum());
			String[] columns = new String[rowColumn.getLastCellNum()];
			for(int i=rowColumn.getFirstCellNum(); i<columns.length; i++){
				columns[i] = (String) getCellValue(rowColumn.getCell(i));
			}

			for(int j=sheet.getFirstRowNum()+1; j<=sheet.getLastRowNum(); j++){
				Row row = sheet.getRow(j);
				List<Object> dataList = new ArrayList<Object>();
				List<Object> columnList = new ArrayList<Object>();
				for(int k=rowColumn.getFirstCellNum(); k<row.getLastCellNum(); k++){
					String value = (String) getCellValue(row.getCell(k));
					if(!StringUtils.isBlank(value)){
						dataList.add(value);
						columnList.add(columns[k]);
					}
				}
				columnList.add("create_by");
				dataList.add(userId);
				columnList.add("create_on");
				dataList.add(currentDate);
				columnList.add("version");
				dataList.add(version);
				dataListList.add(dataList);
				columnListList.add(columnList);
			}

			for(int m=0; m<dataListList.size(); m++){
				result += etlResultConfigDao.importConfig(columnListList.get(m), dataListList.get(m));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return result;
	}


	private Object getCellValue(Cell cell) {
		if(cell == null){
			return "";
		}

		switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_NUMERIC:
				return cell.getNumericCellValue();
			case HSSFCell.CELL_TYPE_STRING:
				return cell.getStringCellValue();
			case HSSFCell.CELL_TYPE_BOOLEAN:
				return cell.getBooleanCellValue();
			case HSSFCell.CELL_TYPE_FORMULA:
				return cell.getCellFormula();
			default:
				return "";
		}
	}
}
