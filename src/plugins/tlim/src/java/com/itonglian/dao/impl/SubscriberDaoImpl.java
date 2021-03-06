package com.itonglian.dao.impl;

import com.itonglian.dao.SubscriberDao;
import com.itonglian.entity.OfSubscriber;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.jivesoftware.database.DbConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SubscriberDaoImpl implements SubscriberDao {

    private static final SubscriberDao subscriberDao = new SubscriberDaoImpl();


    private static final Logger Log = LoggerFactory.getLogger(SubscriberDaoImpl.class);

    private static final String INSERT = "INSERT INTO ofsubscriber (user_id,user_name,acct_login,pic,session_id,ts) VALUES(?,?,?,?,?,?)";

    private static final String QUERY_SUBSCRIBERS = "SELECT * FROM ofsubscriber WHERE session_id = ?";

    private static final String DELETE = "DELETE FROM ofsubscriber WHERE user_id = ? AND session_id = ?";

    private static final String DELETE_BY_SESSION = "DELETE FROM ofsubscriber WHERE session_id = ? ";

    public static SubscriberDao getInstance(){
        return subscriberDao;
    }

    @Override
    public void add(OfSubscriber subscriber) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DbConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(INSERT);
            int i=1;
            preparedStatement.setString(i++,subscriber.getUser_id());
            preparedStatement.setString(i++,subscriber.getUser_name());
            preparedStatement.setString(i++,subscriber.getAcct_login());
            preparedStatement.setString(i++,subscriber.getPic());
            preparedStatement.setString(i++,subscriber.getSession_id());
            preparedStatement.setString(i++,subscriber.getTs());
            preparedStatement.execute();
        }catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            DbConnectionManager.closeConnection(preparedStatement,connection);
        }
    }

    @Override
    public void delete(String userId,String sessionId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DbConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(DELETE);
            preparedStatement.setString(1,userId);
            preparedStatement.setString(2,sessionId);
            preparedStatement.execute();
        }catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            DbConnectionManager.closeConnection(preparedStatement,connection);
        }
    }

    @Override
    public void update(OfSubscriber subscriber) {

    }

    @Override
    public OfSubscriber findEntityById(String userId) {
        return null;
    }

    @Override
    public List<OfSubscriber> findSubscribers(String sessionId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DbConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(QUERY_SUBSCRIBERS);
            preparedStatement.setString(1,sessionId);
            resultSet = preparedStatement.executeQuery();
            List<OfSubscriber> list = new ArrayList<OfSubscriber>();
            while(resultSet.next()){
                OfSubscriber ofSubscriber = new OfSubscriber();
                ofSubscriber.setUser_id(resultSet.getString("user_id"));
                ofSubscriber.setUser_name(resultSet.getString("user_name"));
                ofSubscriber.setAcct_login(resultSet.getString("acct_login"));
                ofSubscriber.setPic(resultSet.getString("pic"));
                ofSubscriber.setSession_id(resultSet.getString("session_id"));
                ofSubscriber.setTs(resultSet.getString("ts"));
                list.add(ofSubscriber);
            }
            return list;
        }catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            DbConnectionManager.closeConnection(resultSet,preparedStatement,connection);
        }
        return null;
    }

    @Override
    public void deleteBySession(String sessionId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DbConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(DELETE_BY_SESSION);
            preparedStatement.setString(1,sessionId);
            preparedStatement.execute();
        }catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            DbConnectionManager.closeConnection(preparedStatement,connection);
        }
    }


}
