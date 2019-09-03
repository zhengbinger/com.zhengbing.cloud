package com.zhengbing.cloud.missioncenter.repository;

import com.zhengbing.cloud.missioncenter.entity.Mission;

public interface MissionMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Mission record);

    int insertSelective(Mission record);

    Mission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Mission record);

    int updateByPrimaryKey(Mission record);
}