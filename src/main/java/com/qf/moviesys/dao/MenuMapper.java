package com.qf.moviesys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qf.moviesys.pojo.Menu;

import java.util.List;

public interface MenuMapper extends BaseMapper<Menu> {
    List<Menu> loadMenus(Integer roleId);
}
