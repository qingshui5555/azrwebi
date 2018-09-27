package com.movit.rwe.modules.bi.dashboard.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movit.rwe.common.service.CrudService;
import com.movit.rwe.modules.bi.base.dao.mysql.ChartDao;
import com.movit.rwe.modules.bi.base.entity.mysql.Chart;

@Service
@Transactional
public class ChartService extends CrudService<ChartDao, Chart> {

}
