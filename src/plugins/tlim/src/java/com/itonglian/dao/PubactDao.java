package com.itonglian.dao;

import com.itonglian.entity.OfPubact;

import java.util.List;

public interface PubactDao {

    public void add(String title,String content,String user_id,String session_id);

    public List<OfPubact> findBySession(String session_id);

    public void update(String id_,String title,String content);

    public void delete(String id_);
}
