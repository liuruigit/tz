/** 数字金额大写转换(可以处理整数,小数,负数) */
function digitUppercase(n) {
	var fraction = ['角', '分'];
	var digit = [
		'零', '壹', '贰', '叁', '肆',
		'伍', '陆', '柒', '捌', '玖'
	];
	var unit = [
		['元', '万', '亿'],
		['', '拾', '佰', '仟']
	];
	var head = n < 0 ? '欠' : '';
	n = Math.abs(n);
	var s = '';
	for (var i = 0; i < fraction.length; i++) {
		s += (digit[Math.floor(n * 10 * Math.pow(10, i)) % 10] + fraction[i]).replace(/零./, '');
	}
	s = s || '整';
	n = Math.floor(n);
	for (var i = 0; i < unit[0].length && n > 0; i++) {
		var p = '';
		for (var j = 0; j < unit[1].length && n > 0; j++) {
			p = digit[n % 10] + unit[1][j] + p;
			n = Math.floor(n / 10);
		}
		s = p.replace(/(零.)*零$/, '').replace(/^$/, '零') + unit[0][i] + s;
	}
	return head + s.replace(/(零.)*零元/, '元')
			.replace(/(零.)+/g, '零')
			.replace(/^整$/, '零元整');
}

function check_num(obj,msg){
	var number = $(obj).val();
	var type = /^[1-9][1-9]*/;
	var re = new RegExp(type);
	if (number.match(re) == null) {
		tail(obj,msg);
		return false;
	}
	return true
}

//验证是否为空
function check_blank(obj, msg){
	if($(obj).val() != ''){
		return true;
	}else{
		tail(obj,msg);
		return false;
	}
}

//过滤输入字符的长度
function check_str_len(name,obj,maxLength){
	obj.value=$(obj).val().replace(/(^\s*)|(\s*$)/g, "");
	var newvalue = $(obj).val().replace(/[^\x00-\xff]/g, "**");
	var length11 = newvalue.length;
	if(length11>maxLength){
		//alert();
		$(obj).val("");
		tail(obj,name+"字符长度在"+maxLength+"个以内");
		obj.focus();
		return false;
	}
	return true;
}

//验证只能为数字
function checkNumber(obj){
	var reg = /^[0-9]+$/;
	if($(obj).val()!=""&&!reg.test($(obj).val())){
		return true;
	}
	tail(obj,'请输入数字！');
	return false;
}

function testNum(obj){
	var reg = /^[0-9]+$/;
	if($(obj).val()!=""&&!reg.test($(obj).val())){
		tail(obj,'请输入数字！');
		return false;
	}
	return true;
}

//验证数字大小的范围
function check_num_value(obj,minvalue,maxvalue,msg){
	var reg = /^[0-9]*[1-9][0-9]*$/;
	var re = new RegExp(type);
	if($(obj).val()!=""&&!re.match($(obj).val())){
		tail(obj,msg);
	}else if(minvalue>$(obj).val()||$(obj).val()>maxvalue){
		tail(obj,msg);
	}

}

//正数校验
function check_positive_num(obj_name,msg){
	check_num_value(obj_name,0,9999999999,msg);
}

//验证只能是字母和数字
function checkZmOrNum(zmnum){
	var zmnumReg=/^[0-9a-zA-Z]*$/;
	if(zmnum.value!=""&&!zmnumReg.test(zmnum.value)){
		tail(zmnum,"只能输入是字母或者数字,请重新输入");
	}
}

//验证双精度数字
function check_double(obj,obj_name){
	var reg = /^[0-9]+(\.[0-9]+)?$/;
	if($(obj).val()!=""&&!reg.test($(obj).val())){
		tail(obj, obj_name+'所填必须为有效的双精度数字');
		$(obj).val("");
		obj.focus();
		return false;
	}
	return true;
}


//复选框全选
function checkboxs_all(obj,cName){
	var checkboxs = document.getElementsByName(cName);
	for(var i=0;i<checkboxs.length;i++){
		checkboxs[i].checked = obj.checked;
	}
}


//验证邮政编码
function check_youbian(obj){
	var reg=/^\d{6}$/;
	if($(obj).val()!=""&&!reg.test($(obj).val())){
		tail(obj,"请输入合法的邮箱！");
	}
}

//验证邮箱格式
function check_email(obj){
	var reg = /^[a-zA-Z0-9_-]+(\.([a-zA-Z0-9_-])+)*@[a-zA-Z0-9_-]+[.][a-zA-Z0-9_-]+([.][a-zA-Z0-9_-]+)*$/;
	if($(obj).val()!=""&&!reg.test($(obj).val())){
		obj.select();
		tail(obj,'电子邮箱格式输入错误！');
		return false;
	}
	return true;
}

/*验证固定电话号码
 0\d{2,3}   代表区号
 [0\+]\d{2,3}   代表国际区号
 \d{7,8} 代表7－8位数字(表示电话号码)
 正确格式：区号-电话号码-分机号(全写|只写电话号码)
 */

function check_phone(obj){
	var reg=/^(([0\+]\d{2,3}-)?(0\d{2,3})-)?(\d{7,8})(-(\d{3,}))?$/;
	if($(obj).val()!=""&&!reg.test($(obj).val())){
		tail(obj,'固话格式输入错误！');
	}
}

//验证手机号码(检验13,15,17,18开头的手机号！)
function check_telephone(obj){
	var reg= /^[1][345789]\d{9}$/;
	if($(obj).val()!=""&&!reg.test($(obj).val())){
		tail(obj,'电话号码格式输入错误！');
		return false;
	}
	return true;
}

//验证是否为中文
function isChinese(obj,obj_name){
	var reg=/^[\u0391-\uFFE5]+$/;
	if($(obj).val()!=""&&!reg.test($(obj).val())){
		alert(obj_name+'必须输入中文！');
		$(obj).val("");
		obj.focus();
		return false;
	}
}

//判断是否是IE浏览器

function checkIsIE(){
	if(-[1,]){
		alert("这不是IE浏览器！");
	}else{
		alert("这是IE浏览器！");
	}
}

//检验时间大小(与当前时间比较)
function checkDate(obj,obj_name){
	var obj_value=$(obj).val().replace(/-/g,"/");//替换字符，变成标准格式(检验格式为：'2009-12-10')
	// var obj_value=$(obj).val().replace("-","/");//替换字符，变成标准格式(检验格式为：'2010-12-10 11:12')
	var date1=new Date(Date.parse(obj_value));
	var date2=new Date();//取今天的日期
	if(date1>date2){
		alert(obj_name+"不能大于当前时间！");
		return false;
	}
}

function check_idCard(obj,msg){
	if(!IdCardValidate(obj.val())){
		tail(obj,msg);
	}
}

var Wi = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 ];    // 加权因子
var ValideCode = [ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 ];            // 身份证验证位值.10代表X
function IdCardValidate(idCard) {
	idCard = trim(idCard.replace(/ /g, ""));               //去掉字符串头尾空格
	if (idCard.length == 15) {
		return isValidityBrithBy15IdCard(idCard);       //进行15位身份证的验证
	} else if (idCard.length == 18) {
		var a_idCard = idCard.split("");                // 得到身份证数组
		if(isValidityBrithBy18IdCard(idCard)&&isTrueValidateCodeBy18IdCard(a_idCard)){   //进行18位身份证的基本验证和第18位的验证
			return true;
		}else {
			return false;
		}
	} else {
		return false;
	}
}
/**
 * 判断身份证号码为18位时最后的验证位是否正确
 * @param a_idCard 身份证号码数组
 * @return
 */
function isTrueValidateCodeBy18IdCard(a_idCard) {
	var sum = 0;
	if (a_idCard[17].toLowerCase() == 'x') {
		a_idCard[17] = 10;
	}
	for ( var i = 0; i < 17; i++) {
		sum += Wi[i] * a_idCard[i];
	}
	valCodePosition = sum % 11;
	if (a_idCard[17] == ValideCode[valCodePosition]) {
		return true;
	} else {
		return false;
	}
}
/**
 * 验证18位数身份证号码中的生日是否是有效生日
 * @param idCard 18位书身份证字符串
 * @return
 */
function isValidityBrithBy18IdCard(idCard18){
	var year =  idCard18.substring(6,10);
	var month = idCard18.substring(10,12);
	var day = idCard18.substring(12,14);
	var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));
	// 这里用getFullYear()获取年份，避免千年虫问题
	if(temp_date.getFullYear()!=parseFloat(year)
		||temp_date.getMonth()!=parseFloat(month)-1
		||temp_date.getDate()!=parseFloat(day)){
		return false;
	}else{
		return true;
	}
}
/**
 * 验证15位数身份证号码中的生日是否是有效生日
 * @param idCard15 15位书身份证字符串
 * @return
 */
function isValidityBrithBy15IdCard(idCard15){
	var year =  idCard15.substring(6,8);
	var month = idCard15.substring(8,10);
	var day = idCard15.substring(10,12);
	var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));
	// 对于老身份证中的你年龄则不需考虑千年虫问题而使用getYear()方法
	if(temp_date.getYear()!=parseFloat(year)
		||temp_date.getMonth()!=parseFloat(month)-1
		||temp_date.getDate()!=parseFloat(day)){
		return false;
	}else{
		return true;
	}
}
//去掉字符串头尾空格
function trim(str) {
	return str.replace(/(^\s*)|(\s*$)/g, "");
}

function showTips(obj,msg){
	$(obj).tips({
		side:3,
		msg:msg,
		bg:'#AE81FF',
		time:2
	});
}

function tail(obj,msg){
	showTips(obj,msg);
	$(obj).focus();
	$(obj).val("");
	return false;
}