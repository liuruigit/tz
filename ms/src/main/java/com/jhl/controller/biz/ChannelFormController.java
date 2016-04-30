package com.jhl.controller.biz;

import com.jhl.controller.base.BaseController;
import com.jhl.entity.Page;
import com.jhl.pojo.biz.ChannelOrder;
import com.jhl.service.biz.ChannelOrderService;
import com.jhl.service.biz.JytBillingService;
import com.jhl.util.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/** 
 * 类名称：ChannelOrderController
 * 创建人：Hally
 * 创建时间：2016-02-26
 */
@Controller
@RequestMapping(value="/channelform")
public class ChannelFormController extends BaseController {
	
	String menuUrl = "channelform/list.do"; //菜单地址(权限用)
	@Resource(name="msChannelorderService")
	private ChannelOrderService channelorderService;
	@Resource(name = "jytbillingService")
	private JytBillingService jytBillingService;
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "新增ChannelOrder");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		channelorderService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除ChannelOrder");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			channelorderService.delete(pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
	}
	
	/**
	 * 修改
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, "修改ChannelOrder");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		channelorderService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 查询所有的报表结果
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page , String user_name){
		logBefore(logger, "列表ChannelOrder");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();

		try{
			pd = this.getPageData();
			if(pd.getString("startDate") == null || pd.getString("endDate") == null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, +7);
				pd.put("startDate", sdf.format(new Date()));
				pd.put("endDate", sdf.format(c.getTime()));
			}

			List<List<String>> countList2 = new ArrayList<>();
			List<List<String>> amountList2 = new ArrayList<>();
			List<List<String>> countList3 = new ArrayList<>();
			List<List<String>> amountList3 = new ArrayList<>();
			for(int i = 2; i<=3; i++) {
				pd.put("TYPE", i);
				//channel平台，根据状态对账
				Map<String, List<String>> mapT2 = getChannelData(pd);
				//jyt平台，根据状态对账
				Map<String, List<String>> mapT3 = getJYTData(pd);
				if(i == 2) {
					countList2.add(0, mapT2.get("count"));//channel的次数
					countList2.add(1, mapT3.get("jytCount"));//channel的次数
					amountList2.add(0, mapT2.get("amount"));
					amountList2.add(1, mapT3.get("jytAmount"));
				}
				if(i == 3) {
					countList3.add(0, mapT2.get("count"));//channel的次数
					countList3.add(1, mapT3.get("jytCount"));//channel的次数
					amountList3.add(0, mapT2.get("amount"));
					amountList3.add(1, mapT3.get("jytAmount"));
				}
			}

			System.out.println(countList2);
			System.out.println(amountList2);
			System.out.println(countList3);
			System.out.println(amountList3);


			//共有参数
			String line_lable = "['金葫芦', '金运通']";
			String x_lable = "交易状态";//x轴的标题
			String x = "['成功交易', '失败交易', '总交易']";//x轴的刻度
			mv.addObject("line_lable", line_lable);
			mv.addObject("x_lable", x_lable);
			mv.addObject("x", x);

			//定义amountList2的参数（类型2：充值的金额）
			int amountMax2 = getMaxDataForArray(amountList2);//Y轴最大刻度值
			String titleAmount2 = "充值金额对比";//统计图的标题
			String amountY_lable2 = "充值金额（￥）";
			mv.addObject("amountList2", amountList2);
			mv.addObject("amountMax2", amountMax2);
			mv.addObject("titleAmount2", titleAmount2);
			mv.addObject("amountY_lable2", amountY_lable2);

			//定义countList2的参数（类型2：充值次数）
			int countMax2 = getMaxDataForArray(countList2);
			String titleCount2 = "充值次数对比";//统计图的标题
			String countY_lable2 = "充值次数（￥）";
			mv.addObject("countList2", countList2);
			mv.addObject("countMax2", countMax2);
			mv.addObject("titleCount2", titleCount2);
			mv.addObject("countY_lable2", countY_lable2);

			//定义amountList2的参数（类型3：提现的金额）
			int amountMax3 = getMaxDataForArray(amountList3);
			String titleAmount3 = "提现金额对比";//统计图的标题
			String amountY_lable3 = "充值金额（￥）";
			mv.addObject("amountList3", amountList3);
			mv.addObject("amountMax3", amountMax3);
			mv.addObject("titleAmount3", titleAmount3);
			mv.addObject("amountY_lable3", amountY_lable3);

			//定义countList2的参数（类型3：充值次数）
			int countMax3 = getMaxDataForArray(countList3);
			String titleCount3 = "提现次数对比";//统计图的标题
			String countY_lable3 = "充值金额（￥）";
			mv.addObject("countList3", countList3);
			mv.addObject("countMax3", countMax3);
			mv.addObject("titleCount3", titleCount3);
			mv.addObject("countY_lable3", countY_lable3);




			mv.addObject("pd", pd);

			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
			mv.setViewName("channelorder/channelorder_form");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}

	/**
	 * 获取数组集合中的最大参数值
	 */
	private int getMaxDataForArray(List<List<String>> lists) {
		int strValue = 0;
		int maxValue = 0;
		for(List<String> list : lists) {
			for(String str : list) {
				strValue = (int)Math.ceil(Double.parseDouble(str));
				maxValue = strValue > maxValue ? strValue : maxValue;
			}
		}
		return maxValue;
	}

	/**
	 * 根据TYPE查询JYT中的数据
	 * @param pd
	 * @return
	 * @throws Exception
     */
	private Map<String, List<String>> getJYTData(PageData pd) throws Exception {
		Map<String, List<String>> map = new HashMap<>();
		PageData pdJyt = jytBillingService.selectAll(pd);
			List<String> countJYT = new ArrayList<>();
			List<String> amountJYT = new ArrayList<>();
			countJYT.add(0,pdJyt == null ? "0" : pdJyt.get("succCount").toString());
			countJYT.add(1,pdJyt == null ? "0" : pdJyt.get("failCount").toString());
			countJYT.add(2,pdJyt == null ? "0" : pdJyt.get("allCount").toString());
			amountJYT.add(0,pdJyt == null ? "0.0" : pdJyt.get("succAmount").toString());
			amountJYT.add(1,pdJyt == null ? "0.0" : pdJyt.get("failAmount").toString());
			amountJYT.add(2,pdJyt == null ? "0.0" : pdJyt.get("allAmount").toString());
			map.put("jytCount", countJYT);
			map.put("jytAmount", amountJYT);
		return map;
	}

	/**
	 * 根据TYPE查询channel中的数据
	 * @return
     */
	private Map<String, List<String>> getChannelData(PageData pd) throws Exception {
		Map<String, List<String>> map = new HashMap<>();
		//数据库查询
		List<PageData> pdChaList = channelorderService.selectStatusChannelReault(pd);
		List<String> count = new ArrayList<>();
		List<String> amount = new ArrayList<>();
		int allCount = 0;
		double allAmount = 0.0;
		String succCount = "0";
		String succAmount = "0.0";
		String failCount = "0";
		String failAmount = "0.0";
		if(pdChaList.size() > 0) {
			for(PageData pdCh : pdChaList) {
				//如果status = 3, 将其中的值加入到一个数组
				Integer status = Integer.parseInt(pdCh.get("status").toString());
				if(status.equals("3")) {
					succCount = pdCh.get("NUM").toString();
					succAmount = pdCh.get("TATAL").toString();
				}
				//如果status = 4，将其中的值加入到一个数组
				if(status.equals("4")) {
					failCount = pdCh.get("NUM").toString();
					failAmount = pdCh.get("TATAL").toString();
				}
				allCount = allCount + Integer.parseInt(pdCh.get("NUM").toString());
				allAmount = allAmount + Double.parseDouble(pdCh.get("TATAL").toString());
			}
		}
		count.add(0,succCount);
		amount.add(0,succAmount);
		count.add(1,failCount);
		amount.add(1,failAmount);
		count.add(2,allCount+"");//次数添加到数组
		amount.add(2,allAmount+"");//金额添加到数组
		map.put("count", count);
		map.put("amount", amount);
		return map;
	}
	
	/**
	 * 去新增页面
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
		logBefore(logger, "去新增ChannelOrder页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("channelorder/channelorder_edit");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	
	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit(){
		logBefore(logger, "去修改ChannelOrder页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd = channelorderService.findById(pd);	//根据ID读取
			mv.setViewName("channelorder/channelorder_edit");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() {
		logBefore(logger, "批量删除ChannelOrder");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				channelorderService.deleteAll(ArrayDATA_IDS);
				pd.put("msg", "ok");
			}else{
				pd.put("msg", "no");
			}
			pdList.add(pd);
			map.put("list", pdList);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		} finally {
			logAfter(logger);
		}
		return AppUtil.returnObject(pd, map);
	}

    /**
     * 批准申请
     */
    @RequestMapping(value="/passAll")
    @ResponseBody
    public Object passAll() {
        logBefore(logger, "批量批准ChannelOrder");
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
        PageData pd = new PageData();
        Map<String,Object> map = new HashMap<String,Object>();
        try {
            pd = this.getPageData();
            List<PageData> pdList = new ArrayList<PageData>();
            String DATA_IDS = pd.getString("DATA_IDS");
            if(null != DATA_IDS && !"".equals(DATA_IDS)){
                String ArrayDATA_IDS[] = DATA_IDS.split(",");

                HashMap<String, List> rs = channelorderService.passAll(ArrayDATA_IDS, ChannelOrder.STATUS_AGREE);
                pd.put("msg", "ok");
                pd.put("rs" , rs );
            }else{
                pd.put("msg", "no");
            }
            pdList.add(pd);
            map.put("list", pdList);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        } finally {
            logAfter(logger);
        }
        return AppUtil.returnObject(pd, map);
    }

	/*
	 * 导出到excel
	 * @return
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel(){
		logBefore(logger, "导出ChannelOrder到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("金额");	//1
			titles.add("银行卡ID");	//2
			titles.add("请求订单号");	//3
			titles.add("同步应答码");	//4
			titles.add("异步应答码");	//5
			titles.add("通知时间");	//6
			titles.add("外部订单号");	//7
			titles.add("用户ID");	//8
			titles.add("0发起充值1平台接收处理中2到账");	//9
			titles.add("创建时间");	//10
			titles.add("1有效");	//11
			titles.add("订单类型0充值1提现");	//12
			dataMap.put("titles", titles);
			List<PageData> varOList = channelorderService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("AMOUNT"));	//1
				vpd.put("var2", varOList.get(i).getString("BANK_ID"));	//2
				vpd.put("var3", varOList.get(i).getString("ORDER_NO"));	//3
				vpd.put("var4", varOList.get(i).getString("SYN_RESULT"));	//4
				vpd.put("var5", varOList.get(i).getString("ASY_RESULT"));	//5
				vpd.put("var6", varOList.get(i).getString("NOTIFY_TIME"));	//6
				vpd.put("var7", varOList.get(i).getString("TRAN_FLOW"));	//7
				vpd.put("var8", varOList.get(i).getString("USER_ID"));	//8
				vpd.put("var9", varOList.get(i).getString("STATUS"));	//9
				vpd.put("var10", varOList.get(i).getString("CREATE_TIME"));	//10
				vpd.put("var11", varOList.get(i).getString("DELETE_STATE"));	//11
				vpd.put("var12", varOList.get(i).getString("TYPE"));	//12
				varList.add(vpd);
			}
			dataMap.put("varList", varList);
			ObjectExcelView erv = new ObjectExcelView();
			mv = new ModelAndView(erv,dataMap);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/* ===============================权限================================== */
	public Map<String, String> getHC(){
		Subject currentUser = SecurityUtils.getSubject();  //shiro管理的session
		Session session = currentUser.getSession();
		return (Map<String, String>)session.getAttribute(Const.SESSION_QX);
	}
	/* ===============================权限================================== */
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
