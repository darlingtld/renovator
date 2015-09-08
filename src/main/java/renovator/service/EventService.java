package renovator.service;


import renovator.pojo.Order;
import renovator.pojo.OrderStatus;
import renovator.pojo.User;
import renovator.pojo.message.req.TextMessage;
import renovator.pojo.message.resp.Article;
import renovator.pojo.message.resp.NewsMessage;
import renovator.util.MessageUtil;
import renovator.util.PropertyHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * Created by darlingtld on 2015/4/26.
 */
@Service
public class EventService {

    @Autowired
    private OrderService orderService;

    private static final String ZUIXINDINGDAN = "zxdd";
    private static final String XIUGAIDINGDAN = "xgdd";

    public Set<String> getCodeSet() {
        return codeSet;
    }

    private Set<String> codeSet = new HashSet<>();

    @PostConstruct
    private void init() {
        codeSet.add(ZUIXINDINGDAN);
        codeSet.add(XIUGAIDINGDAN);
    }

    public String doGetNewOrders(String fromUserName, String toUserName) {
        List<Order> orderList = orderService.getByStatus(OrderStatus.NOT_DELIVERED);
        NewsMessage newsMessage = new NewsMessage();
        newsMessage.setToUserName(fromUserName);
        newsMessage.setFromUserName(toUserName);
        newsMessage.setCreateTime(new Date().getTime());
        newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
        newsMessage.setFuncFlag(0);
        List<Article> articleList = new ArrayList<>();
        Article article = new Article();
        article.setTitle("最新订单——共有" + orderList.size() + "笔新订单");
        article.setPicUrl(PropertyHolder.SERVER + "/images/latest_order_inquiry.jpg");
        articleList.add(article);
        if (orderList.isEmpty()) {
            Article oArticle = new Article();
            oArticle.setTitle("暂无新订单");
            articleList.add(oArticle);
        } else {
            for (int i = 0; i < Math.min(5, orderList.size()); i++) {
                Order order = orderList.get(i);
                Article oArticle = new Article();
                oArticle.setTitle(String.format("下单时间:%s\n用户信息:%s", order.getOrderTs(), order.getBuyerInfo()));
                oArticle.setUrl(PropertyHolder.SERVER + "/checkorder.html#/order/details/" + order.getId());
                articleList.add(oArticle);
            }
        }
        newsMessage.setArticleCount(articleList.size());
        newsMessage.setArticles(articleList);
        return MessageUtil.messageToXml(newsMessage);
    }

    public String respond(String content, String fromUserName, String toUserName) {
        String msg = null;
        switch (content) {
            case ZUIXINDINGDAN:
                msg = doGetNewOrders(fromUserName, toUserName);
                break;
            case XIUGAIDINGDAN:
                msg = doModifyOrders(fromUserName, toUserName);
                break;
        }
        return msg;
    }

    private String doModifyOrders(String fromUserName, String toUserName) {
        List<Order> orderList = orderService.getByStatus(OrderStatus.NOT_DELIVERED);
        NewsMessage newsMessage = new NewsMessage();
        newsMessage.setToUserName(fromUserName);
        newsMessage.setFromUserName(toUserName);
        newsMessage.setCreateTime(new Date().getTime());
        newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
        newsMessage.setFuncFlag(0);
        List<Article> articleList = new ArrayList<>();
        Article article = new Article();
        article.setTitle("修改订单——修改未配送订单");
        article.setPicUrl(PropertyHolder.SERVER + "/images/modify_order.png");
        articleList.add(article);
        if (orderList.isEmpty()) {
            Article oArticle = new Article();
            oArticle.setTitle("暂无新订单");
            articleList.add(oArticle);
        } else {
            article.setUrl(PropertyHolder.SERVER + "/modifyorder.html");
        }
        newsMessage.setArticleCount(articleList.size());
        newsMessage.setArticles(articleList);
        return MessageUtil.messageToXml(newsMessage);
    }

    public String doCodeIntro(String fromUserName, String toUserName) {
        TextMessage textMessage = new TextMessage();
        textMessage.setToUserName(fromUserName);
        textMessage.setFromUserName(toUserName);
        textMessage.setCreateTime(new Date().getTime());
        textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
        StringBuffer sb = new StringBuffer();
        sb.append("代码介绍").append("\n");
        sb.append(ZUIXINDINGDAN).append(" : ").append("最新订单").append("\n");
        sb.append(XIUGAIDINGDAN).append(" : ").append("修改订单").append("\n");
        textMessage.setContent(sb.toString());
        return MessageUtil.messageToXml(textMessage);
    }

    public String doMembershipBinding(String fromUserName, String toUserName) {
        NewsMessage newsMessage = new NewsMessage();
        newsMessage.setToUserName(fromUserName);
        newsMessage.setFromUserName(toUserName);
        newsMessage.setCreateTime(new Date().getTime());
        newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
        newsMessage.setFuncFlag(0);
        List<Article> articleList = new ArrayList<Article>();
        Article article = new Article();
        article.setTitle("请绑定账号");
        article.setDescription("绑定会员账号，使用微信获取更多信息");
        article.setPicUrl(PropertyHolder.SERVER + "/images/account_binding.jpg");
//        article.setUrl(PropertyHolder.SERVER + "/account_binding/index.html?open_id=" + fromUserName);
        articleList.add(article);
        newsMessage.setArticleCount(articleList.size());
        newsMessage.setArticles(articleList);
        return MessageUtil.messageToXml(newsMessage);
    }

    public String doAboutUs(String fromUserName, String toUserName) {
        NewsMessage newsMessage = new NewsMessage();
        newsMessage.setToUserName(fromUserName);
        newsMessage.setFromUserName(toUserName);
        newsMessage.setCreateTime(new Date().getTime());
        newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
        newsMessage.setFuncFlag(0);
        List<Article> articleList = new ArrayList<Article>();
        Article article = new Article();
        article.setTitle("公司简介");
        article.setDescription("点击查看店铺地址、联系方式、营业时间");
        article.setPicUrl(PropertyHolder.SERVER + "/images/logo_ex.jpg");
//        article.setUrl(PropertyHolder.SERVER + "/mall/index.html#/aboutus");
        articleList.add(article);
        newsMessage.setArticleCount(articleList.size());
        newsMessage.setArticles(articleList);
        return MessageUtil.messageToXml(newsMessage);
    }

    public String doProductInquiry(String fromUserName, String toUserName) {
        NewsMessage newsMessage = new NewsMessage();
        newsMessage.setToUserName(fromUserName);
        newsMessage.setFromUserName(toUserName);
        newsMessage.setCreateTime(new Date().getTime());
        newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
        newsMessage.setFuncFlag(0);
        List<Article> articleList = new ArrayList<Article>();
        Article article = new Article();
        article.setTitle("寄卖商品查询");
        article.setDescription("点击查询寄卖商品");
        article.setPicUrl(PropertyHolder.SERVER + "/images/product_inquiry.jpg");
        article.setUrl(PropertyHolder.SERVER);
        articleList.add(article);
        newsMessage.setArticleCount(articleList.size());
        newsMessage.setArticles(articleList);
        return MessageUtil.messageToXml(newsMessage);
    }

    public String doCurrentShopActivity(String fromUserName, String toUserName) {
        NewsMessage newsMessage = new NewsMessage();
        newsMessage.setToUserName(fromUserName);
        newsMessage.setFromUserName(toUserName);
        newsMessage.setCreateTime(new Date().getTime());
        newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
        newsMessage.setFuncFlag(0);
        List<Article> articleList = new ArrayList<Article>();
        Article article = new Article();
        article.setTitle("当前正在进行的店铺活动");
        article.setDescription("点击查看当前正在进行的店铺活动");
        article.setPicUrl(PropertyHolder.SERVER + "/images/shop_activity.jpg");
        article.setUrl(PropertyHolder.SERVER);
        articleList.add(article);
        newsMessage.setArticleCount(articleList.size());
        newsMessage.setArticles(articleList);
        return MessageUtil.messageToXml(newsMessage);
    }

    public String doProductShowCase(String fromUserName, String toUserName) {
        NewsMessage newsMessage = new NewsMessage();
        newsMessage.setToUserName(fromUserName);
        newsMessage.setFromUserName(toUserName);
        newsMessage.setCreateTime(new Date().getTime());
        newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
        newsMessage.setFuncFlag(0);
        List<Article> articleList = new ArrayList<Article>();
        Article article = new Article();
        article.setTitle("寄卖商品展示");
        article.setDescription("点击查看寄卖商品展示");
        article.setPicUrl(PropertyHolder.SERVER + "/images/product_showcase.jpg");
        article.setUrl(PropertyHolder.SERVER);
        articleList.add(article);
        newsMessage.setArticleCount(articleList.size());
        newsMessage.setArticles(articleList);
        return MessageUtil.messageToXml(newsMessage);
    }

    public String doMembershipNotification(String fromUserName, String toUserName) {
        User user = null;//userService.getUserWithOpenId(fromUserName);
        NewsMessage newsMessage = new NewsMessage();
        newsMessage.setToUserName(fromUserName);
        newsMessage.setFromUserName(toUserName);
        newsMessage.setCreateTime(new Date().getTime());
        newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
        newsMessage.setFuncFlag(0);
        List<Article> articleList = new ArrayList<Article>();
        Article article = new Article();
        article.setTitle("会员卡提醒（生日月提醒、会员卡到期提醒）");
        article.setDescription("点击设置会员卡提醒（生日月提醒、会员卡到期提醒）");
        article.setPicUrl(PropertyHolder.SERVER + "/images/membership_notification.jpg");
//        article.setUrl(PropertyHolder.SERVER + "/notification/setting.html?open_id=" + fromUserName);
        articleList.add(article);
        newsMessage.setArticleCount(articleList.size());
        newsMessage.setArticles(articleList);
        return MessageUtil.messageToXml(newsMessage);
    }

    public String doAppointmentReceiveFetch(String fromUserName, String toUserName) {
        User user = null;//userService.getUserWithOpenId(fromUserName);

        NewsMessage newsMessage = new NewsMessage();
        newsMessage.setToUserName(fromUserName);
        newsMessage.setFromUserName(toUserName);
        newsMessage.setCreateTime(new Date().getTime());
        newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
        newsMessage.setFuncFlag(0);
        List<Article> articleList = new ArrayList<Article>();
        Article article = new Article();
        article.setTitle("预约上门收货/送货");
        article.setDescription("点击预约上门收货/送货");
        article.setPicUrl(PropertyHolder.SERVER + "/images/appointment.jpg");
//        article.setUrl(PropertyHolder.SERVER + "/appointment/appointment.html?open_id=" + fromUserName);
        articleList.add(article);
        newsMessage.setArticleCount(articleList.size());
        newsMessage.setArticles(articleList);
        return MessageUtil.messageToXml(newsMessage);
    }

    public String doErrorHandler(String fromUserName, String toUserName) {
        NewsMessage newsMessage = new NewsMessage();
        newsMessage.setToUserName(fromUserName);
        newsMessage.setFromUserName(toUserName);
        newsMessage.setCreateTime(new Date().getTime());
        newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
        newsMessage.setFuncFlag(0);
        List<Article> articleList = new ArrayList<Article>();
        Article article = new Article();
        article.setTitle("服务器正在休息中，稍后再试吧~~");
        article.setDescription("OooooOoooOooOo.");
        article.setPicUrl(PropertyHolder.SERVER + "/images/logo.png");
        article.setUrl(PropertyHolder.SERVER);
        articleList.add(article);
        newsMessage.setArticleCount(articleList.size());
        newsMessage.setArticles(articleList);
        return MessageUtil.messageToXml(newsMessage);
    }

    public String doCurrentOrderStatus(String fromUserName, String toUserName) {
//        User user = userService.getUserWithOpenId(fromUserName);
//        if (user == null) {
//            throw new UserNotFoundException("User not found");
//        }
//
//        List<com.renovator.pojo.Service> serviceList = serviceService.getUncheckedServiceListByUserId(user.getId());
//        NewsMessage newsMessage = new NewsMessage();
//        newsMessage.setToUserName(fromUserName);
//        newsMessage.setFromUserName(toUserName);
//        newsMessage.setCreateTime(new Date().getTime());
//        newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
//        newsMessage.setFuncFlag(0);
//        List<Article> articleList = new ArrayList<>();
//        if (null == serviceList || serviceList.isEmpty()) {
//            Article article = new Article();
//            article.setTitle("服务单目前状态");
//            article.setDescription("您当前还没有待处理的订单");
//            article.setPicUrl(PropertyHolder.SERVER + "/images/order_status.jpg");
//            articleList.add(article);
//        } else {
//            Article article = new Article();
//            article.setTitle("服务单目前状态");
//            article.setPicUrl(PropertyHolder.SERVER + "/images/order_status.jpg");
//            articleList.add(article);
//
//            for (com.renovator.pojo.Service service : serviceList) {
//                Article record = new Article();
//                record.setTitle(String.format("日期 ：%s\n商品 ：%s\n订单状态 ：%s", service.getTs(), service.getProduct().getName(), service.getStatus()));
//                articleList.add(record);
//            }
//
//        }
//
//
//        newsMessage.setArticleCount(articleList.size());
//        newsMessage.setArticles(articleList);
//        return MessageUtil.messageToXml(newsMessage);
        return null;
    }

    public String doMembershipBalance(String fromUserName, String toUserName) {
        User user = null;//userService.getUserWithOpenId(fromUserName);

        NewsMessage newsMessage = new NewsMessage();
        newsMessage.setToUserName(fromUserName);
        newsMessage.setFromUserName(toUserName);
        newsMessage.setCreateTime(new Date().getTime());
        newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
        newsMessage.setFuncFlag(0);
        List<Article> articleList = new ArrayList<Article>();
        Article article = new Article();
        article.setTitle("会员卡余额");
        article.setPicUrl(PropertyHolder.SERVER + "/images/membership_balance.jpg");
        articleList.add(article);

        Article username = new Article();
        username.setTitle(String.format("会员名 ：%s", user.getUsername()));
        articleList.add(username);

        Article balance = new Article();
        balance.setTitle(String.format("会员卡余额 ：%s 元", user.getAccount()));
        articleList.add(balance);

        newsMessage.setArticleCount(articleList.size());
        newsMessage.setArticles(articleList);

        return MessageUtil.messageToXml(newsMessage);
    }

    public String doMembershipExpense(String fromUserName, String toUserName) {
//        User user = userService.getUserWithOpenId(fromUserName);
//        if (user == null) {
//            throw new UserNotFoundException("User not found");
//        }
//        List<com.renovator.pojo.Service> serviceList = serviceService.getServiceListByUserId(user.getId(), 3);
//        NewsMessage newsMessage = new NewsMessage();
//        newsMessage.setToUserName(fromUserName);
//        newsMessage.setFromUserName(toUserName);
//        newsMessage.setCreateTime(new Date().getTime());
//        newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
//        newsMessage.setFuncFlag(0);
//        List<Article> articleList = new ArrayList<>();
//        if (null == serviceList || serviceList.isEmpty()) {
//            Article article = new Article();
//            article.setTitle("会员卡消费记录");
//            article.setDescription("您还没有消费过哦");
//            article.setPicUrl(PropertyHolder.SERVER + "/repository/images/membership_expense.jpg");
//            articleList.add(article);
//        } else {
//            Article article = new Article();
//            article.setTitle("会员卡消费记录");
//            article.setDescription("点击查看您的所有消费记录");
//            article.setPicUrl(PropertyHolder.SERVER + "/repository/images/membership_expense.jpg");
//            article.setUrl(PropertyHolder.SERVER + "/expense/expense_record.html?open_id=" + fromUserName);
//            articleList.add(article);
//
//            //list the latest three expense record
//            for (com.renovator.pojo.Service service : serviceList) {
//                Article record = new Article();
//                record.setTitle(String.format("日期 ：%s\n商品 ：%s", service.getTs(), service.getProduct().getName()));
//                articleList.add(record);
//            }
//        }
//
//        newsMessage.setArticleCount(articleList.size());
//        newsMessage.setArticles(articleList);
//        return MessageUtil.messageToXml(newsMessage);
        return null;
    }
}
