package renovator.service;

/**
 * Created by darlingtld on 2015/2/20.
 */


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import renovator.pojo.AccessToken;
import renovator.pojo.button.ClickButton;
import renovator.pojo.button.ComplexButton;
import renovator.pojo.button.ViewButton;
import renovator.util.PropertyHolder;
import renovator.util.WeixinUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class MenuManager {
    private static Logger log = LoggerFactory.getLogger(MenuManager.class);

    public static void main(String[] args) throws UnsupportedEncodingException {
        String appId = PropertyHolder.APPID;
        String appSecret = PropertyHolder.APPSECRET;

        AccessToken at = WeixinUtil.getAccessToken(appId, appSecret);
        String jsonMenu = getMenu().toJSONString();
        log.debug(jsonMenu);
        if (null != at) {
            int result = WeixinUtil.createMenu(jsonMenu, at.getToken());

            if (0 == result) {
                log.info("菜单创建成功");
            } else {
                log.info("菜单创建失败，错误码：{}", result);
            }
        }
    }

    private static JSONObject getMenu() throws UnsupportedEncodingException {
        ClickButton btn11 = new ClickButton();
        btn11.setName(PropertyHolder.MENU_MEMBERSHIP_BALANCE);
        btn11.setKey(PropertyHolder.MENU_MEMBERSHIP_BALANCE);

        ClickButton btn12 = new ClickButton();
        btn12.setName(PropertyHolder.MENU_MEMBERSHIP_EXPENSE);
        btn12.setKey(PropertyHolder.MENU_MEMBERSHIP_EXPENSE);

        ClickButton btn13 = new ClickButton();
        btn13.setName(PropertyHolder.MENU_CURRENT_ORDER_STATUS);
        btn13.setKey(PropertyHolder.MENU_CURRENT_ORDER_STATUS);

        ClickButton btn14 = new ClickButton();
        btn14.setName(PropertyHolder.MENU_APPOINTMENT_RECEIVE_FETCH);
        btn14.setKey(PropertyHolder.MENU_APPOINTMENT_RECEIVE_FETCH);

        ClickButton btn15 = new ClickButton();
        btn15.setName(PropertyHolder.MENU_MEMBERSHIP_NOTIFICATION);
        btn15.setKey(PropertyHolder.MENU_MEMBERSHIP_NOTIFICATION);

        ClickButton btn21 = new ClickButton();
        btn21.setName(PropertyHolder.MENU_PRODUCT_SHOWCASE);
        btn21.setKey(PropertyHolder.MENU_PRODUCT_SHOWCASE);

        ClickButton btn22 = new ClickButton();
        btn22.setName(PropertyHolder.MENU_PRODUCT_INQUIRY);
        btn22.setKey(PropertyHolder.MENU_PRODUCT_INQUIRY);

        ClickButton btn31 = new ClickButton();
        btn31.setName(PropertyHolder.MENU_ABOUT_US);
        btn31.setKey(PropertyHolder.MENU_ABOUT_US);

        ClickButton btn32 = new ClickButton();
        btn32.setName(PropertyHolder.MENU_CURRENT_SHOP_ACTIVITY);
        btn32.setKey(PropertyHolder.MENU_CURRENT_SHOP_ACTIVITY);

        ComplexButton mainBtn1 = new ComplexButton();
        mainBtn1.setName(PropertyHolder.CMENU_MEMBERSHIP);
        mainBtn1.setSub_button(new ClickButton[]{btn11, btn12, btn13,btn14,btn15});

        ComplexButton mainBtn2 = new ComplexButton();
        mainBtn2.setName(PropertyHolder.CMENU_SERVICE);
        mainBtn2.setSub_button(new ClickButton[]{btn21, btn22});

        ComplexButton mainBtn3 = new ComplexButton();
        mainBtn3.setName(PropertyHolder.CMENU_COMPANY);
        mainBtn3.setSub_button(new ClickButton[]{btn31, btn32});

        JSONArray jsonArray = new JSONArray();
        jsonArray.add(mainBtn1);
        jsonArray.add(mainBtn2);
        jsonArray.add(mainBtn3);

        JSONObject menu = new JSONObject();
        menu.put("button", jsonArray);
        System.out.println(menu);
        return menu;
    }
}
