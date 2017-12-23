package credit;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.HttpClient;
import common.JsonUtils;
import common.MDUtil;
import common.StringUtils;
import org.apache.http.message.BasicNameValuePair;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @Description:立木征信主流程
 * @author: york
 * @date: 2017-02-27 14:38
 * @version: v1.2.0
 */
public class AbstractCredit {

	public BlockingQueue<String> queue=new ArrayBlockingQueue<>(3);

	public BlockingQueue<String> queue2=new ArrayBlockingQueue<>(3);
	
    public static HttpClient httpClient = new HttpClient();

    //定义共同参数
  //  public static final String apiUrl = "https://t.limuzhengxin.cn/mobile_report/v1/task";//服务地址---需客户指定---需联系立木开启IP白名单
  //  public static final String apiUrl = "https://t.limuzhengxin.cn/api/gateway";
  //  public static final String apiKey = "2089970649870861";//API授权 key ---需客户指定
  //  public static final String apiSecret = "5WkqP7toS67S57lz92ACPAu3Zytjzrpl";//API授权 secret---需客户指定

    public static final String apiUrl = "https://api.limuzhengxin.com";//服务地址---需客户指定---需联系立木开启IP白名单
    public static final String apiKey = "2626504090684141";//API授权 key ---需客户指定
    public static final String apiSecret = "qcFPrxakUH0qs8U6SV4l7wNG9XSgalTb";//API授权 secret---需客户指定
    
    public static final String apiUrlTask = apiUrl + "/mobile_report/v1/task";//运营商报告采集任务提交地址
    public static final String apiUrlStatus = apiUrl + "/mobile_report/v1/task/status";//运营商报告采集状态查询地址
    public static final String apiUrlInput = apiUrl + "/mobile_report/v1/task/input";//运营商报告验证码输入地址
    public static final String apiUrlReport = apiUrl + "/mobile_report/v1/task/report";//运营商报告结果获取地址
    public static final String apiUrlData = apiUrl + "/mobile_report/v1/task/data";//运营商报告原始数据获取地址
    
    public static final String accessType = "api";//api:api接入、h5:h5接入、sdk:sdk接入、默认api
    public static final String ts = System.currentTimeMillis() + ""; //用户请求提交的时间戳(毫秒) 如:1404955893000
    
    public final long timeInterval = 5000;//轮训时间 默认5秒

    public static String token = null;

    /**
     * 共同处理流程
     * @param reqParam
     * @throws Exception
     */
    public String doProcess(List<BasicNameValuePair> reqParam) throws Exception{

        //提交受理请求
        String json = httpClient.doPost(apiUrlTask, reqParam);
        System.out.println("json=" + json);
        if(StringUtils.isBlank(json)) {
            System.out.println("查询失败");
            return json;
        } else {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readValue(json, JsonNode.class);
            String code = rootNode.get("code").textValue();

            if("0010".equals(code)) {//受理成功

                token = rootNode.get("token").textValue();
                timer();//每5秒查询一次数据请求
                return json;
            } else {
                System.out.println("查询失败");
                return json;
            }
        }

    }

    /**
     * @param @return
     * @throws
     * @Description:定时器
     */
    public void timer() {
        System.out.println("轮循 start");
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            public void run() {

                try {
                    //循环取的状态，查询结果
                    //停止循环(发送短信失败或信息查询成功)
                    if (roundRobin()) {

                        System.out.println("轮循 end");
                        System.out.println("获取信息结束");
                        timer.cancel();// 停止定时器

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //异常
                }
            }
        }, 0, timeInterval);
    }

    /**
     * @param @param  token
     * @param @return
     * @throws Exception true 停止循环(发送输入信息失败或信息查询成功)，false:未取到结果集或成功取结果集
     * @throws
     * @Description:循环取的状态，查询结果
     */
    public boolean roundRobin() throws Exception {

        //状态查询
        String json = httpClient.doPost(apiUrlStatus, getReqParam());
        JsonNode rootNode = JsonUtils.toJsonNode(json);
        String token = JsonUtils.getJsonValue(rootNode, "token");
        String code =JsonUtils.getJsonValue(rootNode, "code");
        String msg = JsonUtils.getJsonValue(rootNode, "msg");
        System.out.println("循环取的状态:" + code);
        System.out.println("循环取的信息:" + json);
             
        if("2010".equals(code)){
        	queue2.put(json);
        	return true;
        }
        
        if("1013".equals(code)){
        	queue2.put(json);
        	return true;
        }
        
        if (StringUtils.isBlank(code)) {
            //未取到结果集
            return false;
        }

        //0开头标处理成功相关
        if (code.startsWith("0")) {

            if ("0100".startsWith(code)) {//登陆成功
                System.out.println("对象结果查询 >>>>>" + msg);
                return false;
            } else if ("0006".equals(code)) {//等待输入信息

                JsonNode inputNode = rootNode.get("input");

                queue2.put(json);
                
                //获取等待输入类型
                String type = JsonUtils.getJsonValue(inputNode, "type");
                //图片验证码和二维码为base64编码的图片
                String value = JsonUtils.getJsonValue(inputNode, "value");
                //保存到本地作识别用-根据实际业务场景处理
                if (StringUtils.isNotBlank(value)) {
                    StringUtils.GenerateImage(value, token + ".jpeg");
                }

                //是否需要提交信息
                boolean bInput = false;
                if("sms".equals(type)){//短信验证码
                	System.out.println("请提交收到的短信验证码 >>>>>");
                    bInput = true;
                }else if("img".equals(type)){//图片验证码
                	System.out.println("请提交识别出的图片验证码 >>>>>");
                    bInput = true;
                }else if("qr".equals(type)){//二维码
                	System.out.println("请扫描收到的图片二维码 >>>>>");
                    System.out.println("二维码保存在当前程序跟目录下,二维码名称为：【"+token + ".jpeg"+"】 >>>>>");
                    bInput = false;
                }else if("idp".equals(type)){//独立密码
                	System.out.println("请提交您的独立密码 >>>>>");
                    bInput = true;
                }
                
                if (bInput) {
                    //等待输入信息
                    //code = sendInput();
                    code=queue.take();
                	System.out.println("发送输入信息后查询状态：" + code);
                    if ("0009".equals(code)) {//结果    >>>>> 成功或失败
                        //继续轮训
                        return false;
                    } else {
                        //发送失败
                        return true;
                    }
                } else {
                    //继续轮训
                    return false;
                }
            } else if ("0000".startsWith(code)) {//成功
            	 System.out.println("运营商报告结果查询开始 >>>>>");
                 getReport();
                 System.out.println("运营商报告结果查询结束 >>>>>");

                 System.out.println("运营商报告原始数据结果查询开始 >>>>>");
                 getData();
                 System.out.println("运营商报告原始数据结果查询结束 >>>>>");

                queue2.put(json);
                //发送对象结果查询
                getData();
                return true;
            }
            //其他情况继续轮训
            else {
            	queue2.put(json);
                return false;
            }
        }

        //其他异常停止循环
        return true;
    }

    /**
     * 签名转化
     *
     * @param reqParam
     * @return
     */
    public String getSign(List<BasicNameValuePair> reqParam) {

        StringBuffer sbb = new StringBuffer();
        int index = 1;
        for (BasicNameValuePair nameValuePair : reqParam) {
            sbb.append(nameValuePair.getName() + "=" + nameValuePair.getValue());
            if (reqParam.size() != index) {
                sbb.append("&");
            }
            index++;
        }
        String sign = sbb.toString();

        Map<String, String> paramMap = new HashMap<String, String>();
        String ret = "";
        if (!StringUtils.isEmpty(sign)) {
            String[] arr = sign.split("&");
            for (int i = 0; i < arr.length; i++) {
                String tmp = arr[i];
                if (-1 != tmp.indexOf("=")) {
                    paramMap.put(tmp.substring(0, tmp.indexOf("=")), tmp.substring(tmp.indexOf("=") + 1, tmp.length()));
                }

            }
        }
        List<Map.Entry<String, String>> list = new ArrayList<Map.Entry<String, String>>(paramMap.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, String>>() {
            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                int ret = 0;
                ret = o1.getKey().compareTo(o2.getKey());
                if (ret > 0) {
                    ret = 1;
                } else {
                    ret = -1;
                }
                return ret;
            }
        });

        StringBuilder sbTmp = new StringBuilder();
        for (Map.Entry<String, String> mapping : list) {
            if (!"sign".equals(mapping.getKey())) {
                sbTmp.append(mapping.getKey()).append("=").append(mapping.getValue()).append("&");
            }
        }
        sbTmp.setLength(sbTmp.lastIndexOf("&"));
        sbTmp.append(apiSecret);
        ret = MDUtil.SHA1(sbTmp.toString());

        //System.out.println(sb.toString());
        return ret;
    }

    /**
     * 获取共同提交参数
     *
     * @return
     */
    public List<BasicNameValuePair> getReqParam() {
        List<BasicNameValuePair> reqParam = new ArrayList<BasicNameValuePair>();
        reqParam.add(new BasicNameValuePair("apiKey", apiKey));//API授权
        reqParam.add(new BasicNameValuePair("token", token));
        reqParam.add(new BasicNameValuePair("sign", getSign(reqParam)));//请求参数签名
        return reqParam;
    }


    /**
     * @param @return
     * @throws Exception
     * @throws
     * @Description:信息输入
     */
    public String sendInput(String s) throws Exception {
      //  Scanner s = new Scanner(System.in);
        System.out.println("请输入上述提示信息："+s);
        List<BasicNameValuePair> reqParam = new ArrayList<BasicNameValuePair>();
        reqParam.add(new BasicNameValuePair("apiKey", apiKey));//API授权
        reqParam.add(new BasicNameValuePair("token", token));//请求标识
        reqParam.add(new BasicNameValuePair("input", s));//短信验证码/图片验证码/独立密码

        reqParam.add(new BasicNameValuePair("sign", getSign(reqParam)));//请求参数签名
        String json = httpClient.doPost(apiUrlInput, reqParam);
        return JsonUtils.getJsonValue(json, "code");
    }


    /**
     * @param
     * @throws
     * @Description:运营商报告结果查询
     */
    public void getReport() {
        List<BasicNameValuePair> reqParam = new ArrayList<BasicNameValuePair>();

        reqParam.add(new BasicNameValuePair("apiKey", apiKey));//API授权
        reqParam.add(new BasicNameValuePair("token", token));//请求标识

        reqParam.add(new BasicNameValuePair("sign", getSign(reqParam)));//请求参数签名
        String json = httpClient.doPost(apiUrlReport, reqParam);
        System.out.println("运营商报告结果集:" + json);
    }
    
    /**
     * @param
     * @throws
     * @Description:查询的结果集(结果查询)
     */
    public void getData() {
        List<BasicNameValuePair> reqParam = new ArrayList<BasicNameValuePair>();
        reqParam.add(new BasicNameValuePair("apiKey", apiKey));//API授权
        reqParam.add(new BasicNameValuePair("token", token));//请求标识

        reqParam.add(new BasicNameValuePair("sign", getSign(reqParam)));//请求参数签名
        String json = httpClient.doPost(apiUrlData, reqParam);
        System.out.println("查询结果集:" + json);
    }

    /**
     * 获取业务类型
     *
     * @return
     */
    public String getBizType() {
        throw new RuntimeException("请重写该方法");
    }
}
