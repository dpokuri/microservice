package com.tl.booking.gateway.dao.test.impl;

import com.tl.booking.gateway.dao.api.SystemParameterRepository;
import com.tl.booking.gateway.entity.constant.unit.test.CommonTestVariable;
import com.tl.booking.gateway.entity.constant.unit.test.SystemParameterTestVariable;
import com.tl.booking.gateway.entity.dao.SystemParameter;
import com.tl.booking.gateway.dao.test.configuration.TestRepositoryConfiguration;

import org.apache.log4j.MDC;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestRepositoryConfiguration.class})
@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class})
public class SystemParameterRepositoryImplTest extends SystemParameterTestVariable {

  @Autowired
  private SystemParameterRepository systemParameterRepository;

  @Test
  public void updateSystemParameterIsDeleteByIdTest() throws Exception {
    SystemParameter systemParameter = this.systemParameterRepository.save(this.SYSTEM_PARAMETER);
    this.systemParameterRepository.updateSystemParameterIsDeleteById(systemParameter.getId(), 1);
  }

  @Before
  public void setUp() throws Exception {
    MDC.put("storeId", CommonTestVariable.STORE_ID);
    MDC.put("channelId", CommonTestVariable.CHANNEL_ID);
    MDC.put("username", CommonTestVariable.USERNAME);
    MDC.put("requestId", CommonTestVariable.REQUEST_ID);
    MDC.put("serviceId", CommonTestVariable.SERVICE_ID);
  }

  @After
  public void tearDown() throws Exception {
    this.systemParameterRepository.deleteAll();
  }
}

