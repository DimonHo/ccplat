package com.cc.validate.api;


import com.cc.validate.dao.LoginDao;

/**
 * 登录业务层
 * 
 * @author 	wxj
 * @createtime 2014.9.1
 */
public class LoginApi {
	/**
	 * 企业登录验证
	 * @param ccno		cc账号
	 * @param ccpwd		cc密码
	 * @param companyno	企业编号
	 * @return
	 */
	public static String getWebLogin(String ccno,String ccpwd,String companyno){
		
		String valiCccompanycc = LoginDao.validateCccompanycc(ccno, companyno);
		
		if(!"0".equals(valiCccompanycc)){
			return valiCccompanycc;
		}
		
		String valiCcuser = LoginDao.validateCcuser(ccno, ccpwd);
		
		if(!"0".equals(valiCcuser)){
			return valiCcuser;
		}
		
		String valicode = LoginDao.getValiCode();
		return valiCcuser + "_" + valicode;
	}
	/**
	 * 企业用户修改密码
	 * @param ccno		cc账号
	 * @param ccpwd		cc原密码
	 * @param newccpwd	cc新密码
	 * @return
	 */
	public static String updatePwd(String ccno, String ccpwd,String newccpwd) {
		return LoginDao.updatePwd(ccno, ccpwd, newccpwd);
	}
	/**
	 * 验证号是否匹配
	 * @param valicode	验证码
	 * @return
	 */
	public static boolean isValiCode(String valicode) {
		boolean bool = false;
		try {
			String code = LoginDao.getValiCode();
			if(code == null){
				return bool;
			}else if(code.toLowerCase().equals(valicode.toLowerCase())){
				bool = true;
			}else{
				bool = false;
			}
		}catch (Exception e) {
			// TODO: handle exception
			bool = false;
		}
		return bool;
	}
}
