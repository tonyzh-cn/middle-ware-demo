package com.example.demo.protobuf;

import cn.hutool.http.HttpUtil;
import com.example.demo.protobuf.proto.RevoloanQuotaSrv;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("mock")
@Slf4j
public class MockController {
    //这里需要设置produces为application/x-protobuf
    @PostMapping(value = "proto",produces = "application/x-protobuf")
    @ResponseBody
    public RevoloanQuotaSrv.CreditQuotaQueryResponse proto(@RequestBody RevoloanQuotaSrv.CreditQuotaQueryRequest request){
        return RevoloanQuotaSrv.CreditQuotaQueryResponse.newBuilder().setBorrowerLevel("hello").build();
    }

    @GetMapping("test")
    @ResponseBody
    public String test(){
        try {
            byte[] bodyBytes = HttpUtil.createPost("http://localhost:8080/mock/protobuf")
                    .header("Content-Type","application/x-protobuf")//这里需要设置请求头
                    .body(RevoloanQuotaSrv.CreditQuotaQueryRequest.newBuilder().setProductId(100).build().toByteArray())
                    .execute().bodyBytes();
            RevoloanQuotaSrv.CreditQuotaQueryResponse response = RevoloanQuotaSrv.CreditQuotaQueryResponse.parseFrom(bodyBytes);
            log.info("请求结果反序列化后：{}", response.toString());
        } catch (Exception e) {
           return e.getMessage();
        }
        return "success";
    }
}
