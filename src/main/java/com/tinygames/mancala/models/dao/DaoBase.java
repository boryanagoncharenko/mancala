package com.tinygames.mancala.models.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

public abstract class DaoBase
{
//    @Autowired
    protected RedisTemplate<String, String> template;
}
