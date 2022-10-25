package com.gengxiankun.familycapitalpool.listener;

import com.gengxiankun.familycapitalpool.dao.BillDao;
import com.gengxiankun.familycapitalpool.entity.Bill;
import com.gengxiankun.familycapitalpool.event.NotionEvent;
import com.gengxiankun.notion.NotionClient;
import com.gengxiankun.notion.builder.PageBuilder;
import com.gengxiankun.notion.builder.ParentBuilder;
import com.gengxiankun.notion.builder.PropertiesValueBuilder;
import com.gengxiankun.notion.entity.Page;
import com.gengxiankun.notion.entity.Parent;
import com.gengxiankun.notion.entity.PropertyValue;
import com.gengxiankun.notion.entity.ResponseBody;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

/**
 * @author xiankun.geng
 */
@Component
@RequiredArgsConstructor
public class NotionListener {

    private static final Logger logger = LoggerFactory.getLogger(NotionListener.class);

    private final BillDao billDao;

    @Value("${notion.secret}")
    private String secret;

    @Value("${notion.database_id}")
    private String databaseId;

    @Async
    @SneakyThrows
    @EventListener(NotionEvent.class)
    public void syncNotion(NotionEvent event) {
        // 获取账单信息
        Long billId = event.getBillId();
        Optional<Bill> billOptional = this.billDao.findById(billId);
        if (!billOptional.isPresent()) {
            return;
        }
        Bill bill = billOptional.get();
        // 同步 notion
        NotionClient client = new NotionClient(this.secret);
        Parent parent = new ParentBuilder().buildDatabase(this.databaseId);
        Map<String, PropertyValue> properties = new PropertiesValueBuilder()
                .buildTitle("单号", bill.getId().toString())
                .buildSelect("类型", bill.getCapitalType().getName())
                .buildDate("日期", bill.getCreatedAt().toString())
                .buildNumber("金额", bill.getAmount())
                .buildNumber("借调", bill.getBorrowingAmount())
                .buildSelect("期次", bill.getCapitalPool().getTime().toString())
                .getProperties();
        Page page = new PageBuilder().build(parent, properties);
        ResponseBody responseBody = client.createPage(page);
        logger.info("Notion 账单同步成功，ID：{}", responseBody.getId());
    }

}
