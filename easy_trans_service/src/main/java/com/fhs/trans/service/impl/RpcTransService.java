package com.fhs.trans.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.fhs.common.utils.ConverterUtils;
import com.fhs.core.trans.anno.Trans;
import com.fhs.core.trans.constant.TransType;
import com.fhs.core.trans.util.ReflectUtils;
import com.fhs.core.trans.vo.VO;
import com.fhs.trans.vo.BasicVO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * 远程翻译服务
 */
@Data
@Slf4j
public class RpcTransService extends SimpleTransService {

    private RestTemplate restTemplate;


    @Override
    public List<? extends VO> findByIds(List<String> ids, Trans tempTrans) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("ids", ids);
        //执行远程调用
        try {
            String respJson = restTemplate.postForObject("http://" + tempTrans.serviceName()
                    + "/easyTrans/proxy/" + tempTrans.targetClassName() + "/findByIds", paramMap, String.class);
            return JSONArray.parseArray(respJson, BasicVO.class);
        } catch (Exception e) {
            log.error("trans service执行RPC Trans 远程调用错误:" + tempTrans.serviceName(), e);
        }
        return new ArrayList<>();
    }

    @Override
    public VO findById(String id, Trans tempTrans) {
        //执行远程调用
        try {
            return restTemplate.getForObject("http://" + tempTrans.serviceName()
                    + "/easyTrans/proxy/" + tempTrans.targetClassName() + "/findById/" + id, BasicVO.class);
        } catch (Exception e) {
            log.error("trans service执行RPC Trans 远程调用错误:" + tempTrans.serviceName(), e);
        }
        return null;
    }

    /**
     * 创建一个临时缓存map
     *
     * @param po    po
     * @param trans 配置
     * @return
     */
    protected Map<String, String> createTempTransCacheMap(Object po, Trans trans) {
        String fielVal = null;
        Map<String, String> tempCacheTransMap = new LinkedHashMap<>();
        if (po == null) {
            return tempCacheTransMap;
        }
        BasicVO basicVO = (BasicVO) po;
        for (String field : trans.fields()) {
            fielVal = ConverterUtils.toString(basicVO.getObjContentMap().get(field));
            tempCacheTransMap.put(field, fielVal);
        }
        return tempCacheTransMap;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        TransService.registerTransType(TransType.RPC, this);
    }

}
