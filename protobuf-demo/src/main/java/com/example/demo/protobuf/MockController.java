package com.example.demo.protobuf;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.example.demo.protobuf.proto.RevoloanQuotaSrv;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


@RestController
@RequestMapping("mock")
@Slf4j
public class MockController {
    //这里需要设置produces为application/x-protobuf
    @PostMapping(
            value = {"proto"}
//            ,produces = {"application/x-protobuf"}
    )
    @ResponseBody
    public RevoloanQuotaSrv.CreditQuotaQueryResponse proto(@RequestBody RevoloanQuotaSrv.CreditQuotaQueryRequest request){
        return RevoloanQuotaSrv.CreditQuotaQueryResponse.newBuilder().setBorrowerLevel("hello").build();
    }

    @GetMapping("test/pb")
    @ResponseBody
    public String testPb(){
        try {
            byte[] bodyBytes = HttpUtil.createPost("http://localhost:8080/mock/proto")
                    .header("Content-Type","application/x-protobuf")//这里需要设置请求头
                    .header("Accept", "application/x-protobuf")
                    .body(RevoloanQuotaSrv.CreditQuotaQueryRequest.newBuilder().setProductId(100).build().toByteArray())
                    .execute().bodyBytes();
            RevoloanQuotaSrv.CreditQuotaQueryResponse response = RevoloanQuotaSrv.CreditQuotaQueryResponse.parseFrom(bodyBytes);
            log.info("请求结果反序列化后：{}", response.toString());
        } catch (Exception e) {
           return e.getMessage();
        }
        return "success";
    }

    @GetMapping("test/json")
    @ResponseBody
    public String testJson(){
        try {
            HashMap<Object, Object> params=new HashMap<>();
            params.put("productId", "100");
            String response = HttpUtil.createPost("http://localhost:8080/mock/proto")
                    .header("Content-Type", "application/x-protobuf")//这里需要设置请求头
                    .header("Accept", "application/json")
                    .body(RevoloanQuotaSrv.CreditQuotaQueryRequest.newBuilder().setProductId(100).build().toByteArray())
                    .execute().body();
            log.info("返回结果：{}", response);
        } catch (Exception e) {
            return e.getMessage();
        }
        return "success";
    }
}
