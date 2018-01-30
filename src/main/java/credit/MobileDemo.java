package credit;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSONObject;
import com.voucher.manage.tools.MyTestUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:运营商
 * @author kx
 * @version v1.2.0
 */
public class MobileDemo extends AbstractCredit {

    //业务参数
  // public static final String method = "api.mobile.get";//请求接口
 	
    public static final String bizType = "mobile";//业务类型
    public static final String callBackUrl = "";//回调地址

    /*
    public static final String username = "158111111";//账号---需客户指定
    public static final String password = "3";//密码---需客户指定
    public static final String identityName="1";
    public static final String identityCardNo="510";
    */
    public static final String contentType = "all";//内容类型---需客户指定

    public static void main(String[] args) throws Exception {

        //启动信服务

    }

    
    private String footing(String contactRelationship){
    	String contactRelationship2 = null;
    	 if(contactRelationship.equals("父亲")){
    		 contactRelationship2="FATHER";
    	 }else if(contactRelationship.equals("母亲")){
    		 contactRelationship2="MOTHER";
		}else if(contactRelationship.equals("配偶")){
			contactRelationship2="SPOUSE";
		}else if(contactRelationship.equals("子女")){
			contactRelationship2="CHILD";
		}else if(contactRelationship.equals("其他亲属")){
			contactRelationship2="OTHER_RELATIVE";
		}else if(contactRelationship.equals("朋友")){
			contactRelationship2="FRIEND";
		}else if(contactRelationship.equals("同事")){
			contactRelationship2="COWORKER";
		}else if(contactRelationship.equals("其他")){
			contactRelationship2="OTHERS";
		}
    	 
    	 return contactRelationship2;
    }

	public JSONObject process(String username,String password,String identityName,String identityCardNo
			,String clientInfo) throws Exception{

        System.out.println("开始获取运营商信息");

        JSONObject jsonObject=new JSONObject();
        
        try {

        	 org.json.JSONObject jsonObjectorg= new org.json.JSONObject(clientInfo);
			 String contactName1st=jsonObjectorg.getString("contactName1st");
			 String contactIdentityNo1st=jsonObjectorg.getString("contactIdentityNo1st");
			 String contactMobile1st=jsonObjectorg.getString("contactMobile1st");
			 String contactRelationship1st=footing(jsonObjectorg.getString("contactRelationship1st"));
			 
			 String contactName2nd=jsonObjectorg.getString("contactName2nd");
			 String contactIdentityNo2nd=jsonObjectorg.getString("contactIdentityNo2nd");
			 String contactMobile2nd=jsonObjectorg.getString("contactMobile2nd");
			 String contactRelationship2nd=footing(jsonObjectorg.getString("contactRelationship2nd"));
			 
			 String contactName3rd=jsonObjectorg.getString("contactName3rd");
			 String contactIdentityNo3rd=jsonObjectorg.getString("contactIdentityNo3rd");
			 String contactMobile3rd=jsonObjectorg.getString("contactMobile3rd");
			 String contactRelationship3rd=footing(jsonObjectorg.getString("contactRelationship3rd"));
             
			 
			 
            //提交受理请求对象
            List<BasicNameValuePair> reqParam = new ArrayList<BasicNameValuePair>();

            System.out.println(contactName1st+"   "+contactMobile1st+"  "
            		+"  "+contactIdentityNo1st+"  "+contactRelationship1st);
            
           
            
            reqParam.add(new BasicNameValuePair("apiKey", apiKey));//API授权
            reqParam.add(new BasicNameValuePair("callBackUrl", callBackUrl));//callBackUrl
            reqParam.add(new BasicNameValuePair("accessType", accessType));
            
            reqParam.add(new BasicNameValuePair("username", username));//账号
            reqParam.add(new BasicNameValuePair("password",  new String(Base64.encodeBase64(password.getBytes("UTF-8")))));//密码
            reqParam.add(new BasicNameValuePair("identityName", identityName));
            reqParam.add(new BasicNameValuePair("identityCardNo", identityCardNo));
            
            reqParam.add(new BasicNameValuePair("otherInfo", ""));//其他信息
            
            if(!contactName1st.equals("")&&!contactMobile1st.equals("")){
            	reqParam.add(new BasicNameValuePair("contactName1st", contactName1st));
            	reqParam.add(new BasicNameValuePair("contactMobile1st", contactMobile1st));
            	reqParam.add(new BasicNameValuePair("contactIdentityNo1st", contactIdentityNo1st));    
            	reqParam.add(new BasicNameValuePair("contactRelationship1st", contactRelationship1st));
            }
            
            if(!contactName2nd.equals("")&&!contactMobile2nd.equals("")){
            	reqParam.add(new BasicNameValuePair("contactName2nd", contactName2nd));
            	reqParam.add(new BasicNameValuePair("contactIdentityNo2nd", contactIdentityNo2nd));
            	reqParam.add(new BasicNameValuePair("contactMobile2nd", contactMobile2nd));    
            	reqParam.add(new BasicNameValuePair("contactRelationship2nd", contactRelationship2nd));
            }
            
            if(!contactName3rd.equals("")&&!contactMobile3rd.equals("")){
            	reqParam.add(new BasicNameValuePair("contactName3rd", contactName3rd));
            	reqParam.add(new BasicNameValuePair("contactIdentityNo3rd", contactIdentityNo3rd));
            	reqParam.add(new BasicNameValuePair("contactMobile3rd", contactMobile3rd));    
            	reqParam.add(new BasicNameValuePair("contactRelationship3rd", contactRelationship3rd));
            }
            
            reqParam.add(new BasicNameValuePair("sign", getSign(reqParam)));//请求参数签名
            System.out.println(reqParam);
            String json=doProcess(reqParam);
            System.out.println(json);
            jsonObject=JSONObject.parseObject(json);
            return jsonObject;
        }catch (Exception ex){
            System.out.println("开始获取运营商信息异常：" + ex);
            ex.printStackTrace();
            jsonObject.put("msg", "获取运营商信息异常");
            return jsonObject;           
        }
	}

    /**
     * 获取业务类型
     */
    @Override
    public String getBizType(){
        return bizType;
    }
}