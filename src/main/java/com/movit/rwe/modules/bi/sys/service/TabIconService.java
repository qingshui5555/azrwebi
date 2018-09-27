package com.movit.rwe.modules.bi.sys.service;

import org.springframework.stereotype.Service;

import com.movit.rwe.common.service.CrudService;
import com.movit.rwe.modules.bi.base.dao.mysql.TabIconDao;
import com.movit.rwe.modules.bi.base.entity.mysql.TabIcon;

@Service
public class TabIconService extends CrudService<TabIconDao, TabIcon> {
}
