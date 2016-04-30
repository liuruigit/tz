package com.jhl.dao;

import com.jhl.pojo.biz.Project;
import com.jhl.pojo.biz.SupplierProjectMapping;
import com.jhl.pojos.PageInfo;
import com.jhl.pojos.PaginationResult;
import com.jhl.pojos.SQLCondition;
import com.jhl.util.Money;

import java.util.List;
import java.util.Map;

/**
 * Created by hallywu on 16/1/28.
 */
public interface IProjectDao {

    /**
     * 查询标的 - 甲方信息
     * @param id
     * @return
     * @throws Exception
     */
    public SupplierProjectMapping queryMappingSupplier(String no) throws Exception ;

    /**
     * 查询标的名称
     * @param id
     * @return
     * @throws Exception
     */
    public Map<String, Object> queryProName(Integer id) throws Exception;
    /**
     * 更新标的状态
     * @param id
     * @param status
     * @return
     * @throws Exception
     */
    public int updateStatus(Integer id,Project.Status status) throws Exception;

    /**
     * 结算标
     * @param project
     * @param status
     * @return
     * @throws Exception
     */
    public int preSettle(Project project,Project.Status status) throws Exception;

    /**
     * 检查投资数据:累加投资和标的金额比对，校验是否满标
     * @param pid
     * @return
     * @throws Exception
     */
    public boolean checkInvest(int pid) throws Exception ;

    /**
     * 查询投资人
     * @param conditions
     * @param pageInfo
     * @param order
     * @return
     * @throws Exception
     */
    public PaginationResult<Map<String, Object>> queryInvestAcc(List<SQLCondition> conditions, PageInfo pageInfo, String order) throws Exception;

    /**
     * 查询附件
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> queryProAttach(int id) throws Exception;

    /**
     * 查询推荐标的
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> queryRecommendProjs() throws Exception;

    /**
     * 查询标的详情
     * @param id
     * @return
     * @throws Exception
     */
    public Map<String, Object> queryProInfo(int id) throws Exception;

    /**
     * 更新项目的融资金额.
     * 使用where条件,避免更新数据时用锁:
     * 1>SELLED_AMOUNT + 本次融资金额 <= 总的融资金额时,即可成功融资
     * 2> SELLED_AMOUNT + 本次融资金额 > 总的融资金额时, 本次融资失败,项目可融资余额不足.
     * 数据的串行执行特性可以保证数据的正确性
     *
     * @param id
     * @param amount
     * @return
     */
    public int updateInvestAmount(Integer id, Money amount) throws Exception;

    /**
     * 如果已经满标,更新标的状态
     *
     * @param id
     * @return
     */
    public int updateStatusIfFull(Integer id) throws Exception;

    /**
     * 标的列表
     * @param conditions
     * @param pageInfo
     * @param order
     * @return
     * @throws Exception
     */
    public PaginationResult<Map<String,Object>> queryForPageMap(List<SQLCondition> conditions, PageInfo pageInfo,String order) throws Exception ;


    /**
     * 查询标的的房产信息数量
     * @param project
     * @return
     */
    public Integer queryInfoForCount(Project project);
}
